package timeTableModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import node.Node;
import org.jdom2.Element;


public class Book extends Node {
    private int bookID;
    private String teacherLogin;
    private Date beginDate;
    private Date endDate;
    private int roomId;

    public Book(int bookID, String teacherLogin, Date beginDate, Date endDate, int roomId, Element bookNode) {
        super(bookNode);
        this.setBookID(bookID);
        this.setTeacherLogin(teacherLogin);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setRoomId(roomId);
    }

    public static Book initWithElement(Element bookNode) {
        try {
            int bookingId = Integer.parseInt(bookNode.getChildText("BookingId"));
            String login = bookNode.getChildText("Login");
            SimpleDateFormat parserSDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date begin = parserSDF.parse(bookNode.getChildText("DateBegin"));
            Date end = parserSDF.parse(bookNode.getChildText("DateEnd"));
            int roomId = Integer.parseInt(bookNode.getChildText("RoomId"));
            return new Book(bookingId, login, begin, end, roomId, bookNode);
        } catch (Exception e) {
            return null;
        }
    }

    public static Book initWithoutElement(int bookID, String teacherLogin, Date beginDate, Date endDate, int roomId, Element parentNode) {
        Element bookNode = new Element("Book");
        parentNode.addContent(bookNode);

        Element loginNode = new Element("Login");
        loginNode.setText(teacherLogin);
        bookNode.addContent(loginNode);

        SimpleDateFormat parserSDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Element beginDateNode = new Element("DateBegin");
        beginDateNode.setText(parserSDF.format(beginDate));
        bookNode.addContent(beginDateNode);

        Element endDateNode = new Element("DateEnd");
        endDateNode.setText(parserSDF.format(endDate));
        bookNode.addContent(endDateNode);

        Element roomIdNode = new Element("RoomId");
        roomIdNode.setText(Integer.toString(roomId));
        bookNode.addContent(roomIdNode);

        return Book.initWithElement(bookNode);
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
