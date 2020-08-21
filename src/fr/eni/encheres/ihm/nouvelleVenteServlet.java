package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.UtilisateurManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 * @author rbonin2020
 * servlet permettant la création d'un nouvel article 
 */
@WebServlet("/nouvelleVente")
public class nouvelleVenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleManager aMger = new ArticleManager();

       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// récupération des données du formulaire
		String nomArticle = request.getParameter("nomArticle");
		System.out.println(nomArticle);
		String description = request.getParameter("description");
		//Categorie categorie = (Categoriet)request.getParameter("categorie");
		int prixDepart = Integer.parseInt(request.getParameter("prixDepart"));
		int prixVente = 0;
		// formatage des dates
		LocalDateTime dateFin = LocalDateTime.parse(request.getParameter("dateFin"));
		LocalDateTime dateDebut = LocalDateTime.parse(request.getParameter("dateDebut"));
				
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");

		request.setAttribute("nomArticle", nomArticle);
		request.setAttribute("description", description);
		//request.setAttribute("categorie", categorie);			toDo: transformer un String en Categorie + finir création d'un nouvel article
		request.setAttribute("prixDepart", prixDepart);
		request.setAttribute("dateDebut", dateDebut);
		request.setAttribute("dateFin", dateFin);

		request.setAttribute("rue", rue);
		request.setAttribute("codepostal", codepostal);
		request.setAttribute("ville", ville);
		
		//création d'un nouvel article
		Utilisateur utilisateur = null;
		//Article article = new Article(nomArticle, description, dateDebut, dateFin, prixDepart,prixVente, utilisateur, categorie);
		//aMger.addArticle(article);

		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

}
