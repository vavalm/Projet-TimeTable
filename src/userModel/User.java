package userModel;

import node.Node;
import org.jdom2.Element;

public class User extends Node {

	/**
	 * Contient un login, un nom, un prénom et un mot de passe
	 *
	 */

	private String login;
	private String firstname;
	private String surname;
	private String pwd;

	/**
	 * Constructeur d'un utilisateur
	 *
	 * @param login
	 * 		Login de l'utilisateur
	 * @param firstname
	 * 		Prénom de l'utilisateur
	 * @param surname
	 * 		Nom de l'utilisateur
	 * @param pwd
	 * 		Mot de passe de l'utilisateur
	 * @param userNode
	 * 		Element noeud associé à l'utilisateur
	 *
	 */
	
	public User (String login, String firstname, String surname, String pwd, Element userNode ){
		super(userNode);
		setLogin(login);
		setFirstname(firstname);
		setSurname(surname);
		setPassword(pwd);
	}

	/**
	 * Setter du login de l'utilisateur modifiant l'Element noeud correspondant
	 * @param login
	 * 		Nouveau login de l'utilisateur
	 */
	
	public void setLogin(String login){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("login").setText(login);
			} catch (Exception e) {
				System.out.println("login field not found");
			}
		}
		this.login = login;
	}

	/**
	 * Getter du login de l'utilisateur
	 * @return
	 * 		Login de l'utilisateur
	 */
	
	public String getLogin(){
		return this.login;
	}

	/**
	 * Setter du prénom de l'utilisateur modifiant l'Element noeud correspondant
	 * @param firstname
	 * 		Nouveau prénom de l'utilisateur
	 */

	public void setFirstname(String firstname){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("firstname").setText(firstname);
			} catch (Exception e) {
				System.out.println("firstname field not found");
			}
		}
		this.firstname = firstname;
	}

	/**
	 * Getter du prénom de l'utilisateur
	 * @return
	 * 		Prénom de l'utilisateur
	 */
	
	public String getFirstname(){
		return this.firstname;
	}

	/**
	 * Setter du nom de l'utilisateur modifiant l'Element noeud correspondant
	 * @param surname
	 * 		Nouveau nom de l'utilisateur
	 */

	public void setSurname(String surname){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("surname").setText(surname);
			} catch (Exception e) {
				System.out.println("surname field not found");
			}
		}
		this.surname = surname;
	}

	/**
	 * Getter du nom de l'utilisateur
	 * @return
	 * 		Nom de l'utilisateur
	 */
	
	public String getSurname(){
		return this.surname;
	}

	/**
	 * Setter du mot de passe de l'utilisateur modifiant l'Element noeud correspondant
	 * @param pwd
	 * 		Nouveau mot de passe de l'utilisateur
	 */


	public void setPassword(String pwd){
		if (this.getNode() != null) {
			try {
				this.getNode().getChild("pwd").setText(pwd);
			} catch (Exception e) {
				System.out.println("pwd field not found");
			}
		}
		this.pwd = pwd;
	}

	/**
	 * Getter du mot de passe de l'utilisateur
	 * @return
	 * 		Mot de passe de l'utilisateur
	 */
	
	public String getPassword(){
		return this.pwd;
	}
}
