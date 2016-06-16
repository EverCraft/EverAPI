package fr.evercraft.everapi.services;

public interface MailService {
	
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public boolean send(String adress, String object, String message);
	
	public boolean alert(String object, String message);
}
