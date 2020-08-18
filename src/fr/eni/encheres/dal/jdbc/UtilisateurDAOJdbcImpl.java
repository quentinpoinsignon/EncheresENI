package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * Classe impl�mentant l'interface UtilisateurDAO et contenant les diff�rentes
 * m�thodes permettant de g�rer les requ�tes SQL li�es aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	public static Connection getConnection() throws SQLException {

		return JdbcTools.getConnection();

	}

	@Override
	public void userInsert(Utilisateur user) throws Exception {

		final String USER_INSERT = "INSERT INTO UTILISATEURS(pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES(?,?,?,?,?,?,?,?)";

		try (Connection databaseConnection = getConnection();
				PreparedStatement preparedStatement = databaseConnection.prepareStatement(USER_INSERT);) {

			// Valoriser les paramètres de la requete

			preparedStatement.setString(1, user.getPseudo());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getEmail());
			preparedStatement.setString(5, user.getTelephone());
			preparedStatement.setString(6, user.getRue());
			preparedStatement.setString(7, user.getCodePostal());
			preparedStatement.setString(8, user.getVille());
			preparedStatement.setString(9, user.getMotDePasse());
			preparedStatement.setInt(10, user.getCredit());
			preparedStatement.setBoolean(5, user.getAdministrateur());

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
