package userModel;

public class Student extends User{
    private int studentID;

    public Student(String login, String firstname, String surname, String pwd, int studentID){
        super(login, firstname, surname, pwd);
        setStudentID(studentID);
    }

    public void setStudentID(int studentID){
        this.studentID = studentID;
    }

    public int getStudentID(){
        return this.studentID;
    }

}
