package userModel;

import userController.UserController;
/**
 * Cette classe permet de tester les fonctions du contrôleur d'utilisateurs.
 * Elle crée une base de données de 6 utilisateurs et les sauvegarde dans le fichier "usersDB.xml". 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe pouvant être modifiée suivant vos besoins

public class Main {
	/**
	 * Fonction principale 
	 * 
	 * @param args
	 * 			Les arguments donnés en entrée du programme.
	 * 
	 */
	public static void main(String[] args) {
		final String file="usersDB.xml";
		UserController UC=new UserController(file);
		System.out.println(UC.getUserClass("MF","iknowall"));
		System.out.println(UC.getUserName("KR"));
		//UC.addAdmin("su","MC",0001,"MOMO", "BOUBOU",  "@tron");
		UC.addAdmin("su","KR",0002,"Keanu", "Reeves",  "redpill");
		UC.associateStudToGroup("su","BC",1);
		System.out.println(UC.getStudentGroup("BC"));
		UC.addGroup("su",2);
		UC.removeGroup("su",2);
		/*UC.addTeacher("su","GS",1001,"Grand", "Schtroumpf",  "salsepareille");
		UC.addTeacher("su","MF",1002,"Morgan", "Freeman",  "iknowall");
		UC.addTeacher("su","MB",1002,"MOHAMED", "BOUAZAOUI",  "iknowall");
		UC.addStudent("su","BC",2001,"BAPTISTE", "CAMOMILLIE",  "stake");
		UC.removeUser("su","KR");*/

		UC.saveDB();
	}
}
