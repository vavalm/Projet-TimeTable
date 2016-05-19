package timeTableModel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.internal.SystemProperty;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
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
public class TimeTableDB {
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
		//TODO	À modifier
		super();
        this.rooms = new Hashtable<>();
        this.timesTables = new Hashtable<>();
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

            // Get rooms
            Element roomsNode = timeTablesDBNode.getChild("Rooms");
            List roomNodes = roomsNode.getChildren("Room");
            Iterator i = roomNodes.iterator();
            while (i.hasNext()) {
                Element roomNode = (Element)i.next();
                Room newRoom = Room.initWithElement(roomNode);
                if (newRoom != null) {
                    rooms.put(newRoom.getRoomID(), newRoom);
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
                    this.timesTables.put(newTimeTable.getTimeTableID(), newTimeTable);
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
		
	}
}
