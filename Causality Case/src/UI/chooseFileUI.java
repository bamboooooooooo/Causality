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
		JFileChooser fileChooser = new JFileChooser();// 文件选择器  
		fileChooser.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘    
		fileChooser.addChoosableFileFilter(new JAVAFileFilter("xml"));//添加可选择的文件的后缀名类型
		state = fileChooser.showDialog(new JLabel(), "选择");
		if(state == 0){
			file = fileChooser.getSelectedFile();//获取文件 	
			ipd.setfileName(file);
		}
	    
	  }   
	public chooseFileUI(importDigraphDialog idd) {  
		// TODO Auto-generated method stub
		JFileChooser fileChooser = new JFileChooser();// 文件选择器  
		fileChooser.setCurrentDirectory(new File("d://"));// 文件选择器的初始目录定为d盘    
		fileChooser.addChoosableFileFilter(new JAVAFileFilter("xml"));//添加可选择的文件的后缀名类型
		state = fileChooser.showDialog(new JLabel(), "选择");
		if(state == 0){
			file = fileChooser.getSelectedFile();//获取文件 	
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
	    /* 在accept()方法中,当程序所抓到的是一个目录而不是文件时,我们返回true值,表示将此目录显示出来. */
	    public boolean accept(File file) {
	        if (file.isDirectory()) {
	            return true;
	        }
	        String fileName = file.getName();
	        int index = fileName.lastIndexOf('.');
	        if (index > 0 && index < fileName.length() - 1) {
	            // 表示文件名称不为".xxx"现"xxx."之类型
	            String extension = fileName.substring(index + 1).toLowerCase();
	            // 若所抓到的文件扩展名等于我们所设置要显示的扩展名(即变量ext值),则返回true,表示将此文件显示出来,否则返回
	            // true.
	            if (extension.equals(ext))
	                return true;
	        }
	        return false;
	    }
	    // 实现getDescription()方法,返回描述文件的说明字符串!!!
	    public String getDescription() {
	        if(ext.equals("xml"))
	        	return "(*.xml)";
	        return "";
	    }
	}
}
