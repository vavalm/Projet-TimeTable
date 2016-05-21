package userModel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.internal.SystemProperty;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
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

public class UserDB {

	/**
	 * Le fichier contenant la base de données.
	 */
	private String file;

	private List<Student> studentList = new ArrayList<Student>();
	private List<Teacher> teacherList = new ArrayList<Teacher>();
	private List<Admin> adminList = new ArrayList<Admin>();


	/**
	 * Constructeur de UserDB.
	 * <p>
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 *
	 * @param file Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String file) {
		//TODO Fonction à modifier
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
		Document parserDB;
		SAXBuilder sxb = new SAXBuilder();
		Element userDBNode;

		try {
			parserDB = sxb.build(new File(this.file));
			userDBNode = parserDB.getRootElement();


			// Get Groups

			// Get Students
			Element studentsNode = userDBNode.getChild("Students");
			List studentNodes = studentsNode.getChildren("Student");
			Iterator i = studentNodes.iterator();
			while (i.hasNext()) {
				Element studentNode = (Element) i.next();

				Student newStudent = Student.initWithElement(studentNode);

				if (newStudent != null) {
					this.studentList.add(newStudent);

				} else {
					System.out.println("Student attribute missing");
				}

			}


			// Get Teachers

			Element teachersNode = userDBNode.getChild("Teachers");
			List teacherNodes = teachersNode.getChildren("Teacher");
			i = teacherNodes.iterator();
			while (i.hasNext()){
				Element teacherNode = (Element) i.next();
				Teacher newTeacher = Teacher.initWithElement(teacherNode);
				if (newTeacher != null){
					this.teacherList.add(newTeacher);
				} else {
					System.out.println("Teacher attribute missing");
				}

			}




			// Get Administrators

			Element adminsNode = userDBNode.getChild("Administrators");
			List adminNodes = adminsNode.getChildren("Administrator");
			i = adminNodes.iterator();
			while (i.hasNext()){
				Element adminNode = (Element) i.next();
				Admin newAdmin = Admin.initWithElement(adminNode);
				if (newAdmin != null){
					this.adminList.add(newAdmin);
				} else {
					System.out.println("Admin attribute missing");
				}

			}



			/*for(int j=0; j<adminList.size();j++){
				Admin admin = adminList.get(j);
				System.out.println(admin.getFirstname());
			}*/


		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}

