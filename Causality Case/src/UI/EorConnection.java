package UI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import DoItem.ConnectionHandle;
import DoItem.DrawJpanel;
import po.Eor;
import po.Status;

public class EorConnection extends JFrame implements ActionListener{
	private EorConnection ec;
	private DrawJpanel drawJpanel;
	private JPanel jpanel;
	private List<Status> statusList;
	private List<Status> selList;
	private Container con;
	private JButton addButton;
	private JButton removeButton;
	private DefaultListModel inputModel;
	private JList inputJList;
	private JButton OKButton;
	private JButton delButton;
	private JButton cancelButton;
	private JScrollPane jScrollPane;
	private Eor eor;

	public EorConnection(DrawJpanel djp,JPanel jp,Eor eor,List<Status> staList) {
		// TODO Auto-generated constructor stub
		ec = this;
		this.statusList = new ArrayList<Status>(staList);
		this.eor = eor;
		if(eor != null) {
			this.selList = new ArrayList<Status>(eor.getStatusList());
		}
		else {
			this.selList = new ArrayList<Status>();  
		}
		Iterator<Status> iterator = statusList.iterator();  
	    while(iterator.hasNext()) {  
	    	Status status = iterator.next();
	    	for(Status sta : selList) {
	    		if(status == sta) {
	    			iterator.remove();
	    		}
	    	}
	     }
		this.drawJpanel = djp;
		this.jpanel = jp;
		createEorConnection();
	}

	private void createEorConnection() {
		// TODO Auto-generated method stub
		setTitle("输入约束关系（异或）");
		setSize(450, 550);
		setLocationRelativeTo(jpanel);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		addButton = new JButton("add");
		removeButton = new JButton("remove");
		inputModel = new DefaultListModel();
		if(selList != null) {
			for(Status status : selList) {
				inputModel.addElement(status.getName());
			}
		}
		inputJList = new JList(inputModel);
		OKButton = new JButton("确定");
		delButton = new JButton("删除");
		cancelButton = new JButton("取消");

		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		OKButton.addActionListener(this);
		delButton.addActionListener(this);
		cancelButton.addActionListener(this);
		jScrollPane = new JScrollPane(inputJList);
		

		addButton.setBounds(320, 40, 80, 30);
		removeButton.setBounds(320, 90, 80, 30);
		jScrollPane.setBounds(30, 40, 250, 420);
		OKButton.setBounds(320, 325, 80, 30);
		delButton.setBounds(320, 375, 80, 30);
		cancelButton.setBounds(320, 425, 80, 30);
		
		con.add(jScrollPane);
		con.add(addButton);
		con.add(removeButton);
		con.add(OKButton);
		con.add(delButton);
		con.add(cancelButton);
		con.setLayout(null);
	}

	public void setInputList(List<Status> selList, List<Status> staList) {
		// TODO Auto-generated method stub
		this.selList = new ArrayList(selList);
		this.statusList = new ArrayList(staList);
		inputModel.removeAllElements();
		for(Status status : selList) {
			inputModel.addElement(status.getName());
		}
		System.out.println("sss");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		ConnectionHandle ch = new ConnectionHandle();
		if(e.getSource() == addButton) {
			new AddStatusList(ec,statusList,selList).setVisible(true);
		}
		if(e.getSource() == removeButton) {
			int selSta = -1;
			selSta = inputJList.getSelectedIndex();
			if(selSta != -1) {
				for(Status status : selList) {
					if(status.getName().equals(inputModel.elementAt(selSta))){
						selList.remove(status);
						statusList.add(status);
						break;
					}
				}
				inputModel.removeElementAt(selSta);
			}
		}
		if(e.getSource() == OKButton) {
			if(selList.size() < 2) {
				String message = "请选择至少两个输入状态！";
				JOptionPane.showMessageDialog(this, message);
				return;
			}
			else {
				if(eor != null)
					ch.deleteEor(drawJpanel.getEorList(), drawJpanel.getPointList(), drawJpanel.getEorList().indexOf(eor));
				ch.createEor(drawJpanel,selList);
				jpanel.repaint();
				dispose();
			}
		}
		if(e.getSource() == delButton) {
			if(eor != null) {
				ch.deleteEor(drawJpanel.getEorList(), drawJpanel.getPointList(), drawJpanel.getEorList().indexOf(eor));
				jpanel.repaint();
				dispose();
			}
			else 
				dispose();
		}
		if(e.getSource() == cancelButton) {
			dispose();
		}
	}

}
