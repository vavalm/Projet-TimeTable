package timeTableController;

import java.sql.Time;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jdom2.Element;
import timeTableModel.Book;
import timeTableModel.Room;
import timeTableModel.TimeTable;
import timeTableModel.TimeTableDB;

/**
 * Cette classe est le contrôleur d'emplois du temps que vous devez implémenter.
 * Elle contient un attribut correspondant à la base de données d'emplois du temps que vous allez créer.
 * Elle contient toutes les fonctions de l'interface ITimeTableController que vous devez implémenter.
 *
 * @author Valentin Maupin, Jose Mennesson et Quentin Solard
 * @version 05/2016
 */


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
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        return timeTable.getBooks().get(bookId).getTeacherLogin();
    }

    @Override
    public String[] roomsIdToString() {
        this.loadDB();
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
        this.loadDB();
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
        this.loadDB();
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
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Enumeration element = timeTable.getBooks().elements();
        String response[] = new String[timeTable.getBooks().size()];
        int i = 0;
        while (element.hasMoreElements()) {
            Book r = (Book) element.nextElement();
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
        this.loadDB();
        if (this.tTDB.getRooms().get(roomId) != null) {
            System.out.println("RoomId already in use");
            return false;
        }
        try {
            Room newRoom = Room.initWithoutElement(roomId, capacity, this.tTDB.getNode().getChild("Rooms"));
            if (newRoom == null) {
                return false;
            } else {
                this.tTDB.getRooms().put(roomId, newRoom);
                this.saveDB();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeRoom(int roomId) {
        this.loadDB();
        Room room = this.tTDB.getRooms().remove(roomId);
        if (room == null) {
            System.out.println("Room does not exist");
            return false;
        } else {
            this.tTDB.getNode().getChild("Rooms").removeContent(room.getNode());
            this.saveDB();
            return true;
        }
    }

    @Override
    public int getRoom(int timeTableId, int bookId) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Book book = timeTable.getBooks().get(bookId);
        if (book != null) {
            return book.getRoomId();
        }
        return 0;
    }

    @Override
    public boolean addTimeTable(int timeTableId) {
        this.loadDB();
        if (this.tTDB.getTimesTables().get(timeTableId) != null) {
            System.out.println("TimeTableId is already in use");
            return false;
        }
        try {
            TimeTable newTimeTable = TimeTable.initWithoutElement(timeTableId, this.tTDB.getNode().getChild("TimeTables"));
            if (newTimeTable == null) {
                return false;
            } else {
                this.tTDB.getTimesTables().put(timeTableId, newTimeTable);
                this.saveDB();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeTimeTable(int timeTableId) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().remove(timeTableId);
        if (timeTable == null) {
            return false;
        } else {
            this.tTDB.getNode().getChild("TimeTables").removeContent(timeTable.getNode());
            this.saveDB();
            return true;
        }
    }

    @Override
    public boolean addBooking(int timeTableId, int bookingId, String login, Date dateBegin, Date dateEnd, int roomId) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        if (timeTable != null) {
            try {
                Element booksNode = timeTable.getNode().getChild("Books");
                if (this.tTDB.getTimesTables().get(timeTableId).getBooks().get(bookingId) != null) {
                    System.out.println("BookingId already in use");
                    return false;
                }

                // on vérifie si la salle n'est pas déjà réservée à la même date
                if (this.tTDB.isRoomInDateRangeAlreadyUsed(dateBegin, dateEnd, roomId)) {
                    System.out.println("Salle déjà utilisée");
                    return false;
                }

                // on vérifie également que la plage horaire n'est pas déjà occupée dans l'emploi du temps
                if (timeTable.isDateRangeOverlapping(dateBegin, dateEnd)) {
                    System.out.println("Plage horaire déjà utilisée");
                    return false;
                }

                Book newBook = Book.initWithoutElement(bookingId, login, dateBegin, dateEnd, roomId, booksNode);
                if (newBook != null) {
                    this.tTDB.getTimesTables().get(timeTableId).getBooks().put(bookingId, newBook);
                    this.saveDB();
                    return true;
                } else {
                    System.out.println("Erreur lors de l'initialisation de la réservation");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Missing Books element");
                e.printStackTrace();
                return false;
            }

        } else {
            System.out.println("TimeTable not found");
            return false;
        }
    }

    @Override
    public void getBookingsDate(int timeTableId, Hashtable<Integer, Date> dateBegin, Hashtable<Integer, Date> dateEnd) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        if (timeTable == null) {
            System.out.println("TimeTable does not exist");
            return;
        }
        Hashtable<Integer, Book> books = timeTable.getBooks();
        Enumeration element = books.elements();
        while (element.hasMoreElements()) {
            Book book = (Book) element.nextElement();
            dateBegin.put(book.getBookID(), book.getBeginDate());
            dateEnd.put(book.getBookID(), book.getEndDate());
        }
    }

    @Override
    public boolean removeBook(int timeTableId, int bookId) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        if (timeTable == null) {
            System.out.println("TimeTable does not exist");
            return false;
        }
        Book book = timeTable.getBooks().get(bookId);
        if (book == null) {
            System.out.println("Book doesn't exist");
            return false;
        }
        timeTable.getBooks().remove(bookId);
        try {
            timeTable.getNode().getChild("Books").removeContent(book.getNode());
            this.saveDB();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getBookingsMaxId(int timeTableId) {
        this.loadDB();
        TimeTable timeTable = this.tTDB.getTimesTables().get(timeTableId);
        Enumeration element = timeTable.getBooks().elements();
        int maxKey = 0;
        while (element.hasMoreElements()) {
            Book r = (Book) element.nextElement();
            if (r.getBookID() > maxKey) {
                maxKey = r.getBookID();
            }
        }
        return maxKey + 1;
    }

    @Override
    public boolean saveDB() {
        return tTDB.saveDB();
    }

    @Override
    public boolean loadDB() {
        return tTDB.loadDB();
    }
}
