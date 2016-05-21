package userModel;

import org.jdom2.Element;

public class Teacher extends User{
    private int teacherID;

    public Teacher(String login, String firstname, String surname, String pwd, int teacherID){
        super(login, firstname, surname, pwd);
        setTeacherID(teacherID);
    }


    static Teacher initWithElement(Element teacherNode) {
        try {
            String login = teacherNode.getChildText("login");
            String firstname =  teacherNode.getChildText("firstname");
            String surname = teacherNode.getChildText("surname");
            String pwd = teacherNode.getChildText("pwd");
            int teacherId = Integer.parseInt(teacherNode.getChildText("teacherId"));

            return new Teacher(login, firstname, surname, pwd, teacherId);

        } catch (Exception e) {
            return null;
        }
    }

    public void setTeacherID(int teacherID){
        this.teacherID = teacherID;
    }

    public int getTeacherID(){
        return this.teacherID;
    }

}
