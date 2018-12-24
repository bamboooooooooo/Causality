package DoItem;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import borMi.dataStructure.TestCase;
import po.And;
import po.Eor;
import po.Identical;
import po.Inor;
import po.Not;
import po.Only;
import po.Or;
import po.Require;
import po.Status;
import processor.MiProcessorM;
import utils.TestCasesPrinter;

public class CaseHandle {
	private List<Identical> identicalList;//恒等
	private List<And> andList;//与
	private List<Or> orList;//或
	private List<Not> notList;//非
	private List<Inor> inorList;//包含
	private List<Eor> eorList;//互斥
	private List<Only> onlyList;//唯一
	private List<Require> requireList;//要求
	private List<Status> statusList;
	private List<Status> staList;
	private ToDnf toDnf;
	
	public void createCase(DrawJpanel drawJpanel) {
		statusList = drawJpanel.getStatusList();
		identicalList = drawJpanel.getIdenticalList();
		andList = drawJpanel.getAndList();
		orList = drawJpanel.getOrList();
		notList = drawJpanel.getNotList();
		inorList = drawJpanel.getInorList();
		eorList = drawJpanel.getEorList();
		onlyList = drawJpanel.getOnlyList();
		requireList = drawJpanel.getRequireList();
		String result = null;
		toDnf = new ToDnf();
		for(Status status : statusList) {
			if(status.getType() == 2) {
				result = createDNF(status);
				System.out.println(status.getName() + "=" + result);
				result = toDnf.toDnf(result);
//				System.out.println(result);
				MiProcessorM mi = new MiProcessorM(result);
				HashSet<TestCase> trueTestCases = mi.trueTestCases;
				HashSet<TestCase> falseTestCases = mi.falseTestCases;
				System.out.println("True:");
				TestCasesPrinter.print(trueTestCases); // TestCasesPrinter在utils包下面
				System.out.println("False:");
				TestCasesPrinter.print(falseTestCases);
				System.out.println();
			}
			
		}
		
	}
	
	protected String createDNF(Status status) {
		
		String str = "";
		if(status.getType() == 2) {
			List<String> connectionList = status.getConnection();
			a:for(String connection : connectionList) {
				String type = connection.split(" ")[0];
				int index = Integer.parseInt(connection.split(" ")[1]);
				switch(type) {
				case "identical":str = toIdentical(identicalList,index);
								 break;
				case "not":str = toNot(notList,index);
				 		   break;
				case "and":str = toAnd(andList,index);
						   break a;
				case "or":str = toOr(orList,index);
				   		  break a;
				default:
				}
			}
		}
		else if(status.getType() == 3) {

			List<String> connectionList = status.getConnection();

//			System.out.println(connectionList.size());
			b:for(String connection : connectionList) {
				String type = connection.split(" ")[0];
				int index = Integer.parseInt(connection.split(" ")[1]);
				int k = Integer.parseInt(connection.split(" ")[2]);
				if(k == 0) {
					continue;
				}
				switch(type) {
				case "identical":str = "(" + toIdentical(identicalList,index) + ")";
								break;
				case "not":str = "(" + toNot(notList,index) + ")";
						   break;
				case "and":str = "(" + toAnd(andList,index) + ")";
						   break b;
				case "or":str = "(" + toOr(orList,index) + ")";
						  break b;
				default:
				}
			}
		}
		return str;
	}
	
	protected String toIdentical(List<Identical> identicalList,int index) {
		Status start = identicalList.get(index).getStart();
		String result = "";
		if(start.getType() == 1) {
			result = start.getName();
		}
		else {
			result = createDNF(start);
		}
		return result;
	}
	protected String toNot(List<Not> notList, int index) {
		// TODO Auto-generated method stub
		Status start = notList.get(index).getStart();
		String result = "";
		if(start.getType() == 1) {
			result = "!" + start.getName();
		}
		else {
			result = "!" + createDNF(start);
		}
		return result;
	}
	protected String toAnd(List<And> andList,int index) {
		String result = "";
		staList = andList.get(index).getStartList();
//		for(Status sta : staList) {
//			System.out.println(sta.getName());
//			System.out.println("a" + staList.indexOf(sta));
//		}
//		for(Status sta : staList) {
//			if(sta.getType() == 1) {
//				result = result + sta.getName();
//				if(staList.indexOf(sta) + 1 != staList.size()) {
//					result = result + "&";
//				}
//			}
//			else {
//				System.out.println(sta.getName());
//				System.out.println("b" + staList.indexOf(sta));
//				for(Status status : staList) {
//					if(sta == status) {
//						System.out.println(sta.getName());
//						System.out.println("a" + staList.indexOf(status));
//					}
//					
//				}
//				result = result + createDNF(sta);
//				if(staList.indexOf(sta) + 1 != staList.size()) {
//					result = result + "&";
//				}
//			}
//		}
		Iterator<Status> it = staList.iterator();
		while(it.hasNext()) {
			Status sta = it.next();
			if(sta.getType() == 1) {
				result = result + sta.getName();
				if(it.hasNext()) {
					result = result + "&";
				}
			}
			else {
				result = result + createDNF(sta);
				if(it.hasNext()) {
					result = result + "&";
				}
			}
		}
		return result;
	}
	protected String toOr(List<Or> orList2, int index) {
		// TODO Auto-generated method stub
		String result = "";
		staList = orList.get(index).getStartList();
		Iterator<Status> it = staList.iterator();
		while(it.hasNext()) {
			Status sta = it.next();
			if(sta.getType() == 1) {
				result = result + sta.getName();
				if(it.hasNext()) {
					result = result + "|";
				}
			}
			else {
				result = result + createDNF(sta);
				if(it.hasNext()) {
					result = result + "|";
				}
			}
		}
		return result;
	}
}
