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

/**
 * @author qpoinsig2020
 * traitement de la page de login
 */

@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
	private String messageErreur = "";

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
	
		//à recoder pour traiter le mot de passe oublié (remplacer accueil ci-dessous)
		if(("accueil").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
		request.setAttribute("erreurLogin", messageErreur);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		messageErreur = "";
		//récupération des attributs
		String action = request.getParameter("action");
		
		switch (action) {
		case "connexion":
			String identifiant = request.getParameter("identifiant");
			String password = request.getParameter("password");
			
			if(identifiant.equals("") || password.equals("")) {
				messageErreur = "Veuillez entrer un login et un mot de passe";
				request.setAttribute("erreurLogin", messageErreur);
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
			}
			
			else {
				Utilisateur connectedUser = null;
				switch (ihmUtils.natureIdentifiant(identifiant)) {
				case "pseudo":
					connectedUser = uMger.connectionByPseudo(identifiant, password);
					break;
				case "email":
					connectedUser = uMger.connectionByEmail(identifiant, password);
					break;
				default:
					break;
				}
				if(connectedUser != null) {
					HttpSession session = request.getSession(true);
					session.setAttribute("connectedUser", connectedUser);
					request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
				}
				else {
					messageErreur = "Login ou mot de passe incorrect";
					request.setAttribute("erreurLogin", messageErreur);
					request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
				}
			}
			break;
		case "creerCompte" :
			request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
			break;
		case "annuler" :
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
			break;
		default:
			break;
		}
	}
}
