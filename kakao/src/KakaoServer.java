import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class KakaoServer {
	// 접속한 클라이언트의 사용자 이름과 출력 스트림을 해쉬 테이블에 보관
	// 나중에 특정 사용자에게 메시지를 보낼때 사용. 현재 접속해 있는 사용자의 전체 리스트를 구할때도 사용

	ArrayList<Integer> friend;
	
	//String[] array = null;
	DB db = new DB();// DB 연결을 위해 객체 생성
	Connection conn = db.conn(); // 자바와 DB 연결통로 객체인 Connection 객체 생성
	PreparedStatement pstmt;// 파라미터를 물음표로 받을 수 있음. 어떤 정보를 조건(물음표)에 대해 가져오려할 때 유용하게 사용 가능. 쿼리문을 좀더 편하게 사용하기위해
							// 사용.
	
	ResultSet rs; // 쿼리 전송의 결과 객체를 반환하기위함
	
	int usernumber; //로그인한 유저 고유번호
	int friendnumber; //유저의 친구 고유번호
	int count; //친구가 몇명인지

	public static void main(String[] args) {
		new KakaoServer().go();
	}

	private void go() {
		try {
			ServerSocket serverSock = new ServerSocket(6000); // 채팅을 위한 소켓 포트 5000 사용

			while (true) {
				Socket userSocket = serverSock.accept(); // 새로운 클라이언트 접속 대기

				// 클라이언트를 위한 입출력 스트림 및 스레드 생성
				Thread t1 = new Thread(new UserHandler(userSocket));
				t1.start();

				System.out.println("S : 클라이언트 연결 됨"); // 상태를 보기위한 출력 메시지
			}
		} catch (Exception ex) {
			System.out.println("S : 클라이언트  연결 중 이상발생"); // 상태를 보기위한 출력 메시지
			ex.printStackTrace();
		}
	}

	// 7.28오전 밑에 제외하고 go함수 쓰레드 두개 없애면 실행됨
	// 유저와 1:1 대응하는 메시지 수신 스레드
	private class UserHandler implements Runnable {
		Socket sock; // 클라이언트 연결용 소켓
		ObjectOutputStream writer; // 클라이언트로 송신하기 위한 스트림
		ObjectInputStream reader; // 클라이언트로 부터 수신하기 위한 스트림

		// 구성자. 클라이언트와의 소켓에서 읽기와 쓰기 스트림 만들어 냄
		// 스트림을 만들때 InputStream을 먼저 만들면 Hang함. 그래서 OutputStream먼저 만들었음.
		// 이것은 클라이언트에서 InpitStreams을 먼저 만들기 때문임 안그러면 데드락
		public UserHandler(Socket userSocket) {
			try {
				sock = userSocket;
				writer = new ObjectOutputStream(userSocket.getOutputStream());
				reader = new ObjectInputStream(userSocket.getInputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// 클라이언트에서 받은 메시지에 따라 상응하는 작업을 수행

		public void run() {
			// TODO Auto-generated method stub
			ChatMessage message;
			ChatMessage.MsgType type;
			try {
				while (true) {
					// 읽은 메시지의 종류에 따라 각각 할일이 정해져 있음
					message = (ChatMessage) reader.readObject(); // 클라이언트의 전송 메시지 받음
					type = message.getType();
					if (type == ChatMessage.MsgType.CREATION) {
						handleCreation(message.getName(), message.getEmail(), message.getPw(), message.getPhone(),
								message.getGender(), writer);
					} 
					else if (type == ChatMessage.MsgType.LOGIN_TRY) {
						handleLogin(message.getEmail(), message.getPw(), writer);
						
					} 
					else if (type == ChatMessage.MsgType.LOGOUT) {

					} 
					else if (type == ChatMessage.MsgType.CONFIRM) {
						handleConfirm(message.getEmail(), message.getPhone(), writer);
					} 
					else if(type == ChatMessage.MsgType.CHANGE) {
						handleChange(message.getEmail(),message.getPw(),message.getPhone());
					}
					else if(type == ChatMessage.MsgType.ADD) {
						handleAdd(message.getName(),message.getPhone(),writer);
					}
					else if (type == ChatMessage.MsgType.USER_MSG) {
						// handleMessage(message.getSender(), message.getReceiver(),
						// message.getContents());
					} 
					else if (type == ChatMessage.MsgType.NO_ACT) {
						// 무시해도 되는 메시지
						continue;
					} 
					else {
						// 정체가 확인되지 않는 이상한 메시지?
						throw new Exception("S : 클라이언트에서 알수 없는 메시지 도착했음");
					}
				}
			} catch (Exception ex) {
				System.out.println("S : 클라이언트 접속 종료"); // 연결된 클라이언트 종료되면 예외발생
				// 이를 이용해 스레드 종료시킴
			}
		}// close run
	}// close inner class

	// 유저 카카오계정 생성 정보받기
	private synchronized void handleCreation(String name, String email, String pw, String phone, String gender,
			ObjectOutputStream writer) {
		int a = 0; //a가 0이면 계정 생성완료, 1이면 계정 생성실패
		String sql = "insert into user(이름,이메일,비밀번호,전화번호,성별)values(?,?,?,?,?);";
		String sql2 = "select 이메일 from user where 이메일 like ?;";
		try {
			pstmt = conn.prepareStatement(sql2); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
			pstmt.setString(1, email);
			rs = pstmt.executeQuery(); // Statement를 넣어도되지만,앞에 넣었으므로 현재는 안넣음

			if (rs.next()) {// 다음 레코드가 있을때
				a = 1;
				System.out.println("이미 가입된 이메일입니다.");
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.CREATION_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
		if (a == 0) {
			try {
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.CREATION_SUCCESS, "", "", "", "", "", "", "", ""));
				pstmt = conn.prepareStatement(sql); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
				pstmt.setString(1, name); // 물음표가 있는 위치 순서로 번호가 지정됨. 물음표의 개수만큼 만들어줘야함.(개수 안맞추면 오류가 난다!)
				pstmt.setString(2, email);
				pstmt.setString(3, pw);
				pstmt.setString(4, phone);
				pstmt.setString(5, gender);

				// WORD는 String으로 받은 String의 cname, cphone, ctype, cnumber를 전달해서 세팅해서 실행하도록 해줌.
				// cname, cphone, ctype, cnumber은 해당되는 부분의 JTextField에서 적힌 정보를 받아와야함!(텍스트필드
				// 변수.getText(););

				int result = pstmt.executeUpdate(); // 쿼리 전송의 결과 객체를 반환하기위함. 0이면 오류, 1이면 제대로 실행된 것.
				// executeUpdate는 insert, update, delete 문에서 사용
				// executeQuery는 select문에서 사용.
				System.out.println("result = " + result);

				pstmt.close(); // 종료
				conn.close(); // 종료
			} catch (Exception ex) {
				System.out.println("S : 서버에서 송신 중 이상 발생");
				ex.printStackTrace();
			}
		}

	}

	// 로그인 정보 받기
	public void handleLogin(String email, String pw, ObjectOutputStream writer) {
		// TODO Auto-generated method stub
		String sql = "select 유저번호 from user where 이메일 like ? and 비밀번호 like ? ;";
		String sql2 = "select 친구번호 from user,friend where user.유저번호 like ? and friend.유저번호 like ?;";
		String sql3 = "select 이름,성별 from user where 유저번호 like ? ;";
		friend= new ArrayList<Integer>(); 
		
		int a=0; //a가 0이면 로그인 성공 , a가 1이면 로그인 실패
		try {
			pstmt = conn.prepareStatement(sql); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
			pstmt.setString(1, email);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery(); // Statement를 넣어도되지만,앞에 넣었으므로 현재는 안넣음

			if (rs.next()) {// 다음 레코드가 있을때
				usernumber=rs.getInt(1);
				System.out.println("카카오톡 유저입니다.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.NO_ACT, "", "", "", "", "", "", "", ""));
			} else {
				a=1;
				System.out.println("없는 유저입니다.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN_FAILURE, "", "", "", "", "", "", "", ""));
			}
			if(a==0) {
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1,usernumber);
				pstmt.setInt(2, usernumber);
				rs = pstmt.executeQuery();
				while (rs.next()) {// 다음 레코드가 있을때
			         friend.add(rs.getInt(1));  //해당 친구번호를 arraylist에 저장
				}
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1,usernumber);
				rs = pstmt.executeQuery();
				//유저정보에 대한 정보
				while (rs.next()) {
					System.out.println(rs.getString(1));
					writer.writeObject(new ChatMessage(ChatMessage.MsgType.FRIEND_LIST,rs.getString(1) , "", "", "", rs.getString(2), "", "", ""));
					//writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN,"" , "", "", "", "", "", "", ""));
				}
				//친구에 대한 정보
			    for(int i=0;i<friend.size();i++) {
			    	pstmt.setInt(1, friend.get(i));
			    	rs = pstmt.executeQuery();
			    	
			    	while (rs.next()) {
			    		System.out.println("hi"+rs.getString(1));
			    		System.out.println("hi"+rs.getString(2));
			    		writer.writeObject(new ChatMessage(ChatMessage.MsgType.FRIEND_LIST,rs.getString(1) , "", "", "", rs.getString(2), "", "", ""));
			    		
			    	}
			    }
			    writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN,"" , "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
		
	}
	// 비밀번호 재설정시 계정유뮤확인
	public void handleConfirm(String email, String phone, ObjectOutputStream writer) {
		// TODO Auto-generated method stub
		String sql = "select 이메일,전화번호 from user where 이메일 like ? and 전화번호 like ? ;";
		try {
			pstmt = conn.prepareStatement(sql); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
			pstmt.setString(1, email);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery(); // Statement를 넣어도되지만,앞에 넣었으므로 현재는 안넣음

			if (rs.next()) {// 다음 레코드가 있을때
				System.out.println("카카오톡 유저입니다.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM, "", "", "", "", "", "", "", ""));
			} else {
				System.out.println("없는 유저입니다.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
	}
	// 비밀번호 변경
	public void handleChange(String email,String pw,String phone) {
		// TODO Auto-generated method stub
		String sql = "update user set 비밀번호 = ? where 이메일 like ? and 전화번호 like ? ;";
		try {
			pstmt = conn.prepareStatement(sql); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
			pstmt.setString(1, pw);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
			System.out.println("successfully updating!!!");
			pstmt.close();

		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
	}
	
	public void handleAdd(String name, String phone,ObjectOutputStream writer) {
		// TODO Auto-generated method stub
		int a = 0; //a가 0이면 친구추가 진행, 1이면 서버에 친구정보 없음
		int b= 0; //b가 0이면 친구추가 완료 , 1이면 이미 존재하는 친구
		String sql = "insert into friend(유저번호,친구번호)values(?,?);";
		String sql2 = "select 유저번호,이름,전화번호 from user where 이름 like ? and 전화번호 like ?;";
		String sql3 = "select 유저번호,친구번호 from friend where 유저번호 like ? and 친구번호 like ?;";
		String sql4 = "select 이름,성별 from user where 유저번호 like ? ;";
		try {
			//친구추가하고자 하는 정보가 카카오 유저인지 먼저 판단하기
			pstmt = conn.prepareStatement(sql2); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery(); // Statement를 넣어도되지만,앞에 넣었으므로 현재는 안넣음

			if (rs.next()) {// 다음 레코드가 있을때 -> 친구테이블에 정보삽입
				 friendnumber=rs.getInt(1);
			}
			else {
				a=1;
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.ADD_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : 서버에서 송신 중 이상 발생");
			ex.printStackTrace();
		}
		if (a == 0) {
			try {
				pstmt = conn.prepareStatement(sql3); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
				pstmt.setInt(1, usernumber);
				pstmt.setInt(2, friendnumber);
				rs = pstmt.executeQuery(); // Statement를 넣어도되지만,앞에 넣었으므로 현재는 안넣음

				if (rs.next()) {// 다음 레코드가 있을때 -> 친구테이블에 정보삽입
					 b=1;
					 writer.writeObject(
								new ChatMessage(ChatMessage.MsgType.FRIEND_EXIST, "", "", "", "", "", "", "", ""));
				}
				else {
					//writer.writeObject(new ChatMessage(ChatMessage.MsgType.ADD, "", "", "", "", "", "", "", ""));
				}

			} catch (Exception ex) {
				System.out.println("S : 서버에서 송신 중 이상 발생");
				ex.printStackTrace();
			}
			if(b==0) { //실질적인 친구추가하는 부분
				
			try {
				
				pstmt = conn.prepareStatement(sql); // sql문을 conn을 이용해 전달, sql문을 DB에 전달한다고 생각하면 될듯! try catch문이 필요한 문장
				pstmt.setInt(1, usernumber); // 물음표가 있는 위치 순서로 번호가 지정됨. 물음표의 개수만큼 만들어줘야함.(개수 안맞추면 오류가 난다!)
				pstmt.setInt(2, friendnumber);

				int result = pstmt.executeUpdate(); // 쿼리 전송의 결과 객체를 반환하기위함. 0이면 오류, 1이면 제대로 실행된 것.
				// executeUpdate는 insert, update, delete 문에서 사용
				// executeQuery는 select문에서 사용.
				System.out.println("result = " + result);
				
				pstmt = conn.prepareStatement(sql4);
				pstmt.setInt(1,friendnumber);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					writer.writeObject(new ChatMessage(ChatMessage.MsgType.FRIEND_LIST,rs.getString(1) , "", "", "", rs.getString(2), "", "", ""));
				}

				writer.writeObject(new ChatMessage(ChatMessage.MsgType.ADD, "", "", "", "", "", "", "", ""));
				pstmt.close(); // 종료
				conn.close(); // 종료
			} catch (Exception ex) {
				System.out.println("S : 서버에서 송신 중 이상 발생");
				ex.printStackTrace();
			}
			}
		}
		
	}

	// 유저가 대화 상대방에게 보내는 메시지. 그 상대 혹은 "원하는 친구전체"에게 보내주어야 함
	/*
	 * private synchronized void handleMessage(String sender, String receiver,
	 * String contents) { //같은 단톡방에 있는 친구전체에게 보내는 경우를 처리해야 함, 이경우 리시버가 다수임
	 * if(receiver.length()>4) { array=receiver.split(","); for(int
	 * i=0;i<array.length;i++) { who.add(array[i]); //메시지를 받는사람이 다수일 경우 arraylist에
	 * 받는 사람저장 추후에 db로 넘겨줌 } } // 특정 상대,자신에게 보내는 경우라면 else {
	 * array=receiver.split(","); who.add(array[0]); //who[0]에는 메시지를 보내려는 상대방 이름 }
	 * try {
	 * 
	 * } catch (Exception ex) { System.out.println("S : 서버에서 송신 중 이상 발생");
	 * ex.printStackTrace(); } }
	 */

}
