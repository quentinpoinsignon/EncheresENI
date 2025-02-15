package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.corba.se.impl.ior.OldPOAObjectKeyTemplate;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * @author qpoinsig2020
 * @Commentaire  Servlet gérant l'écran de modification de l'utilisateur. Tous les champs peuvent être modifiés indépendamment.
 * 
 */
@WebServlet("/modifUtilisateur")
public class modifUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("btnAction");
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur)session.getAttribute("connectedUser");
		String message = "";
		
		switch (action) {
		case "modifier":
			// modification des infos sans mot de passe
			connectedUser.setPseudo(request.getParameter("pseudo"));
			connectedUser.setPrenom(request.getParameter("prenom"));
			connectedUser.setTelephone(request.getParameter("telephone"));
			connectedUser.setCodePostal(request.getParameter("codepostal"));
			connectedUser.setNom(request.getParameter("nom"));
			connectedUser.setEmail(request.getParameter("email"));
			connectedUser.setRue(request.getParameter("rue"));
			connectedUser.setVille(request.getParameter("ville"));
			connectedUser = uMger.editUserProfile(connectedUser);
			
			//modification du mot de passe
			String oldPassword = request.getParameter("oldPassword");
			String newPassword = request.getParameter("newPassword");
			String newPasswordConfirm = request.getParameter("newPasswordConfirm");
			
			
			if(oldPassword != null && newPassword != null && newPasswordConfirm != null) {
				if(!newPassword.equals(newPasswordConfirm)) {
					message = "Les champs Nouveau mot de passe et Confirmation ne correspondent pas";
					request.setAttribute("modifEffectuee", false);
					}
				else if(!oldPassword.equals("") & !newPassword.equals("") && oldPassword.equals(newPassword)) {
					message = "Le nouveau mot de passe doit être différent de l'ancien";
					request.setAttribute("modifEffectuee", false);
				}
				else if(newPassword.equals(newPasswordConfirm)) {
					uMger.editUserPassword(connectedUser, oldPassword, newPassword);
					message = "Modifications du profil effectuée";
					request.setAttribute("modifEffectuee", true);
				}
			}
			
			//set des attributes pour la jsp
			
			request.setAttribute("msgModif", message);
			request.getRequestDispatcher("/WEB-INF/pages/modifUtilisateur.jsp").forward(request, response);
			break;
		case "supprimer":
			uMger.removeUserProfile(connectedUser.getNoUtilisateur());
			session.removeAttribute("connectedUser");
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
			break;
		case "annuler":
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
			break;
		case "retour":
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
			break;
		default:
			break;
		}
		
	}

}
