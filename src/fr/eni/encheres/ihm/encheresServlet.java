package fr.eni.encheres.ihm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class encheresServlet
 */
@WebServlet("/encheres")
public class encheresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In the doGet");
		String action = request.getParameter("action");
		RequestDispatcher rd = null;
		if(("inscription").equals(action)) {
			System.out.println("getparameter ok");
		rd = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/inscription.jsp");
		rd.forward(request, response);
		}
//		if (action != null) {
//			System.out.println("action ok");
//		}
//		RequestDispatcher rd=null;
//		rd = this.getServletContext().getNamedDispatcher("/WEB-INF/pages/accueil.jsp");
//		rd.forward(request, response);
//		if(("inscription").equals(action)) {
//			request.getRequestDispatcher("/WEB-INF/pages/inscription.jsp").forward(request, response);
//		}
//		else {
		request.getRequestDispatcher("/WEB-INF/pages/accueil.jsp").forward(request, response);
//	}
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
