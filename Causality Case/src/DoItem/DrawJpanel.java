package DoItem;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import UI.AndConnection;
import UI.OrConnection;
import UI.RequireConnection;
import UI.EorConnection;
import UI.IdenticalConnection;
import UI.InorConnection;
import UI.NotConnection;
import UI.OnlyConnection;
import UI.StatusAttribute;
import po.And;
import po.Eor;
import po.Identical;
import po.Inor;
import po.LinePanel;
import po.MyPanel;
import po.Not;
import po.Only;
import po.Or;
import po.SignPoint;
import po.Require;
import po.Status;
import po.StatusPanel;

public class DrawJpanel implements ActionListener, MouseListener, MouseMotionListener {
	private MyPanel jPanel;
	private JPopupMenu popupMenu;
	private JPopupMenu conMenu;
	private List<StatusPanel> spList;
	private List<LinePanel> lpList;
	private StatusPanel sp;
	private XMLHandle xmlhandle;
	private String path = "";
	//右击目录
	private JMenu newConnectMenu,causalityMenu,inputrelationMenu;
	private JMenuItem identicalItem,andItem,orItem,notItem;
	private JMenuItem inorItem,eorItem,onlyItem,requireItem;
	private JMenuItem newStateItem;
	private int r = 30;
	
	private List<Status> statusList;//状态
	private List<Identical> identicalList;//恒等
	private List<And> andList;//与
	private List<Or> orList;//或
	private List<Not> notList;//非
	private List<Inor> inorList;//包含
	private List<Eor> eorList;//互斥
	private List<Only> onlyList;//唯一
	private List<Require> requireList;//要求
	
	private int type = -1;
	private int x = -1;
	private int y = -1;

	private int sqx,sqy,szx,szy;
	private Status status;
	private DrawJpanel djp = this;
	private JPopupMenu staPopupMenu;
	private JMenuItem copyItem;
	private JMenuItem cutItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	private JMenuItem searchItem;
	
	private int staseli;
	private Status pendingSta;
	
	private Identical identical;
	private SignPoint cutflag;
	private StatusPanel pendingStaPanel;
	private LinePanel lp;
	private List<SignPoint> pointList;// 点信息向量组
	private JMenuItem proConItem;
	private JMenuItem delConItem;
	private static String selectType;
	private static int selectK;
	
	public List<SignPoint> getPointList() {
		return pointList;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public List<Identical> getIdenticalList() {
		return identicalList;
	}

	public List<And> getAndList() {
		return andList;
	}

	public List<Or> getOrList() {
		return orList;
	}

	public List<Not> getNotList() {
		return notList;
	}

	public List<Inor> getInorList() {
		return inorList;
	}

	public List<Eor> getEorList() {
		return eorList;
	}

	public List<Only> getOnlyList() {
		return onlyList;
	}

	public List<Require> getRequireList() {
		return requireList;
	}

	//画板初始化
	public JPanel initJpanel(String fileName,String filePath){
		jPanel = new MyPanel();//创建画板
		jPanel.setBackground(Color.WHITE);
		jPanel.addMouseListener(this);//为画板增加监听
		jPanel.addMouseMotionListener(this);
		path = filePath + fileName;
		xmlhandle.readXML(path);//读取XML文件内容
		spList = new ArrayList<StatusPanel>();
		lpList = new ArrayList<LinePanel>();
		statusList = xmlhandle.getStatusList();//获取状态列表
		identicalList = xmlhandle.getIdenticalList();
		andList = xmlhandle.getAndList();
		orList = xmlhandle.getOrList();
		notList = xmlhandle.getNotList();
		inorList = xmlhandle.getInorList();
		eorList = xmlhandle.getEorList();
		onlyList = xmlhandle.getOnlyList();
		requireList = xmlhandle.getRequireList();
		pointList = xmlhandle.getPointList();
		
		initsqList();//状态面板初始化
		updateJpanel();
		jPanel.setLayout(null);
		return jPanel;
	}

	//画板修改
	public void updateJpanel() {
		if(spList != null) {
			for(int i = 0;i < spList.size();i ++ ){
				if(spList.get(i).getIndex() == 0) {
					drawPanel(i);
					addListen(i);
					jPanel.add(spList.get(i));
					spList.get(i).setIndex(1);
				}
			}	
		}
		if(lpList != null) {
			for(LinePanel lp : lpList) {
				if(lp.getT() == 0) {
					jPanel.add(lp);
					lp.setT(1);
				}
			}
		}
		jPanel.setIdenticalList(identicalList);
		jPanel.setAndList(andList);
		jPanel.setOrList(orList);
		jPanel.setNotList(notList);
		jPanel.setEorList(eorList);
		jPanel.setInorList(inorList);
		jPanel.setOnlyList(onlyList);
		jPanel.setRequireList(requireList);
		
		jPanel.repaint();
	}
	//状态面板初始化
	private void initsqList(){
		for(int i = 0;i < statusList.size();i ++){
			sp = new StatusPanel();
			spList.add(sp);
		}
	}
	
	//弹出菜单
	public void popupMenuList(){
		//Project Menu点击右键
		popupMenu = new JPopupMenu();//创建一个弹出式菜单
		newConnectMenu = new JMenu("添加关联");
		causalityMenu = new JMenu("输入-输出关系");
		inputrelationMenu = new JMenu("约束关系");
		identicalItem = new JMenuItem("—恒等");
		andItem = new JMenuItem("∧与");
		orItem = new JMenuItem("∨或");
		notItem = new JMenuItem("∽非");
		inorItem = new JMenuItem("|包含");
		eorItem = new JMenuItem("E互斥");
		onlyItem = new JMenuItem("O唯一");
		requireItem = new JMenuItem("R要求");
		newStateItem = new JMenuItem("添加状态");
		pasteItem = new JMenuItem("粘贴");
			
		newConnectMenu.add(causalityMenu);
		newConnectMenu.add(inputrelationMenu);
		causalityMenu.add(identicalItem);
		causalityMenu.add(andItem);
		causalityMenu.add(orItem);
		causalityMenu.add(notItem);
		inputrelationMenu.add(inorItem);
		inputrelationMenu.add(eorItem);
		inputrelationMenu.add(onlyItem);
		inputrelationMenu.add(requireItem);
			
		identicalItem.addActionListener(this);
		andItem.addActionListener(this);
		orItem.addActionListener(this);
		notItem.addActionListener(this);
		inorItem.addActionListener(this);
		eorItem.addActionListener(this);
		onlyItem.addActionListener(this);
		requireItem.addActionListener(this);
		newStateItem.addActionListener(this);
		pasteItem.addActionListener(this);
			
		popupMenu.add(newStateItem);
		popupMenu.add(newConnectMenu);
		popupMenu.add(pasteItem);
	}
	public void conMenuList() {
		conMenu = new JPopupMenu();//创建一个弹出式菜单
//		jPanel.addMouseListener(this);
		proConItem = new JMenuItem("属性");
		delConItem = new JMenuItem("删除");
		
		proConItem.addActionListener(this);
		delConItem.addActionListener(this);
		
		conMenu.add(proConItem);
		conMenu.add(delConItem);
	}
	//新建状态
	public void createSp(String newStatusName) {
		status = new Status();
		status.setX(x - 1);
		status.setY(y - 1);
		status.setR(r);
		status.setName(newStatusName);
		status.setState(1);
		status.setType(0);
		statusList.add(status);
		sp = new StatusPanel();
		spList.add(sp);
		
	}

//	//新建“与”连接
//	public void createAnd(And and) {
//		andList.add(and);
//		updateJpanel();
//	}
//	//新建“或”连接
//	public void createOr(Or or) {
//		orList.add(or);
//		updateJpanel();
//	}
//	//新建“非”连接
//	public void createNot(Not not) {
//		notList.add(not);
//		updateJpanel();
//	}
//	//新建“异或”连接
//	public void createEor(Eor eor) {
//		eorList.add(eor);
//		updateJpanel();
//	}
//	//新建“包含”连接
//	public void createInor(Inor inor) {
//		inorList.add(inor);
//		updateJpanel();
//	}
//	//新建“唯一”连接
//	public void createOnly(Only only) {
//		onlyList.add(only);
//		updateJpanel();
//	}
//	//新建“要求”连接
//	public void createRequire(Require require) {
//		requireList.add(require);
//		updateJpanel();
//	}
	
	//更改状态属性
	public void updateSta(Status sta,int i) {
		statusList.get(i).setName(sta.getName());
	}
	private void deleteStatus(int staseli) {
		// TODO Auto-generated method stub
		if(statusList.get(staseli).getConnection().size() != 0) {
			String message = "当前状态存在链接关系，不能删除！";
			JOptionPane.showMessageDialog(jPanel, message);
			return;
		}
		else {
			jPanel.remove(spList.get(staseli));
			statusList.get(staseli).setState(0);
		}
		jPanel.repaint();
	}
	//状态面板绘制
	public void drawPanel(int i){
		spList.get(i).setOpaque(false);//状态面板透明
		status = statusList.get(i);
		type = 0;
		int sx = status.getX();
	    int sy = status.getY();
		int sr = status.getR();
		spList.get(i).setBounds(sx, sy, 2 * sr + 2, 2 * sr + 2);
		spList.get(i).setVisible(true);
		spList.get(i).setPart(status, type);
		spList.get(i).repaint();
		type = -1;
	}
	
	//状态右击弹出菜单
	public void staPopupMenu() {
		staPopupMenu = new JPopupMenu();//创建一个弹出式菜单
		jPanel.addMouseListener(this);
		
		copyItem = new JMenuItem("复制");
		cutItem = new JMenuItem("剪切");
		deleteItem = new JMenuItem("删除");
		searchItem = new JMenuItem("属性");
		
		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		deleteItem.addActionListener(this);
		searchItem.addActionListener(this);
		
		staPopupMenu.add(copyItem);
		staPopupMenu.add(cutItem);
		staPopupMenu.add(deleteItem);
		staPopupMenu.add(searchItem);
	}
	
	//状态面板添加监听
	public void addListen(int i) {
		spList.get(i).addMouseListener(new MouseListener(){
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == spList.get(i)){
				if(spList.get(i).getBorder() == BorderFactory.createEtchedBorder())
					spList.get(i).setBorder(BorderFactory.createEmptyBorder());
				else
					spList.get(i).setBorder(BorderFactory.createEtchedBorder());
			}
			if(e.getButton() == MouseEvent.BUTTON3 && e.getSource() == spList.get(i)) {
				staPopupMenu();
				x = e.getX();
				y = e.getY();
				staPopupMenu.show(spList.get(i),e.getX(), e.getY());
				staseli = i;
			}
			if(e.getClickCount() == 2 && e.getSource() == spList.get(i)) {
				new StatusAttribute(djp,jPanel,statusList.get(i),i).setVisible(true);
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			 Point point = MouseInfo.getPointerInfo().getLocation(); 
			 sqx = point.x; 
			 sqy = point.y;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			ConnectionHandle ch;
			if(e.getSource() == spList.get(i)){
				ch = new ConnectionHandle();
				Point point = MouseInfo.getPointerInfo().getLocation(); 
				szx = point.x; 
				szy = point.y;
				int x = spList.get(i).getX() + szx - sqx;
				int y = spList.get(i).getY() + szy - sqy;
				statusList.get(i).setX(x);
				statusList.get(i).setY(y);
				spList.get(i).setBounds(x,y,2 * r + 2,2 * r + 2);
				spList.get(i).repaint();
				ch.updateIdentical(identicalList, pointList, statusList.get(i), x, y);
				ch.updateNot(notList, pointList, statusList.get(i), x, y);
				ch.updateAnd(andList, pointList, statusList.get(i), x, y);
				ch.updateOr(orList, pointList, statusList.get(i), x, y);
				ch.updateEor(eorList, pointList, statusList.get(i), x, y);
				ch.updateInro(inorList, pointList, statusList.get(i), x, y);
				ch.updateOnly(onlyList, pointList, statusList.get(i), x, y);
				ch.updateRequire(requireList, pointList, statusList.get(i), x, y);
				
				jPanel.repaint();
			}
						 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
						
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
					
		}
		});
	}

	//事件监听	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == newStateItem){
			new StatusAttribute(djp,jPanel).setVisible(true);
		}
		if(e.getSource() == identicalItem) {
			new IdenticalConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == andItem) {
			new AndConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == orItem) {
			new OrConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == notItem) {
			new NotConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == inorItem) {
			new InorConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == eorItem) {
			new EorConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == onlyItem) {
			new OnlyConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == requireItem) { 
			new RequireConnection(djp,jPanel,null,statusList).setVisible(true);
		}
		if(e.getSource() == pasteItem) {
			createSp(pendingSta.getName());
			updateJpanel();
			jPanel.repaint();
		}
		
		if(e.getSource() == cutItem) {
			pendingSta = statusList.get(staseli);
			pendingStaPanel = spList.get(staseli);
			jPanel.remove(pendingStaPanel);
			pendingSta.setState(0);
			jPanel.repaint();
		}
		if(e.getSource() == copyItem) {
			pendingSta = statusList.get(staseli);
		}
		if(e.getSource() == deleteItem) {
			deleteStatus(staseli);
		}
		if(e.getSource() == searchItem) {
			new StatusAttribute(djp,jPanel,statusList.get(staseli),staseli).setVisible(true);	
		}
		if(e.getSource() == proConItem) {
			switch(selectType) {
			case "identical":new IdenticalConnection(djp,jPanel,identicalList.get(selectK),statusList).setVisible(true);
				break;
			case "not":new NotConnection(djp,jPanel,notList.get(selectK),statusList).setVisible(true);
				break;
			case "and":new AndConnection(djp,jPanel,andList.get(selectK),statusList).setVisible(true);
				break;
			case "or":new OrConnection(djp,jPanel,orList.get(selectK),statusList).setVisible(true);
				break;
			case "inor":new InorConnection(djp,jPanel,inorList.get(selectK),statusList).setVisible(true);
				break;
			case "eor":new EorConnection(djp,jPanel,eorList.get(selectK),statusList).setVisible(true);
				break;
			case "only":new OnlyConnection(djp,jPanel,onlyList.get(selectK),statusList).setVisible(true);
				break;
			case "require":new RequireConnection(djp,jPanel,requireList.get(selectK),statusList).setVisible(true);
				break;
			default:
			}
			
		}
		if(e.getSource() == delConItem) {
			ConnectionHandle ch = new ConnectionHandle();
			switch(selectType) {
			case "identical":ch.deleteIdentical(identicalList,pointList,selectK);
				break;
			case "not":ch.deleteNot(notList,pointList,selectK);
				break;
			case "and":ch.deleteAnd(andList,pointList,selectK);
				break;
			case "or":ch.deleteOr(orList,pointList,selectK);
				break;
			case "inor":ch.deleteInor(inorList,pointList,selectK);
				break;
			case "eor":ch.deleteEor(eorList,pointList,selectK);
				break;
			case "only":ch.deleteOnly(onlyList,pointList,selectK);
				break;
			case "require":ch.deleteRequire(requireList,pointList,selectK);
				break;
			default:
			}
			jPanel.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
			
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
			
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		if (e.getButton() == MouseEvent.BUTTON3) {
//				jPanel.add(popupMenu);
			if(searchConnection(x,y,pointList) == false) {
				popupMenuList();
				popupMenu.show(jPanel,x, y);
			}
			else {
				conMenuList();
				conMenu.show(jPanel,x, y);
				System.out.println(selectType + selectK);
			}
			
		}
		if(e.getClickCount() == 2) {
			if(searchConnection(x,y,pointList) == true) {
				switch(selectType) {
				case "identical":new IdenticalConnection(djp,jPanel,identicalList.get(selectK),statusList).setVisible(true);
					break;
				case "not":new NotConnection(djp,jPanel,notList.get(selectK),statusList).setVisible(true);
					break;
				case "and":new AndConnection(djp,jPanel,andList.get(selectK),statusList).setVisible(true);
					break;
				case "or":new OrConnection(djp,jPanel,orList.get(selectK),statusList).setVisible(true);
					break;
				case "inor":new InorConnection(djp,jPanel,inorList.get(selectK),statusList).setVisible(true);
					break;
				case "eor":new EorConnection(djp,jPanel,eorList.get(selectK),statusList).setVisible(true);
					break;
				case "only":new OnlyConnection(djp,jPanel,onlyList.get(selectK),statusList).setVisible(true);
					break;
				case "require":new RequireConnection(djp,jPanel,requireList.get(selectK),statusList).setVisible(true);
					break;
				default:
				}
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
			
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
			
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
			
	}
	private static boolean searchConnection(int x, int y, List<SignPoint> pointList) {
		// TODO Auto-generated method stub
		boolean result = false; 
		SignPoint point ;
		List<SignPoint> testPoint = new ArrayList<SignPoint>();
		a:for(int i = 0;i < pointList.size();i ++) {
			point = pointList.get(i);
			if(point != null) {
				testPoint.add(point);
			}
			else {
				boolean bool = isContain(x,y,testPoint);
				if(bool == true) {
					selectType = testPoint.get(0).getType();
					selectK =  testPoint.get(0).getI();
//					ConnectionHandle ch = new ConnectionHandle();
//					ch.ChearchConnection(testPoint.get(0).getType(), testPoint.get(0).getI());
					result = true;
					break a;
				}
				testPoint = new ArrayList<SignPoint>();
			}
		}
		return result;
	}
	public static boolean isContain(int x,int y,List<SignPoint> pointList) {
		boolean bool = false;
		List<Integer>  xlist = new ArrayList<Integer>();
		List<Integer>  ylist = new ArrayList<Integer>();
		int up = pointList.get(0).getY(),down = pointList.get(0).getY(),left = pointList.get(0).getX(),right = pointList.get(0).getX();
		for(SignPoint point : pointList) {
			xlist.add(point.getX());
			ylist.add(point.getY());
			if(point.getY() < up)
				up = point.getY();
			else if(point.getY() > down)
				down = point.getY();
			if(point.getX() < left)
				left = point.getX();
			else if(point.getX() > right)
				right = point.getX();
		}
		if (x < left || x > right || y < up || y > down) {
			return bool;
		}
		Integer[] verty = ylist.toArray(new Integer[xlist.size()]);
		Integer[] vertx = xlist.toArray(new Integer[xlist.size()]);
		bool = pnpoly(pointList.size(),vertx,verty,x,y);
		return bool;
	}
	public static boolean pnpoly(int nvert, Integer[] vertx, Integer[] verty, int testx, int testy)
	{
		int i, j;
		Boolean bool = false;
		for (i = 0, j = nvert-1; i < nvert; j = i++) {
			if ( ((verty[i]>testy) != (verty[j]>testy)) && 
					(testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
				bool = !bool;
		}
		return bool;
	}
}
