package persistencia;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import dominio.Equipo;
import dominio.Formacion;

@Stateless
public class FormacionDAO implements IFormacionDAO {

	@javax.persistence.PersistenceContext(unitName = "JatrickAppServer")
	private javax.persistence.EntityManager em;

	public boolean guardarFormacion(Formacion formacion) {

		try {
			em.persist(formacion);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}

	public int getIdFormacion(String nombreE) {
		int id = 0;
		try {
			id = (Integer) em.createQuery(
					"Select f.id From Formacion f Where f.equipo = '" + nombreE
							+ "'").getSingleResult();
		} catch (Exception e) {

			e.printStackTrace();

		}
		return id;
	}

	public void modificarFormacion(Formacion f) {
		try {
			em.merge(f);
		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	@Override
	public Formacion getFormacion(Integer idFormacion) {
		
		Formacion f = new Formacion();
		try {
			
			f = em.find(Formacion.class,idFormacion);
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		
		return f;
	}

}
