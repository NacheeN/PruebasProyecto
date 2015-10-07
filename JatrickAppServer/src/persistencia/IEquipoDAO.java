package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.Liga;
import dominio.Usuario;


@Local
public interface IEquipoDAO {
	
	public boolean guardarEquipo(Equipo equipo);	
	public Equipo buscarEquipo(String equipo);
	public List<Equipo> listarEquipos();
	public boolean borrarEquipo(Equipo equipo);
	public boolean asignarUsuario(Usuario u, Equipo equipo);
	public boolean guardarCambiosEquipo(Equipo equipo);
	boolean asignarLiga(Liga l, Equipo e);
	public List<Equipo> listarEquipos(Liga laliga);
	public  void cambiarCaptial(String nombreE, int i) ;
	public String darCiudad(String equipoName);
	public Double buscarCapital(String equipoName);
	public boolean existeEquipo(String equipoName);
	public List<String> darZona(String equipoName);
}
