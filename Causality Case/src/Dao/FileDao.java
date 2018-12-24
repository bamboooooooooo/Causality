package Dao;

import java.util.List;

import po.FileInfo;

//数据库操作

public interface FileDao {
	//按filePath查找文件
	public List<FileInfo> seleceFileByPath(String filePath);
	//按fileType查找文件
	public List<FileInfo> selectFileByType(String fileType);
	//添加文件信息
	public int addFile(FileInfo fileinfo);
	//删除文件信息
	public int deleteFile(String filename,String filepath);
	//按地址删除文件信息
	public int deleteFileByPath(String filepath);
	//修改文件信息
	public int updateFile(FileInfo fileinfo);
	//修改文件名
	public int updateFileName(String newName,String OldName);
	//查询文件信息
	public FileInfo selectFileByName(String fileName);
	//查询文件信息
	public FileInfo selectFileByFolder(String fileName,String folder);
	//查询文件夹信息
	public FileInfo selectFolderByName(String folderName);
}
