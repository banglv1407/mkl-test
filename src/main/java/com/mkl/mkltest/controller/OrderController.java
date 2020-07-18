package com.mkl.mkltest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.mkl.mkltest.controller.api.BuyOrderRequest;
import com.mkl.mkltest.entity.BuyOrder;
import com.mkl.mkltest.entity.User;
import com.mkl.mkltest.spring.FirebaseAppConfig;
import com.mkl.mkltest.utility.CommonMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    FirebaseAppConfig db;

    @PostMapping(value = "/buy")
    public String p1Matching(@RequestBody BuyOrderRequest buyOrderRequest) throws InterruptedException, ExecutionException {
        String username = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        // CollectionReference cr = db.getFirebase().collection("Transaction");
        // ApiFuture<QuerySnapshot> querySnapShot = cr.get();
        // BuyOrder buyOrder = db.getFirebase().document("Transaction/BuyOrder/P1" + user.getUserName()).get().get().toObject(BuyOrder.class);
        User user = db.getFirebase().document("User/" + username).get().get().toObject(User.class);
        BuyOrder buyOrder = new BuyOrder();
        buyOrder.setSession(buyOrderRequest.getSession());
        buyOrder.setUser(user);
        buyOrder.setCreateDate(System.currentTimeMillis());
        buyOrder.setCreateDateInt(CommonMethod.getSimpleDayToInt(System.currentTimeMillis()));
        buyOrder.setId(user.getUserName()+"_"+ buyOrder.getCreateDateInt());
        try {
            // db.getFirebase().document("User/" + user.getUserName()).set(user);
            DocumentReference df = db.getFirebase().document("Transaction/BuyOrder/"+ buyOrderRequest.getSession() + "/" + buyOrder.getUser().getUserName());
            df.set(buyOrder);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

    public static void main(String[] args) throws ParseException {
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(date);
        System.out.println(dateFormat.format(date));
        
        if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("12:07")))
        {
            System.out.println("Current time is greater than 12.07");
        }else{
            System.out.println("Current time is less than 12.07");
        }
    }
}