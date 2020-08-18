package fr.eni.encheres.ihm;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bo.Utilisateur;

/**
 * Servlet implementation class inscriptionServlet
 */
@WebServlet("/inscription")
public class inscriptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
<<<<<<< HEAD
        
        Utilisateur user = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse)



=======
        String message;
        
        if (password != confirmation) {
        	
        	message = "La confirmation est différente du mot de passe!";
			this.getServletContext().getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
        Utilisateur utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, password);
        request.getRequestDispatcher("/WEB-INF/pages/listeEnchres.jsp").forward(request, response);
        System.out.println(utilisateur.getNom());
>>>>>>> 6042f29190b72ea60264afb4cc2c78306100643d
	}

}
