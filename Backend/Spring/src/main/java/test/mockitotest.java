package test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.owner.MockUserFunctions;
import org.springframework.samples.petclinic.owner.UserFunctions;
import org.springframework.samples.petclinic.owner.Users;
import org.springframework.samples.petclinic.owner.leaderBoard;



public class mockitotest {

	

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getScoreByIdTest() {
		
		MockUserFunctions test = mock(MockUserFunctions.class);
		UserFunctions testGetWinner = new UserFunctions();
		ArrayList<Users> fetchresponse = new ArrayList<>();
		Users user1 = new Users();
		user1.setScore(2);
		user1.setUser_name("User1");
		fetchresponse.add(user1);
		
		Users user2 = new Users();
		user2.setScore(5);
		user2.setUser_name("User2");
		fetchresponse.add(user2);

		
		Users user3 = new Users();
		user3.setScore(7);
		user3.setUser_name("User3");
		fetchresponse.add(user3);

	
		Users user4 = new Users();
		user4.setScore(9);
		user4.setUser_name("User4");
		fetchresponse.add(user4);

	
		Users user5 = new Users();
		user5.setScore(1);
		user5.setUser_name("User5");
		fetchresponse.add(user5);


		
		fetchresponse.add(new Users());
		when(test.getUsersInLobby()).thenReturn(fetchresponse);
		
	
		int winningScore  = testGetWinner.getWinner(test.getUsersInLobby()).getScore();
		assertEquals(winningScore,9);
		
		
		
		
	}
		//done
	
		
		@Test
		public void getRankByIdTest() {
			MockUserFunctions test = mock(MockUserFunctions.class);
			UserFunctions testGetRank = new UserFunctions();
			
			ArrayList<leaderBoard> fetchresponse = new ArrayList<>();
			leaderBoard rank1 = new leaderBoard();
			rank1.setRank(1);
			fetchresponse.add(rank1);
			
			leaderBoard rank2 = new leaderBoard();
			rank2.setRank(5);
			fetchresponse.add(rank2);

			leaderBoard rank3 = new leaderBoard();
			rank3.setRank(3);
			fetchresponse.add(rank3);
			

			leaderBoard rank4 = new leaderBoard();
			rank4.setRank(6);
			fetchresponse.add(rank4);

			leaderBoard rank5 = new leaderBoard();
			rank5.setRank(4);
			fetchresponse.add(rank5);
			
			


			
			fetchresponse.add(new leaderBoard());
			when(test.getleaderBoardsInLobby()).thenReturn(fetchresponse);
			
			int rank  = testGetRank.getRank(test.getleaderBoardsInLobby()).getRank();
			assertEquals(rank,6);
		
			
			
		}
		

		@Test
		public void getRankEmpty() {
			MockUserFunctions test = mock(MockUserFunctions.class);
			UserFunctions testGetRank = new UserFunctions();
			
			ArrayList<leaderBoard> fetchresponse = new ArrayList<>();
	
			

			when(test.getleaderBoardsInLobby()).thenReturn(fetchresponse);
			
			//String expectedResponse = "Leaderboard is empty";
			
			boolean isEmpty = testGetRank.getRank(test.getleaderBoardsInLobby()).getUserName().equals("Leaderboard is empty");
			assertEquals(isEmpty,true);
		
			//checks if the leaderboard is empty or not. 
			
			
		}
		
		

}