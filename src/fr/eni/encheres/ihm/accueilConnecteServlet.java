package fr.eni.encheres.ihm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class accueilConnecteServlet
 */

@WebServlet("/accueilConnecte")
public class accueilConnecteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Paramètres nécessaires pour griser la partie achat vente en fonction de la sélection du bouton radio : NE MARCHE PAS
//		String selectAchatsVentes = request.getParameter("rdoSelectAchatsVentes");
//		System.out.println(selectAchatsVentes);
//		switch (selectAchatsVentes) {
//		case ("achats"):
//			request.setAttribute("rdoAchatsVentes", "achats");
//			break;
//		case ("ventes"):
//			request.setAttribute("rdoAchatsVentes", "ventes");
//			break;
//		default:
//			request.setAttribute("rdoAchatsVentes", "achats");
//			break;
//		}
//		request.setAttribute("rdoAchatsVentes", "achats");
		
		//Gestion du menu nav en haut à droite
		
		
		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
