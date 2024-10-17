package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_impasto", nullable = false)
    private Impasto impasto;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pizza_ingrediente",
        joinColumns = @JoinColumn(name = "id_pizza"),
        inverseJoinColumns = @JoinColumn(name = "id_ingrediente")
    )
    private List<Ingrediente> ingredienti;


    public Pizza() {}

    public Pizza(String nome, Impasto impasto, User user, List<Ingrediente> ingredienti) {
        this.nome = nome;
        this.impasto = impasto;
        this.user = user;
        this.ingredienti = ingredienti;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Impasto getImpasto() {
        return impasto;
    }

    public void setImpasto(Impasto impasto) {
        this.impasto = impasto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(List<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }
    
    public void addIngrediente(Ingrediente ingrediente) {
        if (this.ingredienti == null) {
            this.ingredienti = new ArrayList<>();
        }
        this.ingredienti.add(ingrediente);
    }

    public void removeIngrediente(Ingrediente ingrediente) {
        if (this.ingredienti != null) {
            this.ingredienti.remove(ingrediente);
        }
    }
}
