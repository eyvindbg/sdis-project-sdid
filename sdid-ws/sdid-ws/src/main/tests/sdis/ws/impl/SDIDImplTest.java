package sdis.ws.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.ulisboa.tecnico.sdis.id.ws.AuthReqFailed_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.EmailAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.InvalidEmail_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.InvalidUser_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.UserAlreadyExists_Exception;
import pt.ulisboa.tecnico.sdis.id.ws.UserDoesNotExist_Exception;
import sdid.ws.impl.SDIDImpl;

public class SDIDImplTest {

	
	// CREATE USER
	
	@Test
	public void testCreateUser() {
		SDIDImpl impl = new SDIDImpl();
		
		try {
			impl.createUser("alice", "alice@tecnico.pt");
			assertEquals(1, impl.getUsers().size());
		} 
		
		catch (Exception e) {
			fail("Unexpected exception was thrown.");
		}
	}
	
	@Test (expected=UserAlreadyExists_Exception.class)
	public void testCreateUserUserExists() throws Exception {
		
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("alice", "alice@tecnico.pt");
		impl.createUser("alice", "bruno@tecnico.pt");
	}
	
	@Test (expected=InvalidUser_Exception.class)
	public void testCreateUserInvalidUserNull() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser(null, "alice@tecnico.pt");
	}
	
	@Test (expected=InvalidUser_Exception.class)
	public void testCreateUserInvalidUserEmptyString() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("", "alice@tecnico.pt");
	}
	
	@Test (expected=InvalidEmail_Exception.class)
	public void testCreateUserInvalidEmailNoAt() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("alice", "alicetecnico.pt");
	}
	
	@Test (expected=InvalidEmail_Exception.class)
	public void testCreateUserInvalidEmailNoDomain() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("alice", "alice@tecnico");
	}
	
	@Test (expected=EmailAlreadyExists_Exception.class)
	public void testCreateUserEmailExists() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("alice", "alice@tecnico.pt");
		impl.createUser("bruno", "alice@tecnico.pt");
	}
	
	// REMOVE USER
	
	@Test
	public void testRemoveUser() {
		SDIDImpl impl = new SDIDImpl();
		
		try {
			impl.createUser("alice", "alice@tecnico.pt");
			assertEquals(1, impl.getUsers().size());
			impl.removeUser("alice");
			assertEquals(0, impl.getUsers().size());
		} 
		
		catch (Exception e) {
			fail("Unexpected exception was thrown.");
		}
		
	}
	
	@Test (expected=UserDoesNotExist_Exception.class)
	public void testRemoveUserDoesNotExist() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		impl.removeUser("alice");
	}
	
	// REQUEST AUTHENTICATION
	
	@Test
	public void testRequestAuthentication() {
		SDIDImpl impl = new SDIDImpl();
		
		try {
			impl.createUser("alice", "alice@tecnico.pt");
			impl.getUsers().get(0).setPassword("Aaa1");
			
			byte[] res = impl.requestAuthentication("alice", new byte[]{'A', 'a', 'a', '1'});
			assertEquals(1, (int)res[0]);
		}
		
		catch (Exception e) {
			fail("Unexpected exception was thrown.");
		}
	}
	
	@Test (expected=AuthReqFailed_Exception.class)
	public void testRequestAuthenticationUserDoesNotExist() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		impl.requestAuthentication("alice", new byte[]{'A', 'a', 'a', '1'});
	}
	
	@Test (expected=AuthReqFailed_Exception.class)
	public void testRequestAuthenticationInvalidPassword() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		
		impl.createUser("alice", "alice@tecnico.pt");
		impl.getUsers().get(0).setPassword("Aaa1");
		
		impl.requestAuthentication("alice", new byte[]{'B', 'b', 'b', '2'});
	}
	
	// RENEW PASSWORD
	
	@Test
	public void renewPasswordTest() {
		SDIDImpl impl = new SDIDImpl();
		
		try { 
			impl.createUser("alice", "alice@tecnico.pt");
			String password = impl.getUsers().get(0).getPassword();
			
			impl.renewPassword("alice");
			String passwordRenewed = impl.getUsers().get(0).getPassword();
			
			assertNotEquals(password, passwordRenewed);
		}
		
		catch (Exception e) {
			fail("Unexpected exception was thrown.");
		}
	}
	
	@Test (expected=UserDoesNotExist_Exception.class)
	public void testRenewPasswordUserDoesNotExist() throws Exception {
		SDIDImpl impl = new SDIDImpl();
		impl.renewPassword("alice");
	}

}
