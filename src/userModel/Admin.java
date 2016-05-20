package userModel;

public class Admin extends User {
	
	private int adminID;
	
	public Admin(String login, String firstname, String surname, String pwd, int adminID){
		super(login, firstname, surname, pwd);
		setAdminID(adminID);
	}
	
	public void setAdminID(int adminID){
		this.adminID = adminID;
	}
	
	public int getAdminID(){
		return this.adminID;
	}
	
}
