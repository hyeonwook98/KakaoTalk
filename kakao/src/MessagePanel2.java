import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MessagePanel2 extends JPanel {

	int x;
	int height;
	int width;
	String contents;
	String CHAT_BACK = "src/image/채팅말풍선 노란색.png";
	ImageIcon chatBackground;
	public MessagePanel2(int x,int width,int height) {
		this.height=height;
		this.width=width;
		add(Box.createVerticalStrut(5));
		//setLayout(new GridLayout());
		//this.contents=contents;
		
		chatBackground = new ImageIcon(CHAT_BACK);
		setBounds(x,10,width,height);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(chatBackground.getImage(),0,0,352,height,null);
	}
}