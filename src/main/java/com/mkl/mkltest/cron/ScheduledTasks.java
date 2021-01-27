package com.mkl.mkltest.cron;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.DocumentReference;
import com.mkl.mkltest.entity.Chart;
import com.mkl.mkltest.entity.Summary;
import com.mkl.mkltest.spring.FirebaseAppConfig;
import com.mkl.mkltest.utility.CommonMethod;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.database.annotations.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    FirebaseAppConfig db;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    public void scheduleTaskWithFixedRate() {

    }

    public void scheduleTaskWithFixedDelay() {
    }
    @Scheduled(cron = "1 * * * * ?")
    public void listenTransaction() {
        Long cid = System.currentTimeMillis()/60000 * 60000 + 60000;
		db.getFirebase().collection("System/"+ cid + "/BetLog")
		// .whereEqualTo("chartId", cid)
				.addSnapshotListener(new EventListener<QuerySnapshot>() {

					@Override
					public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirestoreException e) {
						if (e != null) {
							// Log.w(TAG, "Listen failed.", e);
							return;
                        }
                        DocumentReference dfSummary = db.getFirebase().document("System/"+cid+"/Summary/AllSystem");
						
						try {
                            Summary summary = dfSummary.get().get().toObject(Summary.class);
                            Double totalBetDown = summary.getTotalBetDownAmount();
                            Double totalBetUp = summary.getTotalBetUpAmount();
                            for (DocumentChange doc : snapshots.getDocumentChanges()) {
                                System.out.println("current bet " + doc.getDocument().get("betDownAmount") + ":" + doc.getDocument().get("betUpAmout"));
                                summary.setTotalBetDownAmount(totalBetDown + Double.parseDouble(doc.getDocument().get("betDownAmount").toString()));
                                summary.setTotalBetUpAmount(totalBetUp + Double.parseDouble(doc.getDocument().get("betUpAmout").toString()));
                                System.out.println("total bet " + summary.getTotalBetDownAmount() + ":" + summary.getTotalBetUpAmount());
                            }
                            dfSummary.set(summary);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ExecutionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

            }
        });
    }

    // @Scheduled(cron = "1 * 0 ? * * *")
    @Scheduled(cron = "0 * * * * ?")
    public void autoGenChart() throws IOException {
        final String urlReq = "https://api1.binance.com/api/v3/klines?symbol=BTCUSDT&interval=1m&limit=1";
        Long cid = System.currentTimeMillis()/60000 * 60000 + 60000;
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");

        Response responseBnb = Jsoup.connect(urlReq)
        .headers(headerMap)
        .method(Method.GET)
        .followRedirects(true)
        .ignoreContentType(true)
        .execute();
        String subString =responseBnb.body().substring(1, responseBnb.body().length()-1);
        String [] ress = subString.split("\\[*]");
        
        String responseTodo = ress[0].substring(1, ress[0].length());
        // String responseMustReach = ress[1].substring(2, ress[1].length());
        System.out.println(responseTodo);
        // System.out.println(responseMustReach);
    
        String [] responseTodoArray = responseTodo.split(",");
        // String [] responseMustReachArray = responseMustReach.split(",");
        Long openTime = Long.parseLong(responseTodoArray[0]);
        Double openPrice = Double.parseDouble(responseTodoArray[1].substring(1, responseTodoArray[1].length() - 1));
        Double highPrice = Double.parseDouble(responseTodoArray[2].substring(1, responseTodoArray[2].length() - 1));
        Double lowPrice = Double.parseDouble(responseTodoArray[3].substring(1, responseTodoArray[3].length() - 1));
        Double closePrice = Double.parseDouble(responseTodoArray[4].substring(1, responseTodoArray[4].length() - 1));
        // System.out.println("Create chart: ");
        Chart chart = new Chart();
        chart.setId(cid);
        chart.setOpenTime(openTime);
        chart.setCloseTime(cid + 60000);
        chart.setOpenPrice(openPrice);
        chart.setClosePrice(closePrice);
        chart.setHighPrice(highPrice);
        chart.setLowPrice(lowPrice);
        chart.setChartType(1);
        // responseTodoObject.volume = Double.parseDouble(responseTodoArray[5].substring(1, responseTodoArray[5].length() - 1));
        // responseTodoObject.closeTime = Long.parseLong(responseTodoArray[6]);
        // responseTodoObject.quoteAssetVolume = Double.parseDouble(responseTodoArray[7].substring(1, responseTodoArray[7].length() - 1));
        // responseTodoObject.numberOfTrades = Long.parseLong(responseTodoArray[8]);
        // responseTodoObject.buyBaseAssetVolume = Double.parseDouble(responseTodoArray[9].substring(1, responseTodoArray[9].length() - 1));
        // responseTodoObject.buyQuoteAssetVolumer = Double.parseDouble(responseTodoArray[10].substring(1, responseTodoArray[10].length() - 1));

        Summary summary = new Summary();
        summary.setId(cid);
        summary.setChartId(cid);
        try {
            String typeChart = chart.getChartType() == 1 ? "Current" : "Balance";
            DocumentReference df = db.getFirebase().document("System/"+ cid + "/Chart/" + typeChart);
            df.set(chart);
            DocumentReference autoGenSummary = db.getFirebase().document("System/"+cid+"/Summary/AllSystem");
            autoGenSummary.set(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
