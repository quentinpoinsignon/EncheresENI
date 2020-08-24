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

 
/**
 * @author qpoinsig2020
 * Servlet gérant l'affichage d'un profil utilisateur
 */
 
@WebServlet("/profil")
public class profilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
	
	
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur)session.getAttribute("connectedUser");
		String pseudoTemp = request.getParameter("user");
		Boolean isConnectedUser = (connectedUser.getPseudo().equals(pseudoTemp)) ? true : false;
		
		Utilisateur utilisateur = uMger.getUserProfileByPseudo(pseudoTemp);
		//définition des paramètres à envoyer à la jsp profil
		request.setAttribute("isConnectedUser", isConnectedUser);
		request.setAttribute("pseudo", utilisateur.getPseudo());
		request.setAttribute("nom", utilisateur.getNom());
		request.setAttribute("prenom", utilisateur.getPrenom());
		request.setAttribute("email", utilisateur.getEmail());
		request.setAttribute("telephone", utilisateur.getTelephone());
		request.setAttribute("rue", utilisateur.getRue());
		request.setAttribute("codePostal", utilisateur.getCodePostal());
		request.setAttribute("ville", utilisateur.getVille());
		
	
		request.getRequestDispatcher("/WEB-INF/pages/profil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (("retour").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
		}
		if (("modifier").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/modifUtilisateur.jsp").forward(request, response);
		}
	}

}
