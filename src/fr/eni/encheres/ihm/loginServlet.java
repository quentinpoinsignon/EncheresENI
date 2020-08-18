package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		//à recoder pour traiter le mot de passe oublié (remplacer accueil ci-dessous)
		if(("accueil").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//récupération des attributs
		String identifiant = request.getParameter("identifiant");
		String password = request.getParameter("password");
		String connexion = request.getParameter("connexion");
		String rememberme = request.getParameter("rememberme");
		String creercompte = request.getParameter("creercompte");
		
		System.out.println(creercompte);
//		if(identifiant != null && password != null) {
//			System.out.println("ok");
//		}
		
		if (("1").equals(creercompte)) {
			request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
		}
		
		if (("1").equals(connexion) && identifiant != null && password != null) {
			System.out.println((String)identifiant);
			uMger.checkUtilisateur(identifiant, password);
//		}
		
		
	
//		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

}
}
