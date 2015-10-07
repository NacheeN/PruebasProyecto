package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import utilidades.JugadorAdapter;
import dominio.Ciudad;
import dominio.Equipo;
import dominio.Formacion;
import dominio.Jugador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import controladores.IEquipoController;
import controladores.IFormacionController;

//para mapearlo en la url
@Path("/FormacionService")
public class FormacionService extends Application {

	@EJB
	private IFormacionController ifc;
	@EJB
	private IEquipoController iec;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	public Response getStatus() {

		return Response
				.ok("{\"status\":\"El servicio de las formaciones esta funcionando...\"}")
				.build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/formacionSuplentes/{equipo}")
	public Response getJugadoresSuplentes(@PathParam("equipo") String nombreE) {

		String response = null;

		try {

			// buscar el id de la formacion que corresponde al equipo

			int idFormacion = ifc.getIdFormacion(nombreE);
			List<Jugador> jugadores = ifc.listarJugadoresSuplentes(idFormacion);
			response = jugadoresToJson(jugadores);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/formacionDelanteros/{equipo}")
	public Response getJugadoresDelanteros(@PathParam("equipo") String nombreE) {

		String response = null;

		try {

			int idFormacion = ifc.getIdFormacion(nombreE);

			List<Jugador> jugadores = ifc
					.listarJugadoresDelanteros(idFormacion);

			response = jugadoresToJson(jugadores);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/formacionMediocampistas/{equipo}")
	public Response getJugadoresMediocampistas(
			@PathParam("equipo") String nombreE) {

		String response = null;

		try {

			int idFormacion = ifc.getIdFormacion(nombreE);

			List<Jugador> jugadores = ifc
					.listarJugadoresMediocampistas(idFormacion);
			response = jugadoresToJson(jugadores);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/formacionDefensas/{equipo}")
	public Response getJugadoresDefensas(@PathParam("equipo") String nombreE) {

		String response = null;

		try {

			int idFormacion = ifc.getIdFormacion(nombreE);

			List<Jugador> jugadores = ifc.listarJugadoresDefensas(idFormacion);
			response = jugadoresToJson(jugadores);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/formacionArquero/{equipo}")
	public Response getJugadoresArquero(@PathParam("equipo") String nombreE) {

		String response = null;

		try {

			int idFormacion = ifc.getIdFormacion(nombreE);

			List<Jugador> jugadores = ifc.listarJugadorArquero(idFormacion);
			response = jugadoresToJson(jugadores);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/modificarFormacion")
	public Response modificarFormacion(String datos) {
		
		
		//System.out.println("FORMACION SERVICE " + datos);

		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		Gson gson = gsonBuilder.create();
		
		Formacion f = gson.fromJson(datos, Formacion.class);
		String returnCode = "200";
		
		// Para poder modificar esa formacion.
		
		int idFormacion = ifc.getIdFormacion(f.getEquipo().getNombreE());
		f.setId(idFormacion);
		
		//System.out.println("FORMACION SERVICE 2 " + f.getEquipo().getNombreE());

		Equipo e = iec.buscarEquipo(f.getEquipo().getNombreE());
		f.setEquipo(e);
		
		
		
		// Seteo los equipos a esos jugadores, sino me quedaban en null
		
		System.out.println("Defensas");

		for (Jugador j : f.getDefensas()) {
	//		System.out.println(j.getNombreJ()+j.getTiro() + j.getEquipo() + j.getPrecio()+j.isVender()+j.getFechaNac());
			j.setEquipo(e);
			
		}

		System.out.println("Delanteros");
		for (Jugador j : f.getDelanteros()) {
	//		System.out.println(j.getNombreJ()+j.getTiro() + j.getEquipo() + j.getPrecio()+j.isVender()+j.getFechaNac());
			j.setEquipo(e);
		}

		System.out.println("Mediocampistas");
		for (Jugador j : f.getMediocampistas()) {
	//		System.out.println(j.getNombreJ()+j.getTiro() + j.getEquipo() + j.getPrecio()+j.isVender()+j.getFechaNac());
			j.setEquipo(e);
		}

		System.out.println("Suplentes");
		for (Jugador j : f.getSuplentes()) {
//			System.out.println(j.getNombreJ()+j.getTiro() + j.getEquipo() + j.getPrecio()+j.isVender()+j.getFechaNac());
			j.setEquipo(e);
		}

		System.out.println("Arquero");
		for (Jugador j : f.getArquero()) {
//			System.out.println(j.getNombreJ()+j.getTiro() + j.getEquipo() + j.getPrecio()+j.isVender()+j.getFechaNac());
			j.setEquipo(e);
		}
	

		try {

			e.setFormacion(f);
			
			ifc.modificarFormacion(f);

			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/EquipoWebService/rest/equiposervice/equipo/"
					+ "La formacion del equipo " + f.getEquipo().getNombreE()
					+ "\"," + "\"message\":\"Formacion modificada con exito.\""
					+ "}";

		} catch (Exception err) {

			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"La formacion no se pudo modificar.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}

		return Response.status(201).entity(returnCode).build();
	}
	
	
	public String jugadoresToJson(List<Jugador> jugadores) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.registerTypeAdapter(Jugador.class,
				new JugadorAdapter()).create();
		return gson.toJson(jugadores);
	}

}
