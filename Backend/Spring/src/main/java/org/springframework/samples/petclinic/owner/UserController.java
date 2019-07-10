/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.UsesJava7;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
class UserController {

	@Autowired
	UserRepository UserRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/user/new") 
	public Users saveUser(@RequestBody Users user) {
		List<Users> results = UserRepository.findAll();
		boolean userExists = false;
		// check all previous users, if same user name this entry is not saved
		for (Users a : results) {
			if (user.getUser_name() == a.getUser_name()) {
				userExists = true;
			}
		}
		if (!userExists) {
			UserRepository.save(user);
			return user; // gets the last result which you added
		} else {
			Users b = new Users();
			return b;
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/user")
	public Boolean logIn(@RequestBody Users b) {
		List<Users> all = UserRepository.findAll();
		for (Users a : all) {
			if (a.getUser_name()== b.getUser_name()&& 
					a.getPassword() == b.getPassword()) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/user/leaderboard")
	public List<leaderBoard> leaderBoard() {
		List<Users> users = UserRepository.findAll();
		List<leaderBoard> results = new ArrayList<>();
		Collections.sort(users);
		if (users.size() >= 10) {
			for (int i = 0; i < 10; i++) {
				leaderBoard a = new leaderBoard();
				a.setScore(users.get(i).getScore());
				a.setRank(users.get(i).getRank());
				a.setUserName(users.get(i).getUser_name());
				results.add(a);
			}
		} else {
			for (Users b: users) {
				leaderBoard c = new leaderBoard();
				c.setScore(b.getScore());
				c.setRank(b.getRank());
				c.setUserName(b.getUser_name());
				results.add(c);
			}
		}
		return results;
	}

}
