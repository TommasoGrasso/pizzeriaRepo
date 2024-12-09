package controller;

import java.io.IOException;

import dao.PizzaDao;

import javax.ejb.EJB;
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
    
    @EJB 
    private PizzaDao pizzaDao;  
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("impasti", pizzaDao.getAllImpasti());
        request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
        }
        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }
        
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            Long pizzaId = Long.parseLong(request.getParameter("pizzaId"));
            pizzaDao.deletePizzaById(pizzaId);
            
            request.setAttribute("impasti", pizzaDao.getAllImpasti());
            request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
            request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
            
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }
        
        if ("update".equals(action)) {
            Long pizzaId = Long.parseLong(request.getParameter("pizzaId"));
            String nuovoNome = request.getParameter("pizzaName");
            String nuovoImpastoId = request.getParameter("impasto");
            String[] nuoviIngredienti = request.getParameterValues("ingredienti");

            pizzaDao.updatePizza(pizzaId, nuovoNome, nuovoImpastoId, nuoviIngredienti);

            request.setAttribute("impasti", pizzaDao.getAllImpasti());
            request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
            request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));

            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
            return;
        }

        String pizzaName = request.getParameter("pizzaName");
        String impastoId = request.getParameter("impasto");
        String[] ingredienti = request.getParameterValues("ingredienti");
        
        if (impastoId == null || ingredienti == null || ingredienti.length == 0) {
            request.setAttribute("avviso", "Riempire ingredienti e impasto per salvare la pizza.");
            request.setAttribute("impasti", pizzaDao.getAllImpasti());
            request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
            request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
            
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        }

        pizzaDao.savePizza(pizzaName, impastoId, user.getId(), ingredienti);

        request.setAttribute("impasti", pizzaDao.getAllImpasti());
        request.setAttribute("ingredienti", pizzaDao.getAllIngredienti());
        request.setAttribute("pizzeUtente", pizzaDao.getPizzeByUser(user.getId()));
        
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }

}