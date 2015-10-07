package Presentacion;

import java.io.Serializable;
import java.util.ArrayList;






import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;




import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import utilidades.JugadorAdapter;
import Wrappers.WrapperEquipo;
import Wrappers.WrapperExperiencia;
import Wrappers.WrapperJugador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


@ManagedBean
@javax.faces.bean.SessionScoped
public class EquipoMB implements Serializable {
	
	
	  private static final long serialVersionUID = 1L;
	    
 
	  private String nombre;
	  private double capital;
	  private String ciudad;
	  private String pais;
	  private String entrenamiento;
	  private WrapperJugador selectedJugador;
	  private List<WrapperJugador> misJugadores = new ArrayList<WrapperJugador>();
	  private List<WrapperJugador> selectedJugadores;
	  private List<WrapperExperiencia> jugadoresExp = new ArrayList<WrapperExperiencia>();
	  private String dorsalPNG;
	  
	  
	

///////////////////////////////*****Alta Equipo******//////////////////////////// 
	  
			public String altaEquipo()
			{	
				
				String existeEquipo;
				this.capital=100000;
				ClientRequest request;
				ClientResponse<String> response;
				
				String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
				
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("equipo", this.nombre);	
				
				
				try {
					request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/existeEquipo/"+this.nombre);
					response = request.get(String.class);					
					existeEquipo= response.getEntity(String.class);	
					
					if (existeEquipo.equals("true")) {
						FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("El cuadro ya existe"));
						throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
							
					}
					else{
						request.clear();
						
						request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/equipo");
						WrapperEquipo equipo = new WrapperEquipo(this.nombre, this.capital);
						
						String equipoJson = toJSONString(equipo);
						request.header("username",nick);
						request.body("application/json", equipoJson);
						
						GsonBuilder gsonBuilder = new GsonBuilder();
						Gson gson = gsonBuilder.create();
						
						response = request.post(String.class);
											
						JsonParser parser = new JsonParser();
						JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
						
						ArrayList<String> zona = new ArrayList<String>();
						
						 for(JsonElement obj : jArray)
						 {
						    	String elem = gson.fromJson(obj , String.class);
						    	zona.add(elem);				        
						     
						 }
									
					
						 
						this.ciudad = zona.get(0);
						this.pais = zona.get(1);	
						

						System.out.println("Estoy en EQUIPO MB");
						System.out.println("La zona es: "+ this.ciudad +" - "+ this.pais);
						
						request.clear();
						
						request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/dorsal/"+ nick);
						
						
						response = request.get(String.class);		
						
						Integer dorsal = parser.parse(response.getEntity()).getAsInt();
						this.dorsalPNG = "camiseta"+dorsal.toString()+".png";
						
						
						System.out.println(response.getEntity());
						System.out.println("El dorsal es: "+ dorsal);
						System.out.println("El dorsal es: "+ this.dorsalPNG);
						
						
						
						Double capital2 = this.capital;
						
						System.out.println("El capital es: "+this.capital);
						
						
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dorsal", this.dorsalPNG);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("capital", capital2.toString());
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pais", this.pais);
						FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ciudad", this.ciudad);
						FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
					
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
				
			}
			
			
			public String setEntrenamiento()
			{	
				
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/setEntrenamiento");
								
				try {
				
					String equipoJson = toJSONString(this.nombre);
					System.out.println(equipoJson);
					request.header("entrenamiento",this.entrenamiento);
					request.body("application/json", equipoJson);
					
					ClientResponse<String> response = request.post(String.class);
					System.out.println("nanananana:"+response.getEntity(String.class));
					
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			public String darEquipos(){		
						
				
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/equiposervice/equipos");
				request.accept("application/json");
				ClientResponse<String> response;
				
				
				try {
				
					response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					Gson gson = gsonBuilder.create();
					
					    JsonParser parser = new JsonParser();
					    JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

					    ArrayList<WrapperEquipo> lcs = new ArrayList<WrapperEquipo>();

					    for(JsonElement obj : jArray )
					    {
					    	WrapperEquipo cse = gson.fromJson( obj , WrapperEquipo.class);
					        lcs.add(cse);
					        
					      
					    }
					
					
				} catch (Exception e) {
					 
					e.printStackTrace();
				}
				
				
				return null;
			}
			
///////////////////////////////////////////////////////Lista jugadores del equipo a Vender////////////////
			public List<WrapperJugador> darJugadores(){		
				System.out.println("entro dar Jugadores");
				String equipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
				System.out.println("entro dar Jugadoresjjkhkjhjkh"+equipo );
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/jugadores/"+ equipo);
				
				ArrayList<WrapperJugador> lwj = new ArrayList<WrapperJugador>();
				
				try {
					request.accept("application/json");
					System.out.println("entro dar Jugadores nombre equipo" + equipo);
					
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement Jugador : jArray) {
						WrapperJugador wj = new WrapperJugador();
						wj = gson.fromJson(Jugador, WrapperJugador.class);
						
						lwj.add(wj);
					
					}
					this.misJugadores = lwj;
					FacesContext.getCurrentInstance().getExternalContext().redirect("Venta.xhtml");
					
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
				return null;
			}
			
///////////////////////////////////	
			public List<WrapperJugador> darJugadoresMiEquipo(){		
				
				String equipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/jugadores/"+ equipo);
				
				ArrayList<WrapperJugador> lwj = new ArrayList<WrapperJugador>();
				
				try {
					request.accept("application/json");
				
					
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement Jugador : jArray) {
						WrapperJugador wj = new WrapperJugador();
						wj = gson.fromJson(Jugador, WrapperJugador.class);
						
						lwj.add(wj);
					
					}
					this.misJugadores = lwj;
								
				

					this.dorsalPNG = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dorsal");	
					this.pais = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pais");	
					this.capital = Double.parseDouble((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("capital"));					
					this.nombre = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");					
					this.ciudad = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ciudad");					
					FacesContext.getCurrentInstance().getExternalContext().redirect("MiEquipo.xhtml");
					
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
				return null;
			}
/////////////////////////////////////Vende Jugadores seleccionados////////////////////////////////////////			
			public void venderJugadores() {
			
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/vender");
				
				try {
				
					String jugadoresJson = jugadoresToJSONString(selectedJugadores);
					request.body("application/json", jugadoresJson);
					ClientResponse<String> response = request.post(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					System.out.println("venta status::" + response.getStatus() );
					if (response.getStatus() == 201){
						System.out.println("entro hghghgh::");
						
						addMessage("OK", "Jugadores puestos a la venta.");
						FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
						
					}
				
				
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
			}

/////////////////////////////////////////Lista Jugadores a Comprar////////////////////////////////////////////////////

			public List<WrapperJugador> compJugadores(){		
				System.out.println("entro comp Jugadores");
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/comprarJugadores");
				String equipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
				ArrayList<WrapperJugador> lwj = new ArrayList<WrapperJugador>();
				
				try {
					request.accept("application/json");
					request.header("equipo",equipo);
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement Jugador : jArray) {
						WrapperJugador wj = new WrapperJugador();
						wj = gson.fromJson(Jugador, WrapperJugador.class);
						
						lwj.add(wj);
					
					}
					this.misJugadores = lwj;
					FacesContext.getCurrentInstance().getExternalContext().redirect("Comprar.xhtml");
					
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
				return null;
			}
///////////////////////////Compra los jugadores seleccionados/////////////////////////////////
			public void comprar() {
				System.out.println("entro comprarrrrrrrr" );
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/comprar");
				String equipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
				
				try {
					
					String jugadoresJson = jugadoresToJSONString(selectedJugadores);
					request.header("equipo",equipo);
					request.body("application/json", jugadoresJson);
					ClientResponse<String> response = request.post(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					if (response.getStatus() == 201){
						addMessage("OK", "Jugadores Comprados.");
						FacesContext.getCurrentInstance().getExternalContext().redirect("Index.xhtml");
					
					}
				
				
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
			}

////////////////////////////////////////////////////////////////////////////////////////////////////	
///////////////////////////////////Trae la experiencia de los jugadores de mi Equipo/////////////////			
			
			public List<WrapperJugador> experienciaJugadores(){		
				System.out.println("entro exp Jugadores");
				String equipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
				System.out.println("entro dar Jugadoresjjkhkjhjkh"+equipo );
				ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/experiencia/"+ equipo);
				
				ArrayList<WrapperExperiencia> lwe = new ArrayList<WrapperExperiencia>();
				
				try {
					request.accept("application/json");
					System.out.println("entro dar exp Jugadores nombre equipo" + equipo);
					
					ClientResponse<String> response = request.get(String.class);
					GsonBuilder gsonBuilder = new GsonBuilder();
					
					///////////////////////
					gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Gson gson = gsonBuilder.create();
					
					JsonParser parser = new JsonParser();
					JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();
					
					for (JsonElement experiencia : jArray) {
						WrapperExperiencia we = new WrapperExperiencia();
						we = gson.fromJson(experiencia, WrapperExperiencia.class);
						
						lwe.add(we);
					
					}
					this.jugadoresExp = lwe;
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("Experiencia.xhtml");
					
				
				} catch (Exception e) {
				
					e.printStackTrace();
				}
				
				
				return null;
			}



////////////////////////////////////////////////////////////////////////////////////////////
			
			public String getNombre() {
				return nombre;
			}
			public void setNombre(String nombre) {
				this.nombre = nombre;
			}
			
			public String getCiudad() {
				return ciudad;
			}
			public void setCiudad(String ciudad) {
				this.ciudad = ciudad;
			}
			
			public double getCapital() {
				return capital;
			}
			public void setCapital(double capital) {
				this.capital = capital;
			}
			
			public List<WrapperJugador> getMisJugadores() {
				return misJugadores;
			}
			
			public void setMisJugadores(List<WrapperJugador> misJugadores) {
				this.misJugadores = misJugadores;
			}
		
			public WrapperJugador getSelectedJugador() {
			        
				return selectedJugador;
			    
			}
			 
			    
			public void setSelectedJugador(WrapperJugador selectedJugador) {
			        
				this.selectedJugador = selectedJugador;
			    
			}
			 
			    
			public List<WrapperJugador> getSelectedJugadores() {
			        
				return selectedJugadores;
			    
			}
			 
			public String getEntrenamiento() {
				return entrenamiento;
			}    
			
			public void setSelectedJugadores(List<WrapperJugador> selectedJugadores) {
			        
				this.selectedJugadores = selectedJugadores;
			    
			}
			
			public List<WrapperExperiencia> getJugadoresExp() {
				return jugadoresExp;
			}
			
			public void setJugadoresExp(List<WrapperExperiencia> jugadoresExp) {
				this.jugadoresExp = jugadoresExp;
			}
			
			
			
			 public String getPais() {
				return pais;
			}


			public void setPais(String pais) {
				this.pais = pais;
			}


			public String getDorsalPNG() {
				return dorsalPNG;
			}


			public void setDorsalPNG(String dorsalPNG) {
				this.dorsalPNG = dorsalPNG;
			}


			public void setEntrenamiento(String entrenamiento) {
				this.entrenamiento = entrenamiento;
			}


			public void addMessage(String summary, String detail) {
			        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
			        FacesContext.getCurrentInstance().addMessage(null, message);
			    
			 }
			 
			public String toJSONString(Object object) {	//	Funcion que convierte de objeto java a JSON
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson = gsonBuilder.create();
				return gson.toJson(object);
			}
			
			public String jugadoresToJSONString(List<WrapperJugador> jugadores) {  
			    GsonBuilder gsonBuilder = new GsonBuilder();
			    Gson gson = gsonBuilder.registerTypeAdapter(WrapperJugador.class, new JugadorAdapter()).create();
			    return gson.toJson(jugadores);
			}  

	

}
