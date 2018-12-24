package UI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import DoItem.ConnectionHandle;
import DoItem.DrawJpanel;
import po.And;
import po.Status;

@SuppressWarnings("serial")
public class AndConnection extends JFrame implements ActionListener{
	private Container con;
	private JLabel inputLabel;
	private JLabel outputLabel;
	private JButton OKButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JButton addButton1,addButton2;
	private JButton removeButton1,removeButton2;
	private JList inputJList,outputJList;
	private JPanel upJpanel,centerJpanel,downJpanel;
	private JPanel leftBtnJpanel,rightBtnJpanel;
	private JScrollPane leftScrollPane,rightScrollPane;
	private DefaultListModel inputModel,outputModel;
	private List<Status> inputList,statusList;
	private Status outputStatus;
	private AndConnection ac = this;
	private JPanel jpanel;
	private DrawJpanel drawJpanel;
	private And and;
	
	public AndConnection(DrawJpanel djp,JPanel jp,And and,List<Status> staList) {
		// TODO Auto-generated constructor stub
		inputList = new ArrayList<Status>();
		this.statusList = new ArrayList(staList);
		this.and = and;
		if(and != null) {
			this.inputList = new ArrayList<Status>(and.getStartList());
			this.outputStatus = and.getEnd();
			Iterator<Status> iterator = statusList.iterator();  
		    while(iterator.hasNext()) {  
		    	Status status = iterator.next();
		    	for(Status sta : inputList) {
		    		if(status == sta) {
		    			iterator.remove();
		    		}
		    	}
		    	if(status == outputStatus) {
		    		iterator.remove();
		    	}
		     }  
		}
		this.drawJpanel = djp;
		this.jpanel = jp;
		createAndConnection();
	}
	public void createAndConnection() {
		setTitle("输入-输出关系（与）");
		setSize(550, 600);
		setLocationRelativeTo(jpanel);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		inputLabel = new JLabel("输入");
		outputLabel = new JLabel("输出");
		OKButton = new JButton("确定");
		cancelButton = new JButton("取消");
		deleteButton = new JButton("删除");
		addButton1 = new JButton("add");
		addButton1.setBounds(10, 10, 80, 30);
		addButton2 = new JButton("add");
		addButton2.setBounds(10, 10, 80, 30);
		removeButton1 = new JButton("remove");
		removeButton1.setBounds(10, 50, 80, 30);
		removeButton2 = new JButton("remove");
		removeButton2.setBounds(10, 50, 80, 30);
		inputModel = new DefaultListModel();
		for(Status status : inputList) {
			inputModel.addElement(status.getName());
		}
		inputJList = new JList(inputModel);
		outputModel = new DefaultListModel();
		if(outputStatus != null)
			outputModel.addElement(outputStatus.getName());
		outputJList = new JList(outputModel);
		
		OKButton.addActionListener(this);
		cancelButton.addActionListener(this);
		deleteButton.addActionListener(this);
		addButton1.addActionListener(this);
		addButton2.addActionListener(this);
		removeButton1.addActionListener(this);
		removeButton2.addActionListener(this);
		
		leftScrollPane = new JScrollPane(inputJList);
		rightScrollPane = new JScrollPane(outputJList);
		leftBtnJpanel = new JPanel();
		rightBtnJpanel = new JPanel();
		upJpanel = new JPanel();
		centerJpanel = new JPanel();
		downJpanel = new JPanel();
		
		leftBtnJpanel.add(addButton1);
		leftBtnJpanel.add(removeButton1);
		leftBtnJpanel.setLayout(null);
		rightBtnJpanel.add(addButton2);
		rightBtnJpanel.add(removeButton2);
		rightBtnJpanel.setLayout(null);
		
		upJpanel.add(inputLabel);
		upJpanel.add(outputLabel);
		upJpanel.setLayout(new GridLayout(1, 2));
		upJpanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		centerJpanel.add(leftScrollPane);
		centerJpanel.add(leftBtnJpanel);
		centerJpanel.add(rightScrollPane);
		centerJpanel.add(rightBtnJpanel);
		centerJpanel.setLayout(new GridLayout(1, 3));
		
		downJpanel.add(OKButton);
		downJpanel.add(deleteButton);
		downJpanel.add(cancelButton);
		downJpanel.setLayout(new FlowLayout(2,10,10));
		
		con.add(Box.createRigidArea (new Dimension(15, 15)));
		con.add(upJpanel,BorderLayout.NORTH);
		con.add(centerJpanel,BorderLayout.CENTER);
		con.add(downJpanel,BorderLayout.SOUTH);
	}
	public void setOutputStatus(String staname) {
		for(Status sta : statusList) {
			if(sta.getName().equals(staname)) {
				outputStatus = sta;
				break;
			}
		}
		outputModel.addElement(outputStatus.getName());
	}
	public void setInputList(List<Status> inputList,List<Status> staList){
		this.inputList = inputList;
		this.statusList = staList;
		inputModel.removeAllElements();
		for(Status status : inputList) {
			inputModel.addElement(status.getName());
		}
		repaint();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		ConnectionHandle ch = new ConnectionHandle();
		if(arg0.getSource() == addButton1) {
			new AddStatusList(ac,statusList,inputList).setVisible(true);
		}
		if(arg0.getSource() == addButton2) {
			if(outputStatus == null) {
				 new AddStatus(ac,statusList).setVisible(true);
			}
			else {
				String message = "只允许存在一个输出状态！";
				JOptionPane.showMessageDialog(ac, message);
			}
		}
		if(arg0.getSource() == removeButton1) {
			int selSta = -1;
			selSta = inputJList.getSelectedIndex();
			if(selSta != -1) {
				for(Status status : inputList) {
					if(status.getName().equals(inputModel.elementAt(selSta))){
						inputList.remove(status);
						statusList.add(status);
						break;
					}
				}
				inputModel.removeElementAt(selSta);
			}
		}
		if(arg0.getSource() == removeButton2) {
			int selSta = -1;
			selSta = outputJList.getSelectedIndex();
			if(selSta != -1) {
				outputModel.removeElementAt(selSta);
				outputStatus = null;
			}
				
		}
		if(arg0.getSource() == OKButton) {
			if(inputList.size() < 2) {
				String message = "请选择至少两个输入状态！";
				JOptionPane.showMessageDialog(this, message);
				return;
			}
			if(outputStatus == null) {
				String message = "请选择输出状态！";
				JOptionPane.showMessageDialog(this, message);
				return;
			}
			if(and != null)
				ch.deleteAnd(drawJpanel.getAndList(), drawJpanel.getPointList(), drawJpanel.getAndList().indexOf(and));
			ch.createAnd(drawJpanel,inputList,outputStatus);
			jpanel.repaint();
			dispose();
		}
		if(arg0.getSource() == deleteButton) {
			if(and != null)
				ch.deleteAnd(drawJpanel.getAndList(), drawJpanel.getPointList(), drawJpanel.getAndList().indexOf(and));
			jpanel.repaint();
			dispose();
		}
		if(arg0.getSource() == cancelButton) {
			dispose();
		}
	}

}
