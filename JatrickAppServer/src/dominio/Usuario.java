package dominio;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@Column(name = "Nick", nullable = false)
	private String nick;

	@Column(name = "Nombre", nullable = false)
	private String nombre;

	@Column(name = "Mail", nullable = false)
	private String mail;

	@Column(name = "Password", nullable = false)
	// @NotNull
	private String password;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Equipo equipo;


	@Column(name = "partidojugando")
	private Integer partidojugando;
	
	@ManyToOne
	@JoinColumn(name="Roles")
	private Roles roles;
	
	@Column(name="EjecutandoPartido")
	boolean EjecutandoPartido;
	
	@Column(name="contadorEjecucion")
	private Integer ContadorEjecucion;
	
	@Column(name = "dorsal")
	private Integer dorsal;
	
	public boolean isEjecutandoPartido() {
		return EjecutandoPartido;
	}

	public void setEjecutandoPartido(boolean ejecutandoPartido) {
		EjecutandoPartido = ejecutandoPartido;
	}

	public Usuario() {

	}

	public Usuario(String nick, String password, String mail, String nombre) {
		this.nick = nick;
		this.password = password;
		this.mail = mail;
		this.nombre = nombre;
	}

	public Usuario(String nick, String password) {
		this.nick = nick;
		this.password = password;
	}

	public Usuario(Usuario p) {
		this.nick = p.getNick();
		this.password = p.getPassword();
		this.mail = p.getMail();
		this.nombre = p.getNombre();
	}

	public String getNombre() {
		return nombre;
	}
	

	public Integer getDorsal() {
		return dorsal;
	}

	public void setDorsal(Integer dorsal) {
		this.dorsal = dorsal;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public Integer getPartidojugando() {
		return partidojugando;
	}

	public void setPartidojugando(Integer partidojugando) {
		this.partidojugando = partidojugando;
	}

	public Integer getContadorEjecucion() {
		return ContadorEjecucion;
	}

	public void setContadorEjecucion(Integer contadorEjecucion) {
		ContadorEjecucion = contadorEjecucion;
	}
	
	
}
