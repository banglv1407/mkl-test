package com.mkl.mkltest.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

	@GetMapping(value = "/")
	public RedirectView  index() throws InterruptedException, ExecutionException {

		return new RedirectView("/swagger-ui.html");
	}
	

}
