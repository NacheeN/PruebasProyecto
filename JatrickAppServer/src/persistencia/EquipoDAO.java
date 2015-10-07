package persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Entrenamiento;
import dominio.Equipo;
import dominio.Liga;
import dominio.Usuario;


@Stateless
public class EquipoDAO implements IEquipoDAO {
	@javax.persistence.PersistenceContext(unitName="JatrickAppServer")
	private javax.persistence.EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarEquipo(Equipo equipo){
		
		
		try {
			equipo.setEntrenamiento(em.find(Entrenamiento.class,"Balanceado"));
			em.persist(equipo);
			return true;
			
		} catch (Exception e) {
		
			e.printStackTrace();
			
		}
		
		return false;
		
	}
	
	
	public List<Equipo> listarEquipos() {
		
		List<Equipo> equipos = em.createQuery("Select e FROM Equipo e",Equipo.class).getResultList();
		
		return equipos;
		  
	}


	public Equipo buscarEquipo(String equipo) {
		
		Equipo e = em.find(Equipo.class, equipo); 
		return e;
		
		
	
	}
	
	
	public boolean borrarEquipo(Equipo equipo){

		try {			
			Equipo equipoBorrar = em.getReference(Equipo.class, equipo.getNombreE());
			em.remove(equipoBorrar);
			
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}


	@Override
	public boolean asignarUsuario(Usuario u,Equipo e) {
		try {
			
			e.setManager(u);
			
			em.merge(e);
			return true;
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		return false;
	
	}


	@Override
	public boolean guardarCambiosEquipo(Equipo equipo) {
		try{
		
			em.merge(equipo);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean asignarLiga(Liga l, Equipo e) {
		try {
			
			e.setLiga(l);
			
			em.merge(e);
			
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return false;

	}

	@Override
	public List<Equipo> listarEquipos(Liga laliga) {

		try {
			String nombreLiga = laliga.getNombreLiga();
			List<Equipo> equipos = em.createQuery("Select e FROM Equipo e WHERE e.liga = '"+nombreLiga+"'",Equipo.class).getResultList();
			return equipos;
		} catch (Exception ex) {
			ex.printStackTrace();

		}
			return null;
		}
	

	public  void cambiarCaptial(String nombreE, int i) {
		try {
		Equipo e= em.find(Equipo.class,nombreE);
		e.setCapital(i);
		em.merge(e);
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		
	}
	
	public String darCiudad(String equipoName) {
		String nombreC = "";
		
		try {
			
		  Equipo e = em.find(Equipo.class, equipoName);			 
		  nombreC = e.getCiudad().getCiudad();

		  
		} catch (Exception ex) {
			ex.printStackTrace();
	
		}
		  
		  return nombreC;
	}



	public Double buscarCapital(String equipoName) {
		
		Double capital = 0.0;
		
		try {
			Equipo e= em.find(Equipo.class,equipoName);
			capital = e.getCapital();
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		
		return capital;
	}



	public boolean existeEquipo(String equipoName) {
	
		boolean existe = false;
		try {
			
			Equipo e = em.find(Equipo.class, equipoName);	
			if(e!=null){
				existe=true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return existe;
	}


	public List<String> darZona(String equipoName) {
	
		List<String> zona = new ArrayList<String>();
		
		try {
			
		  Equipo e = em.find(Equipo.class, equipoName);		
		  zona.add(e.getCiudad().getCiudad());
		  zona.add(e.getCiudad().getZonaGeografica());
		

		  
		} catch (Exception ex) {
			ex.printStackTrace();
	
		}
		  
		  return zona;
	}

}
