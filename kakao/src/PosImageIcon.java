
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

// ImageIcon�� ��ǥ�� ��ġ�� �ο��ϰ��� ImageIcon Ŭ������ �����
public class PosImageIcon extends ImageIcon {
	String img;
	int x;				// ImageIcon�� X��ǥ
	int y;				// ImageIcon�� y��ǥ
	
	int width;			// ImageIcon�� ����
	int height;			// ImageIcon�� ����
	
	
	public PosImageIcon(int x,int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
}
