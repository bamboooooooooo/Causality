package DoItem;

import java.awt.EventQueue;

import UI.mainUI;

public class begin {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					mainUI ui = new mainUI();
					ui.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
