package userController;

import userModel.*;

import java.util.Iterator;
import java.util.List;


/**
 * Cette classe est le contrôleur d'utilisateurs que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données utilisateurs que vous allez créer.
 * Elle contient toutes les fonctions de l'interface IUserController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

public class UserController implements IUserController
{
	
	/**
	 * Contient une instance de base de données d'utilisateurs
	 * 
	 */
	private UserDB userDB=null;
	
	
	/**
	 * Constructeur de controleur d'utilisateurs créant la base de données d'utilisateurs
	 * 
	 * @param userfile
	 * 		Fichier XML contenant la base de données d'utilisateurs
	 */
	public UserController(String userfile){
		UserDB userDB=new UserDB(userfile);
		this.setUserDB(userDB);
	}

	@Override
	public String getUserName(String userLogin) {

		List<User> userList = this.userDB.getUserList();

		for(int i=0; i<userList.size(); i++){

			if (userList.get(i).getLogin().equals(userLogin)){

				return userList.get(i).getFirstname().concat(" ").concat(userList.get(i).getSurname());
			}

		}
		return null;
	}

	@Override
	public String getUserClass(String userLogin, String userPwd) {

		List<User> userList = this.userDB.getUserList();
		String Administrator = "Administrator";
		String teacher = "Teacher";
		String student = "Student";
		String notfound = "Not found";

		for(int i=0; i<userList.size(); i++){

			if (userList.get(i).getLogin().equals(userLogin) && userList.get(i).getPassword().equals(userPwd)){
				if(userList.get(i).getClass().equals(Admin.class)){
					return Administrator;
				}

				if(userList.get(i).getClass().equals(Student.class)){
					return student;
				}

				if(userList.get(i).getClass().equals(Teacher.class)){
					return teacher;
				}


				}

			}

		return notfound;
	}

	@Override
	public int getStudentGroup(String studentLogin) {
		List<Group> groupList = this.userDB.getGroupList();

		for(int i=0; i<groupList.size(); i++){
			List<Student> studentfromGroup = groupList.get(i).getComposition();

			for(int j=0; j<studentfromGroup.size();j++){
				if(studentfromGroup.get(j).getLogin().equals(studentLogin)){
					return groupList.get(i).getGroupId();
				}
			}

		}

		return -1;
	}

	@Override
	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname,
			String pwd) {
		return this.userDB.addAdmin(adminLogin,newAdminlogin,adminID,firstname,surname,pwd);
	}

	@Override
	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) {
		return this.userDB.addTeacher(adminLogin,newteacherLogin,teacherID,firstname,surname,pwd);
	}

	@Override
	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) {
		return this.userDB.addStudent(adminLogin,newStudentLogin,studentID,firstname,surname,pwd);
	}

	@Override
	public boolean removeUser(String adminLogin, String userLogin) {
		return this.userDB.removeUser(adminLogin,userLogin);
	}

	@Override
	public boolean addGroup(String adminLogin, int groupId) {
		return this.userDB.addGroup(adminLogin,groupId);
	}

	@Override
	public boolean removeGroup(String adminLogin, int groupId) {
		return this.userDB.removeGroup(adminLogin,groupId);
	}

	@Override
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) {
		return this.userDB.associateStudToGroup(adminLogin, studentLogin, groupId);
	}

	@Override
	public String[] usersToString() {
		List<User> usersList = this.userDB.getUserList();
		String users[] = new String[usersList.size()];

		for(int i = 0; i<usersList.size(); i++){
			if(usersList.get(i).getClass().equals(Student.class)){
				users[i] ="Student | Login: ";
			}else if(usersList.get(i).getClass().equals(Teacher.class)){
				users[i] = "Teacher | Login: ";
			}else if(usersList.get(i).getClass().equals(Admin.class)){
				users[i] = "Admin | Login: ";
			}

			users[i] += usersList.get(i).getLogin();
			users[i] += " | Firstname: " + usersList.get(i).getFirstname();
			users[i] += " | Surname: " + usersList.get(i).getSurname();
			users[i] += " | Password: " + usersList.get(i).getPassword();

			if(usersList.get(i).getClass().equals(Student.class)){
				users[i] += " | Group:" + getStudentGroup(usersList.get(i).getLogin());
			}
		}

		return users;
	}

	@Override
	public String[] usersLoginToString() {
		List<User> usersList = this.userDB.getUserList();
		String usersLogin[] = new String[usersList.size()];

		for(int i = 0; i<usersList.size(); i++){
			usersLogin[i] = usersList.get(i).getLogin();
		}

		return usersLogin;
	}

	@Override
	public String[] studentsLoginToString() {
		List<User> usersList = this.userDB.getUserList();
		int nbStudent = 0;
		int i;

		for(i = 0;i<usersList.size(); i++){
			if(usersList.get(i).getClass().equals(Student.class)){
				nbStudent++;
			}
		}

		String studentsLogin[] = new String[nbStudent];
		nbStudent = 0;

		for(i = 0; i<usersList.size(); i++){
			if(usersList.get(i).getClass().equals(Student.class)){
				studentsLogin[nbStudent] = usersList.get(i).getLogin();
				nbStudent++;
			}
		}

		return studentsLogin;
	}

	@Override
	public String[] groupsIdToString() {
		List<Group> groupsList = this.userDB.getGroupList();
		String groupsId[] = new String[groupsList.size()];

		for(int i = 0; i<groupsList.size(); i++){
			groupsId[i] = Integer.toString(groupsList.get(i).getGroupId());
		}

		return groupsId;
	}

	@Override
	public String[] groupsToString() {
		List<Group> groupsList = this.userDB.getGroupList();
		String groups[] = new String[groupsList.size()];

		for(int i = 0; i<groupsList.size(); i++){
			groups[i] = "GroupId: " + groupsList.get(i).getGroupId();
			groups[i] += " | NumberOfStudents: " + groupsList.get(i).getStudentNumber();
		}

		return groups;
	}

	@Override
	public boolean loadDB() {
		userDB.loadDB();
		return false;
	}

	@Override
	public boolean saveDB() {
		userDB.saveDB();
		return false;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}
	
	

}

