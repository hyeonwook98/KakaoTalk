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
		}
	public void ButtonCreation() {
		
	}
}
