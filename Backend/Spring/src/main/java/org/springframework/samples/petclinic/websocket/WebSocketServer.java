package websocket;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.samples.petclinic.owner.UserRepository;
import org.springframework.samples.petclinic.owner.Users;
import org.springframework.stereotype.Component;

import antlr.collections.List;

@ServerEndpoint("/websocket/game")
@Component
public class WebSocketServer {
	/**
	 * Value that keeps track of the current sessions that the user is in
	 */
	private Session session;
	/**
	 * Hash map that represents the
	 */
	private static Map<Session, String> sessionUserMap = new HashMap();
	/**
	 * Hash map that represents all of the usernames in a session.
	 */
	private int clientCount = 0;
	public static Map<String, Session> usernameSessionMap = new HashMap();
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	/**
	 * Array List that stores the history for the chat
	 */
	protected ArrayList<String> chatHistory = new ArrayList<String>();
	/**
	 * String that stores who sent the message
	 */
	private String sender;
	/**
	 * String that stores who the message is intended to.
	 */
	private String recipient;

	private ArrayList<String> userList;

	//game map 40x22

	private static String[][] map = new String[40][22];

	//x location of of wall

	private static int wallX = 0;

	private static int wallY = 0;

	//width of wall
	private static int width = 3;

	private static int height = 3;

	//list of walls
	private static ArrayList<String> walls = new ArrayList<String>();
	/**
	 * Method that will add a account to the serveer when they request this
	 * method
	 * 
	 * @param session
	 * @param username
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		// keeps track of the name of user (this is important when pushing the
		// message to the server)
		// adds user to the sessions (one is of users and one is of usernames)

		// first client connection contains size x by y +username
		// username_numblockshigh_numbblockswide
		// "username_screen numblockshigh numblockswide" where username is the
		// actual username of the person

		// if 0 clients are connected generate walls and sent the correct string
		// to the new user
		// if

		//if # of connected people is 0
		//generate walls
		//add user to map
		//broadcast walls
		//icrement client count
		if (clientCount == 0) {
			walls.add(generateWall());
			walls.add(generateWall());
			logger.info("Entered into Open");
			sessionUserMap.put(session, username);
			usernameSessionMap.put(username, session);
			broadcast(walls.get(0));
			broadcast(walls.get(1));
			broadcast(generateBead());
			clientCount++;


			//every 60 seconds we broadcast done signaling game time has finished and front end closes the game
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					try {
						broadcast("done");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} //give it a task to run. wait for event to occur and then run it when it comes

			};
			timer.schedule(task, 0, 60000);
		} 
		//if there is already existing client you send them the walls and dot
		else {
			logger.info("Entered into Open");
			sessionUserMap.put(session, username);
			usernameSessionMap.put(username, session);
			sendMessageToAPracticularUser(username, walls.get(0));
			sendMessageToAPracticularUser(username, walls.get(1));
			broadcast(generateBead());
			clientCount++;
		}

	}

	/**
	 * Sends a message to a particular user
	 *
	 * @param Message
	 * @param user
	 * 
	 */
	// sends message to a particular user
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public static void sendToUser(String Message, String user) throws Exception {
		sessionUserMap.forEach((session, username) -> {
			synchronized (session) {
				if (username.equals(user)) {
					try {
						session.getBasicRemote().sendText(Message);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * Returns all users in a arraylist connected to the server
	 *
	 * @param Message
	 * @param user
	 * @return returnUsers
	 */
	// get list of all connected users
	@MessageMapping("/chat/users")
	@SendTo("/topic/users")
	public static ArrayList<String> returnUsers(String Message, String user) throws Exception {
		ArrayList<String> a = new ArrayList<String>();
		sessionUserMap.forEach((session, username) -> {
			a.add(username);
		});
		return a;
	}

	/**
	 * Method that will send a message, either to everyone in the server, or
	 * just to one person, when method is called.
	 * 
	 * @param session
	 * @param message
	 * @throws IOException
	 */
	@OnMessage // invoked getting message from client
	public void onMessage(Session session, String message) throws IOException {
		if (message.equals("newdot")) { //this just makes the new dot
			broadcast(this.generateBead());
		} else {
			//"username x y"
			//split message to obtain x and y and then update local game map to show there has a movement
			broadcast(message);
			String[] e = message.split(" ");
			int x = Integer.parseInt(e[1]);
			int y = Integer.parseInt(e[2]);
			map[x][y] = "P";
		}
	}

	@OnClose
	/**
	 * Method that removes a account from the hashmap and removes them from the
	 * server when the leave.
	 * 
	 * @param session
	 * @throws IOException
	 */
	public void onClose(Session session) throws IOException { // connection is
																// closed for
		clientCount--;														// the client
		logger.info("Entered into close");
		// finds the user and removes them from the session
		String username = sessionUserMap.get(session);
		sessionUserMap.remove(session);
		usernameSessionMap.remove(username);
		
	}

	/**
	 * Method that is used for debugging when errors occur in the server.
	 * 
	 * @param session
	 * @param throwable
	 */
	@OnError // error or broken between client and server
	public void onError(Session session, Throwable throwable) {
		logger.info("Entered into error");
	}

	/**
	 * Method that helps assist the onMessage method when a account is intended
	 * to send a message to another particular account/user instead of everyone
	 * on the server.
	 * 
	 * @param username
	 * @param message
	 */
	public void sendMessageToAPracticularUser(String username, String message) {
		try {
			// attempts to send the String message to the user that has been
			// passed through
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException E) {
			// if for some reason it cannot, it will print an error message to
			// the user.
			logger.info("Exception " + E.getMessage().toString());
			E.printStackTrace();
		}
	}

	/**
	 * Method that is used to send the message to the other intended
	 * accounts/user.
	 * 
	 * @param message
	 * @throws IOException
	 */ // sends message to all clients which are connected to server co
	public static void broadcast(String message) throws IOException {
		sessionUserMap.forEach((session, username) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// return "Wall top left right bottom"
	// FE: "Wall 4 5 6 7"
	public static String generateWall() {
		String wall = "walls ";
		Random r = new Random();
		//picks random number between 5 & 35 for wall x
		wallX = r.nextInt((35 - 5) + 1) + 5;
		//picks random number between 17 & 5 
		wallY = r.nextInt((17 - 5) + 1) + 5;
		wall += wallX + " " + wallY + " " + width + " " + height;
		//puts x's to signify wall in map
		for (int i = wallX; i <= wallX + 4; i++) {
			for (int j = wallY; j <= wallY + 4; j++) {
				map[i][j] = "X";

			}
		}
		//returns string holding wall structure 
		//strings looks like "x y width height"
		return wall;
	}

	// Return "DOT x y"
	// FE "DOT 3 45"
	public static String generateBead() {
		Random r = new Random();
		int x = wallX;
		int y = wallY;
		// you keep generating dots until you find a location in which the space is open
		while (map[x][y] == "X" || map[x][y] == "P" || map[x][y] == "B") {
			x = r.nextInt((38 - 2) + 1) + 2;
			y = r.nextInt((18 - 2) + 1) + 2;
		}
		map[x][y] = "B";
		return ("dot " + x + " " + y);
	}

}