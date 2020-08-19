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
 *
 *         Classe impl�mentant l'interface UtilisateurDAO et contenant les
 *         diff�rentes m�thodes permettant de g�rer les requ�tes SQL li�es aux
 *         utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	/**
	 * D�claration des variables de classes
	 * 
	 * @Constante USER_INSERT -> String contenant la requ�te SQL permettant
	 *            d'enregistrer un nouvel utilisateur dans la base de donn�es
	 * @Constante VERIF_USER_DATABASE -> String contenant la requ�te SQL permettant
	 *            de v�rifier un utilisateur est enregistr� dans la base de donn�es
	 *            � l'aide de son mail et de son mot de passe
	 * @Constante USER_INSERT -> String contenant la requ�te SQL permettant de
	 *            v�rifier un utilisateur est enregistr� dans la base de donn�es �
	 *            l'aide de son pseudo et de son mot de passe
	 */

	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";
	private final String VERIF_USER_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ? AND mot_de_passe = ?";;
	private final String VERIF_PSEUDO_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where pseudo= ? AND mot_de_passe = ?";

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

			throw new Exception("Erreur lors de l'ajout de l'utilisateur");

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

			throw new Exception("Erreur lors de l'ajout de l'utilisateur");

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
	 * 
	 * String userEmail = email; // Il faut r�cup�rer les informations String
	 * userPassword = password; // rentr�es dans le formulaire par l'utilisateur
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
	 * // Cr�ation de la session
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
