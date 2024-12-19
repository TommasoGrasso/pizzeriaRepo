package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import model.*;

@Controller
public class PizzeriaController {

    @Autowired
    private PizzaService pizzaService;

    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        // Autenticazione dell'utente
        User user = pizzaService.authenticateUser(username, password);
        
        if (user != null) {
            session.setAttribute("user", user); // Store user in session
            model.addAttribute("user", user); 
            return "dashboard"; // Return the dashboard view
        } else {
            model.addAttribute("error", "Credenziali errate, riprova.");
            return "login"; // Return the login view
        }
    }
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("pizze", pizzaService.getPizzeByUser(user.getId()));
            model.addAttribute("impasti", pizzaService.getAllImpasti());
            model.addAttribute("ingredienti", pizzaService.getAllIngredienti());
            return "dashboard"; // Show the dashboard page
        } else {
            model.addAttribute("error", "Sessione scaduta, effettua il login.");
            return "login"; // Redirect to login if the session is expired
        }
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public String savePizza(@RequestParam String pizzaName, @RequestParam String impastoId, 
                            @RequestParam(value = "ingredienti", required = false) String[] ingredienti, 
                            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:login"; // Redirect to login if session is invalid
        }
        
        if (impastoId == null || ingredienti == null || ingredienti.length == 0) {
            model.addAttribute("avviso", "Riempire ingredienti e impasto per salvare la pizza.");
            model.addAttribute("impasti", pizzaService.getAllImpasti());
            model.addAttribute("ingredienti", pizzaService.getAllIngredienti());
            model.addAttribute("pizzeUtente", pizzaService.getPizzeByUser(user.getId()));
            return "dashboard"; // Show the dashboard with the warning
        }
        
        pizzaService.savePizza(pizzaName, impastoId, user.getId(), ingredienti);
        model.addAttribute("impasti", pizzaService.getAllImpasti());
        model.addAttribute("ingredienti", pizzaService.getAllIngredienti());
        model.addAttribute("pizzeUtente", pizzaService.getPizzeByUser(user.getId()));
        
        return "dashboard"; // Show the updated dashboard
    }
}
