package UI;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import DoItem.FileInfoHandle;

public class chooseProjectUI extends JFrame implements TreeSelectionListener{

	private JButton OKButton;
	private JButton cancelButton;
	private JLabel pathLabel;
	private Container con;
	private JScrollPane scrollPane;
	private JTree jtree;
	private String projectname = "";
	public chooseProjectUI(importProjectDialog ipd) {
		// TODO Auto-generated constructor stub
		setTitle("导入");
		setSize(250, 350);
		setLocationRelativeTo(ipd);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		pathLabel = new JLabel("导入到:            ");
		OKButton = new JButton("确认");
		cancelButton = new JButton("取消");
		OKButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub
				ipd.setfilePath(projectname);
				con.removeAll();
				dispose();
			}
		});		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub 
				con.removeAll();
				dispose();
			}
	    });	
		JPanel upPanel = new JPanel(); 
		upPanel.add(pathLabel);
		upPanel.setLayout(new FlowLayout(0,10,10));
		
		JPanel middlePanel = new JPanel(); 
		jtree = new JTree();
		jtree = FileInfoHandle.projectTree();
		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		if(jtree != null){
			jtree.addTreeSelectionListener(this);//给整个树加选择监听
			scrollPane.setViewportView(jtree);
		}
		middlePanel.add(Box.createHorizontalStrut(10));
		middlePanel.add(scrollPane);
		middlePanel.add(Box.createHorizontalStrut(10));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		
		JPanel downPanel = new JPanel(); 
		downPanel.add(OKButton);
		downPanel.add(cancelButton);
		downPanel.setLayout(new FlowLayout(2,10,20));
		
		BoxLayout layout=new BoxLayout(con, BoxLayout.Y_AXIS); 
		con.setLayout(layout);
		con.add(upPanel);
		con.add(middlePanel);
		con.add(downPanel);
	}
	
	public chooseProjectUI(NewGraphUI npui) {
		// TODO Auto-generated constructor stub
		setTitle("项目选择");
		setSize(250, 350);
		setLocationRelativeTo(npui);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		pathLabel = new JLabel("创建因果图到:            ");
		OKButton = new JButton("确认");
		cancelButton = new JButton("取消");
		OKButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub
				npui.setProjectName(projectname);
				con.removeAll();
				dispose();
			}
		});		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub 
				con.removeAll();
				dispose();
			}
	    });	
		JPanel upPanel = new JPanel(); 
		upPanel.add(pathLabel);
		upPanel.setLayout(new FlowLayout(0,10,10));
		
		JPanel middlePanel = new JPanel(); 
		jtree = new JTree();
		jtree = FileInfoHandle.projectTree();
		scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.WHITE);
		if(jtree != null){
			jtree.addTreeSelectionListener(this);//给整个树加选择监听
			scrollPane.setViewportView(jtree);
		}
		middlePanel.add(Box.createHorizontalStrut(10));
		middlePanel.add(scrollPane);
		middlePanel.add(Box.createHorizontalStrut(10));
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
		
		JPanel downPanel = new JPanel(); 
		downPanel.add(OKButton);
		downPanel.add(cancelButton);
		downPanel.setLayout(new FlowLayout(2,10,20));
		
		BoxLayout layout=new BoxLayout(con, BoxLayout.Y_AXIS); 
		con.setLayout(layout);
		con.add(upPanel);
		con.add(middlePanel);
		con.add(downPanel);
	}

	public void valueChanged(TreeSelectionEvent tse) {
		 DefaultMutableTreeNode selectionNode=(DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
		 if(selectionNode.isLeaf())
			 projectname = selectionNode.toString(); 
	}
}
