package controladores;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;


import javax.ejb.Stateless;

import dominio.Equipo;
import dominio.Mensaje;
import dominio.Roles;
import dominio.Usuario;
import persistencia.IEquipoDAO;
import persistencia.IUsuarioDAO;

@Stateless
public class UsuarioController implements IUsuarioController {

	@EJB
	private IUsuarioDAO UsuarioDAO;
	@EJB
	private IEquipoDAO EquipoDAO;
	public boolean guardarUsuario(String nick, String Password,String mail,String nombre) {
		try{
				
			
			Usuario u = new Usuario(nick,Password,mail,nombre);
			u.setEjecutandoPartido(false);
			u.setContadorEjecucion(0);
			u.setDorsal(randInt(1, 4));
			
			if (u.getNick().equals("Admin")){
				System.out.println("Soy el admin");
				Roles r = new Roles(1,"Admin");
				u.setRoles(r);
				
			}else{
				
				System.out.println("Soy el user");
				Roles r = new Roles(2,"User");
				u.setRoles(r);
				
			}
			/*
			if(this.UsuarioDAO == null)
				System.out.println("Es null");
			else
				System.out.println("No es Null");
			*/
			
			return UsuarioDAO.guardarUsuario(u);		
		
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean existeUsuario(String nick, String password) {
		
		try{
			return UsuarioDAO.existeUsuario(new Usuario(nick,password));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean tieneEquipo(String nick){
		
		try{
			return UsuarioDAO.tieneEquipo(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public String darEquipo(String nick) {
		  
		  try{
			  
		   String nombreE = UsuarioDAO.darEquipo(nick);
		   
		//   System.out.println("ESTOY EN EL CONTROLADOR USUARIO EN DAR EQUIPO");

		   
		 //  System.out.println(nombreE);


		   return nombreE;
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  return null;
	}

	public String darRol(String nick){
		try{
			
				return UsuarioDAO.darol(nick);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
public boolean enviarMensaje(String equipoEmisor,String equipoReceptor,String Mensaje){
		
		try{
			
			Date d = new Date();
			String fecha=d.toString();
			
			Equipo e = EquipoDAO.buscarEquipo(equipoReceptor);
			Mensaje Mensaje1= new Mensaje(e, equipoEmisor, Mensaje,fecha );
		//	System.out.println("LLEGUE AL CONTROLADOR DESP DE LOS MENSAJES");
			
			UsuarioDAO.guardarMensaje(Mensaje1);
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

	public List<Mensaje> recibirMensaje(String equipo){ 
		try{
		List<Mensaje> mensaje=UsuarioDAO.getMensajes(equipo);
		return mensaje;
		}catch(Exception e){
			e.printStackTrace();
			
		}
	return null;
		
	}

	public boolean ejecutandoPartido(String nick) {
		
		try{
		return UsuarioDAO.ejecutandoPartido(nick);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return false;
	}


	public Usuario buscarUsuario(String nick) {
		
		try{
		return UsuarioDAO.getUsuario(nick);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}

	public Integer darContadorEjecucion(String nick) {
		
		try{
			return UsuarioDAO.darContadorEjecucion(nick);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return 0;
	}


	public List<Usuario> buscarUsuariosEjecucion() {

		try{
			return UsuarioDAO.buscarUsuariosEjecucion();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return null;
	}


	public void modificarUsuario(Usuario u) {
		try{
	
			UsuarioDAO.modificarUsuario(u);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	

	private int randInt(int min, int max) {

	   
	    Random rand = new Random();

	    
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}


	public Integer darDorsal(String nick) {
		Integer dorsal = 0;
		try{
			
			dorsal = UsuarioDAO.darDorsal(nick);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return dorsal;
	}

	public Integer darIdPartidoEjecutando(String nick) {
		
		try {
			Usuario u = UsuarioDAO.getUsuario(nick);
			return u.getPartidojugando();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return 0;
	}
}
