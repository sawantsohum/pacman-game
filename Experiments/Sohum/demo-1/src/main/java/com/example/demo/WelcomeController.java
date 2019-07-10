package com.example.demo;

import com.example.demo.Welcome;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
	private static final String msg = "Hello %s!";
	ArrayList<String> a = new ArrayList<String>();
	@GetMapping("/add/user")
	@ResponseBody
	public Welcome welcomeUser(@RequestParam(name = "name") String name) {
		a.add(String.format(name));
		//return new Welcome(String.format(msg, name));
		return new Welcome(a.toString());
	}
}
