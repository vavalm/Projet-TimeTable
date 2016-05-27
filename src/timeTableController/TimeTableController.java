package timeTableController;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import timeTableModel.Book;
import timeTableModel.Room;
import timeTableModel.TimeTable;
import timeTableModel.TimeTableDB;

/**
 * Cette classe est le contrôleur d'emplois du temps que vous devez implémenter.
 * Elle contient un attribut correspondant à la base de données d'emplois du temps que vous allez créer.
 * Elle contient toutes les fonctions de l'interface ITimeTableController que vous devez implémenter.
 *
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 */

//TODO Classe à modifier

public class TimeTableController implements ITimeTableController {

    /**
     * Contient une instance de base de données d'emplois du temps
     */
    private TimeTableDB tTDB;

    /**
     * Constructeur de controleur d'emplois du temps créant la base de données d'emplois du temps
     *
     * @param tTfile Fichier XML contenant la base de données d'emplois du temps
     */
    public TimeTableController(String tTfile) {
        this.tTDB = new TimeTableDB(tTfile);
    }

    @Override
    public String getTeacherLogin(int timeTableId, int bookId) {
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        return timeTable.getBooks().get(bookId).getTeacherLogin();
    }

    @Override
    public String[] roomsIdToString() {
        Hashtable<Integer, Room> table = tTDB.getRooms();
        String response[] = new String[table.size()];

        int i = 0;

        Enumeration<Room> elements = table.elements();
        while (elements.hasMoreElements()) {
            Room r = elements.nextElement();
            try {
                response[i] = Integer.toString(r.getRoomID());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public String[] roomsToString() {
        Hashtable<Integer, Room> table = tTDB.getRooms();
        String response[] = new String[table.size()];

        int i = 0;

        Enumeration<Room> elements = table.elements();
        while (elements.hasMoreElements()) {
            Room r = elements.nextElement();
            try {
                response[i] = "ID: ";
                response[i] += Integer.toString(r.getRoomID());
                response[i] += " | Capacity: " + Integer.toString(r.getMaxStudents());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public String[] timeTablesIDToString() {
        Hashtable<Integer, TimeTable> table = tTDB.getTimesTables();
        String response[] = new String[table.size()];

        int i = 0;

        Enumeration<TimeTable> elements = table.elements();
        while (elements.hasMoreElements()) {
            TimeTable r = elements.nextElement();
            try {
                response[i] = Integer.toString(r.getTimeTableID());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public String[] booksIdToString(int timeTableId) {
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Enumeration element = timeTable.getBooks().elements();
        String response[] = new String[timeTable.getBooks().size()];
        int i = 0;
        while (element.hasMoreElements()) {
            Book r = (Book)element.nextElement();
            try {
                response[i] = Integer.toString(r.getBookID());
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public boolean addRoom(int roomId, int capacity) {
        return this.tTDB.addRoom(roomId, capacity);
    }

    @Override
    public boolean removeRoom(int roomId) {
        Room room = this.tTDB.getRooms().remove(roomId);
        if (room == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getRoom(int timeTableId, int bookId) {
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Book book = timeTable.getBooks().get(bookId);
        if (book != null) {
            return book.getRoomId();
        }
        return 0;
    }

    @Override
    public boolean addTimeTable(int timeTableId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeTimeTable(int timeTableId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {

        return false;
    }

    @Override
    public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean removeBook(int timeTableId, int bookId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getBookingsMaxId(int timeTableId) {
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Enumeration element = timeTable.getBooks().elements();
        int maxKey = 0;
        while (element.hasMoreElements()) {
            Book r = (Book)element.nextElement();
            if (r.getBookID() > maxKey) {
                maxKey = r.getBookID();
            }
        }
        return maxKey;
    }

    @Override
    public boolean saveDB() {
        tTDB.saveDB();
        return false;
    }

    @Override
    public boolean loadDB() {
        tTDB.loadDB();
        return false;
    }


}
