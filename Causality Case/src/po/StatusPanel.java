package po;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class StatusPanel extends JPanel{
	private Status status;
	private int type;
	private int index;//未添加0，已添加1
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setPart(Status s,int t){
		status = s;
		type = t;
	}
	public void paint(Graphics g) {
	     super.paint(g);
	     //设置画笔
		 g.setColor(Color.BLACK);
		 int l = 25;
		 g.setFont(new Font(null, 0,l));
		 if(type == 0){
			 int x = status.getX();
			 int y = status.getY();
			 int r = status.getR();
			 String str = "";
			 str = status.getName();
			 int strWidth = g.getFontMetrics().stringWidth(str);
			 g.drawOval(1, 1, r*2, r*2);
			 g.drawString(str,r-strWidth/2,r+l/2);
		 }
	}
}
