package org.springframework.samples.petclinic.owner;

import java.util.ArrayList;

public class UserFunctions {

	public Users getWinner(ArrayList <Users> players){
		int i; 
		int j = -100;
		Users highest = players.get(0);
		for(i= 0; i < players.size()-1; i++) {
			//find high score
			if(players.get(i).getScore() > j) {
				j = players.get(i).getScore();
				highest = players.get(i);
				
			}
			
		}
		
		return highest;
		
	}
public leaderBoard getRank(ArrayList <leaderBoard> rankplayers) {
		
		int i;
		int j = -1;
		if(rankplayers.size() == 0)
		{
			leaderBoard l = new leaderBoard();
			l.setUserName("Leaderboard is empty");
			return l;
		}
		leaderBoard rankhigh = rankplayers.get(0);
		//if the leaderboard is empty return 0
		
		
		
		for(i = 0; i < rankplayers.size()-1; i++) {
			if(rankplayers.get(i).getRank()>j) {
				//find highest rank
				j = rankplayers.get(i).getRank();
				rankhigh = rankplayers.get(i);
			}
		}
		return rankhigh;
		
	}
	
	
	
}
