import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MessagePanel1 extends JPanel{
	String CHAT_BACK = "src/image/ä��â �г�2 �����ϴû�.png";
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
