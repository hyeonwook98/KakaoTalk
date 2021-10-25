import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class People extends JPanel {
	

	String name;
    String gender;
    //ArrayList<People> friendPanel;
    ArrayList<JButton> chatButton = new ArrayList<>();
    ArrayList<JButton> musicButton = new ArrayList<>();
    ArrayList<JButton> profileButton= new ArrayList<>();
    ArrayList<JLabel> label_name= new ArrayList<>();
    int count=0;
	
	public People(String name, String gender) {
		this.name=name;
		this.gender=gender;
		
		setLayout(null);
		setPreferredSize(new Dimension(170,65));
		setBackground(Color.white);
		
		label_name.add(new JLabel(name));
		label_name.get(count).setBounds(77,16,45,33);
		label_name.get(count).setFont(label_name.get(count).getFont().deriveFont(14.0f));
		
		
		if(gender.equals("害失")) {
			profileButton.add(new JButton(new ImageIcon("src/image/害失.jpg")));
			profileButton.get(count).setBounds(19,13,40,40);
		}
		else if(gender.equals("食失")) {
			profileButton.add(new JButton(new ImageIcon("src/image/食失.jpg")));
			profileButton.get(count).setBounds(19,13,40,40);
		}
		
		chatButton.add(new JButton());
		chatButton.get(count).setBounds(0,0,313,65);
		chatButton.get(count).setContentAreaFilled(false);
		chatButton.get(count).setFocusPainted(false);
		
		musicButton.add(new JButton()) ;
		musicButton.get(count).setBounds(160,18,140,30);
		
		add(label_name.get(count));
		add(profileButton.get(count));
		add(musicButton.get(count));
		add(chatButton.get(count));
		
		add(label_name.get(count));
		
		count++;
		}
}
