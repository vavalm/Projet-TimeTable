package timeTableModel;


import node.Node;
import org.jdom2.Element;

/**
 * Cette classe représente une salle dans la base de données
 *
 * @author Valentin Maupin et Quentin Solard
 * @version 06/2016
 */
public class Room extends Node {

    /**
     * Identifiant de la salle
     */
    private int roomID;

    /**
     * Capacité maximum de la salle
     */
    private int maxStudents;


    /**
     * Constructeur de Room
     *
     * @param roomID      L'identifiant de la salle
     * @param maxStudents La capacité maximum de la salle
     * @param roomNode    L'élément xml de la salle
     */
    public Room(int roomID, int maxStudents, Element roomNode) {
        super(roomNode);
        this.setRoomID(roomID);
        this.setMaxStudents(maxStudents);
    }

    /**
     * Fonction initialisant une salle à partir d'un élément xml
     *
     * @param roomNode L'élément xml de la salle à créer
     * @return La salle créée
     */
    public static Room initWithElement(Element roomNode) {
        try {
            int roomId = Integer.parseInt(roomNode.getChildText("RoomId"));
            int roomCapacity = Integer.parseInt(roomNode.getChildText("Capacity"));
            return new Room(roomId, roomCapacity, roomNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fonction initialisant une salle et son élément xml associé
     *
     * @param roomID     L'identifiant de la salle
     * @param capacity   La capacité maximum de la salle
     * @param parentNode L'élément parent du nouvel élément (L'élément xml "Rooms")
     * @return La salle créée
     */
    public static Room initWithoutElement(int roomID, int capacity, Element parentNode) {
        Element roomNode = new Element("Room");
        parentNode.addContent(roomNode);

        Element roomIdNode = new Element("RoomId");
        roomIdNode.setText(Integer.toString(roomID));
        roomNode.addContent(roomIdNode);

        Element capacityNode = new Element("Capacity");
        capacityNode.setText(Integer.toString(capacity));
        roomNode.addContent(capacityNode);

        return Room.initWithElement(roomNode);
    }

    /**
     * Getter de roomID
     *
     * @return L'identifiant de la salle
     */
    public int getRoomID() {
        return roomID;
    }

    /**
     * Setter de roomID
     *
     * @param roomID L'identifiant de la salle
     */
    public void setRoomID(int roomID) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("RoomId").setText(Integer.toString(roomID));
            } catch (Exception e) {
                System.out.println("RoomId field not find");
            }
        }
        this.roomID = roomID;
    }

    /**
     * Getter de maxStudents
     *
     * @return La capacité maximum de la salle
     */
    public int getMaxStudents() {
        return maxStudents;
    }

    /**
     * Setter de maxStudents
     *
     * @param maxStudents La capacité maximum de la salle
     */
    public void setMaxStudents(int maxStudents) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("Capacity").setText(Integer.toString(maxStudents));
            } catch (Exception e) {
                System.out.println("Capacity field not find");
            }
        }
        this.maxStudents = maxStudents;
    }
}
