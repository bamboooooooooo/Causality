package UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DoItem.FileHandle;
import DoItem.FileInfoHandle;
import DoItem.InitXML;
import po.FileInfo;

public class NewGraphUI extends JFrame{
	private static Container con;
	private JLabel graphNameLabel;
	private JLabel projectNameLabel;
	private static JTextField graphName;
	private static JTextField projectName;
	private JButton createButton;
	private JButton cancelButton;
	private String graphname;
	private static String projectname = "";
//	private JButton searchProjectButton;
	private static JPanel upPanel;
	private static JPanel downPanel;
	private static chooseProjectUI cp;
	private NewGraphUI npui;
	
	public NewGraphUI(mainUI mui,String path){
		projectname = path;
		npui = this;
		this.setTitle("新建因果图");
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		con = this.getContentPane();
		graphNameLabel = new JLabel("因果图名称:");
		graphName = new JTextField();
		graphName.setColumns(20);
		projectNameLabel = new JLabel("项 目 选 择  :");
		projectName = new JTextField(path);
		projectName.setColumns(20);
		projectName.enable(false);
//		searchProjectButton = new JButton("...");
		createButton = new JButton("新建");
		cancelButton = new JButton("取消");
//		searchProjectButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				new chooseProjectUI(npui).setVisible(true);
//			}
//		});
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				graphname = graphName.getText();
				try {
					createDigraph(mui,graphname,projectname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				graphName.setText("");
				projectName.setText("");
			}
		});
		
		upPanel = new JPanel(); 
		downPanel = new JPanel(); 
		upPanel.setLayout(new FlowLayout(0,15,20));
		downPanel.setLayout(new FlowLayout(1,40,10));
		
		upPanel.add(graphNameLabel);
		upPanel.add(graphName);
		upPanel.add(projectNameLabel);
		upPanel.add(projectName);
//		upPanel.add(searchProjectButton);
		downPanel.add(createButton);
		downPanel.add(cancelButton);
		con.add(upPanel);
		con.add(downPanel);
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));
	}
	public static void setProjectName(String pn){
		projectname = pn;
		projectName.setText(projectname);
		projectName.validate();
	}
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		graphname = graphName.getText();
//		if(e.getSource() == createButton){
//			try {
//				createProject(graphname,projectname);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		if(e.getSource() == cancelButton){
//			graphName.setText("");
//			projectName.setSelectedIndex(-1);
//		}
//	}

	private void createDigraph(mainUI mui,String graphname, String projectname) throws IOException {
		// TODO Auto-generated method stub
		if(graphname.matches("")){
			String message = "请添加新建因果图名！";
			JOptionPane.showMessageDialog(getRootPane(), message);
			return;
		}
//		if(projectname.matches("")){
//			String message = "请选择因果图创建目录！";
//			JOptionPane.showMessageDialog(getRootPane(), message);
//			return;
//		}
		System.out.println(projectname);
		FileInfo fileinfo = FileInfoHandle.selectFolderInfo(projectname);
		if(fileinfo != null){
			String filename = graphname + ".xml";
			int flag = FileHandle.createFile(filename, graphname, fileinfo.getFilePath() + projectname + "\\");
			if(flag == 0){
				String message = "创建失败！";
				JOptionPane.showMessageDialog(getRootPane(), message);
				this.validate();
			}
			else if(flag == 1){
				String message = "创建成功！";
				JOptionPane.showMessageDialog(getRootPane(), message);
				dispose();
				mui.repairMenu();
			}
			else{
				String message = "文件已存在！";
				JOptionPane.showMessageDialog(getRootPane(), message);
				this.validate();
			}
		}
	}
	
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		// TODO Auto-generated method stub
//		if(e.getStateChange() == ItemEvent.SELECTED){
//			projectname = (String)projectName.getSelectedItem();
//		}
//	}
}
