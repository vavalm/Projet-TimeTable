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

//TODO Classe à modifier

public class UserDB extends Node{

	/**
	 * Le fichier contenant la base de données.
	 */
	private String file;

	/*private List<Student> studentList = new ArrayList<Student>();
	private List<Teacher> teacherList = new ArrayList<Teacher>();
	private List<Admin> adminList = new ArrayList<Admin>();*/

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	private List<User> userList = new ArrayList<User>();
	private List<Group> groupList = new ArrayList<Group>();
	private Document parserDB;
	private Element userDBNode;


	/**
	 * Constructeur de UserDB.
	 * <p>
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
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


	public void loadDB() {

		SAXBuilder sxb = new SAXBuilder();
		File xmlFile = new File(this.file);

		if(xmlFile.exists()) {
			try {
				Document parserDB;
				parserDB = sxb.build(xmlFile);
				userDBNode = parserDB.getRootElement();
				this.setNode(userDBNode);

				// Get Students
				Element studentsNode = userDBNode.getChild("Students");
				List studentNodes = studentsNode.getChildren("Student");
				Iterator i = studentNodes.iterator();
				while (i.hasNext()) {
					Element studentNode = (Element) i.next();

					Student newStudent = Student.initWithElement(studentNode);

					if (newStudent != null) {
						this.userList.add(newStudent);

					} else {
						System.out.println("Student attribute missing");
					}

				}


				// Get Teachers

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

				// Get Administrators

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

				// Get Groups
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


			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

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
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newAdminlogin)){
				return false;
			}
		}

		Admin newAdmin = Admin.initWithoutElement(newAdminlogin,firstname,surname,pwd,adminID, this.getNode().getChild("Administrators"));

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
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newTeacherlogin)){
				return false;
			}
		}

		Teacher newTeacher = Teacher.initWithoutElement(newTeacherlogin,firstname,surname,pwd,teacherID, this.getNode().getChild("Teachers"));

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
		for(int i=0;i<AllUsers.size();i++){
			if(AllUsers.get(i).getLogin().equals(newStudentlogin)){
				System.out.println("ET BAH NON Cet ELV EXISTE DEJA");
				return false;
			}
		}

		Student newStudent = Student.initWithoutElement(newStudentlogin,firstname,surname,pwd,studentID, this.getNode().getChild("Students"));

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
		for(int i=0;i<AllGroups.size();i++){
			if(AllGroups.get(i).getGroupId() == groupId){
				return false;
			}
		}


		Group newGroup = Group.initWithoutElement(groupId, this.getNode().getChild("Groups"));

		if (newGroup == null){
			return false;
		} else {
			this.getGroupList().add(newGroup);
			this.saveDB();
			return true;
		}

	}


	public boolean removeGroup(String adminLogin, int groupId) {
		// TODO Auto-generated method stub
		List<Group> groupsList = this.getGroupList();
		List<User> usersList = this.getUserList();

		for(int i=0; i<groupsList.size();i++){
			if(groupsList.get(i).getGroupId() == groupId){

					//tous les étudiants qui étaient liés au groupe doivent avoir un groupId à -1
						List<Student> studentfromGroup = groupsList.get(i).getComposition();

						for(int j=0; j<studentfromGroup.size();j++){
							if(studentfromGroup.get(j) != null) {
								studentfromGroup.get(j).getNode().getChild("groupId").setText("-1");
								studentfromGroup.remove(j);

							}

						}



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


		for(int i=0; i<usersList.size();i++){

			if(usersList.get(i).getLogin().equals(studentLogin)){

				if(usersList.get(i).getClass().equals(Student.class)) {

					List<Group> groupsList = this.getGroupList();

					for (int j = 0; j < groupsList.size(); j++) {
						if (groupsList.get(j).getGroupId() == (groupId)) {

							List<Student> composition = groupsList.get(j).getComposition();

							composition.add((Student)usersList.get(i));
							groupsList.get(j).setStudentNumber(groupsList.get(j).getStudentNumber()+1);
							// Accède au node du student et modifie le champ groupId avec le nouveau
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
		// TODO Auto-generated method stub
		List<User> usersList = this.getUserList();

		for(int i=0; i<usersList.size();i++){
			if(usersList.get(i).getLogin().equals(userLogin)){

				// Si suppression d'un Student, supprimer de son groupe
				if(usersList.get(i).getClass().equals(Student.class)){
					List<Group> groupsList = this.getGroupList();
					for(int j=0; j<groupsList.size();j++){
						List<Student> composition = groupsList.get(j).getComposition();
						for(int k=0; k<composition.size(); k++){
							if(composition.get(k).getLogin().equals(userLogin)){
								composition.remove(k);
								groupsList.get(j).setStudentNumber(groupsList.get(j).getStudentNumber()-1);
							}

						}
					}

					this.getNode().getChild("Students").removeContent(usersList.get(i).getNode());



				}else if(usersList.get(i).getClass().equals(Teacher.class)){

					this.getNode().getChild("Teachers").removeContent(usersList.get(i).getNode());

				}else if(usersList.get(i).getClass().equals(Admin.class)){

					this.getNode().getChild("Administrators").removeContent(usersList.get(i).getNode());
				}
				usersList.remove(i);
				this.saveDB();
				return true;
			}
		}
		return false;
	}



}

