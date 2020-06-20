package com.mkl.mkltest.streamer;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.cloud.firestore.Firestore;

import com.mkl.mkltest.entity.User;

@Component
@Streamer("com.mkl.mkltest.entity.User")
public class UserStreamer {
//	@Autowired
//	UserRepository userRepository; 
	@Autowired
	Firestore firestore;
	public void stream(String user_id)
	{
		System.out.println("User stream method is called id : "+user_id);
		try {
			if(firestore.document(user_id).get().get().exists())
			{
				System.out.println("found document with id : "+user_id);
				
				//TODO Insert or update to mysql
				//TODO Insert or update to bigquery
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
