package DoItem;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import Dao.FileDao;
import Dao.Impl.FileDaoImpl;
import po.FileInfo;

public class FileInfoHandle {
	private static FileDao fd;
	//�ļ���Ϣ����
	public static JTree fileInfoList(String location){
		JTree jtree = null;
		DefaultMutableTreeNode root;
		DefaultMutableTreeNode projectname = null;
		DefaultMutableTreeNode filename = null;
		fd = new FileDaoImpl();
		FileInfo fileinfo;
		
		List<FileInfo> fil = new ArrayList<FileInfo>();
		fil = fd.selectFileByType("folder");
		
		if(!fil.isEmpty()){
			root=new DefaultMutableTreeNode("��Ŀ");
			jtree=new JTree(root);
			for(FileInfo fi:fil){					//����FileInfo[]����
				projectname = new DefaultMutableTreeNode(fi.getFileName());
				root.add(projectname);
				String newpath = location + fi.getFileName() + "\\";
				List<FileInfo> fil2 = new ArrayList<FileInfo>();
				fil2 = fd.seleceFileByPath(newpath);
				if(fil2 != null)
				{
					for(FileInfo fi2:fil2)
					{
						filename = new DefaultMutableTreeNode(fi2.getFileName());
						projectname.add(filename);
					}
				}
			}
		}
		return jtree;
	}
	
//	//��Ŀ��Ϣ����
//	public static List<FileInfo> projectList(){
//		fd = new FileDaoImpl();
//		FileInfo fileinfo;
//			
//		List<FileInfo> fil = new ArrayList<FileInfo>();
//		fil = fd.selectFileByType("folder");
//		return fil;
//	}
	
	//	��Ŀ��Ϣ����
	public static JTree projectTree(){
		JTree jtree = null;
		DefaultMutableTreeNode root;
		DefaultMutableTreeNode projectname = null;
		fd = new FileDaoImpl();
		FileInfo fileinfo;
			
		List<FileInfo> fil = new ArrayList<FileInfo>();
		fil = fd.selectFileByType("folder");
		if(!fil.isEmpty()){
			root=new DefaultMutableTreeNode("��Ŀ");
			jtree=new JTree(root);
			for(FileInfo fi:fil){					//����FileInfo[]����
				projectname = new DefaultMutableTreeNode(fi.getFileName());
				root.add(projectname);
			}
		}
		return jtree;
	}
	//����ļ���Ϣ
	public static void addFileInfo(FileInfo fileinfo)
	{
		fd = new FileDaoImpl();
		fd.addFile(fileinfo);
	}
	
	//�����ļ���Ϣ
	public static FileInfo selectFileInfo(String fileName)
	{
		fd = new FileDaoImpl();
		boolean flag = false;
		FileInfo fi = new FileInfo();
		fi = fd.selectFileByName(fileName);
        return fi;
	}
	//�����ļ���Ϣ
	public static FileInfo selectFileByFolder(String fileName,String folder)
	{
		fd = new FileDaoImpl();
		boolean flag = false;
		FileInfo fi = new FileInfo();
		fi = fd.selectFileByFolder(fileName, folder);
	    return fi;
	}
	//�����ļ�����Ϣ
	public static FileInfo selectFolderInfo(String fileName)
	{
		fd = new FileDaoImpl();
		boolean flag = false;
		FileInfo fi = new FileInfo();
		fi = fd.selectFolderByName(fileName);
        return fi;
	}
	 //ɾ���ļ���Ϣ
    public static void delFileInfo(String filename,String filepath){
    	fd = new FileDaoImpl();
    	fd.deleteFile(filename,filepath);
    }
    public static void delFolderInfo(String projectname,String location){
    	fd = new FileDaoImpl();
    	fd.deleteFile(projectname,location);
    	int i = fd.deleteFileByPath(location + projectname + "\\");
    	System.out.println(location + projectname + "\\");
    	System.out.println(i);
    }
    
}
