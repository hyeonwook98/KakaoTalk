import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MessagePanel3 extends JPanel {

	int x;
	int height;
	int width;
	String contents;
	String CHAT_BACK = "src/image/Ã¤ÆÃ¸»Ç³¼± Èò»ö.png";
	ImageIcon chatBackground;
	public MessagePanel3(int x,int width,int height) {
		this.height=height;
		this.width=width;
		add(Box.createVerticalStrut(5));
		
		chatBackground = new ImageIcon(CHAT_BACK);
		setBounds(x,10,width,height);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(chatBackground.getImage(),0,0,352,height,null);
	}
}
