package com.mkl.mkltest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import io.swagger.annotations.ApiOperation;
import com.mkl.mkltest.controller.api.UserLogin;
import com.mkl.mkltest.controller.api.UserRegister;
import com.mkl.mkltest.entity.User;
import com.mkl.mkltest.spring.FirebaseAppConfig;
import com.mkl.mkltest.spring.SecurityTokenConfig;
import com.mkl.mkltest.utility.AuthorityCryptor;
import com.mkl.mkltest.utility.CommonMethod;
import com.mkl.mkltest.utility.UtilitiesMethod;

@RestController
@RequestMapping(path = "/user") 
public class UserController {
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    FirebaseAppConfig db;
    @GetMapping(value = "/getAllUser")
    public List<User> getAllUser() throws InterruptedException, ExecutionException {
        List<User> lUsers = new ArrayList<User>();
        CollectionReference cr = db.getFirebase().collection("User");
        ApiFuture<QuerySnapshot> querySnapShot = cr.get();
        for(DocumentSnapshot doc : querySnapShot.get().getDocuments()){
            User user = doc.toObject(User.class);
            lUsers.add(user);
        }
		return lUsers;
    }
    @PostMapping(value = "/getUser")
    public User getAllUser(@RequestBody User userReq) throws InterruptedException, ExecutionException {
        // User user = db.getFirebase().collection("User").document(id).get().get().toObject(User.class);
        CollectionReference cr = db.getFirebase().collection("User");
        
        User user = cr.document(userReq.getUserName()).get().get().toObject(User.class);
		return user;
    }
	@PostMapping(value = "/register")
	public String register(@RequestBody UserRegister userRegisterRequest) throws InterruptedException, ExecutionException {
		User user = db.getFirebase().document("Users/" + userRegisterRequest.getUserName()).get().get().toObject(User.class);
		CollectionReference cr = db.getFirebase().collection("User");
		ApiFuture<QuerySnapshot> querySnapShot = cr.get();
		int phoneNumberCount=0;
		for(DocumentSnapshot doc : querySnapShot.get().getDocuments()){
			User eachUser = doc.toObject(User.class);
			if (eachUser.getPhoneNumber().equalsIgnoreCase(userRegisterRequest.getPhoneNumber())){
				phoneNumberCount++;
			}
		}
		if (phoneNumberCount>1){
			throw new ResponseStatusException(HttpStatus.CONFLICT, userRegisterRequest.getPhoneNumber() + " limit account ");
		}
		if (user != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, userRegisterRequest.getUserName() + " already exists ");
		}
        if (userRegisterRequest.getUserName().length()<0 &&  userRegisterRequest.getUserName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserName null");
		}
		if (userRegisterRequest.getPhoneNumber().length()<0 &&  userRegisterRequest.getUserName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserName null");
		}
		user = new User();
		user.setPhoneNumber(userRegisterRequest.getPhoneNumber());
		user.setBirthDay(userRegisterRequest.getBirthDay());
        user.setId(userRegisterRequest.getUserName()+System.currentTimeMillis()); 
        user.setUserName(userRegisterRequest.getUserName());
        user.setFullName(userRegisterRequest.getFullName());
		user.setZalo(userRegisterRequest.getZalo());
		user.setBankCode(userRegisterRequest.getBankCode());
		user.setBankAccountNumber(userRegisterRequest.getBankAccountNumber());
		user.setBirthDayInt(CommonMethod.getSimpleDayToInt(userRegisterRequest.getBirthDay()));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
		user.setPassword(hashedPassword);
		String hashedSecret = passwordEncoder.encode(userRegisterRequest.getSecretWord());
		user.setSecretWord(hashedSecret);
		db.getFirebase().document("User/" + user.getUserName()).set(user);
		return "OK";
	}
    @PostMapping(value = "/login")
	@ApiOperation(value = "Login", response = String.class)
	public String login(@RequestBody UserLogin userLoginRequest) throws InterruptedException, ExecutionException {
		
		User user = db.getFirebase().collection("User")
								.document(userLoginRequest.getUsername())
								.get().get().toObject(User.class);
		
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, userLoginRequest.getUsername() + " not exists");
		}
		
		if (!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password !!");
		}
		List<String> permissions = null;
		String a[] = {"GET_ROLE"};
		permissions = Arrays.asList(a);
		// if (user.getRoles() != null) {
		// 	List<List<String>> nestedPermissions = user.getRoles().stream().map(Role::getPermissions)
	    //             .collect(Collectors.toList());
			
		// 	permissions = UtilitiesMethod.nestedListToArr(nestedPermissions, String.class);
		// }
 		Algorithm algorithm = Algorithm.HMAC256(SecurityTokenConfig.TOKEN_SECRET);
		String token = JWT.create().withJWTId(user.getId().toString()).withIssuer(SecurityTokenConfig.TOKEN_ISSUER)
				.withAudience(userLoginRequest.getUsername(), AuthorityCryptor.encodeToHex(permissions))
				.sign(algorithm);
		return token;
	}
}
