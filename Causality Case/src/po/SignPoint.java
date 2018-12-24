package po;

public class SignPoint {
	private int x;
	private int y;
	private String type;//连接类型
	private int i;//列表位置
	
	public SignPoint(int x,int y,String type,int i) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		this.type = type;
		this.i = i;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
}
