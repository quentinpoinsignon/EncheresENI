package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class accueilConnecteServlet
 * @author qpoinsig2020
 * 
 * @Commentaire 
 * 
 */

@WebServlet("/accueilConnecte")
public class accueilConnecteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(("deconnexion").equals(action)){
			request.getSession().removeAttribute("connectedUser");
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
		else { 
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("rechercher");
		
		if(("rechercher").equals(action)) {
			String categorie = request.getParameter("selectedCategorie");
			String search = request.getParameter("search");
			request.setAttribute("selectedCategorie", categorie);
			request.setAttribute("search", search);
		}
		
		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

}
