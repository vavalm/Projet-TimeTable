package userModel;

import org.jdom2.Element;

public class Admin extends User {

    /**
     * Contient un identifiant d'administrateur
     *
     */
	private int adminID;

    /**
     * Constructeur d'un utilisateur administrateur
     *
     * @param login
     * 		Login de l'administrateur
     * @param firstname
     * 		Prénom de l'administrateur
     * @param surname
     * 		Nom de l'administrateur
     * @param pwd
     * 		Mot de passe de l'administrateur
     * @param adminID
     * 		Identifiant de l'administrateur
     * @param adminNode
     * 		Element noeud associé à l'administrateur
     *
     */

	public Admin(String login, String firstname, String surname, String pwd, int adminID, Element adminNode){
		super(login, firstname, surname, pwd, adminNode);
		setAdminID(adminID);
	}

    /**
     * Fonction permettant de créer un nouvel objet administrateur à partir des éléments du noeud administrateur
     * @param adminNode
     * 		Element noeud associé à l'administrateur
     * @return
     * 		La nouvelle instance d'administrateur
     */

    static Admin initWithElement(Element adminNode) {
        try {
            String login = adminNode.getChildText("login");
            String firstname =  adminNode.getChildText("firstname");
            String surname = adminNode.getChildText("surname");
            String pwd = adminNode.getChildText("pwd");
            int adminID = Integer.parseInt(adminNode.getChildText("adminId"));

            return new Admin(login, firstname, surname, pwd, adminID, adminNode);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction permettant de créer un nouvel objet administrateur à partir de paramètres choisis par l'utilisateur
     * @param login
     * 		Login de l'administrateur
     * @param firstname
     * 		Prénom de l'administrateur
     * @param surname
     * 		Nom de l'administrateur
     * @param pwd
     * 		Mot de passe de l'administrateur
     * @param adminID
     * 		Identifiant de l'administrateur
     * @param parentNode
     * 		Element noeud parent associé à l'administrateur
     * @return
     * 		La nouvelle instance d'administrateur
     */

    static Admin initWithoutElement( String login, String firstname, String surname, String pwd, int adminID, Element parentNode) {
        Element adminNode = new Element("Administrator");
        parentNode.addContent(adminNode);

        Element loginNode = new Element("login");
        loginNode.setText(login);
        adminNode.addContent(loginNode);

        Element firstnameNode = new Element("firstname");
        firstnameNode.setText(firstname);
        adminNode.addContent(firstnameNode);

        Element surnameNode = new Element("surname");
        surnameNode.setText(surname);
        adminNode.addContent(surnameNode);

        Element pwdNode = new Element("pwd");
        pwdNode.setText(pwd);
        adminNode.addContent(pwdNode);

        Element adminIdNode = new Element("adminId");
        adminIdNode.setText(Integer.toString(adminID));
        adminNode.addContent(adminIdNode);

        return Admin.initWithElement(adminNode);
    }

    /**
     * Setter de l'identifiant de l'administrateur modifiant l'Element noeud correspondant
     * @param adminID
     * 		Nouvel identifiant de l'administrateur
     */

    public void setAdminID(int adminID) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("adminId").setText(Integer.toString(adminID));
            } catch (Exception e) {
                System.out.println("adminId field not found");
            }
        }
        this.adminID = adminID;
    }

    /**
     * Getter de l'identifiant de l'administrateur
     * @return
     * 		L'identifiant de l'administrateur
     */

	public int getAdminID(){
		return this.adminID;
	}
	
}
