package timeTableModel;

import java.util.Date;



public class Book {
    private int bookID;
    private String teacherLogin;
    private Date beginDate;
    private Date endDate;

    public Book(int bookID, String teacherLogin, Date beginDate, Date endDate) {
        this.setBookID(bookID);
        this.setTeacherLogin(teacherLogin);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTeacherLogin() {
        return teacherLogin;
    }

    public void setTeacherLogin(String teacherLogin) {
        this.teacherLogin = teacherLogin;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
