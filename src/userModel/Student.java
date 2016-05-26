package userModel;
import org.jdom2.Element;
import org.omg.PortableInterceptor.Interceptor;


public class Student extends User{
    private int studentID;

    public Student(String login, String firstname, String surname, String pwd, int studentID, Element studentNode){
        super(login, firstname, surname, pwd, studentNode);
        setStudentID(studentID);
    }

    static Student initWithElement(Element studentNode) {
        try {
            String login = studentNode.getChildText("login");
            String firstname =  studentNode.getChildText("firstname");
            String surname = studentNode.getChildText("surname");
            String pwd = studentNode.getChildText("pwd");
            int studentID = Integer.parseInt(studentNode.getChildText("studentId"));

            return new Student(login, firstname, surname, pwd, studentID, studentNode);

        } catch (Exception e) {
            return null;
        }
    }

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

    public int getStudentID(){
        return this.studentID;
    }

}
