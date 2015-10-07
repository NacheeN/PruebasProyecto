package controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.google.gson.JsonElement;

import persistencia.ICiudadDAO;
import persistencia.IEquipoDAO;
import persistencia.IFormacionDAO;
import persistencia.IJugadorDAO;
import persistencia.IUsuarioDAO;
import dominio.Ciudad;
import dominio.Equipo;
import dominio.Formacion;
import dominio.Jugador;
import dominio.Usuario;

@Stateless
public class FormacionController implements IFormacionController {

	@EJB
	private IJugadorDAO jugadorDAO;
	@EJB
	private IFormacionDAO formacionDAO;

	public int getIdFormacion(String nombreE) {

		int id = formacionDAO.getIdFormacion(nombreE);
		return id;
	}

	public List<Jugador> listarJugadoresSuplentes(int idFormacion) {

		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {

			jugadores = jugadorDAO.listarJugadoresSuplentes(idFormacion);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public List<Jugador> listarJugadoresDelanteros(int idFormacion) {

		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {

			jugadores = jugadorDAO.listarJugadoresDelanteros(idFormacion);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public List<Jugador> listarJugadoresMediocampistas(int idFormacion) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {

			jugadores = jugadorDAO.listarJugadoresMediocampistas(idFormacion);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public List<Jugador> listarJugadoresDefensas(int idFormacion) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {

			jugadores = jugadorDAO.listarJugadoresDefensas(idFormacion);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public List<Jugador> listarJugadorArquero(int idFormacion) {
		List<Jugador> jugadores = new ArrayList<Jugador>();
		try {

			jugadores = jugadorDAO.listarJugadorArquero(idFormacion);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public void modificarFormacion(Formacion f) {

		try {
			System.out.println("FORMACION CONTROLLER " +f.getEquipo().getNombreE());
			
			for(Jugador j: f.getDefensas())
			{
				System.out.println(j.getNombreJ() + " // "+ j.getTipo() + " // " + j.getAtaque() + " // " + j.getEquipo().getNombreE());	

			}
			
			
			formacionDAO.modificarFormacion(f);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
