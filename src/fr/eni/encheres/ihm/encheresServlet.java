package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;


/**
 * @author qpoinsig2020
 * Servlet de la page d'accueil
 */

@WebServlet({"/encheres", "/accueil"})
public class encheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnchereManager eMger = new EnchereManager();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inthedoget");
		//initialisation de la liste des articles
		List<Article> listeEncheres = eMger.userSearchEncheres("", "", 0,
				false, false, false, false, false, false);
		request.setAttribute("listeEncheres", listeEncheres);
		
		
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
				break;
			}
		}
		else request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("rechercher");
		if(("rechercher").equals(action)) {
			String research = (request.getParameter("search")==null?"":request.getParameter("search"));
			String categorie = request.getParameter("selectedCategorie");
			int idCategorie = Integer.parseInt(categorie);
			List<Article> listeEncheres = eMger.userSearchEncheres(research, "", idCategorie, false, false, false, false, false, false);
			request.setAttribute("listeEncheres", listeEncheres);
			request.setAttribute("selectedCategorie", categorie);
			request.setAttribute("search", research);
			
		}
		else {
			List<Article> listeEncheres = eMger.userSearchEncheres("", "", 0,
					false, false, false, false, false, false);
			request.setAttribute("listeEncheres", listeEncheres);
		}
		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

	}
