package Wrappers;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class WrapperFormacion {

	@SerializedName("equipo")
	private WrapperEquipo equipo;

	@SerializedName("suplentes")
	private List<WrapperJugador> suplentes;

	@SerializedName("delanteros")
	private List<WrapperJugador> delanteros;

	@SerializedName("mediocampistas")
	private List<WrapperJugador> mediocampistas;

	@SerializedName("defensas")
	private List<WrapperJugador> defensas;

	@SerializedName("arquero")
	private List<WrapperJugador> arquero;
	
	public WrapperFormacion(){}

	public WrapperEquipo getEquipo() {
		return equipo;
	}

	public void setEquipo(WrapperEquipo equipo) {
		this.equipo = equipo;
	}

	public List<WrapperJugador> getSuplentes() {
		return suplentes;
	}

	public void setSuplentes(List<WrapperJugador> suplentes) {
		this.suplentes = suplentes;
	}

	public List<WrapperJugador> getDelanteros() {
		return delanteros;
	}

	public void setDelanteros(List<WrapperJugador> delanteros) {
		this.delanteros = delanteros;
	}

	public List<WrapperJugador> getMediocampistas() {
		return mediocampistas;
	}

	public void setMediocampistas(List<WrapperJugador> mediocampistas) {
		this.mediocampistas = mediocampistas;
	}

	public List<WrapperJugador> getDefensas() {
		return defensas;
	}

	public void setDefensas(List<WrapperJugador> defensas) {
		this.defensas = defensas;
	}

	public List<WrapperJugador> getArquero() {
		return arquero;
	}

	public void setArquero(List<WrapperJugador> arquero) {
		this.arquero = arquero;
	}
	
	
		
		
		
}