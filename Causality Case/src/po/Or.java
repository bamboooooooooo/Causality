package po;

import java.util.List;

public class Or {
	private List<Cable> cableList;
	private List<Status> startList;
	private Status end;
	
	public List<Cable> getCableList() {
		return cableList;
	}
	public void setCableList(List<Cable> cableList) {
		this.cableList = cableList;
	}
	public List<Status> getStartList() {
		return startList;
	}
	public void setStartList(List<Status> startList) {
		this.startList = startList;
	}
	public Status getEnd() {
		return end;
	}
	public void setEnd(Status end) {
		this.end = end;
	}
}
