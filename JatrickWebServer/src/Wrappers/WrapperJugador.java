package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperJugador {
	

	@SerializedName("nombreJ")
	private String nombreJ;

	@SerializedName("fechaNac")
	private String fechaNac;

	@SerializedName("Tiro")
	private Integer tiro;

	@SerializedName("ataque")
	private Integer ataque;

	@SerializedName("defensa")
	private Integer defensa;

	@SerializedName("porteria")
	private Integer porteria;

	@SerializedName("tipo")
	private String tipo;

	@SerializedName("star")
	private boolean star;
	
	@SerializedName("vender")
	private boolean vender;
	
	@SerializedName("precio")
	private double precio;
	
	/*
	@SerializedName("equipo")
	private WrapperEquipo equipo;
	*/
	
	public WrapperJugador() {
		// TODO Auto-generated constructor stub
	}
	
	public WrapperJugador(String nombreJ,String fechaNac,Integer Tiro, Integer ataque, Integer defensa, Integer porteria,String tipo,boolean star,boolean vender,double precio) {
		this.nombreJ = nombreJ;
		this.fechaNac = fechaNac;
		this.tiro = Tiro;
		this.ataque = ataque;
		this.defensa = defensa;
		this.porteria = porteria;
		this.tipo = tipo;
		this.star=star;
		this.vender = vender;
		this.precio = precio;
	
	}


	public String getNombreJ() {
		return nombreJ;
	}


	public void setNombreJ(String nombreJ) {
		this.nombreJ = nombreJ;
	}


	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}


	public Integer getTiro() {
		return tiro;
	}
	
	public void setTiro(Integer tiro) {
		this.tiro = tiro;
	}


	public Integer getAtaque() {
		return ataque;
	}


	public void setAtaque(Integer ataque) {
		this.ataque = ataque;
	}


	public Integer getDefensa() {
		return defensa;
	}


	public void setDefensa(Integer defensa) {
		this.defensa = defensa;
	}


	public Integer getPorteria() {
		return porteria;
	}


	public void setPorteria(Integer porteria) {
		this.porteria = porteria;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public boolean isStar() {
		return star;
	}


	public void setStar(boolean star) {
		this.star = star;
	}
	
	public boolean isVender() {
		return vender;
	}

	public void setVender(boolean vender) {
		this.vender = vender;
	}
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String imprimirJugadorData(){
		
		return (this.nombreJ+" "+ this.tipo+" "+ this.ataque+" "+this.defensa+" "+this.tiro+" "+this.porteria+" "+this.star+" "+this.vender+" "+this.precio);
	}

}
