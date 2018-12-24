package po;

import java.util.ArrayList;
import java.util.List;

public class Status {
	private int x;
	private int y;
	private int r;
	private String name;
	private int state;//0已删除，1保存
	private int type;//0初始状态，1因，2果，3既因又果 
//	private List<Status> connection = new ArrayList<Status>();
	private List<String> connection = new ArrayList<String>();
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public List<String> getConnection() {
		return connection;
	}
	public void setConnection(List<String> connection) {
		this.connection = connection;
	}
	
}
