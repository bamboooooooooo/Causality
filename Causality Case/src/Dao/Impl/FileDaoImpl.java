package Dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.FileDao;
import po.FileInfo;
import utils.JdbcUtil;

public class FileDaoImpl implements FileDao {

	@Override
	public int addFile(FileInfo fileinfo) {
		// TODO Auto-generated method stub
		int i = 0;// 澹版槑i瀛樺偍鍙楀奖鍝嶈鏁�
		Connection conn = null;// 澹版槑鏁版嵁搴撹繛鎺ュ璞�
		PreparedStatement ps = null;// 鍒涘缓鎵ц鏁版嵁搴撴搷浣滃璞�
		// sql娣诲姞璇彞
		String sql = new String();
		sql = "insert into fileInfo(fileName,filePath,fileType,fileState) value(?,?,?,?)";
		try {
			conn = JdbcUtil.getConnection();// 鑾峰彇杩炴帴
			ps = conn.prepareStatement(sql);// 鎵цsql

			ps.setString(1, fileinfo.getFileName());// 璧嬪�煎埌闂彿澶�
			ps.setString(2, fileinfo.getFilePath());
			ps.setString(3, fileinfo.getFileType());
			ps.setInt(4, fileinfo.getFileState());
				
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();// 鍦ㄥ懡浠よ鎵撳嵃寮傚父淇℃伅鍦ㄧ▼搴忎腑鍑洪敊鐨勪綅缃強鍘熷洜
		} finally {
			JdbcUtil.closeConn(conn);// 閲婃斁璧勬簮
			JdbcUtil.closeStmt(ps);
		}
		return i;
	}

	@Override
	public int deleteFile(String filename,String filepath) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		int i = 0;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "delete from fileInfo where fileName=? and filepath=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, filename);
			ps.setString(2, filepath);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
		}
		return i;
	}

	@Override
	public int updateFile(FileInfo fileinfo) {
		// TODO Auto-generated method stub
		Connection conn = null;// 澹版槑鏁版嵁搴撹繛鎺ュ璞�
		PreparedStatement ps = null;// 鍒涘缓鎵ц鏁版嵁搴撴搷浣滃璞�
		int i = 0;// 澹版槑i瀛樺偍鍙楀奖鍝嶈鏁�
		// sql璇彞
		String sql = "update fileInfo set filePath=?,fileType=?,fileState=? where fileName=?";
		try {
			conn = JdbcUtil.getConnection();// 鑾峰彇杩炴帴
			ps = conn.prepareStatement(sql);// 鎵цsql
			ps.setString(1, fileinfo.getFilePath());
			ps.setString(2, fileinfo.getFileType());
			ps.setInt(3, fileinfo.getFileState());
			ps.setString(4, fileinfo.getFileName());
			i = ps.executeUpdate();// 鑾峰彇鍙楀奖鍝嶈鏁�
		} catch (SQLException e) {
			e.printStackTrace();// 鍦ㄥ懡浠よ鎵撳嵃寮傚父淇℃伅鍦ㄧ▼搴忎腑鍑洪敊鐨勪綅缃強鍘熷洜
		} finally {
			JdbcUtil.closeConn(conn);// 閲婃斁璧勬簮
			JdbcUtil.closeStmt(ps);
		}
		return i;
	}

	@Override
	public FileInfo selectFileByName(String fileName) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from fileInfo where fileName=? and fileType='file'";
		FileInfo fileinfo = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, fileName);
			rs = ps.executeQuery();
			if (rs.next()) {
				fileinfo = new FileInfo();
				fileinfo.setFileName(fileName);
				fileinfo.setFilePath(rs.getString("filePath"));
				fileinfo.setFileType(rs.getString("fileType"));
				fileinfo.setFileState(rs.getInt("fileState"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
			JdbcUtil.closeRs(rs);
		}

		return fileinfo;
	}

	@Override
	public int updateFileName(String newName, String OldName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FileInfo> seleceFileByPath(String filePath) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from fileInfo where filePath=? and fileState=1";
		List<FileInfo> fiList = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, filePath);
			rs = ps.executeQuery();
			fiList=new ArrayList<FileInfo>();
			FileInfo fi=null;
			while (rs.next()) {
				fi=new FileInfo();
				fi.setFileName(rs.getString("fileName"));
				fi.setFilePath(rs.getString("filePath"));
				fi.setFileType(rs.getString("fileType"));
				fi.setFileState(rs.getInt("fileState"));
				fiList.add(fi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
			JdbcUtil.closeRs(rs);
		}
		return fiList;
	}

	@Override
	public List<FileInfo> selectFileByType(String fileType) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from fileInfo where fileType=? and fileState=1";
		List<FileInfo> fiList = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, fileType);
			rs = ps.executeQuery();
			fiList=new ArrayList<FileInfo>();
			FileInfo fi=null;
			while (rs.next()) {
				fi=new FileInfo();
				fi.setFileName(rs.getString("fileName"));
				fi.setFilePath(rs.getString("filePath"));
				fi.setFileType(rs.getString("fileType"));
				fi.setFileState(rs.getInt("fileState"));
				fiList.add(fi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
			JdbcUtil.closeRs(rs);
		}
		return fiList;
	}

	@Override
	public FileInfo selectFolderByName(String folderName) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from fileInfo where fileName=? and fileType='folder'";
		FileInfo fileinfo = null;
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, folderName);
			rs = ps.executeQuery();
			if (rs.next()) {
				fileinfo = new FileInfo();
				fileinfo.setFileName(folderName);
				fileinfo.setFilePath(rs.getString("filePath"));
				fileinfo.setFileType(rs.getString("fileType"));
				fileinfo.setFileState(rs.getInt("fileState"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
			JdbcUtil.closeRs(rs);
		}

		return fileinfo;
	}

	@Override
	public FileInfo selectFileByFolder(String fileName, String folder) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from fileInfo where fileName=? and filePath=?";
		FileInfo fileinfo = null;
		String path = "E:\\Causality Case\\" + folder + "\\";
		try {
			conn = JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, fileName);
			ps.setString(2, path);
			rs = ps.executeQuery();
			if (rs.next()) {
				fileinfo = new FileInfo();
				fileinfo.setFileName(fileName);
				fileinfo.setFilePath(rs.getString("filePath"));
				fileinfo.setFileType(rs.getString("fileType"));
				fileinfo.setFileState(rs.getInt("fileState"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
			JdbcUtil.closeRs(rs);
		}

		return fileinfo;
	}

	@Override
	public int deleteFileByPath(String filepath) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		int i = 0;
		try {
			conn = JdbcUtil.getConnection();
			String sql = "delete from fileInfo where filepath=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, filepath);
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeConn(conn);
			JdbcUtil.closeStmt(ps);
		}
		return i;
	}

}
