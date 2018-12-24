package UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DoItem.FileHandle;
import DoItem.FileInfoHandle;
import po.FileInfo;

public class newProjectDialog extends JFrame{

	Container con;
	private JLabel nameLabel,addressLabel;
	private JTextField projectName,address;
	private JButton searchButton,createButton,cancelButton;
	String projectname = "",digraphname,filename,location = "E:\\Causality Case\\";
	FileHandle filehandle;
//	mainFrame mf = new mainFrame();
	
	public newProjectDialog(mainUI mf) {
		// TODO Auto-generated constructor stub
//		super(new mainUI(), "�½���Ŀ"); // ʵ����һ��JDialog�����ָ���丸���塢���ڱ��������
		setTitle("�½���Ŀ");
		setSize(400, 200);
//		setLocation(700,400);
		setLocationRelativeTo(mf);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		con = this.getContentPane();
		nameLabel = new JLabel("��Ŀ����:");
		addressLabel = new JLabel("����·��:");
		projectName = new JTextField();
		address = new JTextField(location);
		projectName.setColumns(25);
//		fileName.setColumns(25);
		address.setColumns(25);
		address.setEditable(false);
//		searchButton = new JButton("ѡ��·��...");
//		searchButton.addActionListener(new ActionListener(){ 
//			@Override
//			public void actionPerformed(ActionEvent e) { 
//			// TODO Auto-generated method stub 
//	        JFileChooser jfc=new JFileChooser(); 
//	        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES ); 
//	        
//	        jfc.showDialog(new JLabel(), "ѡ��"); 
//	        File file=jfc.getSelectedFile(); 
//	        location = file.getAbsolutePath();
//	        address.setText(location);
//			}
//	    });
		createButton = new JButton("�½�");
		createButton.addActionListener(new ActionListener(){ 
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub 
				projectname = projectName.getText();
				if(projectname.equals("")){
					String message = "��������Ŀ���ƣ�";
					JOptionPane.showMessageDialog(getRootPane(), message);
					return;
				}
				else{
					createProject(mf,projectname,location);
				}
//				filename = fileName.getText();
//				createfile(mf,filename,location);
			}
	    });
		cancelButton = new JButton("ȡ��");
		cancelButton.addActionListener(new ActionListener() {        
			@Override
			public void actionPerformed(ActionEvent e) {
				projectName.setText("");
			}
		});
		
		con.setLayout(new FlowLayout(1,10,20));
		con.add(nameLabel);
		con.add(projectName);
		con.add(addressLabel);
		con.add(address);
//		con.add(searchButton);
		con.add(createButton);
		con.add(cancelButton);
	}
	protected void createProject(mainUI mf, String projectname, String location) {
	// TODO Auto-generated method stub
		FileInfo fi = FileInfoHandle.selectFolderInfo(projectname);
		if(fi == null)
		{
			boolean flag = filehandle.selectProject(projectname,location);
			if(flag == true){
				String message = "����Ŀ�Ѵ��ڣ�";
				JOptionPane.showMessageDialog(getRootPane(), message);
			}
			else{
				boolean res = filehandle.createProject(projectname, location);
				if(res == true){
					location = location + projectname + "\\";
					digraphname = projectname;
					filename = projectname + ".xml";
					int i = filehandle.createFile(filename,digraphname, location);
					if(i == 1){
						String message = "��Ŀ�����ɹ���";
						JOptionPane.showMessageDialog(getRootPane(), message);
						mf.repairMenu();
						dispose();
					} 
				}
				else{
					String message = "��Ŀ����ʧ�ܣ�";
					JOptionPane.showMessageDialog(getRootPane(), message);
				}
			}
		}
		else
		{
			String message = "����Ŀ�Ѵ��ڣ�";
			JOptionPane.showMessageDialog(getRootPane(), message);
		}
}
	public void createfile(mainUI mf,String filename,String location)
	{
		int res = filehandle.createFile(filename, digraphname, location);
		if(res == 1){
			mf.repairMenu();
			dispose();
		}
		else
		{
			String message = "���ļ��Ѵ��ڣ�";
			JOptionPane.showMessageDialog(getRootPane(), message);
		}
	}
}
