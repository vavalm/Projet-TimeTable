package timeTableModel;

import java.text.SimpleDateFormat;
import java.util.Date;

import node.Node;
import org.jdom2.Element;

/**
 * Cette classe représente une réservation dans la base de données
 *
 * @author Valentin Maupin et Quentin Solard
 * @version 06/2016
 */
public class Book extends Node {

    /**
     * L'identifiant de la réservation
     */
    private int bookID;

    /**
     * Le login du professeur ayant effectué la réservation
     */
    private String teacherLogin;

    /**
     * La date et l'heure du début de la réservation
     */
    private Date beginDate;

    /**
     * La date et l'heure de fin de la réservation
     */
    private Date endDate;

    /**
     * L'identifiant de la salle réservée
     */
    private int roomId;


    /**
     * Le constructeur de Book
     *
     * @param bookID       L'identifiant de la réservation
     * @param teacherLogin Le login du professeur ayant effectué la réservation
     * @param beginDate    La date et l'heure du début de la réservation
     * @param endDate      La date et l'heure de fin de la réservation
     * @param roomId       L'identifiant de la salle réservée
     * @param bookNode     L'élément xml de la nouvelle
     */
    public Book(int bookID, String teacherLogin, Date beginDate, Date endDate, int roomId, Element bookNode) {
        super(bookNode);
        this.setBookID(bookID);
        this.setTeacherLogin(teacherLogin);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setRoomId(roomId);
    }

    /**
     * Fonction initialisant une réservation à partir d'un élément xml
     *
     * @param bookNode L'élément xml de la réservation à créer
     * @return La réservation créée
     */
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

    /**
     * @param bookID       L'identifiant de la réservation
     * @param teacherLogin Le login du professeur ayant effectué la réservation
     * @param beginDate    La date et l'heure du début de la réservation
     * @param endDate      La date et l'heure de fin de la réservation
     * @param roomId       L'identifiant de la salle réservée
     * @param parentNode   L'élément parent du nouvel élément (L'élément xml "Books")
     * @return La réservation créée
     */
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

    /**
     * Getter de bookID
     *
     * @return L'identifiant de la réservation
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * Setter de bookID
     *
     * @param bookID L'identifiant de la réservation
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }


    /**
     * Getter de teacherLogin
     *
     * @return Le login du professeur ayant effectué la réservation
     */
    public String getTeacherLogin() {
        return teacherLogin;
    }

    /**
     * Setter de teacherLogin
     *
     * @param teacherLogin Le login du professeur ayant effectué la réservation
     */
    public void setTeacherLogin(String teacherLogin) {
        this.teacherLogin = teacherLogin;
    }

    /**
     * Getter de beginDate
     *
     * @return La date et l'heure du début de la réservation
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * Setter de beginDate
     *
     * @param beginDate La date et l'heure du début de la réservation
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * Getter de endDate
     *
     * @return La date et l'heure de fin de la réservation
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter de endDate
     *
     * @param endDate La date et l'heure de fin de la réservation
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter de roomId
     *
     * @return L'identifiant de la salle réservée
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Setter de roomId
     *
     * @param roomId L'identifiant de la salle réservée
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

}
