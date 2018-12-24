package DoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

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

public class XMLHandle {
	//组件
		private static List<Element> statusElement;//状态
		private static List<Element> identicalElement;//恒等
		private static List<Element> andElement;//与
		private static List<Element> orElement;//或
		private static List<Element> notElement;//非
		private static List<Element> inorElement;//包含
		private static List<Element> eorElement;//互斥
		private static List<Element> onlyElement;//唯一
		private static List<Element> requireElement;//要求
		
		private static List<Status> statusList;//状态
		private static List<Identical> identicalList;//恒等
		private static List<And> andList;//与
		private static List<Or> orList;//或
		private static List<Not> notList;//非
		private static List<Inor> inorList;//包含
		private static List<Eor> eorList;//互斥
		private static List<Only> onlyList;//唯一
		private static List<Require> requireList;//要求
		
		private static Status status;
		private static Identical identical;
		private static And and;
		private static Or or;
		private static Not not;
		private static Inor inor;
		private static Eor eor;
		private static Only only;
		private static Require require;
		private static Cable cable;
		private static List<String> constaList;
		private static List<SignPoint> pointList;
//		private static SignPoint p1,p2,p3,p4;
		private static PointHandle ph;
		
		public static void readXML(String path){
			try {   
				File f = new File(path);  //文件查找 
				SAXReader reader = new SAXReader();   
				Document doc = reader.read(f);   //构建XML源的DOM4J文档
				Element root = doc.getRootElement();   //得到的XML的根元素

				statusElement = root.elements("state");
				identicalElement = root.elements("identical");
				andElement = root.elements("and");
				orElement = root.elements("or");
				notElement = root.elements("not");
				inorElement = root.elements("inor");
				eorElement = root.elements("eor");
				onlyElement = root.elements("only");
				requireElement = root.elements("require");
				
				getElement();
				
			} catch (Exception e) {   
				e.printStackTrace();   
			}   
		}
		
		public static void writeXML(String filename,String path,DrawJpanel drawJpanel) {
			statusList = new ArrayList<Status>(drawJpanel.getStatusList());
			identicalList = new ArrayList<Identical>(drawJpanel.getIdenticalList());
			andList = new ArrayList<And>(drawJpanel.getAndList());
			orList = new ArrayList<Or>(drawJpanel.getOrList());
			notList = new ArrayList<Not>(drawJpanel.getNotList());
			inorList = new ArrayList<Inor>(drawJpanel.getInorList());
			eorList = new ArrayList<Eor>(drawJpanel.getEorList());
			onlyList = new ArrayList<Only>(drawJpanel.getOnlyList());
			requireList = new ArrayList<Require>(drawJpanel.getRequireList());
			String text = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n" + "<root>";
			for(Status status : statusList) {
				if(status.getState() == 1)
					text = text + "<state cx=\"" + status.getX() + "\" cy=\"" + status.getY() + "\" r=\"" + status.getR() + "\" name=\"" + status.getName() + "\" type=\"" + status.getType() + "\" />\n";
			}
			for(Identical identical : identicalList) {
				text = text + "<identical x1=\"" + identical.getX1() + "\" y1=\"" + identical.getY1() + "\" x2=\"" + identical.getX2() + "\" y2=\"" + identical.getY2() + "\" start=\"" + identical.getStart().getName() + "\" end=\"" + identical.getEnd().getName() + "\" />\n";
			}
			for(Not not : notList) {
				text = text + "<not x1=\"" + not.getX1() + "\" y1=\"" + not.getY1() + "\" x2=\"" + not.getX2() + "\" y2=\"" + not.getY2() + "\" start=\"" + not.getStart().getName() + "\" end=\"" + not.getEnd().getName() + "\" />\n";
			}
			for(And and : andList) {
				text = text + "<and end=\"" + and.getEnd().getName() + "\" >";
				List<Cable> cableList = new ArrayList<Cable> (and.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"" + cable.getStart().getName() + "\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</and>\n";
			}
			for(Or or : orList) {
				text = text + "<or end=\"" + or.getEnd().getName() + "\" >";
				List<Cable> cableList = new ArrayList<Cable> (or.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"" + cable.getStart().getName() + "\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</or>\n";
			}
			for(Inor inor : inorList) {
				text = text + "<inor>";
				List<Cable> cableList = new ArrayList<Cable> (inor.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</inor>\n";
			}
			for(Eor eor : eorList) {
				text = text + "<eor>";
				List<Cable> cableList = new ArrayList<Cable> (eor.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</eor>\n";
			}
			for(Only only : onlyList) {
				text = text + "<only>";
				List<Cable> cableList = new ArrayList<Cable> (only.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</only>\n";
			}
			for(Require require : requireList) {
				text = text + "<require>";
				List<Cable> cableList = new ArrayList<Cable> (require.getCableList());
				for(Cable cable : cableList) {
					text = text + "<cable x1=\"" + cable.getX1() + "\" y1=\"" + cable.getY1() + "\" x2=\"" + cable.getX2() + "\" y2=\"" + cable.getY2() + "\" start=\"\" end=\"" + cable.getEnd().getName() + "\" />\n";	
				}
				text = text + "</require>\n";
			}
			text = text + "</root> "; 
			FileHandle.saveFile(filename, path, text);
		}
		private static void getElement() {
			// TODO Auto-generated method stub
			statusList = new ArrayList<Status>();
			identicalList = new ArrayList<Identical>();
			andList = new ArrayList<And>();
			orList = new ArrayList<Or>();
			notList = new ArrayList<Not>();
			inorList = new ArrayList<Inor>();
			eorList = new ArrayList<Eor>();
			onlyList = new ArrayList<Only>();
			requireList = new ArrayList<Require>();
			pointList = new ArrayList<SignPoint>();
			
			//获取状态信息
			for(Element ele:statusElement){
				status = new Status();
				status.setX(Integer.parseInt(ele.attributeValue("cx")));
				status.setY(Integer.parseInt(ele.attributeValue("cy")));
				status.setR(Integer.parseInt(ele.attributeValue("r")));
				status.setName(ele.attributeValue("name"));
				status.setState(1);
				status.setType(Integer.parseInt(ele.attributeValue("type")));
				statusList.add(status);
			 }
			
			 //获取“恒等”关系
			 for(Element ele:identicalElement){
				 identical = new Identical();
				 int x1 = Integer.parseInt(ele.attributeValue("x1"));
				 int y1 = Integer.parseInt(ele.attributeValue("y1"));
				 int x2 = Integer.parseInt(ele.attributeValue("x2"));
				 int y2 = Integer.parseInt(ele.attributeValue("y2"));
				 identical.setX1(x1);
				 identical.setY1(y1);
				 identical.setX2(x2);
				 identical.setY2(y2);
				 String start = ele.attributeValue("start");
				 String end = ele.attributeValue("end");
				 Status startSta = null,endSta = null;
				 int len = identicalList.size();
				 String str = "identical " + len;
				 for(Status status : statusList) {
					 if(status.getName().equals(start)) {
						 startSta = status;
					 }
					 else if(status.getName().equals(end)) {
						 endSta = status;
					 }
					 else
						 continue;
				 }
				 
				 constaList = startSta.getConnection();
				 constaList.add(str + " " + 0);
				 startSta.setConnection(constaList);
				 
				 constaList = endSta.getConnection();
				 constaList.add(str + " " + 1);
				 endSta.setConnection(constaList);
				 
				 identical.setStart(startSta);
				 identical.setEnd(endSta);
				 identicalList.add(identical);
				 ph = new PointHandle();
				 ph.addPoint(pointList, x1, y1, x2, y2,"identical",identicalElement.indexOf(ele));
			 }
			 
			 //获取“与”关系
			 for(Element ele:andElement){
				 and = new And();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> startList = new ArrayList<Status>();
				 String endName = ele.attributeValue("end");
				 Status endSta = null;
				 int len = andList.size();
				 String str = "and " + len;
				 for(Status status : statusList) {
					 if(status.getName().equals(endName)) {
						 endSta = status;
					 }
				 }
				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String start = cabele.attributeValue("start");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 cable.setEnd(endSta);
					 ph.addPoint(pointList, x1, y1, x2, y2,"and",andElement.indexOf(ele));
					 for(Status status : statusList) {
						 if(status.getName().equals(start)) {
							 cable.setStart(status);
							 startList.add(status);
							 constaList = status.getConnection();
							 constaList.add(str + " " + 0);
							 status.setConnection(constaList);
						 }
						 else
							 continue;
					 }
					 cableList.add(cable);
				 }
				 constaList = endSta.getConnection();
				 Status upSta = startList.get(0),downSta = startList.get(0);
				 for(Status start : startList) {
					 if(start.getY() < upSta.getY()) {
						 upSta = start;
					 }
					 else if(start.getY() > downSta.getY()) {
						 downSta = start;
					 }
				 }
				 constaList.add(str + " " + 1);
				 endSta.setConnection(constaList);
				 and.setEnd(endSta);
				 and.setStartList(startList);
				 and.setCableList(cableList);
				 andList.add(and);
			 }
			 
			 //获取“或”关系
			 for(Element ele:orElement){
				 or = new Or();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> startList = new ArrayList<Status>();
				 String endName = ele.attributeValue("end");
				 Status endSta = null;
				 int len = orList.size();
				 String str = "or " + len;
				 for(Status status : statusList) {
					 if(status.getName().equals(endName)) {
						 endSta = status;
						 or.setEnd(endSta);
					 }
				 }
				 
				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String start = cabele.attributeValue("start");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 cable.setEnd(endSta);
					 ph.addPoint(pointList, x1, y1, x2, y2,"or",orElement.indexOf(ele));
					 for(Status status : statusList) {
						 if(status.getName().equals(start)) {
							 cable.setStart(status);
							 startList.add(status);
							 constaList = status.getConnection();
							 constaList.add(str + " " + 0);
							 status.setConnection(constaList);
						 }
						 else
							 continue;
					 }	
					 cableList.add(cable);
				 }
				 constaList = endSta.getConnection();
				 Status upSta = startList.get(0),downSta = startList.get(0);
				 for(Status start : startList) {
					 if(start.getY() < upSta.getY()) {
						 upSta = start;
					 }
					 else if(start.getY() > downSta.getY()) {
						 downSta = start;
					 }
				 }
				 constaList.add(str + " " + 1);
				 endSta.setConnection(constaList);
				 or.setStartList(startList);
				 or.setCableList(cableList);
				 orList.add(or);
			 }
			 
			 //获取“非”关系
			 for(Element ele:notElement){
				 not = new Not();
				 ph = new PointHandle();
				 int x1 = Integer.parseInt(ele.attributeValue("x1"));
				 int y1 = Integer.parseInt(ele.attributeValue("y1"));
				 int x2 = Integer.parseInt(ele.attributeValue("x2"));
				 int y2 = Integer.parseInt(ele.attributeValue("y2"));
				 String start = ele.attributeValue("start");
				 String end = ele.attributeValue("end");
				 Status startSta = null,endSta = null;
				 int len = notList.size();
				 String str = "not " + len;
				 for(Status status : statusList) {
					 if(status.getName().equals(start)) {
						 startSta = status;
					 }
					 else if(status.getName().equals(end)) {
						 endSta = status;
					 }
					 else
						 continue;
				 }
				 constaList = startSta.getConnection();
				 constaList.add(str + " " + 0);
				 startSta.setConnection(constaList);
				 
				 constaList = endSta.getConnection();
				 constaList.add(str + " " + 1);
				 endSta.setConnection(constaList);

				 not.setX1(x1);
				 not.setY1(y1);
				 not.setX2(x2);
				 not.setY2(y2);
				 not.setStart(startSta);
				 not.setEnd(endSta);
				 notList.add(not);
				 ph.addPoint(pointList, x1, y1, x2, y2,"not",notElement.indexOf(ele));
			 }
			 
			 //获取“包含”关系
			 for(Element ele:inorElement){
				 inor = new Inor();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> staList = new ArrayList<Status>();

				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String end = cabele.attributeValue("end");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setStart(null);
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 for(Status status : statusList) {
						 if(status.getName().equals(end)) {
							 cable.setEnd(status);
							 staList.add(status);
						 }
						 else
							 continue;
					 }
					 ph.addPoint(pointList, x1, y1, x2, y2,"inor",inorElement.indexOf(ele));
					 cableList.add(cable);
				 }
				 inor.setStatusList(staList);
				 inor.setCableList(cableList);
				 inorList.add(inor);
			 }
			 
			 //获取“互斥”关系
			 for(Element ele:eorElement){
				 eor = new Eor();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> staList = new ArrayList<Status>();

				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String end = cabele.attributeValue("end");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setStart(null);
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 for(Status status : statusList) {
						 if(status.getName().equals(end)) {
							 cable.setEnd(status);
							 staList.add(status);
						 }
						 else
							 continue;
					 }
					 ph.addPoint(pointList, x1, y1, x2, y2,"eor",eorElement.indexOf(ele));
					 cableList.add(cable);
				 }
				 eor.setStatusList(staList);
				 eor.setCableList(cableList);
				 eorList.add(eor);
			 }
			 
			 //获取“唯一”关系
			 for(Element ele:onlyElement){
				 only = new Only();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> staList = new ArrayList<Status>();

				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String end = cabele.attributeValue("end");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setStart(null);
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 for(Status status : statusList) {
						 if(status.getName().equals(end)) {
							 cable.setEnd(status);
							 staList.add(status);
						 }
						 else
							 continue;
					 }
					 ph.addPoint(pointList, x1, y1, x2, y2,"only",onlyElement.indexOf(ele));
					 cableList.add(cable);
				 }
				 only.setStatusList(staList);
				 only.setCableList(cableList);
				 onlyList.add(only);
			 }
			 
			 //获取“唯一”关系
			 for(Element ele:requireElement){
				 require = new Require();
				 ph = new PointHandle();
				 List<Element> cableElement = new ArrayList<Element>();
				 cableElement = ele.elements("cable");
				 List<Cable> cableList = new ArrayList<Cable>();
				 List<Status> staList = new ArrayList<Status>();

				 for(Element cabele : cableElement){
					 cable = new Cable();
					 String end = cabele.attributeValue("end");
					 int x1 = Integer.parseInt(cabele.attributeValue("x1"));
					 int y1 = Integer.parseInt(cabele.attributeValue("y1"));
					 int x2 = Integer.parseInt(cabele.attributeValue("x2"));
					 int y2 = Integer.parseInt(cabele.attributeValue("y2"));
					 cable.setStart(null);
					 cable.setX1(x1);
					 cable.setY1(y1);
					 cable.setX2(x2);
					 cable.setY2(y2);
					 for(Status status : statusList) {
						 if(status.getName().equals(end)) {
							 cable.setEnd(status);
							 staList.add(status);
						 }
						 else
							 continue;
					 }
					 ph.addPoint(pointList, x1, y1, x2, y2,"require",requireElement.indexOf(ele));
					 cableList.add(cable);
				 }
				 require.setStatusList(staList);
				 require.setCableList(cableList);
				 requireList.add(require);
				 
//				 Status upSta = staList.get(0),downSta = staList.get(0);
//				 for(Status status : staList) {
//					 constaList.add(status);
//					 if(status.getY() < upSta.getY()) {
//						 upSta = status;
//					 }
//					 else if(status.getY() > downSta.getY()) {
//						 downSta = status;
//					 }
//				 }
				 
//				 p1 = new SignPoint(cableList.get(0).getX1(),cableList.get(0).getY1(),"require",requireList.indexOf(require));
//				 p2 = new SignPoint(upSta.getX(),upSta.getY(),"require",requireList.indexOf(require));
//				 p3 = new SignPoint(downSta.getX(),downSta.getY(),"require",requireList.indexOf(require));
//				 pointList.add(p1);
//				 pointList.add(p2);
//				 pointList.add(p3);
//				 pointList.add(null);
			 }
		}

		public static List<Status> getStatusList() {
			return statusList;
		}

		public static void setStatusList(List<Status> statusList) {
			XMLHandle.statusList = statusList;
		}

		public static List<Identical> getIdenticalList() {
			return identicalList;
		}

		public static void setIdenticalList(List<Identical> identicalList) {
			XMLHandle.identicalList = identicalList;
		}

		public static List<And> getAndList() {
			return andList;
		}

		public static void setAndList(List<And> andList) {
			XMLHandle.andList = andList;
		}

		public static List<Or> getOrList() {
			return orList;
		}

		public static void setOrList(List<Or> orList) {
			XMLHandle.orList = orList;
		}

		public static List<Not> getNotList() {
			return notList;
		}

		public static void setNotList(List<Not> notList) {
			XMLHandle.notList = notList;
		}

		public static List<Inor> getInorList() {
			return inorList;
		}

		public static void setInorList(List<Inor> inorList) {
			XMLHandle.inorList = inorList;
		}

		public static List<Eor> getEorList() {
			return eorList;
		}

		public static void setEorList(List<Eor> eorList) {
			XMLHandle.eorList = eorList;
		}

		public static List<Only> getOnlyList() {
			return onlyList;
		}

		public static void setOnlyList(List<Only> onlyList) {
			XMLHandle.onlyList = onlyList;
		}

		public static List<Require> getRequireList() {
			return requireList;
		}

		public static void setRequireList(List<Require> requireList) {
			XMLHandle.requireList = requireList;
		}

		public static List<SignPoint> getPointList() {
			return pointList;
		}

		public static void setPointList(List<SignPoint> pointList) {
			XMLHandle.pointList = pointList;
		}
}
