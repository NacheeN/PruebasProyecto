package dominio;

import java.io.Serializable;
import java.util.List;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "rol", nullable = false)
	private String rol;

	 @OneToMany(mappedBy="roles")
	 private List <Usuario> usuarios;
	 
	 
	
	public Roles() {

	}

	public Roles(int id, String rol) {
		this.id = id;
		this.rol = rol;
		
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setJugadores(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
