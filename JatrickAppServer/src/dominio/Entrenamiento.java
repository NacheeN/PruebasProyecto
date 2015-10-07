package dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import javax.persistence.Table;


@Entity
@Table(name = "entrenamiento")
public class Entrenamiento implements Serializable {
	
	
private static final long serialVersionUID = 1L; // Mapping JPA
	
	
	@Id
	@Column(name = "Nombre", nullable = false)
	private String nombre;
	
	@Column(name = "Descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "entrenamientoAtaque", nullable = false)
	private int entrenamientoAtaque;
	
	@Column(name = "entrenamientoDefensa", nullable = false)
	private int entrenamientoDefensa;
	
	@Column(name = "entrenamientoTiro", nullable = false)
	private int entrenamientoTiro;
	
	@Column(name = "entrenamientoPorteria", nullable = false)
	private int entrenamientoPorteria;
	

	public Entrenamiento(){
		
	}

	public Entrenamiento(String nombre,String descripcion, int entrenamientoAtaque,
			int entrenamientoDefensa, int entrenamientoTiro,
			int entrenamientoPorteria) {
		this.descripcion=descripcion;
		this.nombre = nombre;
		this.entrenamientoAtaque = entrenamientoAtaque;
		this.entrenamientoDefensa = entrenamientoDefensa;
		this.entrenamientoTiro = entrenamientoTiro;
		this.entrenamientoPorteria = entrenamientoPorteria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEntrenamientoAtaque() {
		return entrenamientoAtaque;
	}

	public void setEntrenamientoAtaque(int entrenamientoAtaque) {
		this.entrenamientoAtaque = entrenamientoAtaque;
	}

	public int getEntrenamientoDefensa() {
		return entrenamientoDefensa;
	}

	public void setEntrenamientoDefensa(int entrenamientoDefensa) {
		this.entrenamientoDefensa = entrenamientoDefensa;
	}

	public int getEntrenamientoTiro() {
		return entrenamientoTiro;
	}

	public void setEntrenamientoTiro(int entrenamientoTiro) {
		this.entrenamientoTiro = entrenamientoTiro;
	}

	public int getEntrenamientoPorteria() {
		return entrenamientoPorteria;
	}

	public void setEntrenamientoPorteria(int entrenamientoPorteria) {
		this.entrenamientoPorteria = entrenamientoPorteria;
	}
	

	

}
