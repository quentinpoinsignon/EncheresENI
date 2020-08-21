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
 * @Commentaire Classe implémentant l'interface UtilisateurDAO et contenant les
 *              différentes méthodes permettant de gérer les requêtes SQL liées
 *              aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	/**
	 * Déclaration des variables de classes
	 * 
	 * @Constante USER_INSERT -> String contenant la requête SQL permettant
	 *            d'enregistrer un nouvel utilisateur dans la base de données
	 * 
	 **/
	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";

	/**
	 * @Constante VERIF_EMAIL_DATABASE -> String contenant la requête SQL permettant
	 *            de vérifier un utilisateur est enregistré dans la base de données
	 *            à l'aide de son mail et de son mot de passe
	 **/
	private final String VERIF_EMAIL_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ?";;
	/**
	 * @Constante VERIF_PSEUDO_DATABASE -> String contenant la requête SQL
	 *            permettant de vérifier un utilisateur est enregistré dans la base
	 *            de données à l'aide de son pseudo et de son mot de passe
	 */
	private final String VERIF_PSEUDO_DATABASE = "SELECT no_utilisateur, pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where pseudo= ?";
	/**
	 * @Constante USER_PROFIL_REQUEST_BY_PSEUDO -> String contenant la requête sql
	 *            permettant de rechercher un utilisateur grâce à son pseudo et
	 *            d'afficher son profil
	 */
	private final String USER_PROFIL_REQUEST_BY_PSEUDO = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE pseudo = ?";
	/**
	 * @Constante EDIT_USER_PROFIL -> String contenant la requête sql permettant de
	 *            modifier les informations d'un utilisateur dans la base de
	 *            données. On sélectionne l'utilisateur grâce à l'id utilisateur
	 */
	private final String EDIT_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET pseudo = ?  , nom  = ?, prenom = ?, email = ?,telephone = ?,rue=?, code_postal=?, ville=?, mot_de_passe=?\r\n"
			+ "WHERE no_utilisateur =  ?";

	/**
	 * @Constante USER_PROFIL_REQUEST_BY_ID -> String contenant la requête SQL
	 *            permettant de récupérer les informations d'un utilisateur grâce à
	 *            son identifiant
	 */
	private final String USER_PROFIL_REQUEST_BY_ID = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE no_utilisateur = ?";

	/**
	 * @Constante REMOVE_USER_PROFIL -> Chaine de caractère contenant une requête
	 *            SQL permettant de modifier les informations d'un utilisateur afin
	 *            de supprimer toutes ses informations personelles en les remplaçant
	 *            par des valeurs par défaut.
	 * 
	 */
	private final String REMOVE_USER_PROFIL = "UPDATE UTILISATEURS\r\n"
			+ "SET nom  = 'Utilisateur désinscrit', prenom = 'Utilisateur désinscrit', email = 'XXXX@email.com',telephone = 'XXXXXXXXXX',rue='XXXXXXX',code_postal='XXXXX' ,ville='XXXXX', mot_de_passe='MNy5jH3we6SjA44UeJ7A68vn5DcrD2'\r\n"
			+ "WHERE no_utilisateur =  ?";
	/**
	 * @Constante SELECT_ALL_PSEUDO -> Chaine de caractères contenant une requête
	 *            sql permettant de récupérer l'ensemble des pseudos enregistrés
	 *            dans la base de données
	 */

	private final String SELECT_ALL_PSEUDO = "SELECT pseudo FROM UTILISATEURS\r\n" + "ORDER BY pseudo";

	/**
	 * @Constante SELECT_ALL_EMAIL -> Chaine de caractères contenant une requête SQL
	 *            permettant de récupérer l'ensemble des emails enregistrés dans la
	 *            base de données
	 */
	private final String SELECT_ALL_EMAIL = "SELECT email FROM UTILISATEURS\r\n\" + \"ORDER BY email";

	/**
	 * @Constante SELECT_CRYPTED_PASSWORD -> Chaine de caratères contenant une
	 *            requête SQL permettant de récupérer la version cryptée d'un mot de
	 *            passe présent dans la base de données à partir du pseudo
	 */
	private final String SELECT_CRYPTED_PASSWORD_BY_PSEUDO = "SELECT mot_de_passe FROM UTILISATEURS\r\n"
			+ "WHERE pseudo = ?";

	/**
	 * @Constante SELECT_CRYPTED_PASSWORD_BY_EMAIL -> Chaine de caratères contenant
	 *            une requête SQL permettant de récupérer la version cryptée d'un
	 *            mot de passe présent dans la base de données à partir de l'email
	 */

	private final String SELECT_CRYPTED_PASSWORD_BY_EMAIL = "SELECT mot_de_passe FROM UTILISATEURS\r\n"
			+ "WHERE email = ?";

	/**
	 * @Constante SELECT_USER_INFORMATION_AFTER_EDIT -> Chaine de caractère
	 *            contenant une requête SQL utilisés dans la fonction
	 *            editUserInformation() permettant de récupérer les informations de
	 *            l'utilisateur après modifications
	 */
	private final String SELECT_USER_INFORMATION_AFTER_EDIT = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit FROM UTILISATEURS\r\n"
			+ "WHERE no_utilisateur = ?";

	/**
	 * @Constante UPDATE_USER_PASSWORD -> Chaine de caractère contenant une requête
	 *            SQL permettant de modifier le mot de passe de l'utilisateur
	 */
	private final String UPDATE_USER_PASSWORD = "UPDATE UTILISATEURS\r\n" + "SET mot_de_passe = ? \r\n"
			+ "WHERE pseudo = ?";

	/**
	 * @author jarrigon2020
	 * @param email    -> Chaine de caractère qui correspond à l'email fourni par
	 *                 l'utilisateur
	 * @param password -> Chaine de caractère correspondant au mot de passe fourni
	 *                 par l'utilisateur
	 * @return user -> Objet de type Utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette méthode permet de vérifier si l'email et le mot de passe
	 *              fourni par l'utilisateur sont bien présents dans la base de
	 *              données et correspondent entre eux.
	 */
	@Override
	public Utilisateur userConnection(String email, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		// On récupère la version cryptée du mot de passe
		String hashedPassword = getUserCryptedPasswordByEmail(email);

		// On vérifie si le mot de passe en clair correspond au mot de passe haché
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
	 * @param pseudo -> Chaine de caractère contenant le pseudo à partir duquel on
	 *               souhaite récupérer le mot de passe
	 * 
	 * @return hashedPassword -> Chaine de caractère contenant un mot de passe
	 *         crypté dans la base de données
	 * @throws Exception
	 * @Commentaire Cette méthode permet de récupérer une version cryptée d'une mot
	 *              de passe dans la base de données à partir du pseudo utilisateur
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

			throw new Exception("Erreur lors de la récupération du mot de passe");

		}

		return hashedPassword;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param email -> Chaine de caractère contenant l'email à partir duquel on
	 *              souhaite récupérer le mot de passe
	 * 
	 * @return hashedPassword -> Chaine de caractère contenant un mot de passe
	 *         crypté dans la base de données
	 * @throws Exception
	 * @Commentaire Cette méthode permet de récupérer une version cryptée d'un mot
	 *              de passe dans la base de données à partir du mail de
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

			throw new Exception("Erreur lors de la récupération du mot de passe");

		}

		return hashedPassword;

	}

	/**
	 * @author jarrigon2020
	 * @param pseudo   -> Chaine de caractère qui correspond au pseudo fourni par
	 *                 l'utilisateur
	 * @param password -> Chaine de caractère correspondant au mot de passe fourni
	 *                 par l'utilisateur
	 * @return user -> Objet de type Utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette méthode permet de vérifier si le pseudo et le mot de passe
	 *              fourni par l'utilisateur sont bien présents dans la base de
	 *              données et correspondent entre eux.
	 */

	@Override
	public Utilisateur userConnectionPseudo(String pseudo, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		// On récupère la version cryptée du mot de passe
		String hashedPassword = getUserCryptedPasswordByPseudo(pseudo);

		// On vérifie si le mot de passe en clair correspond au mot de passe haché
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
	 *              la base de données. Le mot de passe est crypté grâce à
	 *              l'utilisation de la classe BCrypt
	 */
	@Override
	public void userInsert(Utilisateur user) throws Exception {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_INSERT,
						Statement.RETURN_GENERATED_KEYS)) {

			// Cryptage du mot de passe avant l'insertion dans la base de données

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
	 * @param pseudo -> Chaine de caractère correspondant au pseudo fourni
	 * 
	 * @return userProfil -> Objet de type Utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire
	 * 
	 *              Permet de rechercher le profil d'un utilisateur à partir de son
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

			throw new Exception("L'utilisateur demandé n'existe pas");

		}

		return userProfil;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param user -> Objet de type Utilisateur. Il permet de récupérer les
	 *             informations
	 * 
	 * @return userEdit -> Objet de type Utilisateur
	 * 
	 * @throws SQLException
	 * 
	 * @commentaire
	 * 
	 *              Cette méthode permet de modifier les informations de
	 *              l'utilisateur dans la base de données et de récupérer un nouvel
	 *              objet de type Utilisateur avec les informations misent à jour
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

			System.out.println("Avant la requête 2");

			preparedStatement.executeUpdate();

			System.out.println("Après la requête 2");

			// Il faut récupérer les infos misent à jour dans la base de données

			PreparedStatement newPreparedStatement = databaseConnection
					.prepareStatement(SELECT_USER_INFORMATION_AFTER_EDIT);

			newPreparedStatement.setInt(1, idUser);

			System.out.println("Avant la requête 2");
			myResultSet = newPreparedStatement.executeQuery();
			System.out.println("Après la requête 2");

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
		System.out.println(userEdit);
		return userEdit;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param user        -> Objet de type Utilisateur. Permet de récupérer l'ancien
	 *                    mot de passe enregistré dans la base de données
	 * @param oldPassword -> Chaine de caractères correspondant à l'ancien mot de
	 *                    passe fourni par l'utilisateur
	 * @param newPassword -> Chaine de caractères correspondant au nouveau mot de
	 *                    passe fourni par l'utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire Cette fonction permet de modifier le mot de passe d'un
	 *              utilisateur dans la base de données
	 */
	public void editUserPassword(Utilisateur user, String oldPassword, String newPassword) throws Exception {

		String pseudo = user.getPseudo();
		int idUser = user.getNoUtilisateur();

		// On récupère l'ancien mot de passe grâce au pseudo
		String hashedPassword = getUserCryptedPasswordByPseudo(pseudo);

		// On vérifie si le mot de passe chiffré correspond avec oldPassword
		if (BCrypt.checkpw(oldPassword, hashedPassword)) {

			// On chiffre le nouveau mot de passe
			String newhashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

			try (Connection databaseConnection = JdbcTools.getConnection();
					PreparedStatement preparedStatement = databaseConnection.prepareStatement(UPDATE_USER_PASSWORD);) {

				preparedStatement.setString(1, newhashedPassword);
				preparedStatement.setInt(2, idUser);

				preparedStatement.executeUpdate();

			} catch (SQLException e) {

				throw new Exception("Une erreur est survenue pendant la mise à jour du mot de passe");

			}

		}

	}

	/**
	 * @author jarrigon2020
	 * @param idUser -> int Correspond à l'identifiant de l'utilisateur dans la base
	 *               de données
	 * 
	 * @return userInformation -> Objet de type utilisateur
	 * @throws Exception
	 * 
	 * @Commentaire Cette méthode permet de récupérer les informations d'un
	 *              utilisateur grâce à son identifiant
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

			throw new Exception("Problème lors de la récupération des informations de l'utilisateur");

		}

		return userInformation;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param idUser -> Int correspondant à l'identifiant de l'utilisateur dans la
	 *               base de données
	 * 
	 * @Commentaire Cette méthode permet de supprimer les données personnelles d'un
	 *              utilisateur dans la base de données en les remplaçant par des
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
	 *         caractères contenant l'ensemble des pseudo enregistrés sur le site
	 * 
	 * @Commentaire Cette méthode permet de récupérer l'ensemble des pseudos
	 *              enregistrés dans la base de données
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
	 *         caractères contenant l'ensemble des emails enregistrés sur le site
	 * 
	 * @Commentaire Cette méthode permet de récupérer l'ensemble des emails
	 *              enregistrés dans la base de données
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
