package timeTableModel;


import node.Node;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimeTable extends Node {
    private int timeTableID;
    private ArrayList<Book> books;

    public TimeTable(int timeTableID) {
        this.setTimeTableID(timeTableID);
    }

    static TimeTable initWithElement(Element timeTableNode) {
        try {
            int timeTableId = Integer.parseInt(timeTableNode.getChildText("TimeTableId"));
            TimeTable newTimeTable = new TimeTable(timeTableId);
            newTimeTable.setBooks(new ArrayList<>());
            Element booksNode = timeTableNode.getChild("Books");
            List bookNodes = booksNode.getChildren("Book");
            Iterator i = bookNodes.iterator();
            while (i.hasNext()) {
                Element bookNode = (Element)i.next();
                Book newBook = Book.initWithElement(bookNode);
                if (newBook != null) {
                    newTimeTable.addBook(newBook);
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

    static TimeTable initWithoutElement(int timeTableID, Element parentNode) {
        Element timeTableNode = new Element("TimeTable");
        parentNode.addContent(timeTableNode);

        Element timeTableIDNode = new Element("TimeTableId");
        timeTableIDNode.setText(Integer.toString(timeTableID));
        timeTableNode.addContent(timeTableIDNode);

        return TimeTable.initWithElement(timeTableNode);
    }

    public void addBook(Book book) {
        if (!getBooks().contains(book)) {
            getBooks().add(book);
        }
    }

    public boolean addBooking(int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
        Book newBook = Book.initWithoutElement(bookingId, login, dateBegin, dateEnd, roomId, this.getNode());
        if (newBook != null) {
            this.addBook(newBook);
            return true;
        } else {
            return false;
        }

    }

    public boolean removeBook(Book book) {
        for (int i = 0; i < getBooks().size(); i++) {
            if (getBooks().get(i) == book) {
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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
