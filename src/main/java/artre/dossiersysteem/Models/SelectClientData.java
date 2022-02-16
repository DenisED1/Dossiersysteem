package artre.dossiersysteem.Models;

public class SelectClientData {
	private String clientNr;
	private String firstName;
	private String lastName;

	public SelectClientData(String clientNr, String firstName, String lastName) {
        this.clientNr = clientNr;
        this.firstName = firstName;
        this.lastName = lastName;
	}

	public String getClientNr() {
        return clientNr;
    }

    public void setClientNr(String clientNr) {
    	this.clientNr = clientNr;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
