package DoItem;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import Dao.FileDao;
import Dao.Impl.FileDaoImpl;
import po.FileInfo;

public class FileHandle {
	
	//新建项目
		public static boolean createProject(String projectName,String location){
			String filenameTemp = location + projectName;
	        File file = new File(filenameTemp);
	        boolean flag = false;
	        try {
	            	flag = file.mkdirs();//判断此目录是否已经创建成功
	            	if(flag == true){
	            		FileInfo fileinfo = new FileInfo();
	            		fileinfo.setFileName(projectName);
	            		fileinfo.setFilePath(location);
	            		fileinfo.setFileType("folder");
	            		fileinfo.setFileState(1);
	            		FileInfoHandle.addFileInfo(fileinfo);
	            	}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return flag;
	    }
		
	//新建文件
	public static int createFile(String fileName,String digraphname,String location){
		int res = 0;//初始状态为0，成功创建文件1，文件已存在2
		String filenameTemp;
        filenameTemp = location+fileName;//文件路径+名称+文件类型
        File file = new File(filenameTemp);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                InitXML.initxml(fileName, location);
                FileInfo fileinfo = new FileInfo();
        		fileinfo.setFileName(fileName);
        		fileinfo.setFilePath(location);
        		fileinfo.setFileType("file");
        		fileinfo.setFileState(1);
        		FileInfoHandle.addFileInfo(fileinfo);
        		FileInfo digraphinfo = new FileInfo();
                digraphinfo = fileinfo;
                digraphinfo.setFileName(digraphname);
                FileInfoHandle.addFileInfo(digraphinfo);
                res = 1;
            }
            else
            	res = 2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return res;
    }
	//读取文件
	public static String readFile(String filePath){
		byte[] b = null;
		try { 		  
			  FileInputStream is = new FileInputStream(filePath);
			  BufferedInputStream bis = new BufferedInputStream(is);
				b = new byte[is.available()];
				bis.read(b);
				is.close();
				bis.close();
		  } catch (FileNotFoundException e) {
				e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		String str = new String(b);
		return str;
	}
	
	//文件导入
	public static int importFile(File oldfile,String path) throws IOException{
		int flag = 0;
		if(!oldfile.getName().matches("(?i).+xml$"))	
		{
			return flag;
		}
		FileInfo fi = FileInfoHandle.selectFileInfo(oldfile.getName());
		if(fi != null && fi.getFilePath().equals(path)){
			flag = 1;
			return flag;
		}
		File newfile = new File(path + oldfile.getName());
		FileInputStream ins = new FileInputStream(oldfile);
		FileOutputStream out = new FileOutputStream(newfile);
		//自定义缓冲对象
		try{
			int length = ins.available();
			byte[] b = new byte[length];
			int n=0;
			while((n=ins.read(b))!=-1){
				out.write(b, 0, b.length);
			} 
			FileInfo fileinfo = new FileInfo();
			fileinfo.setFileName(oldfile.getName());
			fileinfo.setFilePath(path);
			fileinfo.setFileType("file");
			fileinfo.setFileState(1);
			FileInfoHandle.addFileInfo(fileinfo);
			
			FileInfo fileinfo2 = new FileInfo();
			fileinfo2 = fileinfo;
			int leng=fileinfo2.getFileName().indexOf(".");
			String graphname=fileinfo2.getFileName().substring(0,leng); 
			fileinfo2.setFileName(graphname);
			FileInfoHandle.addFileInfo(fileinfo2);
			flag = 2;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			ins.close();
			out.close();
		}
		return flag;
	}
	
	//文件保存
			public static void saveFile(String filename,String filepath,String text) {
				// TODO Auto-generated method stub
				String path = filepath + filename;
				OutputStream os = null;
			        try {
			           os = new FileOutputStream(path);
			           OutputStreamWriter write = new OutputStreamWriter(os,"UTF-8");
			           BufferedWriter writer=new BufferedWriter(write);
			           writer.write(text);
			           writer.close();
			           write.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (Exception e) {
			            e.printStackTrace();
			        } finally {
			            // 完毕，关闭所有链接
			            try {
			                os.close();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }

			}

	//文件写入
	public static boolean writeFileContent(String filepath,String newstr) throws IOException{
        Boolean bool = false;
        String filein = newstr+"\r\n";//新写入的行，换行
        String temp  = "";
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }
    
    /**
     * 删除文件
     * @param fileName 文件名称
     * @return
     */
    public static boolean delFile(String fileName,String location){
        Boolean bool = false;
        File file  = new File(location + fileName);
        try {
            if(file.exists()){
                file.delete();
                bool = true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        FileInfoHandle.delFileInfo(fileName,location);
        return bool;
    }
    
    /**
     * 删除项目
     * @param projectName 项目名称
     * @return
     */
    public static boolean delProject(String location){
        Boolean bool = false;
//        String path = location + projectName;
        File file  = new File(location);
        if (file.exists()) {//判断文件是否存在  
        	if (file.isFile()) {//判断是否是文件  
        		file.delete();//删除文件   
        	}else if (file.isDirectory()) {//否则如果它是一个目录  
        		File[] files = file.listFiles();//声明目录下所有的文件 files[];  
        		for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
        			delProject(files[i].getPath());//把每个文件用这个方法进行迭代  
        		}  
        	}
        }
        bool = file.delete();;//删除文件夹  
        return bool;
    }
    
    //文件查找
	public static boolean selectFile(String filename,String folder) {
		// TODO Auto-generated method stub
		boolean flag = false;
		FileInfo fi = FileInfoHandle.selectFileByFolder(filename,folder);
		String path = fi.getFilePath() + fi.getFileName();
		File file = new File(path);
		flag = file.exists();
		return flag;
	}
	
	public static boolean selectProject(String projectname,String location) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String path = location + projectname;
		File file = new File(path);
		flag = file.exists();
		return flag;
	}
   
}
