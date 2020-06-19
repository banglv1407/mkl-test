package com.mkl.mkltest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.mkl.mkltest.controller.request.UserLoginRequest;
import com.mkl.mkltest.controller.request.userRegisterRequest.UserRegisterRequest;
import com.mkl.mkltest.model.User;
import com.mkl.mkltest.service.FirebaseInitializer;
import com.mkl.mkltest.utility.AuthorityCryptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    // @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    FirebaseInitializer db;
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
        
        User user = cr.document(userReq.getId()).get().get().toObject(User.class);
		return user;
    }
	@PostMapping(value = "/register")
	public String register(@RequestBody UserRegisterRequest userRegisterRequest) throws InterruptedException, ExecutionException {
		// User user = db.getFirebase().document("Users/").get().get().toObject(User.class);
		
		// if (user != null) {
		// 	throw new ResponseStatusException(HttpStatus.CONFLICT, userRegisterRequest.getUserName() + " already exists ");
		// }
        if (userRegisterRequest.getUserName().length()<0 &&  userRegisterRequest.getUserName().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UserName null");
        }
		User user = new User();
		user.setPhone(userRegisterRequest.getPhone());
		user.setBirthYear(userRegisterRequest.getBirthYear());
        user.setId(userRegisterRequest.getUserName()+System.currentTimeMillis()); // ?? make sure id and phone is same 
        user.setUserName(userRegisterRequest.getUserName());
        user.setFullName(userRegisterRequest.getFullName());
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());
        user.setPassword(hashedPassword);
		db.getFirebase().document("User/" + user.getUserName()).set(user); // asynchronous => it may be fail to insert 
		return "OK";
	}
    @PostMapping(value = "/login")
	public String login(@RequestBody UserLoginRequest userLoginRequest) throws InterruptedException, ExecutionException {
		
		User user = db.getFirebase().document("User/" + userLoginRequest.getUserName()).get().get().toObject(User.class);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Wrong password !!");
		}
	
		Algorithm algorithm = Algorithm.HMAC256("AV-ACCESS-KEY");
		String token = JWT.create().withJWTId(user.getId().toString()).withIssuer("avissomethingveryseret")
				.withAudience(userLoginRequest.getUserName())//, AuthorityCryptor.encodeToHex(permissions)
				.sign(algorithm);
		
		return token;
	}
}