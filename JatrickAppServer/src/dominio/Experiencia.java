package dominio;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name = "experiencia")
public class Experiencia implements Serializable {
	
	private static final long serialVersionUID = 1L; // Mapping JPA
	

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ExpAtaque", nullable = false)
	private Integer ExpAtaque;
	
	@Column(name = "ExpTiro", nullable = false)
	private Integer ExpTiro;
	
	@Column(name = "ExpDefensa", nullable = false)
	private Integer ExpDefensa;
	
	@Column(name = "ExpPorteria", nullable = false)
	private Integer ExpPorteria;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Jugador jugador;
	
	public Experiencia() {
		
	}
	public Experiencia(Integer expAtaque,Integer ExpTiro, Integer ExpDefensa,Integer ExpPorteria) {
		this.ExpAtaque=expAtaque;
		this.ExpDefensa=ExpDefensa;
		this.ExpPorteria=ExpPorteria;
		this.ExpTiro=ExpTiro;
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public Integer getExpAtaque() {
		return ExpAtaque;
	}

	public void setExpAtaque(Integer expAtaque) {
		ExpAtaque = expAtaque;
	}

	public Integer getExpTiro() {
		return ExpTiro;
	}

	public void setExpTiro(Integer expTiro) {
		ExpTiro = expTiro;
	}

	public Integer getExpDefensa() {
		return ExpDefensa;
	}

	public void setExpDefensa(Integer expDefensa) {
		ExpDefensa = expDefensa;
	}

	public Integer getExpPorteria() {
		return ExpPorteria;
	}

	public void setExpPorteria(Integer expPorteria) {
		ExpPorteria = expPorteria;
	}

	
	
}
