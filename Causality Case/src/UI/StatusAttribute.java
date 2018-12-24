package UI;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DoItem.DrawJpanel;
import po.Status;

public class StatusAttribute  extends JFrame{
	private Container con;
	private JButton OKButton;
	private JButton cancelButton;
	private JLabel statusLabel;
	private JTextField statusName;
	private String statusname;
	private StatusAttribute ns = this;
	private List<Status> staList;
	
	public StatusAttribute(DrawJpanel djp,JPanel jp){
		// TODO Auto-generated constructor stub
		staList = djp.getStatusList();
		setTitle("状态属性");
		setSize(400, 180);
		setLocationRelativeTo(jp);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		statusLabel = new JLabel("状态名:            ");
		statusName = new JTextField("");
		statusName.setColumns(20);
		OKButton = new JButton("确定");
		cancelButton = new JButton("取消");
		OKButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(statusName.getText().equals("")) {
					String message = "状态名不能为空！";
					JOptionPane.showMessageDialog(getRootPane(), message);
				}
				else {
					statusname = statusName.getText();
					for(Status sta : staList) {
						if(sta.getName().equals(statusname))
						{
							String message = "该状态已存在,请更换状态名！";
							JOptionPane.showMessageDialog(getRootPane(), message);
							return;
						}
					}
					djp.createSp(statusname);
					djp.updateJpanel();
					
					jp.repaint();
					ns.dispose();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				statusName.setText(""); 
			}
		});
		
		JPanel upJpanel = new JPanel();
		JPanel downJpanel = new JPanel();
		upJpanel.add(statusLabel);
		upJpanel.add(statusName);
		upJpanel.setLayout(new FlowLayout(0,15,20));
		
		downJpanel.add(OKButton);
		downJpanel.add(cancelButton);
		downJpanel.setLayout(new FlowLayout(1,40,20));
		
		con.add(upJpanel);
		con.add(downJpanel);
		BoxLayout layout=new BoxLayout(con, BoxLayout.Y_AXIS); 
		con.setLayout(layout);
	}
	public StatusAttribute(DrawJpanel djp,JPanel jp,Status sta,int i){
		// TODO Auto-generated constructor stub
		setTitle("状态属性");
		setSize(400, 180);
		setLocationRelativeTo(jp);
		con = this.getContentPane();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		statusLabel = new JLabel("状态名:            ");
		statusName = new JTextField(sta.getName());
		statusName.setColumns(20);
		OKButton = new JButton("确定");
		cancelButton = new JButton("取消");
		OKButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(statusName.getText().equals("")) {
					String message = "状态名不能为空！";
					JOptionPane.showMessageDialog(getRootPane(), message);
				}
				else {
					sta.setName(statusName.getText());
					djp.updateSta(sta, i);
					
					jp.repaint();
					ns.dispose();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				statusName.setText(""); 
			}
		});
		
		JPanel upJpanel = new JPanel();
		JPanel downJpanel = new JPanel();
		upJpanel.add(statusLabel);
		upJpanel.add(statusName);
		upJpanel.setLayout(new FlowLayout(0,15,20));
		
		downJpanel.add(OKButton);
		downJpanel.add(cancelButton);
		downJpanel.setLayout(new FlowLayout(1,40,20));
		
		con.add(upJpanel);
		con.add(downJpanel);
		BoxLayout layout=new BoxLayout(con, BoxLayout.Y_AXIS); 
		con.setLayout(layout);
	}
}
