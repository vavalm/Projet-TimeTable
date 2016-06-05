package userModel;
import node.Node;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.*;



/**
 * 
 * Cette classe gére la base de données d'utilisateurs. Elle doit permettre de sauvegarder et charger les utilisateurs et les groupes à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

public class UserDB extends Node{

	/**
	 * Le fichier contenant la base de données.
	 */
	private String file;

	/**
	 * Contient la liste des utilisateurs, la liste des groupes, document utilisé pour parcourir le fichier XML et noeud Element associé à UserDB
	 */
	private List<User> userList = new ArrayList<User>();
	private List<Group> groupList = new ArrayList<Group>();
	private Document parserDB;
	private Element userDBNode;


	/**
	 * Constructeur de UserDB.
	 *
	 * @param file Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String file) {
		super();
		this.setFile(file);
		loadDB();

	}

	/**
	 * Getter de file
	 *
	 * @return Le nom du fichier qui contient la base de données.
	 */

	public String getFile() {
		return file;
	}

	/**
	 * Setter de file
	 *
	 * @param file Le nom du fichier qui contient la base de données.
	 */

	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * Getter de la liste d'utilisateurs
	 * @return
	 * 		La liste d'utilisateurs
	 */

	public List<User> getUserList() {
		return userList;
	}

	/**
	 * Setter de la liste d'utilisateurs
	 *
	 * @param userList La nouvelle liste d'utilisateurs
	 */

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * Getter de la liste de groupes
	 * @return
	 * 		La liste de groupes
	 */

	public List<Group> getGroupList() {
		return groupList;
	}

	/**
	 * Setter de la liste de groupes
	 *
	 * @param groupList La nouvelle liste de groupes
	 */

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	public void loadDB() {

		SAXBuilder sxb = new SAXBuilder();
		File xmlFile = new File(this.file);

		//On vérifie l'existence du fichier XML

		if(xmlFile.exists()) {
			try {
				Document parserDB;
				parserDB = sxb.build(xmlFile);
				userDBNode = parserDB.getRootElement();
				this.setNode(userDBNode);

				// On charge l'ensemble des groupes du fichier XML dans la liste de groupes

				Element groupsNode = userDBNode.getChild("Groups");
				List groupNodes = groupsNode.getChildren("Group");
				Iterator j = groupNodes.iterator();
				while (j.hasNext()) {
					Element groupNode = (Element) j.next();

					Group newGroup = Group.initWithElement(groupNode);

					if (newGroup != null) {
						this.groupList.add(newGroup);

					} else {
						System.out.println("Group attribute missing");
					}
				}

				// On charge l'ensemble des étudiants du fichier XML dans la liste d'utilisateurs

				Element studentsNode = userDBNode.getChild("Students");
				List studentNodes = studentsNode.getChildren("Student");
				Iterator i = studentNodes.iterator();
				while (i.hasNext()) {
					Element studentNode = (Element) i.next();

					Student newStudent = Student.initWithElement(studentNode);

					if (newStudent != null) {
                        Iterator groupListIt = this.getGroupList().iterator();
                        while (groupListIt.hasNext()) {
                            Group group = (Group)groupListIt.next();
                            if (group.getGroupId() == newStudent.getGroupID()) {
                                group.getComposition().add(newStudent);
                            }
                        }

						this.userList.add(newStudent);

					} else {
						System.out.println("Student attribute missing");
					}

				}

				// On charge l'ensemble des professeurs du fichier XML dans la liste d'utilisateurs

				Element teachersNode = userDBNode.getChild("Teachers");
				List teacherNodes = teachersNode.getChildren("Teacher");
				i = teacherNodes.iterator();
				while (i.hasNext()) {
					Element teacherNode = (Element) i.next();
					Teacher newTeacher = Teacher.initWithElement(teacherNode);
					if (newTeacher != null) {
						this.userList.add(newTeacher);
					} else {
						System.out.println("Teacher attribute missing");
					}

				}

				// On charge l'ensemble des administrateurs du fichier XML dans la liste d'utilisateurs

				Element adminsNode = userDBNode.getChild("Administrators");
				List adminNodes = adminsNode.getChildren("Administrator");
				i = adminNodes.iterator();
				while (i.hasNext()) {
					Element adminNode = (Element) i.next();
					Admin newAdmin = Admin.initWithElement(adminNode);
					if (newAdmin != null) {
						this.userList.add(newAdmin);
					} else {
						System.out.println("Admin attribute missing");
					}

				}




			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Si le fichier XML n'existe pas, on en crée un avec l'arborescence minimale et on y rajoute un administrateur afin de pouvoir au moins se connecter à l'interface

		} else {
			Document xmlDB = new Document();
			Element root = new Element("UsersDB");
			this.setNode(root);
			Element groupsNode = new Element("Groups");
			root.addContent(groupsNode);
			Element adminsNode = new Element("Administrators");
			root.addContent(adminsNode);
			Element studentsNode = new Element("Students");
			root.addContent(studentsNode);
			Element teachersNode = new Element("Teachers");
			root.addContent(teachersNode);
			xmlDB.setRootElement(root);

			addAdmin("su","su",42,"super","user","1234");
			this.saveDB();
		}
	}

	public void saveDB() {

		try{
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(this.getNode().getDocument(), new FileOutputStream(this.getFile()));
		}
		catch (java.io.IOException e){}
	}



	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname, String pwd) {

		List<User> AllUsers = this.getUserList();

		//On vérifie si le login n'est pas déjà utilisé par un autre utilisateur
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newAdminlogin)){
				return false;
			}
		}

		//On crée un nouvel administrateur à partir des paramètres de la fonction
		Admin newAdmin = Admin.initWithoutElement(newAdminlogin,firstname,surname,pwd,adminID, this.getNode().getChild("Administrators"));

		//On vérifie que le nouvel administrateur a bien été créé
		if (newAdmin == null){
			return false;
		} else {
			this.getUserList().add(newAdmin);
			this.saveDB();
			return true;
		}

	}


	public boolean addTeacher(String teacherLogin, String newTeacherlogin, int teacherID, String firstname, String surname, String pwd) {

		List<User> AllUsers = this.getUserList();

		//On vérifie si le login n'est pas déjà utilisé par un autre utilisateur
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newTeacherlogin)){
				return false;
			}
		}

		//On crée un nouveau professeur à partir des paramètres de la fonction
		Teacher newTeacher = Teacher.initWithoutElement(newTeacherlogin,firstname,surname,pwd,teacherID, this.getNode().getChild("Teachers"));

		//On vérifie que le nouveau professeur a bien été créé
		if (newTeacher == null){
			return false;
		} else {
			this.getUserList().add(newTeacher);
			this.saveDB();
			return true;
		}

	}


	public boolean addStudent(String studentLogin, String newStudentlogin, int studentID, String firstname, String surname, String pwd) {

		List<User> AllUsers = this.getUserList();

		//On vérifie si le login n'est pas déjà utilisé par un autre utilisateur
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newStudentlogin)){
				return false;
			}
		}

		//On crée un nouvel étudiant à partir des paramètres de la fonction
		Student newStudent = Student.initWithoutElement(newStudentlogin,firstname,surname,pwd,studentID, this.getNode().getChild("Students"));

		//On vérifie que le nouvel étudiant a bien été créé
		if (newStudent == null){
			return false;
		} else {
			this.getUserList().add(newStudent);
			this.saveDB();
			return true;
		}

	}

	public boolean addGroup(String adminLogin, int groupId) {

		List<Group> AllGroups = this.getGroupList();

		//On vérifie si l'identifiant de groupe n'est pas déjà utilisé par un autre groupe
		for(int i=0;i<AllGroups.size();i++){
			if(AllGroups.get(i).getGroupId() == groupId){
				return false;
			}
		}

		//On crée un nouveau groupe à partir des paramètres de la fonction
		Group newGroup = Group.initWithoutElement(groupId, this.getNode().getChild("Groups"));

		//On vérifie que le nouveau groupe a bien été créé
		if (newGroup == null){
			return false;
		} else {
			this.getGroupList().add(newGroup);
			this.saveDB();
			return true;
		}

	}


	public boolean removeGroup(String adminLogin, int groupId) {
		List<Group> groupsList = this.getGroupList();
		List<User> usersList = this.getUserList();

		for(int i=0; i<groupsList.size();i++){

			//On parcourt la liste des groupes pour voir si l'identifiant de groupe recherché correspond à un identifiant existant
			if(groupsList.get(i).getGroupId() == groupId){

						List<Student> studentfromGroup = groupsList.get(i).getComposition();

						//On parcourt la liste des étudiants appartenant au groupe recherché pour remettre leur identifiant de groupe à "-1" et pour les supprimer de cette liste
						for(int j=0; j<studentfromGroup.size();j++){
							if(studentfromGroup.get(j) != null) {
								studentfromGroup.get(j).getNode().getChild("groupId").setText("-1");
								studentfromGroup.remove(j);

							}

						}


				//On supprime le groupe à la fois du XML et de la liste de groupes
				this.getNode().getChild("Groups").removeContent(groupsList.get(i).getNode());
				groupsList.remove(i);
				this.saveDB();
				return true;
			}
		}
		return false;
	}


	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId){
		List<User> usersList = this.getUserList();

		//On parcourt la liste des utilisateurs

		for(int i=0; i<usersList.size();i++){

			//On vérifie que le login que l'on veut lier correspond bien à un login existant et que l'utilisateur ayant ce login est bien un étudiant

			if(usersList.get(i).getLogin().equals(studentLogin)){

				if(usersList.get(i).getClass().equals(Student.class)) {

					List<Group> groupsList = this.getGroupList();

					//On recherche dans la liste de groupes si il y en a un dont l'identifiant correspond bien à celui que l'on souhaite lier
					for (int j = 0; j < groupsList.size(); j++) {
						if (groupsList.get(j).getGroupId() == (groupId)) {

							//On récupère la liste des étudiants qui compose le groupe recherché
							List<Student> composition = groupsList.get(j).getComposition();

							//On rajoute l'étudiant recherché à la liste d'étudiants qui compose le groupe recherché
							composition.add((Student)usersList.get(i));

							//On incrémente le nombre d'étudiants appartenant au groupe recherché
							groupsList.get(j).setStudentNumber(groupsList.get(j).getStudentNumber()+1);

							// On accède au node du student et modifie son identifiant de groupe par le nouveau
							usersList.get(i).getNode().getChild("groupId").setText(Integer.toString(groupId));
							this.saveDB();
							return true;


						}
					}
				}


			}

		}
		return false;
	}


	public boolean removeUser(String adminLogin, String userLogin) {
		List<User> usersList = this.getUserList();

		for(int i=0; i<usersList.size();i++){

			//On vérifie que le login de l'utilisateur que l'on souhaite supprimer correspond bien à un login existant
			if(usersList.get(i).getLogin().equals(userLogin)){

				//On vérifie si l'utilisateur est un étudiant afin de le supprimer de son groupe et de le retrouver dans le XML
				if(usersList.get(i).getClass().equals(Student.class)){
					List<Group> groupsList = this.getGroupList();
					for(int j=0; j<groupsList.size();j++){
						//On récupère la liste d'étudiants de chaque groupe existant
						List<Student> composition = groupsList.get(j).getComposition();
						for(int k=0; k<composition.size(); k++){
							//On regarde si le login recherché correspond à l'un des étudiants d'un des groupes
							if(composition.get(k).getLogin().equals(userLogin)){
								//On supprime l'étudiant de la liste de son groupe et on décrémente le nombre d'étudiants de ce groupe
								composition.remove(k);
								groupsList.get(j).setStudentNumber(groupsList.get(j).getStudentNumber()-1);
							}

						}
					}
					//On supprime l'étudiant du XML
					this.getNode().getChild("Students").removeContent(usersList.get(i).getNode());


				//On vérifie si l'utilisateur est un professeur afin de le retrouver dans le XML
				}else if(usersList.get(i).getClass().equals(Teacher.class)){
					//On supprime le professeur du XML
					this.getNode().getChild("Teachers").removeContent(usersList.get(i).getNode());

				//On vérifie si l'utilisateur est un administrateur afin de le retrouver dans le XML
				}else if(usersList.get(i).getClass().equals(Admin.class)){
					//On supprime l'administrateur du XML
					this.getNode().getChild("Administrators").removeContent(usersList.get(i).getNode());
				}
				//On supprime l'utilisateur de la liste d'utilisateurs
				usersList.remove(i);
				this.saveDB();
				return true;
			}
		}
		return false;
	}



}

