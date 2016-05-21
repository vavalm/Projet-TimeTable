package userModel;
import org.jdom2.Element;
import org.omg.PortableInterceptor.Interceptor;


public class Student extends User{
    private int studentID;

    public Student(String login, String firstname, String surname, String pwd, int studentID){
        super(login, firstname, surname, pwd);
        setStudentID(studentID);
    }


    static Student initWithElement(Element studentNode) {
        try {
            String login = studentNode.getChildText("login");
            String firstname =  studentNode.getChildText("firstname");
            String surname = studentNode.getChildText("surname");
            String pwd = studentNode.getChildText("pwd");
            int studentID = Integer.parseInt(studentNode.getChildText("studentId"));

            return new Student(login, firstname, surname, pwd,studentID);

        } catch (Exception e) {
            return null;
        }
    }

    public void setStudentID(int studentID){
        this.studentID = studentID;
    }

    public int getStudentID(){
        return this.studentID;
    }

}
