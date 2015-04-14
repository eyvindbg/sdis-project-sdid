package sdid.ws.impl;

import javax.jws.*;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.UUID;

import pt.ulisboa.tecnico.sdis.id.ws.*;

@WebService(
    endpointInterface="pt.ulisboa.tecnico.sdis.id.ws.SDId", 
    wsdlLocation="SD-ID.1_1.wsdl",
    name="SDID",
    portName="SDIdImplPort",
    targetNamespace="urn:pt:ulisboa:tecnico:sdis:id:ws",
    serviceName="SDId"
)
public class SDIDImpl implements SDId {
	
	public static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private ArrayList<User> users = new ArrayList<User>();

	public void createUser(String userId, String emailAddress)
			throws EmailAlreadyExists_Exception, InvalidEmail_Exception,
			InvalidUser_Exception, UserAlreadyExists_Exception {

		if (emailExists(emailAddress)) throw new EmailAlreadyExists_Exception("Email already exists.", new EmailAlreadyExists());
		
		if (!validEmail(emailAddress)) throw new InvalidEmail_Exception("Email is invalid.", new InvalidEmail());
		
		if (!validUserId(userId)) throw new InvalidUser_Exception("User ID is invalid.", new InvalidUser());
		
		if (userExists(userId)) throw new UserAlreadyExists_Exception("User already exists.", new UserAlreadyExists());
		
		users.add(new User(userId, emailAddress, generatePassword()));
		
		System.out.println("User was created: (" + userId + ", " + emailAddress + ")");
		
	}

	public void renewPassword(String userId) throws UserDoesNotExist_Exception {
		
		if (!userExists(userId)) throw new UserDoesNotExist_Exception("User does not exist.", new UserDoesNotExist());
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) {
				users.get(i).setPassword(generatePassword());
				break;
			}
		}
		
	}

	public void removeUser(String userId) throws UserDoesNotExist_Exception {

		if (!userExists(userId)) throw new UserDoesNotExist_Exception("User does not exist.", new UserDoesNotExist());
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) {
				users.remove(i);
				break;
			}
		}
	}

	public byte[] requestAuthentication(String userId, byte[] reserved) throws AuthReqFailed_Exception {
		
		User user = null;
		String password = new String(reserved);
		
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) user = users.get(i);
		}
		
		if (user == null) throw new AuthReqFailed_Exception("User does not exist.", new AuthReqFailed());
		
		if (!password.equals(user.getPassword())) throw new AuthReqFailed_Exception("Password incorrect.", new AuthReqFailed());
		
		return new byte[]{1};
	}
	
	private boolean userExists(String userId) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserId().equals(userId)) return true;
		}
		return false;
	}
	
	private boolean emailExists(String emailAddress) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmailAddress().equals(emailAddress)) return true;
		}
		return false;
	}
	
	private boolean validEmail(String emailAddress) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailAddress);
		return matcher.matches();
	}
	
	private boolean validUserId(String userId) {
		if (userId == null || userId.equals("")) return false;
		return true;
	}
	
	private String generatePassword() {
		String uuid = UUID.randomUUID().toString();
		String password = "";
		SecureRandom random = new SecureRandom();
		
		while (password.length() < 8) {
			int i = random.nextInt(uuid.length());
			if (uuid.charAt(i) != '-') password += uuid.charAt(i);
		}
		
		return password;
	}

	
	private byte[] generateToken() {
		return UUID.randomUUID().toString().getBytes();
	}

	public ArrayList<User> getUsers() {
		return users;
	}
}
