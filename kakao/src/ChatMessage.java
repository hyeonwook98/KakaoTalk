import java.io.Serializable;

public class ChatMessage implements Serializable {
	// 메시지 타입 정의
	// 1개의 메시지 종류 필드와 3개의 String 필드.
	//
	public enum MsgType{NO_ACT,CREATION,CREATION_FAILURE,CREATION_SUCCESS,CONFIRM,CONFIRM_FAILURE,CHANGE,LOGIN_TRY,LOGIN,LOGIN_SUCCESS,LOGIN_FAILURE,ADD,FRIEND_EXIST,ADD_FAILURE,FRIEND_LIST,USER_INFO,LOGOUT,USERMSG_RECORD,FRIENDMSG_RECORD,SEND_MSG,YOURSELF_MSG,USER_MSG,WITHFRIEND_MSG,SERVER_MSG};
	
	private MsgType type;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String gender;
	private int sender;
	private int receiver;
	private String contents;
	
	public ChatMessage() {
		this(MsgType.NO_ACT,"","","","","",0,0,"");
	}
	public ChatMessage(MsgType t, String n, String e, String pw ,String p,String g, int sID, int rID, String mesg) {
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

	public void setSender (int id) {
		sender = id;
	}
	public int getSender() {
		return sender;
	}
	
	public void setReceiver (int id) {
		receiver = id;
	}
	public int getReceiver() {
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
