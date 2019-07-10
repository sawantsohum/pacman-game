package wschat;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
public class ChatMessageController {

	//add user to the chart and send message
	//payload is coming from the messager handler
	//im unsure about this
	@MessageMapping("/chat.registration") //we use the same url to client to server
	@SendTo("/messagetopic/public")
	public ChatMessager registration(@Payload ChatMessager chatmessages, SimpMessageHeaderAccessor headerAccessor) {
		//we
		headerAccessor.getSessionAttributes().put("username", chatmessages.getSender());
		return chatmessages; //not return ChatMessager should be return ChatMessager
		
		
	}
	@MessageMapping("/chat.send") //we use the same url to client to server
	@SendTo("/messagetopic/public") //same request address response from above
	public ChatMessager sendMessage(@Payload ChatMessager chatmessages) {
		return chatmessages;
	}
	
}
//done with server side and we need to add the client side