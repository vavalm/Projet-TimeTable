package timeTableModel;


public class Room {
    private int roomID;
    private int maxStudents;

    public Room(int roomID, int maxStudents) {
        this.setRoomID(roomID);
        this.setMaxStudents(maxStudents);
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
