
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class login {

	JFrame frame, mainFrame, creationFrame, pwChangeFrame1;
	LoginPanel lp;
	FriendPanel fp;
	MainPanel mp;
	CreationPanel cp;
	pwChangePanel1 cp1;
	JTextField id, cName, cEmail, cPw, cPhone, pwEmail, pwPhone;
	JPasswordField password;
	JLabel friend;
	ImageIcon loginBackground;
	ImageIcon createBackground;
	ImageIcon changeBackground1;
	JButton loginButton;	JButton createButton;	JButton pwChangeButton; 	JButton friendButton1; 	JButton friendButton2; 	JButton ChatButton1; 	JButton ChatButton2;
	JButton PlusButton1;	JButton PlusButton2; 	JButton emoticonButton1; 	JButton emoticonButton2; 	JButton noticeButton1; 	JButton noticeButton2;
	JButton settingButton1;	JButton settingButton2;	JButton searchButton;	JButton addButton; 	JButton confirmButton; JButton nextButton;
	JRadioButton men, women;
	ObjectInputStream reader; // 수신용 스트림
	ObjectOutputStream writer; // 송신용 스트림
	int loginConfirm = 0;
	String user, email, pw, phone, gender;
	Socket sock;
	// 배경
	String L_BACK = "src/image/카카오톡 로그인 화면.png";
	String C_BACK = "src/image/카카오계정 생성 화면.png";
	String C1_BACK = "src/image/비밀번호 재설정 화면1.png";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		login login = new login();
		login.go();

	}

	public void go() {
		frame = new JFrame("카카오톡");
		// 클라이언드 프레임 창 조정
		frame.setBounds(100, 100, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);

		id = new JTextField();
		password = new JPasswordField();
		id.setBounds(58, 220, 240, 39);
		password.setBounds(58, 258, 240, 39);
		password.setEchoChar('●'); // 비밀번호의 입력을 *모양으로 표시되도록 설정

		loginButton = new JButton(new ImageIcon("src/image/로그인 버튼.png"));
		createButton = new JButton(new ImageIcon("src/image/계정생성 버튼.png"));
		pwChangeButton = new JButton(new ImageIcon("src/image/비밀번호 재설정 버튼.png"));
		loginButton.setBounds(58, 300, 240, 40);
		createButton.setBounds(60, 500, 110, 30);
		pwChangeButton.setBounds(185, 500, 110, 30);
		ButtonListener bl = new ButtonListener();
		dbButtonListener b2 = new dbButtonListener();
		loginButton.addActionListener(b2);
		createButton.addActionListener(bl);
		pwChangeButton.addActionListener(bl);

		lp = new LoginPanel();
		lp.setBounds(0, 0, 370, 580);
		lp.add(id);
		lp.add(password);
		lp.add(loginButton);
		lp.add(createButton);
		lp.add(pwChangeButton);
		frame.getContentPane().add(lp);

		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		frame.setVisible(true);
	}

	public void mainFrame() {
		mainFrame = new JFrame("카카오톡");
		// 클라이언트 프레임 창 조정
		mainFrame.setBounds(100, 100, 410, 670);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setLocationRelativeTo(null);

		friendButton1 = new JButton(new ImageIcon("src/image/친구1.png"));
		friendButton1.setBounds(14, 40, 40, 40);
		// friendButton1.setBorderPainted(false);
		friendButton1.setContentAreaFilled(false);
		friendButton1.setFocusPainted(false);

		ChatButton1 = new JButton(new ImageIcon("src/image/채팅1.png"));
		ChatButton1.setBounds(14, 100, 40, 40);
		// ChatButton1.setBorderPainted(false);
		ChatButton1.setContentAreaFilled(false);
		ChatButton1.setFocusPainted(false);

		PlusButton1 = new JButton(new ImageIcon("src/image/더보기1.png"));
		PlusButton1.setBounds(14, 160, 40, 40);
		// PlusButton1.setBorderPainted(false);
		PlusButton1.setContentAreaFilled(false);
		PlusButton1.setFocusPainted(false);

		emoticonButton1 = new JButton(new ImageIcon("src/image/이모티콘 샵1.png"));
		emoticonButton1.setBounds(14, 419, 40, 40);
		// emoticonButton1.setBorderPainted(false);
		emoticonButton1.setContentAreaFilled(false);
		emoticonButton1.setFocusPainted(false);

		noticeButton1 = new JButton(new ImageIcon("src/image/알림1.png"));
		noticeButton1.setBounds(14, 459, 40, 40);
		// noticeButton1.setBorderPainted(false);
		noticeButton1.setContentAreaFilled(false);
		noticeButton1.setFocusPainted(false);

		settingButton1 = new JButton(new ImageIcon("src/image/설정1.png"));
		settingButton1.setBounds(14, 499, 40, 40);
		// settingButton1.setBorderPainted(false);
		settingButton1.setContentAreaFilled(false);
		settingButton1.setFocusPainted(false);

		searchButton = new JButton(new ImageIcon("src/image/통합검색.png"));
		searchButton.setBounds(240, 45, 25, 25);
		searchButton.setBorderPainted(false);
		searchButton.setContentAreaFilled(false);
		searchButton.setFocusPainted(false);

		addButton = new JButton(new ImageIcon("src/image/친구추가.png"));
		addButton.setBounds(280, 46, 30, 30);
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setFocusPainted(false);

		friend = new JLabel("친구");
		friend.setBounds(18, 20, 70, 80);
		friend.setFont(friend.getFont().deriveFont(20.0f)); // 친구 글꼴 크기 조정

		mp = new MainPanel();
		mp.setBounds(0, 0, 66, 630);
		mp.add(friendButton1);
		mp.add(ChatButton1);
		mp.add(PlusButton1);
		mp.add(emoticonButton1);
		mp.add(noticeButton1);
		mp.add(settingButton1);

		fp = new FriendPanel();
		fp.setBounds(66, 0, 328, 79);
		fp.setBackground(Color.white);
		fp.add(friend);
		fp.add(searchButton);
		fp.add(addButton);
		mainFrame.getContentPane().add(mp);
		mainFrame.getContentPane().add(fp);
		mainFrame.setVisible(true);
	}

	public void creationFrame() {
		creationFrame = new JFrame("카카오계정 생성");
		// 클라이언드 프레임 창 조정
		creationFrame.setBounds(94, 100, 386, 536);
		// creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		creationFrame.getContentPane().setLayout(null);
		creationFrame.setLocationRelativeTo(null);
		// frame.setResizable(false);

		cName = new JTextField();
		cEmail = new JTextField();
		cPw = new JTextField();
		cPhone = new JTextField();

		cName.setBounds(110, 194, 200, 32);
		cEmail.setBounds(110, 234, 200, 32);
		cPw.setBounds(110, 274, 200, 32);
		cPhone.setBounds(110, 333, 200, 32);

		confirmButton = new JButton();
		confirmButton.setBounds(38, 427, 300, 38);
		// confirmButton.setBorderPainted(false);
		confirmButton.setContentAreaFilled(false);
		confirmButton.setFocusPainted(false);

		dbButtonListener b2 = new dbButtonListener();
		confirmButton.addActionListener(b2);

		ButtonGroup gChoice = new ButtonGroup(); // 라디어 버튼을 그룹화 하기위한 객체 생성
		men = new JRadioButton("남성"); // 라디오 버튼 생성
		women = new JRadioButton("여성"); // 라디오 버튼 생성

		gChoice.add(men);
		gChoice.add(women);

		men.setBackground(Color.white);
		women.setBackground(Color.white);
		men.setBounds(110, 366, 50, 40);
		women.setBounds(160, 366, 50, 40);

		cp = new CreationPanel();
		cp.setBounds(0, 0, 370, 580);
		cp.add(confirmButton);
		cp.add(cName);
		cp.add(cEmail);
		cp.add(cPw);
		cp.add(cPhone);
		cp.add(men);
		cp.add(women);
		creationFrame.getContentPane().add(cp);

		creationFrame.setVisible(true);
	}

	public void pwChangeFrame1() {
		pwChangeFrame1=new JFrame("비밀번호 재설정");
		// 클라이언드 프레임 창 조정
		pwChangeFrame1.setBounds(92, 100, 384, 370);
		//creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		pwChangeFrame1.getContentPane().setLayout(null);
		pwChangeFrame1.setLocationRelativeTo(null);
        // frame.setResizable(false);      
       
		pwEmail = new JTextField();
		pwPhone = new JTextField();
		
		pwEmail.setBounds(110, 167, 200, 32);
		pwPhone.setBounds(110, 223, 200, 32);
		
		nextButton = new JButton();
		nextButton.setBounds(38, 271, 300, 38);
		//confirmButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setFocusPainted(false);
		
		dbButtonListener b2 = new dbButtonListener();
		nextButton.addActionListener(b2);
		
        cp1=new pwChangePanel1();
        cp1.setBounds(0,0, 370, 580);
        cp1.add(nextButton);
        cp1.add(pwEmail);cp1.add(pwPhone);
		pwChangeFrame1.getContentPane().add(cp1);
        
        
		pwChangeFrame1.setVisible(true);
	}

	///////////////////////////////////////// 패널////////////////////////////////////////////////

	// 로그인 패널
	class LoginPanel extends JPanel {
		public LoginPanel() {
			setLayout(null);
			loginBackground = new ImageIcon(L_BACK);
		}

		public void paintComponent(Graphics g) {
			// loginBackground.draw(g);
			g.drawImage(loginBackground.getImage(), 0, 0, 355, 563, null);
		}
	}

	// 메인 패널
	class MainPanel extends JPanel {
		public MainPanel() {
			setLayout(null);
		}
	}

	// 친구 패널
	class FriendPanel extends JPanel {
		public FriendPanel() {
			setLayout(null);

		}
	}

	// 카카오계정 생성 패널
	class CreationPanel extends JPanel {
		public CreationPanel() {
			setLayout(null);
			createBackground = new ImageIcon(C_BACK);
		}

		public void paintComponent(Graphics g) {
			// loginBackground.draw(g);
			g.drawImage(createBackground.getImage(), 0, 0, 370, 496, null);
		}
	}

	// 비빌번호 재설정 패널1
	class pwChangePanel1 extends JPanel {
		public pwChangePanel1() {
			setLayout(null);
			changeBackground1 = new ImageIcon(C1_BACK);
		}

		public void paintComponent(Graphics g) {
			// loginBackground.draw(g);
			g.drawImage(changeBackground1.getImage(), 0, 0, 370, 330, null);
		}
	}

	private void setUpNetworking() {
		try {
			// sock = new Socket("220.69.203.11", 5000); // 오동익의 컴퓨터
			sock = new Socket("127.0.0.1", 6000); // 소켓 통신을 위한 포트는 5000번 사용키로 함
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "서버접속에 실패하였습니다. 접속을 종료합니다.");
			ex.printStackTrace();
			frame.dispose(); // 네트워크가 초기 연결 안되면 클라이언트 강제 종료
		}
	} // close setUpNetworking

	// 로그인화면 버튼 리스너
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource() == createButton) {
				creationFrame();
			}
			if (e.getSource() == pwChangeButton) {
				pwChangeFrame1();
			}
		}
	}

	// DB와 연동해야하는 버튼 리스너
	class dbButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == confirmButton) {
				user = cName.getText();
				email = cEmail.getText();
				pw = cPw.getText();
				phone = cPhone.getText();
				gender = men.getText();
				processCreation();

			}
			if (e.getSource() == loginButton) {
				email = id.getText();
				pw = password.getText();
				processLogin();

			}
			if (e.getSource() == nextButton) {
				email = pwEmail.getText();
				phone = pwPhone.getText();
				processPwChange1();
			}
		}

	}

	////////////////////////////// 유저가 서버로 보내는 메시지/////////////////////////////////
	// 카카오 계정 생성 처리
	public void processCreation() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(
					new ChatMessage(ChatMessage.MsgType.CREATION, user, email, pw, phone, gender, "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "카카오 계정 생성 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}

	// 카카오톡 로그인 처리
	public void processLogin() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, "", email, pw, "", "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "카카오톡 로그인 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}
	//비밀번호 재설정 처리1
	public void processPwChange1() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM, "", email, "", phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "카카오톡 계정확인 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}

	/////////////////////////////// 유저가 서버로 부터 받는 메시지///////////////////////////////
	public class IncomingReader implements Runnable {
		public void run() {
			ChatMessage message;
			ChatMessage.MsgType type;
			try {
				while (true) {
					message = (ChatMessage) reader.readObject(); // 서버로기 부터의 메시지 대기
					type = message.getType();
					if (type == ChatMessage.MsgType.CREATION_SUCCESS) {
						creationFrame.dispose();
					} 
					else if (type == ChatMessage.MsgType.CREATION_FAILURE) {
						JOptionPane.showMessageDialog(null, "이미 가입된 이메일입니다. 다른 이메일을 입력하세요");
					} 
					else if (type == ChatMessage.MsgType.LOGIN) {
						mainFrame();
						frame.setVisible(false);
					} 
					else if (type == ChatMessage.MsgType.LOGIN_FAILURE) {
						JOptionPane.showMessageDialog(null, "Login이 실패하였습니다. 다시 로그인하세요");
					}
					else if (type == ChatMessage.MsgType.CONFIRM) {
						
					}
					else if (type == ChatMessage.MsgType.CONFIRM_FAILURE) {
						JOptionPane.showMessageDialog(null, "없는 카카오 계정입니다. 이메일과 전화번호를 다시 입력하세요");
					}
				}
			} catch (Exception ex) {
				System.out.println("유저 스레드 종료"); // 프레임이 종료될 경우 이를 통해 스레드 종료
			}
		}
	}
}
