import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Friend extends JPanel {
	

	String name;
    String gender;
    String phone;
    //ArrayList<People> friendPanel;
    ArrayList<JButton> chatButton = new ArrayList<>();
    ArrayList<JButton> musicButton = new ArrayList<>();
    ArrayList<JButton> profileButton= new ArrayList<>();
    ArrayList<JLabel> label_name= new ArrayList<>();
    ArrayList<JButton> chatProfileButton = new ArrayList<>(); //채팅방에서 프로필과 라벨사용함 밑에도
    ArrayList<JLabel> chatLabel = new ArrayList<>();          //채팅방에서 프로필과 라벨사용함
    int count=0;
	
	public Friend(String name, String gender, String phone) {
		this.name=name;
		this.gender=gender;
		this.phone=phone;
		
		setLayout(null);
		setPreferredSize(new Dimension(170,65));
		setBackground(Color.white);
		
		label_name.add(new JLabel(name));
		label_name.get(count).setBounds(66,16,45,33);
		label_name.get(count).setFont(label_name.get(count).getFont().deriveFont(14.0f));
		
		chatLabel.add(new JLabel(name));
		chatLabel.get(count).setBounds(74,10,45,33);
		chatLabel.get(count).setFont(chatLabel.get(count).getFont().deriveFont(13.0f));
		
		if(gender.equals("남성")) {
			profileButton.add(new JButton(new ImageIcon("src/image/남성.jpg")));
			profileButton.get(count).setBounds(19,13,40,40);
			chatProfileButton.add(new JButton(new ImageIcon("src/image/남성.jpg")));
			chatProfileButton.get(count).setBounds(15,14,45,45);
		}
		else if(gender.equals("여성")) {
			profileButton.add(new JButton(new ImageIcon("src/image/여성.jpg")));
			profileButton.get(count).setBounds(19,13,40,40);
			chatProfileButton.add(new JButton(new ImageIcon("src/image/여성.jpg")));
			chatProfileButton.get(count).setBounds(15,14,45,45);
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
