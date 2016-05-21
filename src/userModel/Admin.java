package userModel;

import org.jdom2.Element;

public class Admin extends User {
	
	private int adminID;
	
	public Admin(String login, String firstname, String surname, String pwd, int adminID){
		super(login, firstname, surname, pwd);
		setAdminID(adminID);
	}

    static Admin initWithElement(Element adminNode) {
        try {
            String login = adminNode.getChildText("login");
            String firstname =  adminNode.getChildText("firstname");
            String surname = adminNode.getChildText("surname");
            String pwd = adminNode.getChildText("pwd");
            int adminId = Integer.parseInt(adminNode.getChildText("adminId"));

            return new Admin(login, firstname, surname, pwd, adminId);

        } catch (Exception e) {
            return null;
        }
    }
	
	
	public void setAdminID(int adminID){
		this.adminID = adminID;
	}
	
	public int getAdminID(){
		return this.adminID;
	}
	
}
