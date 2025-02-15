package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;


/**
 * Servlet implementation class accueilConnecteServlet
 * 
 * @author qpoinsig2020
 * 
 * @Commentaire Servlet gérant l'affichage une fois l'utilisateur connecté
 * 
 */

@WebServlet("/accueilConnecte")
public class accueilConnecteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnchereManager eMger = new EnchereManager();
	public Logger logger = (Logger) LoggerFactory.getLogger(accueilConnecteServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (("deconnexion").equals(action)) {
			// on tue la session en enlevant l'utilisateur connecté
			Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("connectedUser");
			logger.info("Utilisateur " + connectedUser.getPseudo() + " déconnecté");
			request.getSession().removeAttribute("connectedUser");
			connectedUser = null;
			//set des attributs pour affichage correct de la page d'accueil non connecté
			List<Article> listeEncheres = eMger.userSearchEncheres("", "", 0,
					false, false, false, false, false, false);
			request.setAttribute("listeEncheres", listeEncheres);
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		}
		else {
			Utilisateur connectedUser = (Utilisateur)request.getSession().getAttribute("connectedUser");
			String userPseudo = connectedUser.getPseudo();
			Boolean openAuction = true;
			List<Article> listeEncheres = eMger.userSearchEncheres("", userPseudo, 0,
					openAuction, false, false, false, false, false);
			request.setAttribute("listeEncheres", listeEncheres);
			request.setAttribute("openAuction", openAuction);
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// récupération des paramètres
		String action = (request.getParameter("rechercher")==null?"":request.getParameter("rechercher"));
		String categorie = (request.getParameter("selectedCategorie")==null?"0":request.getParameter("selectedCategorie"));
		String research = (request.getParameter("search")==null?"":request.getParameter("search"));
		Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("connectedUser");
		String userPseudo = connectedUser.getPseudo();
		int idCategorie = (Integer.valueOf(categorie)==null?0:Integer.parseInt(categorie));
		Boolean openAuction = false;
		Boolean myAuction = false;
		Boolean winAuction = false;
		Boolean myCurrentSales = false;
		Boolean notSartedSales = false;
		Boolean endedSales = false;
		String rdoAV = (request.getParameter("rdoAV")==null?"":request.getParameter("rdoAV"));

		//set des booleens nécessaires à l'exécution de la requête sql en fonction du choix utilisateur
		switch (rdoAV) {
		case "encheresOuvertes":
			openAuction = true;
			break;
		case "mesEncheres":
			myAuction = true;
			break;
		case "encheresRemportees":
			winAuction = true;
			break;
		case "ventesEnCours":
			myCurrentSales = true;
			break;
		case "ventesNonDebutees":
			notSartedSales = true; 
			break;
		case "ventesTerminees":
			endedSales = true;
			break;
		default:
			openAuction = true;
			break;
		}
		// récupération de la liste d'Enchères en fonction des critères sélectionnés par l'utilisateur
		List<Article> listeEncheres = eMger.userSearchEncheres(research, userPseudo, idCategorie,
				openAuction, winAuction, myAuction, myCurrentSales, notSartedSales, endedSales);

		request.setAttribute("listeEncheres", listeEncheres);

		// set des attributs pour persistance des choix utilisateur
		request.setAttribute("selectedCategorie", categorie);
		request.setAttribute("search", research);
		request.setAttribute("openAuction", openAuction);
		request.setAttribute("myAuction", myAuction);
		request.setAttribute("winAuction", winAuction);
		request.setAttribute("myCurrentSales", myCurrentSales);
		request.setAttribute("notSartedSales", notSartedSales);
		request.setAttribute("endedSales", endedSales);
		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

}
