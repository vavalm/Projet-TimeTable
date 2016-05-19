package timeTableModel;


import org.jdom2.Element;

public class Room {
    private int roomID;
    private int maxStudents;

    public Room(int roomID, int maxStudents) {
        this.setRoomID(roomID);
        this.setMaxStudents(maxStudents);
    }

    static Room initWithElement(Element roomNode) {
        try {
            int roomId = Integer.parseInt(roomNode.getChildText("RoomId"));
            int roomCapacity = Integer.parseInt(roomNode.getChildText("Capacity"));
            return new Room(roomId, roomCapacity);
        } catch (Exception e) {
            return null;
        }
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }
}
