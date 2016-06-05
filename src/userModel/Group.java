package userModel;

import node.Node;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.List;

public class Group extends Node {

    /**
     * Contient un identifiant de groupe
     *
     */

    private int groupId;

    /**
     * Contient un nombre d'étudiants
     *
     */

    private int studentNumber;

    /**
     * Contient la liste d'étudiants composant le groupe
     *
     */

    private List<Student> composition;

    /**
     * Constructeur d'un groupe
     *
     * @param groupId
     * 		Identifiant du groupe
     * @param studentNumber
     * 		Nombre d'étudiants dans le groupe
     * @param groupNode
     * 		Element noeud associé au groupe
     *
     */

    public Group(int groupId, int studentNumber, Element groupNode){
        super(groupNode);
        this.groupId = groupId;
        this.studentNumber = studentNumber;
        this.composition = new ArrayList<Student>();
    }

    /**
     * Fonction permettant de créer un nouvel objet groupe à partir des éléments du noeud groupe
     * @param groupNode
     * 		Element noeud associé au groupe
     * @return
     * 		La nouvelle instance de groupe
     */

    static Group initWithElement(Element groupNode) {
        try {
            int groupId = Integer.parseInt(groupNode.getChildText("groupId"));

            return new Group(groupId, 0, groupNode);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction permettant de créer un nouvel objet groupe à partir de paramètres choisis par l'utilisateur
     * @param groupId
     * 		Identifiant du groupe
     * @param parentNode
     * 		Element noeud parent associé au groupe
     * @return
     * 		La nouvelle instance de groupe
     */

    static Group initWithoutElement(int groupId, Element parentNode) {
        Element groupNode = new Element("Group");
        parentNode.addContent(groupNode);

        Element groupIdNode = new Element("groupId");
        groupIdNode.setText(Integer.toString(groupId));
        groupNode.addContent(groupIdNode);

        return Group.initWithElement(groupNode);
    }

    /**
     * Getter de l'identifiant du groupe
     * @return
     * 		L'identifiant du groupe
     */

    public int getGroupId() {
        return groupId;
    }

    /**
     * Setter de l'identifiant du groupe modifiant l'Element noeud correspondant
     * @param groupId
     * 		Nouvel identifiant du groupe
     */

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

    /**
     * Getter du nombre d'étudiants dans le groupe
     * @return
     * 		Nombre d'étudiants dans le groupe
     */

    public int getStudentNumber() {
        return studentNumber;
    }

    /**
     * Setter du nombre d'étudiants dans le groupe modifiant l'Element noeud correspondant
     * @param studentNumber
     * 		Nouveau nombre d'étudiants dans le groupe
     */

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * Getter de la liste d'étudiants composant le groupe
     * @return
     * 		La liste d'étudiants qui compose le groupe
     */

    public List<Student> getComposition() {
        return composition;
    }

    /**
     * Setter de la liste d'étudiants composant le groupe modifiant l'Element noeud correspondant
     * @param composition
     * 		Nouvelle liste d'étudiants composant le groupe
     */

    public void setComposition(List<Student> composition) {
        this.composition = composition;
    }



}
