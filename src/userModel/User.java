package userModel;



public class User {
	
	private String login;
	private String firstname;
	private String surname;
	private String pwd;
	
	public User (String login, String firstname, String surname, String pwd){
		setLogin(login);
		setFirstname(firstname);
		setSurname(surname);
		setPassword(pwd);
	}
	
	public void setLogin(String login){
		this.login = login;
	}
	
	public String getLogin(){
		return this.login;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public String getFirstname(){
		return this.firstname;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public void setPassword(String pwd){
		this.pwd = pwd;
	}
	
	public String getPassword(){
		return this.pwd;
	}
}
