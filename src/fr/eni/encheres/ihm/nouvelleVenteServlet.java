package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
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
	private CategorieManager cMger = new CategorieManager();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// récupération des données du formulaire
		//SELECT utl.pseudo, ctg.libelle, ctg.no_categorie, nom_article, description, date_debut_encheres,date_fin_encheres,prix_initial,prix_vente
		String nomArticle = request.getParameter("nomArticle");
		String description = request.getParameter("description");
		int selectedCategorie = Integer.parseInt(request.getParameter("selectedCategorie"));
		List<Categorie> listeCategories = cMger.selectAllCategories();
		Categorie selectedCategorieObject = ihmUtils.returnCategorieById(listeCategories, selectedCategorie);
		
		int prixDepart = Integer.parseInt(request.getParameter("prixDepart"));
		int prixVente = 0;
		// formatage des dates
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.FRENCH);
		Date dateFin=null;
		try {
			dateFin = formatter.parse(request.getParameter("dateFin"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Date dateDebut=null;
		try {
			dateDebut = formatter.parse(request.getParameter("dateDebut"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");

		//code pour changer de date
//		class Main
//		{
//			// Function to convert java.util Date to java.sql Date in Java
//			public static java.sql.Date convert(java.util.Date date)
//			{
//				return new java.sql.Date(date.getTime());
//			}
//
//			public static void main (String[] args)
//			{
//				java.util.Date utilDate = new java.util.Date();
//				System.out.println("java.util.Date : " + utilDate);
//
//				java.sql.Date sqlDate = convert(utilDate);
//				System.out.println("java.sql.Date  : " + sqlDate);
//			}
//		}
		
		
		
		
		
		
//		request.setAttribute("nomArticle", nomArticle);
//		request.setAttribute("description", description);
//
//		request.setAttribute("prixDepart", prixDepart);
//		request.setAttribute("dateDebut", dateDebut);
//		request.setAttribute("dateFin", dateFin);
//
//		request.setAttribute("rue", rue);
//		request.setAttribute("codepostal", codepostal);
//		request.setAttribute("ville", ville);
		
		//création d'un nouvel article
		Utilisateur utilisateur = null;
		Article article = new Article(nomArticle, description, dateDebut, dateFin, prixDepart, prixVente, utilisateur, selectedCategorieObject);
		aMger.addArticle(article);

		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
	}

}
