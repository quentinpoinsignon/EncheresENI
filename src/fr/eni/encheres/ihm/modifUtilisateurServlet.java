package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/modifUtilisateur")
public class modifUtilisateurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action = request.getParameter("btnAction");
	switch (action) {
	case "modifier":
		
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
