package controladores;

import java.util.List;

import javax.ejb.Local;

import dominio.Mensaje;
import dominio.Usuario;

@Local
public interface IUsuarioController {
	public boolean guardarUsuario(String nick, String Password, String mail, String nombre);
	public boolean existeUsuario(String nick, String password);
	public boolean tieneEquipo(String nick);
	public String darEquipo(String nick);
	public String darRol(String nick);
	public boolean enviarMensaje(String string, String string2, String string3);
	public List<Mensaje> recibirMensaje(String equipo);
	public boolean ejecutandoPartido(String nick);
	public Usuario buscarUsuario(String nick);
	public Integer darContadorEjecucion(String nick);
	public List<Usuario> buscarUsuariosEjecucion();
	public void modificarUsuario(Usuario u);
	public Integer darDorsal(String nick);
	public Integer darIdPartidoEjecutando(String nick);
}
