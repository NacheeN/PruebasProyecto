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

import utilidades.ExperienciaAdapter;
import utilidades.JugadorAdapter;
import dominio.Ciudad;
import dominio.Equipo;





import dominio.Experiencia;
import dominio.Jugador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import controladores.IEquipoController;
import controladores.IUsuarioController;


@Path("/EquipoService") // para mapearlo en la url
public class EquipoService extends Application {
	
	@EJB
	private IEquipoController iec;
	@EJB
	private IUsuarioController iuc;
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/zona/{equipo}")
	// para mapearlo en la url
	public Response getZona(@PathParam("equipo") String equipoName) {
		
		String respuesta = "";
		try{
			
			List<String> zona = iec.darZona(equipoName);			
			respuesta = toJSONString(zona);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 return Response.ok(respuesta).build();

	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Ciudad/{equipo}")
	// para mapearlo en la url
	public Response getCiudad(@PathParam("equipo") String equipoName) {
		
		String nombreC = iec.darCiudad(equipoName);	
		
		 return Response.ok(nombreC).build();

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	// para mapearlo en la url
	public Response getStatus() {
		// System.out.println("WENOOOOOOOOOOOOOOOOOOO");
		return Response
				.ok("{\"status\":\"El servicio de los equipos esta funcionando...\"}")
				.build();

	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/equipos")		
	public Response getEquipos()
	{
		
		String response = null;
		try {

			List<Equipo> list = iec.listarEquipos();//query.getResultList();
			
		
			response = toJSONString(list);
			
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
	@Path("/equipo/{name}")	
	public Response getEquipo(@PathParam("name") String equipoName){
		String response = null;
		try {
			
			Equipo existingEquipo = iec.buscarEquipo(equipoName);
			
			if(null == existingEquipo){
				response = "{\"status\":\"401\","
						+ "\"message\":\"No content found \""
						+ "\"developerMessage\":\"Equipo - "+equipoName+" Not Found " + "}";
				return Response.status(401).entity(response).build();
			}
			
			response = toJSONString(existingEquipo);
		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).build();
		
	}
	//////////////////////////Trae true si el equipo ya fue ingresado//////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/existeEquipo/{name}")	
	public Response existeEquipo(@PathParam("name") String equipoName){
		String returnCode = "200";
		String response = null;
		boolean existe = false;
		try {
			
			existe = iec.existeEquipo(equipoName);
			
			
			
		} catch (Exception err) {
			err.printStackTrace();		
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 

		}
		return  Response.status(201).entity(existe).build(); 
		
	}
	
	////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/equipo")
	public Response createNewEquipo(String datos, @HeaderParam("username") String username){
	
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		Equipo equipo = gson.fromJson(datos, Equipo.class);
		String returnCode = "200";
		
		String response = "";
		try {
			//System.out.println("EQUIPO SERVICE "+equipo.getNombreE());
			List<String> zona= iec.altaEquipo(equipo,username);	
			response = toJSONString(zona);
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/EquipoWebService/rest/equiposervice/equipo/"+equipo.getNombreE()+"\","
					+ "\"message\":\"New equipo successfully created.\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not created.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 

		}
		return Response.ok(response).build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/setEntrenamiento")
	public Response setEntrenamiento(String datos, @HeaderParam("entrenamiento") String entrenamiento){
		
		
		// Create a new Gson object that could parse all passed in elements
	
		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		
		String nick = gson.fromJson(datos, String.class);
		
		
		String returnCode = "200";
		
		try {
			
			String equipo = iuc.darEquipo(nick);
			
			System.out.println("Equipo de "+nick+" :"+equipo);
			iec.setEntrenamiento(entrenamiento, equipo);		
			
		} catch (Exception err) {
			err.printStackTrace();
			
			return  Response.status(500).entity(returnCode).build(); 

		}
		return  Response.status(201).entity(returnCode).build(); 
	}
	
///////////////////////////////////////////Compra Y Venta Jugadores//////////////////////////////////////
///////////////Lista los jugadores del equipo pasado//////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jugadores/{name}")	
	public Response getJugadores(@PathParam("name") String equipoName){
		String response = null;
		try {
		
			List<Jugador> jugadores = iec.misJugadores(equipoName);
			response = jugadoresToJSONString(jugadores);
		
		} catch (Exception err) {
			response = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
		
		
	}

//////////////////////Pone a la venta los jugadores pasados//////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/vender")
	public Response venderJugadores(String datos){
	
		boolean vendido;
		String returnCode = "200";
		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		Gson gson = gsonBuilder.create();
		try{
		
			// Obtengo la lista de jugadores pareando los datos que me llegan (JSON string)
			JsonParser parser = new JsonParser();
			JsonArray jArray = parser.parse(datos).getAsJsonArray();
			ArrayList<Jugador> lj = new ArrayList<Jugador>();
			
			for (JsonElement jugador : jArray ){
				Jugador	jg ;  	
				jg = gson.fromJson( jugador, Jugador.class);
				lj.add(jg);
				
			}
			
			System.out.println("jugadores en servicio" + lj.toString());
			vendido=iec.venderJugadores(lj);
			
		
		}catch(Exception err) {
			returnCode = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(returnCode).build();
		}
		
		
		return  Response.status(201).entity(returnCode).build(); 
		
	}

//////////Lista los jugadores a Comprar///////////////////////////////// 
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/comprarJugadores")	
	public Response getJugadoresComprar( @HeaderParam("equipo") String equipo){
		String response = null;
		try {
			System.out.println("jugadores a comprar mi equipo   " + equipo);
			List<Jugador> jugadores = iec.jugadoresAComp(equipo);
			response = jugadoresToJSONString(jugadores);
			
		} catch (Exception err) {
			response = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	
	
	}

/////////////////////////////Compra los jugadores pasados//////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/comprar")
	public Response comprarJugadores(String datos, @HeaderParam("equipo") String equipo){
	
		boolean compra;
		String returnCode = "200";
		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd");
		Gson gson = gsonBuilder.create();
		try{
		
			// Obtengo la lista de jugadores pareando los datos que me llegan (JSON string)
			JsonParser parser = new JsonParser();
			JsonArray jArray = parser.parse(datos).getAsJsonArray();
			ArrayList<Jugador> lj = new ArrayList<Jugador>();
			
			for (JsonElement jugador : jArray ){
				Jugador	jg ;  	
				jg = gson.fromJson( jugador, Jugador.class);
				lj.add(jg);
			
			}
			
			System.out.println("jugadores en servicio" + lj.toString());
			compra=iec.comprarJugadores(lj,equipo);
			System.out.println("jugadores en servicio sale compra" );
		
		}catch(Exception err) {
			returnCode = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(returnCode).build();
		}
		
		
		return  Response.status(201).entity(returnCode).build(); 
	
	}
///////////////////////////////////////
///////////////////////////////////////Trae la experiencia de todos los jugadores del Equipo/////////////////////////
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/experiencia/{name}")	
	public Response getJugadoresExperiencia(@PathParam("name") String equipoName){
		String response = null;
		try {
			List<Experiencia> exp = iec.getJugadoresExperiencia(equipoName);
			response = experienciaToJSONString(exp);
		
		} catch (Exception err) {
			response = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
		
	
	}
		
	
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/equipo/{name}")
	public Response deleteEquipo(@PathParam("name") String nombreE) {
		
		String returnCode = "";

		try {
			Equipo equipo = iec.buscarEquipo(nombreE);
			iec.borrarEquipo(equipo);
			
			returnCode = "{"
					+ "\"message\":\"Equipo succesfully deleted\""
					+ "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","+
					"\"message\":\"Resource not deleted.\""+
					"\"developerMessage\":\""+err.getMessage()+"\""+
					"}";
			return  Response.status(500).entity(returnCode).build(); 
		}
		return Response.ok(returnCode,MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/capital/{name}")	
	public Response getCapital(@PathParam("name") String equipoName){
		String response = null;
		try {
			
			Double capital = iec.buscarCapital(equipoName);
			
			response = toJSONString(capital);
			
		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
		
	}
	
	
	public String toJSONString(Object object) {	//	Funcion que convierte de objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
	
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	public String jugadoresToJSONString(List<Jugador> jugadores) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Jugador.class, new JugadorAdapter()).create();
	    return gson.toJson(jugadores);
	} 
	
	public String experienciaToJSONString(List<Experiencia> experiencia) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Experiencia.class, new ExperienciaAdapter()).create();
	    return gson.toJson(experiencia);
	}  
	

}
