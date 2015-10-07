package persistencia;

import java.util.Random;

import javax.ejb.Stateless;

import dominio.Ciudad;
import dominio.Equipo;




@Stateless
public class CiudadDAO implements ICiudadDAO {
	
	@javax.persistence.PersistenceContext(unitName="JatrickAppServer")
	private javax.persistence.EntityManager em;
	
	
    public Ciudad darCiudad(Equipo equipo) {
    	Random rnd = new Random();
    	//from + rndGenerator.nextInt(to - from + 1)
    	int num = 1 + rnd.nextInt(325);
    	
    	try {	
    		Ciudad c = em.find(Ciudad.class, num);
    		equipo.setCiudad(c);
    		return c;
    	} catch (Exception e) {

			e.printStackTrace();

		}
    	
		return null;
		
		
	
	}

}