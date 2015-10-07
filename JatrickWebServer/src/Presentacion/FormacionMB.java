package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.primefaces.event.DragDropEvent;

import Wrappers.WrapperEquipo;
import Wrappers.WrapperFormacion;
import Wrappers.WrapperJugador;
import Wrappers.WrapperLiga;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@ManagedBean
@javax.faces.bean.SessionScoped
public class FormacionMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<WrapperJugador> delanteros = new ArrayList();
	private List<WrapperJugador> mediocampistas = new ArrayList();
	private List<WrapperJugador> defensas = new ArrayList();
	private List<WrapperJugador> suplentes = new ArrayList();
	private List<WrapperJugador> arquero = new ArrayList();

	// /// Para mantener en memoria los cambios
	private List<String> delanterosAux = new ArrayList();
	private List<String> mediocampistasAux = new ArrayList();
	private List<String> defensasAux = new ArrayList();
	private List<String> suplentesAux = new ArrayList();
	private List<String> arqueroAux = new ArrayList();

	@PostConstruct
	public void iniciar() {

		this.suplentes = getJugadoresSuplentes();
		this.delanteros = getJugadoresDelanteros();
		this.mediocampistas = getJugadoresMediocampistas();
		this.defensas = getJugadoresDefensas();
		this.arquero = getJugadorArquero();

		for (WrapperJugador j : this.suplentes) {
			if (!(this.suplentesAux.contains(j.getNombreJ())))
				this.suplentesAux.add(j.getNombreJ());

		}

		for (WrapperJugador j : this.delanteros) {
			if (!(this.suplentesAux.contains(j.getNombreJ())))
				this.delanterosAux.add(j.getNombreJ());

		}

		for (WrapperJugador j : this.mediocampistas) {
			if (!(this.mediocampistasAux.contains(j.getNombreJ())))
				this.mediocampistasAux.add(j.getNombreJ());

		}

		for (WrapperJugador j : this.defensas) {
			if (!(this.defensasAux.contains(j.getNombreJ())))
				this.defensasAux.add(j.getNombreJ());

		}

		for (WrapperJugador j : this.arquero) {
			if (!(this.arqueroAux.contains(j.getNombreJ())))
				this.arqueroAux.add(j.getNombreJ());

		}

		/*
		 * System.out.println("Estoy en Post Construct");
		 * System.out.println(this.delanterosAux.toString());
		 */
	}

	public String modificarFormacion() {

		// System.out.println("Estoy en modificar formacion");

		List<WrapperJugador> delanteros = new ArrayList();
		List<WrapperJugador> mediocampistas = new ArrayList();
		List<WrapperJugador> defensas = new ArrayList();
		List<WrapperJugador> suplentes = new ArrayList();
		List<WrapperJugador> arquero = new ArrayList();

		// / Recorro las listas temporales para enviar a guardar la formacion

		// / Arquero
		for (String j : this.arqueroAux) {

			for (WrapperJugador jugador : this.delanteros) {
				if (jugador.getNombreJ().equals(j)) {
					arquero.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.mediocampistas) {
				if (jugador.getNombreJ().equals(j)) {
					arquero.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.defensas) {
				if (jugador.getNombreJ().equals(j)) {
					arquero.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.arquero) {
				if (jugador.getNombreJ().equals(j)) {
					arquero.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.suplentes) {
				if (jugador.getNombreJ().equals(j)) {
					arquero.add(jugador);

				}
			}

		}

		// / Defensas
		for (String j : this.defensasAux) {

			for (WrapperJugador jugador : this.delanteros) {
				if (jugador.getNombreJ().equals(j)) {
					defensas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.mediocampistas) {
				if (jugador.getNombreJ().equals(j)) {
					defensas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.defensas) {
				if (jugador.getNombreJ().equals(j)) {
					defensas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.arquero) {
				if (jugador.getNombreJ().equals(j)) {
					defensas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.suplentes) {
				if (jugador.getNombreJ().equals(j)) {
					defensas.add(jugador);

				}
			}

		}

		// / Mediocampistas
		for (String j : this.mediocampistasAux) {

			for (WrapperJugador jugador : this.delanteros) {
				if (jugador.getNombreJ().equals(j)) {
					mediocampistas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.mediocampistas) {
				if (jugador.getNombreJ().equals(j)) {
					mediocampistas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.defensas) {
				if (jugador.getNombreJ().equals(j)) {
					mediocampistas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.arquero) {
				if (jugador.getNombreJ().equals(j)) {
					mediocampistas.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.suplentes) {
				if (jugador.getNombreJ().equals(j)) {
					mediocampistas.add(jugador);

				}
			}

		}

		// / Delanteros
		for (String j : this.delanterosAux) {

			for (WrapperJugador jugador : this.delanteros) {
				if (jugador.getNombreJ().equals(j)) {
					delanteros.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.mediocampistas) {
				if (jugador.getNombreJ().equals(j)) {
					delanteros.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.defensas) {
				if (jugador.getNombreJ().equals(j)) {
					delanteros.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.arquero) {
				if (jugador.getNombreJ().equals(j)) {
					delanteros.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.suplentes) {
				if (jugador.getNombreJ().equals(j)) {
					delanteros.add(jugador);

				}
			}

		}

		// / Suplentes
		for (String j : this.suplentesAux) {

			for (WrapperJugador jugador : this.delanteros) {
				if (jugador.getNombreJ().equals(j)) {
					suplentes.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.mediocampistas) {
				if (jugador.getNombreJ().equals(j)) {
					suplentes.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.defensas) {
				if (jugador.getNombreJ().equals(j)) {
					suplentes.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.arquero) {
				if (jugador.getNombreJ().equals(j)) {
					suplentes.add(jugador);

				}
			}

			for (WrapperJugador jugador : this.suplentes) {
				if (jugador.getNombreJ().equals(j)) {
					suplentes.add(jugador);

				}
			}

		}

		
		WrapperFormacion f = new WrapperFormacion();
		WrapperEquipo e = new WrapperEquipo();

		// Pido el nombre del equipo y lo seteo
		// String equipo = (String)
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		e.setNombreE(nombreEquipo);

		f.setArquero(arquero);
		f.setDefensas(defensas);
		f.setDelanteros(delanteros);
		f.setEquipo(e);
		f.setMediocampistas(mediocampistas);
		f.setSuplentes(suplentes);

		ClientRequest request;

		try {

			request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/FormacionService/modificarFormacion");

			String formacionJSON = toJSONString(f);

			System.out.println(formacionJSON);

			request.body("application/json", formacionJSON);

			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Error al modificar la Formacion"));
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}
			
			//// Actualizo los datos			
			this.suplentes = getJugadoresSuplentes();
			this.delanteros = getJugadoresDelanteros();
			this.mediocampistas = getJugadoresMediocampistas();
			this.defensas = getJugadoresDefensas();
			this.arquero = getJugadorArquero();

			

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Se modifico la formacion con exito"));

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return null;
	}

	public String addDelantero() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String nombreJ = (String) map.get("nombre");

		String jugador = "";
		String tipoDesde = "";

		/*
		 * System.out.println("Estoy en addDelantero()");
		 * System.out.println(this.delanterosAux.toString());
		 */

		// Para ver de que columna proviene el jugador, tengo que recorrer todas las listas
		for (String j : this.suplentesAux) {

			if (j.equals(nombreJ)) {
				
				jugador = j;	
				tipoDesde = "Suplente";
			
			}
		
		}

		for (String j : this.arqueroAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Arquero";

			}
		}

		for (String j : this.defensasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Defensa";

			}
		}

		for (String j : this.mediocampistasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Mediocampista";

			}
		}

		switch (tipoDesde) {

		case "Suplente":
			this.suplentesAux.remove(jugador);
			break;

		case "Delantero":
			break;

		case "Mediocampista":
			this.mediocampistasAux.remove(jugador);
			break;

		case "Defensa":
			this.defensasAux.remove(jugador);
			break;

		case "Arquero":
			this.arqueroAux.remove(jugador);
			break;

		default:
			break;
		}

		this.delanterosAux.add(jugador);

		/*
		 * System.out.println(this.delanterosAux.toString());
		 * System.out.println(this.delanteros.toString());
		 * System.out.println(this.suplentesAux.toString());
		 * System.out.println(this.suplentes.toString());
		 */

		return null;
	}

	public String addSuplente() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String nombreJ = (String) map.get("nombre");

		String jugador = "";
		String tipoDesde = "";

		// Para ver de que columna proviene el jugador
		for (String j : this.delanterosAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Delantero";

			}
		}

		for (String j : this.arqueroAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Arquero";

			}
		}

		for (String j : this.defensasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Defensa";

			}
		}

		for (String j : this.mediocampistasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Mediocampista";

			}
		}

		switch (tipoDesde) {

		case "Suplente":
			break;

		case "Delantero":
			this.delanterosAux.remove(jugador);
			break;

		case "Mediocampista":
			this.mediocampistasAux.remove(jugador);
			break;

		case "Defensa":
			this.defensasAux.remove(jugador);
			break;

		case "Arquero":
			this.arqueroAux.remove(jugador);
			break;

		default:
			break;
		}

		this.suplentesAux.add(jugador);
		return null;
	}

	public String addMediocampista() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String nombreJ = (String) map.get("nombre");

		String jugador = "";
		String tipoDesde = "";

		for (String j : this.delanterosAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Delantero";

			}
		}

		for (String j : this.arqueroAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Arquero";

			}
		}

		for (String j : this.defensasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Defensa";

			}
		}

		for (String j : this.suplentesAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Suplente";

			}
		}

		switch (tipoDesde) {

		case "Suplente":
			this.suplentesAux.remove(jugador);
			break;

		case "Delantero":
			this.delanterosAux.remove(jugador);
			break;

		case "Mediocampista":
			//this.mediocampistasAux.remove(jugador);
			break;

		case "Defensa":
			this.defensasAux.remove(jugador);
			break;

		case "Arquero":
			this.arqueroAux.remove(jugador);
			break;

		default:
			break;
		}

		this.mediocampistasAux.add(jugador);

		return null;
	}

	public String addDefensa() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String nombreJ = (String) map.get("nombre");

		String jugador = "";
		String tipoDesde = "";
			
		for (String j : this.delanterosAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Delantero";

			}
		}

		for (String j : this.arqueroAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Arquero";

			}
		}

		for (String j : this.mediocampistasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Mediocampista";

			}
		}

		for (String j : this.suplentesAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Suplente";

			}
		}

		switch (tipoDesde) {

		case "Suplente":
			this.suplentesAux.remove(jugador);
			break;

		case "Delantero":
			this.delanterosAux.remove(jugador);
			break;

		case "Mediocampista":
			this.mediocampistasAux.remove(jugador);
			break;

		case "Defensa":
			break;

		case "Arquero":
			this.arqueroAux.remove(jugador);
			break;

		default:
			break;
		}

		System.out.println(suplentesAux);
		
		
		this.defensasAux.add(jugador);
		System.out.println(defensasAux);
		
		return null;
	}

	public String addArquero() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map map = context.getExternalContext().getRequestParameterMap();
		String nombreJ = (String) map.get("nombre");

		String jugador = "";
		String tipoDesde = "";

		for (String j : this.delanterosAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Delantero";

			}
		}

		for (String j : this.defensasAux){

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Defensa";

			}
		}

		for (String j : this.mediocampistasAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Mediocampista";

			}
		}

		for (String j : this.suplentesAux) {

			if (j.equals(nombreJ)) {
				jugador = j;
				tipoDesde = "Suplente";

			}
		}

		switch (tipoDesde) {

		case "Suplente":
			this.suplentesAux.remove(jugador);
			break;

		case "Delantero":
			this.delanterosAux.remove(jugador);
			break;

		case "Mediocampista":
			this.mediocampistasAux.remove(jugador);
			break;

		case "Defensa":
			this.defensasAux.remove(jugador);
			break;

		case "Arquero":
			break;

		default:
			break;
		}

		this.arqueroAux.add(jugador);

		return null;
	}

	public List<WrapperJugador> misJugadores() {

		try {

			this.suplentesAux.clear();
			this.delanterosAux.clear();
			this.mediocampistasAux.clear();
			this.defensasAux.clear();
			this.arqueroAux.clear();

			for (WrapperJugador j : this.suplentes) {

				if (!(this.suplentesAux.contains(j.getNombreJ()))) {
					this.suplentesAux.add(j.getNombreJ());
				}

			}

			for (WrapperJugador j : this.delanteros) {
				if (!(this.suplentesAux.contains(j.getNombreJ()))) {
					this.delanterosAux.add(j.getNombreJ());
				}
			}

			for (WrapperJugador j : this.mediocampistas) {
				if (!(this.mediocampistasAux.contains(j.getNombreJ()))) {
					this.mediocampistasAux.add(j.getNombreJ());
				}

			}

			for (WrapperJugador j : this.defensas) {
				if (!(this.defensasAux.contains(j.getNombreJ()))) {
					this.defensasAux.add(j.getNombreJ());
				}

			}

			for (WrapperJugador j : this.arquero) {
				if (!(this.arqueroAux.contains(j.getNombreJ()))) {
					this.arqueroAux.add(j.getNombreJ());
				}

			}
/*
			System.out.println("Estoy en Mis Jugadores");
			System.out.println(this.delanterosAux.toString());
			System.out.println(this.defensasAux.toString());
			System.out.println(this.mediocampistasAux.toString());
			System.out.println(this.arqueroAux.toString());
*/
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("Formacion.xhtml");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public List<WrapperJugador> getJugadoresSuplentes() {
		
		// /// Acordarse de usar el /formacion/{nombreE} con lo de la sesion,
		// este caso funca siempre para RealMadrid
		ClientRequest request;

		ArrayList<WrapperJugador> lwjs = new ArrayList<WrapperJugador>();
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		try {
			request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/FormacionService/formacionSuplentes/"+nombreEquipo);

			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			// gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			// gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();

			/*
			 * System.out.println(response.getStatus() + "///" + response);
			 * System.out.println(response.getEntity());
			 */

			JsonArray jugadoresSuplentes = parser.parse(response.getEntity())
					.getAsJsonArray();

			for (JsonElement jugador : jugadoresSuplentes) {

				WrapperJugador wj = new WrapperJugador();
				wj = gson.fromJson(jugador, WrapperJugador.class);

				lwjs.add(wj);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lwjs;

	}

	public List<WrapperJugador> getJugadoresDelanteros() {

		ClientRequest request;
		ArrayList<WrapperJugador> lwjd = new ArrayList<WrapperJugador>();
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		try {
			request = new ClientRequest(
					"http://localhost:8080/JatrickAppServer/rest/FormacionService/formacionDelanteros/"+nombreEquipo);

			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();

			JsonArray jugadoresDelanteros = parser.parse(response.getEntity())
					.getAsJsonArray();

			for (JsonElement jugador : jugadoresDelanteros) {

				WrapperJugador wj = new WrapperJugador();
				wj = gson.fromJson(jugador, WrapperJugador.class);

				lwjd.add(wj);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lwjd;

	}

	public List<WrapperJugador> getJugadoresMediocampistas() {

		ClientRequest request;
		ArrayList<WrapperJugador> lwjm = new ArrayList<WrapperJugador>();
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		try {
			request = new ClientRequest(
					"http://localhost:8080/JatrickAppServer/rest/FormacionService/formacionMediocampistas/"+nombreEquipo);

			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();

			JsonArray jugadoresMediocampistas = parser.parse(
					response.getEntity()).getAsJsonArray();

			for (JsonElement jugador : jugadoresMediocampistas) {

				WrapperJugador wj = new WrapperJugador();
				wj = gson.fromJson(jugador, WrapperJugador.class);

				lwjm.add(wj);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lwjm;

	}

	public List<WrapperJugador> getJugadoresDefensas() {

		ClientRequest request;
		ArrayList<WrapperJugador> lwjd = new ArrayList<WrapperJugador>();
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		try {
			request = new ClientRequest(
					"http://localhost:8080/JatrickAppServer/rest/FormacionService/formacionDefensas/"+nombreEquipo);

			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();

			JsonArray jugadoresDefensas = parser.parse(response.getEntity())
					.getAsJsonArray();

			for (JsonElement jugador : jugadoresDefensas) {

				WrapperJugador wj = new WrapperJugador();
				wj = gson.fromJson(jugador, WrapperJugador.class);

				lwjd.add(wj);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lwjd;

	}

	public List<WrapperJugador> getJugadorArquero() {

		ClientRequest request;
		ArrayList<WrapperJugador> lwja = new ArrayList<WrapperJugador>();
		String nombreEquipo= FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo").toString();
		
		try {
			request = new ClientRequest(
					"http://localhost:8080/JatrickAppServer/rest/FormacionService/formacionArquero/"+nombreEquipo);

			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();

			JsonArray jugadorArquero = parser.parse(response.getEntity())
					.getAsJsonArray();

			for (JsonElement jugador : jugadorArquero) {

				WrapperJugador wj = new WrapperJugador();
				wj = gson.fromJson(jugador, WrapperJugador.class);

				lwja.add(wj);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lwja;

	}

	public String toJSONString(Object object) { // Funcion que convierte de
												// objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

	public List<WrapperJugador> getDelanteros() {
		return delanteros;
	}

	public void setDelanteros(List<WrapperJugador> delanteros) {
		this.delanteros = delanteros;
	}

	public List<WrapperJugador> getMediocampistas() {
		return mediocampistas;
	}

	public void setMediocampistas(List<WrapperJugador> mediocampistas) {
		this.mediocampistas = mediocampistas;
	}

	public List<WrapperJugador> getDefensas() {
		return defensas;
	}

	public void setDefensas(List<WrapperJugador> defensas) {
		this.defensas = defensas;
	}

	public List<WrapperJugador> getSuplentes() {
		return suplentes;
	}

	public void setSuplentes(List<WrapperJugador> suplentes) {
		this.suplentes = suplentes;
	}

	public List<WrapperJugador> getArquero() {
		return arquero;
	}

	public void setArquero(List<WrapperJugador> arquero) {
		this.arquero = arquero;
	}

}
