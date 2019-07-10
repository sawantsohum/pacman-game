package wschat;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class WSconfig implements WebSocketMessageBrokerConfigurer{
//im confused if this is working.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registration) {
		//WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registration);
		//to access the url we must do this it is a fixed url
		
		////socket  is when the web socket is either disconnection, then the connection
		//between client and server will still continue.
		registration.addEndpoint("/addStomp").withSockJS(); 
		
		
	}
	
	//now we enable the message broker 
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registration) {
		registration.enableSimpleBroker("/messagetopic");
		registration.setApplicationDestinationPrefixes("/pacmanapp");
		
		//WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registration);
	}

	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		// TODO Auto-generated method stub
		return false;
	}
}
