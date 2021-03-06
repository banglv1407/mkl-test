package com.mkl.mkltest.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mkl.mkltest.entity.Summary;
import com.mkl.mkltest.utility.CommonMethod;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

@Configuration
public class FirebaseAppConfig {
	@Bean
	public FirebaseApp getFirebaseApp() throws IOException {

		// FirebaseOptions options = new FirebaseOptions.Builder()
		// .setCredentials((GoogleCredentials.fromStream(inputStream))).build();
		// if(FirebaseApp.getApps().size()==0)
		// FirebaseApp.initializeApp(options);
		InputStream serviceAccount = this.getClass().getClassLoader()
				.getResourceAsStream("test-mkl-280615-firebase-adminsdk-l02h7-895a64421a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://test-mkl-280615.firebaseio.com").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}

		return FirebaseApp.getInstance();
	}

	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();
	}

}
