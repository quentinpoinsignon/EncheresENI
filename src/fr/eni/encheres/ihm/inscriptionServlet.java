package fr.eni.encheres.ihm;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class inscriptionServlet
 */
@WebServlet("/inscription")
public class inscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UtilisateurManager uMger = new UtilisateurManager();
	
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String pseudo = request.getParameter("pseudo");
	    String nom = request.getParameter("nom");
	    String prenom = request.getParameter("prenom");
	    String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
        String codepostal = request.getParameter("codepostal");
        String ville = request.getParameter("ville");
        String password = request.getParameter("password");
        String confirmation = request.getParameter("confirmation");
        String creer = request.getParameter("creer");
        
        

        Utilisateur utilisateur = null;
        if(("1").equals(creer)) {
        	utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, password);
        	uMger.addUtilisateur(utilisateur);
        }
        request.getRequestDispatcher("/WEB-INF/pages/listeEncheres.jsp").forward(request, response);

	}

}
