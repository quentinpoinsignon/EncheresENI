package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * @author jarrigon2020
 * 
 * @Commentaire Classe impl�mentant l'interface UtilisateurDAO et contenant les
 *              diff�rentes m�thodes permettant de g�rer les requ�tes SQL li�es
 *              aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	/**
	 * D�claration des variables de classes
	 * 
	 * @Constante USER_INSERT -> String contenant la requ�te SQL permettant
	 *            d'enregistrer un nouvel utilisateur dans la base de donn�es
	 * 
	 **/
	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";

	/**
	 * @Constante VERIF_EMAIL_DATABASE -> String contenant la requ�te SQL permettant
	 *            de v�rifier un utilisateur est enregistr� dans la base de donn�es
	 *            � l'aide de son mail et de son mot de passe
	 **/
	private final String VERIF_EMAIL_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ? AND mot_de_passe = ?";;
	/**
	 * @Constante VERIF_PSEUDO_DATABASE -> String contenant la requ�te SQL
	 *            permettant de v�rifier un utilisateur est enregistr� dans la base
	 *            de donn�es � l'aide de son pseudo et de son mot de passe
	 */
	private final String VERIF_PSEUDO_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where pseudo= ? AND mot_de_passe = ?";
	/**
	 * @Constante USER_PROFIL_REQUEST_BY_PSEUDO -> String contenant la requ�te sql
	 *            permettant de rechercher un utilisateur gr�ce � son pseudo et
	 *            d'afficher son profil
	 */
	private final String USER_PROFIL_REQUEST_BY_PSEUDO = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE pseudo = ?";
	/**
	 * @Constante EDIT_USER_PROFIL -> String contenant la requ�te sql permettant de
	 *            modifier les informations d'un utilisateur dans la base de
	 *            donn�es. On s�lectionne l'utilisateur gr�ce � l'id utilisateur
	 */
	private final String EDIT_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET pseudo = '? ' , nom  = '?', prenom = '?', email = '?',telephone = '?',rue='?', code_postal='?', ville='?', mot_de_passe='?'\r\n"
			+ "WHERE no_utilisateur =  ?";

	/**
	 * @Constante USER_PROFIL_REQUEST_BY_ID -> String contenant la requ�te SQL
	 *            permettant de r�cup�rer les informations d'un utilisateur gr�ce �
	 *            son identifiant
	 */
	private final String USER_PROFIL_REQUEST_BY_ID = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE no_utilisateur = ?";

	/**
	 * @Constante REMOVE_USER_PROFIL -> Chaine de caract�re contenant une requ�te
	 *            SQL permettant de modifier les informations d'un utilisateur afin
	 *            de supprimer toutes ses informations personelles en les rempla�ant
	 *            par des valeurs par d�faut.
	 * 
	 */
	private final String REMOVE_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET nom  = 'Utilisateur d�sinscrit', prenom = 'Utilisateur d�sinscrit', email = 'XXXX@email.com',telephone = 'XXXXXXXXXX',rue='XXXXXXX',code_postal='XXXXX' ,ville='XXXXX', mot_de_passe='MNy5jH3we6SjA44UeJ7A68vn5DcrD2'\r\n"
			+ "WHERE no_utilisateur =  ?";
	/**
	 * @Constante SELECT_ALL_PSEUDO -> Chaine de charact�res contenant une requ�te
	 *            sql permettant de r�cup�rer l'ensemble des pseudos enregistr�s
	 *            dans la base de donn�es
	 */

	private final String SELECT_ALL_PSEUDO = "SELECT pseudo FROM UTILISATEURS\r\n" + "ORDER BY pseudo";

	/**
	 * @Constante SELECT_ALL_EMAIL -> Chaine de caract�res contenant une requ�te SQL
	 *            permettant de r�cup�rer l'ensemble des emails enregistr�s dans la
	 *            base de donn�es
	 */
	private final String SELECT_ALL_EMAIL = "SELECT email FROM UTILISATEURS\\r\\n\" + \"ORDER BY email";

	/**
	 * @author jarrigon2020
	 * @param email    -> Chaine de caract�re qui correspond � l'email fourni par
	 *                 l'utilisateur
	 * @param password -> Chaine de caract�re correspondant au mot de passe fourni
	 *                 par l'utilisateur
	 * @return user -> Objet de type Utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette m�thode permet de v�rifier si l'email et le mot de passe
	 *              fourni par l'utilisateur sont bien pr�sents dans la base de
	 *              donn�es et correspondent entre eux.
	 */
	@Override
	public Utilisateur userConnection(String email, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_EMAIL_DATABASE);) {

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {
				int userId = MyResultset.getInt(1);
				String pseudo = MyResultset.getString(2);
				String nom = MyResultset.getString(3);
				String prenom = MyResultset.getString(4);
				String userEmail = MyResultset.getString(5);
				String telephone = MyResultset.getString(6);
				String rue = MyResultset.getString(7);
				String codePostal = MyResultset.getString(8);
				String ville = MyResultset.getString(9);
				String motDePasse = MyResultset.getString(10);

				user = new Utilisateur(userId, pseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
						motDePasse);

			}

		} catch (SQLException e) {

			throw new Exception("Erreur lors la tentative de connexion");

		}

		return user;

	}

	/**
	 * @author jarrigon2020
	 * @param pseudo   -> Chaine de caract�re qui correspond au pseudo fourni par
	 *                 l'utilisateur
	 * @param password -> Chaine de caract�re correspondant au mot de passe fourni
	 *                 par l'utilisateur
	 * @return user -> Objet de type Utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette m�thode permet de v�rifier si le pseudo et le mot de passe
	 *              fourni par l'utilisateur sont bien pr�sents dans la base de
	 *              donn�es et correspondent entre eux.
	 */

	@Override
	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_PSEUDO_DATABASE);) {

			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, password);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {
				int userId = MyResultset.getInt(1);
				String userPseudo = MyResultset.getString(1);
				String nom = MyResultset.getString(2);
				String prenom = MyResultset.getString(3);
				String userEmail = MyResultset.getString(4);
				String telephone = MyResultset.getString(5);
				String rue = MyResultset.getString(6);
				String codePostal = MyResultset.getString(7);
				String ville = MyResultset.getString(8);
				String motDePasse = MyResultset.getString(9);

				user = new Utilisateur(userId, userPseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
						motDePasse);

			}

			MyResultset.close();

		} catch (SQLException e) {

			throw new Exception("Erreur lors de la tentative de connexion");

		}

		return user;

	}

	/**
	 * @author jarrigon2020
	 * @param user -> Objet de type Utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette fonction permet d'enregistrer un nouvel utilisateur dans
	 *              la base de donn�es
	 */
	@Override
	public void userInsert(Utilisateur user) throws Exception {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_INSERT,
						Statement.RETURN_GENERATED_KEYS)) {

			preparedStatement.setString(1, user.getPseudo());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getTelephone());
			preparedStatement.setString(6, user.getRue());
			preparedStatement.setString(7, user.getCodePostal());
			preparedStatement.setString(8, user.getVille());
			preparedStatement.setString(9, user.getMotDePasse());

			// preparedStatement.getGeneratedKeys();

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new Exception("Erreur lors de l'ajout de l'utilisateur");

		}

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param pseudo -> Chaine de caract�re correspondant au pseudo fourni
	 * 
	 * @return userProfil -> Objet de type Utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire
	 * 
	 *              Permet de rechercher le profil d'un utilisateur � partir de son
	 *              pseudo et d'afficher l'ensemble de ses informations
	 */
	@Override
	public Utilisateur showUserProfil(String pseudo) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur userProfil = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection
						.prepareStatement(USER_PROFIL_REQUEST_BY_PSEUDO);) {

			preparedStatement.setString(1, pseudo);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {

				String userPseudo = MyResultset.getString(1);
				String nom = MyResultset.getString(2);
				String prenom = MyResultset.getString(3);
				String userEmail = MyResultset.getString(4);
				String telephone = MyResultset.getString(5);
				String rue = MyResultset.getString(6);
				String codePostal = MyResultset.getString(7);
				String ville = MyResultset.getString(8);

				userProfil = new Utilisateur(userPseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville);

			}
			MyResultset.close();

		} catch (SQLException e) {

			throw new Exception("L'utilisateur demand� n'existe pas");

		}

		return userProfil;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param pseudo          -> Chaine de caract�re correspondant au pseudo de
	 *                        l'utilisateur
	 * @param name            -> Chaine de caract�re correspondant au nom de
	 *                        l'utilisateur
	 * @param firstName       -> Chaine de caract�re correspondant au pr�nom de
	 *                        l'utilisateur
	 * @param email           -> Chaine de caract�re correspondant � l'email de
	 *                        l'utilisateur
	 * @param telephoneNumber -> Chaine de caract�re correspondant au num�ro de
	 *                        t�l�phone de l'utilisateur
	 * @param street          -> Chaine de caract�re correspondant � la rue de
	 *                        l'utilisateur
	 * @param postalCode      -> Chaine de caract�re correspondant au code postal de
	 *                        l'utilisateur
	 * @param password        -> Chaine de caract�re correspondant au mot de passe
	 *                        de l'utilisateur
	 * @param idUser          ->int correspondant � l'indentifiant de l'utilisateur
	 *                        dans la base de donn�es. Il permet de cibler
	 *                        pr�cis�ment l'utilisateur dont on souhaite modifier
	 *                        les donn�es
	 * @throws SQLException
	 * 
	 * @commentaire
	 * 
	 *              Cette m�thode permet de modifier les informations de
	 *              l'utilisateur dans la base de donn�es
	 */
	@Override
	public void editUserProfil(String pseudo, String name, String firstName, String email, String telephoneNumber,
			String street, String postalCode, String password, int idUser) throws SQLException {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(EDIT_USER_PROFIL);) {

			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, email);
			preparedStatement.setString(5, telephoneNumber);
			preparedStatement.setString(6, street);
			preparedStatement.setString(7, postalCode);
			preparedStatement.setString(8, password);
			preparedStatement.setInt(9, idUser);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new SQLException(
					"Probl�me lors de l'enregistrement des informations de l'utilisateur dans la base de donn�es");
		}

	}

	/**
	 * @author jarrigon2020
	 * @param idUser -> int Correspond � l'identifiant de l'utilisateur dans la base
	 *               de donn�es
	 * 
	 * @return userInformation -> Objet de type utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire Cette m�thode permet de r�cup�rer les informations d'un
	 *              utilisateur gr�ce � son identifiant
	 * 
	 */
	@Override
	public Utilisateur getUserInformation(int idUser) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur userInformation = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_PROFIL_REQUEST_BY_ID);) {

			preparedStatement.setInt(1, idUser);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {

				String userPseudo = MyResultset.getString(1);
				String nom = MyResultset.getString(2);
				String prenom = MyResultset.getString(3);
				String userEmail = MyResultset.getString(4);
				String telephone = MyResultset.getString(5);
				String rue = MyResultset.getString(6);
				String codePostal = MyResultset.getString(7);
				String ville = MyResultset.getString(8);
				String motDePasse = MyResultset.getString(9);

				userInformation = new Utilisateur(userPseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
						motDePasse);

			}
			MyResultset.close();
		} catch (SQLException e) {

			throw new Exception("Probl�me lors de la r�cup�ration des informations de l'utilisateur");

		}

		return userInformation;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param idUser -> Int correspondant � l'identifiant de l'utilisateur dans la
	 *               base de donn�es
	 * 
	 * @Commentaire Cette m�thode permet de supprimer les donn�es personnelles d'un
	 *              utilisateur dans la base de donn�es en les rempla�ant par des
	 *              valeurs par defaut
	 */
	@Override
	public void removeUserProfil(int idUser) throws Exception {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(REMOVE_USER_PROFIL)) {

			preparedStatement.setInt(1, idUser);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new Exception("Erreur lors de l'ajout de l'utilisateur");

		}

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @return listPseudo -> Objet de type ArrayList contenant des chaines de
	 *         caract�res contenant l'ensemble des pseudo enregistr�s sur le site
	 * 
	 * @Commentaire Cette m�thode permet de r�cup�rer l'ensemble des pseudos
	 *              enregistr�s dans la base de donn�es
	 */
	@Override
	public List<String> selectAllPseudo() throws Exception {

		List<String> listPseudo = new ArrayList<String>();
		String pseudo = null;
		ResultSet MyResultset = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SELECT_ALL_PSEUDO)) {

			MyResultset = preparedStatement.executeQuery();

			while (MyResultset.next()) {

				pseudo = MyResultset.getString(1);

				listPseudo.add(pseudo);

			}

			MyResultset.close();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return listPseudo;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @return listEmail -> Objet de type ArrayList contenant des chaines de
	 *         caract�res contenant l'ensemble des emails enregistr�s sur le site
	 * 
	 * @Commentaire Cette m�thode permet de r�cup�rer l'ensemble des emails
	 *              enregistr�s dans la base de donn�es
	 */
	@Override
	public List<String> selectAllEmail() throws Exception {

		List<String> listEmail = new ArrayList<String>();
		String email = null;
		ResultSet MyResultset = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(SELECT_ALL_EMAIL)) {

			MyResultset = preparedStatement.executeQuery();

			while (MyResultset.next()) {

				email = MyResultset.getString(1);

				listEmail.add(email);

			}

			MyResultset.close();

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		return listEmail;

	}

}
