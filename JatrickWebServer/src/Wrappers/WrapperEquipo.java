package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperEquipo {
	
	
	@SerializedName("nombreE")
	private String nombreE;
	
	@SerializedName("capital")
	private double capital;
	
	public WrapperEquipo() {}
	
	public WrapperEquipo(String nombre, double capital){
	
	this.nombreE = nombre;
	this.capital = capital;
	
	}
	
	
	public String getNombreE() {
		return nombreE;
	}

	public void setNombreE(String nombreE) {
		this.nombreE = nombreE;
	}

	
	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public String imprimirEquipoData(){
		
		return (this.nombreE+" "+ this.capital);
	}
	

	
	
	
	
	

}
