package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import DoItem.FileHandle;

public class chooseFileUI{
	File file; 
	int state;
	public chooseFileUI(importProjectDialog ipd) {  
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();// �ļ�ѡ����  
		fileChooser.setCurrentDirectory(new File("d://"));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��    
		fileChooser.addChoosableFileFilter(new JAVAFileFilter("xml"));//��ӿ�ѡ����ļ��ĺ�׺������
		state = fileChooser.showDialog(new JLabel(), "ѡ��");
		if(state == 0){
			file = fileChooser.getSelectedFile();//��ȡ�ļ� 	
			ipd.setfileName(file);
		}
	    
	  }   
	public chooseFileUI(importDigraphDialog idd) {  
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();// �ļ�ѡ����  
		fileChooser.setCurrentDirectory(new File("d://"));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��    
		fileChooser.addChoosableFileFilter(new JAVAFileFilter("xml"));//��ӿ�ѡ����ļ��ĺ�׺������
		state = fileChooser.showDialog(new JLabel(), "ѡ��");
		if(state == 0){
			file = fileChooser.getSelectedFile();//��ȡ�ļ� 	
			idd.setfileName(file);
		}
	    
	  }   
	public File getFile(){
		return file;
	}
//	public int getState(){
//		return state;
//	}
	class JAVAFileFilter extends FileFilter {
	    String ext;
	    public JAVAFileFilter(String ext) {
	        this.ext = ext;
	    }
	    /* ��accept()������,��������ץ������һ��Ŀ¼�������ļ�ʱ,���Ƿ���trueֵ,��ʾ����Ŀ¼��ʾ����. */
	    public boolean accept(File file) {
	        if (file.isDirectory()) {
	            return true;
	        }
	        String fileName = file.getName();
	        int index = fileName.lastIndexOf('.');
	        if (index > 0 && index < fileName.length() - 1) {
	            // ��ʾ�ļ����Ʋ�Ϊ".xxx"��"xxx."֮����
	            String extension = fileName.substring(index + 1).toLowerCase();
	            // ����ץ�����ļ���չ����������������Ҫ��ʾ����չ��(������extֵ),�򷵻�true,��ʾ�����ļ���ʾ����,���򷵻�
	            // true.
	            if (extension.equals(ext))
	                return true;
	        }
	        return false;
	    }
	    // ʵ��getDescription()����,���������ļ���˵���ַ���!!!
	    public String getDescription() {
	        if(ext.equals("xml"))
	        	return "(*.xml)";
	        return "";
	    }
	}
}
