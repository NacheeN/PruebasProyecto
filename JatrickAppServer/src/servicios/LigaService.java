package servicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import controladores.IEquipoController;
import controladores.ILigaController;
import controladores.IUsuarioController;
import dominio.Ciudad;
import dominio.Equipo;
import dominio.EventosPartido;
import dominio.Liga;
import dominio.Partido;
import persistencia.LigaDAO;
import utilidades.EventoAdapter;
import utilidades.PartidoAdapter;

@Path("/LigaService")
public class LigaService extends Application {

	@EJB
	private ILigaController ilc;
	@EJB
	private IEquipoController iec;
	@EJB
	private IUsuarioController iuc;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/status")
	// para mapearlo en la url
	public Response getStatus() {
		// System.out.println("WENOOOOOOOOOOOOOOOOOOO");
		return Response
				.ok("{\"status\":\"El servicio de los ligas esta funcionando...\"}")
				.build();

	}
	
//////////////////////////Trae true si la liga ya fue ingresado//////////////////////////////////
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/existeLiga/{name}")	
	public Response existeLiga(@PathParam("name") String liga){
		String returnCode = "200";
		String response = null;
		boolean existe = false;
		try {
		
			existe = ilc.existeLiga(liga);
		
		
		
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
	@Produces(MediaType.APPLICATION_JSON)
	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)
	// lo que recibe
	@Path("/liga")
	public Response createNewLiga(String datos) {
		System.out.println("LIGA SERVICE " + datos);
		// System.out.println("LIGA SERVICE "+nombreLiga);

		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto book pareando los datos que me llegan (JSON string)
		Liga liga = gson.fromJson(datos, Liga.class);
		String returnCode = "200";

		try {

			ilc.guardarLiga(liga.getNombreLiga()); // Ver bien porque returna
													// boolean, pero se controla
													// todo por exepciones

			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/LigaWebService/rest/ligaservice/liga/"
					+ liga.getNombreLiga() + "\","
					+ "\"message\":\"New liga successfully created.\"" + "}";
		} catch (Exception err) {
			err.printStackTrace();
			returnCode = "{\"status\":\"500\","
					+ "\"message\":\"Resource not created.\""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(500).entity(returnCode).build();

		}
		return Response.status(201).entity(returnCode).build();
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/unirseLiga")
	public Response unirseLiga(String datos, @HeaderParam("username") String username){
		
		System.out.println("HOOOOLAAA");
		System.out.println("Liga SERVICE "+datos);
		System.out.println("Liga SERVICE "+username);
		
		
		// Create a new Gson object that could parse all passed in elements
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		Liga Liga = gson.fromJson(datos, Liga.class);
		String returnCode = "200";
		
		
		

		try {
			System.out.println(Liga);
            
			//Integer IdLiga = ilc.darIdLiga(Liga.getNombreLiga());
			//System.out.println(IdLiga);
			Liga Laliga = ilc.darLiga(Liga.getNombreLiga());
			System.out.println(username);
            
			System.out.println("1");
			String nombreEq = iuc.darEquipo(username);
			System.out.println(nombreEq);

			System.out.println("2");
			Equipo Team = iec.buscarEquipo(nombreEq);
			System.out.println("3");
			
			Team.setLiga(Laliga);
			iec.asignarLiga(Laliga,Team);
			ilc.sumarEquipo(Laliga);
			int cantEq = ilc.cantidadEquipos(Laliga);
			System.out.println("Cantidad de eqipos");
			System.out.println(cantEq);
			
			if (cantEq == 4){
				System.out.println("Entro al crear Fixture");
				///////////Creo el Fixture ///////////////
				
				List<Equipo> ListaEq =iec.listarEquipos(Laliga);
				Equipo Eq1 = ListaEq.get(0);
				Equipo Eq2 = ListaEq.get(1);
				Equipo Eq3 = ListaEq.get(2);
				Equipo Eq4 = ListaEq.get(3);
				System.out.println(Eq1);
				System.out.println(Eq2);
				System.out.println(Eq3);
				System.out.println(Eq4);
				ilc.crearFixture(Eq1, Eq2, Eq3, Eq4);
				
			}
			
			System.out.println("4");
			
			returnCode = "{"
					+ "\"href\":\"http://localhost:8080/EquipoWebService/rest/equiposervice/equipo/"+Liga.getNombreLiga()+"\","
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
		return  Response.status(201).entity(returnCode).build(); 
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/resultados/")	
	public Response getJugadores(@HeaderParam("username") String username){
		String response = null;
		try {
			
		//	System.out.println("entro dar resultados servicio" + username);
			
			 String equipo = iuc.darEquipo(username);
			 Liga MiLiga = ilc.darLigaEq(equipo);
			 String NombreLiga = MiLiga.getNombreLiga();
			
			List<Partido> partidos = ilc.resultados(NombreLiga);
			
			
			//System.out.println("ESTOY EN LIGA SERVICE!! ANTES PARTIDOTOJSONSTRING");
			
			response = partidoToJSONString(partidos);
		//	System.out.println("ESTOY EN LIGA SERVICE DESPUES PARTIDOTOJSONSTRING");
			
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
	@Path("/tabla/")	
	public Response getTabla(@HeaderParam("username") String username){
		String response = null;
		try {
			
			 String equipo = iuc.darEquipo(username);
			 Liga MiLiga = ilc.darLigaEq(equipo);
			 String liga = MiLiga.getNombreLiga();
			 List<Partido> partidos = ilc.partidosFinalizados(MiLiga.getNombreLiga());
		
			 System.out.println("ESTOY EN LIGA SERVICE DESPUES de traer los partidos");
			 System.out.println(partidos);
				
			 
			 List<Equipo> equipos = ilc.darEquiposLiga(liga);
			 
			 String nombreEq1 = equipos.get(0).getNombreE();
			 String nombreEq2 = equipos.get(1).getNombreE();
			 String nombreEq3 = equipos.get(2).getNombreE();
			 String nombreEq4 = equipos.get(3).getNombreE();
			 
			 Integer puntosEq1 = 0;
			 Integer puntosEq2 = 0;
			 Integer puntosEq3 = 0;
			 Integer puntosEq4 = 0;
			 
			 
			 for(Partido p : partidos){
			
				 System.out.println("ESTOY EN LIGA SERVICe " +p.getNombreLiga()+ " "+ p.getEquipo1().getNombreE() + " " + p.getEquipo2().getNombreE() );
					
				 
				 if(darMayor(p.getGolesL(), p.getGolesV()) == 1){
					 
					 if(p.getEquipo1().getNombreE().equals(nombreEq1)){
						 puntosEq1 = puntosEq1 + 3;
					 }else if(p.getEquipo1().getNombreE().equals(nombreEq2)){
						 puntosEq2 = puntosEq2 + 3;
					 }else if(p.getEquipo1().getNombreE().equals(nombreEq3)){
						 puntosEq3 = puntosEq3 + 3;
					 }else{
						 puntosEq4 = puntosEq4 + 3;
					 }
					 
				 }else if(darMayor(p.getGolesL(), p.getGolesV()) == 2){
					 if(p.getEquipo2().getNombreE().equals(nombreEq1)){
						 
						 puntosEq1 = puntosEq1 + 3;
					 }else if(p.getEquipo2().getNombreE().equals(nombreEq2)){
						 puntosEq2 = puntosEq2 + 3;
					 }else if(p.getEquipo2().getNombreE().equals(nombreEq3)){
						 puntosEq3 = puntosEq3 + 3;
					 }else{
						 puntosEq4 = puntosEq4 + 3;
					 }
					  
				 }else{
	 				if(p.getEquipo1().getNombreE().equals(nombreEq1)){
						 
						 puntosEq1 = puntosEq1 + 1;
					 }else if(p.getEquipo1().getNombreE().equals(nombreEq2)){
						 puntosEq2 = puntosEq2 + 1;
					 }else if(p.getEquipo1().getNombreE().equals(nombreEq3)){
						 puntosEq3 = puntosEq3 + 1;
					 }else{
						 puntosEq4 = puntosEq4 + 1;
					 }
	 				
	 				if(p.getEquipo2().getNombreE().equals(nombreEq1)){
						 
						 puntosEq1 = puntosEq1 + 1;
					 }else if(p.getEquipo2().getNombreE().equals(nombreEq2)){
						 puntosEq2 = puntosEq2 + 1;
					 }else if(p.getEquipo2().getNombreE().equals(nombreEq3)){
						 puntosEq3 = puntosEq3 + 1;
					 }else{
						 puntosEq4 = puntosEq4 + 1;
					 }
				 }
					 
				 
			 }
			
					 
			 System.out.println("ESTOY EN LIGA SERVICE Antes de agregar resultados");
				
			 
		List<String> resultados = new ArrayList<String>();
		Random rnd = new Random();
		
		String equipoIzq = "";
		String equipoDer = "";
		Integer puntosIzq = 0;
		Integer puntosDer = 0;
		
		String equipoRestanteIzq = "";
		String equipoRestanteDer = "";
		Integer restanteIzq = 0;
		Integer restanteDer = 0;
		
		Integer ordenadoIzq = darMayor(puntosEq1, puntosEq2);
		Integer ordenadoDer = darMayor(puntosEq3, puntosEq4);
				
		if(ordenadoIzq == 1){
			equipoIzq = nombreEq1;
			puntosIzq = puntosEq1;
			
			equipoRestanteIzq = nombreEq2;
			restanteIzq = puntosEq2;
		}else if(ordenadoIzq == 2){
			equipoIzq = nombreEq2;
			puntosIzq = puntosEq2;
			
			
			equipoRestanteIzq = nombreEq1;
			restanteIzq = puntosEq1;
		}else{
			
			if((1 + rnd.nextInt(2)) == 1){
				equipoIzq = nombreEq1;
				puntosIzq = puntosEq1;
				equipoRestanteIzq = nombreEq2;
				restanteIzq = puntosEq2;
			}else{
				equipoIzq = nombreEq2;
				puntosIzq = puntosEq2;
				equipoRestanteIzq = nombreEq1;
				restanteIzq = puntosEq1;
			}
			
		}

		
		
		if(ordenadoDer == 1){
			equipoDer = nombreEq3;
			puntosDer = puntosEq3;
			
			equipoRestanteDer = nombreEq4;
			restanteDer = puntosEq4;
		}else if(ordenadoIzq == 2){
			equipoDer = nombreEq4;
			puntosDer = puntosEq4;
			
			equipoRestanteDer = nombreEq3;
			restanteDer = puntosEq3;
		}else{
			if((1 + rnd.nextInt(2)) == 1){
				equipoDer = nombreEq3;
				puntosDer = puntosEq3;				
				equipoRestanteDer = nombreEq4;
				restanteDer = puntosEq4;
			}else{
				equipoDer = nombreEq4;
				puntosDer = puntosEq4;				
				equipoRestanteDer = nombreEq3;
				restanteDer = puntosEq3;
			}
			
		}
		
		
		Integer ordenadoMayor = darMayor(puntosIzq, puntosDer);
		
		if(ordenadoMayor == 1){
			// mayor viene de la izquierda
			resultados.add(equipoIzq);
			resultados.add(puntosIzq.toString());
			resultados.add(equipoDer);
			resultados.add(puntosDer.toString());			
		}else if(ordenadoMayor == 2){
			resultados.add(equipoDer);
			resultados.add(puntosDer.toString());
			resultados.add(equipoIzq);
			resultados.add(puntosIzq.toString());
		}else{
			if((1 + rnd.nextInt(2)) == 1){
				resultados.add(equipoIzq);
				resultados.add(puntosIzq.toString());
				resultados.add(equipoDer);
				resultados.add(puntosDer.toString());
			}else{
				resultados.add(equipoDer);
				resultados.add(puntosDer.toString());
				resultados.add(equipoIzq);
				resultados.add(puntosIzq.toString());
			}
		}
		
		Integer ordenadoTercero = darMayor(restanteIzq, restanteDer);
		
		if(ordenadoTercero == 1){
			// mayor viene de la izquierda
			resultados.add(equipoRestanteIzq);
			resultados.add(restanteIzq.toString());
			resultados.add(equipoRestanteDer);
			resultados.add(restanteDer.toString());			
		}else if(ordenadoTercero == 2){
			resultados.add(equipoRestanteDer);
			resultados.add(restanteDer.toString());
			resultados.add(equipoRestanteIzq);
			resultados.add(restanteIzq.toString());
		}else{
			if((1 + rnd.nextInt(2)) == 1){
				resultados.add(equipoRestanteIzq);
				resultados.add(restanteIzq.toString());
				resultados.add(equipoRestanteDer);
				resultados.add(restanteDer.toString());	
			}else{
				resultados.add(equipoRestanteDer);
				resultados.add(restanteDer.toString());
				resultados.add(equipoRestanteIzq);
				resultados.add(restanteIzq.toString());
			}
			
			
		}
		
		
		/*
		resultados.add(nombreEq1);
		resultados.add(puntosEq1.toString());
		resultados.add(nombreEq2);
		resultados.add(puntosEq2.toString());
		resultados.add(nombreEq3);
		resultados.add(puntosEq3.toString());
		resultados.add(nombreEq4);
		resultados.add(puntosEq4.toString());
			*/ 
			 
			response = toJSONString(resultados);
			System.out.println("ESTOY EN LIGA SERVICE DESPUES resultados" + response);
			
			
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
	@Path("/ligas")
	// ruta:http://localhost:8080/LigaService/rest/LigaService/ligas
	public Response getLigas() {

		String response = null;
		try {

	
			List<Liga> list = ilc.listarLigas();

			response = toJSONString(list);

//			System.out.println(response);

		} catch (Exception err) {
			response = "{\"status\":\"401\","
					+ "\"message\":\"No content found \""
					+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();

		// return null;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/buscarPartido")	
	public Response getPartido( @HeaderParam("nick") String nick){
		
		String response = null;
		
		try {
			
			Partido p = ilc.buscarPartidoEjectuando(nick);
			
			response = partidoToJsonString(p);
			
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
	@Path("/buscarEventos")	
	public Response buscarEventos( @HeaderParam("idPartido") Integer idPartido){
		
		String response = null;
		
		try {
			
			List<EventosPartido> eventos = ilc.buscarEventos(idPartido);
			
			
			//System.out.println("ESTOY LIGA SERVICE, ANTES TOJSON");
			
			response = eventoToJSONString(eventos);
			
		//	System.out.println("ESTOY LIGA SERVICE, despues TOJSON"+response);
			
		} catch (Exception err) {
			response = "{\"status\":\"401\","
			+ "\"message\":\"No content found \""
			+ "\"developerMessage\":\"" + err.getMessage() + "\"" + "}";
			return Response.status(401).entity(response).build();
		}
		return Response.ok(response).build();
	
	
	}
	public int darMayor(int n1, int n2){
		int res = 0;
		if(n1>n2){
			
			res = 1;
		}
		else if(n1<n2){
			res=2;
		}
			return res;
		
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)	// lo que genera
	@Consumes(MediaType.APPLICATION_JSON)	// lo que recibe
	@Path("/partidoAmistoso")
	public Response setEntrenamiento(String datos){
		
		
		// Create a new Gson object that could parse all passed in elements
	
		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		// Obtengo el objeto Equipo pareando los datos que me llegan (JSON string)
		
		String nick = gson.fromJson(datos, String.class);
		
		
		String returnCode = "200";
		
		try {
			
			String equipo = iuc.darEquipo(nick);
			
			ilc.partidoAmistoso(equipo);
		
			
			
		} catch (Exception err) {
			err.printStackTrace();
			
			return  Response.status(500).entity(returnCode).build(); 

		}
		return  Response.status(201).entity(returnCode).build(); 
	}
	

	public String eventoToJSONString(List<EventosPartido> eventos) { 
		 GsonBuilder gsonBuilder = new GsonBuilder();
		 Gson gson = gsonBuilder.registerTypeAdapter(EventosPartido.class, new EventoAdapter()).create();
		 return gson.toJson(eventos);
	}
	

	public String partidoToJsonString(Partido p) { // Funcion que convierte de
		GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Partido.class, new PartidoAdapter()).create();
	    return gson.toJson(p);
	}
	
	public String toJSONString(Object object) { // Funcion que convierte de
		// objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	public String partidoToJSONString(List<Partido> partidos) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Partido.class, new PartidoAdapter()).create();
	    return gson.toJson(partidos);
	}  

}
