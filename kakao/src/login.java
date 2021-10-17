
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class login {

	JFrame frame, mainFrame, creationFrame, pwChangeFrame1 , pwChangeFrame2 , friendAddFrame;
	LoginPanel lp;
	FriendPanel fp; FriendListPanel flp; UserPanel up;
	MainPanel mp;
	CreationPanel cp;
	JPanel FriendListPanel;
	pwChangePanel1 cp1; pwChangePanel2 cp2; friendAddPanel ap;
	JTextField id, cName, cEmail, cPw, cPhone, pwEmail, pwPhone, aName , aPhone;
	JPasswordField password, changePw, changePwRe;
	JLabel friend,name2 ,fcount;
	ImageIcon loginBackground;	ImageIcon createBackground;	ImageIcon changeBackground1; ImageIcon changeBackground2; ImageIcon addBackground;
	JButton loginButton;	JButton createButton;	JButton pwChangeButton; 	JButton friendButton1; 	JButton friendButton2; 	JButton ChatButton1; 	JButton ChatButton2;
	JButton PlusButton1;	JButton PlusButton2; 	JButton emoticonButton1; 	JButton emoticonButton2; 	JButton noticeButton1; 	JButton noticeButton2;
	JButton settingButton1;	JButton settingButton2;	JButton searchButton;	JButton addButton; 	JButton confirmButton; JButton nextButton; JButton successButton; JButton fAddButton;
	 JButton profileButton2;JButton musicButton2;JButton chatButton3;
	JRadioButton men, women;
	ObjectInputStream reader; // 수신용 스트림
	ObjectOutputStream writer; // 송신용 스트림
	Thread readerThread;
	JScrollPane scroll;
	Scrollbar bar;
	ArrayList<People> list;  ArrayList<JPanel> userpanel; ArrayList<JButton> profileButton ; 
	ArrayList<JButton> musicButton; ArrayList<JButton> chatButton; ArrayList<JLabel> name;
	int x,y,width,height;
	int loginConfirm = 0;
	String user, email, pw,pw2, phone, gender;
	Socket sock;
	// 배경
	String L_BACK = "src/image/카카오톡 로그인 화면.png";
	String C_BACK = "src/image/카카오계정 생성 화면.png";
	String C1_BACK = "src/image/비밀번호 재설정 화면1.png";
	String C2_BACK = "src/image/비밀번호 재설정 화면2.png";
	String A_BACK = "src/image/친구추가 화면.png";

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

		setUpNetworking();
		readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		list = new ArrayList<People>();
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
		
		ButtonListener b1 = new ButtonListener();
		addButton.addActionListener(b1);

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
		
		/*flp = new FriendListPanel();
		flp.setBounds(66,79,328,551);
		flp.setBackground(Color.white);
		/*
		scroll = new JScrollPane(flp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportView(flp);
		scroll.setBounds(66,79,329,553);
		*/
	    FriendListPanel = new JPanel();
		FriendListPanel.setLayout(new BoxLayout(FriendListPanel,BoxLayout.Y_AXIS));
		FriendListPanel.setBounds(66,79,326,551);;
		FriendListPanel.setBackground(Color.white);
		
		
		//mainFrame.add(mp);mainFrame.add(fp);
		//mainFrame.getContentPane().add(mp); mainFrame.getContentPane().add(fp);
		
		//mainFrame.getContentPane().add(FriendListPanel);
		
		processUserList();
		
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
	
	
	public void pwChangeFrame2() {
		pwChangeFrame2=new JFrame("비밀번호 재설정");
		// 클라이언드 프레임 창 조정
		pwChangeFrame2.setBounds(89, 100, 387, 422);
		//creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		pwChangeFrame2.getContentPane().setLayout(null);
		pwChangeFrame2.setLocationRelativeTo(null);
        // frame.setResizable(false);      
       
		changePw = new JPasswordField();
		changePwRe = new JPasswordField();
		
		changePw.setEchoChar('●');
		changePwRe.setEchoChar('●');
		changePw.setBounds(180, 81, 180, 32);
		changePwRe.setBounds(180, 119, 180, 32);
		
		successButton = new JButton();
		successButton.setBounds(46, 283, 275, 37);
		//successButton.setBorderPainted(false);
		successButton.setContentAreaFilled(false);
		successButton.setFocusPainted(false);
		
		dbButtonListener b2 = new dbButtonListener();
		successButton.addActionListener(b2);
		
        cp2=new pwChangePanel2();
        cp2.setBounds(0,0, 370, 580);
        //cp2.add(nextButton);
        cp2.add(changePw);cp2.add(changePwRe); cp2.add(successButton);
		pwChangeFrame2.getContentPane().add(cp2);
        
        
		pwChangeFrame2.setVisible(true);
	}
	
	public void friendAddFrame() {
		friendAddFrame=new JFrame("친구 추가");
		// 클라이언드 프레임 창 조정
		friendAddFrame.setBounds(95, 85, 337, 478);
		//creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		friendAddFrame.getContentPane().setLayout(null);
		friendAddFrame.setLocationRelativeTo(null);
        // frame.setResizable(false);      
       
		aName = new JTextField();
		aPhone = new JTextField();
		
		
		aName.setBounds(100, 92, 150, 32);
		aPhone.setBounds(100, 160, 150, 32);
		
		fAddButton = new JButton();
		fAddButton.setBounds(235, 378, 70, 34);
		//successButton.setBorderPainted(false);
		fAddButton.setContentAreaFilled(false);
		fAddButton.setFocusPainted(false);
		
		dbButtonListener b2 = new dbButtonListener();
		fAddButton.addActionListener(b2);
		
        ap=new friendAddPanel();
        ap.setBounds(0,0, 370, 580);
        ap.add(aName); ap.add(aPhone);
        ap.add(fAddButton);
        
        friendAddFrame.getContentPane().add(ap);
        
        
        friendAddFrame.setVisible(true);
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
	// 비빌번호 재설정 패널2
		class pwChangePanel2 extends JPanel {
			public pwChangePanel2() {
				setLayout(null);
				changeBackground2 = new ImageIcon(C2_BACK);
			}

			public void paintComponent(Graphics g) {
				// loginBackground.draw(g);
				g.drawImage(changeBackground2.getImage(), 0, 0, 370, 382, null);
			}
		}
		//유저의 친구목록 패널
		class FriendListPanel extends JPanel{
			public FriendListPanel() {/*
				//setLayout(null);
				setLayout(new BoxLayout(flp,BoxLayout.Y_AXIS));
				//setLayout(new GridLayout(1,1));
*/
			}
		}
		class UserPanel extends JPanel{
			public UserPanel() {
				setLayout(null);
			}
		}
		
		//친구추가 패널
		class friendAddPanel extends JPanel{
			public friendAddPanel() {
				setLayout(null);
				addBackground = new ImageIcon(A_BACK);
			}

			public void paintComponent(Graphics g) {
				// loginBackground.draw(g);
				g.drawImage(addBackground.getImage(), 0, 0, 320, 438, null);
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
			if (e.getSource() == addButton) {
				friendAddFrame();
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
			if (e.getSource() == successButton) {
				pw = changePw.getText();
				pw2 = changePwRe.getText();
				if(pw2.length()>7) {
					if(pw.equals(pw2)==true) {
						processPwChange2();
						JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다.");
						pwChangeFrame2.dispose();;
					}
					else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 비밀번호를 다시 입력하세요");
					}
				}
				else 
					JOptionPane.showMessageDialog(null, "비밀번호를 8~32자리로 설정해주세요");
			}
			if (e.getSource() == fAddButton) {
				phone = aPhone.getText();
				user = aName.getText();
				processFriendAdd();
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
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN_TRY, "", email, pw, "", "", "", "", ""));
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
	//비밀번호 변경
	public void processPwChange2() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CHANGE, "", email, pw2, phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "비밀번호 변경 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}

	public void processFriendAdd() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.ADD, user, "", "", phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "친구추가 중 서버접속에 문제가 발생하였습니다.");
			ex.printStackTrace();
		}
	}
	public void processUserList() {
		try {
			userpanel = new ArrayList<JPanel>();
			name = new ArrayList<JLabel>();
			profileButton = new ArrayList<JButton>();
			chatButton = new ArrayList<JButton>();
			musicButton = new ArrayList<JButton>();
			int i=0;
			
			for(i=0;i<list.size();i++) {
				if(i==0) {
			userpanel.add(new JPanel());
			userpanel.get(i).setLayout(null);
			userpanel.get(i).setPreferredSize(new Dimension(170,70));
			//userpanel.get(0).setBounds(0,0,170,20);
			userpanel.get(i).setBackground(Color.white);
			
			name.add(new JLabel(list.get(i).name));
			name.get(i).setBounds(77,20,45,33);
			name.get(i).setFont(name.get(i).getFont().deriveFont(14.0f));
			
			if(list.get(i).gender.equals("남성")) {
				profileButton.add(new JButton(new ImageIcon("src/image/남성.jpg")));
				profileButton.get(i).setBounds(19,10,50,50);
			}
			else if(list.get(i).gender.equals("여성")) {
				profileButton.add(new JButton(new ImageIcon("src/image/여성.jpg")));
				profileButton.get(i).setBounds(19,10,50,50);
			}
			
			chatButton.add(new JButton());
			chatButton.get(i).setBounds(0,0,313,70);
			chatButton.get(i).setContentAreaFilled(false);
			chatButton.get(i).setFocusPainted(false);
			
			musicButton.add(new JButton()) ;
			musicButton.get(i).setBounds(160,20,140,30);
			
			userpanel.get(i).add(name.get(i));userpanel.get(i).add(profileButton.get(i));userpanel.get(i).add(chatButton.get(i));userpanel.get(i).add(musicButton.get(i));
			
			FriendListPanel.add(userpanel.get(i));
			
			//친구라벨
			userpanel.add(new JPanel());
			userpanel.get(1).setLayout(null);
			userpanel.get(1).setPreferredSize(new Dimension(170,40));
			userpanel.get(1).setBackground(Color.white);
			
			fcount = new JLabel("친구  "+(list.size()-1));
			fcount.setBounds(20,10,40,20);
			fcount.setFont(fcount.getFont().deriveFont(10.0f));
			
			userpanel.get(1).add(fcount);
			
			FriendListPanel.add(userpanel.get(1));
				}
			else {
				userpanel.add(new JPanel());
				userpanel.get(i+1).setLayout(null);
				userpanel.get(i+1).setPreferredSize(new Dimension(170,70));
				userpanel.get(i+1).setBackground(Color.white);
				
				name.add(new JLabel(list.get(i).name));
				name.get(i).setBounds(77,20,45,33);
				name.get(i).setFont(name.get(i).getFont().deriveFont(14.0f));
				
				if(list.get(i).gender.equals("남성")) {
					profileButton.add(new JButton(new ImageIcon("src/image/남성.jpg")));
					profileButton.get(i).setBounds(19,10,50,50);
				}
				else if(list.get(i).gender.equals("여성")) {
					profileButton.add(new JButton(new ImageIcon("src/image/여성.jpg")));
					profileButton.get(i).setBounds(19,10,50,50);
				}
				
				chatButton.add(new JButton());
				chatButton.get(i).setBounds(0,0,313,70);
				chatButton.get(i).setContentAreaFilled(false);
				chatButton.get(i).setFocusPainted(false);
				
				musicButton.add(new JButton()) ;
				musicButton.get(i).setBounds(160,20,140,30);
				
				userpanel.get(i+1).add(name.get(i));userpanel.get(i+1).add(profileButton.get(i));userpanel.get(i+1).add(chatButton.get(i));userpanel.get(i+1).add(musicButton.get(i));
				FriendListPanel.add(userpanel.get(i+1));
			}
			
			}
			userpanel.add(new JPanel());
			userpanel.get(i+1).setLayout(null);
			userpanel.get(i+1).setPreferredSize(new Dimension(170,480));
			userpanel.get(i+1).setBackground(Color.white);
			FriendListPanel.add(userpanel.get(i+1));
			
			
			//FriendListPanel.add(name); FriendListPanel.add(profileButton);FriendListPanel.add(musicButton);FriendListPanel.add(chatButton);
			
			
			FriendListPanel.repaint();
			scroll = new JScrollPane(FriendListPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll.setBounds(66,79,328,551);
			//scroll.setLayout(null);
			
			
			//mainFrame.add(mp); mainFrame.add(fp); mainFrame.add(scroll);
			mainFrame.getContentPane().add(scroll);
			mainFrame.getContentPane().add(mp); mainFrame.getContentPane().add(fp);
			//mainFrame.add(scroll);
			//FriendListPanel.repaint();
			//scroll.repaint() ;mp.repaint(); fp.repaint(); 
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "친구 리스트 패널 생성중 문제 발생");
			ex.printStackTrace();
		}
		
		/*
	
			flp.add(name); flp.add(profileButton);  flp.add(musicButton);flp.add(chatButton); 
			}
			else {
				//각 버튼들 +55씩해주면 위치맞음
				//각 버튼들 +55씩해주면 위치맞음
				//각 버튼들 +55씩해주면 위치맞음
				//각 버튼들 +55씩해주면 위치맞음
				name2 = new JLabel("최승표");
				name2.setBounds(77,112,45,33);
				name2.setFont(name2.getFont().deriveFont(14.0f));
				
				profileButton2 = new JButton(new ImageIcon("src/image/여성.jpg"));
				profileButton2.setBounds(19,108,40,40);
				
				chatButton3 = new JButton();
				chatButton3.setBounds(0,100,313,55);
				chatButton3.setContentAreaFilled(false);
				chatButton3.setFocusPainted(false);
				
				musicButton2 = new JButton();
				musicButton2.setBounds(160,115,140,25);
				
				fcount = new JLabel("친구");
				fcount.setBounds(18,77,40,20);
				fcount.setFont(fcount.getFont().deriveFont(12.0f));
				
				flp.add(fcount);
				
				flp.add(profileButton2); flp.add(chatButton3); flp.add(musicButton2); flp.add(name2);
		
			}
		}
		     */  
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
					else if (type == ChatMessage.MsgType.NO_ACT) {
						
					} 
					else if(type == ChatMessage.MsgType.LOGIN) {
						mainFrame();
						frame.dispose();
					}
					else if (type == ChatMessage.MsgType.LOGIN_FAILURE) {
						JOptionPane.showMessageDialog(null, "Login에 실패하였습니다. 다시 로그인하세요");
					}
					else if (type == ChatMessage.MsgType.CONFIRM) {
						pwChangeFrame1.dispose();
						pwChangeFrame2();
					}
					else if (type == ChatMessage.MsgType.CONFIRM_FAILURE) {
						JOptionPane.showMessageDialog(null, "카카오 계정이 존재하지 않습니다. 이메일과 전화번호를 다시 입력하세요");
					}
					else if (type == ChatMessage.MsgType.ADD_FAILURE) {
						JOptionPane.showMessageDialog(null, "카카오 계정이 존재하지 않습니다. 이메일과 전화번호를 다시 입력하세요");
					}
					else if (type == ChatMessage.MsgType.FRIEND_EXIST) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 친구입니다.");
					}
					else if (type == ChatMessage.MsgType.FRIEND_LIST) {
						list.add(new People(message.getName(),message.getGender()));
					}
					
				}
			} catch (Exception ex) {
				System.out.println("유저 스레드 종료"); // 프레임이 종료될 경우 이를 통해 스레드 종료
			}
		}
	}
}
