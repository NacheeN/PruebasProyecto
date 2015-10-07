package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperExperiencia {
	
	@SerializedName("ExpAtaque")
	private Integer ExpAtaque;

	@SerializedName("ExpTiro")
	private Integer ExpTiro;

	@SerializedName("ExpDefensa")
	private Integer ExpDefensa;

	@SerializedName("ExpPorteria")
	private Integer ExpPorteria;

	@SerializedName("nombreJ")
	private String nombreJ;

	
	public WrapperExperiencia() {
		// TODO Auto-generated constructor stub
	}
	
	public WrapperExperiencia(Integer expAtaque, Integer expTiro, Integer expDefensa, Integer expPorteria, String nombreJ  ) {
		this.ExpAtaque=expAtaque;
		this.ExpTiro=expTiro;
		this.ExpDefensa=expDefensa;
		this.ExpPorteria=expPorteria;
		this.nombreJ = nombreJ;
	}

	public Integer getExpAtaque() {
		return ExpAtaque;
	}


	public void setExpAtaque(Integer expAtaque) {
		this.ExpAtaque = expAtaque;
	}

	public Integer getExpTiro() {
		return ExpTiro;
	}


	public void setExpTiro(Integer expTiro) {
		this.ExpTiro = expTiro;
	}
	
	public Integer getExpDefensa() {
		return ExpDefensa;
	}

	public void setExpDefensa(Integer expDefensa) {
		this.ExpDefensa = expDefensa;
	}
	
	public Integer getExpPorteria() {
		return ExpPorteria;
	}


	public void setExpPorteria(Integer expPorteria) {
		this.ExpPorteria = expPorteria;
	}
	
	public String getNombreJ() {
		return nombreJ;
	}


	public void setNombreJ(String nombreJ) {
		this.nombreJ = nombreJ;
	}


	
	public String imprimirJugadorData(){
		
		return (this.ExpAtaque+" "+this.ExpTiro+" "+this.ExpDefensa+" "+this.ExpPorteria+" "+this.nombreJ);
	}

	

}
