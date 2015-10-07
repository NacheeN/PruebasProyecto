package jobs;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import controladores.IEquipoController;
import dominio.Equipo;
import dominio.Partido;


public class entrenamientoJob implements Job {

	@EJB
	private IEquipoController iec;
	
	
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		       
	     try{
	        	Context context = new InitialContext();
        		IEquipoController iec = (IEquipoController) context.lookup("java:global/JatrickAppServer/EquipoController!controladores.IEquipoController");
        		
        		
	        	
	        	if (iec != null){
	        		//Tengo que traer Todos los equipos
	        		ArrayList<Equipo> equiposList= (ArrayList<Equipo>)iec.listarEquipos();
	        		
	        		for(Equipo e: equiposList){
	        			if (e.getNombreE().compareTo("BotsFC")!=0){
	        				iec.EntrenarEquipo(e.getNombreE());
	        			}
	        		}
	        		
	        		//	iec.EntrenarEquipo("FrenteAmplioFC");
	        		//iec.simularPartido(new Partido());
	        	}
	        	else{
	        		System.out.println("es null");
	        		
	        	}
	        	
	        	}
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	       	}
	       	
	       	
	}
	 

	 
}




 
   
  