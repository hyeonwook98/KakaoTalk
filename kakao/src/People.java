import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class People {
	

	String name;
    String gender;
    JPanel panel;
    JButton profileButton;
    JButton chatButton;
    JButton musicButton;
    JLabel label_name;
    int x,y,width,height;
	int moveY=55;
	
	public People(String name, String gender) {
		this.name=name;
		this.gender=gender;
		
		panel.setBounds(0,0,315,75);
		panel.setLayout(null);
		label_name = new JLabel(name);
		label_name.setBounds(77,20,45,33);
		label_name.setFont(label_name.getFont().deriveFont(14.0f));
		
		if(gender.equals("害失")) {
			profileButton = new JButton(new ImageIcon("src/image/害失.jpg"));
			profileButton.setBounds(19,10,50,50);
		}
		else if(gender.equals("食失")) {
			profileButton = new JButton(new ImageIcon("src/image/食失.jpg"));
			profileButton.setBounds(19,10,50,50);
		}
		chatButton = new JButton();
		chatButton.setBounds(0,0,313,70);
		chatButton.setContentAreaFilled(false);
		chatButton.setFocusPainted(false);
		
		musicButton = new JButton();
		musicButton.setBounds(160,20,140,30);
		
		panel.add(label_name); panel.add(profileButton); panel.add(chatButton); panel.add(musicButton);
		}
	
}
