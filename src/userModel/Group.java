package userModel;

import node.Node;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class Group extends Node {

    private int groupId;
    private int studentNumber;
    private List<Student> composition;



    public Group(int groupId, int studentNumber, Element groupNode){
        super(groupNode);
        this.groupId = groupId;
        this.studentNumber = studentNumber;
        this.composition = new ArrayList<Student>();
    }

    static Group initWithElement(Element groupNode) {
        try {
            int groupId = Integer.parseInt(groupNode.getChildText("groupId"));

            return new Group(groupId, 0, groupNode);

        } catch (Exception e) {
            return null;
        }
    }

    static Group initWithoutElement(int groupId, Element parentNode) {
        Element groupNode = new Element("Group");
        parentNode.addContent(groupNode);

        Element groupIdNode = new Element("groupId");
        groupIdNode.setText(Integer.toString(groupId));
        groupNode.addContent(groupIdNode);

        return Group.initWithElement(groupNode);
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        if (this.getNode() != null) {
            try {
                this.getNode().getChild("groupId").setText(Integer.toString(groupId));
            } catch (Exception e) {
                System.out.println("groupId field not found");
            }
        }
        this.groupId = groupId;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }



}
