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
	//�һ�Ŀ¼
	private JMenu newConnectMenu,causalityMenu,inputrelationMenu;
	private JMenuItem identicalItem,andItem,orItem,notItem;
	private JMenuItem inorItem,eorItem,onlyItem,requireItem;
	private JMenuItem newStateItem;
	private int r = 30;
	
	private List<Status> statusList;//״̬
	private List<Identical> identicalList;//���
	private List<And> andList;//��
	private List<Or> orList;//��
	private List<Not> notList;//��
	private List<Inor> inorList;//����
	private List<Eor> eorList;//����
	private List<Only> onlyList;//Ψһ
	private List<Require> requireList;//Ҫ��
	
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
	private List<SignPoint> pointList;// ����Ϣ������
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

	//�����ʼ��
	public JPanel initJpanel(String fileName,String filePath){
		jPanel = new MyPanel();//��������
		jPanel.setBackground(Color.WHITE);
		jPanel.addMouseListener(this);//Ϊ�������Ӽ���
		jPanel.addMouseMotionListener(this);
		path = filePath + fileName;
		xmlhandle.readXML(path);//��ȡXML�ļ�����
		spList = new ArrayList<StatusPanel>();
		lpList = new ArrayList<LinePanel>();
		statusList = xmlhandle.getStatusList();//��ȡ״̬�б�
		identicalList = xmlhandle.getIdenticalList();
		andList = xmlhandle.getAndList();
		orList = xmlhandle.getOrList();
		notList = xmlhandle.getNotList();
		inorList = xmlhandle.getInorList();
		eorList = xmlhandle.getEorList();
		onlyList = xmlhandle.getOnlyList();
		requireList = xmlhandle.getRequireList();
		pointList = xmlhandle.getPointList();
		
		initsqList();//״̬����ʼ��
		updateJpanel();
		jPanel.setLayout(null);
		return jPanel;
	}

	//�����޸�
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
	//״̬����ʼ��
	private void initsqList(){
		for(int i = 0;i < statusList.size();i ++){
			sp = new StatusPanel();
			spList.add(sp);
		}
	}
	
	//�����˵�
	public void popupMenuList(){
		//Project Menu����Ҽ�
		popupMenu = new JPopupMenu();//����һ������ʽ�˵�
		newConnectMenu = new JMenu("��ӹ���");
		causalityMenu = new JMenu("����-�����ϵ");
		inputrelationMenu = new JMenu("Լ����ϵ");
		identicalItem = new JMenuItem("�����");
		andItem = new JMenuItem("����");
		orItem = new JMenuItem("�Ż�");
		notItem = new JMenuItem("�׷�");
		inorItem = new JMenuItem("|����");
		eorItem = new JMenuItem("E����");
		onlyItem = new JMenuItem("OΨһ");
		requireItem = new JMenuItem("RҪ��");
		newStateItem = new JMenuItem("���״̬");
		pasteItem = new JMenuItem("ճ��");
			
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
		conMenu = new JPopupMenu();//����һ������ʽ�˵�
//		jPanel.addMouseListener(this);
		proConItem = new JMenuItem("����");
		delConItem = new JMenuItem("ɾ��");
		
		proConItem.addActionListener(this);
		delConItem.addActionListener(this);
		
		conMenu.add(proConItem);
		conMenu.add(delConItem);
	}
	//�½�״̬
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

//	//�½����롱����
//	public void createAnd(And and) {
//		andList.add(and);
//		updateJpanel();
//	}
//	//�½���������
//	public void createOr(Or or) {
//		orList.add(or);
//		updateJpanel();
//	}
//	//�½����ǡ�����
//	public void createNot(Not not) {
//		notList.add(not);
//		updateJpanel();
//	}
//	//�½����������
//	public void createEor(Eor eor) {
//		eorList.add(eor);
//		updateJpanel();
//	}
//	//�½�������������
//	public void createInor(Inor inor) {
//		inorList.add(inor);
//		updateJpanel();
//	}
//	//�½���Ψһ������
//	public void createOnly(Only only) {
//		onlyList.add(only);
//		updateJpanel();
//	}
//	//�½���Ҫ������
//	public void createRequire(Require require) {
//		requireList.add(require);
//		updateJpanel();
//	}
	
	//����״̬����
	public void updateSta(Status sta,int i) {
		statusList.get(i).setName(sta.getName());
	}
	private void deleteStatus(int staseli) {
		// TODO Auto-generated method stub
		if(statusList.get(staseli).getConnection().size() != 0) {
			String message = "��ǰ״̬�������ӹ�ϵ������ɾ����";
			JOptionPane.showMessageDialog(jPanel, message);
			return;
		}
		else {
			jPanel.remove(spList.get(staseli));
			statusList.get(staseli).setState(0);
		}
		jPanel.repaint();
	}
	//״̬������
	public void drawPanel(int i){
		spList.get(i).setOpaque(false);//״̬���͸��
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
	
	//״̬�һ������˵�
	public void staPopupMenu() {
		staPopupMenu = new JPopupMenu();//����һ������ʽ�˵�
		jPanel.addMouseListener(this);
		
		copyItem = new JMenuItem("����");
		cutItem = new JMenuItem("����");
		deleteItem = new JMenuItem("ɾ��");
		searchItem = new JMenuItem("����");
		
		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		deleteItem.addActionListener(this);
		searchItem.addActionListener(this);
		
		staPopupMenu.add(copyItem);
		staPopupMenu.add(cutItem);
		staPopupMenu.add(deleteItem);
		staPopupMenu.add(searchItem);
	}
	
	//״̬�����Ӽ���
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

	//�¼�����	
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
