package Presentacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.TimerService;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import Wrappers.WrapperEquipo;
import Wrappers.WrapperEvento;
import Wrappers.WrapperJugador;
import Wrappers.WrapperPartido;


@ManagedBean
@javax.faces.bean.ViewScoped
public class PartidoMB implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String equipo1,equipo2;
	public String getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}

	public String getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}

	private int timer=0;
	private int contador=0;
	private int iter=0;
	private Boolean stop=false;
	public int getIter() {
		return iter;
	}

	public void setIter(int iter) {
		this.iter = iter;
	}

	public Boolean getStop() {
		return stop;
	}

	public void setStop(Boolean stop) {
		this.stop = stop;
	}

	private List<WrapperEvento> eventos=new ArrayList<WrapperEvento>();
	private String evento;
	private List<WrapperEvento> test=new ArrayList<WrapperEvento>();
	private int goles1=0,goles2=0;
	
	@PostConstruct
	public void loadDatos(){	
		
		try {
			ClientRequest request = new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/buscarPartido");	
			
			String nick = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
			
			request.accept("application/json");

			request.header("nick",nick);
			
			ClientResponse<String> response = request.get(String.class);
			
			//System.out.println(response.getEntity());
			
			
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Gson gson = gsonBuilder.create();
			
			WrapperPartido p = new WrapperPartido();
			p = gson.fromJson(response.getEntity(), WrapperPartido.class);
				
			equipo1=p.getIdEquipo();
			equipo2=p.getIdEquipo2();
			
			request.clear();
			request= new ClientRequest("http://localhost:8080/JatrickAppServer/rest/LigaService/buscarEventos");	
			
			request.accept("application/json");

			request.header("idPartido",p.getIdPartido());
			
			response=request.get(String.class);
			
			
			
			
			GsonBuilder gsonBuilder2 = new GsonBuilder();
			Gson gson2 = gsonBuilder2.create();
			
			JsonParser parser = new JsonParser();
			JsonArray jArray = parser.parse(response.getEntity()).getAsJsonArray();

			ArrayList<WrapperEvento> ListaEventos = new ArrayList<WrapperEvento>();

		    for(JsonElement obj : jArray )
		    {
		    	WrapperEvento we = gson.fromJson( obj , WrapperEvento.class);
		    	ListaEventos.add(we);
		        
		      
		    }
		    
			this.test=ListaEventos;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 InitialContext ctx;
		try {
			ctx = new InitialContext();
			 ITimerBean timerGlobal = (ITimerBean) ctx.lookup("java:global/JatrickWebServer/TimerBean!Presentacion.ITimerBean");
			 
	         int tiemporestante=Math.round(timerGlobal.getCurrentTime()/1000);
	         
	         
	         contador=60-tiemporestante;
	         
	         System.out.println("PARTIDO MB VAN TRANSCURRIDOS "+contador);
			//contador=0;
	         
			timer=Math.round((60/test.size()));
			
			System.out.println("PARTIDO MB TIMER "+timer);
			iter= Math.round(contador/timer);
			
			for (int i=0;i<iter;i++){
				
				
				eventos.add(test.get(i));
				
				if ("gol1".compareTo(test.get(i).getEsGol())==0){
					System.out.println("ES GOL1 "+goles1);
					this.goles1=this.goles1+1;
				}
				else if ("gol2".compareTo(test.get(i).getEsGol())==0){
					System.out.println("ES GOL2 "+goles2);
					this.goles2=this.goles2+1;
				}
				
			}
			
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		
		//TimerService timer=TimerService.createTimer();
	}
	
	public void addDato(){
		
		if (iter<test.size()){
			
		eventos.add(test.get(iter));
		
		if ("gol1".compareTo(test.get(iter).getEsGol())==0){
			System.out.println("ES GOL1 "+goles1);
			this.goles1=this.goles1+1;
		}
		else if ("gol2".compareTo(test.get(iter).getEsGol())==0){
			System.out.println("ES GOL2 "+goles2);
			this.goles2=this.goles2+1;
		}
		
		iter=iter+1;
		}
		if(iter>= test.size()){
			  stop = true;
			
		}
	}
	public int getTimer() {
		return timer;
	}
	public void setTimer(int timer) {
		this.timer = timer;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public List<WrapperEvento> getEventos() {
		return eventos;
	}
	public void setEventos(List<WrapperEvento> eventos) {
		this.eventos = eventos;
	}
	public String getEvento() {
		return evento;
	}
	public void setEvento(String evento) {
		this.evento = evento;
	}
	public List<WrapperEvento> getTest() {
		return test;
	}
	public void setTest(List<WrapperEvento> test) {
		this.test = test;
	}

	public int getGoles1() {
		return goles1;
	}

	public void setGoles1(int goles1) {
		this.goles1 = goles1;
	}

	public int getGoles2() {
		return goles2;
	}

	public void setGoles2(int goles2) {
		this.goles2 = goles2;
	}
	
}
