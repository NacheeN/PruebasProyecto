package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "partido")
public class Partido implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="IdEquipo") 
	private Equipo equipo1;
	
	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="IdEquipo2") 
	private Equipo equipo2;

	@Column(name = "fecha", nullable = false)
	private Date fecha;
	
	
	@Column(name = "local", nullable = false)
	private String local;
	
	@Column(name = "golesL", nullable = false)
	private int golesL;
	
	@Column(name = "golesV", nullable = false)
	private int golesV;
	
	@Column(name = "finalizado", nullable = false)
	private boolean finalizado;

	@Column(name = "nombreLiga", nullable = false)
	private String nombreLiga;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Partido", fetch = FetchType.EAGER)
	List<EventosPartido> EventosPartido;
	
	public Partido(){
		
		//this.Comentarios = new ArrayList<String>() ;
	}
	
	
	public Partido(Equipo equipo1, Equipo equipo2, Date fecha,String local, String nombreLiga) {
		
		
		this.local=local;
		this.EventosPartido = new ArrayList<EventosPartido>() ;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
		this.fecha = fecha;
		this.golesL = 0;
		this.golesV = 0;
		this.finalizado = false;
		this.nombreLiga = nombreLiga;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<EventosPartido> getEventosPartido() {
		return EventosPartido;
	}

	public void setEventosPartido(List<EventosPartido> EventosPartido) {
		this.EventosPartido = EventosPartido;
	}

	public Equipo getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public String getLocal() {
		return local;
	}


	public void setLocal(String local) {
		this.local = local;
	}

	public void setNombreLiga(String nombreLiga) {
	this.nombreLiga = nombreLiga;
	}
	public void setGolesV(int golesV) {
		this.golesV = golesV;
	}
	public void setGolesL(int golesL) {
		this.golesL = golesL;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	public boolean isFinalizado() {
		return finalizado;
	}
	public String getNombreLiga() {
		return nombreLiga;
	}
	public int getGolesV() {
		return golesV;
	}
	public int getGolesL() {
		return golesL;
	}
	
	
	
	
	
}
