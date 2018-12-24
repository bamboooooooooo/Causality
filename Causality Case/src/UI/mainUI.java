package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;

import DoItem.FileHandle;
import DoItem.FileInfoHandle;
import DoItem.CaseHandle;
import DoItem.DrawJpanel;
import DoItem.XMLHandle;
import po.FileInfo;

public class mainUI extends JFrame implements MouseListener, ActionListener, WindowListener, MouseMotionListener {
	private mainUI mui = this;
	private String path = "E:\\Causality Case\\";// 存储路径
	private JPanel mainPanel, fileMuneJPanel, leftPanel, rightPanel;
	private JTree jtree;
	private JMenu fileMenu, editMenu, implementMenu, helpMenu;// 菜单
	// 二级菜单private JMenu
	private JMenu newItem;
	private JMenuItem importItem, saveItem, saveasItem, exitItem, newproject, newgraph;// 文件子菜单
	private JMenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, selectallItem;/// 编辑子菜单
	private JMenuItem caseItem, saveGraphItem;// 执行子菜单
	private JMenuItem helpItem, aboutItem;// 帮助子菜单
	private JPopupMenu popupMenu, nodePopupMenu, rootPopupMenu;
	private JMenuItem newProject, importProject;// 右击目录
	private JMenuItem newDigraph, importDigraph, deleteDigraph, deleteProject;
	private static JTabbedPane workTabbedPane;
	private static JTextArea jta;
	JScrollPane scrollPane;
	JTabbedPane leftTabbedPane;
	int i = 0, j = 0;
	private String projectname = "";
	private String fileselect = "";
	private String fileparent = "";
	//右击目录
	private JMenu newConnect,causality,inputrelation;
	private JMenuItem identical,and,or,not;
	private JMenuItem inor,eor,only,require;
	private JMenuItem newState;
	private int type = -1;
	private int x = -1;
	private int y = -1;
	private static DrawJpanel drawJpanel;

	public mainUI() {
		// TODO Auto-generated constructor stub
		setTitle("Causality Case");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 菜单初始化
		initMenuBar();
		// 主面板初始化
		initMainPanel();

	}

	private void initMenuBar() {
		JMenuBar mainMenuBar;
		mainMenuBar = new JMenuBar();

		fileMenu = new JMenu("文件");
		newItem = new JMenu("新建");
		importItem = new JMenuItem("导入");
		saveItem = new JMenuItem("保存");
		saveasItem = new JMenuItem("另存为");
		exitItem = new JMenuItem("退出");
		importItem.addActionListener(this);
		saveItem.addActionListener(this);
		saveasItem.addActionListener(this);
		exitItem.addActionListener(this);

		fileMenu.add(newItem);
		fileMenu.add(importItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.add(exitItem);
		newproject = new JMenuItem("新建项目");
		newgraph = new JMenuItem("新建图");
		newproject.addActionListener(this);
		newgraph.addActionListener(this);
		newItem.add(newproject);
		newItem.add(newgraph);

		editMenu = new JMenu("编辑");
		undoItem = new JMenuItem("撤销");
		redoItem = new JMenuItem("前进");
		cutItem = new JMenuItem("剪切");
		copyItem = new JMenuItem("复制");
		pasteItem = new JMenuItem("粘贴");
		selectallItem = new JMenuItem("全选");
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(selectallItem);

		implementMenu = new JMenu("执行");
		caseItem = new JMenuItem("生成测试用例");
		saveGraphItem = new JMenuItem("图片保存");
		caseItem.addActionListener(this);
		implementMenu.add(caseItem);
		implementMenu.add(saveGraphItem);

		helpMenu = new JMenu("帮助");
		helpItem = new JMenuItem("帮助");
		aboutItem = new JMenuItem("关于");
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(editMenu);
		mainMenuBar.add(implementMenu);
		mainMenuBar.add(helpMenu);

		setJMenuBar(mainMenuBar);
	}

	/** 初始化界面主框架 */
	private void initMainPanel() {
		// 声明主panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());
		addWindowListener(this);

		// 窗口分割（左右）
		JSplitPane leftSplitPane = new JSplitPane();
		// 边框设置
		leftSplitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		// 设置分割线初始位置（窗口*0.15）
		leftSplitPane.setResizeWeight(0.12);
		// 设置分割线能否移动
		leftSplitPane.setEnabled(true);

		// 声明左右Panel
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setLayout(new GridLayout());
		rightPanel.setLayout(new GridLayout());
		// 标签Panel
		leftTabbedPane = new JTabbedPane();

		viewMenuList();

		leftTabbedPane.add("项目目录", fileMuneJPanel);
		leftPanel.add(leftTabbedPane);
		workTabbedPane = new JTabbedPane();
		rightPanel.add(workTabbedPane);

		// Panel添加
		leftSplitPane.setLeftComponent(leftPanel);
		leftSplitPane.setRightComponent(rightPanel);
		mainPanel.add(leftSplitPane);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	// 动态设置窗口大小
	private void do_this_windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		Toolkit toolkit = getToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = (int) (screenSize.width * 0.8);
		int height = (int) (screenSize.height * 0.8);
		setSize(width, height);
		setLocationRelativeTo(null); // 窗口位于屏幕中央
	}

	// 目录栏显示
	public void viewMenuList() {
		fileMuneJPanel = new JPanel();
		jtree = new JTree();
		jtree = FileInfoHandle.fileInfoList(path);

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		if (jtree != null) {
			jtree.addMouseListener(this);// 给整个树加选择监听
			scrollPane.setViewportView(jtree);
		}
		fileMuneJPanel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		fileMuneJPanel.setLayout(new BoxLayout(fileMuneJPanel, BoxLayout.X_AXIS));
	}

	// 弹出菜单
	public void popupMenuList() {
		// Project Menu点击右键
		popupMenu = new JPopupMenu();// 创建一个弹出式菜单
		scrollPane.addMouseListener(this);

		newProject = new JMenuItem("新建项目");
		importProject = new JMenuItem("导入项目");
		newProject.addActionListener(this);
		importProject.addActionListener(this);
		popupMenu.add(newProject);
		popupMenu.add(importProject);
	}

	// //文件导入
	// private void importProgect() {
	// // TODO Auto-generated method stub
	// new importProjectDialog().setVisible(true);
	// repairMenu();
	// }

	// 更新
	public void repairMenu() {
		leftTabbedPane.removeAll();
		viewMenuList();
		leftTabbedPane.add("项目目录", fileMuneJPanel);
		leftTabbedPane.updateUI();

		mainPanel.repaint();
	}

	// 文件读取
	public void readfile(String filePath, String fileName) {
		jta = new JTextArea();// 文本区域
		JScrollPane jsp = new JScrollPane(jta); // 滚动条

		String str = FileHandle.readFile(filePath);
		jta.append(str + "\n");
		workTabbedPane.add(fileName, jsp);
		workTabbedPane.setTabComponentAt(workTabbedPane.getTabCount() - 1, new TabbedPanel(workTabbedPane));
	}

	// Project Menu点击右键
	public void rootPopupMenuList() {
		rootPopupMenu = new JPopupMenu();// 创建一个弹出式菜单
		scrollPane.addMouseListener(this);

		newDigraph = new JMenuItem("新建因果图");
		importDigraph = new JMenuItem("导入因果图");
		deleteProject = new JMenuItem("删除项目");
		newDigraph.addActionListener(this);
		importDigraph.addActionListener(this);
		deleteProject.addActionListener(this);
		rootPopupMenu.add(newDigraph);
		rootPopupMenu.add(importDigraph);
		rootPopupMenu.add(deleteProject);
	}
	// Project Menu点击右键
	public void nodePopupMenuList() {
		nodePopupMenu = new JPopupMenu();// 创建一个弹出式菜单
		scrollPane.addMouseListener(this);

		deleteDigraph = new JMenuItem("删除");
		deleteDigraph.addActionListener(this);
		nodePopupMenu.add(deleteDigraph);
	}

	//弹出菜单
		public void panelPopupMenuList(){
			//Project Menu点击右键
			popupMenu = new JPopupMenu();//创建一个弹出式菜单
//			jp.addMouseListener(this);
//			jp.add(popupMenu);
			newConnect = new JMenu("添加关联");
			causality = new JMenu("输入-输出关系");
			inputrelation = new JMenu("约束关系");
			identical = new JMenuItem("—恒等");
			and = new JMenuItem("∧与");
			or = new JMenuItem("∨或");
			not = new JMenuItem("∽非");
			inor = new JMenuItem("|包含");
			eor = new JMenuItem("E互斥");
			only = new JMenuItem("O唯一");
			require = new JMenuItem("R要求");
			newState = new JMenuItem("添加状态");
			
			newConnect.add(causality);
			newConnect.add(inputrelation);
			causality.add(identical);
			causality.add(and);
			causality.add(or);
			causality.add(not);
			inputrelation.add(inor);
			inputrelation.add(eor);
			inputrelation.add(only);
			inputrelation.add(require);
			
			identical.addActionListener(this);
			and.addActionListener(this);
			or.addActionListener(this);
			not.addActionListener(this);
			inor.addActionListener(this);
			eor.addActionListener(this);
			only.addActionListener(this);
			require.addActionListener(this);
			newState.addActionListener(this);
			
			popupMenu.add(newState);
			popupMenu.add(newConnect);
		}
	// public static void saveFile(){
	// String text = jta.getText();
	// int no = workTabbedPane.getSelectedIndex();
	// String filename = workTabbedPane.getTitleAt(no);
	// FileHandle.saveFile(filename, text);
	// }
	// 文件保存
	public static void saveFile() {
		int no = workTabbedPane.getSelectedIndex();
		String filename = workTabbedPane.getTitleAt(no);
		FileInfo fileinfo = FileInfoHandle.selectFileInfo(filename);
		String path = "";
		path = fileinfo.getFilePath();
		if(filename.endsWith(".xml")) {
			String text = "";
			text = jta.getText();
			FileHandle.saveFile(filename, path, text);
		}
		else {
			XMLHandle.writeXML(filename + ".xml", path, drawJpanel);
		}
	}

	// 文件另存为
	public static void saveAsFile() {
		int state;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		state = chooser.showDialog(new JLabel(), "另存为");
		String path = "";
		if (state == 0)
			path = chooser.getSelectedFile().getPath() + "//";
		String text = jta.getText();
		int no = workTabbedPane.getSelectedIndex();
		String filename = workTabbedPane.getTitleAt(no);
		FileHandle.saveFile(filename, path, text);
	}

	// 事件监听
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newProject || e.getSource() == newproject) {// 新建项目
			new newProjectDialog(mui).setVisible(true);
		}
		if (e.getSource() == importProject || e.getSource() == importItem) {// 导入项目
			new importProjectDialog(mui).setVisible(true);
		}
		if (e.getSource() == importDigraph) {
			new importDigraphDialog(mui, projectname).setVisible(true);
		}
		if (e.getSource() == saveItem) {
			saveFile();
		}
		if (e.getSource() == newDigraph) {
			new NewGraphUI(mui,projectname).setVisible(true);
		}
		if (e.getSource() == saveItem) {
			if (workTabbedPane.getTabCount() != 0)
				saveFile();
		}
		if (e.getSource() == saveasItem) {
			if (workTabbedPane.getTabCount() != 0)
				saveAsFile();
		}
		if (e.getSource() == exitItem) {
			System.exit(0);// 系统退出
		}
		if (e.getSource() == deleteDigraph) {
			String filename = "";
			String digraphname = "";
			FileInfo fileinfo = FileInfoHandle.selectFileByFolder(fileselect, fileparent);
			if (fileinfo.getFileName().matches("(?i).+xml$")) {
				filename = fileinfo.getFileName();
				int index = filename.indexOf(".");
				digraphname = filename.substring(0, index);
			}
			else{
				digraphname = fileinfo.getFileName();
				filename = digraphname + ".xml";
			}
			boolean flag = FileHandle.delFile(filename, fileinfo.getFilePath());
			FileInfoHandle.delFileInfo(digraphname, fileinfo.getFilePath());
			if(flag == true)
				repairMenu();
		}
		if (e.getSource() == deleteProject) {
			FileInfo fileinfo = FileInfoHandle.selectFolderInfo(projectname);
			String location = fileinfo.getFilePath() + projectname;
			boolean flag = FileHandle.delProject(location);
	        FileInfoHandle.delFolderInfo(projectname,fileinfo.getFilePath());
			if(flag){
				repairMenu();
			}
		}	
		if (e.getSource() == caseItem) {
			CaseHandle cah = new CaseHandle();
			cah.createCase(drawJpanel);
		}
	}

	// 鼠标监听
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 树节点监听

		DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
		if (e.getButton() == MouseEvent.BUTTON3 && selectionNode != null) {
			if (selectionNode.isLeaf() && !selectionNode.getParent().toString().equals("项目")) { // 叶子节点的监听
				fileselect = selectionNode.toString();
				fileparent = selectionNode.getParent().toString();
				nodePopupMenuList();
				nodePopupMenu.show(scrollPane, e.getX(), e.getY());
			} else if (selectionNode.isRoot()) {
				popupMenuList();
				popupMenu.show(scrollPane, e.getX(), e.getY());
			} else {
				projectname = selectionNode.toString();
				rootPopupMenuList();
				rootPopupMenu.show(scrollPane, e.getX(), e.getY());
			}

		}
		if (e.getClickCount() == 2) { // 鼠标双击事件
			if (selectionNode.isLeaf()) { // 叶子节点的监听
				fileselect= selectionNode.toString();
				fileparent = selectionNode.getParent().toString();
				String filePath = path + selectionNode.getParent().toString() + "\\";
				if(fileselect.endsWith(".xml")){
					if (FileHandle.selectFile(fileselect,fileparent) == false) {
						int index = fileselect.indexOf(".");
						String digraphname = fileselect.substring(0, index);
						FileInfoHandle.delFileInfo(fileselect,filePath);
						FileInfoHandle.delFileInfo(digraphname, filePath);
						String message = "该文件不存在！";
						JOptionPane.showMessageDialog(getRootPane(), message);
						repairMenu();
					}
					else{	
						String fp  = "";
						fp = filePath + selectionNode.toString();
						System.out.println(fp);
						System.out.println(fileselect);
						readfile(fp, fileselect);
					}
				} 
				else {
					String filename = fileselect + ".xml";
					if (FileHandle.selectFile(filename,fileparent) == false) {
						FileInfoHandle.delFileInfo(fileselect,filePath);
						FileInfoHandle.delFileInfo(filename, filePath);
						String message = "当前因果图不存在！";
						JOptionPane.showMessageDialog(getRootPane(), message);
						repairMenu();
					}
					else{
						FileInfo fileinfo = new FileInfo();
						fileinfo = FileInfoHandle.selectFileByFolder(fileselect, selectionNode.getParent().toString());
						readXML(fileselect, fileinfo.getFilePath());
					}
				}
			}
		}
	}

	//读取XML文件
	private void readXML(String fileName, String filePath) {
		// TODO Auto-generated method stub
		drawJpanel = new DrawJpanel();
		JPanel jPanel = drawJpanel.initJpanel(fileName + ".xml",filePath);
		workTabbedPane.add(fileName, jPanel);
		workTabbedPane.setTabComponentAt(workTabbedPane.getTabCount() - 1,new TabbedPanel(workTabbedPane));
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		do_this_windowOpened(e);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);// 系统退出
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
