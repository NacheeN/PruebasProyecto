package persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;




import dominio.Experiencia;
import dominio.Jugador;



@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExperienciaDAO implements IExperienciaDAO {

	@javax.persistence.PersistenceContext(unitName="JatrickAppServer")
	private javax.persistence.EntityManager em;
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarExperiencia(Experiencia Experiencia){
			
			try {
				em.persist(Experiencia);
				return true;
				
			} catch (Exception e) {
			
				e.printStackTrace();
				
			}
			
			return false;
			
		}



	public Experiencia getExperiencia(String nombrej) {

	Experiencia ex=	(Experiencia) em.createQuery("SELECT e FROM Experiencia e where e.jugador='"+nombrej+"'", Experiencia.class).getSingleResult();
	return ex;	
	}
	
	public boolean guardarCambiosExp(Experiencia e) {
		try {
			
					
			em.merge(e);
			return true;
			
		}catch(Exception ex){
			ex.printStackTrace();
			
		}
		return false;
	
	}



	@Override
	public List<Experiencia> getJugadoresExperiencia(List<Jugador> jugadores) {
		List<Experiencia> exp = new ArrayList<Experiencia>();
		try{
			
		 	
			for (Jugador jg : jugadores){
				
				exp.add((Experiencia) em.createQuery("SELECT e FROM Experiencia e where e.jugador='"+jg.getNombreJ()+"'", Experiencia.class).getSingleResult());
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
		return exp;
	}

}
