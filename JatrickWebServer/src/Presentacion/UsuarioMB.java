package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import Wrappers.WrapperUsuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



@ManagedBean
@javax.faces.bean.SessionScoped
public class UsuarioMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String room;
	private String nick;
	private String password;
	private String mail;
	private String nombre;
	private String rol;
	private String equipo;
	private String ciudad;
	private String pais;
	private Double capital;
	private String dorsalPNG;
	private Integer dorsal;
	private boolean ejecutando;
	private String link;
	private boolean iniciado;
	
	public String Login(){
		
		ClientRequest request;
		  
		try {

			request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/login");		
			
			WrapperUsuario u = new WrapperUsuario(nick, password, mail, nombre);
			
			String userJson = toJSONString(u);			
			request.body("application/json", userJson);		
			
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			
			ClientResponse<String> response = request.post(String.class);
			
			JsonParser parser = new JsonParser();
		    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
			
			ArrayList<Boolean> lista = new ArrayList<Boolean>();

			    for(JsonElement obj : jArray)
			    {
			    	Boolean booleano = gson.fromJson(obj , Boolean.class);
			    	lista.add(booleano);
			        
			  
			    }
			
			System.out.println(lista.get(0).toString() + lista.get(1).toString());
			    
			if (lista.get(0) == false) {				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credenciales incorrectas o Perfil deshabilitado"));
				FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}
			else if((lista.get(0) == true) && (lista.get(1) == false)){				
				
				request.clear();
				
				
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/rol/"+this.nick);		
				
				ClientResponse<String> respuesta = request.get(String.class);

				this.rol = respuesta.getEntity(String.class);
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", this.rol);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.nick);
				
				if("Usuario".equals(this.rol)){					
				FacesContext.getCurrentInstance().getExternalContext().redirect("AltaEquipo.xhtml");
				}else
				{
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("AltaLiga.xhtml");
				}
			//	return "AltaEquipo";		
				
			}		
			else
			{
						
				request.clear();
				
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/rol/"+this.nick);		
				
				ClientResponse<String> respuesta = request.get(String.class);
				
				this.rol = respuesta.getEntity(String.class);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.nick);	
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", this.rol);
				
				if("Admin".equals(this.rol)){					
					FacesContext.getCurrentInstance().getExternalContext().redirect("AltaLiga.xhtml");
				}
				
				request.clear();
				
		//		System.out.println("Antes Request equipo");
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/Equipo/"+this.nick);		
				respuesta = request.get(String.class);
				
			//	System.out.println(respuesta);
				
				this.equipo = respuesta.getEntity(String.class);
				
				request.clear();
				
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/zona/"+this.equipo);		
				response = request.get(String.class);
		
				
				jArray = parser.parse(response.getEntity()).getAsJsonArray();
				ArrayList<String> zona = new ArrayList<String>();
				
				 for(JsonElement obj : jArray)
				 {
				    	String elem = gson.fromJson(obj , String.class);
				    	zona.add(elem);				        
				     
				 }
				 
				this.ciudad = zona.get(0);
				this.pais = zona.get(1);
				
				
				request.clear();
				
				
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/capital/"+ equipo);
				
				
				response = request.get(String.class);				
		
				
				this.capital = parser.parse(response.getEntity()).getAsDouble();
				
				request.clear();
				
				
				request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/dorsal/"+ this.nick);
				
				
				response = request.get(String.class);				
		
				
				this.dorsal = parser.parse(response.getEntity()).getAsInt();
				this.dorsalPNG = "camiseta"+this.dorsal.toString()+".png";
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Bienvenido! "+this.nombre));	
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dorsal", this.dorsalPNG);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("capital", this.capital.toString());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("equipo", this.equipo);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pais", this.pais);
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ciudad", this.ciudad);		
			// 	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", this.rol);
			//	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.nick);					
				FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
				
			}
			
			} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
	return null;

	}

	public String logOut() {

		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("Index.xhtml");
			// return "Index";

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		// return "Index.xhtml?faces-redirect=true";
		return null;
	}

	public String registroUsuario() {

		ClientRequest request;

		try {
			request = new ClientRequest(
					"http://localhost:8080/JatrickAppServer/rest/UsuarioService/usuario");
			WrapperUsuario usuario = new WrapperUsuario(this.nick,
					this.password, this.mail, this.nombre);

			// transformo el usuario a ingresar en Json string
			String usuarioJSON = toJSONString(usuario);

			// Seteo el objeto usuario al body del request
			request.body("application/json", usuarioJSON);

			// se obtiene una respuesta por parte del webService
			ClientResponse<String> response = request.post(String.class);

			if (response.getStatus() != 201) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Error al ingresar un nuevo usuario"));
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			// System.out.println(response.getEntity());
			// System.out.println(response.getStatus());

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Alta de Usuario con exito"));
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("Index.xhtml");
			// return "Index";

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		return null;
	}
	
public String partidoActivo(){
		
		
		ClientRequest request;
		
		 try{	
		
			 request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/ejecutandoPartido");		
				
				String nickJson = toJSONString(this.nick);	
				
				request.body("application/json", nickJson);		
				
				
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				
				ClientResponse<String> response = request.post(String.class);
				
				JsonParser parser = new JsonParser();
				
				JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
				ArrayList<String> lista = new ArrayList<String>();
				
				 for(JsonElement obj : jArray)
				 {
				    	String elem = gson.fromJson(obj , String.class);
				    	lista.add(elem);				        
				     
				 }


			    Boolean b = Boolean.parseBoolean(lista.get(0));
			    Integer contadorEjecucion = Integer.parseInt(lista.get(1));
			    
			 
				if(b){					

					this.room = lista.get(2);					
				
			        
			         InitialContext ctx = new InitialContext();
			         ITimerBean timer = (ITimerBean) ctx.lookup("java:global/JatrickWebServer/TimerBean!Presentacion.ITimerBean");
			       
			         if((iniciado)&&( timer.getCurrentTime()==0)){
			        	  iniciado=false;
			          }
			        
			         if(!iniciado){
			         iniciado=true;
			          timer.scheduleTimer(60000-(contadorEjecucion*1000));
			          this.link="SE JUEGA PARTIDO";
			          
			          
			          
			          
			          }
	          
				}
				else{
					
					this.link="";
					
					
				}
		 
			
	}
	 catch(Exception e){
		 
		 e.printStackTrace();
	 }
		
		
		return null;
	}
	
	public String irChat()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("Index2.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getPassword() {
		return password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNick() {
		return nick;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public boolean isEjecutando() {
		return ejecutando;
	}

	public void setEjecutando(boolean ejecutando) {
		this.ejecutando = ejecutando;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isIniciado() {
		return iniciado;
	}

	public void setIniciado(boolean iniciado) {
		this.iniciado = iniciado;
	}
	
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Double getCapital() {
		return capital;
	}

	public void setCapital(Double capital) {
		this.capital = capital;
	}

	public String getDorsalPNG() {
		return dorsalPNG;
	}

	public void setDorsalPNG(String dorsalPNG) {
		this.dorsalPNG = dorsalPNG;
	}

	public Integer getDorsal() {
		return dorsal;
	}

	public void setDorsal(Integer dorsal) {
		this.dorsal = dorsal;
	}

	public String toJSONString(Object object) { // Funcion que convierte de
												// objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		// gsonBuilder.setDateFormat("yyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // ISO8601
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

}
