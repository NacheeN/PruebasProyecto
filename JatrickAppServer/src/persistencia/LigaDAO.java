package persistencia;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Equipo;
import dominio.EventosPartido;
import dominio.Liga;
import dominio.Partido;



@Stateless
public class LigaDAO implements ILigaDAO {
	
	@javax.persistence.PersistenceContext(unitName = "JatrickAppServer")
	private javax.persistence.EntityManager em;

	
	public boolean guardarLiga(Liga liga) {

		System.out.println("DAO: " + liga.getNombreLiga());

		try {
			System.out.println(liga.getFechaFinalizado().toString()
					+ " /// FIn_" + liga.getFechaInicio().toString() + "//"
					+ liga.getEquipos() + "//" + liga.getNombreLiga());
			em.persist(liga);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}

	public boolean existeLiga(Liga liga) {

		try {
			Liga l = em.find(Liga.class, liga.getNombreLiga()); // Si no se
																// encuentra a
																// la liga,
																// se retorna
																// NULL

			if ((l != null))
				return true;
			else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Liga> listarLigas() {
		
		try {
			List<Liga> ligas = em.createQuery("Select l From Liga l WHERE l.finalizado=false AND l.nroEq <= 3", Liga.class).getResultList();
		//	List<Liga> ligas = em.createQuery("SELECT l	FROM Liga l WHERE l.finalizado=false GROUP BY l.id HAVING COUNT(l) <= 2", Liga.class).getResultList();
			
	//		System.out.println(ligas);
			
			return ligas;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Liga getLiga(String nombreLiga) {

		return em.find(Liga.class, nombreLiga);
	}

	
	public Liga buscarLiga(String nombreLiga) {
		Liga l = em.find(Liga.class, nombreLiga);
		return l;

	}

	public Integer darIDLiga(String nombreLiga){
		
		try {
		int IDLiga = (Integer) em.createQuery("Select l.id From Liga l WHERE l.nombreLiga = '"+nombreLiga+"'").getSingleResult();

			
			return IDLiga;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void sumoEquipo(Liga laliga) {
		
		try {
			laliga.setNroEq(laliga.getNroEq()+1);
			em.merge(laliga);

				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardarPartido(Partido partido) {

		try {
			
			em.merge(partido);
		

		} catch (Exception e) {

			e.printStackTrace();

		}

	

	}

	@Override
	public int cantidadEquipos(Liga laliga) {
		
		try {
			String nombreLiga = laliga.getNombreLiga();
			int nro = (Integer) em.createQuery("Select l.nroEq From Liga l WHERE l.nombreLiga = '"+nombreLiga+"'").getSingleResult();

				
				return nro;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return 0;
		}

	@Override
	public Liga darLigaEq(String equipo) {
		

		try {
			
			Liga nombreLiga = (Liga) em.createQuery("Select e.liga From Equipo e WHERE e.nombreE = '"+equipo+"'").getSingleResult();

				return nombreLiga;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
		
	}

	@Override
	public List<Partido> traerResultados(String miLiga) {
		try{
			List<Partido> partidos = em.createQuery("Select p From Partido p WHERE p.nombreLiga='"+miLiga+"'", Partido.class).getResultList();
	/*		System.out.println(partidos.get(0).getFecha());
			System.out.println(partidos.get(0).getFecha().toString());
			System.out.println(partidos.get(0).getFecha().toGMTString());
			System.out.println(partidos.get(0).getFecha().toLocaleString());
			System.out.println(partidos.get(0).getFecha().getTimezoneOffset());

			System.out.println(partidos.get(0).getFecha().getHours());
			System.out.println(partidos.get(0).getFecha().getTime());
		*/
			return partidos;
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		return null;
	}

	@Override
	public List<Partido> listarPartidos() {
		
		
List<Partido> partidos = em.createQuery("Select p FROM Partido p where p.finalizado="+false ,Partido.class).getResultList();
		
		return partidos;
		
		
	}

	public List<Equipo> darEquipos(String nombreLiga) {
		try{
			List<Equipo> equipos = em.createQuery("Select e From Equipo e WHERE e.liga.nombreLiga = '"+nombreLiga+"'", Equipo.class).getResultList();
			
			return equipos;
		}catch(Exception e){
			e.printStackTrace();
			
		}
		 
		return null;
	}

public Partido buscarPartido(Integer partidoId) {
	try{
	
		Partido partido = em.find(Partido.class, partidoId);
		return partido;
	}catch(Exception e){
		e.printStackTrace();
		
	}
		return null;
	}

	
	public List<EventosPartido> buscarEventos(Integer idPartido) {
		
		try{
		//	System.out.println("ANTES QUERY"+idPartido);
			List<EventosPartido> eventos = em.createQuery("Select e From EventosPartido e WHERE e.Partido.id = "+idPartido, EventosPartido.class).getResultList();
			
		/*	for(EventosPartido e : eventos){
			System.out.println("DESPUES DE QUERY"+e.getEvento()+"/"+e.getResultado());
			}
			*/
			return eventos;
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		
		return null;
	}


	public void modificarLiga(Liga laLiga) {
		try{
			em.merge(laLiga);
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		
	}

	@Override
	public List<Partido> traerPartidosFinalizados(String nombreLiga) {
		try{
			List<Partido> partidos = em.createQuery("Select p From Partido p WHERE p.nombreLiga = '"+nombreLiga+"' AND p.finalizado = 1", Partido.class).getResultList();
			
			return partidos;
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		return null;
	}

	}

	

	
	
		
	


