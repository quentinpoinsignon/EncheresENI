package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bo.Article;


/**
 * @author qpoinsig2020
 * Servlet de la page d'accueil
 */

@WebServlet({"/encheres", "/accueil"})
public class encheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleManager aMger = new ArticleManager();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirection avec boutons inscrire et connecter 
		String action = request.getParameter("action");
		if(("inscription").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
		}
		if(("login").equals(action)) {
			request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
		}
		if((action) == null) {
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	}
