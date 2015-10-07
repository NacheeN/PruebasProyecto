package dominio;

import java.io.Serializable;
import java.sql.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jugador")
public class Jugador implements Serializable {	

	private static final long serialVersionUID = 1L; // Mapping JPA
	
	@Id
	@Column(name = "NombreJ", nullable = false)
	private String nombreJ;
	
	@Column(name = "FechaNac", nullable = false)
	private Date fechaNac;
	
	@Column(name = "Tiro", nullable = false)
	private Integer Tiro;
		
	@Column(name = "Ataque", nullable = false)
	private Integer ataque;

	@Column(name = "Defensa", nullable = false)
	private Integer defensa;
	
	@Column(name = "Porteria", nullable = false)
	private Integer porteria;
	
	@Column(name="Tipo", nullable = false)
	private String tipo;
	
	@Column(name="Estrella", nullable = false)
	private boolean star;
	
	@Column(name = "Vender", nullable = false)
	private boolean vender;

	@Column(name = "Precio", nullable = false)
	private double precio;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="Equipo",nullable = true)
	private Equipo equipo;
	
	
	
	public Jugador() {
	
	}
	
	public void cargarJugador(String nombre,String tipo,boolean star) {
		
		
		this.nombreJ = nombre;
		
		if (star){
			if("Defensa".compareTo(tipo)==0)
			{
				this.star = true;
				this.tipo = "Defensa";
				this.defensa= this.randInt(70,90)+5;
				this.ataque = this.randInt(50,60);
				this.porteria = 50;
				this.Tiro = this.randInt(70,80);
				this.vender = false;
				this.precio = 1000;
				
			}
			else if("Atacante".compareTo(tipo)==0)
			{
				this.star = true;
				this.tipo = "Atacante";
				this.defensa= this.randInt(50,60);
				this.ataque = this.randInt(75,90);			
				this.porteria = 50;
				this.Tiro = this.randInt(75,90);
				this.vender = false;
				this.precio = 1000;
				
			}
			else if("Mediocampista".compareTo(tipo)==0)
			{
				this.star = true;
				this.tipo = "Mediocampista";
				this.defensa= this.randInt(70,80);
				this.ataque = this.randInt(70,80);			
				this.porteria = 50;
				this.Tiro = this.randInt(70,90);
				this.vender = false;
				this.precio = 1000;
				
			}
			else
			{
				this.star = true;
				this.tipo = "Arquero";
				this.defensa= this.randInt(60,80);
				this.ataque = this.randInt(50,60);			
				this.porteria = this.randInt(75,90);			
				this.Tiro = 50;
				this.vender = false;
				this.precio = 1000;
			}
		}
		else{
			if("Defensa".compareTo(tipo)==0)
			{
				this.star = false;
				this.tipo = "Defensa";
				this.defensa= this.randInt(60,75);
				this.ataque = this.randInt(50,60);
				this.porteria = 50;
				this.Tiro = this.randInt(60,75);
				this.vender = false;
				this.precio = 1000;
			}
			else if("Atacante".compareTo(tipo)==0)
			{
				this.star = false;
				this.tipo = "Atacante";
				this.defensa= this.randInt(50,60);
				this.ataque = this.randInt(60,75);			
				this.porteria = 50;
				this.Tiro = this.randInt(60,75);
				this.vender = false;
				this.precio = 1000;
				
			}
			else if("Mediocampista".compareTo(tipo)==0)
			{
				this.star = false;
				this.tipo = "Mediocampista";
				this.defensa= this.randInt(60,75);
				this.ataque = this.randInt(60,75);			
				this.porteria = 50;
				this.Tiro = this.randInt(60,75);
				this.vender = false;
				this.precio = 1000;
				
			}
			else
			{
				this.star = false;
				this.tipo = "Arquero";
				this.defensa= this.randInt(50,60);
				this.ataque = this.randInt(50,60);			
				this.porteria = this.randInt(60,75);			
				this.Tiro = 50;
				this.vender = false;
				this.precio = 1000;
			}
			
			
			
		}
		
	}
	
	public double calcularPrecio(Integer Tiro, Integer ataque, Integer defensa, Integer porteria,String tipo){
		double precio=0;
		if("Defensa".compareTo(tipo)==0)
		{
			precio = defensa * 1000;
			
		}
		else if("Atacante".compareTo(tipo)==0)
		{
			precio= (Tiro + ataque)*1000;
			
		}
		else if("Mediocampista".compareTo(tipo)==0)
		{
			precio=(Tiro+ataque+defensa)*1000;
			
		}
		else
		{
			precio=1000*porteria;
		}
		
		return precio;
	}
	

/*
 boolean star = true;
 for (int i = 1;i<9;i++){
 	
 	jugador j = new jugador ("nombre","Defensa",star)
 	if (i >= 2){
 		star=false;
 	}
 	listajugadores.add(j);
 
 }
 
 

 
 
 */

private int randInt(int min, int max) {

    // NOTE: Usually this should be a field rather than a method
    // variable so that it is not re-seeded every call.
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}
	
	
	
	
	
	public Jugador(String nombreJ,Date fechaNac,Integer Tiro, Integer ataque, Integer defensa, Integer porteria,String tipo,boolean star, boolean vender, double precio) {
		this.nombreJ = nombreJ;
		this.fechaNac = fechaNac;
		this.Tiro = Tiro;
		this.ataque = ataque;
		this.defensa = defensa;
		this.porteria = porteria;
		this.tipo=tipo;
		this.star=star;
		this.vender=vender;
		this.precio=precio;
	}
	
	public Jugador(Jugador j){
		   
			this.nombreJ = j.getNombreJ();
		this.fechaNac = j.getFechaNac();
		this.Tiro = j.getTiro();
		this.ataque = j.getAtaque();
		this.defensa = j.getDefensa();
		this.porteria = j.getPorteria();
		this.tipo = j.getTipo();
		this.star = j.isStar();	
		this.vender = j.isVender();
		this.precio = j.getPrecio();
	}
	public String getNombreJ() {
		return nombreJ;
	}
	
	public void setNombreJ(String nombreJ) {
		this.nombreJ = nombreJ;
	}
	
	public Date getFechaNac() {
		return fechaNac;
	}
	
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	
	public Integer getTiro() {
		return Tiro;
	}
	
	public void setTiro(Integer Tiro) {
		this.Tiro = Tiro;
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
	
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setVender(boolean vender) {
		this.vender = vender;
	}

	public boolean isVender() {
		return vender;
	}
	
	public Equipo getEquipo() {
		return equipo;
	}
	
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	

    
   

}
   