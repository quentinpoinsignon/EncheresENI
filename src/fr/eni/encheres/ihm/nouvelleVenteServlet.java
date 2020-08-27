package fr.eni.encheres.ihm;

import java.io.IOException;
import java.sql.Date;

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
import fr.eni.encheres.bo.Utilisateur;

/**
 * @author rbonin2020
 * @author qpoinsig2020
 * servlet permettant la création d'un nouvel article 
 */

@WebServlet("/nouvelleVente")
public class nouvelleVenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleManager aMger = new ArticleManager();
	private CategorieManager cMger = new CategorieManager();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// récup de l'utilisateur connecté
		HttpSession session = request.getSession();
		Utilisateur connectedUser = (Utilisateur)session.getAttribute("connectedUser");
		//set des attributs du point de retrait avec l'adresse par défaut de l'utilisateur connecté
		request.setAttribute("rue", connectedUser.getRue());
		request.setAttribute("codepostal", connectedUser.getCodePostal());
		request.setAttribute("ville", connectedUser.getVille());
		request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
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
		String rue = request.getParameter("rue");
		String ville = request.getParameter("ville");
		String codepostal = request.getParameter("codepostal");
		String msg = "";
		HttpSession session = request.getSession();
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("connectedUser");
		System.out.println(utilisateur.toString());
		String essaiDebut = request.getParameter("dateDebut");
		System.out.println(essaiDebut);
		

		// récupération des dates
		// formatage des dates en java.sql.time
		java.util.Date debut = null;
		java.util.Date fin = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
		try {
			debut = formatter.parse(request.getParameter("dateDebut"));
			System.out.println(debut.toString());
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		try {
			fin = formatter.parse(request.getParameter("dateFin"));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		java.sql.Date dateDebut=null;
			dateDebut = bllUtils.dateUtilToSql(debut);

		java.sql.Date dateFin=null;
			dateFin = bllUtils.dateUtilToSql(fin);
		
		if(dateFin.after(dateDebut) && dateFin!=null && dateDebut!=null) {
			// création de l'article sans point de retrait
			// nouvelle vente à 0 par défaut à la création de la vente
			Article newArticle = new Article(nomArticle, description, dateDebut, dateFin, prixDepart, prixVente, utilisateur, selectedCategorie, 0);
			
			// saisie de l'article dans la BDD
			aMger.addArticle(newArticle, rue, ville, codepostal);

			msg = "Article ajouté !";
			request.setAttribute("rue", rue);
			request.setAttribute("codepostal", codepostal);
			request.setAttribute("ville", ville);
		}
		else {
			msg = "Erreur lors de l'ajout de l'article : la date de fin doit être ultérieure à la date de début";
			request.setAttribute("nomArticle", nomArticle);
			request.setAttribute("description", description);
			request.setAttribute("prixDepart", prixDepart);
			request.setAttribute("rue", rue);
			request.setAttribute("codepostal", codepostal);
			request.setAttribute("ville", ville);
		}
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/WEB-INF/pages/nouvelleVente.jsp").forward(request, response);
	}

}
