package timeTableModel;


import node.Node;
import org.jdom2.Element;

public class Room extends Node{
    private int roomID;
    private int maxStudents;

    public Room(int roomID, int maxStudents, Element roomNode) {
        super(roomNode);
        this.setRoomID(roomID);
        this.setMaxStudents(maxStudents);
    }

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

    public int getRoomID() {
        return roomID;
    }

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

    public int getMaxStudents() {
        return maxStudents;
    }

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
