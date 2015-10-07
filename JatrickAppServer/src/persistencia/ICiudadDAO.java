package persistencia;

import javax.ejb.Local;

import dominio.Ciudad;
import dominio.Equipo;

@Local
public interface ICiudadDAO {
	
		
	public Ciudad darCiudad(Equipo equipo);
	

}