package DTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ingredienteDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class IngredienteDTO implements Serializable{
	
	private static final long serialVersionUID = -302543065901090808L;

	private int id;
	private String nome;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public IngredienteDTO(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	public IngredienteDTO() {
		super();
	}
	

}
