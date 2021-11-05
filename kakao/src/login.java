
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class login {

	JFrame frame, mainFrame, creationFrame, pwChangeFrame1 , pwChangeFrame2 , friendAddFrame, chatFrame;
	LoginPanel lp;
	FriendPanel fp; FriendListPanel flp; UserPanel up; ChatPanel chatPanel;
	MainPanel mp; ChatPanel1 chatPanel1; ChatPanel2 chatPanel2; ChatPanel3 chatPanel3;
	CreationPanel cp;
	JPanel FriendListPanel,ChatListPanel,userPanel,friendCountPanel,nullPanel ;
	pwChangePanel1 cp1; pwChangePanel2 cp2; friendAddPanel ap;
	JTextField id, cName, cEmail, cPw, cPhone, pwEmail, pwPhone, aName , aPhone;
	JPasswordField password, changePw, changePwRe;
	JLabel friend,chat,name2,friendCount ;
	ImageIcon loginBackground;	ImageIcon createBackground;	ImageIcon changeBackground1; ImageIcon changeBackground2; ImageIcon addBackground;
	ImageIcon chatPanel1Background;  ImageIcon chatPanel2Background; ImageIcon chatPanel3Background;
	JButton loginButton;	JButton createButton;	JButton pwChangeButton; 	JButton friendButton1; 	JButton friendButton2; 	JButton ChatButton1; 	JButton ChatButton2;
	JButton PlusButton1;	JButton PlusButton2; 	JButton emoticonButton1; 	JButton emoticonButton2; 	JButton noticeButton1; 	JButton noticeButton2;
	JButton settingButton1;	JButton settingButton2;	JButton searchButton1; JButton searchButton2;	JButton addButton; 	JButton confirmButton; JButton nextButton; JButton successButton; JButton fAddButton;
	JButton profileButton2;JButton musicButton2;JButton chatButton3;  JButton chatProfile; JLabel chatLabel;
	JRadioButton men, women;
	ObjectInputStream reader; // ���ſ� ��Ʈ��
	ObjectOutputStream writer; // �۽ſ� ��Ʈ��
	Thread readerThread;
	JScrollPane scroll1,scroll2,chat_scroll;
	JTextArea outgoing;
	ArrayList<Friend> list;  
	ArrayList<JButton> chat_Button;
	ArrayList<JButton> music_Button;
	ArrayList<JButton> profile_Button;
	ArrayList<JLabel> label_name ;
	ArrayList<JButton> chat_ProfileButton ;                    //ä�ù濡�� �����ʰ� �󺧻���� �ؿ���
    ArrayList<JLabel> chat_Label = new ArrayList<>();          //ä�ù濡�� �����ʰ� �󺧻����
	JPanel userpanel; JButton profileButton ; 
	JButton musicButton; JButton chatButton; JLabel name;
	int x,y,width,height;
	int loginConfirm = 0; 
	//int i=0;
	Friend as;
	String loginUser,userGender,userPhone; //�α����� ������ ���� ����
	int listIndex; //ä���ϰ��� �ϴ� ģ�� ��ư �������� list�ε��� ����
	String user, email, pw,pw2, phone, gender;
	Socket sock;
	// ���
	String L_BACK = "src/image/īī���� �α��� ȭ��.png";
	String C_BACK = "src/image/īī������ ���� ȭ��.png";
	String C1_BACK = "src/image/��й�ȣ �缳�� ȭ��1.png";
	String C2_BACK = "src/image/��й�ȣ �缳�� ȭ��2.png";
	String A_BACK = "src/image/ģ���߰� ȭ��.png";
	String CP1_BACK = "src/image/ä��â �г�1 �����ϴû�.png";
	String CP2_BACK = "src/image/ä��â �г�2 �����ϴû�.png";
	String CP3_BACK = "src/image/ä��â �г�3.png";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		login login = new login();
		login.go();

	}

	public void go() {
		frame = new JFrame("īī����");
		// Ŭ���̾�� ������ â ����
		frame.setBounds(100, 100, 370, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);

		setUpNetworking();
		readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		list = new ArrayList<Friend>();
		id = new JTextField();
		password = new JPasswordField();
		id.setBounds(58, 220, 240, 39);
		password.setBounds(58, 258, 240, 39);
		password.setEchoChar('��'); // ��й�ȣ�� �Է��� *������� ǥ�õǵ��� ����

		loginButton = new JButton(new ImageIcon("src/image/�α��� ��ư.png"));
		createButton = new JButton(new ImageIcon("src/image/�������� ��ư.png"));
		pwChangeButton = new JButton(new ImageIcon("src/image/��й�ȣ �缳�� ��ư.png"));
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
		mainFrame = new JFrame("īī����");
		// Ŭ���̾�Ʈ ������ â ����
		mainFrame.setBounds(100, 100, 410, 670);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setLocationRelativeTo(null);

		chat_Button = new ArrayList<>();
		music_Button = new ArrayList<>();
		profile_Button= new ArrayList<>();
		label_name= new ArrayList<>();
		chat_ProfileButton = new ArrayList<>();
		chat_Label = new ArrayList<>();
		///////////////////////////////////////////////////////�����г�(��Ʈ�� �г�)//////////////
		mp = new MainPanel();
		mp.setBounds(0, 0, 66, 630);

		friendButton2 = new JButton(new ImageIcon("src/image/ģ��2.png"));
		friendButton2.setBounds(14, 40, 40, 40);
		// friendButton1.setBorderPainted(false);
		friendButton2.setContentAreaFilled(false);
		friendButton2.setFocusPainted(false);
		
		friendButton1 = new JButton(new ImageIcon("src/image/ģ��1.png"));
		friendButton1.setBounds(14, 40, 40, 40);
		// friendButton1.setBorderPainted(false);
		friendButton1.setContentAreaFilled(false);
		friendButton1.setFocusPainted(false);

		ChatButton1 = new JButton(new ImageIcon("src/image/ä��1.png"));
		ChatButton1.setBounds(14, 100, 40, 40);
		// ChatButton1.setBorderPainted(false);
		ChatButton1.setContentAreaFilled(false);
		ChatButton1.setFocusPainted(false);
		
		ChatButton2 = new JButton(new ImageIcon("src/image/ä��2.png"));
		ChatButton2.setBounds(14, 100, 40, 40);
		// ChatButton1.setBorderPainted(false);
		ChatButton2.setContentAreaFilled(false);
		ChatButton2.setFocusPainted(false);

		PlusButton1 = new JButton(new ImageIcon("src/image/������1.png"));
		PlusButton1.setBounds(14, 160, 40, 40);
		// PlusButton1.setBorderPainted(false);
		PlusButton1.setContentAreaFilled(false);
		PlusButton1.setFocusPainted(false);

		emoticonButton1 = new JButton(new ImageIcon("src/image/�̸�Ƽ�� ��1.png"));
		emoticonButton1.setBounds(14, 419, 40, 40);
		// emoticonButton1.setBorderPainted(false);
		emoticonButton1.setContentAreaFilled(false);
		emoticonButton1.setFocusPainted(false);

		noticeButton1 = new JButton(new ImageIcon("src/image/�˸�1.png"));
		noticeButton1.setBounds(14, 459, 40, 40);
		// noticeButton1.setBorderPainted(false);
		noticeButton1.setContentAreaFilled(false);
		noticeButton1.setFocusPainted(false);

		settingButton1 = new JButton(new ImageIcon("src/image/����1.png"));
		settingButton1.setBounds(14, 499, 40, 40);
		// settingButton1.setBorderPainted(false);
		settingButton1.setContentAreaFilled(false);
		settingButton1.setFocusPainted(false);
		
		mp.add(ChatButton1);
		mp.add(PlusButton1);
		mp.add(emoticonButton1);
		mp.add(noticeButton1);
		mp.add(settingButton1);
		mp.add(friendButton2);
		mp.add(friendButton1); friendButton1.setVisible(false); friendButton1.setEnabled(false);
		mp.add(ChatButton2); ChatButton2.setVisible(false); ChatButton2.setEnabled(false);
		
		/////////////////////////////////////////////////////ģ���г�///////////////
		fp = new FriendPanel();
		fp.setBounds(66, 0, 328, 79);
		fp.setBackground(Color.white);
		
		friend = new JLabel("ģ��");
		friend.setBounds(18, 20, 70, 80);
		friend.setFont(friend.getFont().deriveFont(20.0f)); // ģ�� �۲� ũ�� ����
		
		searchButton1 = new JButton(new ImageIcon("src/image/���հ˻�.png"));
		searchButton1.setBounds(240, 45, 25, 25);
		searchButton1.setBorderPainted(false);
		searchButton1.setContentAreaFilled(false);
		searchButton1.setFocusPainted(false);
		
		addButton = new JButton(new ImageIcon("src/image/ģ���߰�.png"));
		addButton.setBounds(280, 46, 30, 30);
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setFocusPainted(false);
		
		fp.add(friend);
		fp.add(searchButton1);
		fp.add(addButton);
		
		
		///////////////////////////////////ģ������г�////////////
	    FriendListPanel = new JPanel();
		FriendListPanel.setLayout(new BoxLayout(FriendListPanel,BoxLayout.Y_AXIS));
		FriendListPanel.setBounds(66,79,326,551);;
		FriendListPanel.setBackground(Color.white);
		

		/////////////////////////////////////////////////////ä���г�
		chatPanel = new ChatPanel(); 
		chatPanel.setBounds(66, 0, 328, 79);
		chatPanel.setBackground(Color.white);
		
		chat = new JLabel("ä��");
		chat.setBounds(18, 20, 70, 80);
		chat.setFont(friend.getFont().deriveFont(20.0f));//ä�� �۲� ũ�� ����

		searchButton2 = new JButton(new ImageIcon("src/image/���հ˻�.png"));
		searchButton2.setBounds(240, 45, 25, 25);
		searchButton2.setBorderPainted(false);
		searchButton2.setContentAreaFilled(false);
		searchButton2.setFocusPainted(false);

		chatPanel.add(chat);
		chatPanel.add(searchButton2);
		
		////////////////////////////////////////////////ä�ø���г�
		ChatListPanel = new JPanel();
		ChatListPanel.setLayout(new BoxLayout(ChatListPanel,BoxLayout.Y_AXIS));
		ChatListPanel.setBounds(66,79,326,551);;
		ChatListPanel.setBackground(Color.white);
		
		///////////////////////////////////////////////
		ButtonListener b1 = new ButtonListener();
		addButton.addActionListener(b1);
		ChatButton1.addActionListener(b1);
		friendButton1.addActionListener(b1);
		
		processUserList();
		//processChatList();
		
		mainFrame.getContentPane().add(mp);
		mainFrame.setVisible(true);
	}

	public void creationFrame() {
		creationFrame = new JFrame("īī������ ����");
		// Ŭ���̾�� ������ â ����
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

		ButtonGroup gChoice = new ButtonGroup(); // ���� ��ư�� �׷�ȭ �ϱ����� ��ü ����
		men = new JRadioButton("����"); // ���� ��ư ����
		women = new JRadioButton("����"); // ���� ��ư ����

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
		pwChangeFrame1=new JFrame("��й�ȣ �缳��");
		// Ŭ���̾�� ������ â ����
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
		pwChangeFrame2=new JFrame("��й�ȣ �缳��");
		// Ŭ���̾�� ������ â ����
		pwChangeFrame2.setBounds(89, 100, 387, 422);
		//creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		pwChangeFrame2.getContentPane().setLayout(null);
		pwChangeFrame2.setLocationRelativeTo(null);
        // frame.setResizable(false);      
       
		changePw = new JPasswordField();
		changePwRe = new JPasswordField();
		
		changePw.setEchoChar('��');
		changePwRe.setEchoChar('��');
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
		friendAddFrame=new JFrame("ģ�� �߰�");
		// Ŭ���̾�� ������ â ����
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
	public void chatFrame(String name, String gender ,int listindex) {
		chatFrame=new JFrame();
		// Ŭ���̾�� ������ â ����
		chatFrame.setBounds(1150, 210, 368, 634);
		//chatFrame.setPreferredSize(new Dimension(368,612));
		//creationFrame.setDefaultCloseOperation(creationFrame.EXIT_ON_CLOSE);
		chatFrame.getContentPane().setLayout(null);
		
		chatPanel1 = new ChatPanel1();
		chatPanel1.setBounds(0,0,353,76);
		//chatPanel1.setPreferredSize(new Dimension(353,76));
		
		chatPanel2 = new ChatPanel2();
		chatPanel2.setBounds(0,72,352,450);
		
		chatPanel3 = new ChatPanel3();
		chatPanel3.setBounds(0,493,352,102);
		
		if(name.equals("")||gender.equals("")) {
		chatPanel1.add(chat_Label.get(listindex));
		chatPanel1.add(chat_ProfileButton.get(listindex));
		}
		else {
			if(gender.equals("����")) {
				chatProfile = new JButton(new ImageIcon("src/image/����.jpg"));
				chatProfile.setBounds(15,14,45,45);
			}
			else if(gender.equals("����")) {
				chatProfile= new JButton(new ImageIcon("src/image/����.jpg"));
				chatProfile.setBounds(15,14,45,45);
			}
			chatLabel = new JLabel(name);
			chatLabel.setBounds(74,10,45,33);
			chatPanel1.add(chatProfile);
			chatPanel1.add(chatLabel);
		}
		
		outgoing = new JTextArea();
		outgoing.setBounds(10,10,265,57);
		outgoing.setLineWrap(true);
        outgoing.setWrapStyleWord(true);
        outgoing.setEditable(true);
        
        chat_scroll=new JScrollPane(outgoing,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	chat_scroll.setBorder(null);
        chat_scroll.setBounds(10,10,265,55);
        chatPanel3.add(chat_scroll);
        
		chatFrame.getContentPane().add(chatPanel1);
		chatFrame.getContentPane().add(chatPanel2);
		chatFrame.getContentPane().add(chatPanel3);
		
		
		
		
		chatFrame.setVisible(true);
	}

	///////////////////////////////////////// �г�////////////////////////////////////////////////

	// �α��� �г�
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

	// ���� �г�
	class MainPanel extends JPanel {
		public MainPanel() {
			setLayout(null);
		}
	}

	// ģ�� �г�
	class FriendPanel extends JPanel {
		public FriendPanel() {
			setLayout(null);
		}
	}
	//ä�� �г�
	class ChatPanel extends JPanel{
		public ChatPanel() {
			setLayout(null);
		}
	}

	// īī������ ���� �г�
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

	// �����ȣ �缳�� �г�1
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
	// �����ȣ �缳�� �г�2
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
		//������ ģ����� �г�
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
		
		//ģ���߰� �г�
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
		//ä�ù��г� 1
		class ChatPanel1 extends JPanel{
			public ChatPanel1() {
				setLayout(null);
				chatPanel1Background = new ImageIcon(CP1_BACK);
			}
			public void paintComponent(Graphics g) {
				g.drawImage(chatPanel1Background.getImage(), 0, 0, 353, 73, null);
			}
		}
		//ä�ù��г� 2
		class ChatPanel2 extends JPanel{
			public ChatPanel2() {
				setLayout(null);
				chatPanel2Background = new ImageIcon(CP2_BACK);
			}
			public void paintComponent(Graphics g) {
				g.drawImage(chatPanel2Background.getImage(), 0, 0, 352, 420, null);
			}
		}
		//ä�ù��г� 3
		class ChatPanel3 extends JPanel{
			public ChatPanel3() {
				setLayout(null);
				chatPanel3Background = new ImageIcon(CP3_BACK);
			}
			public void paintComponent(Graphics g) {
				g.drawImage(chatPanel3Background.getImage(), 0, 0, 352, 104, null);
			}
		}

	private void setUpNetworking() {
		try {
			// sock = new Socket("220.69.203.11", 5000); // �������� ��ǻ��
			sock = new Socket("127.0.0.1", 6000); // ���� ����� ���� ��Ʈ�� 5000�� ���Ű�� ��
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "�������ӿ� �����Ͽ����ϴ�. ������ �����մϴ�.");
			ex.printStackTrace();
			frame.dispose(); // ��Ʈ��ũ�� �ʱ� ���� �ȵǸ� Ŭ���̾�Ʈ ���� ����
		}
	} // close setUpNetworking

	// ��ư ������
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
			if(e.getSource() == friendButton2) {
				
			}
            if(e.getSource() == ChatButton1) {
            	//�гμ���
            	fp.setVisible(false);
            	fp.setEnabled(false);
            	chatPanel.setVisible(true);
            	chatPanel.setEnabled(true);
            	
            	processChatList();
            	
            	//��ư����
            	ChatButton1.setVisible(false);
            	ChatButton1.setEnabled(false);
            	ChatButton2.setVisible(true);
            	ChatButton2.setEnabled(true);
            	friendButton2.setVisible(false);
            	friendButton2.setEnabled(false);
            	friendButton1.setVisible(true);
            	friendButton1.setEnabled(true);
            	//mainFrame.repaint();
            	mp.repaint();
			}
            if(e.getSource() == friendButton1) {
            	//�гμ���
                fp.setVisible(true);
                fp.setEnabled(true);
                chatPanel.setVisible(false);
            	chatPanel.setEnabled(false);
            	
            	scroll1.setVisible(true);
            	scroll1.setEnabled(true);
            	scroll2.setVisible(false);
            	scroll2.setEnabled(false);
            	
            	//��ư����
            	friendButton2.setVisible(true);
            	friendButton2.setEnabled(true);
            	friendButton1.setVisible(false);
            	friendButton1.setEnabled(false);
            	ChatButton1.setVisible(true);
            	ChatButton1.setEnabled(true);
            	ChatButton2.setVisible(false);
            	ChatButton2.setEnabled(false);
            	mp.repaint();
			}
          //���ο��� ä�ú�����
            if(e.getSource() ==chatButton){
            	chatFrame(loginUser,userGender,0);
            }
            //ģ������ ä�ú�����
            for(int i=0;i<list.size();i++) {
                if(e.getSource() == chat_Button.get(i)) {
            	listIndex=i; //�ش� list�ε����� ����
            	chatFrame("","",listIndex); //�����ӿ� �ش� list�ε��� ��ȣ�� �Ѱ���
            }
            }
            
            
		}
	}

	// DB�� �����ؾ��ϴ� ��ư ������
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
						JOptionPane.showMessageDialog(null, "��й�ȣ�� ����Ǿ����ϴ�.");
						pwChangeFrame2.dispose();;
					}
					else {
						JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�. ��й�ȣ�� �ٽ� �Է��ϼ���");
					}
				}
				else 
					JOptionPane.showMessageDialog(null, "��й�ȣ�� 8~32�ڸ��� �������ּ���");
			}
			if (e.getSource() == fAddButton) {
				phone = aPhone.getText();
				user = aName.getText();
				processFriendAdd();
			}
		}

	}

	////////////////////////////// ������ ������ ������ �޽���/////////////////////////////////
	// īī�� ���� ���� ó��
	public void processCreation() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(
					new ChatMessage(ChatMessage.MsgType.CREATION, user, email, pw, phone, gender, "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "īī�� ���� ���� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}


	// īī���� �α��� ó��
	public void processLogin() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN_TRY, "", email, pw, "", "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "īī���� �α��� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}
	//��й�ȣ �缳�� ó��1
	public void processPwChange1() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CONFIRM, "", email, "", phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "īī���� ����Ȯ�� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}
	//��й�ȣ ����
	public void processPwChange2() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.CHANGE, "", email, pw2, phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "��й�ȣ ���� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}

	public void processFriendAdd() {
		// TODO Auto-generated method stub
		try {
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.ADD, user, "", "", phone, "", "", "", ""));
			writer.flush();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ģ���߰� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
			ex.printStackTrace();
		}
	}
	public void processUserList() {
		try {
			//////////////////////////////////////////�����г�
			userPanel=new JPanel();
			userPanel.setLayout(null);
			userPanel.setPreferredSize(new Dimension(170,70));
			userPanel.setBackground(Color.white);
			
			ButtonListener b1 = new ButtonListener();
			
			name = new JLabel(loginUser);
			name.setBounds(77,20,45,33);
			name.setFont(name.getFont().deriveFont(14.0f));
			
			if(userGender.equals("����")) {
				profileButton = new JButton(new ImageIcon("src/image/����.jpg"));
				profileButton.setBounds(19,10,50,50);
			}
			else if(userGender.equals("����")) {
				profileButton = new JButton(new ImageIcon("src/image/����.jpg"));
				profileButton.setBounds(19,10,50,50);
			}
			
			chatButton = new JButton();
			chatButton.setBounds(0,0,313,70);
			chatButton.setContentAreaFilled(false);
			chatButton.setFocusPainted(false);
			chatButton.addActionListener(b1);
			
			musicButton=new JButton() ;
			musicButton.setBounds(160,20,140,30);
			
			userPanel.add(name);userPanel.add(musicButton);userPanel.add(profileButton);userPanel.add(chatButton);
			FriendListPanel.add(userPanel);
			
			/////////////////////////////////////////ģ��ī��Ʈ�г�
			friendCountPanel = new JPanel();
			friendCountPanel.setLayout(null);
			friendCountPanel.setPreferredSize(new Dimension(170,40));
			friendCountPanel.setBackground(Color.white);
			
			friendCount = new JLabel("ģ��  "+list.size());
			friendCount.setBounds(20,10,40,20);
			friendCount.setFont(friendCount.getFont().deriveFont(10.0f));
			
			friendCountPanel.add(friendCount);
			FriendListPanel.add(friendCountPanel);
			
			/////////////////////////////////////////ģ���г� //
			for(int i=0;i<list.size();i++) {
				//list.get(i).chatButton.addActionListener(b1);//���⹮��
				label_name.add(new JLabel(list.get(i).name));
				label_name.get(i).setBounds(66,16,45,33);
				label_name.get(i).setFont(label_name.get(i).getFont().deriveFont(14.0f));
				
				chat_Label.add(new JLabel(list.get(i).name));
				chat_Label.get(i).setBounds(74,10,45,33);
				chat_Label.get(i).setFont(chat_Label.get(i).getFont().deriveFont(13.0f));
				
				if(list.get(i).gender.equals("����")) {
					profile_Button.add(new JButton(new ImageIcon("src/image/����.jpg")));
					profile_Button.get(i).setBounds(19,13,40,40);
					chat_ProfileButton.add(new JButton(new ImageIcon("src/image/����.jpg")));
					chat_ProfileButton.get(i).setBounds(15,14,45,45);
				}
				else if(list.get(i).gender.equals("����")) {
					profile_Button.add(new JButton(new ImageIcon("src/image/����.jpg")));
					profile_Button.get(i).setBounds(19,13,40,40);
					chat_ProfileButton.add(new JButton(new ImageIcon("src/image/����.jpg")));
					chat_ProfileButton.get(i).setBounds(15,14,45,45);
				}
				
				chat_Button.add(new JButton());
				chat_Button.get(i).setBounds(0,0,313,65);
				chat_Button.get(i).setContentAreaFilled(false);
				chat_Button.get(i).setFocusPainted(false);
				chat_Button.get(i).addActionListener(b1);
				
				music_Button.add(new JButton()) ;
				music_Button.get(i).setBounds(160,18,140,30);
				
				list.get(i).add(label_name.get(i));
				list.get(i).add(profile_Button.get(i));
				list.get(i).add(music_Button.get(i));
				list.get(i).add(chat_Button.get(i));
				
				FriendListPanel.add(list.get(i));
			}
			
			//////////////////////////////////////////null�г�
			nullPanel = new JPanel();
			nullPanel.setLayout(null);
			nullPanel.setPreferredSize(new Dimension(170,480));
			nullPanel.setBackground(Color.white);
			
			FriendListPanel.add(nullPanel);
			
			FriendListPanel.repaint();
			scroll1 = new JScrollPane(FriendListPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroll1.setBounds(66,79,328,553);
			//scroll.setLayout(null);

			mainFrame.getContentPane().add(scroll1);
			 mainFrame.getContentPane().add(fp);
			
			
			
		}catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "ģ�� ����Ʈ �г� ������ ���� �߻�");
			ex.printStackTrace();
		}
	}
	public void processChatList() { 
		scroll1.setVisible(false);
    	scroll1.setEnabled(false);
    	
		scroll2 = new JScrollPane(ChatListPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(66,79,328,553);
		
		mainFrame.getContentPane().add(scroll2);  scroll2.setVisible(true); scroll2.setEnabled(true);
		mainFrame.getContentPane().add(chatPanel); chatPanel.setVisible(true); chatPanel.setEnabled(true);
	}

	/////////////////////////////// ������ ������ ���� �޴� �޽���///////////////////////////////
	public class IncomingReader implements Runnable {
		public void run() {
			ChatMessage message;
			ChatMessage.MsgType type;
			try {
				while (true) {
					message = (ChatMessage) reader.readObject(); // �����α� ������ �޽��� ���
					type = message.getType();
					if (type == ChatMessage.MsgType.CREATION_SUCCESS) {
						creationFrame.dispose();
					} 
					else if (type == ChatMessage.MsgType.CREATION_FAILURE) {
						JOptionPane.showMessageDialog(null, "�̹� ���Ե� �̸����Դϴ�. �ٸ� �̸����� �Է��ϼ���");
					} 
					else if (type == ChatMessage.MsgType.NO_ACT) {
						
					} 
					else if(type == ChatMessage.MsgType.LOGIN) {
						mainFrame();
						frame.dispose();
					}
					else if (type == ChatMessage.MsgType.LOGIN_FAILURE) {
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");
					}
					else if (type == ChatMessage.MsgType.CONFIRM) {
						pwChangeFrame1.dispose();
						pwChangeFrame2();
					}
					else if (type == ChatMessage.MsgType.CONFIRM_FAILURE) {
						JOptionPane.showMessageDialog(null, "īī�� ������ �������� �ʽ��ϴ�. �̸��ϰ� ��ȭ��ȣ�� �ٽ� �Է��ϼ���");
					}
					else if (type == ChatMessage.MsgType.ADD) {
						friendAddFrame.dispose();
						mainFrame();
					}
					else if (type == ChatMessage.MsgType.ADD_FAILURE) {
						JOptionPane.showMessageDialog(null, "īī�� ������ �������� �ʽ��ϴ�. �̸��ϰ� ��ȭ��ȣ�� �ٽ� �Է��ϼ���");
					}
					else if (type == ChatMessage.MsgType.FRIEND_EXIST) {
						JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ģ���Դϴ�.");
					}
					else if (type == ChatMessage.MsgType.FRIEND_LIST) {
						list.add(new Friend(message.getName(),message.getGender(),message.getPhone()));
					}
					else if (type == ChatMessage.MsgType.USER_INFO) {
						loginUser=message.getName(); userGender=message.getGender(); userPhone=message.getPhone();
					}
					
				}
			} catch (Exception ex) {
				System.out.println("���� ������ ����"); // �������� ����� ��� �̸� ���� ������ ����
			}
		}
	}
}
