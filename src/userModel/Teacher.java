package userModel;

public class Teacher extends User{
    private int teacherID;

    public Teacher(String login, String firstname, String surname, String pwd, int teacherID){
        super(login, firstname, surname, pwd);
        setTeacherID(teacherID);
    }

    public void setTeacherID(int teacherID){
        this.teacherID = teacherID;
    }

    public int getTeacherID(){
        return this.teacherID;
    }

}
