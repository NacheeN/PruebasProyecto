package Presentacion;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import Wrappers.WrapperEquipo;
import Wrappers.WrapperLiga;
import Wrappers.WrapperUsuario;
import Wrappers.WrapperPartido;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@ManagedBean
@javax.faces.bean.SessionScoped
public class LigaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombreLiga;
	private List<WrapperLiga> ligasPendientes = new ArrayList<WrapperLiga>();
	private List<WrapperPartido> resultados = new ArrayList<WrapperPartido>();
	private List<String> puntos = new ArrayList<String>();
	
	public List<WrapperLiga> miLiga() {
		System.out.println("Entre Liga managed Bean ");

		/*
		 * llamo web service, cargo las ligas. Vas a tener que modificar la
		 * parte del servicio para que solo te traiga las que tienen por defecto
		 * alguna fecha de inicio que vos pienses que es por default Ej:
		 * 1/1/1111 y desp cuando se anotan todos los equipos le das fehca de
		 * inicio y fecha finalizacion lo dejas default. Se setea fecha
		 * finalizacion cuando se jugaron todos los partidos
		 */
		ClientRequest request = new ClientRequest(
				"http://localhost:8080/JatrickAppServer/rest/LigaService/ligas");
		ArrayList<WrapperLiga> lwl = new ArrayList<WrapperLiga>();

		try {

			request.accept("application/json");
			ClientResponse<String> response;

			response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
			// gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();
	/*		System.out.println(response.getStatus() + "///" + response);
			System.out.println(response.getEntity());
*/
			JsonArray jArray = parser.parse(response.getEntity())
					.getAsJsonArray();

			for (JsonElement liga : jArray) {
				WrapperLiga wl = new WrapperLiga();
				wl = gson.fromJson(liga, WrapperLiga.class);
	/*			System.out
						.println(wl.getNombreLiga() + "//////"
								+ wl.getFechaFinalizado() + "///"
								+ wl.getFechaInicio());

				 wl.addEquipo(new WrapperEquipo("empty", 0));

				System.out
						.println(wl.getNombreLiga() + "//////"
								+ wl.getFechaFinalizado() + "///"
								+ wl.getFechaInicio());
*/
				
				lwl.add(wl);
				
				
			/*	System.out.println("Paso primer Lista");
				System.out.println(lwl.get(0).getNombreLiga() + "///"
						+ lwl.get(0).getFechaFinalizado() + "///"
						+ lwl.get(0).getFechaInicio());
				 this.ligasPendientes.add(wl);
			*/
			}
			this.ligasPendientes = lwl;
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("miLiga.xhtml");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
		// return "miLiga.xhtml?faces-redirect=true";
	}
	
	public String unirseLiga()
	{	
		System.out.println("Entre unirseLiga MB");
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/unirseLiga");
	    String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		//String nombreE= "Madrid";
		try {
		
			WrapperLiga Liga = new WrapperLiga(this.nombreLiga);
				
			String ligaJson = toJSONString(Liga);
			
			System.out.println(ligaJson);
			//System.out.println(nombreEq);
			
		//	request.header("equiponame",nombreE);
			request.header("username",nick);
			request.body("application/json", ligaJson);
			
			ClientResponse<String> response = request.post(String.class);
			System.out.println("nanananana:"+response.getEntity(String.class));
			
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(response.getEntity()).getAsJsonObject();
			
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public String altaLiga() {

		ClientRequest request;
		ClientResponse<String> response;
		String existeLiga;

		try {
		    request= new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/existeLiga/"+this.nombreLiga);

			response = request.get(String.class);					
			existeLiga= response.getEntity(String.class);
			if (existeLiga.equals("true")) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("La liga ya existe"));
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
					
			}
			else{
				request.clear();
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/liga");

				WrapperLiga liga = new WrapperLiga(this.nombreLiga);

				String ligaJSON = toJSONString(liga);
				System.out.println(ligaJSON);

				request.body("application/json", ligaJSON);

				response = request.post(String.class);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Alta de Liga con exito"));
                
				FacesContext.getCurrentInstance().getExternalContext().redirect("AltaLiga.xhtml");				

			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public List<WrapperPartido> darResultados(){		
		System.out.println("entro dar Resultados");
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/resultados");
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		ArrayList<WrapperPartido> lwp = new ArrayList<WrapperPartido>();
		
		try {
			request.header("username",nick);
			request.accept("application/json");
			
			ClientResponse<String> response = request.get(String.class);
			GsonBuilder gsonBuilder = new GsonBuilder();

			///////////////////////
			gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			for (JsonElement Partido : jArray) {
				WrapperPartido wp = new WrapperPartido();
				wp = gson.fromJson(Partido, WrapperPartido.class);

				lwp.add(wp);
		
			}
			this.resultados = lwp;
			
			request.clear();
			
			request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/tabla");
			request.header("username",nick);
			request.accept("application/json");
			
			response = request.get(String.class);
			
			GsonBuilder gsonB = new GsonBuilder();	
			Gson Gson = gsonB.create();

			System.out.println("ESTOY EN LIGAMB DESPUES DE LLAMADO" + response.getEntity());
			
			JsonArray resultadosJArray = parser.parse(response.getEntity()).getAsJsonArray();

			List<String> puntajes = new ArrayList<String>();
			
			for (JsonElement palabra : resultadosJArray) {
				
				String s = Gson.fromJson(palabra, String.class);
				puntajes.add(s);
			}
			
			this.puntos = puntajes;

			
			FacesContext.getCurrentInstance().getExternalContext().redirect("Resultados.xhtml");
			
			
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	public String partidoAmistoso(){
		
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/partidoAmistoso");
		
		try {
			String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			String nickJson = toJSONString(nick);
			
			request.body("application/json", nickJson);
			
			ClientResponse<String> response = request.post(String.class);
			
			
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(response.getEntity()).getAsJsonObject();
			
			
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
		
	}
	
	
	
	public List<WrapperLiga> getLigasPendientes() {
		return ligasPendientes;
	}
	
	public void setLigasPendientes(List<WrapperLiga> ligasPendientes) {
		this.ligasPendientes = ligasPendientes;
	}
	public String getNombreLiga() {
		return nombreLiga;
	}

	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}
	
	public List<WrapperPartido> getResultados() {
		return resultados;
	}
	
	public void setResultados(List<WrapperPartido> resultados) {
		this.resultados = resultados;
	}

	public List<String> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<String> puntos) {
		this.puntos = puntos;
	}

	public String toJSONString(Object object) { // Funcion que convierte de
												// objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		
		
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		//gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}
	
	
}
