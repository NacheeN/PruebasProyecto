package controladores;

import java.util.List;

import javax.ejb.Local;

import dominio.Ciudad;
import dominio.Equipo;
import dominio.Experiencia;
import dominio.Jugador;
import dominio.Liga;
import dominio.Partido;

@Local
public interface IEquipoController {
	
	
	public List<String> altaEquipo(Equipo equipo, String username);
	public List<Jugador> misJugadores(String equipo);
	public Equipo buscarEquipo(String equipo);
	public boolean venderJugadores(List<Jugador> jugadores);
	public List<Jugador> jugadoresAComp(String miEquipo);
	public boolean comprarJugadores(List<Jugador> jugadores,String equipo);
	public List<Experiencia> getJugadoresExperiencia(String miEquipo);
	public List<Equipo> listarEquipos();
	public boolean borrarEquipo(Equipo equipo);
	public boolean EntrenarEquipo(String equipo);
	public boolean setEntrenamiento(String entrenamiento,String equipo);
	public void asignarLiga(Liga laliga, Equipo team);
	public boolean simularPartido(Partido partido);
	public List<Equipo> listarEquipos(Liga laliga);
	public String darCiudad(String equipoName);
	public Double buscarCapital(String equipoName);	
	public boolean existeEquipo(String equipoName);
	public List<String> darZona(String equipoName);


}
