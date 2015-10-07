package jobs;

import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



import dominio.Usuario;
import controladores.IUsuarioController;

public class ejecucionJob implements Job{


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		
		try{
			
			Context context = new InitialContext();
			IUsuarioController iuc = (IUsuarioController) context.lookup("java:global/JatrickAppServer/UsuarioController!controladores.IUsuarioController");
			
			
			List<Usuario> usuarios = iuc.buscarUsuariosEjecucion();
			//System.out.println(usuarios.size());
			
			for(Usuario u:usuarios){
				if(u.getContadorEjecucion()== 0){
					u.setContadorEjecucion(1);
					
					
				}
				else if((u.getContadorEjecucion()<60)&&(u.getContadorEjecucion()>0)){
					u.setContadorEjecucion(u.getContadorEjecucion()+1);
				}
				else{
					u.setContadorEjecucion(0);
					u.setEjecutandoPartido(false);
					
				}
				iuc.modificarUsuario(u);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}

}
