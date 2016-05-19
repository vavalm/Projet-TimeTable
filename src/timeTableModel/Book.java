package timeTableModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom2.Element;


public class Book {
    private int bookID;
    private String teacherLogin;
    private Date beginDate;
    private Date endDate;
    private int roomId;

    public Book(int bookID, String teacherLogin, Date beginDate, Date endDate, int roomId) {
        this.setBookID(bookID);
        this.setTeacherLogin(teacherLogin);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setRoomId(roomId);
    }

    static Book initWithElement(Element bookNode) {
        try {
            int bookingId = Integer.parseInt(bookNode.getChildText("BookingId"));
            String login = bookNode.getChildText("Login");
            SimpleDateFormat parserSDF=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date begin = parserSDF.parse(bookNode.getChildText("DateBegin"));
            Date end = parserSDF.parse(bookNode.getChildText("DateEnd"));
            int roomId = Integer.parseInt(bookNode.getChildText("RoomId"));
            return new Book(bookingId, login, begin, end, roomId);
        } catch (Exception e) {
            return null;
        }
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
