import javax.swing.JLabel;

public class MessageLabel extends JLabel {

	String contents;
	public MessageLabel(String contents) {
		this.contents=contents;
		setText(this.contents);
		setFont(getFont().deriveFont(13.0f));
	}
}
