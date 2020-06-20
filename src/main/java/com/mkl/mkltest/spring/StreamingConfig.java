package com.mkl.mkltest.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.mkl.mkltest.streamer.Streamer;

@Configuration
public class StreamingConfig {

	private static final Logger log = Logger.getLogger(StreamingConfig.class.getName());

	private static final String SUBSCRIPTION_NAME = "spring-data-stream";

	@Autowired
	private ApplicationContext ctx;

	private Map<String, Object> streamingBeanMap;

	@Bean
	public MessageChannel pubsubInputChannel() {
		return new DirectChannel();
	}

	@PostConstruct
	public  void init() {
		streamingBeanMap = new HashMap<String, Object>();
		for (String name : ctx.getBeanNamesForAnnotation(Streamer.class)) {
			Object bean = ctx.getBean(name);
			String value = bean.getClass().getAnnotation(Streamer.class).value();
			streamingBeanMap.put(value, bean);
			
		}
		log.info(streamingBeanMap.size()+" streamer added ...");
		
	}

	
}