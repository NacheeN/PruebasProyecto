package Wrappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;



public class WrapperLiga {
	
	
	@SerializedName("nombreLiga")
	private String nombreLiga;
	
	@SerializedName("finalizado")
	private boolean finalizado;
	
	@SerializedName("fechaInicio")
	private Date fechaInicio;
	
	@SerializedName("fechaFinalizado")
	private Date fechaFinalizado;
	
/*	@SerializedName("equipos")
	private List<WrapperEquipo> equipos;
	*/
	public WrapperLiga(){
	//	this.equipos = new ArrayList<WrapperEquipo>();
		
	}
	
	public WrapperLiga(String nombreLiga){
	
	this.nombreLiga = nombreLiga;
	
	
	}
	

	
	public Date getFechaFinalizado() {
		return fechaFinalizado;
	}
	
	public void setFechaFinalizado(Date fechaFinalizado) {
		this.fechaFinalizado = fechaFinalizado;
	}
	

	public String getNombreLiga() {
		return nombreLiga;
	}

	public void setNombreLiga(String nombreLiga) {
		this.nombreLiga = nombreLiga;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	/*
	public void addEquipo(WrapperEquipo wrapperEquipo) {
		this.equipos.add(wrapperEquipo);
		
	}
	
	public List<WrapperEquipo> getEquipos() {
		return equipos;
	}
	
	public void setEquipos(List<WrapperEquipo> equipos) {
		this.equipos = equipos;
	}
	*/
}