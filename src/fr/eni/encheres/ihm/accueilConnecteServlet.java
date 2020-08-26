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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if (("deconnexion").equals(action)) {
			request.getSession().removeAttribute("connectedUser");
			request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// récupération des paramètres
		String action = request.getParameter("rechercher");
		if (("rechercher").equals(action)) {
			String research = request.getParameter("search");
			Utilisateur connectedUser = (Utilisateur) request.getSession().getAttribute("connectedUser");
			String userPseudo = connectedUser.getPseudo();
			int idCategorie = Integer.parseInt(request.getParameter("selectedCategorie"));
			Boolean openAuction = false;
			Boolean myAuction = false;
			Boolean winAuction = false;
			Boolean myCurrentSales = false;
			Boolean notSartedSales = false;
			Boolean endedSales = false;
			String rdoAV = request.getParameter("rdoAV");

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
				break;
			}
			// récupération de la liste d'Enchères en fonction des critères sélectionnés par
			// l'utilisateur
			List<Article> listeEncheres = eMger.userSearchEncheres(research, userPseudo, idCategorie,
					openAuction, winAuction, myAuction, myCurrentSales, notSartedSales, endedSales);

			request.setAttribute("listeEncheres", listeEncheres);
			request.setAttribute("selectedCategorie", idCategorie);
			request.setAttribute("search", research);

			// set des boutons radios
			request.setAttribute("openAuction", openAuction);
			request.setAttribute("myAuction", myAuction);
			request.setAttribute("winAuction", winAuction);
			request.setAttribute("myCurrentSales", myCurrentSales);
			request.setAttribute("notSartedSales", notSartedSales);
			request.setAttribute("endedSales", endedSales);
		}

		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

}
