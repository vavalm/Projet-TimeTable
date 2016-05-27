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
	
	public void loadDB() {
		Document xmlDB;
		SAXBuilder sxb = new SAXBuilder();
        Element timeTablesDBNode;
		try {
			xmlDB = sxb.build(new File(this.file));

            timeTablesDBNode = xmlDB.getRootElement();
            this.setNode(timeTablesDBNode);

            // Get rooms
            Element roomsNode = timeTablesDBNode.getChild("Rooms");
            List roomNodes = roomsNode.getChildren("Room");
            Iterator i = roomNodes.iterator();
            while (i.hasNext()) {
                Element roomNode = (Element)i.next();
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
                Element timeTableNode = (Element)i.next();
                TimeTable newTimeTable = TimeTable.initWithElement(timeTableNode);
                if (newTimeTable != null) {
                    this.getTimesTables().put(newTimeTable.getTimeTableID(), newTimeTable);
                } else {
                    System.out.println("TimeTable attribute missing");
                }
            }

		} catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public void saveDB() {
		try {
            XMLOutputter exit = new XMLOutputter(Format.getPrettyFormat());
            exit.output(this.getNode().getDocument(), new FileOutputStream(this.getFile()));
        } catch (java.io.IOException e) {
            e.printStackTrace();
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
            return false;
        }
    }
}
