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
	   
		//récupération des données du formulaire
		String pseudo = request.getParameter("pseudo");
	    String nom = request.getParameter("nom");
	    String prenom = request.getParameter("prenom");
	    String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		String rue = request.getParameter("rue");
        String codepostal = request.getParameter("codepostal");
        String ville = request.getParameter("ville");
        String motDePasse = request.getParameter("password");
        String confirmation = request.getParameter("confirmation");
        String creer = request.getParameter("creer");
        String messageErreur = "";
        //Vérification des caractères du pseudo
        /*if(!ihmUtils.isAlphaNumeric(pseudo)) {
        	System.out.println("Le pseudo contient un caractère interdit");
        }*/
        
   
        
        //verification confirmation = mot de passe
        if (confirmation.equals(motDePasse)) {
        	 //création du nouvel utilisateur 
            Utilisateur utilisateur = null;
            if(("1").equals(creer)) {
            	utilisateur = new Utilisateur(pseudo, nom, prenom, email, telephone, rue, codepostal, ville, motDePasse);
            	uMger.addUtilisateur(utilisateur);
            }
            request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
        } else {
        	//champs différents
        	System.out.println("mots de passe différents!");
        	messageErreur = "Les mots de passe sont différents!";
			request.setAttribute("erreurMotDePasse", messageErreur);
			request.setAttribute("pseudo", pseudo);
			request.setAttribute("nom", nom);
			request.setAttribute("prenom", prenom);
			request.setAttribute("email", email);
			request.setAttribute("telephone", telephone);
			request.setAttribute("rue", rue);
			request.setAttribute("codepostal", codepostal);
			request.setAttribute("ville", ville);
			
	        request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);

        }

       

	}

}
