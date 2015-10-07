package Presentacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Wrappers.WrapperEntrenamiento;
import Wrappers.WrapperEquipo;

@ManagedBean
@javax.faces.bean.SessionScoped
public class EntrenamientoMB implements Serializable {
	
	  private static final long serialVersionUID = 1L;
	  
	  
	  private List<WrapperEntrenamiento> entrenamientos;
	  
	  private String entSel;

	  public String getEntSel() {
		return entSel;
	}


	public void setEntSel(String entSel) {
		this.entSel = entSel;
	}


	@PostConstruct
	    public void init() {
	        //Cities
		  entrenamientos = new ArrayList<WrapperEntrenamiento>();
		  
		  entrenamientos.add(new WrapperEntrenamiento("Defensa", " Entrenamiento enfocado hacia lo DEFENSIVO", 0, 10, 0, 10));
		  entrenamientos.add(new WrapperEntrenamiento("Ataque", "  Entrenamiento enfocado hacia lo OFENSIVO", 10, 0, 10, 0));
		  entrenamientos.add(new WrapperEntrenamiento("Balanceado", " Entrenamiento Balanceado", 5, 5, 5, 5));
	       this.entSel="Defensivo";
	    }
	
	public String entrenamiento(){
		
	return "Entrenamiento.xhtml?faces-redirect=true";
	
	}
			
			
	public List<WrapperEntrenamiento> getEntrenamientos() {
		return this.entrenamientos;
	}


	public void setEntrenamientos(List<WrapperEntrenamiento> entrenamientos) {
		this.entrenamientos = entrenamientos;
	}
	
	public String guardarEntrenamiento(){
		
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/EquipoService/setEntrenamiento");
		String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		try {
		
							
			String entrenamientoJson = toJSONString(nick);
			
			
			request.header("entrenamiento",entSel);
			
			request.body("application/json", entrenamientoJson);
			
			ClientResponse<String> response = request.post(String.class);
			
			
			
			
			
			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		 
		System.out.println("Entsel: "+this.entSel);
		
		
		
		return null;
	}
	public String toJSONString(Object object) {	//	Funcion que convierte de objeto java a JSON
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

}

