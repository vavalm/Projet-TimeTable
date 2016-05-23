package userModel;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int groupId;
    private int studentNumber;
    private List<Student> composition;



    public Group(int groupId, int studentNumber){
        this.groupId = groupId;
        this.studentNumber = studentNumber;
        this.composition = new ArrayList<Student>();
    }

    static Group initWithElement(Element groupNode) {
        try {
            int groupId = Integer.parseInt(groupNode.getChildText("groupId"));

            return new Group(groupId, 0);

        } catch (Exception e) {
            return null;
        }
    }




    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }



}
