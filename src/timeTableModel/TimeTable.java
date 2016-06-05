package timeTableModel;


import node.Node;
import org.jdom2.Element;

import java.util.*;

/**
 * Cette classe représente un emploi du temps dans la base de données
 *
 * @author Valentin Maupin et Quentin Solard
 * @version 06/2016
 */
public class TimeTable extends Node {

    /**
     * L'identifiant de l'eemploi du temps
     */
    private int timeTableID;

    /**
     * La liste de réservations de l'emploi du temps
     */
    private Hashtable<Integer, Book> books;

    /**
     * Le constructeur de TimeTable
     *
     * @param timeTableID L'identifiant de TimeTable
     */
    public TimeTable(int timeTableID, Element timeTableNode) {
        super(timeTableNode);
        this.setTimeTableID(timeTableID);
    }

    /**
     * Fonction initialisant un emploi du temps à partir d'un élément xml
     *
     * @param timeTableNode L'élément xml de l'emploi du temps à créer
     * @return L'emploi du temps créé
     */
    public static TimeTable initWithElement(Element timeTableNode) {
        try {
            int timeTableId = Integer.parseInt(timeTableNode.getChildText("TimeTableId"));
            TimeTable newTimeTable = new TimeTable(timeTableId, timeTableNode);
            newTimeTable.setBooks(new Hashtable<>());
            Element booksNode = timeTableNode.getChild("Books");
            List bookNodes = booksNode.getChildren("Book");
            Iterator i = bookNodes.iterator();
            while (i.hasNext()) {
                Element bookNode = (Element) i.next();
                Book newBook = Book.initWithElement(bookNode);
                if (newBook != null) {
                    if (newTimeTable.addBooking(newBook) == false) {
                        return null;
                    }
                } else {
                    System.out.println("Book attribute missing");
                }
            }
            return newTimeTable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fonction initialisant un emploi du temps et son élément xml associé
     *
     * @param timeTableID L'id de l'emploi du temps
     * @param parentNode  L'élément parent du nouvel élément (L'élément xml "TimeTables")
     * @return L'emploi du temps créé
     */
    public static TimeTable initWithoutElement(int timeTableID, Element parentNode) {
        Element timeTableNode = new Element("TimeTable");
        parentNode.addContent(timeTableNode);

        Element timeTableIDNode = new Element("TimeTableId");
        timeTableIDNode.setText(Integer.toString(timeTableID));
        timeTableNode.addContent(timeTableIDNode);

        Element booksNode = new Element("Books");
        timeTableIDNode.addContent(booksNode);

        return TimeTable.initWithElement(timeTableNode);
    }

    /**
     * Permet d'ajouter une réservation existante à l'emploi du temps
     *
     * @param newBook Nouvelle réservation
     * @return Un booléen indiquant si la réservation a bien été ajoutée
     */
    public boolean addBooking(Book newBook) {
        if (this.books.containsKey(newBook.getBookID())) {
            return false;
        } else {
            this.books.put(newBook.getBookID(), newBook);
            return true;
        }
    }

    /**
     * Permet d'ajouter une nouvelle réservation à l'emploi du temps
     *
     * @param bookingId L'identifiant de la réservation
     * @param login     Le login du professeur ayant effectué la réservation
     * @param dateBegin La date et l'heure du début de la réservation
     * @param dateEnd   La date et l'heure de fin de la réservation
     * @param roomId    L'identifiant de la salle réservée
     * @return Un booléen indiquant si la réservation a bien été ajoutée
     */
    public boolean addBooking(int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
        Book newBook = Book.initWithoutElement(bookingId, login, dateBegin, dateEnd, roomId, this.getNode());
        if (newBook != null) {
            this.addBooking(newBook);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Permet de supprimer une réservation
     *
     * @param book La réservation à supprimer
     * @return Un booléen indiquant si la réservation a bien été supprimée
     */
    public boolean removeBook(Book book) {
        for (int i = 0; i < getBooks().size(); i++) {
            if (getBooks().get(i) == book) {
                this.getNode().removeContent(getBooks().get(i).getNode());
                getBooks().remove(i);
                return true;
            }
        }
        return false;
    }


    /**
     * Getter de timeTableID
     *
     * @return L'identifiant de l'emploi du temps
     */
    public int getTimeTableID() {
        return timeTableID;
    }


    /**
     * Setter de timeTableID
     *
     * @param timeTableID L'identifiant de l'emploi du temps
     */
    public void setTimeTableID(int timeTableID) {
        this.timeTableID = timeTableID;
    }

    /**
     * Getter de books
     *
     * @return La liste des réservations de l'emploi du temps
     */
    public Hashtable<Integer, Book> getBooks() {
        return books;
    }


    /**
     * Setter de books
     *
     * @param books La liste des réservations de l'emploi du temps
     */
    public void setBooks(Hashtable<Integer, Book> books) {
        this.books = books;
    }

    /**
     * Fonction permettant de vérifier qu'une plage horaire ne soit pas déjà occupée dans l'emploi du temps
     *
     * @param date1 Début de la plage horaire
     * @param date2 Fin de la plage horaire
     * @return Un booléen vrai si la plage horaire est déjà occupée
     */
    public boolean isDateRangeOverlapping(Date date1, Date date2) {
        Enumeration<Book> bookElement = this.getBooks().elements();
        while (bookElement.hasMoreElements()) {
            Book book = bookElement.nextElement();
            if (date1.before(book.getEndDate()) && date2.after(book.getBeginDate())) {
                return true;
            }
        }
        return false;
    }
}
