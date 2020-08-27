package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

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
	 * @value "INSERT INTO
	 *        UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe)
	 *        VALUES(?,?,?,?,?,?,?,?,?)";
	 * 
	 **/
	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";

	/**
	 * @Constante VERIF_EMAIL_DATABASE -> String contenant la requ�te SQL permettant
	 *            de v�rifier un utilisateur est enregistr� dans la base de donn�es
	 *            � l'aide de son mail et de son mot de passe
	 * 
	 * @value "SELECT no_utilisateur,
	 *        pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur
	 *        FROM UTILISATEURS where email= ?";
	 **/
	private final String VERIF_EMAIL_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ?";
	/**
	 * @Constante VERIF_PSEUDO_DATABASE -> String contenant la requ�te SQL
	 *            permettant de v�rifier un utilisateur est enregistr� dans la base
	 *            de donn�es � l'aide de son pseudo et de son mot de passe
	 * 
	 * @value "SELECT no_utilisateur,
	 *        pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur
	 *        FROM UTILISATEURS where pseudo= ?";
	 */
	private final String VERIF_PSEUDO_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where pseudo= ?";
	/**
	 * @Constante USER_PROFIL_REQUEST_BY_PSEUDO -> String contenant la requ�te sql
	 *            permettant de rechercher un utilisateur gr�ce � son pseudo et
	 *            d'afficher son profil
	 * 
	 * @value "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM
	 *        UTILISATEURS WHERE pseudo = ?";
	 */
	private final String USER_PROFIL_REQUEST_BY_PSEUDO = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE pseudo = ?";
	/**
	 * @Constante EDIT_USER_PROFIL -> String contenant la requ�te sql permettant de
	 *            modifier les informations d'un utilisateur dans la base de
	 *            donn�es. On s�lectionne l'utilisateur gr�ce � l'id utilisateur
	 * 
	 * @value "UPDATE UTILISATEURS\r\n" + "SET pseudo = ? , nom = ?, prenom = ?,
	 *        email = ?,telephone = ?,rue=?, code_postal=?, ville=?,
	 *        mot_de_passe=?\r\n" + "WHERE no_utilisateur = ?";
	 * 
	 */
	private final String EDIT_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET pseudo = ?  , nom  = ?, prenom = ?, email = ?,telephone = ?,rue=?, code_postal=?, ville=?, mot_de_passe=?\r\n"
			+ "WHERE no_utilisateur =  ?";

	/**
	 * @Constante USER_PROFIL_REQUEST_BY_ID -> String contenant la requ�te SQL
	 *            permettant de r�cup�rer les informations d'un utilisateur gr�ce �
	 *            son identifiant
	 * 
	 * @value "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM
	 *        UTILISATEURS WHERE no_utilisateur = ?";
	 */
	private final String USER_PROFIL_REQUEST_BY_ID = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE no_utilisateur = ?";

	/**
	 * @Constante REMOVE_USER_PROFIL -> Chaine de caract�re contenant une requ�te
	 *            SQL permettant de modifier les informations d'un utilisateur afin
	 *            de supprimer toutes ses informations personelles en les rempla�ant
	 *            par des valeurs par d�faut.
	 * 
	 * @value "UPDATE UTILISATEURS\r\n" + "SET nom = 'Utilisateur désinscrit',
	 *        prenom = 'Utilisateur désinscrit', email = 'XXXX@email.com',telephone
	 *        = 'XXXXXXXXXX',rue='XXXXXXX',code_postal='XXXXX' ,ville='XXXXX',
	 *        mot_de_passe='MNy5jH3we6SjA44UeJ7A68vn5DcrD2'\r\n" + "WHERE
	 *        no_utilisateur = ?";
	 * 
	 */
	private final String REMOVE_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET nom  = 'Utilisateur d�sinscrit', prenom = 'Utilisateur d�sinscrit', email = 'XXXX@email.com',telephone = 'XXXXXXXXXX',rue='XXXXXXX',code_postal='XXXXX' ,ville='XXXXX', mot_de_passe='MNy5jH3we6SjA44UeJ7A68vn5DcrD2'\r\n"
			+ "WHERE no_utilisateur =  ?";
	/**
	 * @Constante SELECT_ALL_PSEUDO -> Chaine de caract�res contenant une requ�te
	 *            sql permettant de r�cup�rer l'ensemble des pseudos enregistr�s
	 *            dans la base de donn�es
	 * 
	 * @value "SELECT pseudo FROM UTILISATEURS\r\n" + "ORDER BY pseudo";
	 */

	private final String SELECT_ALL_PSEUDO = "SELECT pseudo FROM UTILISATEURS\r\n" + "ORDER BY pseudo";

	/**
	 * @Constante SELECT_ALL_EMAIL -> Chaine de caractères contenant une requète SQL
	 *            permettant de récupérer l'ensemble des emails enregistr�s dans la
	 *            base de données
	 * 
	 * @value "SELECT email FROM UTILISATEURS\r\n\" + \"ORDER BY email";
	 */
	private final String SELECT_ALL_EMAIL = "SELECT email FROM UTILISATEURS\r\n\" + \"ORDER BY email";

	/**
	 * @Constante SELECT_CRYPTED_PASSWORD -> Chaine de carat�res contenant une
	 *            requ�te SQL permettant de r�cup�rer la version crypt�e d'un mot de
	 *            passe pr�sent dans la base de donn�es � partir du pseudo
	 * 
	 * @value "SELECT mot_de_passe FROM UTILISATEURS\r\n" + "WHERE pseudo = ?";
	 */
	private final String SELECT_CRYPTED_PASSWORD_BY_PSEUDO = "SELECT mot_de_passe FROM UTILISATEURS\r\n"
			+ "WHERE pseudo = ?";

	/**
	 * @Constante SELECT_CRYPTED_PASSWORD_BY_EMAIL -> Chaine de carat�res contenant
	 *            une requ�te SQL permettant de r�cup�rer la version crypt�e d'un
	 *            mot de passe pr�sent dans la base de donn�es � partir de l'email
	 * 
	 * @value "SELECT mot_de_passe FROM UTILISATEURS\r\n" + "WHERE email = ?";
	 */

	private final String SELECT_CRYPTED_PASSWORD_BY_EMAIL = "SELECT mot_de_passe FROM UTILISATEURS\r\n"
			+ "WHERE email = ?";

	/**
	 * @Constante SELECT_USER_INFORMATION_AFTER_EDIT -> Chaine de caract�re
	 *            contenant une requ�te SQL utilis�s dans la fonction
	 *            editUserInformation() permettant de r�cup�rer les informations de
	 *            l'utilisateur apr�s modifications
	 * 
	 * @value "SELECT
	 *        pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit
	 *        FROM UTILISATEURS\r\n" + "WHERE no_utilisateur = ?";
	 */
	private final String SELECT_USER_INFORMATION_AFTER_EDIT = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit FROM UTILISATEURS\r\n"
			+ "WHERE no_utilisateur = ?";

	/**
	 * @Constante UPDATE_USER_PASSWORD -> Chaine de caract�re contenant une requ�te
	 *            SQL permettant de modifier le mot de passe de l'utilisateur
	 * 
	 * @value "UPDATE UTILISATEURS\r\n" + "SET mot_de_passe = ? \r\n" + "WHERE
	 *        pseudo = ?";
	 */
	private final String UPDATE_USER_PASSWORD = "UPDATE UTILISATEURS\r\n" + "SET mot_de_passe = ? \r\n"
			+ "WHERE pseudo = ?";

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
	 *              Cette méthode permet de v�rifier si l'email et le mot de passe
	 *              fourni par l'utilisateur sont bien pr�sents dans la base de
	 *              donn�es et correspondent entre eux.
	 */
	@Override
	public Utilisateur userConnection(String email, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		// On r�cup�re la version crypt�e du mot de passe
		String hashedPassword = getUserCryptedPasswordByEmail(email);

		// On v�rifie si le mot de passe en clair correspond au mot de passe hach�
		if (BCrypt.checkpw(password, hashedPassword)) {

			try (Connection databaseConnection = JdbcTools.getConnection();
					PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_EMAIL_DATABASE);) {

				preparedStatement.setString(1, email);

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
					int userCredit = MyResultset.getInt(11);

					user = new Utilisateur(userId, pseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
							motDePasse, userCredit);

				}

			} catch (SQLException e) {

				throw new Exception("Erreur lors la tentative de connexion");

			}
		}
		return user;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param pseudo -> Chaine de caract�re contenant le pseudo � partir duquel on
	 *               souhaite r�cup�rer le mot de passe
	 * 
	 * @return hashedPassword -> Chaine de caract�re contenant un mot de passe
	 *         crypt� dans la base de donn�es
	 * @throws Exception
	 * @Commentaire Cette m�thode permet de r�cup�rer une version crypt�e d'une mot
	 *              de passe dans la base de donn�es � partir du pseudo utilisateur
	 * 
	 */

	private String getUserCryptedPasswordByPseudo(String pseudo) throws Exception {

		ResultSet MyResultset = null;
		String hashedPassword = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection
						.prepareStatement(SELECT_CRYPTED_PASSWORD_BY_PSEUDO);) {

			preparedStatement.setString(1, pseudo);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {

				hashedPassword = MyResultset.getString(1);

			}

			MyResultset.close();

		} catch (SQLException e) {

			throw new Exception("Erreur lors de la r�cup�ration du mot de passe");

		}

		return hashedPassword;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param email -> Chaine de caract�re contenant l'email � partir duquel on
	 *              souhaite r�cup�rer le mot de passe
	 * 
	 * @return hashedPassword -> Chaine de caract�re contenant un mot de passe
	 *         crypt� dans la base de donn�es
	 * @throws Exception
	 * @Commentaire Cette m�thode permet de r�cup�rer une version crypt�e d'un mot
	 *              de passe dans la base de donn�es � partir du mail de
	 *              l'utilisateur
	 * 
	 */

	private String getUserCryptedPasswordByEmail(String email) throws Exception {

		ResultSet MyResultset = null;
		String hashedPassword = null;

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection
						.prepareStatement(SELECT_CRYPTED_PASSWORD_BY_EMAIL);) {

			preparedStatement.setString(1, email);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {

				hashedPassword = MyResultset.getString(1);

			}

			MyResultset.close();

		} catch (SQLException e) {

			throw new Exception("Erreur lors de la r�cup�ration du mot de passe");

		}

		return hashedPassword;

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

		// On r�cup�re la version crypt�e du mot de passe
		String hashedPassword = getUserCryptedPasswordByPseudo(pseudo);

		// On v�rifie si le mot de passe en clair correspond au mot de passe hach�
		if (BCrypt.checkpw(password, hashedPassword)) {

			try (Connection databaseConnection = JdbcTools.getConnection();
					PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_PSEUDO_DATABASE);) {

				preparedStatement.setString(1, pseudo);

				MyResultset = preparedStatement.executeQuery();

				// BCrypt.checkpw(candidate, hashed);

				if (MyResultset.next()) {
					int userId = MyResultset.getInt(1);
					String userPseudo = MyResultset.getString(2);
					String nom = MyResultset.getString(3);
					String prenom = MyResultset.getString(4);
					String userEmail = MyResultset.getString(5);
					String telephone = MyResultset.getString(6);
					String rue = MyResultset.getString(7);
					String codePostal = MyResultset.getString(8);
					String ville = MyResultset.getString(9);
					String motDePasse = MyResultset.getString(10);
					int userCredit = MyResultset.getInt(11);

					user = new Utilisateur(userId, userPseudo, nom, prenom, userEmail, telephone, rue, codePostal,
							ville, motDePasse, userCredit);

				}

				MyResultset.close();

			} catch (SQLException e) {

				throw new Exception("Erreur lors de la tentative de connexion");

			}

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
	 *              la base de donn�es. Le mot de passe est crypt� gr�ce �
	 *              l'utilisation de la classe BCrypt
	 */
	@Override
	public void userInsert(Utilisateur user) throws Exception {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_INSERT,
						Statement.RETURN_GENERATED_KEYS)) {

			// Cryptage du mot de passe avant l'insertion dans la base de donn�es

			String passwordHash = BCrypt.hashpw(user.getMotDePasse(), BCrypt.gensalt());

			preparedStatement.setString(1, user.getPseudo());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getTelephone());
			preparedStatement.setString(6, user.getRue());
			preparedStatement.setString(7, user.getCodePostal());
			preparedStatement.setString(8, user.getVille());
			preparedStatement.setString(9, passwordHash);

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
	 * @param user -> Objet de type Utilisateur. Il permet de r�cup�rer les
	 *             informations
	 * 
	 * @return userEdit -> Objet de type Utilisateur
	 * 
	 * @throws SQLException
	 * 
	 * @commentaire
	 * 
	 *              Cette m�thode permet de modifier les informations de
	 *              l'utilisateur dans la base de donn�es et de r�cup�rer un nouvel
	 *              objet de type Utilisateur avec les informations misent � jour
	 */
	@Override
	public Utilisateur editUserProfil(Utilisateur user) throws SQLException {

		Utilisateur userEdit = null;
		ResultSet myResultSet = null;
		int idUser = user.getNoUtilisateur();

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(EDIT_USER_PROFIL);) {

			preparedStatement.setString(1, user.getPseudo());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getTelephone());
			preparedStatement.setString(6, user.getRue());
			preparedStatement.setString(7, user.getCodePostal());
			preparedStatement.setString(8, user.getVille());
			preparedStatement.setString(9, user.getMotDePasse());
			preparedStatement.setInt(10, idUser);

			preparedStatement.executeUpdate();

			// Il faut r�cup�rer les infos misent � jour dans la base de donn�es

			PreparedStatement newPreparedStatement = databaseConnection
					.prepareStatement(SELECT_USER_INFORMATION_AFTER_EDIT);

			newPreparedStatement.setInt(1, idUser);

			myResultSet = newPreparedStatement.executeQuery();

			while (myResultSet.next()) {

				String pseudo = myResultSet.getString(1);
				String name = myResultSet.getString(2);
				String firstName = myResultSet.getString(3);
				String email = myResultSet.getString(4);
				String telephone = myResultSet.getString(5);
				String street = myResultSet.getString(6);
				String postalCode = myResultSet.getString(7);
				String town = myResultSet.getString(8);
				String password = myResultSet.getString(9);
				int credit = myResultSet.getInt(10);

				userEdit = new Utilisateur(idUser, pseudo, name, firstName, email, telephone, street, postalCode, town,
						password, credit);
			}

		} catch (SQLException e) {

			throw new SQLException(e.getMessage());
		}
		return userEdit;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param user        -> Objet de type Utilisateur. Permet de r�cup�rer l'ancien
	 *                    mot de passe enregistr� dans la base de donn�es
	 * @param oldPassword -> Chaine de caract�res correspondant à l'ancien mot de
	 *                    passe fourni par l'utilisateur
	 * @param newPassword -> Chaine de caract�res correspondant au nouveau mot de
	 *                    passe fourni par l'utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire Cette fonction permet de modifier le mot de passe d'un
	 *              utilisateur dans la base de donn�es
	 */
	@Override
	public void editUserPassword(Utilisateur user, String oldPassword, String newPassword) throws Exception {

		String pseudo = user.getPseudo();

		// On r�cup�re l'ancien mot de passe gr�ce au pseudo
		String hashedPassword = getUserCryptedPasswordByPseudo(pseudo);

		// On v�rifie si le mot de passe chiffr� correspond avec oldPassword
		if (BCrypt.checkpw(oldPassword, hashedPassword)) {

			// On chiffre le nouveau mot de passe
			String newhashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

			try (Connection databaseConnection = JdbcTools.getConnection();
					PreparedStatement preparedStatement = databaseConnection.prepareStatement(UPDATE_USER_PASSWORD);) {

				preparedStatement.setString(1, newhashedPassword);
				preparedStatement.setString(2, pseudo);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {

				throw new Exception("Une erreur est survenue pendant la mise � jour du mot de passe");

			}

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

				userInformation = new Utilisateur(userPseudo, nom, prenom, userEmail, telephone, rue, codePostal,
						ville);

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
