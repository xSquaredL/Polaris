package com.polaris.rest;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

	@RequestMapping(value = "greeting", method = RequestMethod.GET)
	public String greeting() throws ServletException {
		return "hi";
	}
}
