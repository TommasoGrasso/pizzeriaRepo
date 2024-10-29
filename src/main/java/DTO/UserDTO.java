package DTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "userDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO implements Serializable{ 

	private static final long serialVersionUID = -302543065901090808L;
	
	private int id;
	private String username;
//	private String password;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
//	
//	public String getPassword() {
//		return password;
//	}
//	
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	public UserDTO(int id, String username) {
		super();
		this.id = id;
		this.username = username;
//		this.password = password;
	}
	
	public UserDTO() {
		super();
	}
	
}
