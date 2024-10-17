package controller;

import java.io.IOException;

import dao.PizzaDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PizzaDao dao = new PizzaDao();
		request.setAttribute("impasti", dao.getAllImpasti());
		request.setAttribute("ingredienti", dao.getAllIngredienti());
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			request.setAttribute("pizzeUtente", dao.getPizzeByUser(user.getId()));
		}
		
		
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		PizzaDao dao = new PizzaDao();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.jsp"); 
			return;
		}
		
	    String action = request.getParameter("action");
	    if ("delete".equals(action)) {
	        Long pizzaId = Long.parseLong(request.getParameter("pizzaId"));
	        dao.deletePizzaById(pizzaId);
	       
	        request.setAttribute("impasti", dao.getAllImpasti());
	        request.setAttribute("ingredienti", dao.getAllIngredienti());
	        request.setAttribute("pizzeUtente", dao.getPizzeByUser(user.getId()));
	        
	        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	        return;
	    }
	    
	    if ("update".equals(action)) {
	        Long pizzaId = Long.parseLong(request.getParameter("pizzaId"));
	        String nuovoNome = request.getParameter("pizzaName");
	        String nuovoImpastoId = request.getParameter("impasto");
	        String[] nuoviIngredienti = request.getParameterValues("ingredienti");

	        // Chiamata al metodo per aggiornare la pizza
	        dao.updatePizza(pizzaId, nuovoNome, nuovoImpastoId, nuoviIngredienti);

	        // Ricarica le pizze dell'utente
	        request.setAttribute("impasti", dao.getAllImpasti());
	        request.setAttribute("ingredienti", dao.getAllIngredienti());
	        request.setAttribute("pizzeUtente", dao.getPizzeByUser(user.getId()));

	        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	        return;
	    }


		String pizzaName = request.getParameter("pizzaName");
		String impastoId = request.getParameter("impasto");
		String[] ingredienti = request.getParameterValues("ingredienti");
		
		
		if (impastoId == null || ingredienti == null || ingredienti.length == 0) {
			request.setAttribute("avviso", "Riempire ingredienti e impasto per salvare la pizza.");
			request.setAttribute("impasti", dao.getAllImpasti());
			request.setAttribute("ingredienti", dao.getAllIngredienti());
			
			request.setAttribute("pizzeUtente", dao.getPizzeByUser(user.getId()));
			
			request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
			return;
		}

		dao.savePizza(pizzaName, impastoId, user.getId(), ingredienti);

		request.setAttribute("impasti", dao.getAllImpasti());
		request.setAttribute("ingredienti", dao.getAllIngredienti());

		request.setAttribute("pizzeUtente", dao.getPizzeByUser(user.getId()));
		
		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);

	}

}


//@WebServlet("/dashboard")
//public class DashboardServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		PizzaDao pizzaDao = new PizzaDao();
//
//		// Recupera impasti e ingredienti
//		request.setAttribute("impasti", pizzaDao.getAllImpasti());
//		request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
//
//		// Recupera le pizze dell'utente
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//
//		if (user != null) {
//			request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
//		}
//
//		// Forward alla pagina JSP
//		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//
//		if (user == null) {
//			response.sendRedirect("login.jsp");
//			return;
//		}
//
//		// Recupera i dati inviati dal form
//		String pizzaName = request.getParameter("pizzaName");
//		String impastoId = request.getParameter("impasto");
//		String[] ingredienti = request.getParameterValues("ingredienti");
//
//		// Verifica che impasto e ingredienti siano stati selezionati
//		if (impastoId == null || ingredienti == null || ingredienti.length == 0) {
//			request.setAttribute("avviso", "Riempire ingredienti e impasto per salvare la pizza.");
//			
//			PizzaDao pizzaDao = new PizzaDao();
//			request.setAttribute("impasti", pizzaDao.getAllImpasti());
//			request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
//
//			// Recupera le pizze dell'utente anche in caso di errore
//			request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
//
//			request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
//			return;
//		}
//
//		// Salva la pizza
//		PizzaDao pizzaDao = new PizzaDao();
//		pizzaDao.savePizza(pizzaName, impastoId, user.getId(), ingredienti);
//
//		// Aggiorna impasti, ingredienti e pizze dell'utente
//		request.setAttribute("impasti", pizzaDao.getAllImpasti());
//		request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
//		request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
//
//		// Forward alla pagina JSP
//		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
//	}
//}
