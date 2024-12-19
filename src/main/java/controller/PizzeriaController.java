package controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import model.*;

@Controller
public class PizzeriaController {

    @Autowired
    private PizzaService pizzaService;

    @RequestMapping("/login")
    public ModelAndView login(String username, String password) {
        ModelAndView modelAndView = new ModelAndView();
        
        // Autenticazione dell'utente
        User user = pizzaService.authenticateUser(username, password);
        
        if (user != null) {
            // Se l'autenticazione ha successo, si redirige alla dashboard
            modelAndView.setViewName("dashboard.jsp");
            modelAndView.addObject("user", user); // Aggiungi l'oggetto user al modello
        } else {
            // Se l'autenticazione fallisce, ritorna alla pagina di login con errore
            modelAndView.setViewName("login.jsp");
            modelAndView.addObject("error", "Credenziali errate, riprova.");
        }
        
        return modelAndView;
    }
    
    @RequestMapping("/dashboard")
    public ModelAndView dashboard(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();

        // Recupera l'utente dalla sessione
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Se l'utente è autenticato, visualizza la dashboard
            modelAndView.setViewName("dashboard.jsp");
            modelAndView.addObject("user", user);
            modelAndView.addObject("pizze", pizzaService.getPizzeByUser(user.getId()));
            modelAndView.addObject("impasti", pizzaService.getAllImpasti());
            modelAndView.addObject("ingredienti", pizzaService.getAllIngredienti());
        } else {
            // Se l'utente non è autenticato, redirigi alla pagina di login
            modelAndView.setViewName("login.jsp");
            modelAndView.addObject("error", "Sessione scaduta, effettua il login.");
        }

        return modelAndView;
    }
}
