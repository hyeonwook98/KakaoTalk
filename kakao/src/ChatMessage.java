import java.io.Serializable;

public class ChatMessage implements Serializable {
	// �޽��� Ÿ�� ����
	// 1���� �޽��� ���� �ʵ�� 3���� String �ʵ�.
	//
	public enum MsgType{NO_ACT,CREATION,LOGIN,LOGOUT,USER_MSG,SERVER_MSG};
	
	private MsgType type;
	private String sender;
	private String receiver;
	private String contents;
	
	public ChatMessage() {
		this(MsgType.NO_ACT,"","","");
	}
	public ChatMessage(MsgType t, String sID, String rID, String mesg) {
		type = t;
		sender = sID;
		receiver = rID;
		contents = mesg;
	}
	public void setType (MsgType t) {
		type = t;
	}
	public MsgType getType() {
		return type;
	}

	public void setSender (String id) {
		sender = id;
	}
	public String getSender() {
		return sender;
	}
	
	public void setReceiver (String id) {
		receiver = id;
	}
	public String getReceiver() {
		return receiver;
	}
	
	public void setContents (String mesg) {
		contents = mesg;
	}
	public String getContents() {
		return contents;
	}

}
