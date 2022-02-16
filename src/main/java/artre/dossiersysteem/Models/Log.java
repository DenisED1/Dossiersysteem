package artre.dossiersysteem.Models;

public class Log {
	private String logText;
	private String lastEditDate;

	public Log() {
		
	}
	
	public Log(String text, String dateString) {
		logText = text;
		lastEditDate = dateString;
	}

	public String getLogText() {
		return logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
	}

	public String getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
}
