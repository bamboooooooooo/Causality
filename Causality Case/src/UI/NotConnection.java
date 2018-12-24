package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import DoItem.ConnectionHandle;
import DoItem.DrawJpanel;
import po.Not;
import po.Status;

public class NotConnection extends JFrame{
	private Container con;
	private JLabel inputLabel,outputLabel;
	private JPanel upJpanel,centerJpanel,downJpanel,leftJpanel,rightJpanel;
	private JRadioButton statusButton;
	private ButtonGroup inputGroup,outputGroup;
	private JButton OKButton,cancelButton,deleteButton;
	private JPanel jpanel;
	private DrawJpanel drawJpanel;
	private List<Status> statusList;
	private Status start;
	private Status end;
	private JScrollPane leftScrollPane,rightScrollPane;
	private String inputSta,outputSta;
	private Not not;
	private ConnectionHandle ch;
	
	public NotConnection(DrawJpanel djp,JPanel jp,Not not,List<Status> staList) {
		this.drawJpanel = djp;
		this.jpanel = jp;
		this.statusList = new ArrayList<Status>(staList);
		start = new Status();
		end = new Status();
		if(not != null) {
			this.start = not.getStart();
			this.end = not.getEnd();
		}
		this.inputSta = start.getName();
		this.outputSta = end.getName();
		// TODO Auto-generated constructor stub
		createnotConnection();
	}

	private void createnotConnection() {
		// TODO Auto-generated method stub
		setTitle("输入-输出关系（恒等）");
		setSize(300, 350);
		setLocationRelativeTo(jpanel);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ch = new ConnectionHandle();
		
		inputLabel = new JLabel("输入");
		outputLabel = new JLabel("输出");
		OKButton = new JButton("确定");
		cancelButton = new JButton("取消");
		deleteButton = new JButton("删除");
		OKButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub
				createIdentical(statusList);
			}
		});		
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub 
				dispose();
			}
	    });	
		deleteButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) { 
			// TODO Auto-generated method stub
				if(not != null)
					ch.deleteNot(drawJpanel.getNotList(), drawJpanel.getPointList(), drawJpanel.getNotList().indexOf(not));
				jpanel.repaint();
				dispose();
			}
		});	
		
		upJpanel = new JPanel();
		centerJpanel = new JPanel();
		downJpanel = new JPanel();
		leftJpanel = new JPanel();
		rightJpanel = new JPanel();
		
		upJpanel.add(inputLabel);
		upJpanel.add(outputLabel);
		upJpanel.setLayout(new GridLayout(1, 2));
		upJpanel.setSize(350, 50);
		upJpanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		inputGroup = new ButtonGroup();
		outputGroup = new ButtonGroup();
		for(Status status : statusList) {
			statusButton = new JRadioButton(status.getName());
			if(status == start)
				statusButton.setSelected(true);
			statusButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) { 
				// TODO Auto-generated method stub 
					inputSta = e.getActionCommand();
				}
		    });	
			inputGroup.add(statusButton);
			leftJpanel.add(statusButton);
		}
		for(Status status : statusList) {
			statusButton = new JRadioButton(status.getName());
			if(status == end) {
				statusButton.setSelected(true);
			}
			statusButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) { 
				// TODO Auto-generated method stub 
					outputSta = e.getActionCommand();
				}
		    });	
			outputGroup.add(statusButton);
			rightJpanel.add(statusButton);
		}
		BoxLayout leftlayout=new BoxLayout(leftJpanel, BoxLayout.Y_AXIS); 
		BoxLayout rightlayout=new BoxLayout(rightJpanel, BoxLayout.Y_AXIS); 
		
		leftJpanel.setLayout(leftlayout);
		rightJpanel.setLayout(rightlayout);
		
		leftScrollPane = new JScrollPane(leftJpanel);
		leftScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		leftScrollPane.setBorder(BorderFactory.createEtchedBorder());
		rightScrollPane = new JScrollPane(rightJpanel);
		rightScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		rightScrollPane.setBorder(BorderFactory.createEtchedBorder());
		
		centerJpanel.add(leftScrollPane);
		centerJpanel.add(rightScrollPane);
		centerJpanel.setLayout(new GridLayout(1, 2));
		centerJpanel.setSize(350, 200);
		
		downJpanel.add(OKButton);
		downJpanel.add(cancelButton);
		downJpanel.setLayout(new FlowLayout(1,40,20));
		downJpanel.setSize(350, 50);
		
		con.add(Box.createRigidArea (new Dimension(15, 15)));
		con.add(upJpanel,BorderLayout.NORTH);
		con.add(centerJpanel,BorderLayout.CENTER);
		con.add(downJpanel,BorderLayout.SOUTH);
	}

	protected void createIdentical(List<Status> staList) {
		// TODO Auto-generated method stub
		if(inputSta.equals(outputSta)) {
			String message = "不能添加自身的连接！";
			JOptionPane.showMessageDialog(this, message);
			return;
		}
		Status startSta = null,endSta = null;
		 for(Status status : staList) {
			 if(status.getName().equals(inputSta)) {
//				 for(Status sta : status.getConnection()) {
//					 if(sta.getName().equals(outputSta)) {
//						 String message = "当前状态之间已存在连接！";
//							JOptionPane.showMessageDialog(this, message);
//							return;
//					 }	 
//				 }
				 startSta = status;
			 }
			 else if(status.getName().equals(outputSta)) {
				 endSta = status;
			 }
			 else
				 continue;
		 }	
		 ch = new ConnectionHandle();
		 if(not != null)
			 ch.deleteNot(drawJpanel.getNotList(),drawJpanel.getPointList(),drawJpanel.getNotList().indexOf(not));
		 ch.createIdentical(drawJpanel, startSta, endSta);
		 dispose();
	}

}
