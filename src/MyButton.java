import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyButton extends JButton {
	private Color txtColor = Color.white;
	private Color bgColor = Color.blue;
	public MyButton(String Text) {
		super(Text);
		setBorderPainted(false);
		setOpaque(false);
	}
	public void setTxtColor(Color c) {
		txtColor = c;
	}
	public void setBgColor(Color c) {
		bgColor = c;
	}
	@Override
	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		
		g.setColor(bgColor);
		g.fillRoundRect(0, 0, w, h, 100, 100);
		
		g.setColor(txtColor);
		g.drawString(getText(),(int)getSize().getWidth()/2-12, getHeight()/2+5);
		
	}

}
