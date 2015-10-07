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

@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable {

	
	private static final long serialVersionUID = 1L; // Mapping JPA
	
	
	@Id
	@Column(name="id" , nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="nombreE")
	Equipo equipoReceptor;
	
	@Column(name="emisor" , nullable = false)
	String equipoEmisor;
	
	@Column(name="descripcion" , nullable = false)
	String descripcion;
	
	@Column(name="fecha" , nullable = false)
	String Date;

	public Mensaje( Equipo equipoReceptor, String equipoEmisor,
			String descripcion, String date) {
		super();
		
		this.equipoReceptor = equipoReceptor;
		this.equipoEmisor = equipoEmisor;
		this.descripcion = descripcion;
		Date = date;
	}

	public Mensaje(){}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Equipo getEquipoReceptor() {
		return equipoReceptor;
	}

	public void setEquipoReceptor(Equipo equipoReceptor) {
		this.equipoReceptor = equipoReceptor;
	}

	public String getEquipoEmisor() {
		return equipoEmisor;
	}

	public void setEquipoEmisor(String equipoEmisor) {
		this.equipoEmisor = equipoEmisor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}




}
