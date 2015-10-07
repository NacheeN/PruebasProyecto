package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Entrenamiento;
import dominio.Equipo;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class entrenamientoDAO implements IEntrenamientoDAO {

	@javax.persistence.PersistenceContext(unitName="JatrickAppServer")
	private javax.persistence.EntityManager em;
	
	@Override
	public Entrenamiento buscarEntrenamiento(String entrenamiento) {
		
		
		Entrenamiento e = em.find(Entrenamiento.class, entrenamiento); 
		

		return e;
	}

}
