
package pt.ulisboa.tecnico.sdis.id.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SDId", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SDId {


    /**
     * 
     * @param emailAddress
     * @param userId
     * @throws InvalidEmail_Exception
     * @throws EmailAlreadyExists_Exception
     * @throws UserAlreadyExists_Exception
     * @throws InvalidUser_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "createUser", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.CreateUser")
    @ResponseWrapper(localName = "createUserResponse", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.CreateUserResponse")
    public void createUser(
        @WebParam(name = "userId", targetNamespace = "")
        String userId,
        @WebParam(name = "emailAddress", targetNamespace = "")
        String emailAddress)
        throws EmailAlreadyExists_Exception, InvalidEmail_Exception, InvalidUser_Exception, UserAlreadyExists_Exception
    ;

    /**
     * 
     * @param userId
     * @throws UserDoesNotExist_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "renewPassword", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RenewPassword")
    @ResponseWrapper(localName = "renewPasswordResponse", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RenewPasswordResponse")
    public void renewPassword(
        @WebParam(name = "userId", targetNamespace = "")
        String userId)
        throws UserDoesNotExist_Exception
    ;

    /**
     * 
     * @param userId
     * @throws UserDoesNotExist_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "removeUser", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RemoveUser")
    @ResponseWrapper(localName = "removeUserResponse", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RemoveUserResponse")
    public void removeUser(
        @WebParam(name = "userId", targetNamespace = "")
        String userId)
        throws UserDoesNotExist_Exception
    ;

    /**
     * 
     * @param reserved
     * @param userId
     * @return
     *     returns byte[]
     * @throws AuthReqFailed_Exception
     */
    @WebMethod
    @WebResult(name = "credentials", targetNamespace = "")
    @RequestWrapper(localName = "requestAuthentication", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RequestAuthentication")
    @ResponseWrapper(localName = "requestAuthenticationResponse", targetNamespace = "urn:pt:ulisboa:tecnico:sdis:id:ws", className = "pt.ulisboa.tecnico.sdis.id.ws.RequestAuthenticationResponse")
    public byte[] requestAuthentication(
        @WebParam(name = "userId", targetNamespace = "")
        String userId,
        @WebParam(name = "reserved", targetNamespace = "")
        byte[] reserved)
        throws AuthReqFailed_Exception
    ;

}
