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
	private String path = "E:\\Causality Case\\";// �洢·��
	private JPanel mainPanel, fileMuneJPanel, leftPanel, rightPanel;
	private JTree jtree;
	private JMenu fileMenu, editMenu, implementMenu, helpMenu;// �˵�
	// �����˵�private JMenu
	private JMenu newItem;
	private JMenuItem importItem, saveItem, saveasItem, exitItem, newproject, newgraph;// �ļ��Ӳ˵�
	private JMenuItem undoItem, redoItem, cutItem, copyItem, pasteItem, selectallItem;/// �༭�Ӳ˵�
	private JMenuItem caseItem, saveGraphItem;// ִ���Ӳ˵�
	private JMenuItem helpItem, aboutItem;// �����Ӳ˵�
	private JPopupMenu popupMenu, nodePopupMenu, rootPopupMenu;
	private JMenuItem newProject, importProject;// �һ�Ŀ¼
	private JMenuItem newDigraph, importDigraph, deleteDigraph, deleteProject;
	private static JTabbedPane workTabbedPane;
	private static JTextArea jta;
	JScrollPane scrollPane;
	JTabbedPane leftTabbedPane;
	int i = 0, j = 0;
	private String projectname = "";
	private String fileselect = "";
	private String fileparent = "";
	//�һ�Ŀ¼
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
		// �˵���ʼ��
		initMenuBar();
		// ������ʼ��
		initMainPanel();

	}

	private void initMenuBar() {
		JMenuBar mainMenuBar;
		mainMenuBar = new JMenuBar();

		fileMenu = new JMenu("�ļ�");
		newItem = new JMenu("�½�");
		importItem = new JMenuItem("����");
		saveItem = new JMenuItem("����");
		saveasItem = new JMenuItem("���Ϊ");
		exitItem = new JMenuItem("�˳�");
		importItem.addActionListener(this);
		saveItem.addActionListener(this);
		saveasItem.addActionListener(this);
		exitItem.addActionListener(this);

		fileMenu.add(newItem);
		fileMenu.add(importItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.add(exitItem);
		newproject = new JMenuItem("�½���Ŀ");
		newgraph = new JMenuItem("�½�ͼ");
		newproject.addActionListener(this);
		newgraph.addActionListener(this);
		newItem.add(newproject);
		newItem.add(newgraph);

		editMenu = new JMenu("�༭");
		undoItem = new JMenuItem("����");
		redoItem = new JMenuItem("ǰ��");
		cutItem = new JMenuItem("����");
		copyItem = new JMenuItem("����");
		pasteItem = new JMenuItem("ճ��");
		selectallItem = new JMenuItem("ȫѡ");
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(selectallItem);

		implementMenu = new JMenu("ִ��");
		caseItem = new JMenuItem("���ɲ�������");
		saveGraphItem = new JMenuItem("ͼƬ����");
		caseItem.addActionListener(this);
		implementMenu.add(caseItem);
		implementMenu.add(saveGraphItem);

		helpMenu = new JMenu("����");
		helpItem = new JMenuItem("����");
		aboutItem = new JMenuItem("����");
		helpMenu.add(helpItem);
		helpMenu.add(aboutItem);

		mainMenuBar.add(fileMenu);
		mainMenuBar.add(editMenu);
		mainMenuBar.add(implementMenu);
		mainMenuBar.add(helpMenu);

		setJMenuBar(mainMenuBar);
	}

	/** ��ʼ����������� */
	private void initMainPanel() {
		// ������panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());
		addWindowListener(this);

		// ���ڷָ���ң�
		JSplitPane leftSplitPane = new JSplitPane();
		// �߿�����
		leftSplitPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		// ���÷ָ��߳�ʼλ�ã�����*0.15��
		leftSplitPane.setResizeWeight(0.12);
		// ���÷ָ����ܷ��ƶ�
		leftSplitPane.setEnabled(true);

		// ��������Panel
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanel.setLayout(new GridLayout());
		rightPanel.setLayout(new GridLayout());
		// ��ǩPanel
		leftTabbedPane = new JTabbedPane();

		viewMenuList();

		leftTabbedPane.add("��ĿĿ¼", fileMuneJPanel);
		leftPanel.add(leftTabbedPane);
		workTabbedPane = new JTabbedPane();
		rightPanel.add(workTabbedPane);

		// Panel���
		leftSplitPane.setLeftComponent(leftPanel);
		leftSplitPane.setRightComponent(rightPanel);
		mainPanel.add(leftSplitPane);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
	}

	// ��̬���ô��ڴ�С
	private void do_this_windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		Toolkit toolkit = getToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int width = (int) (screenSize.width * 0.8);
		int height = (int) (screenSize.height * 0.8);
		setSize(width, height);
		setLocationRelativeTo(null); // ����λ����Ļ����
	}

	// Ŀ¼����ʾ
	public void viewMenuList() {
		fileMuneJPanel = new JPanel();
		jtree = new JTree();
		jtree = FileInfoHandle.fileInfoList(path);

		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		if (jtree != null) {
			jtree.addMouseListener(this);// ����������ѡ�����
			scrollPane.setViewportView(jtree);
		}
		fileMuneJPanel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		fileMuneJPanel.setLayout(new BoxLayout(fileMuneJPanel, BoxLayout.X_AXIS));
	}

	// �����˵�
	public void popupMenuList() {
		// Project Menu����Ҽ�
		popupMenu = new JPopupMenu();// ����һ������ʽ�˵�
		scrollPane.addMouseListener(this);

		newProject = new JMenuItem("�½���Ŀ");
		importProject = new JMenuItem("������Ŀ");
		newProject.addActionListener(this);
		importProject.addActionListener(this);
		popupMenu.add(newProject);
		popupMenu.add(importProject);
	}

	// //�ļ�����
	// private void importProgect() {
	// // TODO Auto-generated method stub
	// new importProjectDialog().setVisible(true);
	// repairMenu();
	// }

	// ����
	public void repairMenu() {
		leftTabbedPane.removeAll();
		viewMenuList();
		leftTabbedPane.add("��ĿĿ¼", fileMuneJPanel);
		leftTabbedPane.updateUI();

		mainPanel.repaint();
	}

	// �ļ���ȡ
	public void readfile(String filePath, String fileName) {
		jta = new JTextArea();// �ı�����
		JScrollPane jsp = new JScrollPane(jta); // ������

		String str = FileHandle.readFile(filePath);
		jta.append(str + "\n");
		workTabbedPane.add(fileName, jsp);
		workTabbedPane.setTabComponentAt(workTabbedPane.getTabCount() - 1, new TabbedPanel(workTabbedPane));
	}

	// Project Menu����Ҽ�
	public void rootPopupMenuList() {
		rootPopupMenu = new JPopupMenu();// ����һ������ʽ�˵�
		scrollPane.addMouseListener(this);

		newDigraph = new JMenuItem("�½����ͼ");
		importDigraph = new JMenuItem("�������ͼ");
		deleteProject = new JMenuItem("ɾ����Ŀ");
		newDigraph.addActionListener(this);
		importDigraph.addActionListener(this);
		deleteProject.addActionListener(this);
		rootPopupMenu.add(newDigraph);
		rootPopupMenu.add(importDigraph);
		rootPopupMenu.add(deleteProject);
	}
	// Project Menu����Ҽ�
	public void nodePopupMenuList() {
		nodePopupMenu = new JPopupMenu();// ����һ������ʽ�˵�
		scrollPane.addMouseListener(this);

		deleteDigraph = new JMenuItem("ɾ��");
		deleteDigraph.addActionListener(this);
		nodePopupMenu.add(deleteDigraph);
	}

	//�����˵�
		public void panelPopupMenuList(){
			//Project Menu����Ҽ�
			popupMenu = new JPopupMenu();//����һ������ʽ�˵�
//			jp.addMouseListener(this);
//			jp.add(popupMenu);
			newConnect = new JMenu("��ӹ���");
			causality = new JMenu("����-�����ϵ");
			inputrelation = new JMenu("Լ����ϵ");
			identical = new JMenuItem("�����");
			and = new JMenuItem("����");
			or = new JMenuItem("�Ż�");
			not = new JMenuItem("�׷�");
			inor = new JMenuItem("|����");
			eor = new JMenuItem("E����");
			only = new JMenuItem("OΨһ");
			require = new JMenuItem("RҪ��");
			newState = new JMenuItem("���״̬");
			
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
	// �ļ�����
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

	// �ļ����Ϊ
	public static void saveAsFile() {
		int state;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		state = chooser.showDialog(new JLabel(), "���Ϊ");
		String path = "";
		if (state == 0)
			path = chooser.getSelectedFile().getPath() + "//";
		String text = jta.getText();
		int no = workTabbedPane.getSelectedIndex();
		String filename = workTabbedPane.getTitleAt(no);
		FileHandle.saveFile(filename, path, text);
	}

	// �¼�����
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == newProject || e.getSource() == newproject) {// �½���Ŀ
			new newProjectDialog(mui).setVisible(true);
		}
		if (e.getSource() == importProject || e.getSource() == importItem) {// ������Ŀ
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
			System.exit(0);// ϵͳ�˳�
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

	// ������
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// ���ڵ����

		DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) jtree.getLastSelectedPathComponent();
		if (e.getButton() == MouseEvent.BUTTON3 && selectionNode != null) {
			if (selectionNode.isLeaf() && !selectionNode.getParent().toString().equals("��Ŀ")) { // Ҷ�ӽڵ�ļ���
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
		if (e.getClickCount() == 2) { // ���˫���¼�
			if (selectionNode.isLeaf()) { // Ҷ�ӽڵ�ļ���
				fileselect= selectionNode.toString();
				fileparent = selectionNode.getParent().toString();
				String filePath = path + selectionNode.getParent().toString() + "\\";
				if(fileselect.endsWith(".xml")){
					if (FileHandle.selectFile(fileselect,fileparent) == false) {
						int index = fileselect.indexOf(".");
						String digraphname = fileselect.substring(0, index);
						FileInfoHandle.delFileInfo(fileselect,filePath);
						FileInfoHandle.delFileInfo(digraphname, filePath);
						String message = "���ļ������ڣ�";
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
						String message = "��ǰ���ͼ�����ڣ�";
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

	//��ȡXML�ļ�
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
		System.exit(0);// ϵͳ�˳�
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
