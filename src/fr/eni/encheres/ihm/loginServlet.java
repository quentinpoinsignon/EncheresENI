package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.DAOFactory;
import fr.eni.encheres.dal.interfaces.UtilisateurDAO;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String messageErreur = "";
		//à recoder pour traiter le mot de passe oublié (remplacer accueil ci-dessous)
		if(("accueil").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
		request.setAttribute("erreurLogin", messageErreur);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//récupération des attributs
		String identifiant = request.getParameter("identifiant");
		String password = request.getParameter("password");
		String connexion = request.getParameter("connexion");
		String rememberme = request.getParameter("rememberme");
		String creercompte = request.getParameter("creercompte");
		String messageErreur = "";
		
		System.out.println(creercompte);
//		if(identifiant != null && password != null) {
//			System.out.println("ok");
//		}
		
		if (("1").equals(creercompte)) {
			request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
		}
		
		if (("1").equals(connexion) && identifiant != null && password != null) {
			System.out.println((String)identifiant);
			Utilisateur connectedUser = null;
			connectedUser = uMger.checkUtilisateur(identifiant, password);
			if(connectedUser != null) {
				session.setAttribute("utilisateurOK", connectedUser);
				request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);
			}
			else {
				messageErreur = "Login ou mot de passe incorrect";
				request.setAttribute("erreurLogin", messageErreur);
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			}
			
			
//		}
		
		
	
//		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

}
}
