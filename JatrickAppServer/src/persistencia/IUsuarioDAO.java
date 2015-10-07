package persistencia;

import java.util.List;

import javax.ejb.Local;







import dominio.Equipo;
import dominio.Mensaje;
import dominio.Usuario;
import dominio.Mensaje;

@Local
public interface IUsuarioDAO {

public boolean guardarUsuario(Usuario usuario);	
public boolean existeUsuario(Usuario usuario);
public boolean tieneEquipo(String nick);
public Usuario getUsuario(String nick);
public boolean asignarEquipo(Usuario u, Equipo equipo);
public String darEquipo(String nick) ;
public String darol(String nick);
public boolean guardarMensaje(Mensaje mensaje1);
public List<Mensaje> getMensajes(String equipo);
public void EjecutandoPartido(Usuario manager, boolean b, Integer integer);
public boolean ejecutandoPartido(String nick);
public Integer darContadorEjecucion(String nick);
public List<Usuario> buscarUsuariosEjecucion();
public void modificarUsuario(Usuario u);
public Integer darDorsal(String nick);

}



 