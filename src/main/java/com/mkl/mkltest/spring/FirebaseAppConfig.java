package com.mkl.mkltest.spring;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;
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
		// 		.setCredentials((GoogleCredentials.fromStream(inputStream))).build();
		// if(FirebaseApp.getApps().size()==0)
		// FirebaseApp.initializeApp(options);
		InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./test-mkl-280615-firebase-adminsdk-l02h7-895a64421a.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
		.setCredentials(GoogleCredentials.fromStream(serviceAccount))
		.setDatabaseUrl("https://test-mkl-280615.firebaseio.com").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
		
		return FirebaseApp.getInstance();
	}
	
	public Firestore getFirestore() {
		return FirestoreClient.getFirestore();
	}

	@Bean
	public void listenFireStore() {
		getFirestore().collection("User")
		.whereEqualTo("userName", "lebang1")
		.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirestoreException e) {
                if (e != null) {
                    // Log.w(TAG, "Listen failed.", e);
                    return;
				}
				for (DocumentChange doc : snapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
							System.out.println("add" + doc.getDocument().get("userName") + ":" + doc.getDocument().get("fullName"));
                            break;
                        case MODIFIED:
							System.out.println("update " + doc.getDocument().get("userName") + ":" + doc.getDocument().get("fullName"));
                            break;
                        case REMOVED:
							System.out.println("delete" + doc.getDocument().get("userName") + ":" + doc.getDocument().get("fullName"));
                            break;
                    }
                }
                // for (QueryDocumentSnapshot doc : snapshots) {
                //     if (doc.get("bankAccountNumber") != null) {
                //         System.out.println(doc.get("userName") + ":" + doc.get("fullName"));
                //     }
                // }
                // Log.d(TAG, "Current cites in CA: " + cities);
            }
        });
	}
}
