package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	 * @Constante VERIF_USER_DATABASE -> String contenant la requête SQL permettant
	 *            de vérifier un utilisateur est enregistré dans la base de données
	 *            à l'aide de son mail et de son mot de passe
	 * @Constante USER_INSERT -> String contenant la requête SQL permettant de
	 *            vérifier un utilisateur est enregistré dans la base de données à
	 *            l'aide de son pseudo et de son mot de passe
	 * @Constante USER_PROFIL_REQUEST_BY_PSEUDO -> String contenant la requête sql
	 *            permettant de rechercher un utilisateur grâce à son pseudo et
	 *            d'afficher son profil
	 * @Constante EDIT_USER_PROFIL -> String contenant la requête sql permettant de
	 *            modifier les informations d'un utilisateur dans la base de
	 *            données. On sélectionne l'utilisateur grâce à l'id utilisateur
	 * 
	 */

	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";
	private final String VERIF_USER_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ? AND mot_de_passe = ?";;
	private final String VERIF_PSEUDO_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where pseudo= ? AND mot_de_passe = ?";
	private final String USER_PROFIL_REQUEST_BY_PSEUDO = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE pseudo = ?";
	private final String EDIT_USER_PROFIL = "";
	private final String USER_PROFIL_REQUEST_BY_ID = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville FROM UTILISATEURS WHERE no_utilisateur = ?";

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

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_USER_DATABASE);) {

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			MyResultset = preparedStatement.executeQuery();

			if (MyResultset.next()) {

				String pseudo = MyResultset.getString(1);
				String nom = MyResultset.getString(2);
				String prenom = MyResultset.getString(3);
				String userEmail = MyResultset.getString(4);
				String telephone = MyResultset.getString(5);
				String rue = MyResultset.getString(6);
				String codePostal = MyResultset.getString(7);
				String ville = MyResultset.getString(8);
				String motDePasse = MyResultset.getString(9);

				user = new Utilisateur(pseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville, motDePasse);

			}

		} catch (SQLException e) {

			throw new Exception("Erreur lors la tentative de connexion");

		}

		return user;

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

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(VERIF_PSEUDO_DATABASE);) {

			preparedStatement.setString(1, pseudo);
			preparedStatement.setString(2, password);

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

				user = new Utilisateur(userPseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
						motDePasse);

			}

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
	 *              la base de données
	 */
	@Override
	public void userInsert(Utilisateur user) throws Exception {

		try (Connection databaseConnection = JdbcTools.getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_INSERT);) {

			preparedStatement.setString(1, user.getPseudo());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getTelephone());
			preparedStatement.setString(6, user.getRue());
			preparedStatement.setString(7, user.getCodePostal());
			preparedStatement.setString(8, user.getVille());
			preparedStatement.setString(9, user.getMotDePasse());

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

		} catch (SQLException e) {

			throw new Exception("L'utilisateur demandé n'existe pas");

		}

		return userProfil;

	}

	/**
	 * @author jarrigon2020
	 * 
	 * @param pseudo          -> Chaine de caractère correspondant au pseudo de
	 *                        l'utilisateur
	 * @param name            -> Chaine de caractère correspondant au nom de
	 *                        l'utilisateur
	 * @param firstName       -> Chaine de caractère correspondant au prénom de
	 *                        l'utilisateur
	 * @param email           -> Chaine de caractère correspondant à l'email de
	 *                        l'utilisateur
	 * @param telephoneNumber -> Chaine de caractère correspondant au numéro de
	 *                        téléphone de l'utilisateur
	 * @param street          -> Chaine de caractère correspondant à la rue de
	 *                        l'utilisateur
	 * @param postalCode      -> Chaine de caractère correspondant au code postal de
	 *                        l'utilisateur
	 * @param password        -> Chaine de caractère correspondant au mot de passe
	 *                        de l'utilisateur
	 * 
	 * @commentaire
	 * 
	 *              Cette méthode permet de modifier les informations de
	 *              l'utilisateur dans la base de données
	 */
	@Override
	public void editUserProfil(String pseudo, String name, String firstName, String email, String telephoneNumber,
			String street, String postalCode, String password) {

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
				String motDePasse = MyResultset.getString(9);

				userInformation = new Utilisateur(userPseudo, nom, prenom, userEmail, telephone, rue, codePostal, ville,
						motDePasse);

			}

		} catch (SQLException e) {

			throw new Exception("Problème lors de la récupération des informations de l'utilisateur");

		}

		return userInformation;

	}

	/**
	 * 
	 * String userEmail = email; // Il faut récupérer les informations String
	 * userPassword = password; // rentrées dans le formulaire par l'utilisateur
	 * 
	 * ResultSet requestResult = null;
	 * 
	 * String verifEmailRequest = " SELECT email FROM UTILISATEURS WHERE email =" +
	 * userEmail; String VerifPassword = "SELECT mot_de_passe FROM UTILISATEURS
	 * WHERE email =" + userPassword; String RecupUserInfo = "SELECT
	 * pseudo,nom,prenom FROM UTILISATEURS WHERE email = " + userEmail + " AND
	 * mot_de_passe =" + userPassword;
	 * 
	 * Connection connect = JdbcTools.getConnection();
	 * 
	 * Statement stmt = connect.createStatement(); requestResult =
	 * stmt.executeQuery(verifEmailRequest);
	 * 
	 * if (requestResult != null) {
	 * 
	 * System.out.println("Email existe"); requestResult =
	 * stmt.executeQuery(VerifPassword);
	 * 
	 * if (requestResult != null) { System.out.println("Mot de passe ok");
	 * requestResult = null; requestResult = stmt.executeQuery(RecupUserInfo);
	 * 
	 * Boolean nextResult = requestResult.next();
	 * 
	 * while (nextResult) {
	 * 
	 * // Création de la session
	 * 
	 * requestResult.getString(1); requestResult.getString(2);
	 * requestResult.getString(3);
	 * 
	 * nextResult = requestResult.next();
	 * 
	 * }
	 * 
	 * requestResult.close();
	 * 
	 * } }
	 * 
	 * }
	 * 
	 */
}
