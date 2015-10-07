package Wrappers;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class WrapperPartido {
	

	@SerializedName("idPartido")
	private Integer idPartido;
	@SerializedName("fecha")
	private Date fecha;
	@SerializedName("finalizado")
	private boolean finalizado;
	@SerializedName("golesL")
	private int golesL;
	@SerializedName("golesV")
	private int golesV;
	@SerializedName("local")
	private String local;
	@SerializedName("IdEquipo")
	private String IdEquipo;
	@SerializedName("IdEquipo2")
	private String IdEquipo2;
	
	
	
	
	public WrapperPartido() {
	}
	
	public WrapperPartido(String IdEquipo, String IdEquipo2, String local, Date fecha, boolean finalizado, int golesV, int golesL,Integer idPartido) {
	
		this.fecha = fecha;
		this.finalizado = finalizado;
		this.golesL = golesL;
		this.golesV = golesV;
		this.IdEquipo = IdEquipo;
		this.IdEquipo2 = IdEquipo2;
		this.local = local;
		this.idPartido = idPartido;
	}
	
	public Integer getIdPartido() {
		return idPartido;
	}
	
	public void setIdPartido(Integer idPartido) {
		this.idPartido = idPartido;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public void setIdEquipo(String idEquipo) {
		IdEquipo = idEquipo;
	}
	public void setIdEquipo2(String idEquipo2) {
		IdEquipo2 = idEquipo2;
	}
	public void setGolesV(int golesV) {
		this.golesV = golesV;
	}
	public void setGolesL(int golesL) {
		this.golesL = golesL;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isFinalizado() {
		return finalizado;
	}public String getLocal() {
		return local;
	}
	public String getIdEquipo2() {
		return IdEquipo2;
	}
	public String getIdEquipo() {
		return IdEquipo;
	}
	public int getGolesV() {
		return golesV;
	}
	public int getGolesL() {
		return golesL;
	}
	public Date getFecha() {
		return fecha;
	}
	
	
	
	
	

}