package DoItem;

import java.util.Iterator;
import java.util.List;

import po.SignPoint;
import po.Status;

public class PointHandle {
	private SignPoint p1,p2,p3,p4;
	
	public void addPoint(List<SignPoint> pointList,int x1,int y1,int x2,int y2,String type,int i) {
		p1 = new SignPoint(x1,y1 - 20,type,i);
		p2 = new SignPoint(x1 - 20,y1,type,i);
		p3 = new SignPoint(x2,y2 + 20,type,i);
		p4 = new SignPoint(x2 + 20,y2,type,i);
		pointList.add(p1);
		pointList.add(p2);
		pointList.add(p3);
		pointList.add(p4);
		pointList.add(null);
	}
	
	public void addPoint(List<SignPoint> pointList,Status upSta,Status downSta,Status rightSta,String type,int i) {
		p1 = new SignPoint(rightSta.getX(),rightSta.getY() + rightSta.getR(),type,i);
		p2 = new SignPoint(upSta.getX() + upSta.getR() * 2,upSta.getY() + upSta.getR(),type,i);
		p3 = new SignPoint(downSta.getX() + downSta.getR() * 2,downSta.getY() + downSta.getR(),type,i);
		pointList.add(p1);
		pointList.add(p2);
		pointList.add(p3);
		pointList.add(null);
	}
	
	public void addPoint(List<SignPoint> pointList,int x,int y,Status upSta,Status downSta,String type,int i) {
		p1 = new SignPoint(x,y,type,i);
		p2 = new SignPoint(upSta.getX(),upSta.getY() + upSta.getR(),type,i);
		p3 = new SignPoint(downSta.getX(),downSta.getY() + downSta.getR(),type,i);
		pointList.add(p1);
		pointList.add(p2);
		pointList.add(p3);
		pointList.add(null);
	}
	
	public void deletePoint(List<SignPoint> pointList,String type,int i) {
		Iterator<SignPoint> it = pointList.iterator();
		while(it.hasNext()){
			SignPoint point = it.next();
			if(point != null) {
				if(point.getType().equals(type) && point.getI() == 0){
			        it.remove();
			    }
			    if(it.next() == null) {
			    	it.remove();
			    	break; 
			    }
			}
		    
		}
	}

}
