package com.mkl.mkltest.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.mkl.mkltest.entity.BuyOrder;
import com.mkl.mkltest.entity.Goods;
import com.mkl.mkltest.spring.FirebaseAppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/match")
public class MatchingController {
    @Autowired
    FirebaseAppConfig db;

    @PostMapping(value = "/")
    public String p1Matching(@RequestParam String session ) throws InterruptedException, ExecutionException {
        try {
            List<BuyOrder> listBuyer = new ArrayList<BuyOrder>();
            List<Goods> listGoods = new ArrayList<Goods>();
            CollectionReference crBuy = db.getFirebase().collection("Transaction/BuyOrder/" + session);
            ApiFuture<QuerySnapshot> BuyerQuerySnapShot = crBuy.get();
            for(DocumentSnapshot doc : BuyerQuerySnapShot.get().getDocuments()){
                BuyOrder bo = doc.toObject(BuyOrder.class);
                listBuyer.add(bo);
            }
                CollectionReference crGood = db.getFirebase().collection("Transaction/Goods/" + session);
                ApiFuture<QuerySnapshot> GoodQuerySnapShot = crGood.get();
                for(DocumentSnapshot doc : GoodQuerySnapShot.get().getDocuments()){
                    Goods go = doc.toObject(Goods.class);
                    listGoods.add(go);
            }

            Collections.shuffle(listBuyer, new Random());
            Collections.shuffle(listGoods, new Random());
            Map<String, String> matchAccount = new HashMap<>();
            while (listBuyer.size() > 0 && listGoods.size() > 0) {
                String bankAccBuyer = listBuyer.get(0).getUser().getBankAccountNumber();
                String userBuyer = listBuyer.get(0).getUser().getUserName();
                if (isOnePerson(listBuyer, listGoods)) {
                    break;
                }
                for (int i = 0; i < listGoods.size(); i++) {
                    if (!bankAccBuyer.equalsIgnoreCase(listGoods.get(i).getOwnerBankAccount())) {
                        matchAccount.put(userBuyer, listGoods.get(i).getOwnerUserName());
                        listGoods.remove(i);
                        listBuyer.remove(0);
                        break;
                    }
                }

            }
            return ("Map buy - sell: " + new Gson().toJson(matchAccount));
        } catch (Exception e) {
            return e.getMessage();
        }
        
    }

    public boolean isOnePerson(List<BuyOrder> listBuyer, List<Goods> listGoods) {
        String baseBankAccBuyer = listBuyer.get(0).getUser().getBankAccountNumber();
        String baseBankAccSeller = listGoods.get(0).getOwnerBankAccount();
        boolean isOneBuyer = true;
        boolean isOneSeller = true;
        if (!baseBankAccBuyer.equalsIgnoreCase(baseBankAccSeller)) {
            return false;
        }
        for (BuyOrder us : listBuyer) {
            if (!us.getUser().getBankAccountNumber().equalsIgnoreCase(baseBankAccBuyer)) {
                isOneBuyer = false;
                break;
            }
        }
        for (Goods goods : listGoods) {
            if (!goods.getOwnerBankAccount().equalsIgnoreCase(baseBankAccSeller)) {
                isOneSeller = false;
                break;
            }
        }

        if (isOneBuyer && isOneSeller) {
            return true;
        } else {
            return false;
        }
    }
}