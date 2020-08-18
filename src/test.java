import java.sql.SQLException;

import fr.eni.encheres.dal.jdbc.UtilisateurDAOJdbcImpl;

public class test {

	public static void main(String[] args) throws SQLException {

		UtilisateurDAOJdbcImpl user = new UtilisateurDAOJdbcImpl();

		user.userConnection("email", "passe");

	}

}
