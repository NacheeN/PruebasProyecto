package Wrappers;

import com.google.gson.annotations.SerializedName;

public class WrapperEvento {

	@SerializedName("idEvento")
	private Integer idEvento;

	@SerializedName("Evento")
	private String Evento;

	@SerializedName("Descripcion")
	private String Descripcion;

	@SerializedName("FactorAleatorio1")
	private String FactorAleatorio1;

	@SerializedName("FactorAleatorio2")
	private String FactorAleatorio2;

	@SerializedName("Resultado")
	private String Resultado;

	@SerializedName("Partido")
	private Integer Partido;
	
	@SerializedName("EsGol")
	private String EsGol;
	
	public WrapperEvento() {
	}

	public WrapperEvento(String evento, String descripcion,
			String factorAleatorio1, String factorAleatorio2, String resultado,
			Integer partido) {
		super();
		Evento = evento;
		Descripcion = descripcion;
		FactorAleatorio1 = factorAleatorio1;
		FactorAleatorio2 = factorAleatorio2;
		Resultado = resultado;
		Partido = partido;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public String getEvento() {
		return Evento;
	}

	public void setEvento(String evento) {
		Evento = evento;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getFactorAleatorio1() {
		return FactorAleatorio1;
	}

	public void setFactorAleatorio1(String factorAleatorio1) {
		FactorAleatorio1 = factorAleatorio1;
	}

	public String getFactorAleatorio2() {
		return FactorAleatorio2;
	}

	public void setFactorAleatorio2(String factorAleatorio2) {
		FactorAleatorio2 = factorAleatorio2;
	}

	public String getResultado() {
		return Resultado;
	}

	public void setResultado(String resultado) {
		Resultado = resultado;
	}

	public Integer getPartido() {
		return Partido;
	}

	public void setPartido(Integer partido) {
		Partido = partido;
	}

	public String getEsGol() {
		return EsGol;
	}

	public void setEsGol(String esGol) {
		EsGol = esGol;
	}

}
