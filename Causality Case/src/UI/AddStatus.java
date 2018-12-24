package UI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

import po.Status;

public class AddStatus extends JFrame implements ItemListener, ActionListener{
	private Container con;
	private JComboBox outputJcomboBox;
	private JButton OKButton;
	private String staname;
	private AndConnection ac;
	private OrConnection oc;
	private List<Status> staList;
	private String type;
	
	public AddStatus(AndConnection ac,List<Status> staList) {
		// TODO Auto-generated constructor stub
		this.ac = ac;
		this.staList = staList;
		this.type = "and";
		createFrame();
	}
	public AddStatus(OrConnection oc,List<Status> staList) {
		// TODO Auto-generated constructor stub
		this.oc = oc;
		this.staList = staList;
		this.type = "or";
		createFrame();
	}
	public void createFrame() {
		setTitle("add");
		setSize(350,150);
		setLocationRelativeTo(ac);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		String[] staName = new String[staList.size()];
		for(int i = 0;i < staList.size();i ++) {
			staName[i] = staList.get(i).getName();
		}
		outputJcomboBox = new JComboBox(staName);
		OKButton = new JButton("确定");
		outputJcomboBox.setSelectedIndex(-1);
		outputJcomboBox.setEditable(false);//设置文本框为可编辑
		outputJcomboBox.addItemListener(this);
		outputJcomboBox.setBounds(40, 35, 150, 30);
		OKButton.addActionListener(this);
		OKButton.setBounds(230, 35, 60, 30);
		con.add(outputJcomboBox);
		con.add(OKButton);
		con.setLayout(null);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == OKButton && type.equals("and")) {
			ac.setOutputStatus(staname);
			dispose();
		}
		else if(arg0.getSource() == OKButton && type.equals("or")){
			oc.setOutputStatus(staname);
			dispose();
		}
	}
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getStateChange() == ItemEvent.SELECTED) {
			staname = (String)outputJcomboBox.getSelectedItem();
		}
	}

}
