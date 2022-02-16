package artre.dossiersysteem.Models;

import java.util.List;

public class Client {
	private Clientdata clientdata;
	private String goalsFile;
	private String logsFile;
	private List<String> documents;
	private String lastEditDate;
	private List<String> secondaryEmployees; // AccessControlList
	private String primaryEmployee;

	public Clientdata getClientdata() {
		return clientdata;
	}

	public void setClientdata(Clientdata clientdata) {
		this.clientdata = clientdata;
	}

	public String getGoalsFile() {
		return goalsFile;
	}

	public void setGoalsFile(String goalsFile) {
		this.goalsFile = goalsFile;
	}

	public String getLogsFile() {
		return logsFile;
	}

	public void setLogsFile(String logsFile) {
		this.logsFile = logsFile;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	public String getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	public List<String> getSecondaryEmployees() {
		return secondaryEmployees;
	}

	public void setSecondaryEmployees(List<String> secondaryEmployee) {
		this.secondaryEmployees = secondaryEmployee;
	}

	public String getPrimaryEmployee() {
		return primaryEmployee;
	}

	public void setPrimaryEmployee(String primaryEmployee) {
		this.primaryEmployee = primaryEmployee;
	}
}
