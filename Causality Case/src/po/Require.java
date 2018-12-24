package po;

import java.util.List;

public class Require {
	private List<Cable> cableList;
	private List<Status> statusList;
	private Status leftSta;
	private Status upSta;
	private Status downSta;
	
	public List<Cable> getCableList() {
		return cableList;
	}
	public void setCableList(List<Cable> cableList) {
		this.cableList = cableList;
	}
	public List<Status> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}
	public Status getLeftSta() {
		return leftSta;
	}
	public void setLeftSta(Status leftSta) {
		this.leftSta = leftSta;
	}
	public Status getUpSta() {
		return upSta;
	}
	public void setUpSta(Status upSta) {
		this.upSta = upSta;
	}
	public Status getDownSta() {
		return downSta;
	}
	public void setDownSta(Status downSta) {
		this.downSta = downSta;
	}
}
