package controladores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;


import javax.ejb.Stateless;









import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import dominio.Equipo;
import dominio.EventosPartido;
import dominio.Liga;
import dominio.Partido;
import dominio.Usuario;
import persistencia.IEquipoDAO;
import persistencia.ILigaDAO;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LigaController implements ILigaController {

	@EJB
	private ILigaDAO LigaDAO;
	@EJB
	private IEquipoController iec;
	@EJB
	private IUsuarioController iuc;
	
	public boolean guardarLiga(String nombreLiga) {
		try{
				
			
			Liga l = new Liga(nombreLiga);
			// Aca es donde vos tenes que elegir una fecha por defecto, yo le deje esa del 2011
			
			Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
			Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
			
			l.setFechaInicio(d1);
			l.setFechaFinalizado(d2);
			l.setFinalizado(false);
			l.setNroEq(0);
			return LigaDAO.guardarLiga(l);		
		
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean unirseLiga(Liga liga, String Eqnombre) {
		try{
				
			Equipo e = iec.buscarEquipo(Eqnombre);
			e.setLiga(liga);
			// Aca es donde vos tenes que elegir una fecha por defecto, yo le deje esa del 2011
		
			
				return true;
		
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	public boolean existeLiga(String nombreLiga) {
		
		try{
			return LigaDAO.existeLiga(new Liga(nombreLiga));
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
public List<Liga> listarLigas() {
		
		List<Liga> ligas = LigaDAO.listarLigas();
		return ligas;
		  
	}
	

public Liga darLiga(String nombreLiga){
	 {
			try{
						
				Liga l = LigaDAO.buscarLiga(nombreLiga);
				return l;
				}
				catch(Exception ex){
					ex.printStackTrace();
				
				}
				
			return null;	
			
			}
	
}

public Integer darIdLiga(String nombreLiga){
	
	Integer s = LigaDAO.darIDLiga(nombreLiga);
	return s;
	
}

@Override
public void sumarEquipo(Liga laliga) {
	try{
		
		LigaDAO.sumoEquipo(laliga);
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		
		}
		
	
	}


public void crearFixture(Equipo Eq1, Equipo Eq2, Equipo Eq3, Equipo Eq4) {
	
	
try{      
		//////////////////////////////Fecha1//////////////////////////////////////////
	
			//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
			//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
			 Liga l = Eq1.getLiga();
			 String liga = l.getNombreLiga();
			 
		//	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			Date d1 = new Date();
			d1.setHours(17);
			d1.setMinutes(0);
			d1.setSeconds(0);
			

			System.out.println("Sale la fechaaaaaaaaaaaaaaaaaaaaaa");
			System.out.println(d1);
			
			Date d2 = new Date(d1.getTime() + (1000 * 60 * 60 * 24));
			Date d3 = new Date(d2.getTime() + (1000 * 60 * 60 * 24));
			Date d4 = new Date(d3.getTime() + (1000 * 60 * 60 * 24));
			Date d5 = new Date(d4.getTime() + (1000 * 60 * 60 * 24));
			Date d6 = new Date(d5.getTime() + (1000 * 60 * 60 * 24));
			
		//	String miFecha = dateFormat.format(d1);
			
		//	Date date = dateFormat.parse(miFecha);
			
			
			Partido p1 = new Partido(Eq1,Eq2,d1,Eq1.getNombreE(),liga);		
			System.out.println("Liga Controller "+p1.getEquipo1().getNombreE());
		   
			LigaDAO.guardarPartido(p1);	
		    
		    Partido p2 = new Partido(Eq3,Eq4,d1,Eq3.getNombreE(),liga);		
		    LigaDAO.guardarPartido(p2);
		    
		    //////////////////////////////Fecha2//////////////////////////////////////////
		    	//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	
		    	
		    	
		    	
		    	
		    	
		    	Partido p3 = new Partido(Eq1,Eq3,d2,Eq1.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p3);	
		    	Partido p4 = new Partido(Eq2,Eq4,d2,Eq2.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p4);
		    
		    	 //////////////////////////////Fecha3//////////////////////////////////////////
		    	//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	
		    	
		    	
		    	
		    	Partido p5 = new Partido(Eq4,Eq1,d3,Eq4.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p5);	

		    	Partido p6 = new Partido(Eq3,Eq2,d3,Eq3.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p6);	
		    	
		    	 //////////////////////////////Fecha4//////////////////////////////////////////
		    	//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	
		    	
		    	Partido p7 = new Partido(Eq2,Eq1,d4,Eq2.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p7);	

		    	Partido p8 = new Partido(Eq4,Eq3,d4,Eq4.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p8);
		    	
		    	 //////////////////////////////Fecha5//////////////////////////////////////////
		    	//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	
		    	
		    	
		    	Partido p9 = new Partido(Eq3,Eq1,d5,Eq3.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p9);	

		    	Partido p10 = new Partido(Eq4,Eq2,d5,Eq4.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p10);
		    	
		    	 //////////////////////////////Fecha6//////////////////////////////////////////
		    	//Date d1 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	//Date d2 = (Date) new SimpleDateFormat("MM/dd/yyyy").parse("08/16/2011");
		    	
		    	
		    	Partido p11 = new Partido(Eq1,Eq4,d6,Eq1.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p11);	

		    	Partido p12 = new Partido(Eq2,Eq3,d6,Eq2.getNombreE(),liga);		
		    	LigaDAO.guardarPartido(p12);
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		
		}
		
	
	}

public void ganadorCampeonato(Liga laLiga){
	
	 String nombreLiga = laLiga.getNombreLiga();
	 List<Partido> partidos = resultados(nombreLiga);
	 List<Equipo> equipos = darEquiposLiga(nombreLiga);
	 
	 Equipo Eq1 = equipos.get(0);
	 Equipo Eq2 = equipos.get(1);
	 Equipo Eq3 = equipos.get(2);
	 Equipo Eq4 = equipos.get(3);
	 
	 int PuntosEq1 = 0;
	 int PuntosEq2 = 0;
	 int PuntosEq3 = 0;
	 int PuntosEq4 = 0;
	 
	 int CantidadGolesEq1 = 0;
	 int CantidadGolesEq2 = 0;
	 int CantidadGolesEq3 = 0;
	 int CantidadGolesEq4 = 0;
	 
	 Partido P1 = partidos.get(0);
	 Partido P2 = partidos.get(1);
	 Partido P3 = partidos.get(2);
	 Partido P4 = partidos.get(3);
	 Partido P5 = partidos.get(4);
	 Partido P6 = partidos.get(5);
	 Partido P7 = partidos.get(6);
	 Partido P8 = partidos.get(7);
	 Partido P9 = partidos.get(8);
	 Partido P10 = partidos.get(9);
	 Partido P11 = partidos.get(10);
	 Partido P12 = partidos.get(11);
	 
	 if((P1.isFinalizado()==true)&&(P2.isFinalizado()==true)&&(P3.isFinalizado()==true)&&(P4.isFinalizado()==true)&&(P5.isFinalizado()==true)&&(P6.isFinalizado()==true)&&(P7.isFinalizado()==true)&&(P8.isFinalizado()==true)&&(P9.isFinalizado()==true)&&(P10.isFinalizado()==true)&&(P11.isFinalizado()==true)&&(P12.isFinalizado()==true)){
	 
		 
		 laLiga.setFinalizado(true);
		 LigaDAO.modificarLiga(laLiga);
		 
		 
	 //////////////////////Partido1/////////////////////
	 if(P1.getGolesL()> P1.getGolesV()){
		 //GANO EQUIPO1 
		 PuntosEq1 = PuntosEq1 +3;
		 CantidadGolesEq1 = CantidadGolesEq1 + P1.getGolesL();
		 CantidadGolesEq2 = CantidadGolesEq2 + P1.getGolesV();
	 }
	 else if(P1.getGolesL()< P1.getGolesV()){
		 //Gano Eq2
		 PuntosEq2 = PuntosEq2 +3; 
		 CantidadGolesEq1 = CantidadGolesEq1 + P1.getGolesL();
		 CantidadGolesEq2 = CantidadGolesEq2 + P1.getGolesV();
	 }
	 else{
		 //empataron
		 PuntosEq1 = PuntosEq1 +1;
		 PuntosEq2 = PuntosEq2 +1;
		 CantidadGolesEq1 = CantidadGolesEq1 + P1.getGolesL();
		 CantidadGolesEq2 = CantidadGolesEq2 + P1.getGolesV();
	 }
	 //////////////////////Partido2/////////////////////
	 if(P2.getGolesL()> P2.getGolesV()){
		 //GANO EQUIPO1 
		 PuntosEq3 = PuntosEq3 +3;
		 CantidadGolesEq3 = CantidadGolesEq3 +P2.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 +P2.getGolesV();
	 }
	 else if(P2.getGolesL()< P2.getGolesV()){
		 //Gano Eq2
		 PuntosEq4 = PuntosEq4 +3; 
		 CantidadGolesEq3 = CantidadGolesEq3 +P2.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 +P2.getGolesV();
	 }
	 else{
		 //empataron
		 PuntosEq3 = PuntosEq3 +1;
		 PuntosEq4 = PuntosEq4 +1;
		 CantidadGolesEq3 = CantidadGolesEq3 +P2.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 +P2.getGolesV();
	 }
	 //////////////////////Partido3/////////////////////
	 if(P3.getGolesL()> P3.getGolesV()){
		 //GANO EQUIPO1 
		 PuntosEq1 = PuntosEq1 +3;
		 CantidadGolesEq1 = CantidadGolesEq1 + P3.getGolesL();
		 CantidadGolesEq3 = CantidadGolesEq3 +P3.getGolesV();
	 }
	 else if(P3.getGolesL()< P3.getGolesV()){
		 //Gano Eq2
		 PuntosEq3 = PuntosEq3 +3; 
		 CantidadGolesEq1 = CantidadGolesEq1 + P3.getGolesL();
		 CantidadGolesEq3 = CantidadGolesEq3 +P3.getGolesV();
	 }
	 else{
		 //empataron
		 PuntosEq3 = PuntosEq3 +1;
		 PuntosEq1 = PuntosEq1 +1;
		 CantidadGolesEq1 = CantidadGolesEq1 + P3.getGolesL();
		 CantidadGolesEq3 = CantidadGolesEq3 +P3.getGolesV();
	 }
	 
	 
	 //////////////////////Partido4/////////////////////
	 if(P4.getGolesL()> P4.getGolesV()){
		 //GANO EQUIPO1 
		 PuntosEq2 = PuntosEq2 +3;
		 CantidadGolesEq2 = CantidadGolesEq2 + P4.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 + P4.getGolesV();
	 }
	 else if(P4.getGolesL()< P4.getGolesV()){
		 //Gano Eq2
		 PuntosEq4 = PuntosEq4 +3; 
		 CantidadGolesEq2 = CantidadGolesEq2 + P4.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 + P4.getGolesV();
	 }
	 else{
		 //empataron
		 PuntosEq2 = PuntosEq2 +1;
		 PuntosEq4 = PuntosEq4 +1;
		 CantidadGolesEq2 = CantidadGolesEq2 + P4.getGolesL();
		 CantidadGolesEq4 = CantidadGolesEq4 + P4.getGolesV();
	 }
	 //////////////////////Partido5/////////////////////
	 if(P5.getGolesL()> P5.getGolesV()){
		 //GANO EQUIPO1 
		 PuntosEq4 = PuntosEq4 +3;
		 CantidadGolesEq4 = CantidadGolesEq4 + P5.getGolesL();
		 CantidadGolesEq1 = CantidadGolesEq1 + P5.getGolesV();
	 }
	 else if(P5.getGolesL()< P5.getGolesV()){
		 //Gano Eq2
		 PuntosEq1 = PuntosEq1 +3; 
		 CantidadGolesEq4 = CantidadGolesEq4 + P5.getGolesL();
		 CantidadGolesEq1 = CantidadGolesEq1 + P5.getGolesV();
	 }
	 else{
		 //empataron
		 PuntosEq1 = PuntosEq1 +1;
		 PuntosEq4 = PuntosEq4 +1;
		 CantidadGolesEq4 = CantidadGolesEq4 + P5.getGolesL();
		 CantidadGolesEq1 = CantidadGolesEq1 + P5.getGolesV();
	 }
		//////////////////////Partido6/////////////////////
		if(P6.getGolesL()> P6.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq3 = PuntosEq3 +3;
		CantidadGolesEq3 = CantidadGolesEq3 + P6.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P6.getGolesV();
		}
		else if(P6.getGolesL()< P6.getGolesV()){
		//Gano Eq2
		PuntosEq2 = PuntosEq2 +3;
		CantidadGolesEq3 = CantidadGolesEq3 + P6.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P6.getGolesV();
		}
		else{
		//empataron
		PuntosEq3 = PuntosEq3 +1;
		PuntosEq2 = PuntosEq2 +1;
		CantidadGolesEq3 = CantidadGolesEq3 + P6.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P6.getGolesV();
		}
		//////////////////////Partido7/////////////////////
		if(P7.getGolesL()> P7.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq2 = PuntosEq2 +3;
		CantidadGolesEq2 = CantidadGolesEq2 + P7.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P7.getGolesV();
		}
		else if(P7.getGolesL()< P7.getGolesV()){
		//Gano Eq2
		PuntosEq1 = PuntosEq1 +3; 
		CantidadGolesEq2 = CantidadGolesEq2 + P7.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P7.getGolesV();
		}
		else{
		//empataron
		PuntosEq1 = PuntosEq1 +1;
		PuntosEq2 = PuntosEq2 +1;
		CantidadGolesEq2 = CantidadGolesEq2 + P7.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P7.getGolesV();
		}
		//////////////////////Partido8/////////////////////
		if(P8.getGolesL()> P8.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq4 = PuntosEq4 +3;
		CantidadGolesEq4 = CantidadGolesEq4 + P8.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P8.getGolesV();
		}
		else if(P8.getGolesL()< P8.getGolesV()){
		//Gano Eq2
		PuntosEq3 = PuntosEq3 +3; 
		CantidadGolesEq4 = CantidadGolesEq4 + P8.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P8.getGolesV();
		}
		else{
		//empataron
		PuntosEq4 = PuntosEq4 +1;
		PuntosEq3 = PuntosEq3 +1;
		CantidadGolesEq4 = CantidadGolesEq4 + P8.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P8.getGolesV();
		}
		//////////////////////Partido9/////////////////////
		if(P9.getGolesL()> P9.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq3 = PuntosEq3 +3;
		CantidadGolesEq3 = CantidadGolesEq3 + P9.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P9.getGolesV();
		}
		else if(P9.getGolesL()< P9.getGolesV()){
		//Gano Eq2
		PuntosEq1 = PuntosEq1 +3; 
		CantidadGolesEq3 = CantidadGolesEq3 + P9.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P9.getGolesV();
		}
		else{
		//empataron
		PuntosEq1 = PuntosEq1 +1;
		PuntosEq3 = PuntosEq3 +1;
		CantidadGolesEq3 = CantidadGolesEq3 + P9.getGolesL();
		CantidadGolesEq1 = CantidadGolesEq1 + P9.getGolesV();
		}
		//////////////////////Partido10/////////////////////
		if(P10.getGolesL()> P10.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq4 = PuntosEq4 +3;
		CantidadGolesEq4 = CantidadGolesEq4 + P10.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P10.getGolesV();
		}
		else if(P10.getGolesL()< P10.getGolesV()){
		//Gano Eq2
		PuntosEq2 = PuntosEq2 +3; 
		CantidadGolesEq4 = CantidadGolesEq4 + P10.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P10.getGolesV();
		}
		else{
		//empataron
		PuntosEq2 = PuntosEq2 +1;
		PuntosEq4 = PuntosEq4 +1;
		CantidadGolesEq4 = CantidadGolesEq4 + P10.getGolesL();
		CantidadGolesEq2 = CantidadGolesEq2 + P10.getGolesV();
		}
		//////////////////////Partido11/////////////////////
		if(P11.getGolesL()> P11.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq1 = PuntosEq1 +3;
		CantidadGolesEq1 = CantidadGolesEq1 + P11.getGolesL();
		CantidadGolesEq4 = CantidadGolesEq4 + P11.getGolesV();
		}
		else if(P11.getGolesL()< P11.getGolesV()){
		//Gano Eq2
		PuntosEq4 = PuntosEq4 +3;
		CantidadGolesEq1 = CantidadGolesEq1 + P11.getGolesL();
		CantidadGolesEq4 = CantidadGolesEq4 + P11.getGolesV();
		}
		else{
		//empataron
		PuntosEq1 = PuntosEq1 +1;
		PuntosEq4 = PuntosEq4 +1;
		CantidadGolesEq1 = CantidadGolesEq1 + P11.getGolesL();
		CantidadGolesEq4 = CantidadGolesEq4 + P11.getGolesV();
		
		}
		//////////////////////Partido12/////////////////////
		if(P12.getGolesL()> P12.getGolesV()){
		//GANO EQUIPO1 
		PuntosEq2 = PuntosEq2 +3;
		CantidadGolesEq2 = CantidadGolesEq2 + P12.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P12.getGolesV();
		
		}
		else if(P12.getGolesL()< P12.getGolesV()){
		//Gano Eq2
		PuntosEq3 = PuntosEq3 +3; 
		CantidadGolesEq2 = CantidadGolesEq2 + P12.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P12.getGolesV();
		}
		else{
		//empataron
		PuntosEq2 = PuntosEq2 +1;
		PuntosEq3 = PuntosEq3 +1;
		CantidadGolesEq2 = CantidadGolesEq2 + P12.getGolesL();
		CantidadGolesEq3 = CantidadGolesEq3 + P12.getGolesV();
		}
		
		
		Random rnd = new Random();
			Equipo equipoIzquierda;
			Equipo equipoDerecha;
			Equipo equipoGanador;
			Integer puntosEquipoIzquierda = 0;
			Integer puntosEquipoDerecha = 0;
			Integer golesEquipoIzquierda = 0;
			Integer golesEquipoDerecha = 0;
		
			/*	
			System.out.println(Eq1.getNombreE()+ " Puntos del equipo 1: "+PuntosEq1 );
			System.out.println(Eq2.getNombreE()+ " Puntos del equipo 2: "+PuntosEq2 );
			System.out.println(Eq3.getNombreE()+ " Puntos del equipo 3: "+PuntosEq3 );
			System.out.println(Eq4.getNombreE()+ " Puntos del equipo 4: "+PuntosEq4 );
			
			System.out.println(Eq1.getNombreE()+ " Goles del equipo 1: "+CantidadGolesEq1 );
			System.out.println(Eq2.getNombreE()+ " Goles del equipo 2: "+CantidadGolesEq2 );
			System.out.println(Eq3.getNombreE()+ " Goles del equipo 3: "+CantidadGolesEq3 );
			System.out.println(Eq4.getNombreE()+ " Goles del equipo 4: "+CantidadGolesEq4 );
		*/	
			int equipoIzq = darMayor(PuntosEq1,PuntosEq2);
						
			if(equipoIzq == 1){
				//System.out.println("El equipo que gano por puntos aca es: "+Eq1.getNombreE() );
				
				equipoIzquierda = Eq1;
				puntosEquipoIzquierda = PuntosEq1;
				golesEquipoIzquierda = CantidadGolesEq1;
				// Equipo 1 tiene mas puntos
			}else if(equipoIzq == 2){
				//System.out.println("El equipo que gano por puntos aca es: "+Eq2.getNombreE() );			
				
				equipoIzquierda = Eq2;
				puntosEquipoIzquierda = PuntosEq2;
				golesEquipoIzquierda = CantidadGolesEq2;
				// Equipo 2 tiene mas puntos
			}else{
				//System.out.println("Los puntos son iguales: "+Eq1.getNombreE() + " - "+ Eq2.getNombreE() );
				
				equipoIzq = darMayor(CantidadGolesEq1, CantidadGolesEq2);
				
				
				if(equipoIzq == 1){
					// se que equipo 1 tiene mas goles que equipo 2
					equipoIzquierda = Eq1;
					puntosEquipoIzquierda = PuntosEq1;
					golesEquipoIzquierda = CantidadGolesEq1;
				//	System.out.println("El ganador de puntos iguales es: "+Eq1.getNombreE());
										
				}else if (equipoIzq == 2){
					//System.out.println("El ganador de puntos iguales es: "+Eq2.getNombreE());
					equipoIzquierda = Eq2;
					puntosEquipoIzquierda = PuntosEq2;
					golesEquipoIzquierda = CantidadGolesEq2;
				}else{
				//	System.out.println("Se da ganador random:");
					equipoIzq = 1 + rnd.nextInt(2);	
					if(equipoIzq == 1){
						
					//	System.out.println("Ganador random: "+Eq1.getNombreE());
						equipoIzquierda = Eq1;
						puntosEquipoIzquierda = PuntosEq1;
						golesEquipoIzquierda = CantidadGolesEq1;
					}else{
					//	System.out.println("Ganador random: "+Eq2.getNombreE());
						equipoIzquierda = Eq2;
						puntosEquipoIzquierda = PuntosEq2;
						golesEquipoIzquierda = CantidadGolesEq2;
					}
				}
				
				
				
				/// debo ver sus goles
			}
			
			int equipoDer = darMayor(PuntosEq3,PuntosEq4);
		
			if(equipoDer == 1){
				//System.out.println("El equipo que gano por puntos aca es: "+Eq3.getNombreE() );
				equipoDerecha = Eq3;
				puntosEquipoDerecha = PuntosEq3;
				golesEquipoDerecha = CantidadGolesEq3;
				// Equipo 1 tiene mas puntos
			}else if(equipoDer == 2){
				//System.out.println("El equipo que gano por puntos aca es: "+Eq4.getNombreE() );
				equipoDerecha = Eq4;
				puntosEquipoDerecha = PuntosEq4;
				golesEquipoDerecha = CantidadGolesEq4;
				// Equipo 2 tiene mas puntos
			}else{
				equipoDer = darMayor(CantidadGolesEq3, CantidadGolesEq4);
				
				if(equipoDer == 1){
					// se que equipo 1 tiene mas goles que equipo 2
					equipoDerecha = Eq3;
					puntosEquipoDerecha = PuntosEq3;
					golesEquipoDerecha = CantidadGolesEq3;
				}else if (equipoDer == 2){
					equipoDerecha = Eq4;
					puntosEquipoDerecha = PuntosEq4;
					golesEquipoDerecha = CantidadGolesEq4;
				}else{					
					
					equipoDer = 1 + rnd.nextInt(2);	
					
					if(equipoDer == 1){
						equipoDerecha = Eq3;
						puntosEquipoDerecha = PuntosEq3;
						golesEquipoDerecha = CantidadGolesEq3;
					}else{
						equipoDerecha = Eq4;
						puntosEquipoDerecha = PuntosEq4;
						golesEquipoDerecha = CantidadGolesEq4;
					}
				}
				
			}
	
			/// aca en enteros 
		//	int resultado = darMayor(equipoIzq, equipoDer);
			int resultado = darMayor(puntosEquipoIzquierda, puntosEquipoDerecha);
			
			
			
			if(resultado == 1){
				equipoGanador = equipoIzquierda;
			}else if(resultado == 2){
				equipoGanador = equipoDerecha;
			}else{
				
				resultado = darMayor(golesEquipoIzquierda, golesEquipoDerecha);
				
				if(resultado == 1){
					equipoGanador = equipoIzquierda;
				}else if(resultado == 2){
					equipoGanador = equipoDerecha;
				}else{
				
					resultado = 1 + rnd.nextInt(2);	
					
					if(resultado == 1){
						equipoGanador = equipoIzquierda;
						
					}else{
						equipoGanador = equipoDerecha;
					}
				}
			}
			
			///
			
			Double Plataactual =  equipoGanador.getCapital();
			Double DineroGanado =  10000.0;
			Plataactual = Plataactual + DineroGanado;
			equipoGanador.setCapital(Plataactual);
			iuc.enviarMensaje("AdminFC", equipoGanador.getNombreE(), "Felicitaciones Acabas de Salir Campeon  de tu Liga y te hemos acreditado $10000 por este hecho.");
	 
	 }// Cierra si todos los partidos estan finalizados
		
}

public int darMayor(int n1, int n2){
	int res = 0;
	if(n1>n2){
		
		res = 1;
	}
	else if(n1<n2){
		res=2;
	}
		return res;
	
}


public List<Equipo> darEquiposLiga(String nombreLiga){
	
	return LigaDAO.darEquipos(nombreLiga);
}


@Override
public int cantidadEquipos(Liga laliga) {
	
	return LigaDAO.cantidadEquipos(laliga);
}

@Override
public Liga darLigaEq(String equipo) {
	
	return LigaDAO.darLigaEq(equipo);
}


public List<Partido> resultados(String miLiga) {
	
	List<Partido> partidos = new ArrayList();
	try{
		
		return partidos = LigaDAO.traerResultados(miLiga);		
		
	}catch(Exception e){
		e.printStackTrace();
		
	}
	return null;
}

public List<Partido> listarPartidos() {
	
	return LigaDAO.listarPartidos();
}


	


public Partido buscarPartidoEjectuando(String nick) {
	

	Usuario u = iuc.buscarUsuario(nick);	
	
	return LigaDAO.buscarPartido(u.getPartidojugando());
}


public List<EventosPartido> buscarEventos(Integer idPartido) {
	
	List<EventosPartido> eventos = new ArrayList<EventosPartido>();
	try{
		
		eventos = LigaDAO.buscarEventos(idPartido);		
		
	}catch(Exception e){
		e.printStackTrace();
		
	}
	return eventos;
}


public void partidoAmistoso(String equipo) {
	Equipo e= iec.buscarEquipo(equipo);
	Equipo bots=iec.buscarEquipo("BotsFC");
	
	if (bots!=null){
		Partido p1 = new Partido(e,bots,new Date(),e.getNombreE(),"AMISTOSO");	
		LigaDAO.guardarPartido(p1);	
	}
	
}

@Override
public List<Partido> partidosFinalizados(String nombreLiga) {
	List<Partido> partidos = new ArrayList();
	try{
		
		return partidos = LigaDAO.traerPartidosFinalizados(nombreLiga);		
		
	}catch(Exception e){
		e.printStackTrace();
		
	}
	return null;
}

}

/*	public boolean tieneTemporadas(){
		
		try{
			
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	*/





