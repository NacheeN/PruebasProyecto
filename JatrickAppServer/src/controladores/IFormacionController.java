package controladores;

import java.util.List;

import javax.ejb.Local;

import dominio.Formacion;
import dominio.Jugador;

@Local
public interface IFormacionController {
	public List<Jugador> listarJugadoresSuplentes(int idFormacion);
	public List<Jugador> listarJugadoresDelanteros(int idFormacion);
	public int getIdFormacion(String nombreE);
	public List<Jugador> listarJugadoresMediocampistas(int idFormacion);
	public List<Jugador> listarJugadoresDefensas(int idFormacion);
	public List<Jugador> listarJugadorArquero(int idFormacion);
	public void modificarFormacion(Formacion f);
}
