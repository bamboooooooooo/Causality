package UI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

public class TabbedPanel extends JPanel{
	private final JTabbedPane pane;      
	public TabbedPanel(final JTabbedPane pane){          
		super((LayoutManager) new FlowLayout(FlowLayout.LEFT, 0, 0));          
		if(pane==null) throw new NullPointerException("TabbedPane is null");          
		this.pane=pane;          
		setOpaque(false);          
		//tab����          
		JLabel label = new JLabel() {              
			@Override              
			public String getText() {                  
				int i = pane.indexOfTabComponent(TabbedPanel.this);                  
				if (i != -1)return pane.getTitleAt(i);                  
				return null;              
				}          
			};          
			add(label);          
			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));          
			add(new TabButton());          
			setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));      }      
	/** tab�ϵĹرհ�ť */      
	private class TabButton extends JButton {          
		public TabButton() {              
			int size = 17;              
			setPreferredSize(new Dimension(size, size));              
			setToolTipText("�ر�");              
			setUI(new BasicButtonUI());              
			setContentAreaFilled(false);              
			setFocusable(false);              
			setBorder(BorderFactory.createEtchedBorder());              
			setBorderPainted(false);              
			//��תЧ��              
			setRolloverEnabled(true);              
			//����¼�������ʱ���߿��Ƴ�ʱȡ���߿�             
			addMouseListener(new MouseAdapter() {                  
				@Override                  
				public void mouseEntered(MouseEvent e) {                      
					Component component = e.getComponent();                      
					if (component instanceof AbstractButton) {                          
						AbstractButton button = (AbstractButton) component;                          
						button.setBorderPainted(true);                      
						}                  
					}                  
				@Override                  
				public void mouseExited(MouseEvent e) {                      
					Component component = e.getComponent();                      
					if (component instanceof AbstractButton) {                          
						AbstractButton button = (AbstractButton) component;                          
						button.setBorderPainted(false);                      
						}                  
					}              
				});              
			//�����رհ�ť�¼�              
			addActionListener(new ActionListener() {                  
				public void actionPerformed(ActionEvent evt) {                      
					int i = pane.indexOfTabComponent(TabbedPanel.this);                      
					if (i != -1)  {
						String message = "�Ƿ񱣴浱ǰ���ģ�";
						String title = "����";
						int value = JOptionPane.showConfirmDialog(pane, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
						if(value == JOptionPane.YES_OPTION){
							mainUI.saveFile();
							pane.remove(i); 
						}
						else if(value == JOptionPane.NO_OPTION)
							pane.remove(i); 
						else{}	
					}
					}              
				});          
			}          
		@Override          
		public void updateUI() {          }          
		@Override          
		protected void paintComponent(Graphics g) {              
			super.paintComponent(g);              
			Graphics2D g2 = (Graphics2D) g.create();              
			//��갴��ʱƫ��һ�������              
			if (getModel().isPressed()) {                  
				g2.translate(1, 1);              
				}              
			g2.setStroke(new BasicStroke(2));              
			g2.setColor(Color.BLACK);              
			//����ڰ�ť��ʱΪ��ɫ             
			if (getModel().isRollover()) {                  
				g2.setColor(Color.RED);              
				}              
			int delta = 6;              
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);              
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);             
			g2.dispose();          
			}      
		}     
	}
