package org.springframework.samples.petclinic.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import antlr.collections.List;

@RestController
public class ChatController {
	@Autowired
	ChatRespository chatRepository;
	
	private final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	/**
	 * This is a method which will return 
	 * all of the chat messages from the server.
	 * This occurs when the first user joins the server.
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/chat")
	public java.util.List<Chat> getAllHistory()
	{
        logger.info("Entered into Controller Layer");
      //return all which were from the chat history
      return chatRepository.findAll();
	}
	/**
	 * This is used to  save a chat message to the database. 
	 * 
	 * @param chat
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/chat/adder")
	public Chat saveChat(@RequestBody Chat chat) {
		//this is unfinished. I  
		return chat;
		
	}

}
