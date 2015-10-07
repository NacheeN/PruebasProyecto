package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "liga")
public class Liga implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

/*	@Id
	@Column(name="id" , nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
*/
	@Id
	@Column(name = "NombreLiga", nullable = false)
	private String nombreLiga;
	
	@Column(name = "Finalizado", nullable = false)
	private boolean finalizado;
	
	@Column(name = "nroEq", nullable = false)
	private int nroEq;

	@OneToMany(targetEntity = Equipo.class, mappedBy = "ciudad", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Equipo> equipos;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaInicio", nullable = false)
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaFinalizado", nullable = false)
	private Date fechaFinalizado;
	
	
	
/*	
	@OneToMany
	@JoinColumn(name="equipo")
	private List<Equipo> equipo;
	
	 
	public List<Equipo> getEquipo() {
		return equipo;
	}
	
	public void setEquipo(List<Equipo> equipo) {
		this.equipo = equipo;
	}
*/
/*	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
*/
	public Liga() {

	}

	public Liga(String nombreLiga) {
		this.nombreLiga = nombreLiga;

	}

	public Liga(Liga l) {
		this.nombreLiga = l.getNombreLiga();

	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFinalizado() {
		return fechaFinalizado;
	}

	public void setFechaFinalizado(Date fechaFinalizado) {
		this.fechaFinalizado = fechaFinalizado;
	}

	public String getNombreLiga() {
		return nombreLiga;
	}

	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}
	public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	public void setNroEq(int nroEq) {
		this.nroEq = nroEq;
	}
	public int getNroEq() {
		return nroEq;
	}

}
