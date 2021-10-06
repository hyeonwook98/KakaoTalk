import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class KakaoServer {
	// ������ Ŭ���̾�Ʈ�� ����� �̸��� ��� ��Ʈ���� �ؽ� ���̺� ����
	// ���߿� Ư�� ����ڿ��� �޽����� ������ ���. ���� ������ �ִ� ������� ��ü ����Ʈ�� ���Ҷ��� ���

	ArrayList<String> who = new ArrayList(); // string Ÿ�Ը� ��밡��
	String[] array = null;
	DB db = new DB();// DB ������ ���� ��ü ����
	Connection conn = db.conn(); // �ڹٿ� DB ������� ��ü�� Connection ��ü ����
	PreparedStatement pstmt;// �Ķ���͸� ����ǥ�� ���� �� ����. � ������ ����(����ǥ)�� ���� ���������� �� �����ϰ� ��� ����. �������� ���� ���ϰ� ����ϱ�����
							// ���.
	ResultSet rs; // ���� ������ ��� ��ü�� ��ȯ�ϱ�����

	public static void main(String[] args) {
		new KakaoServer().go();
	}

	private void go() {
		try {
			ServerSocket serverSock = new ServerSocket(6000); // ä���� ���� ���� ��Ʈ 5000 ���

			while (true) {
				Socket userSocket = serverSock.accept(); // ���ο� Ŭ���̾�Ʈ ���� ���

				// Ŭ���̾�Ʈ�� ���� ����� ��Ʈ�� �� ������ ����
				Thread t1 = new Thread(new UserHandler(userSocket));
				t1.start();

				System.out.println("S : Ŭ���̾�Ʈ ���� ��"); // ���¸� �������� ��� �޽���
			}
		} catch (Exception ex) {
			System.out.println("S : Ŭ���̾�Ʈ  ���� �� �̻�߻�"); // ���¸� �������� ��� �޽���
			ex.printStackTrace();
		}
	}

	// 7.28���� �ؿ� �����ϰ� go�Լ� ������ �ΰ� ���ָ� �����
	// ������ 1:1 �����ϴ� �޽��� ���� ������
	private class UserHandler implements Runnable {
		Socket sock; // Ŭ���̾�Ʈ ����� ����
		ObjectOutputStream writer; // Ŭ���̾�Ʈ�� �۽��ϱ� ���� ��Ʈ��
		ObjectInputStream reader; // Ŭ���̾�Ʈ�� ���� �����ϱ� ���� ��Ʈ��

		// ������. Ŭ���̾�Ʈ���� ���Ͽ��� �б�� ���� ��Ʈ�� ����� ��
		// ��Ʈ���� ���鶧 InputStream�� ���� ����� Hang��. �׷��� OutputStream���� �������.
		// �̰��� Ŭ���̾�Ʈ���� InpitStreams�� ���� ����� ������ �ȱ׷��� �����
		public UserHandler(Socket userSocket) {
			try {
				sock = userSocket;
				writer = new ObjectOutputStream(userSocket.getOutputStream());
				reader = new ObjectInputStream(userSocket.getInputStream());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// Ŭ���̾�Ʈ���� ���� �޽����� ���� �����ϴ� �۾��� ����

		public void run() {
			// TODO Auto-generated method stub
			ChatMessage message;
			ChatMessage.MsgType type;
			try {
				while (true) {
					// ���� �޽����� ������ ���� ���� ������ ������ ����
					message = (ChatMessage) reader.readObject(); // Ŭ���̾�Ʈ�� ���� �޽��� ����
					type = message.getType();
					if (type == ChatMessage.MsgType.CREATION) {
						handleCreation(message.getName(), message.getEmail(), message.getPw(), message.getPhone(),
								message.getGender(), writer);
					} 
					else if (type == ChatMessage.MsgType.LOGIN) {
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
					else if (type == ChatMessage.MsgType.USER_MSG) {
						// handleMessage(message.getSender(), message.getReceiver(),
						// message.getContents());
					} 
					else if (type == ChatMessage.MsgType.NO_ACT) {
						// �����ص� �Ǵ� �޽���
						continue;
					} 
					else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���?
						throw new Exception("S : Ŭ���̾�Ʈ���� �˼� ���� �޽��� ��������");
					}
				}
			} catch (Exception ex) {
				System.out.println("S : Ŭ���̾�Ʈ ���� ����"); // ����� Ŭ���̾�Ʈ ����Ǹ� ���ܹ߻�
				// �̸� �̿��� ������ �����Ŵ
			}
		}// close run
	}// close inner class

	// ���� īī������ ���� �����ޱ�
	private synchronized void handleCreation(String name, String email, String pw, String phone, String gender,
			ObjectOutputStream writer) {
		int a = 0; //a�� 0�̸� ���� �����Ϸ�, 1�̸� ���� ��������
		String sql = "insert into user(�̸�,�̸���,��й�ȣ,��ȭ��ȣ,����)values(?,?,?,?,?);";
		String sql2 = "select �̸��� from user where �̸��� like ?;";
		try {
			pstmt = conn.prepareStatement(sql2); // sql���� conn�� �̿��� ����, sql���� DB�� �����Ѵٰ� �����ϸ� �ɵ�! try catch���� �ʿ��� ����
			pstmt.setString(1, email);
			rs = pstmt.executeQuery(); // Statement�� �־������,�տ� �־����Ƿ� ����� �ȳ���

			if (rs.next()) {// ���� ���ڵ尡 ������
				a = 1;
				System.out.println("�̹� ���Ե� �̸����Դϴ�.");
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.CREATION_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : �������� �۽� �� �̻� �߻�");
			ex.printStackTrace();
		}
		if (a == 0) {
			try {
				writer.writeObject(
						new ChatMessage(ChatMessage.MsgType.CREATION_SUCCESS, "", "", "", "", "", "", "", ""));
				pstmt = conn.prepareStatement(sql); // sql���� conn�� �̿��� ����, sql���� DB�� �����Ѵٰ� �����ϸ� �ɵ�! try catch���� �ʿ��� ����
				pstmt.setString(1, name); // ����ǥ�� �ִ� ��ġ ������ ��ȣ�� ������. ����ǥ�� ������ŭ ����������.(���� �ȸ��߸� ������ ����!)
				pstmt.setString(2, email);
				pstmt.setString(3, pw);
				pstmt.setString(4, phone);
				pstmt.setString(5, gender);

				// WORD�� String���� ���� String�� cname, cphone, ctype, cnumber�� �����ؼ� �����ؼ� �����ϵ��� ����.
				// cname, cphone, ctype, cnumber�� �ش�Ǵ� �κ��� JTextField���� ���� ������ �޾ƿ;���!(�ؽ�Ʈ�ʵ�
				// ����.getText(););

				int result = pstmt.executeUpdate(); // ���� ������ ��� ��ü�� ��ȯ�ϱ�����. 0�̸� ����, 1�̸� ����� ����� ��.
				// executeUpdate�� insert, update, delete ������ ���
				// executeQuery�� select������ ���.
				System.out.println("result = " + result);

				pstmt.close(); // ����
				conn.close(); // ����
			} catch (Exception ex) {
				System.out.println("S : �������� �۽� �� �̻� �߻�");
				ex.printStackTrace();
			}
		}

	}


	

	// �α��� ���� �ޱ�
	public void handleLogin(String email, String pw, ObjectOutputStream writer) {
		// TODO Auto-generated method stub
		String sql = "select �̸���,��й�ȣ from user where �̸��� like ? and ��й�ȣ like ? ;";
		try {
			pstmt = conn.prepareStatement(sql); // sql���� conn�� �̿��� ����, sql���� DB�� �����Ѵٰ� �����ϸ� �ɵ�! try catch���� �ʿ��� ����
			pstmt.setString(1, email);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery(); // Statement�� �־������,�տ� �־����Ƿ� ����� �ȳ���

			if (rs.next()) {// ���� ���ڵ尡 ������
				System.out.println("īī���� �����Դϴ�.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, "", "", "", "", "", "", "", ""));
			} else {
				System.out.println("���� �����Դϴ�.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : �������� �۽� �� �̻� �߻�");
			ex.printStackTrace();
		}
	}
	// ��й�ȣ �缳���� ��������Ȯ��
	public void handleConfirm(String email, String phone, ObjectOutputStream writer) {
		// TODO Auto-generated method stub
		String sql = "select �̸���,��ȭ��ȣ from user where �̸��� like ? and ��ȭ��ȣ like ? ;";
		try {
			pstmt = conn.prepareStatement(sql); // sql���� conn�� �̿��� ����, sql���� DB�� �����Ѵٰ� �����ϸ� �ɵ�! try catch���� �ʿ��� ����
			pstmt.setString(1, email);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery(); // Statement�� �־������,�տ� �־����Ƿ� ����� �ȳ���

			if (rs.next()) {// ���� ���ڵ尡 ������
				System.out.println("īī���� �����Դϴ�.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM, "", "", "", "", "", "", "", ""));
			} else {
				System.out.println("���� �����Դϴ�.");
				writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM_FAILURE, "", "", "", "", "", "", "", ""));
			}

		} catch (Exception ex) {
			System.out.println("S : �������� �۽� �� �̻� �߻�");
			ex.printStackTrace();
		}
	}
	// ��й�ȣ ����
	public void handleChange(String email,String pw,String phone) {
		// TODO Auto-generated method stub
		String sql = "update user set ��й�ȣ = ? where �̸��� like ? and ��ȭ��ȣ like ? ;";
		try {
			pstmt = conn.prepareStatement(sql); // sql���� conn�� �̿��� ����, sql���� DB�� �����Ѵٰ� �����ϸ� �ɵ�! try catch���� �ʿ��� ����
			pstmt.setString(1, pw);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
			System.out.println("successfully updating!!!");
			pstmt.close();

		} catch (Exception ex) {
			System.out.println("S : �������� �۽� �� �̻� �߻�");
			ex.printStackTrace();
		}
	}

	// ������ ��ȭ ���濡�� ������ �޽���. �� ��� Ȥ�� "���ϴ� ģ����ü"���� �����־�� ��
	/*
	 * private synchronized void handleMessage(String sender, String receiver,
	 * String contents) { //���� ����濡 �ִ� ģ����ü���� ������ ��츦 ó���ؾ� ��, �̰�� ���ù��� �ټ���
	 * if(receiver.length()>4) { array=receiver.split(","); for(int
	 * i=0;i<array.length;i++) { who.add(array[i]); //�޽����� �޴»���� �ټ��� ��� arraylist��
	 * �޴� ������� ���Ŀ� db�� �Ѱ��� } } // Ư�� ���,�ڽſ��� ������ ����� else {
	 * array=receiver.split(","); who.add(array[0]); //who[0]���� �޽����� �������� ���� �̸� }
	 * try {
	 * 
	 * } catch (Exception ex) { System.out.println("S : �������� �۽� �� �̻� �߻�");
	 * ex.printStackTrace(); } }
	 */

}
