package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperMensaje {
	
	
	@SerializedName("EquipoEmisor")
	private String EquipoEmisor;
	
	@SerializedName("EquipoReceptor")
	private String EquipoReceptor;
	
	@SerializedName("Fecha")
	private String Fecha;
	
	@SerializedName("Descripcion")
	private String Descripcion;

	public WrapperMensaje(){};
	public WrapperMensaje(String equipoEmisor, String equipoReceptor,
			String fecha, String descripcion) {
		super();
		EquipoEmisor = equipoEmisor;
		EquipoReceptor = equipoReceptor;
		Fecha = fecha;
		Descripcion = descripcion;
	}

	public String getEquipoEmisor() {
		return EquipoEmisor;
	}

	public void setEquipoEmisor(String equipoEmisor) {
		EquipoEmisor = equipoEmisor;
	}

	public String getEquipoReceptor() {
		return EquipoReceptor;
	}

	public void setEquipoReceptor(String equipoReceptor) {
		EquipoReceptor = equipoReceptor;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	
	
	
	
	
	

}
