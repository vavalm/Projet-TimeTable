package userModel;

import org.jdom2.Element;

public class Teacher extends User{
    private int teacherID;

    public Teacher(String login, String firstname, String surname, String pwd, int teacherID, Element teacherNode){
        super(login, firstname, surname, pwd, teacherNode);
        setTeacherID(teacherID);
    }

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

    public int getTeacherID(){
        return this.teacherID;
    }

}
