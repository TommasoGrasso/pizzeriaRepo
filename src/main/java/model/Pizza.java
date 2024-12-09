package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "pizza")
@XmlRootElement(name = "pizza")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pizza {

    @Version
    private Integer version;
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
	@JoinTable(name = "pizza_ingrediente", joinColumns = @JoinColumn(name = "id_pizza"), inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
	private List<Ingrediente> ingredienti;

	public Pizza() {
	}

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
		if (this.ingredienti == null) {
			this.ingredienti = new ArrayList<>();
		}
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
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
