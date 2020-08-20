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
 * @author rbonin2020
 * servlet récupérant les données utilisateur
 * et les ré affichant pour modification
 */
@WebServlet("/profilModif")
public class profilModifServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//on récupère les données utilisateur dans la session
		HttpSession session = request.getSession();
		session.getAttribute("pseudo");
		session.getAttribute("nom");
		session.getAttribute("prenom");
		session.getAttribute("email");
		session.getAttribute("telephone");
		session.getAttribute("rue");
		session.getAttribute("codepostal");
		session.getAttribute("ville");

		request.getRequestDispatcher("/WEB-INF/pages/profilModif.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
			

	}

}
