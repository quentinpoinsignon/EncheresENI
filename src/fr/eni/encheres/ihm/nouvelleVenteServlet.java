package fr.eni.encheres.ihm;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.encheres.bll.ArticleManager;
import fr.eni.encheres.bll.CategorieManager;
import fr.eni.encheres.bll.bllUtils;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

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
		int noCategorie = Integer.parseInt(request.getParameter("selectedCategorie"));
		List<Categorie> listeCategories = cMger.selectAllCategories();
		Categorie selectedCategorie = bllUtils.returnCategorieById(listeCategories, noCategorie);
		
		int prixDepart = Integer.parseInt(request.getParameter("prixDepart"));
		int prixVente = 0;
		
		// formatage des dates
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRENCH);
		java.sql.Date dateFin=null;
		try {
			dateFin = bllUtils.dateUtilToSql(formatter.parse(request.getParameter("dateFin")));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date dateDebut=null;
		try {
			dateDebut = bllUtils.dateUtilToSql(formatter.parse(request.getParameter("dateDebut")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// création de l'article sans point de retrait
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("userConnected");
		Article newArticle = new Article(nomArticle, description, dateDebut, dateFin, prixDepart, prixVente, utilisateur, selectedCategorie);
		
		// création du point de retrait
		String rue = request.getParameter("rue");
		String codepostal = request.getParameter("codepostal");
		String ville = request.getParameter("ville");
		Retrait pointDeRetrait = new Retrait(newArticle, rue, codepostal, ville);
		
		// affectation du point de retrait à l'article
		newArticle.setPointDeRetrait(pointDeRetrait);
		// saisie de l'article dans la BDD
		aMger.addArticle(newArticle);
		
		
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

		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

}
