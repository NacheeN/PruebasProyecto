package jobs;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import controladores.IEquipoController;
import controladores.ILigaController;
import dominio.Liga;
import dominio.Partido;

public class partidoJob implements Job{
	
	

		@Override
		public void execute(JobExecutionContext arg0)		throws JobExecutionException {
			
				
			     try{
			        	Context context = new InitialContext();
		        		IEquipoController iec = (IEquipoController) context.lookup("java:global/JatrickAppServer/EquipoController!controladores.IEquipoController");
		        		
		        		ILigaController ilc=(ILigaController) context.lookup("java:global/JatrickAppServer/LigaController!controladores.ILigaController");
		        		
			        	
			        	if ((iec != null)&&(ilc!=null)){
			        		
			        //	iec.EntrenarEquipo("FrenteAmplioFC");
			        		
			        		ArrayList<Partido> partidos=(ArrayList<Partido>) ilc.listarPartidos();
			        		Date ahora = new Date();
			        		
			        		for(Partido p:partidos){
			        			
			        			
			        			
				        		if(ahora.after(p.getFecha())){
				        			
				        			System.out.println("Tengo que ejecutar el partido de:  "+p.getEquipo1().getNombreE()+" Contra "+p.getEquipo2().getNombreE());
				        			iec.simularPartido(p);
				        			if(p.getNombreLiga().compareTo("AMISTOSO")!=0){
				        				//Si entra aqui es un partido de liga porl o q hago otras operaciones
					        			Liga laLiga = ilc.darLiga(p.getNombreLiga());
					        			ilc.ganadorCampeonato(laLiga);
				        			}
				        		}
				        			
			        		}
			        		
			        		
			        		//iec.simularPartido(new Partido());
			        	}/*
			        	else{
			        		System.out.println("es null");
			        		
			        	}*/
			        	
			        	}
			        catch(Exception e)
			        {
			        	e.printStackTrace();
			       	}
			
		}
		
		

}
