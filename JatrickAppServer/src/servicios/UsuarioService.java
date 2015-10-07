package servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import controladores.IUsuarioController;
import dominio.Mensaje;
import dominio.Usuario;
import persistencia.UsuarioDAO;
import utilidades.MensajeAdapter;

@Path("/UsuarioService")

// para mapearlo en la url
public class UsuarioService extends Application {

	@EJB
	private IUsuarioController iuc;

	// localhost:8080/JatrickAppServer/rest/UsuarioService/status/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	// para mapearlo en la url
	public Response getStatus() {
		// System.out.println("WENOOOOOOOOOOOOOOOOOOO");
		return Response
				.ok("{\"status\":\"El servicio de los usuarios esta funcionando...\"}")
				.build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rol/{nick}")
	public Response darRol(@PathParam("nick") String nick)
	{
		 String rol = iuc.darRol(nick);
		 
		 
		
		 return Response.ok(rol).build();
		// return null;
		
	}
	// localhost:8080/JatrickAppServer/rest/UsuarioService/login/{username}
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	// /{nick}")  @PathParam("nick") String nick,
	public Response login(String datos) {

		boolean existe = false;
		boolean tieneEquipo = false;
		String respuesta;

		// Fabrica f = Fabrica.getInstance();
		// IUsuarioController ipc = f.getICtrlUsuario();

		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Get book Object parsed from JSON string
		Usuario usuario = gson.fromJson(datos, Usuario.class);

		if (iuc.existeUsuario(usuario.getNick(), usuario.getPassword())) {

			existe = true;

			if (iuc.tieneEquipo(usuario.getNick()))
				tieneEquipo = true;

			ArrayList<Boolean> lista = new ArrayList<Boolean>();
			lista.add(existe);
			lista.add(tieneEquipo);

			respuesta = toJSONString(lista);

			return Response.ok().entity(respuesta).build();

		} else {
			ArrayList<Boolean> lista = new ArrayList<Boolean>();
			lista.add(existe);
			lista.add(tieneEquipo);

			respuesta = toJSONString(lista);

			return Response.status(404).entity(respuesta).build();
		}
	}

	// localhost:8080/JatrickAppServer/rest/UsuarioService/usuario
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/usuario")
	public Response guardarPersona(String datos) {
		System.out.println("payload - " + datos);
		boolean creado = false;
		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto book pareando los datos que me llegan (JSON string)
		Usuario usuario = gson.fromJson(datos, Usuario.class);

		String returnCode = "200";

		// Guardo el libro
		try {

			// IUsuarioController iuc = Fabrica.getInstance();

			creado = this.iuc.guardarUsuario(usuario.getNick(),
					usuario.getPassword(), usuario.getMail(),
					usuario.getNombre());

			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/JatrickWebServer/rest/PersonaService/persona/"
					+ usuario.getNombre() + "\","
					+ "\"message\":\"New book successfully created.\"" + "}";

		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"Resource not created.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}
		return Response.status(201).entity(creado).build();
	}
	
	
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/Equipo/{nick}")
	public Response darEquipo(@PathParam("nick") String nick)
	{
		 String nombreE = iuc.darEquipo(nick);	
		
		 return Response.ok(nombreE).build();
	
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/enviarMensaje")
	public Response enviarMensaje(String datos) {
		
		System.out.println("payload - " + datos);
		boolean creado = false;
		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto book pareando los datos que me llegan (JSON string)
		
		ArrayList<String> params = gson.fromJson(datos, new TypeToken<ArrayList<String>>() {}.getType());
		
		
		
		String returnCode = "200";

		// Guardo el libro
		try {
			//emisor receptor mensaje
			iuc.enviarMensaje(params.get(0),params.get(1),params.get(2));
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"Resource not created.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}
		return Response.status(201).entity(creado).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMensajes/{equipo}")
	public Response getMensajes(@PathParam("equipo") String equipo)
	{
		List<Mensaje> mensajes= new ArrayList<Mensaje>();
		
		mensajes = iuc.recibirMensaje(equipo);
		
		String response = "";
		
		response = toMensajeJSONString(mensajes);
		
		System.out.println(response);
		return Response.status(201).entity(response).build();
		// return null;
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ejecutandoPartido")
	public Response ejecutandoPartido(String datos) {
			
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		ArrayList<String> lista = new ArrayList<String>();
		String respuesta = "";
		
		Boolean ejecutandoPartido = false;
		String returnCode = "200";
		
		try {
			
		// Obtengo el nick pasado por parametros
		String nick = gson.fromJson(datos, String.class);
		
		ejecutandoPartido = iuc.ejecutandoPartido(nick);
		
				

		if(ejecutandoPartido){
			
			Integer idPartido = iuc.darIdPartidoEjecutando(nick);
						
			Integer contador = iuc.darContadorEjecucion(nick);			
			
			
			lista.add("true");	
			lista.add(contador.toString());	
			lista.add(idPartido.toString());
		}else
		{
			lista.add("false");	
			lista.add("0");	
			lista.add("0");	
			
		}
		
		
		respuesta = toJSONString(lista);
			
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"Resource not created.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}
		
		return Response.status(201).entity(respuesta).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/dorsal/{nick}")
	public Response darDorsal(@PathParam("nick") String nick)
	{
		 Integer dorsal = iuc.darDorsal(nick);		 
		 
		
		 return Response.ok(dorsal).build();
		// return null;
		
	}
	
	public String toJSONString(Object object) { // Funcion que convierte de
												// objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

	
	public String toMensajeJSONString(List<Mensaje> mensajes){
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Mensaje.class, new MensajeAdapter()).create();
	    return gson.toJson(mensajes);
		
	}
}
