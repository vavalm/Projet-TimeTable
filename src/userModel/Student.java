package userModel;
import org.jdom2.Element;
import org.omg.PortableInterceptor.Interceptor;


public class Student extends User{

    private int studentID;

    private int groupID;

    /**
     * Constructeur d'un utilisateur étudiant
     *
     * @param login
     * 		Login de l'étudiant
     * @param firstname
     * 		Prénom de l'étudiant
     * @param surname
     * 		Nom de l'étudiant
     * @param pwd
     * 		Mot de passe de l'étudiant
     * @param studentID
     * 		Identifiant de l'étudiant
     * @param studentNode
     * 		Element noeud associé à l'étudiant
     *
     */

    public Student(String login, String firstname, String surname, String pwd, int studentID, Element studentNode){
        super(login, firstname, surname, pwd, studentNode);
        setStudentID(studentID);
    }

    /**
     * Fonction permettant de créer un nouvel objet étudiant à partir des éléments du noeud étudiant
     * @param studentNode
     * 		Element noeud associé à l'étudiant
     * @return
     * 		La nouvelle instance d'étudiant
     */

    static Student initWithElement(Element studentNode) {
        try {
            String login = studentNode.getChildText("login");
            String firstname =  studentNode.getChildText("firstname");
            String surname = studentNode.getChildText("surname");
            String pwd = studentNode.getChildText("pwd");
            int groupID = Integer.parseInt(studentNode.getChildText("groupId"));
            int studentID = Integer.parseInt(studentNode.getChildText("studentId"));
            Student newStudent = new Student(login, firstname, surname, pwd, studentID, studentNode);
            newStudent.setGroupID(groupID);
            return newStudent;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction permettant de créer un nouvel objet étudiant à partir de paramètres choisis par l'utilisateur
     * @param login
     * 		Login de l'étudiant
     * @param firstname
     * 		Prénom de l'étudiant
     * @param surname
     * 		Nom de l'étudiant
     * @param pwd
     * 		Mot de passe de l'étudiant
     * @param studentID
     * 		Identifiant de l'étudiant
     * @param parentNode
     * 		Element noeud parent associé à l'étudiant
     * @return
     * 		La nouvelle instance d'étudiant
     */

    static Student initWithoutElement( String login, String firstname, String surname, String pwd, int studentID, Element parentNode) {
        Element studentNode = new Element("Student");
        parentNode.addContent(studentNode);

        Element loginNode = new Element("login");
        loginNode.setText(login);
        studentNode.addContent(loginNode);

        Element firstnameNode = new Element("firstname");
        firstnameNode.setText(firstname);
        studentNode.addContent(firstnameNode);

        Element surnameNode = new Element("surname");
        surnameNode.setText(surname);
        studentNode.addContent(surnameNode);

        Element pwdNode = new Element("pwd");
        pwdNode.setText(pwd);
        studentNode.addContent(pwdNode);

        Element studentIdNode = new Element("studentId");
        studentIdNode.setText(Integer.toString(studentID));
        studentNode.addContent(studentIdNode);

        Element groupIdNode = new Element("groupId");
        groupIdNode.setText("-1");
        studentNode.addContent(groupIdNode);

        return Student.initWithElement(studentNode);
    }

    /**
     * Setter de l'identifiant de l'étudiant modifiant l'Element noeud correspondant
     * @param studentID
     * 		Nouvel identifiant de l'étudiant
     */

    public void setStudentID(int studentID) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("studentId").setText(Integer.toString(studentID));
            } catch (Exception e) {
                System.out.println("studentId field not found");
            }
        }
        this.studentID = studentID;
    }

    /**
     * Contient un identifiant d'étudiant
     *
     */ /**
     * Getter de l'identifiant de l'étudiant
     * @return
     * 		L'identifiant de l'étudiant
     */

    public int getStudentID(){
        return this.studentID;
    }

    /**
     * Contient l'identifiant du groupe auquel l'élève appartient
     */
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
