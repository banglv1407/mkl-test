package com.mkl.mkltest.spring;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseAppConfig {
	@Bean
	public FirebaseApp getFirebaseApp() throws IOException {

		// InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-mkl-280615-firebase-adminsdk-l02h7-4ae8dc5fb2.json");
		// FirebaseOptions options = new FirebaseOptions.Builder()
		// 		.setCredentials((GoogleCredentials.fromStream(inputStream))).build();
		// if(FirebaseApp.getApps().size()==0)
		// FirebaseApp.initializeApp(options);
		
		// return FirebaseApp.getInstance();

		InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("test-mkl-280615-firebase-adminsdk-l02h7-54fd4cfa83.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://test-mkl-280615.firebaseio.com").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		return FirebaseApp.getInstance();
	}
	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();//firebase
	}
}
