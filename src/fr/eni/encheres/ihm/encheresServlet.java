package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bo.Categorie;


/**
 * @author qpoinsig2020
 * Servlet de la page d'accueil
 */

@WebServlet({"/encheres", "/accueil"})
public class encheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static List<Categorie> listeCategories = null;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirection avec boutons inscrire et connecter 
		String action = request.getParameter("action");
		if(action!=null) {
			switch (action) {
			case "inscription":
				request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
				break;
			case "login":
				request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
				break;
			default:
				request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
				break;
			}
		}
		else request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("rechercher");
		if(("rechercher").equals(action)) {
			String categorie = request.getParameter("selectedCategorie");
			request.setAttribute("selectedCategorie", categorie);
		}
		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

	}
