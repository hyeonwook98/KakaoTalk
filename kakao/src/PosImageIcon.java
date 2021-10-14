
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

// ImageIcon에 좌표의 위치를 부여하고자 ImageIcon 클래스를 상속함
public class PosImageIcon extends ImageIcon {
	String img;
	int x;				// ImageIcon의 X좌표
	int y;				// ImageIcon의 y좌표
	
	int width;			// ImageIcon의 넓이
	int height;			// ImageIcon의 높이
	
	
	public PosImageIcon(int x,int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
}
