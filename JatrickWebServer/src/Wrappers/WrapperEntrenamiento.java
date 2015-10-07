package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperEntrenamiento {

	@SerializedName("nombre")
	private String nombre;
	
	@SerializedName("descripcion")
	private String descripcion;
	
	@SerializedName("entrenamientoAtaque")
	private int entrenamientoAtaque;
	
	@SerializedName("entrenamientoDefensa")
	private int entrenamientoDefensa;
	
	@SerializedName("entrenamientoTiro")
	private int entrenamientoTiro;
	
	@SerializedName("entrenamientoPorteria")
	private int entrenamientoPorteria;

	
	public WrapperEntrenamiento(){}
	
	public WrapperEntrenamiento(String nombre, String descripcion,
			int entrenamientoAtaque, int entrenamientoDefensa,
			int entrenamientoTiro, int entrenamientoPorteria) {
		
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.entrenamientoAtaque = entrenamientoAtaque;
		this.entrenamientoDefensa = entrenamientoDefensa;
		this.entrenamientoTiro = entrenamientoTiro;
		this.entrenamientoPorteria = entrenamientoPorteria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEntrenamientoAtaque() {
		return entrenamientoAtaque;
	}

	public void setEntrenamientoAtaque(int entrenamientoAtaque) {
		this.entrenamientoAtaque = entrenamientoAtaque;
	}

	public int getEntrenamientoDefensa() {
		return entrenamientoDefensa;
	}

	public void setEntrenamientoDefensa(int entrenamientoDefensa) {
		this.entrenamientoDefensa = entrenamientoDefensa;
	}

	public int getEntrenamientoTiro() {
		return entrenamientoTiro;
	}

	public void setEntrenamientoTiro(int entrenamientoTiro) {
		this.entrenamientoTiro = entrenamientoTiro;
	}

	public int getEntrenamientoPorteria() {
		return entrenamientoPorteria;
	}

	public void setEntrenamientoPorteria(int entrenamientoPorteria) {
		this.entrenamientoPorteria = entrenamientoPorteria;
	}
	
	
	
	
	
	
	
	
	
}
