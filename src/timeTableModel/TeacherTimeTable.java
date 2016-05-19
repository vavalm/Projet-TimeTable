package timeTableModel;



public class TeacherTimeTable extends TimeTable{
    private String teacherLogin;

    public TeacherTimeTable(String teacherLogin, int timeTableID) {
        super(timeTableID);
        this.setTeacherLogin(teacherLogin);
    }

    public String getTeacherLogin() {
        return teacherLogin;
    }

    public void setTeacherLogin(String teacherLogin) {
        this.teacherLogin = teacherLogin;
    }
}
