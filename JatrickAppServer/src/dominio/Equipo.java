package dominio;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;






@Entity
@Table(name = "equipo")
public class Equipo implements Serializable {
	
	
private static final long serialVersionUID = 1L; // Mapping JPA
	
	
	@Id
	@Column(name = "Nombre", nullable = false)
	private String nombreE;
		
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="Ciudad")
	private Ciudad ciudad;
	
	@Column(name = "Capital", nullable = false)
	private double capital;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Formacion formacion;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Usuario manager;
	
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "equipo", fetch = FetchType.LAZY)
	private List<Jugador> jugadores;
	
	@ManyToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Entrenamiento entrenamiento;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="liga")
	private Liga liga;

	public Equipo() {
	
	}
	
	public Equipo(String nombreE,double capital,Ciudad ciudad) {
		this.nombreE = nombreE;
		this.ciudad = ciudad;
		this.capital = capital;
	}
	
   public Equipo(Equipo e) {
	this.nombreE = e.getNombreE();
	this.ciudad = e.getCiudad();
	this.capital = e.getCapital();
	}

public String getNombreE() {
	return nombreE;
}

public void setNombreE(String nombreE) {
	this.nombreE = nombreE;
}

public double getCapital() {
	return capital;
}

public void setCapital(double capital) {
	this.capital = capital;
}

public Usuario getManager() {
	return manager;
}

public void setManager(Usuario manager) {
	this.manager = manager;
}

public Ciudad getCiudad() {
	return ciudad;
}

public void setCiudad(Ciudad c) {
	this.ciudad = c;
}

public List<Jugador> getJugadores() {
	return jugadores;
}

public void setJugadores(List<Jugador> jugadores) {
	this.jugadores = jugadores;
}

public Entrenamiento getEntrenamiento() {
	return entrenamiento;
}

public void setEntrenamiento(Entrenamiento entrenamiento) {
	this.entrenamiento = entrenamiento;
}
public Formacion getFormacion() {
	return formacion;
}

public void setFormacion(Formacion formacion) {
	this.formacion = formacion;
}
public Liga getLiga() {
	return liga;
}

public void setLiga(Liga liga) {
	this.liga = liga;
}

}
