package timeTableModel;


import java.util.ArrayList;

public class TimeTable {
    private int timeTableID;
    private ArrayList<Book> books;

    public TimeTable(int timeTableID) {
        this.setTimeTableID(timeTableID);
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void removeBook(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i) == book) {
                books.remove(i);
            }
        }
    }

    public int getTimeTableID() {
        return timeTableID;
    }

    public void setTimeTableID(int timeTableID) {
        this.timeTableID = timeTableID;
    }
}
