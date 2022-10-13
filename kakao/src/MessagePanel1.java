import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MessagePanel1 extends JPanel{
	String CHAT_BACK = "src/image/채팅창 패널2 연한하늘색.png";
	ImageIcon chatBackground;
	int height;

	public MessagePanel1(int h) {
		this.height=h;
		setLayout(null);
		chatBackground = new ImageIcon(CHAT_BACK);
		setPreferredSize(new Dimension(352,height));
		
	}
	public void paintComponent(Graphics g) {
		g.drawImage(chatBackground.getImage(),0,0,352,height,null);
	}
}
