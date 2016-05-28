package timeTableModel;


import node.Node;
import org.jdom2.Element;

import java.util.*;

public class TimeTable extends Node {
    private int timeTableID;
    private Hashtable<Integer, Book> books;

    public TimeTable(int timeTableID) {
        this.setTimeTableID(timeTableID);
    }

    public static TimeTable initWithElement(Element timeTableNode) {
        try {
            int timeTableId = Integer.parseInt(timeTableNode.getChildText("TimeTableId"));
            TimeTable newTimeTable = new TimeTable(timeTableId);
            newTimeTable.setBooks(new Hashtable<>());
            Element booksNode = timeTableNode.getChild("Books");
            List bookNodes = booksNode.getChildren("Book");
            Iterator i = bookNodes.iterator();
            while (i.hasNext()) {
                Element bookNode = (Element)i.next();
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

    public boolean addBooking(Book newBook) {
        if (this.books.containsKey(newBook.getBookID())) {
            return false;
        } else {
            this.books.put(newBook.getBookID(), newBook);
            return true;
        }
    }

    public boolean addBooking(int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
        Book newBook = Book.initWithoutElement(bookingId, login, dateBegin, dateEnd, roomId, this.getNode());
        if (newBook != null) {
            this.addBooking(newBook);
            return true;
        } else {
            return false;
        }

    }

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

    public int getTimeTableID() {
        return timeTableID;
    }

    public void setTimeTableID(int timeTableID) {
        this.timeTableID = timeTableID;
    }

    public Hashtable<Integer, Book> getBooks() {
        return books;
    }

    public void setBooks(Hashtable<Integer, Book> books) {
        this.books = books;
    }
}
