package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.internal.Nullable;

@Entity
@Table(name = "formacion")
public class Formacion implements Serializable {

	private static final long serialVersionUID = 1L; // Mapping JPA

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	/*@Column(name = "alineacion", nullable = false)
	private String alineacion;
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Equipo equipo;

	/*
	 * @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) private
	 * Usuario usuario;
	 */

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "suplentes", joinColumns = { @JoinColumn(name = "ID_Formacion", referencedColumnName = "ID") })
	private List<Jugador> suplentes;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "delanteros", joinColumns = { @JoinColumn(name = "ID_Formacion", referencedColumnName = "ID") })
	private List<Jugador> delanteros;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "mediocampistas", joinColumns = { @JoinColumn(name = "ID_Formacion", referencedColumnName = "ID") })
	private List<Jugador> mediocampistas;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "defensas", joinColumns = { @JoinColumn(name = "ID_Formacion", referencedColumnName = "ID") })
	private List<Jugador> defensas;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "arquero", joinColumns = { @JoinColumn(name = "ID_Formacion", referencedColumnName = "ID") })
	private List<Jugador> arquero;
	
	/*@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Jugador arquero;
*/
	public Formacion() {
		this.delanteros = new ArrayList<Jugador>();
		this.mediocampistas = new ArrayList<Jugador>();
		this.defensas = new ArrayList<Jugador>();
		this.suplentes = new ArrayList<Jugador>();
		this.arquero = new ArrayList<Jugador>();

	}
	
	public int PromAtaque(){
		float resultado=0;
		int atacantes=0;
		int mediocampo=0;
		
		for(Jugador aux:delanteros){
			
			atacantes=atacantes+aux.getAtaque();
			
		}
		
		for(Jugador aux:mediocampistas){
			
			mediocampo=mediocampo+aux.getAtaque();
			
		}
		resultado=(atacantes+mediocampo)/2;
		
		return Math.round( resultado);
	}
	
	public int PromDef(){
		float resultado=0;
		int defensas=0;
		int mediocampo=0;
		
		for(Jugador aux:this.defensas){
			
			defensas=defensas+aux.getDefensa();
			
		}
		
		for(Jugador aux:mediocampistas){
			
			mediocampo=mediocampo+aux.getDefensa();
			
		}
		resultado=(defensas+mediocampo)/2;
		
		return Math.round( resultado);
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public List<Jugador> addDelantero(Jugador j) {

		this.delanteros.add(j);
		return this.delanteros;
	}

	public List<Jugador> getDelanteros() {
		return delanteros;
	}

	public void setDelanteros(List<Jugador> delanteros) {
		this.delanteros = delanteros;
	}

	public List<Jugador> addMediocampista(Jugador j) {

		this.mediocampistas.add(j);
		return this.mediocampistas;
	}

	public List<Jugador> getMediocampistas() {
		return mediocampistas;
	}

	public void setMediocampistas(List<Jugador> mediocampistas) {
		this.mediocampistas = mediocampistas;
	}

	public List<Jugador> addDefensa(Jugador j) {

		this.defensas.add(j);
		return this.defensas;
	}

	public List<Jugador> getDefensas() {
		return defensas;
	}

	public void setDefensas(List<Jugador> defensas) {
		this.defensas = defensas;
	}
/*
	public Jugador getArquero() {
		return arquero;
	}

	public void setArquero(Jugador arquero) {
		this.arquero = arquero;
	}
*/
	public List<Jugador> getArquero() {
		return arquero;
	}
	
	public void setArquero(List<Jugador> arquero) {
		this.arquero = arquero;
	}
	
	
	public List<Jugador> addArquero(Jugador j) {
		this.arquero.add(j);
		return this.arquero;
	}
	
	public List<Jugador> addSuplente(Jugador j) {

		this.suplentes.add(j);
		return this.suplentes;
	}

	public List<Jugador> getSuplentes() {
		return this.suplentes;
	}

	public void setSuplentes(List<Jugador> suplentes) {
		this.suplentes = suplentes;
	}
	
	/*
	 
	public String getAlineacion() {
		return alineacion;
	}

	public void setAlineacion(String alineacion) {
		this.alineacion = alineacion;
	}
*/
	
}