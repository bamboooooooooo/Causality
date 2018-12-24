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
import po.Only;
import po.Status;

public class OnlyConnection extends JFrame implements ActionListener{
	private OnlyConnection oc;
	private List<Status> selList;
	private List<Status> statusList;
	private Container con;
	private DrawJpanel drawJpanel;
	private JPanel jpanel;
	private JButton addButton;
	private JButton removeButton;
	private JButton OKButton;
	private JButton cancelButton;
	private JButton delButton;
	private DefaultListModel inputModel;
	private JList inputJList;
	private JScrollPane jScrollPane;
	private Only only;
	
	public OnlyConnection(DrawJpanel djp,JPanel jp,Only only,List<Status> staList) {
		oc = this;
		this.statusList = new ArrayList<Status>(staList);
		this.only = only;
		if(only != null) {
			this.selList = new ArrayList<Status>(only.getStatusList());
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
		createOnlyConnection();
	}

	private void createOnlyConnection() {
		// TODO Auto-generated method stub
		setTitle("输入约束关系（唯一）");
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
	
	public void setInputList(List<Status> selList,List<Status> staList){
		this.selList = new ArrayList(selList);
		this.statusList = new ArrayList(staList);
		inputModel.removeAllElements();
		for(Status status : selList) {
			inputModel.addElement(status.getName());
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		ConnectionHandle ch = new ConnectionHandle();
		if(arg0.getSource() == addButton) {
			new AddStatusList(oc,statusList,selList).setVisible(true);
		}
		if(arg0.getSource() == removeButton) {
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
		if(arg0.getSource() == OKButton) {
			if(selList.size() < 2) {
				String message = "请选择至少两个输入状态！";
				JOptionPane.showMessageDialog(this, message);
				return;
			}
			else {
				if(only != null)
					ch.deleteOnly(drawJpanel.getOnlyList(), drawJpanel.getPointList(), drawJpanel.getOnlyList().indexOf(only));
				ch.createOnly(drawJpanel,selList);
				jpanel.repaint();
				dispose();
			}
		}
		if(arg0.getSource() == delButton) {
			if(only != null) {
				ch.deleteOnly(drawJpanel.getOnlyList(), drawJpanel.getPointList(), drawJpanel.getOnlyList().indexOf(only));
				jpanel.repaint();
				dispose();
			}
			else
				dispose();
		}
		if(arg0.getSource() == cancelButton) {
			dispose();
		}
	}
}
