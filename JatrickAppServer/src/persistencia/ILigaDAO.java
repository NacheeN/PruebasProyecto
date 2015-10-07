package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.EventosPartido;
import dominio.Liga;
import dominio.Partido;


@Local
public interface ILigaDAO {

	public boolean guardarLiga(Liga liga);
	public boolean existeLiga(Liga liga);
	public Liga getLiga(String nombreLiga);
	public List<Liga> listarLigas();
  //  public boolean asignarTemporada(Liga l, Temporada t);
	
	public Liga buscarLiga(String nombreLiga);
	public Integer darIDLiga(String nombreLiga);
	public void sumoEquipo(Liga laliga);
	public void guardarPartido(Partido partido);
	public int cantidadEquipos(Liga laliga);
	public Liga darLigaEq(String equipo);
	public List<Partido> traerResultados(String miLiga);
	public List<Partido> listarPartidos();
	public List<Equipo> darEquipos(String nombreLiga);
	public List<EventosPartido> buscarEventos(Integer idPartido);
	public Partido buscarPartido(Integer idPartido);
	public void modificarLiga(Liga laLiga);
	public List<Partido> traerPartidosFinalizados(String nombreLiga);
	
	
	
}
