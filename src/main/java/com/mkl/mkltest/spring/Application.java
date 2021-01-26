package com.mkl.mkltest.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
@ComponentScan("com.mkl.mkltest.controller")
@ComponentScan("com.mkl.mkltest.spring")
@ComponentScan("com.mkl.mkltest.streamer")
@ComponentScan("com.mkl.mkltest.cron")
@EntityScan("com.mkl.mkltest.entity")
@ComponentScan("com.fsc")
@EnableScheduling
public class Application {

	public static void main(String[] args) {

		ApplicationContext applicationContext = 
				SpringApplication.run(Application.class, args);
		
	}

	@Bean
	public SpringTemplateEngine templateEngine(ApplicationContext ctx)
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(ctx);
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateEngine.setTemplateResolver(templateResolver);
		templateEngine.addDialect(new LayoutDialect());
		
		return templateEngine;
	}
}
