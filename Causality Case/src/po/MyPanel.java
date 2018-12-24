package po;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.JPanel;
/**主画板，绘制链接
 * 
 */
@SuppressWarnings("serial")
public class MyPanel extends JPanel{
//	private List<Status> statusList;//状态
	private List<Identical> identicalList;//恒等
	private List<And> andList;//与
	private List<Or> orList;//或
	private List<Not> notList;//非
	private List<Inor> inorList;//包含
	private List<Eor> eorList;//互斥
	private List<Only> onlyList;//唯一
	private List<Require> requireList;//要求

	public List<Identical> getIdenticalList() {
		return identicalList;
	}
	public void setIdenticalList(List<Identical> identicalList) {
		this.identicalList = identicalList;
	}
	public List<And> getAndList() {
		return andList;
	}
	public void setAndList(List<And> andList) {
		this.andList = andList;
	}
	public List<Or> getOrList() {
		return orList;
	}
	public void setOrList(List<Or> orList) {
		this.orList = orList;
	}
	public List<Not> getNotList() {
		return notList;
	}
	public void setNotList(List<Not> notList) {
		this.notList = notList;
	}
	public List<Inor> getInorList() {
		return inorList;
	}
	public void setInorList(List<Inor> inorList) {
		this.inorList = inorList;
	}
	public List<Eor> getEorList() {
		return eorList;
	}
	public void setEorList(List<Eor> eorList) {
		this.eorList = eorList;
	}
	public List<Only> getOnlyList() {
		return onlyList;
	}
	public void setOnlyList(List<Only> onlyList) {
		this.onlyList = onlyList;
	}
	public List<Require> getRequireList() {
		return requireList;
	}
	public void setRequireList(List<Require> requireList) {
		this.requireList = requireList;
	}

	public void paint(Graphics g) {
		super.paint(g);
	     //设置画笔
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		int l = 30;
		g2d.setFont(new Font(null, 0,l));
		//恒等
		if(identicalList != null) {
			 for(Identical identical:identicalList){
				 int x1 = identical.getX1();
				 int y1 = identical.getY1();
				 int x2 = identical.getX2();
				 int y2 = identical.getY2();
				 Line2D line = new Line2D.Double(x1, y1, x2, y2);
				 g2d.draw(line);
			 }
		}
		 //非
		 for(Not not:notList){
			 int x1 = not.getX1();
			 int y1 = not.getY1();
			 int x2 = not.getX2();
			 int y2 = not.getY2();
			 int midy = y1 + (y2-y1)/2;
			 g.drawLine(x1+1, y1+1, x2+1, y2+1);
			 g.drawLine(x1 + (x2 - x1) / 5, midy + 10, x1 + (x2 - x1) * 2 / 5, midy - 10);
			 g.drawLine(x1 + (x2 - x1) * 2 / 5, midy - 10, x1 + (x2 - x1) * 3 / 5, midy + 10);
			 g.drawLine(x1 + (x2 - x1) * 3 / 5, midy + 10, x1 + (x2 - x1) * 4 / 5, midy - 10);
		 }
		//与
		 for(And and : andList){
			 List<Cable> cableList = and.getCableList();
			 int mt = cableList.get(0).getY1(),md = cableList.get(0).getY1(),j = 0,k = 0;
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 g.drawLine(x1+1, y1+1, x2+1, y2+1);
				 if(y1 < mt){
					 mt = y1;
					 j = i;
				 }
				 if(y1 > md){
					 md = y1;
					 k = i;
				 }
			 }
			 Cable tcable = cableList.get(j);
			 Cable dcable = cableList.get(k);
			 int dt = (int)Math.sqrt((tcable.getX2()-tcable.getX1()) * (tcable.getX2() - tcable.getX1()) + (tcable.getY2() - tcable.getY1()) * (tcable.getY2() - tcable.getY1()));
			 int dd = (int)Math.sqrt((dcable.getX2()-dcable.getX1()) * (dcable.getX2() - dcable.getX1()) + (dcable.getY2() - dcable.getY1()) * (dcable.getY2() - dcable.getY1()));
			 int td = (int)Math.sqrt((dcable.getX1()-tcable.getX1()) * (dcable.getX1() - tcable.getX1()) + (dcable.getY1() - tcable.getY1()) * (dcable.getY1() - tcable.getY1()));
//			 int x = tcable.getX1() + (tcable.getX2() - tcable.getX1()) * 7 / 8;
//			 int y = tcable.getY1() + (tcable.getY2() - tcable.getY1()) * 7 / 8;
			 double d = Math.sqrt(((tcable.getX2() - tcable.getX1()) * 1 / 8) * ((tcable.getX2() - tcable.getX1()) * 1 / 8) + ((tcable.getY2() - tcable.getY1()) * 1 / 8) * ((tcable.getY2() - tcable.getY1()) * 1 / 8));
			 
			 double tjd = Math.atan((double)(tcable.getY2() - tcable.getY1())/(tcable.getX2() - tcable.getX1())) / Math.PI * 180;
			 double djd = Math.acos((double)(dt * dt + dd * dd - td * td) / (2 * dt * dd)) / Math.PI * 180;
			 g.drawString("∧",tcable.getX2() - (tcable.getX2() - tcable.getX1()) / 4 ,tcable.getY2() + 15 );
			 g.drawArc(tcable.getX2() - (int)d, tcable.getY2() - (int)d, 2 * (int)d, 2 * (int)d, 180 - (int)tjd,(int)djd);
		 }
		 //或
		 for(Or or : orList) {
			 List<Cable> cableList = or.getCableList();
			 int mt = cableList.get(0).getY1(),md = cableList.get(0).getY1(),j = 0,k = 0;
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 g.drawLine(x1+1, y1+1, x2+1, y2+1);
				 if(y1 < mt){
					 mt = y1;
					 j = i;
				 }
				 if(y1 > md){
					 md = y1;
					 k = i;
				 }
			 }
			 Cable tcable = cableList.get(j);
			 Cable dcable = cableList.get(k);
			 int dt = (int)Math.sqrt((tcable.getX2()-tcable.getX1()) * (tcable.getX2() - tcable.getX1()) + (tcable.getY2() - tcable.getY1()) * (tcable.getY2() - tcable.getY1()));
			 int dd = (int)Math.sqrt((dcable.getX2()-dcable.getX1()) * (dcable.getX2() - dcable.getX1()) + (dcable.getY2() - dcable.getY1()) * (dcable.getY2() - dcable.getY1()));
			 int td = (int)Math.sqrt((dcable.getX1()-tcable.getX1()) * (dcable.getX1() - tcable.getX1()) + (dcable.getY1() - tcable.getY1()) * (dcable.getY1() - tcable.getY1()));
//			 int x = tcable.getX1() + (tcable.getX2() - tcable.getX1()) * 7 / 8;
//			 int y = tcable.getY1() + (tcable.getY2() - tcable.getY1()) * 7 / 8;
			 double d = Math.sqrt(((tcable.getX2() - tcable.getX1()) * 1 / 8) * ((tcable.getX2() - tcable.getX1()) * 1 / 8) + ((tcable.getY2() - tcable.getY1()) * 1 / 8) * ((tcable.getY2() - tcable.getY1()) * 1 / 8));
			 
			 double tjd = Math.atan((double)(tcable.getY2() - tcable.getY1())/(tcable.getX2() - tcable.getX1())) / Math.PI * 180;
			 double djd = Math.acos((double)(dt * dt + dd * dd - td * td) / (2 * dt * dd)) / Math.PI * 180;
			 g.drawString("∨",tcable.getX2() - (tcable.getX2() - tcable.getX1()) / 4 ,tcable.getY2() + 15 );
			 g.drawArc(tcable.getX2() - (int)d, tcable.getY2() - (int)d, 2 * (int)d, 2 * (int)d, 180 - (int)tjd,(int)djd);
		 }
		 //异或
		 for(Eor eor : eorList) {
			 List<Cable> cableList = eor.getCableList();
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 Line2D line = new Line2D.Double(x1, y1, x2, y2);
				 g2d.draw(line);
			 }
			 int x = cableList.get(0).getX1() - 20;
			 int y = cableList.get(0).getY1() + 10;
			 g.drawString("E", x, y);
		 }
		 //包含
		 for(Inor inor :inorList) {
			 List<Cable> cableList = inor.getCableList();
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 Line2D line = new Line2D.Double(x1, y1, x2, y2);
				 g2d.draw(line);
			 }
			 int x = cableList.get(0).getX1() - 20;
			 int y = cableList.get(0).getY1() + 10;
			 g.drawString("I", x, y);
		 }
		 //唯一
		 for(Only only : onlyList) {
			 List<Cable> cableList = only.getCableList();
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 Line2D line = new Line2D.Double(x1, y1, x2, y2);
				 g2d.draw(line);
			 }
			 int x = cableList.get(0).getX1() -20;
			 int y = cableList.get(0).getY1() + 10;
			 g.drawString("O", x, y);
		 }
		 //要求
		 for(Require require : requireList) {
			 List<Cable> cableList = require.getCableList();
			 for(int i = 0;i < cableList.size();i ++ ){
				 Cable cable = cableList.get(i);
				 int x1 = cable.getX1();
				 int y1 = cable.getY1();
				 int x2 = cable.getX2();
				 int y2 = cable.getY2();
				 Line2D line = new Line2D.Double(x1, y1, x2, y2);
				 g2d.draw(line);
			 }
			 int x = cableList.get(0).getX1() - 20;
			 int y = cableList.get(0).getY1() + 10;
			 g.drawString("R", x, y);
		 }
	}
}
