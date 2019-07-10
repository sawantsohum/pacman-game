package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//store information, getting information from the table methods: username, myrank, score, 
	//and also delete information from the table
	//update the score for the player
	//do queries, find the score of the player, a single player, and a method to find one for multiple player,
	//find the order of the list, first highest score, and second highest score.
	
	//rank and score in users doesn't make sense.
	

@RestController
class leaderBoardController {
	@Autowired
	leaderBoardRepository leaderboardrepository;
	
	//This is a leaderboard repo
	private final Logger logger = LoggerFactory.getLogger(leaderBoardController.class);
	

	/**
	 * Method that will adds leaderboard to the leaderboard table 
	 * which is located in the database.
	 * @param leaderboard
	 * @return
	 */
	@PostMapping(path = "/leaderboard")
	public String username(@RequestBody leaderBoard user_name) {
		logger.info("Entered into Controller");
		leaderboardrepository.save(user_name);
		logger.info("Saved:" + user_name);
		return "successful";
		//error with id
	}
	/**
	 * This is when it will get all users that have 
	 * become the users on the database.
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.GET, path = "/allUsers")
		public List<leaderBoard> getAllUsers(){
			 logger.info("Entered into Controller Layer");
		        List<leaderBoard> results = leaderboardrepository.findAll();
		        logger.info("Number of Records Fetched:" + results.size());
		        
		        return results;
		        //this works
		        
		        
		}
		/**
		 * Method that will adds myrank to the myrank table 
		 * which is located in the database.
		 * @param myrank
		 * @return
		 */
		
		@RequestMapping(method = RequestMethod.POST, path = "/myrank")
		public String myrank(@RequestBody leaderBoard myrank) {
			logger.info("Entered into Controller");
			leaderboardrepository.save(myrank);
			logger.info("Saved:" + myrank);
			return "successful";
			//this throws error
		/**
		 * This is when it will get all ranks that have 
		 * become the ranks on the database.
		 * @return
		 */
		}
		@RequestMapping(method = RequestMethod.GET, path = "/allmyrank")
		public List<leaderBoard> myrank(){
			 logger.info("Entered into Controller Layer");
		        List<leaderBoard> results = leaderboardrepository.findAll();
		       // leaderboardrepository.findById(id)
		        logger.info("Number of Records Fetched:" + results.size());
		        
		        return results;
		        //this one works
	}
		
		/**
		 * Method that will adds score to the score table 
		 * which is located in the database.
		 * @param score
		 * @return
		 */
		@RequestMapping(method = RequestMethod.POST, path = "/score")
		public String score(@RequestBody leaderBoard score) {
			logger.info("Entered into Controller");
			leaderboardrepository.save(score);
			logger.info("Saved:" + score);
			return "successful";
			//change to post request
		/**
		 * This is when it will get all scores that have 
		 * become the scores on the database.
		 * @return
		 */
		
		}
		@RequestMapping(method = RequestMethod.GET, path = "/allscore")
		public List<leaderBoard> score(){
			 logger.info("Entered into Controller Layer");
		        List<leaderBoard> results = leaderboardrepository.findAll();
		        logger.info("Number of Records Fetched:" + results.size());
		        
		        return results;

		}
		
		
}



	
	
	

