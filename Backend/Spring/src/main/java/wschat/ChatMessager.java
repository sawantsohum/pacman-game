package wschat;

public class ChatMessager {

	
	private String contentinformation;
	private String sender;
	private ChatType type; //
	
	//unsure about this working
	
	public enum ChatType{  //specifications 
		CHAT, LEFT, JOIN
	}



	public String getContentinformation() {
		return contentinformation;
	}



	public void setContentinformation(String contentinformation) {
		this.contentinformation = contentinformation;
	}



	public String getSender() {
		return sender;
	}



	public void setSender(String sender) {
		this.sender = sender;
	}



	public ChatType getType() {
		return type;
	}



	public void setType(ChatType type) {
		this.type = type;
	}
	
}
