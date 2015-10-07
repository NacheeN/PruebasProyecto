package Presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Wrappers.WrapperEquipo;
import Wrappers.WrapperLiga;
import Wrappers.WrapperMensaje;

@ManagedBean
@javax.faces.bean.ViewScoped
public class MensajeMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String descripcion;
	private String nombreEqEnviar;
	private String nombreEqEmisor;
	private String fecha;
	private List<WrapperMensaje> mensajes;

	
	@PostConstruct
	public void loadMensajes(){
		this.nombreEqEmisor = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");		
		
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/getMensajes/"+this.nombreEqEmisor);
		
	//	ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/getMensajes/BandidoFC");
		
		
		List<WrapperMensaje> lwm = new ArrayList<WrapperMensaje>();

		try {

			request.accept("application/json");
			ClientResponse<String> response;

			response = request.get(String.class);

			GsonBuilder gsonBuilder = new GsonBuilder();
		//	gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Gson gson = gsonBuilder.create();

			JsonParser parser = new JsonParser();
	
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			for (JsonElement Mensaje : jArray) {
				WrapperMensaje wm = new WrapperMensaje();
				wm = gson.fromJson(Mensaje, WrapperMensaje.class);
				lwm.add(wm);
	
				System.out.println("mensaje mb: "+wm.getDescripcion());
		
				System.out.println(lwm);
			
			}
	
			this.mensajes = lwm;
		
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		
	}

	public String sendMensaje()
	{	
		
		ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/UsuarioService/enviarMensaje");
		//String miequipo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("equipo");
		
		Date d = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha = formatter.format(d);
		
		
		try {
			
			List<String> datos = new ArrayList<String>();
			datos.add(0, this.nombreEqEmisor);
			datos.add(1, this.nombreEqEnviar);
			datos.add(2, this.descripcion);
			
			
			request.body("application/json", datos);
	
			ClientResponse<String> response = request.post(String.class);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("misMensajes.xhtml");
			
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
	
	
	public List<WrapperMensaje> getMensajes() {
		return mensajes;
	}


	public void setMensajes(ArrayList<WrapperMensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public String irNuevoMensaje() throws IOException{
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("CrearMensaje.xhtml");
		return null;
		
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setNombreEqEnviar(String nombreEqEnviar) {
		this.nombreEqEnviar = nombreEqEnviar;
	}
	public void setNombreEqEmisor(String nombreEqEmisor) {
		this.nombreEqEmisor = nombreEqEmisor;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombreEqEnviar() {
		return nombreEqEnviar;
	}
	public String getNombreEqEmisor() {
		return nombreEqEmisor;
	}
	public String getFecha() {
		return fecha;
	}
	
	
public void irMisMensaje() throws IOException{
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("misMensajes.xhtml");
		
	}
	
public String toJSONString(Object object) { // Funcion que convierte de
	// objeto java a JSON
	GsonBuilder gsonBuilder = new GsonBuilder();
	Gson gson = gsonBuilder.create();
	return gson.toJson(object);
}

}
