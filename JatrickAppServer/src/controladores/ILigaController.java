package controladores;

import java.util.List;

import javax.ejb.Local;

import dominio.EventosPartido;
import dominio.Liga;
import dominio.Equipo;
import dominio.Partido;

@Local
public interface ILigaController {
	public boolean guardarLiga(String nombreLiga);
	public boolean existeLiga(String nombreLiga);
	public List<Liga> listarLigas();
	public boolean unirseLiga(Liga liga, String Eqnombre);
	public Liga darLiga(String nombreLiga);
	public Integer darIdLiga(String nombreLiga);
	public void sumarEquipo(Liga laliga);
	public void crearFixture(Equipo Eq1, Equipo Eq2, Equipo Eq3, Equipo Eq4);
	public int cantidadEquipos(Liga laliga);
	public Liga darLigaEq(String equipo);
	public List<Partido> resultados(String miLiga);
	public List<Partido> listarPartidos();
	public List<EventosPartido> buscarEventos(Integer idPartido);
	public Partido buscarPartidoEjectuando(String nick);
	public void ganadorCampeonato(Liga laLiga);
	public void partidoAmistoso(String equipo);	
	public List<Equipo> darEquiposLiga(String nombreLiga);
	public List<Partido> partidosFinalizados(String nombreLiga);
		
	
}
