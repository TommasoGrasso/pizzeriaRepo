package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.*;
import repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PizzaService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private ImpastoRepository impastoRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    public User authenticateUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public void savePizza(String pizzaName, String impastoId, int userId, String[] ingredienti) {
        Impasto impasto = impastoRepository.findById(Long.parseLong(impastoId)).orElse(null);
        User user = userRepository.findById((long) userId).orElse(null);

        if (impasto != null && user != null) {
            Pizza pizza = new Pizza();
            pizza.setNome(pizzaName);
            pizza.setImpasto(impasto);
            pizza.setUser(user);

            // Aggiungiamo gli ingredienti
            for (String ingredienteId : ingredienti) {
                Ingrediente ingrediente = ingredienteRepository.findById(Long.parseLong(ingredienteId)).orElse(null);
                if (ingrediente != null) {
                    pizza.addIngrediente(ingrediente);
                }
            }

            pizzaRepository.save(pizza);
        } else {
            System.err.println("Errore: Impasto o Utente non trovato.");
        }
    }

    public List<Pizza> getPizzeByUser(int userId) {
        return pizzaRepository.findByUserId(userId);
    }

    public void deletePizzaById(Long id) {
        pizzaRepository.deleteById(id);
    }

    public void updatePizza(Long pizzaId, String nuovoNome, String nuovoImpastoId, String[] nuoviIngredienti) {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElse(null);
        if (pizza != null) {
            pizza.setNome(nuovoNome);

            Impasto nuovoImpasto = impastoRepository.findById(Long.parseLong(nuovoImpastoId)).orElse(null);
            if (nuovoImpasto != null) {
                pizza.setImpasto(nuovoImpasto);
            }

            if (nuoviIngredienti != null && nuoviIngredienti.length > 0) {
                pizza.getIngredienti().clear();
                for (String ingredienteId : nuoviIngredienti) {
                    Ingrediente ingrediente = ingredienteRepository.findById(Long.parseLong(ingredienteId)).orElse(null);
                    if (ingrediente != null) {
                        pizza.addIngrediente(ingrediente);
                    }
                }
            }

            pizzaRepository.save(pizza);
        } else {
            System.err.println("Pizza non trovata con ID: " + pizzaId);
        }
    }

    public List<Impasto> getAllImpasti() {
        List<Impasto> impastiList = new ArrayList<>();
        impastoRepository.findAll().forEach(impastiList::add); // Converte l'Iterable in una List
        return impastiList;
    }

    public Iterable<Ingrediente> getAllIngredienti() {
        return ingredienteRepository.findAll();
    }
}
