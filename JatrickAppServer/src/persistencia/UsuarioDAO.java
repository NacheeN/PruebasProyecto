package persistencia;

import java.util.List;

import javax.ejb.Stateless;

import dominio.Equipo;
import dominio.Mensaje;
import dominio.Usuario;

@Stateless
public class UsuarioDAO implements IUsuarioDAO {

	@javax.persistence.PersistenceContext(unitName = "JatrickAppServer")
	private javax.persistence.EntityManager em;

	public boolean guardarUsuario(Usuario usuario) {
/*
		System.out.println("DAO: " + usuario.getNick() + usuario.getPassword()
				+ usuario.getMail() + usuario.getNombre());
	*/
		try {
			em.persist(usuario);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	
	public void modificarUsuario(Usuario u) {
		
		try {
			em.merge(u);			

		} catch (Exception e) {

			e.printStackTrace();

		}
		
	}


	public boolean existeUsuario(Usuario usuario) {

		Usuario u = em.find(Usuario.class, usuario.getNick()); // Si no se
																// encuentra a
																// la persona,
																// se retorna
																// NULL

		if ((u != null) && (u.getPassword().equals(usuario.getPassword())))
			return true;
		else
			return false;
	}

	public boolean tieneEquipo(String nick) {
		Usuario u = em.find(Usuario.class, nick);

		if (u.getEquipo() == null) {
			return false;
		} else
			return true;
	}

	public Usuario getUsuario(String nick) {

		
		
		return em.find(Usuario.class, nick);
	}


	public boolean asignarEquipo(Usuario u, Equipo equipo) {
		
		try {
			u.setEquipo(equipo);
			em.merge(u);
			return true;
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;

	}
	public String darEquipo(String nick) {
		
		  Usuario u = em.find(Usuario.class, nick);		
		  return u.getEquipo().getNombreE();

		 }
	
	public String darol(String nick) {
		Usuario u = em.find(Usuario.class, nick);

		if (u.getRoles().getId() == 1) {
			return "Admin";
		} else {
			return "Usuario";
		}

	}
	
public boolean guardarMensaje(Mensaje mensaje1) {
		
		try {
			em.persist(mensaje1);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;
	}


	public List<Mensaje> getMensajes(String equipo) {
		
		List<Mensaje> mensajes = null;
		
		
		try {
		
			mensajes = em.createQuery("Select m From Mensaje m WHERE m.equipoReceptor ='"+equipo+"'", Mensaje.class).getResultList();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mensajes;
		
		
		
		
		
	}

	public void EjecutandoPartido(Usuario manager, boolean b,Integer integer) {
		try{
		manager.setEjecutandoPartido(b);
		manager.setPartidojugando(integer);
		em.merge(manager);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public boolean ejecutandoPartido(String nick) {
		boolean retorno = false;
		
		try{
			Usuario u = em.find(Usuario.class, nick);
			retorno = u.isEjecutandoPartido();			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retorno;
	}


	public Integer darContadorEjecucion(String nick) {
		Integer retorno = 0;
		
		try{
			Usuario u = em.find(Usuario.class, nick);
			retorno = u.getContadorEjecucion();			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return retorno;
	}


	public List<Usuario> buscarUsuariosEjecucion() {
		
			List<Usuario> usuarios = null;
		
		
		try {
		
			usuarios = em.createQuery("Select u From Usuario u WHERE u.EjecutandoPartido = true", Usuario.class).getResultList();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return usuarios;
		
	}

	public Integer darDorsal(String nick) {

		Integer dorsal = 0;
		try {

			Usuario u = em.find(Usuario.class, nick);
			dorsal = u.getDorsal();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dorsal;

	}


	
}
