package dominio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ciudad")
public class Ciudad implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "Ciudad", nullable = false)
	private String ciudad;

	@Column(name = "Zona", nullable = false)
	private String zonaGeografica;

	@Column(name = "Altura", nullable = false)
	private int altura;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "ciudad", fetch = FetchType.LAZY)
	private List<Equipo> equipos;

	public Ciudad() {

	}

	public Ciudad(String ciudad, String zonaGeografica) {
		this.ciudad = ciudad;
		this.zonaGeografica = zonaGeografica;
	}

	public Ciudad(Ciudad c) {
		this.ciudad = c.getCiudad();
		this.zonaGeografica = c.getZonaGeografica();
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getZonaGeografica() {
		return zonaGeografica;
	}

	public void setZonaGeografica(String zonaGeografica) {
		this.zonaGeografica = zonaGeografica;
	}

	public List<Equipo> getEquipo() {
		return equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public List<Equipo> getEquipos() {
		return equipos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
