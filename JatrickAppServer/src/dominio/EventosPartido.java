package dominio;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.cfg.context.Cascadable;

@Entity
@Table(name = "EventosPartido")
public class EventosPartido implements Serializable{
	
	private static final long serialVersionUID = 1L; // Mapping JPA
	
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(name = "Evento")
	private String Evento;
	
	@Column(name = "Descripcion")
	private String Descripcion;

	@Column(name = "EsGol")
	private String esgol;
	
	@Column(name = "FactorAleatorio1")
	private String FactorAleatorio1;
	
	@Column(name = "FactorAleatorio2")
	private String FactorAleatorio2;
	
	@Column(name = "Resultado")
	private String Resultado;
	
	@ManyToOne(optional=true,fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name="Partido",nullable = true)
	private Partido Partido;
	
	

	public Partido getPartido() {
		return Partido;
	}
	public void setPartido(Partido partido) {
		Partido = partido;
	}
	public EventosPartido(){}
	public EventosPartido(String evento, String descripcion,
			String factorAleatorio1, String factorAleatorio2, String resultado) {
		super();
		Evento = evento;
		Descripcion = descripcion;
		FactorAleatorio1 = factorAleatorio1;
		FactorAleatorio2 = factorAleatorio2;
		Resultado = resultado;
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEvento() {
		return Evento;
	}
	public void setEvento(String evento) {
		Evento = evento;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getFactorAleatorio1() {
		return FactorAleatorio1;
	}
	public void setFactorAleatorio1(String factorAleatorio1) {
		FactorAleatorio1 = factorAleatorio1;
	}
	public String getFactorAleatorio2() {
		return FactorAleatorio2;
	}
	public void setFactorAleatorio2(String factorAleatorio2) {
		FactorAleatorio2 = factorAleatorio2;
	}
	public String getResultado() {
		return Resultado;
	}
	public void setResultado(String resultado) {
		Resultado = resultado;
	}
	public String getEsgol() {
		return esgol;
	}
	public void setEsgol(String esgol) {
		this.esgol = esgol;
	}
	
	
	
}
