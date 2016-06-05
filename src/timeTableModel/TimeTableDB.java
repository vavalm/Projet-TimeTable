package timeTableModel;

import node.Node;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Cette classe gére la base de données d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles à partir d'un fichier XML.
 * La structure du fichier XML devra être la même que celle du fichier TimeTableDB.xml.
 *
 * @author Valentin Maupin, Jose Mennesson et Quentin Solard
 * @version 06/2016
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a>
 */

public class TimeTableDB extends Node {
    /**
     * Le fichier contenant la base de données.
     */
    private String file;

    /**
     * La liste des salles de la base de données
     */
    private Hashtable<Integer, Room> rooms;

    /**
     * La liste des emplois du temps de la base de données
     */
    private Hashtable<Integer, TimeTable> timesTables;

    /**
     * Constructeur de TimeTableDB.
     *
     * @param file Le nom du fichier qui contient la base de données.
     */
    public TimeTableDB(String file) {
        super();
        this.setRooms(new Hashtable<>());
        this.setTimesTables(new Hashtable<>());
        this.setFile(file);
        loadDB();
    }

    /**
     * Getter de file
     *
     * @return Le nom du fichier qui contient la base de données.
     */
    public String getFile() {
        return file;
    }

    /**
     * Setter de file
     *
     * @param file Le nom du fichier qui contient la base de données.
     */
    public void setFile(String file) {
        this.file = file;
    }

    public boolean loadDB() {
        SAXBuilder sxb = new SAXBuilder();
        File xmlFile = new File(this.file);
        if (xmlFile.exists()) {
            Element timeTablesDBNode;
            try {
                Document xmlDB;
                xmlDB = sxb.build(xmlFile);

                timeTablesDBNode = xmlDB.getRootElement();
                this.setNode(timeTablesDBNode);

                // Get rooms
                Element roomsNode = timeTablesDBNode.getChild("Rooms");
                List roomNodes = roomsNode.getChildren("Room");
                Iterator i = roomNodes.iterator();
                while (i.hasNext()) {
                    Element roomNode = (Element) i.next();
                    Room newRoom = Room.initWithElement(roomNode);
                    if (newRoom != null) {
                        getRooms().put(newRoom.getRoomID(), newRoom);
                    } else {
                        System.out.println("Room attribute missing");
                    }
                }

                // Get TimeTables
                Element timeTablesNode = timeTablesDBNode.getChild("TimeTables");
                List timeTableNodes = timeTablesNode.getChildren("TimeTable");
                i = timeTableNodes.iterator();
                while (i.hasNext()) {
                    Element timeTableNode = (Element) i.next();
                    TimeTable newTimeTable = TimeTable.initWithElement(timeTableNode);
                    if (newTimeTable != null) {
                        this.getTimesTables().put(newTimeTable.getTimeTableID(), newTimeTable);
                    } else {
                        System.out.println("TimeTable attribute missing");
                    }
                }

                return true;

            } catch (JDOMException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            Document xmlDB = new Document();
            Element root = new Element("TimeTablesDB");
            this.setNode(root);
            Element roomsNode = new Element("Rooms");
            root.addContent(roomsNode);
            Element timeTablesNode = new Element("TimeTables");
            root.addContent(timeTablesNode);
            xmlDB.setRootElement(root);
            this.saveDB();
            return true;
        }

    }

    public boolean saveDB() {
        try {
            XMLOutputter exit = new XMLOutputter(Format.getPrettyFormat());
            exit.output(this.getNode().getDocument(), new FileOutputStream(this.getFile()));
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Hashtable<Integer, Room> getRooms() {
        return rooms;
    }

    public void setRooms(Hashtable<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    public Hashtable<Integer, TimeTable> getTimesTables() {
        return timesTables;
    }

    public void setTimesTables(Hashtable<Integer, TimeTable> timesTables) {
        this.timesTables = timesTables;
    }

    /**
     * Fonction permettant de vérifier qu'une salle ne soit pas occupée sur une plage horaire dans la base de données
     *
     * @param date1  Début de la plage horaire
     * @param date2  Fin de la plage horaire
     * @param roomId L'identifiant de la salle
     * @return Un booléen vrai si la salle est déjà occupée
     */
    public boolean isRoomInDateRangeAlreadyUsed(Date date1, Date date2, int roomId) {
        Enumeration<TimeTable> timeTableElement = this.getTimesTables().elements();
        while (timeTableElement.hasMoreElements()) {
            TimeTable timeTable = timeTableElement.nextElement();
            Enumeration<Book> bookElement = timeTable.getBooks().elements();
                while (bookElement.hasMoreElements()) {
                    Book book = bookElement.nextElement();
                    if (book.getRoomId() == roomId) {
                        if (date1.before(book.getEndDate()) && date2.after(book.getBeginDate())) {
                            return true;
                        }
                    }
                }
        }
        return false;
    }
}
