package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * Classe implï¿½mentant l'interface UtilisateurDAO et contenant les diffï¿½rentes
 * mï¿½thodes permettant de gï¿½rer les requï¿½tes SQL liï¿½es aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {


	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES(?,?,?,?,?,?,?,?)";
	private final String VERIF_USER_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ? AND mot_de_passe = ?";

	private final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe) VALUES(?,?,?,?,?,?,?,?,?)";
	private final String VERIF_USER_DATABASE = "SELECT pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM UTILISATEURS where email= ? AND mot_de_passe = ?";


	public static Connection getConnection() throws SQLException {

		return JdbcTools.getConnection();

	}

	/**
	 * @author jarrigon2020
	 * @param email    -> Chaine de caractère qui correspond à l'email fourni par
	 *                 l'utilisateur
	 * @param password -> Chaine de caractère correspondant au mot de passe fourni
	 *                 par l'utilisateur
	 * @return user -> Objet de type Utilisateur
	 * 
	 *         Cette méthode permet de vérifier si l'email et le mot de passe fourni
	 *         par l'utilisateur sont bien présents dans la base de données et
	 *         correspondent entre eux.
	 */
	public Utilisateur userConnection(String email, String password) throws Exception {

		ResultSet MyResultset = null;
		Utilisateur user = null;

		try (Connection databaseConnection = getConnection();
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

	@Override
	/**
	 * @author jarrigon2020
	 * @param user -> Objet de type Utilisateur
	 * 
	 *             Cette fonction permet d'enregistrer un nouvel utilisateur dans la
	 *             base de donnï¿½es
	 */
	public void userInsert(Utilisateur user) throws Exception {

		try (Connection databaseConnection = getConnection();
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
	 * String userEmail = email; // Il faut rï¿½cupï¿½rer les informations String
	 * userPassword = password; // rentrï¿½es dans le formulaire par l'utilisateur
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
	 * // Crï¿½ation de la session
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
