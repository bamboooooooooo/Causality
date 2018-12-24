package UI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DoItem.FileHandle;
import DoItem.FileInfoHandle;
import po.FileInfo;

public class importDigraphDialog extends JFrame implements ActionListener{
//	private static JDialog jd = this;
	private static Container con;
	private JLabel nameLabel,pathLabel;
	private static JTextField fileName;
	private static JTextField filePath;
	private JButton searchfileButton,importButton,cancelButton;
	private static String filepath;
//	private static choosePathUI cp;
	private static File file = null;
	private static JPanel upPanel;
	private static JPanel downPanel;
	private static mainUI mainui;
	private static importDigraphDialog idd;
	
	public importDigraphDialog(mainUI mui,String path){
		filepath = path;
		setTitle("导入");
		idd = this;
		setSize(320, 400);
		setLocation(700,300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		con = this.getContentPane();
		nameLabel = new JLabel("选择导入文件:");
		pathLabel = new JLabel("导入到:      ");
		fileName = new JTextField("");
		fileName.setColumns(20);
		filePath = new JTextField(path);
		filePath.setColumns(20);
		filePath.enable(false);
		searchfileButton = new JButton("...");
		importButton = new JButton("导入");
		cancelButton = new JButton("取消");
		searchfileButton.addActionListener(this);
		cancelButton.addActionListener(this);
		importButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				importfile(mui);
			}
		});
		upPanel = new JPanel(); 
		upPanel.setSize(320, 200);
		upPanel.setLayout(new FlowLayout(0,15,20));
		
		upPanel.add(nameLabel);
		upPanel.add(fileName);
		upPanel.add(searchfileButton);
		upPanel.add(pathLabel);
		upPanel.add(filePath);
		
		downPanel = new JPanel(); 
		downPanel.setSize(320, 200);
		downPanel.setLayout(new FlowLayout(1,40,20));
		downPanel.add(importButton);
		downPanel.add(cancelButton);
		
		BoxLayout layout=new BoxLayout(con, BoxLayout.Y_AXIS); 
		con.setLayout(layout);
		con.add(upPanel);
		con.add(downPanel);
		
	}

	public static void setfileName(File f){
		file = f;
		fileName.setText(file.getPath());
		fileName.validate();
	}
	
	private void importfile(mainUI mui){
		if(file == null){
			String message = "请选择待导入文件！";
			JOptionPane.showMessageDialog(getRootPane(), message);
			return;
		}
		
//		FileInfo fileinfo = FileInfoHandle.selectFileInfo(file.getName());
//		if(fileinfo != null){
			try {
				FileInfo f = FileInfoHandle.selectFolderInfo(filepath);
				filepath = f.getFilePath() + f.getFileName() + "\\";
				int flag = FileHandle.importFile(file, filepath);
				if(flag == 0){
					String message = "仅支持导入XMl文件！";
					JOptionPane.showMessageDialog(getRootPane(), message);
					this.validate();
				}
				else if(flag == 1){
					String message = "文件已存在！";
					JOptionPane.showMessageDialog(getRootPane(), message);
					this.validate();
				}
				else{
					String message = "导入成功！";
					JOptionPane.showMessageDialog(getRootPane(), message);
					dispose();
					mui.repairMenu();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == searchfileButton){
			chooseFileUI cf= new chooseFileUI(idd);
		}
		if(e.getSource() == cancelButton){
			fileName.setText(""); 
		}
		
	}
}
