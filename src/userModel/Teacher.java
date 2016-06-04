package userModel;

import org.jdom2.Element;

public class Teacher extends User{

    /**
     * Contient un identifiant de professeur
     *
     */

    private int teacherID;

    /**
     * Constructeur d'un utilisateur professeur
     *
     * @param login
     * 		Login du professeur
     * @param firstname
     * 		Prénom du professeur
     * @param surname
     * 		Nom du professeur
     * @param pwd
     * 		Mot de passe du professeur
     * @param teacherID
     * 		Identifiant du professeur
     * @param teacherNode
     * 		Element noeud associé au professeur
     *
     */

    public Teacher(String login, String firstname, String surname, String pwd, int teacherID, Element teacherNode){
        super(login, firstname, surname, pwd, teacherNode);
        setTeacherID(teacherID);
    }

    /**
     * Fonction permettant de créer un nouvel objet professeur à partir des éléments du noeud professeur
     * @param teacherNode
     * 		Element noeud associé au professeur
     * @return
     * 		La nouvelle instance du professeur
     */


    static Teacher initWithElement(Element teacherNode) {
        try {
            String login = teacherNode.getChildText("login");
            String firstname =  teacherNode.getChildText("firstname");
            String surname = teacherNode.getChildText("surname");
            String pwd = teacherNode.getChildText("pwd");
            int teacherID = Integer.parseInt(teacherNode.getChildText("teacherId"));

            return new Teacher(login, firstname, surname, pwd, teacherID, teacherNode);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction permettant de créer un nouvel objet professeur à partir de paramètres choisis par l'utilisateur
     * @param login
     * 		Login du professeur
     * @param firstname
     * 		Prénom du professeur
     * @param surname
     * 		Nom du professeur
     * @param pwd
     * 		Mot de passe du professeur
     * @param teacherID
     * 		Identifiant du professeur
     * @param parentNode
     * 		Element noeud parent associé au professeur
     * @return
     * 		La nouvelle instance du professeur
     */

    static Teacher initWithoutElement( String login, String firstname, String surname, String pwd, int teacherID, Element parentNode) {
        Element teacherNode = new Element("Teacher");
        parentNode.addContent(teacherNode);

        Element loginNode = new Element("login");
        loginNode.setText(login);
        teacherNode.addContent(loginNode);

        Element firstnameNode = new Element("firstname");
        firstnameNode.setText(firstname);
        teacherNode.addContent(firstnameNode);

        Element surnameNode = new Element("surname");
        surnameNode.setText(surname);
        teacherNode.addContent(surnameNode);

        Element pwdNode = new Element("pwd");
        pwdNode.setText(pwd);
        teacherNode.addContent(pwdNode);

        Element teacherIdNode = new Element("teacherId");
        teacherIdNode.setText(Integer.toString(teacherID));
        teacherNode.addContent(teacherIdNode);

        return Teacher.initWithElement(teacherNode);
    }

    /**
     * Setter de l'identifiant du professeur modifiant l'Element noeud correspondant
     * @param teacherID
     * 		Nouvel identifiant du professeur
     */

    public void setTeacherID(int teacherID) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("teacherId").setText(Integer.toString(teacherID));
            } catch (Exception e) {
                System.out.println("teacherId field not found");
            }
        }
        this.teacherID = teacherID;
    }

    /**
     * Getter de l'identifiant du professeur
     * @return
     * 		L'identifiant du professeur
     */

    public int getTeacherID(){
        return this.teacherID;
    }

}
