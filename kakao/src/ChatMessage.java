import java.io.Serializable;

public class ChatMessage implements Serializable {
	// 메시지 타입 정의
	// 1개의 메시지 종류 필드와 3개의 String 필드.
	//
	public enum MsgType{NO_ACT,CREATION,CREATION_FAILURE,CREATION_SUCCESS,CONFIRM,CONFIRM_FAILURE,CHANGE,LOGIN_TRY,LOGIN,LOGIN_SUCCESS,LOGIN_FAILURE,ADD,FRIEND_EXIST,ADD_FAILURE,FRIEND_LIST,END,LOGOUT,USER_MSG,SERVER_MSG};
	
	private MsgType type;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String gender;
	private String sender;
	private String receiver;
	private String contents;
	
	public ChatMessage() {
		this(MsgType.NO_ACT,"","","","","","","","");
	}
	public ChatMessage(MsgType t, String n, String e, String pw ,String p,String g, String sID, String rID, String mesg) {
		type = t;
		name = n;
		email = e;
		password = pw;
		phone = p;
		gender = g;
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
	public void setName (String n) {
		name = n;
	}
	public String getName() {
		return name;
	}
	public void setEmail (String e) {
	    email = e;
	}
	public String getEmail() {
		return email;
	}
	public void setPw (String pw) {
		password = pw;
	}
	public String getPw() {
		return password;
	}
	
	public void setPhone (String p) {
	    phone = p;
	}
	public String getPhone() {
		return phone;
	}
	public void setGender (String g) {
	    gender = g;
	}
	public String getGender() {
		return gender;
	}

}
