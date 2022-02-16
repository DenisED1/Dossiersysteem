package artre.dossiersysteem.Models;

import java.util.ArrayList;
import java.util.List;

public class LogList {
	private List<Log> logList = new ArrayList<Log>();
	private String lastEditDate;
	
	public List<Log> getLogList() {
		return logList;
	}
	public void setLogList(List<Log> logList) {
		this.logList = logList;
	}
	public void addToLogList(Log log) {
		logList.add(log);
	}
	public String getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
}
