package userModel;

import node.Node;
import org.jdom2.Element;

public class User extends Node {
	
	private String login;
	private String firstname;
	private String surname;
	private String pwd;
	
	public User (String login, String firstname, String surname, String pwd, Element userNode ){
		super(userNode);
		setLogin(login);
		setFirstname(firstname);
		setSurname(surname);
		setPassword(pwd);
	}
	
	public void setLogin(String login){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("login").setText(login);
			} catch (Exception e) {
				System.out.println("login field not found");
			}
		}
		this.login = login;
	}
	
	public String getLogin(){
		return this.login;
	}
	
	public void setFirstname(String firstname){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("firstname").setText(firstname);
			} catch (Exception e) {
				System.out.println("firstname field not found");
			}
		}
		this.firstname = firstname;
	}
	
	public String getFirstname(){
		return this.firstname;
	}
	
	public void setSurname(String surname){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("surname").setText(surname);
			} catch (Exception e) {
				System.out.println("surname field not found");
			}
		}
		this.surname = surname;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public void setPassword(String pwd){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("pwd").setText(pwd);
			} catch (Exception e) {
				System.out.println("pwd field not found");
			}
		}
		this.pwd = pwd;
	}
	
	public String getPassword(){
		return this.pwd;
	}
}
