package persistencia;

import javax.ejb.Local;

import dominio.Entrenamiento;

@Local
public interface IEntrenamientoDAO {

	public Entrenamiento buscarEntrenamiento(String entrenamiento);
}
