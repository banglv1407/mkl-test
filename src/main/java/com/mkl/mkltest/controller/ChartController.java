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
import com.mkl.mkltest.entity.BetLog;
import com.mkl.mkltest.entity.User;
import com.mkl.mkltest.spring.FirebaseAppConfig;
import com.mkl.mkltest.utility.CommonMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

public class ChartController {
    // @Autowired
    // private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    FirebaseAppConfig db;

    @PostMapping(value = "/bet")
    public BetLog bet(@RequestBody BetLog betReq) throws InterruptedException, ExecutionException {
        String username = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        DocumentReference userDf = db.getFirebase().document("User/" + username);
        User user = userDf.get().get().toObject(User.class);
        if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, username + " not exists");
		}
        BetLog betLog = new BetLog();
        betLog.setCreatedDate(System.currentTimeMillis());
        betLog.setBetDownAmount(betReq.getBetDownAmount());
        betLog.setBetUpAmout(betReq.getBetUpAmout());
        betLog.setChartId(betReq.getChartId());
        betLog.setId(betReq.getId());
        betLog.setUserId(user.getId());
        
        try {
            DocumentReference df = db.getFirebase().document("Transaction/BetLog/"+ betReq.getChartId() + "/" + username);
            df.set(betLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Double currentAmountUser = user.getCurrentAmount();
        Double totalBetAmount = betLog.getBetDownAmount() + betLog.getBetUpAmout();
        Double totalUserBet = user.getTotalAmountBet();
        user.setCurrentAmount(currentAmountUser - totalBetAmount);
        user.setTotalAmountBet(totalUserBet + totalBetAmount);
        userDf.set(user);
        return betLog;
    }

    // public static void main(String[] args) throws ParseException {
    //     Date date = new Date() ;
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
    //     dateFormat.format(date);
    //     System.out.println(dateFormat.format(date));
        
    //     if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("12:07")))
    //     {
    //         System.out.println("Current time is greater than 12.07");
    //     }else{
    //         System.out.println("Current time is less than 12.07");
    //     }
    // }
}