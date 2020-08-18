package fr.eni.encheres.dal.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

/**
 * Classe implémentant l'interface UtilisateurDAO et contenant les différentes
 * méthodes permettant de gérer les requêtes SQL liées aux utilisateurs
 */

public class UtilisateurDAOJdbcImpl implements UtilisateurDAO {

	@Override
	public void userConnection(String email, String password) throws SQLException {
		String userEmail = email; // Il faut récupérer les informations
		String userPassword = password; // rentrées dans le formulaire par l'utilisateur

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

					// Création de la session

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
