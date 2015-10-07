package persistencia;



import java.util.List;

import javax.ejb.Local;

import dominio.Equipo;
import dominio.Jugador;



@Local
public interface IJugadorDAO {

public boolean guardarJugador(Jugador jugador);	
public List<Jugador> asignarJugadores(Equipo equipo);
public List<Jugador> misJugadores(Equipo miEquipo);
public boolean venderJugadores(List<Jugador> jugadores);
public List<Jugador> jugadoresAComp(String miEquipo);
public boolean comprarJugadores(List<Jugador> jugadores,Equipo equipo);
public void guardarCambiosJugador(Jugador j);
public List<Jugador> listarJugadoresSuplentes(int idFormacion);
public List<Jugador> listarJugadoresDelanteros(int idFormacion);
public List<Jugador> listarJugadoresMediocampistas(int idFormacion);
public List<Jugador> listarJugadoresDefensas(int idFormacion);
public List<Jugador> listarJugadorArquero(int idFormacion);
}