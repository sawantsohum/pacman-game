package org.springframework.samples.petclinic.websocket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.aspectj.weaver.ast.Not;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table (name = "chats")
public class Chat {
	/**
	 * Value representing the specific column for the chat in the database.
	 */
	@Id
	@Column(name = "id")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private int id;
	
	/**
	 * User is sending in the database.
	 */
	@Column(name = "Messages")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private String message;
	/**
	 * User which that is sending the message.
	 */
	@Column(name = "Sender")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private String sender;
	
	/**
	 * User who is receiving the message and sent from the database.
	 */
	@Column(name = "Recipient")
	@NotFound(action = NotFoundAction.EXCEPTION)
	private String receiver;
	
	/**
	 *  get the message that was sent from the database.
	 * @return
	 */
	public String getMessages() {
		return message;
	}
	/**
	 *  will return user which sent a message.
	 * @return
	 */
	public String getSender() {
		return sender;
	}
	/**
	 *  return the user name of the intended receiver of a message which  
	 * is currently stored on the database.
	 * @return
	 */
	public String getRecipient() {
		return receiver;
	}
	/**
	 * add the String message in the database.
	 * @param message
	 */
	public void addMessage(String message) {
		this.message = message;
	}
	/**
	 *  add the user who sends who is sending the message and 
	 *  then tell it to put it in the database.
	 * @param sender
	 */
	public void addSender(String sender) {
		this.sender = sender;
	}
	/**
	 * Add who is the user and then who will 
	 * receive the message into the database.
	 * @param receiver
	 */
	public void addRecipient(String receiver) {
		this.receiver = receiver;
	}
}