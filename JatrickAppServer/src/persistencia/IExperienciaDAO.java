package persistencia;

import java.util.List;

import javax.ejb.Local;

import dominio.Experiencia;
import dominio.Jugador;


@Local
public interface IExperienciaDAO {

	public boolean guardarExperiencia(Experiencia Experiencia);
	
	public Experiencia getExperiencia(String nombrej);
	
	public boolean guardarCambiosExp(Experiencia e);
	
	public List<Experiencia> getJugadoresExperiencia(List<Jugador> jugadores);
	
}
