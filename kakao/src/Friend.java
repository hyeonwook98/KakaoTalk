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
    int number;
    //ArrayList<People> friendPanel;
    ArrayList<JButton> chatButton = new ArrayList<>();
    ArrayList<JButton> musicButton = new ArrayList<>();
    ArrayList<JButton> profileButton= new ArrayList<>();
    ArrayList<JLabel> label_name= new ArrayList<>();
    ArrayList<JButton> chatProfileButton = new ArrayList<>(); //ä�ù濡�� �����ʰ� �󺧻���� �ؿ���
    ArrayList<JLabel> chatLabel = new ArrayList<>();          //ä�ù濡�� �����ʰ� �󺧻����
	
	public Friend(String name, String gender, int number) {
		this.name=name;
		this.gender=gender;
		this.number=number;
		
		setLayout(null);
		setPreferredSize(new Dimension(170,65));
		setBackground(Color.white);
		}
	
}