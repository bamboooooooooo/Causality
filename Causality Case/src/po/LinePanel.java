package po;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LinePanel extends Canvas{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private String type;//连接类型
	private int Indx;//连接编号
	private int t;//0未添加，1已添加
	
	public LinePanel(int x1,int y1,int x2,int y2,String type,int Indx,int t) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.type = type;
		this.Indx = Indx;
		this.t = t;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIndx() {
		return Indx;
	}
	public void setIndx(int indx) {
		Indx = indx;
	}
	public LinePanel() {
		// TODO Auto-generated constructor stub
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public void paint(Graphics g) {
	     super.paint(g);
	     //设置画笔
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		int l = 30;
		g2d.setFont(new Font(null, 0,l));
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		g2d.draw(line);
	}
}
