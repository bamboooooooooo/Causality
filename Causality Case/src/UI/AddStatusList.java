package UI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import po.Status;

public class AddStatusList extends JFrame implements ActionListener{

	private AndConnection andCon;
	private OrConnection orCon;
	private InorConnection inorCon;
	private EorConnection eorCon;
	private OnlyConnection onlyCon;
	private RequireConnection requireCon;
	private Container con;
	private JButton addButton,delButton,OKButton,cancelButton;
	private DefaultListModel statusModel,selectedModel;
	private JList statusJList,selectedJList;
	private JScrollPane leftScrollPane,rightScrollPane;
	private JPanel centerJpanel,downJpanel;
	private List<Status> statusList,selectedList,staList,selList;
	private String type;
	private JFrame df;
	
	public AddStatusList(AndConnection andCon,List<Status> statusList,List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.andCon = andCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "and";
		this.df = andCon;
		createAdd();
	}
	public AddStatusList(OrConnection orCon,List<Status> statusList,List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.orCon = orCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "or";
		this.df = orCon;
		createAdd();
	}
	public AddStatusList(InorConnection inorCon,List<Status> statusList,List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.inorCon = inorCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "inor";
		this.df = inorCon;
		createAdd();
	}
	public AddStatusList(EorConnection eorCon,List<Status> statusList,List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.eorCon = eorCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "eor";
		this.df = eorCon;
		createAdd();
	}
	public AddStatusList(OnlyConnection onlyCon,List<Status> statusList,List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.onlyCon = onlyCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "only";
		this.df = onlyCon;
		createAdd();
	}
	public AddStatusList(RequireConnection requireCon, List<Status> statusList, List<Status> selectedList) {
		// TODO Auto-generated constructor stub
		this.requireCon = requireCon;
		this.statusList = new ArrayList(statusList);
		this.selectedList = new ArrayList(selectedList);
		this.type = "require";
		this.df = requireCon;
		createAdd();
	}
	public void createAdd() {
		staList = new ArrayList<Status>(statusList);
		selList = new ArrayList<Status>(selectedList);
		setTitle("add");
		setSize(450,500);
		setLocationRelativeTo(df);
		setResizable(false);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addButton = new JButton("选中>>");
		delButton = new JButton("<<撤销");
		OKButton = new JButton("确定");
		cancelButton = new JButton("取消");
		
		addButton.setBounds(185, 120, 75, 30);
		delButton.setBounds(185, 180, 75, 30);
		
		addButton.addActionListener(this);
		delButton.addActionListener(this);
		OKButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		statusModel = new DefaultListModel();
		if(staList != null)
			for(Status status : staList) {
				statusModel.addElement(status.getName());
		}
		selectedModel = new DefaultListModel();
		if(selList != null) {
			for(Status status : selList) {
				selectedModel.addElement(status.getName());
			}
		}
		statusJList = new JList(statusModel);
		selectedJList = new JList(selectedModel);
		leftScrollPane = new JScrollPane(statusJList);
		rightScrollPane = new JScrollPane(selectedJList);
		leftScrollPane.setBounds(30, 20, 140, 350);
		rightScrollPane.setBounds(280, 20, 140, 350);
		
		centerJpanel = new JPanel();
		downJpanel = new JPanel();
		
		centerJpanel.add(leftScrollPane);
		centerJpanel.add(addButton);
		centerJpanel.add(delButton);
		centerJpanel.add(rightScrollPane);
		centerJpanel.setLayout(null);
		centerJpanel.setBounds(0, 0, 450, 400);
		
		downJpanel.add(OKButton);
		downJpanel.add(cancelButton);
		downJpanel.setLayout(new FlowLayout(2,10,10));
		downJpanel.setBounds(0, 400, 450, 100);
		
		con.add(centerJpanel);
		con.add(downJpanel);
		con.setLayout(null);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == addButton) {
			int selSta = -1;
			selSta = statusJList.getSelectedIndex();
			if(selSta != -1) {
				for(Status sta : staList) {
					if(sta.getName().equals(statusModel.elementAt(selSta))) {
						selList.add(sta);
						staList.remove(sta);
						break;
					}
				}
				selectedModel.addElement(statusModel.elementAt(selSta));
				statusModel.removeElementAt(selSta);	
			}
		}
		if(arg0.getSource() == delButton) {
			int selSta = -1;
			selSta = selectedJList.getSelectedIndex();
			Status sta = null;
			if(selSta != -1) {
				for(Status status : selList) {
					if(status.getName().equals(selectedModel.elementAt(selSta))){
						sta = status;
					}
				}
				staList.add(sta);
				selList.remove(sta);
				statusModel.addElement(selectedModel.elementAt(selSta));
				selectedModel.removeElementAt(selSta);
			}
			else {
				return;
			}
		}
		if(arg0.getSource() == OKButton && type.equals("and")) {
			andCon.setInputList(selList,staList);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("or")) {
			orCon.setInputList(selList,staList);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("inor")) {
			inorCon.setInputList(selList,staList);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("eor")) {
			eorCon.setInputList(selList,staList);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("only")) {
			onlyCon.setInputList(selList,staList);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("require")) {
			requireCon.setInputList(selList,staList);
			dispose();
		}
		if(arg0.getSource() == cancelButton) {
			dispose();
		}
	}

}
