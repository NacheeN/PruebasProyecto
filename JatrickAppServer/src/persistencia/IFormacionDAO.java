package persistencia;

import javax.ejb.Local;

import dominio.Formacion;

@Local
public interface IFormacionDAO{

	public boolean guardarFormacion(Formacion formacion);
	public int getIdFormacion(String nombreE);
	public void modificarFormacion(Formacion f);
	public Formacion getFormacion(Integer id);

	
}
