package com.example.demo;


import java.util.Arrays;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
public class NewController {

	@RequestMapping("/topics")
	public @ResponseBody List<newtopic> getAllTopics() {
		return Arrays.asList(new newtopic("spring,", "Framework", "Framework pacman description", "name"),
				new newtopic("spring,", "Framework", "Framework pacman description", "name"),
				new newtopic("spring," ,"Framework", "Framework pacman description", "name"),
				new newtopic("spring,", "Framework", "Framework pacman description", "name")
				
				
				);
	}
	
}
