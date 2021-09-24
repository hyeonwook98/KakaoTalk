import java.io.*;
import java.net.*;
import java.util.*;

public class KakaoServer {
	// ������ Ŭ���̾�Ʈ�� ����� �̸��� ��� ��Ʈ���� �ؽ� ���̺� ����
	// ���߿� Ư�� ����ڿ��� �޽����� ������ ���. ���� ������ �ִ� ������� ��ü ����Ʈ�� ���Ҷ��� ���

	ArrayList<String> who = new ArrayList(); //string Ÿ�Ը� ��밡��
	String[] array = null;
	

	public static void main (String[] args) {
		new KakaoServer().go();
	}

	private void go () {
		try {
			ServerSocket serverSock = new ServerSocket(7000);	// ä���� ���� ���� ��Ʈ 5000 ���

			while(true) {
				Socket userSocket = serverSock.accept();		// ���ο� Ŭ���̾�Ʈ ���� ���

				// Ŭ���̾�Ʈ�� ���� ����� ��Ʈ�� �� ������ ����
				Thread t1 = new Thread(new UserHandler(userSocket));
				t1.start();
				System.out.println("S : Ŭ���̾�Ʈ ���� ��");		// ���¸� �������� ��� �޽���
			}
		} catch(Exception ex) {
			System.out.println("S : Ŭ���̾�Ʈ  ���� �� �̻�߻�");	// ���¸� �������� ���  �޽���
			ex.printStackTrace();
		}
	}
	//7.28���� �ؿ� �����ϰ� go�Լ� ������ �ΰ� ���ָ� �����
	//������ 1:1 �����ϴ� �޽��� ���� ������
	private class UserHandler implements Runnable{
		Socket sock;					// Ŭ���̾�Ʈ ����� ����
		ObjectInputStream reader;		// Ŭ���̾�Ʈ�� ���� �����ϱ� ���� ��Ʈ��
		ObjectOutputStream writer;		// Ŭ���̾�Ʈ�� �۽��ϱ� ���� ��Ʈ��

		// ������. Ŭ���̾�Ʈ���� ���Ͽ��� �б�� ���� ��Ʈ�� ����� ��
		// ��Ʈ���� ���鶧 InputStream�� ���� ����� Hang��. �׷��� OutputStream���� �������.
		// �̰��� Ŭ���̾�Ʈ���� InpitStreams�� ���� ����� ������ �ȱ׷��� �����
		public UserHandler(Socket userSocket) {
			try {
				sock = userSocket;
				writer = new ObjectOutputStream(userSocket.getOutputStream());
				reader = new ObjectInputStream(userSocket.getInputStream());
			} catch(Exception ex) {
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
					message = (ChatMessage) reader.readObject();	  // Ŭ���̾�Ʈ�� ���� �޽��� ����
					type = message.getType();
					if(type == ChatMessage.MsgType.USER_MSG) {
						handleMessage(message.getSender(), message.getReceiver(), message.getContents());
					}
					else if (type == ChatMessage.MsgType.NO_ACT) {
						//  �����ص� �Ǵ� �޽���
						continue;
					}
					else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���?
						throw new Exception("S : Ŭ���̾�Ʈ���� �˼� ���� �޽��� ��������");
					}
				}
			} catch(Exception ex) {
				System.out.println("S : Ŭ���̾�Ʈ ���� ����");				// ����� Ŭ���̾�Ʈ ����Ǹ� ���ܹ߻�
				// �̸� �̿��� ������ �����Ŵ
			}
		}// close run
	}// close inner class

	//������ ��ȭ ���濡�� ������ �޽���. �� ��� Ȥ�� "���ϴ� ģ����ü"���� �����־�� ��
	private synchronized void handleMessage(String sender, String receiver, String contents) {
		//���� ����濡 �ִ� ģ����ü���� ������ ��츦 ó���ؾ� ��, �̰�� ���ù��� �ټ���
		if(receiver.length()>4) {
			array=receiver.split(",");
			for(int i=0;i<array.length;i++) {
				who.add(array[i]);           //�޽����� �޴»���� �ټ��� ��� arraylist�� �޴� ������� ���Ŀ� db�� �Ѱ���
			}
		}
		// Ư�� ���,�ڽſ��� ������ �����
		else {
			array=receiver.split(",");
			who.add(array[0]);   //who[0]���� �޽����� �������� ���� �̸�
		}
	}

	//������ 1:1 �����ϴ� �������� ���� ������
}
