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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Cette classe gére la base de données d'emplois du temps. Elle doit permettre de sauvegarder et charger les emplois du temps ainsi que les salles à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier TimeTableDB.xml.
 * @see <a href="../../TimeTableDB.xml">TimeTableDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier
public class TimeTableDB extends Node {
	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private String file;

    private Hashtable<Integer, Room> rooms;

    private Hashtable<Integer, TimeTable> timesTables;

	/**
	 * 
	 * Constructeur de TimeTableDB. 
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public TimeTableDB(String file){
		super();
        this.setRooms(new Hashtable<>());
        this.setTimesTables(new Hashtable<>());
		this.setFile(file);
        loadDB();
	}
	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public String getFile() {
		return file;
	}
	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
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

    public boolean addRoom(int roomId, int capacity) {
        try {
            Room newRoom = Room.initWithoutElement(roomId, capacity, this.getNode().getChild("Rooms"));
            if (newRoom == null) {
                return false;
            } else {
                this.getRooms().put(roomId, newRoom);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeRoom(int roomId) {
        Room room = this.getRooms().remove(roomId);
        if (room == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addTimeTable(int timeTableId) {
        try {
            TimeTable newTimeTable = TimeTable.initWithoutElement(timeTableId, this.getNode().getChild("TimeTables"));
            if (newTimeTable == null) {
                return false;
            } else {
                this.getTimesTables().put(timeTableId, newTimeTable);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTimeTable(int timeTableId) {
        TimeTable timeTable = this.getTimesTables().remove(timeTableId);
        if (timeTable == null) {
            return false;
        } else {
            return true;
        }
    }
}
