package DoItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import po.And;
import po.Cable;
import po.Eor;
import po.Identical;
import po.Inor;
import po.Not;
import po.Only;
import po.Or;
import po.Require;
import po.SignPoint;
import po.Status;

public class ConnectionHandle {
	private Cable cable;
	private List<Cable> cableList;
	private List<Identical> identicalList;//恒等
	private List<And> andList;//与
	private List<Or> orList;//或
	private List<Not> notList;//非
	private List<Inor> inorList;//包含
	private List<Eor> eorList;//互斥
	private List<Only> onlyList;//唯一
	private List<Require> requireList;//要求
	private List<SignPoint> pointList;
	private PointHandle ph;

	public void createIdentical(DrawJpanel drawJpanel,Status inputSta,Status outputSta) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		identicalList = drawJpanel.getIdenticalList();
		Identical identical = new Identical();
		identical.setStart(inputSta);
		identical.setEnd(outputSta);
		identical.setX1(inputSta.getX() + inputSta.getR() * 2 + 1);//为什么要+1？？？
		identical.setY1(inputSta.getY() + inputSta.getR() + 1);
		identical.setX2(outputSta.getX() + 1);
		identical.setY2(outputSta.getY() + outputSta.getR() + 1);
		int len = identicalList.size();
		String str = "identical " + len;
//		List<String> constaList = inputSta.getConnection();
//		constaList.add(str);
//		inputSta.setConnection(constaList);
		inputSta.getConnection().add(str + " " + 0);
//		constaList = outputSta.getConnection();
//		constaList.add(inputSta);
//		outputSta.setConnection(constaList);
		outputSta.getConnection().add(str + " " + 1);
		identicalList.add(identical);
		if(inputSta.getType() == 2)
			inputSta.setType(3);
		else if(inputSta.getType() == 0)
			inputSta.setType(1);
		if(outputSta.getType() == 0)
			outputSta.setType(2);
		else if(outputSta.getType() == 1)
			outputSta.setType(3);
		ph = new PointHandle();
		ph.addPoint(pointList, identical.getX1(), identical.getY1(), identical.getX2(), identical.getY2(), "identical",drawJpanel.getIdenticalList().indexOf(identical)); 
		drawJpanel.updateJpanel();
	}
	public void createNot(DrawJpanel drawJpanel,Status inputSta,Status outputSta) {
		this.pointList = drawJpanel.getPointList();
		notList = drawJpanel.getNotList();
		Not not = new Not();
		not.setStart(inputSta);
		not.setEnd(outputSta);
		not.setX1(inputSta.getX() + inputSta.getR() * 2 + 1);//为什么要+1？？？
		not.setY1(inputSta.getY() + inputSta.getR() + 1);
		not.setX2(outputSta.getX() + 1);
		not.setY2(outputSta.getY() + outputSta.getR() + 1);
		int len = notList.size();
		String str = "not " + len;
//		List<Status> constaList = inputSta.getConnection();
//		constaList = inputSta.getConnection();
//		constaList.add(outputSta);
//		inputSta.setConnection(constaList);
//		constaList = outputSta.getConnection();
//		constaList.add(inputSta);
//		outputSta.setConnection(constaList);
		inputSta.getConnection().add(str + " " + 0);
		outputSta.getConnection().add(str + " " + 1);
		notList.add(not);
		if(inputSta.getType() == 0)
			inputSta.setType(1);
		else if(inputSta.getType() == 2)
			inputSta.setType(3);
		if(outputSta.getType() == 0)
			outputSta.setType(2);
		else if(outputSta.getType() == 1)
			outputSta.setType(3);
		ph = new PointHandle();
		ph.addPoint(pointList, not.getX1(), not.getY1(), not.getX2(), not.getY2(), "not",drawJpanel.getNotList().indexOf(not));
		drawJpanel.updateJpanel(); 
	}
	public void createAnd(DrawJpanel drawJpanel,List<Status> startList, Status endStatus) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		andList = drawJpanel.getAndList();
		And and = new And();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		and.setStartList(startList);
		and.setEnd(endStatus);
		int len = andList.size();
		String str = "and " + len;
		for(Status status : startList) {
			cable = new Cable();
			cable.setX1(status.getX() + status.getR() * 2);
			cable.setY1(status.getY() + status.getR());
			cable.setX2(endStatus.getX());
			cable.setY2(endStatus.getY() + status.getR());
			cable.setStart(status);
			cable.setEnd(endStatus);
			cableList.add(cable);
			status.getConnection().add(str + " " + 0);
			if(status.getType() == 0)
				status.setType(1);
			else if(status.getType() == 2)
				status.setType(3);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "and",drawJpanel.getAndList().size());
		}
		endStatus.getConnection().add(str + " " + 1);
		if(endStatus.getType() == 0)
			endStatus.setType(2);
		else if(endStatus.getType() == 1)
			endStatus.setType(3);
		and.setCableList(cableList);
		andList.add(and);
		drawJpanel.updateJpanel(); 
	}
	public void createOr(DrawJpanel drawJpanel,List<Status> startList, Status endStatus) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		orList = drawJpanel.getOrList();
		Or or = new Or();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		or.setStartList(startList);
		or.setEnd(endStatus);
		int len = orList.size();
		String str = "or " + len;
		for(Status status : startList) {
			cable = new Cable();
			cable.setX1(status.getX() + status.getR() * 2);
			cable.setY1(status.getY() + status.getR());
			cable.setX2(endStatus.getX());
			cable.setY2(endStatus.getY() + status.getR());
			cable.setStart(status);
			cable.setEnd(endStatus);
			cableList.add(cable);
			status.getConnection().add(str + " " + 0);
			if(status.getType() == 0)
				status.setType(1);
			else if(status.getType() == 2)
				status.setType(3);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "or",drawJpanel.getOrList().size());
		}
		endStatus.getConnection().add(str + " " + 1);
		if(endStatus.getType() == 0)
			endStatus.setType(2);
		else if(endStatus.getType() == 1)
			endStatus.setType(3);
		or.setCableList(cableList);
		orList.add(or);
		drawJpanel.updateJpanel(); 
	}
	public void createInor(DrawJpanel drawJpanel, List<Status> selList) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		Inor inor = new Inor();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		inor.setStatusList(selList);
		int up = selList.get(0).getY();
		int down = selList.get(0).getY();
		int left = selList.get(0).getX();
		int i = 0,j = 0,k = 0;
		for(Status status : selList) {
			if(status.getY() < up) {
				up = status.getY();
				i = selList.indexOf(status);
			}
			else if(status.getY() > down) {
				down = status.getY();
				j = selList.indexOf(status);
			}
			if(status.getX() < left) {
				left = status.getX();
				k = selList.indexOf(status);
			}
		}
		int x = selList.get(k).getX() - 60;
		int y = selList.get(i).getY() + (selList.get(j).getY() - selList.get(i).getY()) / 2;
		for(Status status : selList) {
			cable = new Cable();
			cable.setX1(x);
			cable.setY1(y + status.getR());
			cable.setX2(status.getX());
			cable.setY2(status.getY() + status.getR());
			cable.setStart(null);
			cable.setEnd(status);
			cableList.add(cable);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "inor",drawJpanel.getInorList().size());
		}
		inor.setUpSta(selList.get(i));
		inor.setDownSta(selList.get(j));
		inor.setLeftSta(selList.get(k));
		inor.setCableList(cableList);
		drawJpanel.getInorList().add(inor);
		drawJpanel.updateJpanel(); 
	}
	
	public void createEor(DrawJpanel drawJpanel, List<Status> selList) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		Eor eor = new Eor();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		eor.setStatusList(selList);
		int up = selList.get(0).getY();
		int down = selList.get(0).getY();
		int left = selList.get(0).getX();
		int i = 0,j = 0,k = 0;
		for(Status status : selList) {
			if(status.getY() < up) {
				up = status.getY();
				i = selList.indexOf(status);
			}
			else if(status.getY() > down) {
				down = status.getY();
				j = selList.indexOf(status);
			}
			if(status.getX() < left) {
				left = status.getX();
				k = selList.indexOf(status);
			}
		}
		int x = selList.get(k).getX() - 60;
		int y = selList.get(i).getY() + (selList.get(j).getY() - selList.get(i).getY()) / 2;
		for(Status status : selList) {
			cable = new Cable();
			cable.setX1(x);
			cable.setY1(y + status.getR());
			cable.setX2(status.getX());
			cable.setY2(status.getY() + status.getR());
			cable.setStart(null);
			cable.setEnd(status);
			cableList.add(cable);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "eor",drawJpanel.getEorList().size());
		}
		eor.setUpSta(selList.get(i));
		eor.setDownSta(selList.get(j));
		eor.setLeftSta(selList.get(k));
		eor.setCableList(cableList);
		drawJpanel.getEorList().add(eor);
		drawJpanel.updateJpanel(); 
	}
	public void createOnly(DrawJpanel drawJpanel, List<Status> selList) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		Only only = new Only();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		only.setStatusList(selList);
		int up = selList.get(0).getY();
		int down = selList.get(0).getY();
		int left = selList.get(0).getX();
		int i = 0,j = 0,k = 0;
		for(Status status : selList) {
			if(status.getY() < up) {
				up = status.getY();
				i = selList.indexOf(status);
			}
			else if(status.getY() > down) {
				down = status.getY();
				j = selList.indexOf(status);
			}
			if(status.getX() < left) {
				left = status.getX();
				k = selList.indexOf(status);
			}
		}
		int x = selList.get(k).getX() - 60;
		int y = selList.get(i).getY() + (selList.get(j).getY() - selList.get(i).getY()) / 2;
		for(Status status : selList) {
			cable = new Cable();
			cable.setX1(x);
			cable.setY1(y + status.getR());
			cable.setX2(status.getX());
			cable.setY2(status.getY() + status.getR());
			cable.setStart(null);
			cable.setEnd(status);
			cableList.add(cable);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "only",drawJpanel.getOnlyList().size());
		}
		only.setUpSta(selList.get(i));
		only.setDownSta(selList.get(j));
		only.setLeftSta(selList.get(k));
		only.setCableList(cableList);
		drawJpanel.getOnlyList().add(only);
		drawJpanel.updateJpanel(); 
	}
	public void createRequire(DrawJpanel drawJpanel, List<Status> selList) {
		// TODO Auto-generated method stub
		this.pointList = drawJpanel.getPointList();
		Require require = new Require();
		cableList = new ArrayList<Cable>();
		ph = new PointHandle();
		require.setStatusList(selList);
		int up = selList.get(0).getY();
		int down = selList.get(0).getY();
		int left = selList.get(0).getX();
		int i = 0,j = 0,k = 0;
		for(Status status : selList) {
			if(status.getY() < up) {
				up = status.getY();
				i = selList.indexOf(status);
			}
			else if(status.getY() > down) {
				down = status.getY();
				j = selList.indexOf(status);
			}
			if(status.getX() < left) {
				left = status.getX();
				k = selList.indexOf(status);
			}
		}
		int x = selList.get(k).getX() - 60;
		int y = selList.get(i).getY() + (selList.get(j).getY() - selList.get(i).getY()) / 2;
		for(Status status : selList) {
			cable = new Cable();
			cable.setX1(x);
			cable.setY1(y + status.getR());
			cable.setX2(status.getX());
			cable.setY2(status.getY() + status.getR());
			cable.setStart(null);
			cable.setEnd(status);
			cableList.add(cable);
			ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "require",drawJpanel.getRequireList().size());
		}
		require.setUpSta(selList.get(i));
		require.setDownSta(selList.get(j));
		require.setLeftSta(selList.get(k));
		require.setCableList(cableList);
		drawJpanel.getRequireList().add(require);
		drawJpanel.updateJpanel(); 
	}
	//更改“恒等”连接
	public void updateIdentical(List<Identical> identicalList,List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.identicalList = identicalList;
		this.pointList = pointList;
		ph = new PointHandle();
		for(Identical identical:identicalList){
			if(identical.getStart() == sta){
				identical.setX1(x + 2 * sta.getR());
				identical.setY1(y + sta.getR());
				ph.deletePoint(pointList, "identical", identicalList.indexOf(identical));
				ph.addPoint(pointList, identical.getX1(), identical.getY1(), identical.getX2(), identical.getY2(), "identical", identicalList.indexOf(identical));
			}
			else if(identical.getEnd() == sta){
				identical.setX2(x);
				identical.setY2(y + sta.getR());
				ph.deletePoint(pointList, "identical", identicalList.indexOf(identical));
				ph.addPoint(pointList, identical.getX1(), identical.getY1(), identical.getX2(), identical.getY2(), "identical", identicalList.indexOf(identical));
			}
			else
				continue;
		}
	}
	//更改“非”连接
	public void updateNot(List<Not> notList,List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.notList = notList;
		this.pointList = pointList;
		ph = new PointHandle();
		for(Not not : notList){
			if(not.getStart() == sta){
				not.setX1(x + 2 * sta.getR());
				not.setY1(y + sta.getR());
				ph.deletePoint(pointList, "not", notList.indexOf(not));
				ph.addPoint(pointList, not.getX1(), not.getY1(), not.getX2(), not.getY2(), "not", notList.indexOf(not));
			}
			else if(not.getEnd() == sta){
				not.setX2(x);
				not.setY2(y + sta.getR());
				ph.deletePoint(pointList, "not", notList.indexOf(not));
				ph.addPoint(pointList, not.getX1(), not.getY1(), not.getX2(), not.getY2(), "not", notList.indexOf(not));
			}
			else
				continue;
		}
	}
	//更改“与”连接
	public void updateAnd(List<And> andList, List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.andList = andList;
		this.pointList = pointList;
		ph = new PointHandle();
		for(And and : andList){
			List<Cable> cableList = and.getCableList();
			if(and.getEnd() == sta){
				for(Cable cable : cableList){
					cable.setX2(x);
					cable.setY2(y + sta.getR());
					ph.deletePoint(pointList, "and", andList.indexOf(and));
					ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "and", andList.indexOf(and));
				}
			}
			for(Cable cable : cableList){
				if(cable.getStart() == sta){
					cable.setX1(x + 2 * sta.getR());
					cable.setY1(y + sta.getR());
					ph.deletePoint(pointList, "and", andList.indexOf(and));
					ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "and", andList.indexOf(and));
				 }
			 }
		}
	}
	//更改“或”连接
	public void updateOr(List<Or> orList, List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.orList = orList;
		this.pointList = pointList;
		ph = new PointHandle();
		for(Or or : orList){
			List<Cable> cableList = or.getCableList();
			if(or.getEnd() == sta){
				for(Cable cable : cableList){
					cable.setX2(x);
					cable.setY2(y + sta.getR());
					ph.deletePoint(pointList, "or", orList.indexOf(or));
					ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "or", orList.indexOf(or));
				}
			}
			for(Cable cable : cableList){
				if(cable.getStart() == sta){
					cable.setX1(x + 2 * sta.getR());
					cable.setY1(y + sta.getR());
					ph.deletePoint(pointList, "or", orList.indexOf(or));
					ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "or", orList.indexOf(or));
				 }
			 }
		}
	}
	//更改“包含”连接
	public void updateInro(List<Inor> inorList, List<SignPoint> pointList, Status sta, int x, int y) {
		this.inorList = inorList;
		this.pointList = pointList;
		ph = new PointHandle();
		List<Status> staList;
		for(Inor inor : inorList) {
			staList = new ArrayList<Status>(inor.getStatusList());
			int up = staList.get(0).getY();
			int down = staList.get(0).getY();
			int left = staList.get(0).getX();
			int i = 0,j = 0,k = 0;
			int r = staList.get(0).getR();
			for(Status status : staList) {
				if(status.getY() < up) {
					up = status.getY();
					i = staList.indexOf(status);
				}
				else if(status.getY() > down) {
					down = status.getY();
					j = staList.indexOf(status);
				}
				if(status.getX() < left) {
					left = status.getX();
					k = staList.indexOf(status);
				}
			}
			List<Cable> cableList = inor.getCableList();
			for(Cable cable : cableList) {
				if(cable.getEnd() == sta) {
					cable.setX2(x);
					cable.setY2(y + sta.getR());
				}
				cable.setX1(staList.get(k).getX() - 60);
				cable.setY1(staList.get(i).getY() + (staList.get(j).getY() - staList.get(i).getY()) / 2 + r);
				ph.deletePoint(pointList, "inor", inorList.indexOf(inor));
				ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "inor", inorList.indexOf(inor));
			}
			
		}
	}
	
	//更改“异或”连接
	protected void updateEor(List<Eor> eorList, List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.eorList = eorList;
		this.pointList = pointList;
		ph = new PointHandle();
		List<Status> staList;
		for(Eor eor : eorList) {
			staList = new ArrayList<Status>(eor.getStatusList());
			int up = staList.get(0).getY();
			int down = staList.get(0).getY();
			int left = staList.get(0).getX();
			int i = 0,j = 0,k = 0;
			int r = staList.get(0).getR();
			for(Status status : staList) {
				if(status.getY() < up) {
					up = status.getY();
					i = staList.indexOf(status);
				}
				else if(status.getY() > down) {
					down = status.getY();
					j = staList.indexOf(status);
				}
				if(status.getX() < left) {
					left = status.getX();
					k = staList.indexOf(status);
				}
			}
			List<Cable> cableList = eor.getCableList();
			for(Cable cable : cableList) {
				if(cable.getEnd() == sta) {
					cable.setX2(x);
					cable.setY2(y + sta.getR());
				}
				cable.setX1(staList.get(k).getX() - 60);
				cable.setY1(staList.get(i).getY() + (staList.get(j).getY() - staList.get(i).getY()) / 2 + r);
				ph.deletePoint(pointList, "eor", eorList.indexOf(eor));
				ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "eor", eorList.indexOf(eor));
			}
		}
	}
	//更改“唯一”连接
	protected void updateOnly(List<Only> onlyList, List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.onlyList = onlyList;
		this.pointList = pointList;
		ph = new PointHandle();
		List<Status> staList;
		for(Only only : onlyList) {
			staList = new ArrayList<Status>(only.getStatusList());
			int up = staList.get(0).getY();
			int down = staList.get(0).getY();
			int left = staList.get(0).getX();
			int i = 0,j = 0,k = 0;
			int r = staList.get(0).getR();
			for(Status status : staList) {
				if(status.getY() < up) {
					up = status.getY();
					i = staList.indexOf(status);
				}
				else if(status.getY() > down) {
					down = status.getY();
					j = staList.indexOf(status);
				}
				if(status.getX() < left) {
					left = status.getX();
					k = staList.indexOf(status);
				}
			}
			List<Cable> cableList = only.getCableList();
			for(Cable cable : cableList) {
				if(cable.getEnd() == sta) {
					cable.setX2(x);
					cable.setY2(y + sta.getR());
				}
				cable.setX1(staList.get(k).getX() - 60);
				cable.setY1(staList.get(i).getY() + (staList.get(j).getY() - staList.get(i).getY()) / 2 + r);
				ph.deletePoint(pointList, "only", onlyList.indexOf(only));
				ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "only", onlyList.indexOf(only));
			}
		}
	}
	//更改“要求”连接
	protected void updateRequire(List<Require> requireList, List<SignPoint> pointList, Status sta, int x, int y) {
		// TODO Auto-generated method stub
		this.requireList = requireList;
		this.pointList = pointList;
		ph = new PointHandle();
		List<Status> staList;
		for(Require require : requireList) {
			staList = new ArrayList<Status>(require.getStatusList());
			int up = staList.get(0).getY();
			int down = staList.get(0).getY();
			int left = staList.get(0).getX();
			int i = 0,j = 0,k = 0;
			int r = staList.get(0).getR();
			for(Status status : staList) {
				if(status.getY() < up) {
					up = status.getY();
					i = staList.indexOf(status);
				}
				else if(status.getY() > down) {
					down = status.getY();
					j = staList.indexOf(status);
				}
				if(status.getX() < left) {
					left = status.getX();
					k = staList.indexOf(status);
				}
			}
			List<Cable> cableList = require.getCableList();
			for(Cable cable : cableList) {
				if(cable.getEnd() == sta) {
					cable.setX2(x);
					cable.setY2(y + sta.getR());
				}
				cable.setX1(staList.get(k).getX() - 60);
				cable.setY1(staList.get(i).getY() + (staList.get(j).getY() - staList.get(i).getY()) / 2 + r);
				ph.deletePoint(pointList, "require", requireList.indexOf(require));
				ph.addPoint(pointList, cable.getX1(), cable.getY1(), cable.getX2(), cable.getY2(), "require", requireList.indexOf(require));
			}
		}
	}
	
	public void deleteIdentical(List<Identical> identicalList, List<SignPoint> pointList, int i) {
		Identical identical = identicalList.get(i);
		Status start = identical.getStart();
		Status end = identical.getEnd();
		ph = new PointHandle();
		if(start.getType() == 1)
			start.setType(0);
		else if(start.getType() == 3)
			start.setType(2);
		if(end.getType() == 2)
			end.setType(0);
		else if(end.getType() == 3)
			end.setType(1);
		Iterator<String> iterator = start.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("identical") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }  
	    iterator = end.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("identical") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }  
	    ph.deletePoint(pointList, "identical",i);
		identicalList.remove(i);
		
	}
	public void deleteNot(List<Not> notList, List<SignPoint> pointList,int i) {
		Not not = notList.get(i);
		Status start = not.getStart();
		Status end = not.getEnd();
		ph = new PointHandle();
		if(start.getType() == 1)
			start.setType(0);
		else if(start.getType() == 3)
			start.setType(2);
		if(end.getType() == 2)
			end.setType(0);
		else if(end.getType() == 3)
			end.setType(1);
		Iterator<String> iterator = start.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("not") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }  
	    iterator = end.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("not") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }  
//		for(String str : start.getConnection()) {
//			if(str.split(" ")[0].equals("not") && str.split(" ")[1].equals(String.valueOf(i))) {
//				start.getConnection().remove(str);
//			}
//		}
//		for(String str : end.getConnection()) {
//			if(str.split(" ")[0].equals("not") && str.split(" ")[1].equals(String.valueOf(i))) {
//				end.getConnection().remove(str);
//			}
//		}
		ph.deletePoint(pointList, "not",i);
		notList.remove(i);
	}
	
	public void deleteAnd(List<And> andList, List<SignPoint> pointList,int i) {
		And and = andList.get(i);
		List<Status> start = and.getStartList();
		Status end = and.getEnd();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : start) {
			if(sta.getType() == 1)
				sta.setType(0);
			else if(sta.getType() == 3)
				sta.setType(2);
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("and") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     } 
		}
		iterator = end.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("and") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }
		if(end.getType() == 2)
			end.setType(0);
		else if(end.getType() == 3)
			end.setType(1);
//		for(String str : end.getConnection()) {
//			if(str.split(" ")[0].equals("and") && str.split(" ")[1].equals(String.valueOf(i))) {
//				end.getConnection().remove(str);
//			}
//		}
		ph.deletePoint(pointList, "and",i);
		andList.remove(i);
	}
	
	public void deleteOr(List<Or> orList, List<SignPoint> pointList, int i) {
		Or or = orList.get(i);
		List<Status> start = or.getStartList();
		Status end = or.getEnd();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : start) {
			if(sta.getType() == 1)
				sta.setType(0);
			else if(sta.getType() == 3)
				sta.setType(2);
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("or") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     }
		}
		iterator = end.getConnection().iterator();  
	    while(iterator.hasNext()) {  
	         String str = iterator.next();  
	         if(str.split(" ")[0].equals("or") && str.split(" ")[1].equals(String.valueOf(i))) {
	        	 iterator.remove(); 
				}
	     }
		if(end.getType() == 2)
			end.setType(0);
		else if(end.getType() == 3)
			end.setType(1);
//		for(String str : end.getConnection()) {
//			if(str.split(" ")[0].equals("or") && str.split(" ")[1].equals(String.valueOf(i))) {
//				end.getConnection().remove(str);
//			}
//		}
		ph.deletePoint(pointList, "or",i);
		orList.remove(i);
	}
	
	public void deleteInor(List<Inor> inorList, List<SignPoint> pointList, int i) {
		// TODO Auto-generated method stub
		Inor inor = inorList.get(i);
		List<Status> staList = inor.getStatusList();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : staList) {
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("inor") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     }
		}
		ph.deletePoint(pointList, "inor",i);
		inorList.remove(i);
	}
	public void deleteEor(List<Eor> eorList, List<SignPoint> pointList, int i) {
		// TODO Auto-generated method stub
		Eor eor = eorList.get(i);
		List<Status> staList = eor.getStatusList();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : staList) {
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("eor") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     }
		}
		ph.deletePoint(pointList, "eor",i);
		eorList.remove(i);
	}
	public void deleteOnly(List<Only> onlyList, List<SignPoint> pointList, int i) {
		// TODO Auto-generated method stub
		Only only = onlyList.get(i);
		List<Status> staList = only.getStatusList();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : staList) {
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("only") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     }
		}
		ph.deletePoint(pointList, "only",i);
		onlyList.remove(i);
	}
	public void deleteRequire(List<Require> requireList, List<SignPoint> pointList, int i) {
		// TODO Auto-generated method stub
		Require require = requireList.get(i);
		List<Status> staList = require.getStatusList();
		ph = new PointHandle();
		Iterator<String> iterator;
		for(Status sta : staList) {
			iterator = sta.getConnection().iterator();  
		    while(iterator.hasNext()) {  
		         String str = iterator.next();  
		         if(str.split(" ")[0].equals("require") && str.split(" ")[1].equals(String.valueOf(i))) {
		        	 iterator.remove(); 
					}
		     }
		}
		ph.deletePoint(pointList, "require",i);
		requireList.remove(i);
	}
	
}
