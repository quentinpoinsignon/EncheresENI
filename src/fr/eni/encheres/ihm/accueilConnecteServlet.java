package fr.eni.encheres.ihm;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.encheres.bll.EnchereManager;
import fr.eni.encheres.bo.Enchere;
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
			Boolean shoppingChecked = null;
			Boolean openAuction = null;
			Boolean myAuction = null;
			Boolean winAuction = null;
			Boolean mySales = null;
			Boolean myCurrentSales = null;
			Boolean notSartedSales = null;
			Boolean endedSales = null;
			String rdoSelectAchatsVentes = request.getParameter("rdoSelectAchatsVentes");
			String rdoVentes = request.getParameter("rdoVentes");
			String rdoAchats = request.getParameter("rdoAchats");

			switch (rdoSelectAchatsVentes) {
			case "achats":
				// everything ventes à false
				mySales = false;
				myCurrentSales = false;
				notSartedSales = false;
				endedSales = false;
				// traitement radios achats
				shoppingChecked = true;
				switch (rdoAchats) {
				case "encheresOuvertes":
					openAuction = true;
					myAuction = false;
					winAuction = false;
					break;
				case "mesEncheres":
					openAuction = false;
					myAuction = true;
					winAuction = false;
					break;
				case "encheresRemportees":
					openAuction = false;
					myAuction = false;
					winAuction = true;
					break;
				default:
					break;
				}
				break;
			case "ventes":
				// everything achats à false
				shoppingChecked = false;
				openAuction = false;
				myAuction = false;
				winAuction = false;
				// traitement radios ventes
				mySales = true;
				switch (rdoVentes) {
				case "ventesEnCours":
					myCurrentSales = true;
					notSartedSales = false;
					endedSales = false;
					break;
				case "ventesNonDebutees":
					myCurrentSales = false;
					notSartedSales = true;
					endedSales = false;
					break;
				case "ventesTerminees":
					myCurrentSales = false;
					notSartedSales = false;
					endedSales = true;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}
			// récupération de la liste d'Enchères en fonction des critères sélectionnés par
			// l'utilisateur
			List<Enchere> listeEncheres = eMger.userSearchEncheres(research, userPseudo, idCategorie, shoppingChecked,
					openAuction, winAuction, myAuction, mySales, myCurrentSales, notSartedSales, endedSales);

			request.setAttribute("listeEncheres", listeEncheres);
			request.setAttribute("selectedCategorie", idCategorie);
			request.setAttribute("search", research);

			// set des boutons radios
			request.setAttribute("shoppingChecked", shoppingChecked);
			request.setAttribute("openAuction", openAuction);
			request.setAttribute("myAuction", myAuction);
			request.setAttribute("winAuction", winAuction);
			request.setAttribute("mySales", mySales);
			request.setAttribute("myCurrentSales", myCurrentSales);
			request.setAttribute("notSartedSales", notSartedSales);
			request.setAttribute("endedSales", endedSales);
		}

		request.getRequestDispatcher("/WEB-INF/pages/accueilConnecte.jsp").forward(request, response);
	}

}
