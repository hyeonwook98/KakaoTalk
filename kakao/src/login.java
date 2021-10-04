
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
	ObjectInputStream reader; // ���ſ� ��Ʈ��
	ObjectOutputStream writer; // �۽ſ� ��Ʈ��
	int loginConfirm = 0;
	String user, email, pw, phone, gender;
	Socket sock;
	// ���
	String L_BACK = "src/image/īī���� �α��� ȭ��.png";
	String C_BACK = "src/image/īī������ ���� ȭ��.png";
	String C1_BACK = "src/image/��й�ȣ �缳�� ȭ��1.png";

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

		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		frame.setVisible(true);
	}

	public void mainFrame() {
		mainFrame = new JFrame("īī����");
		// Ŭ���̾�Ʈ ������ â ����
		mainFrame.setBounds(100, 100, 410, 670);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(null);
		mainFrame.setLocationRelativeTo(null);

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

		searchButton = new JButton(new ImageIcon("src/image/���հ˻�.png"));
		searchButton.setBounds(240, 45, 25, 25);
		searchButton.setBorderPainted(false);
		searchButton.setContentAreaFilled(false);
		searchButton.setFocusPainted(false);

		addButton = new JButton(new ImageIcon("src/image/ģ���߰�.png"));
		addButton.setBounds(280, 46, 30, 30);
		addButton.setBorderPainted(false);
		addButton.setContentAreaFilled(false);
		addButton.setFocusPainted(false);

		friend = new JLabel("ģ��");
		friend.setBounds(18, 20, 70, 80);
		friend.setFont(friend.getFont().deriveFont(20.0f)); // ģ�� �۲� ũ�� ����

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

	// �α���ȭ�� ��ư ������
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
			writer.writeObject(new ChatMessage(ChatMessage.MsgType.LOGIN, "", email, pw, "", "", "", "", ""));
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
					else if (type == ChatMessage.MsgType.LOGIN) {
						mainFrame();
						frame.setVisible(false);
					} 
					else if (type == ChatMessage.MsgType.LOGIN_FAILURE) {
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");
					}
					else if (type == ChatMessage.MsgType.CONFIRM) {
						
					}
					else if (type == ChatMessage.MsgType.CONFIRM_FAILURE) {
						JOptionPane.showMessageDialog(null, "���� īī�� �����Դϴ�. �̸��ϰ� ��ȭ��ȣ�� �ٽ� �Է��ϼ���");
					}
				}
			} catch (Exception ex) {
				System.out.println("���� ������ ����"); // �������� ����� ��� �̸� ���� ������ ����
			}
		}
	}
}
