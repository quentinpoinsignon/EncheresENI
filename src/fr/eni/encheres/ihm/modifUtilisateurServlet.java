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

@WebServlet("/modifUtilisateur")
public class modifUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action = request.getParameter("btnAction");
	HttpSession session = request.getSession();
	Utilisateur connectedUser = (Utilisateur)session.getAttribute("connectedUser");
	String message = "";
	
	switch (action) {
	case "modifier":
		connectedUser.setPseudo(request.getParameter("pseudo"));
		connectedUser.setPrenom(request.getParameter("prenom"));
		connectedUser.setTelephone(request.getParameter("telephone"));
		connectedUser.setCodePostal(request.getParameter("codepostal"));
		connectedUser.setNom(request.getParameter("nom"));
		connectedUser.setEmail(request.getParameter("email"));
		connectedUser.setRue(request.getParameter("rue"));
		connectedUser.setVille(request.getParameter("ville"));
		uMger.editUserProfile();
		
		
		break;
	case "supprimer":
		
		break;
	case "annuler":
		System.out.println("case annuler");
		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
		break;
	default:
		break;
	}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
