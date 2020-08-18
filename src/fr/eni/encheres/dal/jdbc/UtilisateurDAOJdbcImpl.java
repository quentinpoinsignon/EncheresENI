package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * Classe impl�mentant l'interface UtilisateurDAO et contenant les diff�rentes
 * m�thodes permettant de g�rer les requ�tes SQL li�es aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	@Override
	public void userConnection(String email, String password) throws SQLException {
		String userEmail = email; // Il faut r�cup�rer les informations
		String userPassword = password; // rentr�es dans le formulaire par l'utilisateur

		ResultSet requestResult = null;

		String verifEmailRequest = " SELECT email FROM UTILISATEURS WHERE email =" + userEmail;
		String VerifPassword = "SELECT mot_de_passe FROM UTILISATEURS WHERE email =" + userPassword;
		String RecupUserInfo = "SELECT pseudo,nom,prenom FROM UTILISATEURS WHERE email = " + userEmail
				+ " AND mot_de_passe =" + userPassword;

		Connection connect = JdbcTools.getConnection();

		Statement stmt = connect.createStatement();
		requestResult = stmt.executeQuery(verifEmailRequest);

		if (requestResult != null) {
			System.out.println("Email existe");
			requestResult = stmt.executeQuery(VerifPassword);
			if (requestResult != null) {
				System.out.println("Mot de passe ok");

				requestResult = stmt.executeQuery(RecupUserInfo);

				Boolean nextResult = requestResult.next();

				while (nextResult) {

					// Cr�ation de la session

					requestResult.getString(1);
					requestResult.getString(2);
					requestResult.getString(3);

					nextResult = requestResult.next();

				}
				requestResult.close();

			}
		}

	}
}
