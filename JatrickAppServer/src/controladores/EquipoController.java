package controladores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import persistencia.EquipoDAO;
import persistencia.ICiudadDAO;
import persistencia.IEntrenamientoDAO;
import persistencia.IEquipoDAO;
import persistencia.IExperienciaDAO;
import persistencia.IFormacionDAO;
import persistencia.IJugadorDAO;
import persistencia.ILigaDAO;
import persistencia.IUsuarioDAO;
import persistencia.LigaDAO;
import dominio.Ciudad;
import dominio.Entrenamiento;
import dominio.Equipo;
import dominio.EventosPartido;
import dominio.Experiencia;
import dominio.Formacion;
import dominio.Jugador;
import dominio.Liga;
import dominio.Partido;
import dominio.Usuario;



@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EquipoController implements IEquipoController {
	
	@EJB
	private IEquipoDAO equipoDAO;
	@EJB
	private IUsuarioDAO usuarioDAO;
	@EJB
	private ICiudadDAO ciudadDAO;
	@EJB
	private IJugadorDAO jugadorDAO;
	@EJB
	private IExperienciaDAO ExperienciaDAO;
	@EJB
	private IEntrenamientoDAO EntrenamientoDAO;
	@EJB
	private IFormacionDAO formacionDAO;
	@EJB
	private ILigaDAO ligaDAO;
	
	
	public ArrayList<String> altaEquipo(Equipo equipo,String username){
		ArrayList<String> zona = new ArrayList<String>();
		try {

			 
                ///////////////Se le asigna y guarda la ciudad///////////////////
				Ciudad c = ciudadDAO.darCiudad(equipo);
				zona.add(c.getCiudad());
				zona.add(c.getZonaGeografica());
				
				///////////////Se le asignan los jugadores////////////////////// 
				List<Jugador> jugadores = jugadorDAO.asignarJugadores(equipo);
				///////////////////////////////////
			if(equipoDAO.guardarEquipo(equipo)){
				
				Usuario u=usuarioDAO.getUsuario(username);
				equipoDAO.asignarUsuario(u,equipo);
				usuarioDAO.asignarEquipo(u,equipo);
				
				// Le seteo al equipo una formaci�n por defecto

				Formacion f = new Formacion();
				f.setEquipo(equipo);
				equipo.setFormacion(f);
			  //f.setAlineacion("4-4-2");
				
				int atacantes = 0;
				int mediocampistas = 0;
				int defensas = 0;
				int arquero = 0;
				
				// Lista para ayudarme  a quitar a los seleccionados
				List<Jugador> aux = new ArrayList<Jugador>();
				
				
				for (Jugador j : jugadores) {
					System.out.println("Adentro foreach");
					if ( ("Atacante".equals(j.getTipo())) && (atacantes < 2) ) {
						
						System.out.println("Soy delantero");
						f.addDelantero(j);
						aux.add(j);
						atacantes++;
					//	jugadores.remove(j);
						
					} else if ( ("Mediocampista".equals(j.getTipo())) && (mediocampistas < 4) ) {
						System.out.println("Soy Mediocampista");
						f.addMediocampista(j);
						aux.add(j);
						mediocampistas++;
					//	jugadores.remove(j);

					} else if ( ("Defensa".equals(j.getTipo())) && (defensas < 4) ) {
						System.out.println("Soy Defensa");
						f.addDefensa(j);
						aux.add(j);
						defensas++;
					//	jugadores.remove(j);

					} else if ( ("Arquero".equals(j.getTipo())) && (arquero < 1) ) {
						
						System.out.println("Soy Arquero");
						f.addArquero(j);
						aux.add(j);
						arquero++;
					//	jugadores.remove(j);

					}

				}
				
				//// Quito los jugadores que son titulares
				
				for (Jugador j : aux) {
					
					jugadores.remove(j);
				}
	
				f.setSuplentes(jugadores);


				formacionDAO.guardarFormacion(f);

				
				
				return zona;
			}
			 
			 
			
			 
		} catch (Exception e) {
		
			e.printStackTrace();
			
		}
		
		return null;
	}
//////////////////////////////////////////////////////Intercambio////////////////////////////////////////////////////////////////////////	
////////////////////////////////Lista los jugadores de el equipo pasado ////////////////////
	public List<Jugador> misJugadores(String equipo){
		List<Jugador> jugadores = new ArrayList();
		try{
			Equipo miEquipo = equipoDAO.buscarEquipo(equipo); 
			System.out.println("Equipo Controller EquipoObj"+ miEquipo.getNombreE());
			jugadores = jugadorDAO.misJugadores(miEquipo);
			System.out.println("Equipo Controller TrajoJugadores"+ jugadores.toString());
			return jugadores;
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
		return null;
	
	}

/////////////////////////////Pone los jugadores pasados para vender /////////////////////////////////////    
	public boolean venderJugadores(List<Jugador> jugadores){
		try{
		
		
			return jugadorDAO.venderJugadores(jugadores);
		
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
		return false;
	
	
	}
///////////////////////////////Lista los jugadores a comprar///////////////////////////////////////
	public List<Jugador> jugadoresAComp(String miEquipo){
		List<Jugador> jugadores = new ArrayList();
		try{
			System.out.println("Equipo Controller Jugadores a comprar");
			jugadores = jugadorDAO.jugadoresAComp(miEquipo);
			System.out.println("Equipo Controller TrajoJugadores"+ jugadores.toString());
			return jugadores;
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
		return null;
	
	
	}
//////////////////////////////////Compra los jugadores pasados/////////////
	public boolean comprarJugadores(List<Jugador> jugadores,String equipo){
	
		System.out.println("Equipo compra" + equipo);
		double precioCompra=0;
		try{
			
			Equipo equipoCompra = equipoDAO.buscarEquipo(equipo);
			
			
			System.out.println("Equipo q compra :" + equipoCompra.getNombreE());
		
					
			Iterator<Jugador> it = jugadores.iterator();
			
			while(it.hasNext()){
				Jugador jugador = it.next();
				precioCompra=precioCompra+ jugador.getPrecio();
			
			}
			
			if(equipoCompra.getCapital()>=precioCompra){
			
				return jugadorDAO.comprarJugadores(jugadores,equipoCompra);
			
			}
			
			
		
		
		}catch(Exception e){
			e.printStackTrace();
		
		}
		
		return false;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////	
	
	public List<Equipo> listarEquipos() {
		
		List<Equipo> equipos = equipoDAO.listarEquipos();
		return equipos;
		  
	}

	
	public Equipo buscarEquipo(String equipo) {
		
		
	try{
				
		Equipo e =equipoDAO.buscarEquipo(equipo);
		return e;
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	return null;	
	
	}
	
	
	public boolean borrarEquipo(Equipo equipo){

		try {			
		    equipoDAO.borrarEquipo(equipo);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}
	
	
	//////////////////////////////////////////// ENTRENAMIENTO //////////////////////////////////
	

	public boolean EntrenarEquipo(String equipo) {
		
		
		try{
					
			Equipo e =equipoDAO.buscarEquipo(equipo);
	
			
			
			
			Entrenamiento en = e.getEntrenamiento();
			
			
			List<Jugador>jugadores=e.getJugadores();
			
			for(Jugador aux :jugadores){
				 
				
				
					Experiencia xp=ExperienciaDAO.getExperiencia(aux.getNombreJ());
			
					//ATAQUE
				if(((xp.getExpAtaque()+en.getEntrenamientoAtaque())>=100)&&(aux.getAtaque()<100)){
					
					aux.setAtaque(aux.getAtaque()+1);
					
					xp.setExpAtaque(0);
				}	
				else{
					xp.setExpAtaque(xp.getExpAtaque()+en.getEntrenamientoAtaque());
				}
				
				//DEFENSA
				if(((xp.getExpDefensa()+en.getEntrenamientoDefensa())>=100)&&(aux.getDefensa()<100)){
								
					aux.setDefensa(aux.getDefensa()+1);
					
					xp.setExpDefensa(0);
				}	
				else{
					xp.setExpDefensa(xp.getExpDefensa()+en.getEntrenamientoDefensa());
				}
	
				//TIRO
				if(((xp.getExpTiro()+en.getEntrenamientoTiro())>=100)&&(aux.getTiro()<100)){
					
					aux.setTiro(aux.getTiro()+1);
					xp.setExpTiro(0);
				}	
				else{
					xp.setExpTiro(xp.getExpTiro()+en.getEntrenamientoTiro());
				}
				//PORTERIA solo si es arquero
				if("Arquero".compareTo(aux.getTipo())==0){
					if(((xp.getExpPorteria()+en.getEntrenamientoPorteria())>=100)&&(aux.getPorteria()<100)){
						
						aux.setPorteria(aux.getPorteria()+1);
						xp.setExpPorteria(0);
					}	
					else{
						xp.setExpPorteria(xp.getExpPorteria()+en.getEntrenamientoPorteria());
					}			
				}
			
				
				jugadorDAO.guardarCambiosJugador(aux);
				ExperienciaDAO.guardarCambiosExp(xp);
			}
			
		}
			catch(Exception ex){
				ex.printStackTrace();
				 
				return false;
				
			}
			
		return true;	
		
		}

	public boolean setEntrenamiento(String entrenamiento,String equipo){
		
		try{
		
			
			
			Equipo eq=equipoDAO.buscarEquipo(equipo);
			
			
			
			Entrenamiento ent = EntrenamientoDAO.buscarEntrenamiento(entrenamiento);
			
		
			
			eq.setEntrenamiento(ent);
			
		
			equipoDAO.guardarCambiosEquipo(eq);
			
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	@Override
	public boolean simularPartido(Partido partido){
		//falta altura y tarjeta
		try{
			int gol1=0;
			int gol2=0;
		//Equipo equipo1 =equipoDAO.buscarEquipo("321FC");
		//Equipo equipo2 =equipoDAO.buscarEquipo("BandidoFC");
		
		//ArrayList<String> Comments=new ArrayList<String>();
		
			ArrayList<EventosPartido> eventos= new ArrayList<EventosPartido>();
		
		
		//0,1,2,3,4,5
			String[] StringAleatorioGolero=new String[]{"se ilumina","siente la prescencia divina","saca sus guantes de oro","activa la garra charrua","tiene una revelacion: Todo el mundo sue�a con marcar el gol perfecto en cambio yo siempre he so�ado con atajarlo. ","se le alinean los astros"};
			//0,1,2,3,4,5
			String[] StringAleatorioAtacante=new String[]{"escapa notablemente","queda exelentemente posicionado","encuentra su fuerza interior","se ilumina","siente una suave brisa","tiene viento a favor"};
			
			String[] StringAleatorioGol= new String[]{"Excelente gol","Una buena maniobra que termino en gol ","Golazo de cabeza","gol","gol, buena definicion","golazo"};
			
			String[] StringAleatorioAtajada= new String[]{"Excelente volada ","gran estirada"," atajada ","Buena atajada","Linda atajado","volada digna de trofeo"};
			
			Equipo equipo1= partido.getEquipo1();
			Equipo equipo2 = partido.getEquipo2();
			/*Ciudad locataria;
			
			if(partido.getLocal()== 1){
				
				locataria=equipo2.getCiudad();
				
			}
			else{
				locataria=equipo1.getCiudad();
			}
			*/
		//	get altura
			if((equipo1!=null)&&(equipo2!=null)){
			
			//cambiar a controlador
			
			usuarioDAO.EjecutandoPartido(equipo1.getManager(),true,partido.getId());
			
			usuarioDAO.EjecutandoPartido(equipo2.getManager(),true,partido.getId());
			
			Formacion formacion1 =equipo1.getFormacion();
			Formacion formacion2 = equipo2.getFormacion();
			//cantidad de atacantes y defensores

			int cantAtaqueEq1=formacion1.getDelanteros().size()+formacion1.getMediocampistas().size();
			
			int cantDefEq1=formacion1.getDefensas().size()+formacion1.getMediocampistas().size();
	
			int cantAtaqueEq2=formacion2.getDelanteros().size()+formacion2.getMediocampistas().size();
			int cantDefEq2=formacion2.getDefensas().size()+formacion2.getMediocampistas().size();
			
			//calculo promedio de ataque y defensa de cada uno
	
			int PromedioAtaque1= Math.round((formacion1.PromAtaque())/cantAtaqueEq1);
			
			int PromedioDefensa1= Math.round((formacion1.PromDef())/cantDefEq1);
			
			System.out.println("ALTURA:"+equipo1.getCiudad().getAltura());
			
			
			
			
			int PromedioAtaque2= RendimientoAltura((Math.round((formacion2.PromAtaque())/cantAtaqueEq2)),equipo1.getCiudad().getAltura());
			
			int PromedioDefensa2=RendimientoAltura((Math.round((formacion2.PromDef())/cantDefEq2)),equipo1.getCiudad().getAltura());
				
			int cantChanse1=calculoChanceGol(PromedioAtaque1);
			
			int cantChanse2=calculoChanceGol(PromedioAtaque2);

			
			//Probabilidad de tarjeta = Oportunidad de gol x (promedio potencia de jugadores defensores y mediocampistas)
			
			double falta1= PromedioAtaque2 - PromedioDefensa1;
			
			double falta2= PromedioAtaque1 - PromedioDefensa2;
			
			////Cambiar de cantidad de tarjetas a cantidad de faltas por equipo y poner 
			int cantFaltas1=calculoTarjetas(falta1);
			int cantFaltas2=calculoTarjetas(falta2);
			
			
			
			int cantLesionados=0;
			//Primer dado para ver si hay lesionados
			for (int i=0;i<3;i++){
				int dado=(randInt(1, 1000));
				if (dado<= 50){
					cantLesionados=cantLesionados+1;
				}
				
			}
			
			System.out.println("cantFaltas1:"+cantFaltas1);
			
			System.out.println("cantFaltas2:"+cantFaltas2);
			
			System.out.println("cantchance1:"+cantChanse1);
			
			System.out.println("cantchance2:"+cantChanse2);
			
			System.out.println("cantLesionados:"+cantLesionados);
			
			
			
			int incidencias=cantFaltas1+cantFaltas2+cantChanse1+cantChanse2+cantLesionados;
			ArrayList<String> amarillas=new ArrayList<String>();
			ArrayList<String> expulsados=new ArrayList<String>();
			
			ArrayList<String> amarillas2=new ArrayList<String>();
			ArrayList<String> expulsados2=new ArrayList<String>();
			
			ArrayList<String> Lesionados1=new ArrayList<String>();
			ArrayList<String> Lesionados2=new ArrayList<String>();
			
			int iteracion=0;
			
			while ((cantFaltas1>0)||(cantFaltas2>0)||(cantChanse1>0)||(cantChanse2>0)||(cantLesionados>0)){
				iteracion=iteracion+1;
				
				int StringAleatorio=randInt(0, 5);
				int quien = randInt(0,1);
			
				if ((quien==0)&&((cantFaltas1>0)||(cantChanse1>0))){
					int que1 = randInt(1, 2);
					if ((que1==1)&&(cantChanse1>0)){
						
						EventosPartido event = new EventosPartido();
						//partido.getComentarios().add("Chanse de gol de equipo 1");
						
						//String aux="Chance de gol de "+equipo1.getNombreE()+"-";
						System.out.println("Chance de gol equipo 1");
						event.setEvento("Chance de gol del equipo: "+equipo1.getNombreE());
						
																
						String posicion = queTipoJugadorAtaque();
						
						if ("Delantero".equals(posicion))
						{
							int queDelantero=randInt(0,(formacion1.getDelanteros().size()-1));
							
							Jugador Delantero = formacion1.getDelanteros().get(queDelantero);
							if (!(EstaEnLista(Delantero.getNombreJ(), expulsados)))
							{
								cantChanse1 = cantChanse1 - 1;
								Jugador Arquero = formacion2.getArquero()
										.get(0);
								//aux = aux+",El Delantero"+Delantero.getNombreJ() +" con tiro: "+Delantero.getTiro()+" se enfreta con el arquero :"+Arquero.getNombreJ();
								//aux.concat(",El Delantero"+Delantero.getNombreJ() +" con tiro: "+Delantero.getTiro()+" se enfreta con el arquero :"+Arquero.getNombreJ());
								event.setDescripcion("el delantero "
										+ Delantero.getNombreJ()
										+ " se enfrenta contra el arquero :"
										+ Arquero.getNombreJ());
								System.out.println("el Delantero"
										+ Delantero.getNombreJ()
										+ " con tiro: " + Delantero.getTiro()
										+ " dispara contra el arquero :"
										+ Arquero.getNombreJ());
								int afectado1 = randInt(0, 1);
								int afectado2 = randInt(0, 1);
								int factorAleatorio1 = randInt(0, 10);
								int factorAleatorio2 = randInt(0, 10);
								int ataque = Delantero.getTiro();
								int porteria = Arquero.getPorteria();
								if (afectado1 == 1) {
									ataque = ataque + factorAleatorio1;
									// aux.concat(", El Delantero "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
									System.out
											.println("El Delantero "
													+ StringAleatorioAtacante[StringAleatorio]
													+ " +" + factorAleatorio1
													+ " de Tiro");
									event.setFactorAleatorio1("El delantero "+ StringAleatorioAtacante[StringAleatorio]	+ " +" + factorAleatorio1+ " de Tiro");
								}
								if (afectado2 == 1) {
									porteria = porteria + factorAleatorio2;
									//aux.concat(", El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
									System.out
											.println("El arquero "
													+ StringAleatorioGolero[StringAleatorio]
													+ " +" + factorAleatorio2
													+ " de porteria");
									event.setFactorAleatorio2("El arquero "+ StringAleatorioGolero[StringAleatorio]	+ " +" + factorAleatorio2+ " de porteria");
								}
								boolean gol = EsGol(ataque, porteria);
								if (gol) {
									//aux.concat(","+StringAleatorioGol[StringAleatorio]+" del delantero: "+Delantero.getNombreJ());
									System.out.println(StringAleatorioGol[StringAleatorio] +" del delantero: "	+ Delantero.getNombreJ());
									
									event.setResultado(StringAleatorioGol[StringAleatorio]+ " del delantero: "+ Delantero.getNombreJ());
									event.setEsgol("gol1");
									gol1 = gol1 + 1;
								} else {
									//aux.concat(","+StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									System.out
											.println(StringAleatorioAtajada[StringAleatorio]
													+ " del golero "
													+ Arquero.getNombreJ());
									event.setResultado(StringAleatorioAtajada[StringAleatorio]+ " del golero "+ Arquero.getNombreJ());
									event.setEsgol("no");
								
								}
								eventos.add(event);
							}
							
							
							//System.out.println(aux);
						}
						else if("Mediocampista".equals(posicion)){
							
							int queMediocampista=randInt(0,(formacion1.getMediocampistas().size()-1));
							Jugador Mediocampista = formacion1.getMediocampistas().get(queMediocampista);
							
							if (!(EstaEnLista(Mediocampista.getNombreJ(), expulsados)))
							
							{
								cantChanse1 = cantChanse1 - 1;
								Jugador Arquero = formacion2.getArquero()
										.get(0);
								event.setDescripcion("El mediocampista "+ Mediocampista.getNombreJ()+ " se enfrenta contra el arquero :"+ Arquero.getNombreJ());
								System.out.println("El mediocampista "+ Mediocampista.getNombreJ()+ " con tiro: "+ Mediocampista.getTiro()+ " dispara contra el arquero :"+ Arquero.getNombreJ());
								
								int afectado1 = randInt(0, 1);
								int afectado2 = randInt(0, 1);
								int factorAleatorio1 = randInt(0, 10);
								int factorAleatorio2 = randInt(0, 10);
								int ataque = Mediocampista.getTiro();
								int porteria = Arquero.getPorteria();
								if (afectado1 == 1) {
									ataque = ataque + factorAleatorio1;
									event.setFactorAleatorio1("El Mediocampista "+ StringAleatorioAtacante[StringAleatorio]+ " +"+ factorAleatorio1+ " de Tiro");
									System.out.println("El Mediocampista "+ StringAleatorioAtacante[StringAleatorio]+ " +" + factorAleatorio1+ " de Tiro");
								}
								if (afectado2 == 1) {
									porteria = porteria + factorAleatorio2;
									event.setFactorAleatorio2("El Arquero se ilumina +"+ factorAleatorio2 + " de porteria");
									System.out.println("El Arquero se ilumina +"+ factorAleatorio2+ " de porteria");
								}
								boolean gol = EsGol(ataque, porteria);
								if (gol) {
									System.out.println(StringAleatorioGol[StringAleatorio]+ " del Mediocampista: "+ Mediocampista.getNombreJ());
									
									event.setResultado(StringAleatorioGol[StringAleatorio]+ " del Mediocampista: "+ Mediocampista.getNombreJ());
									gol1 = gol1 + 1;
									event.setEsgol("gol1");
									
								} else {
									event.setResultado(StringAleatorioAtajada[StringAleatorio]+ " del golero "+ Arquero.getNombreJ());
									System.out.println(StringAleatorioAtajada[StringAleatorio]+ " del golero "+ Arquero.getNombreJ());
									event.setEsgol("no");
								}
								eventos.add(event);
							}
							
							
							
						}
						else{
							int queDefensa=randInt(0,(formacion1.getDefensas().size()-1));
							Jugador Defensa = formacion1.getDefensas().get(queDefensa);
							if (!(EstaEnLista(Defensa.getNombreJ(), expulsados))) {
								cantChanse1 = cantChanse1 - 1;
								Jugador Arquero = formacion2.getArquero().get(0);
								System.out.println("el Defensa "
										+ Defensa.getNombreJ() + " con tiro: "
										+ Defensa.getTiro()
										+ " dispara contra el arquero :"
										+ Arquero.getNombreJ());
								event.setDescripcion("El defensa "+ Defensa.getNombreJ()+ " se enfrenta contra el arquero : "+ Arquero.getNombreJ());
								int afectado1 = randInt(0, 1);
								int afectado2 = randInt(0, 1);
								int factorAleatorio1 = randInt(0, 10);
								int factorAleatorio2 = randInt(0, 10);
								int ataque = Defensa.getTiro();
								int porteria = Arquero.getPorteria();
								if (afectado1 == 1) {

									ataque = ataque + factorAleatorio1;
									event.setFactorAleatorio1("El defensa "+ StringAleatorioAtacante[StringAleatorio]+ " +" + factorAleatorio1+ " de Tiro");
									System.out
											.println("El Defensa "
													+ StringAleatorioAtacante[StringAleatorio]
													+ " +" + factorAleatorio1
													+ " de Tiro");
								}
								if (afectado2 == 1) {
									event.setFactorAleatorio1("El arquero "+ StringAleatorioGolero[StringAleatorio]	+ " +" + factorAleatorio2+ " de porteria");
									porteria = porteria + factorAleatorio2;
									System.out
											.println("El arquero "
													+ StringAleatorioGolero[StringAleatorio]
													+ " +" + factorAleatorio2
													+ " de porteria");
								}
								boolean gol = EsGol(ataque, porteria);
								if (gol) {
									event.setResultado(StringAleatorioGol[StringAleatorio]	+ " del Defensa: "+ Defensa.getNombreJ());
									System.out
											.println(StringAleatorioGol[StringAleatorio]
													+ " del Defensa: "
													+ Defensa.getNombreJ());
									gol1 = gol1 + 1;
									event.setEsgol("gol1");
								} else {
									event.setResultado(StringAleatorioAtajada[StringAleatorio]	+ " del golero "+ Arquero.getNombreJ());
									System.out.println(StringAleatorioAtajada[StringAleatorio]	+ " del golero "+ Arquero.getNombreJ());
									event.setEsgol("no");
								}
								eventos.add(event);
							}
							
						}
					}
					else{
						//////////////////////////////////////////////////////////////////////////////////////////////////////////////
						/////////////////////////////////////////////FALTA EQ 1///////////////////////////////////////////////////////
						//////////////////////////////////////////////////////////////////////////////////////////////////////////////
						
						//partido.getComentarios().add("Falta equipo 1");
						if (cantFaltas1>0){
							EventosPartido event= new EventosPartido();
							event.setEvento("Falta del equipo "+equipo1.getNombreE());
							System.out.println("Falta equipo 1");
						
							String infractor=queTipoJugadorFalta();
							if("Delantero".equals(infractor))
							{
								
								int queDelantero=randInt(0,(formacion1.getDelanteros().size()-1));
								////////////////////////////////////////////////////////////////////////////////////////////
								Jugador DelanteroFalta = formacion1.getDelanteros().get(queDelantero);
								if(!(EstaEnLista(DelanteroFalta.getNombreJ(), expulsados))){
									
								
								
									String Receptor= queTipoJugadorAtaque();
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion2.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion2.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), delanteroReceptor.getAtaque());
										event.setDescripcion("El delantero "+DelanteroFalta.getNombreJ()+" le comete falta al delantero "+delanteroReceptor.getNombreJ());
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DelanteroFalta.getNombreJ());
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										eventos.add(event);
										cantFaltas1 = cantFaltas1 - 1;
									}
									else if("Mediocampista".equals(Receptor)){
										int queMedioRecibe=randInt(0,(formacion2.getMediocampistas().size()-1));
										
										Jugador MediocampistaReceptor = formacion2.getMediocampistas().get(queMedioRecibe);
										
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), MediocampistaReceptor.getAtaque());
										event.setDescripcion("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta al mediocampista"+MediocampistaReceptor.getNombreJ());
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DelanteroFalta.getNombreJ());
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
										}
										event.setEsgol("no");
										eventos.add(event);
										cantFaltas1 = cantFaltas1 - 1;
										
									}
									else {
										
										int queDefensaRecibe=randInt(0,(formacion2.getDefensas().size()-1));
										Jugador defensaReceptor = formacion2.getDefensas().get(queDefensaRecibe);
										
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), defensaReceptor.getDefensa());
										event.setDescripcion("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta al defensa"+defensaReceptor.getNombreJ());
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DelanteroFalta.getNombreJ());
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DelanteroFalta.getNombreJ()+" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas1 = cantFaltas1 - 1;
										eventos.add(event);
									}
									
									
								}
							}
							// Un mediocampista comete la falta
							else if("Mediocampista".equals(infractor)){
								
								int queMediocampista=randInt(0,(formacion1.getMediocampistas().size()-1));
								Jugador MediocampistaFalta = formacion1.getMediocampistas().get(queMediocampista);
								
								if(!(EstaEnLista(MediocampistaFalta.getNombreJ(), expulsados))){
									
								
									String Receptor= queTipoJugadorAtaque();
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion2.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion2.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), delanteroReceptor.getAtaque());
										event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al delantero "+delanteroReceptor.getNombreJ());
										System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas))){
												
												amarillas.add(MediocampistaFalta.getNombreJ());
												System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(MediocampistaFalta.getNombreJ());
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										eventos.add(event);
										cantFaltas1 = cantFaltas1 - 1;
										}
										else if("Mediocampista".equals(Receptor)){
											int queMedioRecibe=randInt(0,(formacion2.getMediocampistas().size()-1));
											
											Jugador MediocampistaReceptor = formacion2.getMediocampistas().get(queMedioRecibe);
											
											boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), MediocampistaReceptor.getAtaque());
											
											event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al mediocampista "+MediocampistaReceptor.getNombreJ());
											System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
											
											if(tarjeta){if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas))){
												
												amarillas.add(MediocampistaFalta.getNombreJ());
												System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
											
												
											}
											else{
												expulsados.add(MediocampistaFalta.getNombreJ());
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
												
												
											}
											event.setEsgol("no");
											eventos.add(event);
											cantFaltas1 = cantFaltas1 - 1;
											
										}
										else {
											int queDefensaRecibe=randInt(0,(formacion2.getDefensas().size()-1));
											Jugador defensaReceptor = formacion2.getDefensas().get(queDefensaRecibe);
											
											boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), defensaReceptor.getDefensa());
											
											event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al defensa "+defensaReceptor.getNombreJ());
											System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
											
											if(tarjeta){
												if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas))){
													
													amarillas.add(MediocampistaFalta.getNombreJ());
													System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
													event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												}
												else{
													expulsados.add(MediocampistaFalta.getNombreJ());
													System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
													event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												}
												
											}
											event.setEsgol("no");
											cantFaltas1 = cantFaltas1 - 1;
											eventos.add(event);
										}
									
								}
							}
							else {
								int queDefensa=randInt(0,(formacion1.getDefensas().size()-1));
								Jugador DefensaFalta = formacion1.getDefensas().get(queDefensa);
								if(!(EstaEnLista(DefensaFalta.getNombreJ(), expulsados))){
									
									String Receptor= queTipoJugadorAtaque();
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion2.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion2.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), delanteroReceptor.getAtaque());
										
										
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al delantero "+delanteroReceptor.getNombreJ());
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DefensaFalta.getNombreJ());
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DefensaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas1 = cantFaltas1 - 1;
										eventos.add(event);
									}
									else if("Mediocampista".equals(Receptor)){
										int queMedioRecibe=randInt(0,(formacion2.getMediocampistas().size()-1));
										
										Jugador MediocampistaReceptor = formacion2.getMediocampistas().get(queMedioRecibe);
										
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), MediocampistaReceptor.getAtaque());
										
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al mediocampista "+MediocampistaReceptor.getNombreJ());
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DefensaFalta.getNombreJ());
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DefensaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
										}
										event.setEsgol("no");
										eventos.add(event);
										cantFaltas1 = cantFaltas1 - 1;
										
									}
									else {
										int queDefensaRecibe=randInt(0,(formacion2.getDefensas().size()-1));
										Jugador defensaReceptor = formacion2.getDefensas().get(queDefensaRecibe);
										
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), defensaReceptor.getDefensa());
										
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al defensa "+defensaReceptor.getNombreJ());
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas))){
												
												amarillas.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+" AAAAAAAAAAAAAAAAAAAAAAAAAAArecibe la segunda tarjeta amarilla y es expulsado");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
											}
											
										}
										event.setEsgol("no");
										cantFaltas1 = cantFaltas1 - 1;
										eventos.add(event);
									}
								}
							}
						
						}//Falta eq 1
					}	
				}
				else if((quien!=0)&&((cantFaltas2>0)||(cantChanse2>0))){
					int que2 = randInt(1, 2);
					if ((que2==1)&&(cantChanse2>0)){
						//partido.getComentarios().add("Chanse de gol de equipo 2");
						EventosPartido event= new EventosPartido();
						event.setEvento("Chance de gol del equipo "+equipo2.getNombreE());
						System.out.println("Chance de gol equipo 2");
						
						
						String posicion = queTipoJugadorAtaque();
						if ("Delantero".equals(posicion)){
							int queDelantero=randInt(0,(formacion2.getDelanteros().size()-1));
							Jugador Delantero = formacion2.getDelanteros().get(queDelantero);
							if(!(EstaEnLista(Delantero.getNombreJ(), expulsados2))){
								cantChanse2 = cantChanse2 - 1;
								Jugador Arquero = formacion1.getArquero().get(0);
								System.out.println("el Delantero"+Delantero.getNombreJ() +" con tiro: "+Delantero.getTiro()+" dispara contra el arquero :"+Arquero.getNombreJ());
								event.setDescripcion("el Delantero"+Delantero.getNombreJ() +" se enfrenta contra el arquero : "+Arquero.getNombreJ());
								
								int afectado1 =randInt(0,1);
								int afectado2 =randInt(0,1);
								int factorAleatorio1=randInt(0,10);
								int factorAleatorio2=randInt(0,10);
								int ataque= Delantero.getTiro();
								int porteria=Arquero.getPorteria();
								if(afectado1==1){
									 ataque= ataque+factorAleatorio1;
									 System.out.println("El Delantero "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
									 event.setFactorAleatorio1("El Delantero "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
								}
								if(afectado2==1){
									porteria= porteria+factorAleatorio2;
									 System.out.println("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
									 event.setFactorAleatorio2("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
								}
								boolean gol = EsGol(ataque,porteria);
								if(gol){
									event.setResultado(StringAleatorioGol[StringAleatorio]+" del delantero"+Delantero.getNombreJ());
									System.out.println(StringAleatorioGol[StringAleatorio]+" del delantero"+Delantero.getNombreJ());
									event.setEsgol("gol2");
									gol2=gol2+1;
								}else{
									event.setResultado(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									System.out.println(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									event.setEsgol("no");
								}
								eventos.add(event);
							}
						}
						else if("Mediocampista".equals(posicion)){
							
							int queMediocampista=randInt(0,(formacion1.getMediocampistas().size()-1));
							Jugador Mediocampista = formacion1.getMediocampistas().get(queMediocampista);
							if(!(EstaEnLista(Mediocampista.getNombreJ(), expulsados2))){
								cantChanse2 = cantChanse2 - 1;
								Jugador Arquero = formacion2.getArquero().get(0);
								System.out.println("el Mediocampista "+Mediocampista.getNombreJ() +" con tiro: "+Mediocampista.getTiro()+" dispara contra el arquero :"+Arquero.getNombreJ());
								event.setDescripcion("el mediocampista "+Mediocampista.getNombreJ() +"se enfrenta contra el arquero :"+Arquero.getNombreJ());
								int afectado1 =randInt(0,1);
								int afectado2 =randInt(0,1);
								int factorAleatorio1=randInt(0,10);
								int factorAleatorio2=randInt(0,10);
								int ataque= Mediocampista.getTiro();
								int porteria=Arquero.getPorteria();
								if(afectado1==1){
									 ataque= ataque+factorAleatorio1;
									 System.out.println("El Mediocampista "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
									 event.setFactorAleatorio1("El Mediocampista "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
								}
								if(afectado2==1){
									porteria= porteria+factorAleatorio2;
									event.setFactorAleatorio2("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
									 System.out.println("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
								}
								boolean gol = EsGol(ataque,porteria);
								if(gol){
									event.setResultado(StringAleatorioGol[StringAleatorio]+" del Mediocampista: "+Mediocampista.getNombreJ());
									System.out.println(StringAleatorioGol[StringAleatorio]+" del Mediocampista: "+Mediocampista.getNombreJ());
									event.setEsgol("gol2");
									gol2=gol2+1;
								}else{
									System.out.println(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									event.setResultado(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									event.setEsgol("no");
								}
											
								eventos.add(event);
							}
						}
						else{
							int queDefensa=randInt(0,(formacion1.getDefensas().size()-1));
							Jugador Defensa = formacion1.getDefensas().get(queDefensa);
							if(!(EstaEnLista(Defensa.getNombreJ(), expulsados2))){
								cantChanse2 = cantChanse2 - 1;
								Jugador Arquero = formacion2.getArquero().get(0);
								System.out.println("el Defensa "+Defensa.getNombreJ() +" con tiro: "+Defensa.getTiro()+" dispara contra el arquero :"+Arquero.getNombreJ());
								event.setDescripcion("el defensa "+Defensa.getNombreJ() +" se enfrenta contra el arquero :"+Arquero.getNombreJ());
								
								int afectado1 =randInt(0,1);
								int afectado2 =randInt(0,1);
								int factorAleatorio1=randInt(0,10);
								int factorAleatorio2=randInt(0,10);
								int ataque= Defensa.getTiro();
								int porteria=Arquero.getPorteria();
								if(afectado1==1){
									 ataque= ataque+factorAleatorio1;
									 event.setFactorAleatorio1("El Defensa "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
									 System.out.println("El Defensa "+StringAleatorioAtacante[StringAleatorio]+" +"+factorAleatorio1+" de Tiro");
								}
								if(afectado2==1){
									event.setFactorAleatorio2("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
									porteria= porteria+factorAleatorio2;
									 System.out.println("El arquero "+StringAleatorioGolero[StringAleatorio]+" +"+factorAleatorio2+" de porteria");
								}
								boolean gol = EsGol(ataque,porteria);
								if(gol){
									
									event.setResultado(StringAleatorioGol[StringAleatorio]+" del Defensa: "+Defensa.getNombreJ());
									System.out.println(StringAleatorioGol[StringAleatorio]+" del Defensa: "+Defensa.getNombreJ());
									gol2=gol2+1;
									event.setEsgol("gol2");
								}else{
									System.out.println(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									event.setResultado(StringAleatorioAtajada[StringAleatorio]+" del golero "+Arquero.getNombreJ());
									event.setEsgol("no");
								}
								
								eventos.add(event);
							}
						}
						
					}
					else{//control z
						//partido.getComentarios().add("Falta equipo 2");
						if (cantFaltas2>0){
							
							EventosPartido event= new EventosPartido();
							event.setEvento("Falta del equipo "+equipo2.getNombreE());
							System.out.println("Falta equipo 2");
							//cantFaltas2 = cantFaltas2 - 1;
							

							
							//System.out.println("Falta equipo 1");
						
							String infractor=queTipoJugadorFalta();
							if("Delantero".equals(infractor))
							{
								
								int queDelantero=randInt(0,(formacion2.getDelanteros().size()-1));
								////////////////////////////////////////////////////////////////////////////////////////////
								Jugador DelanteroFalta = formacion2.getDelanteros().get(queDelantero);
								if(!(EstaEnLista(DelanteroFalta.getNombreJ(), expulsados2))){
									//Selecciono que tipo de jugador es receptor de la falta
									String Receptor= queTipoJugadorAtaque();
									
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion1.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion1.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), delanteroReceptor.getAtaque());
										
										event.setDescripcion("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta al delantero"+delanteroReceptor.getNombreJ());
										
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados2.add(DelanteroFalta.getNombreJ());
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DelanteroFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
									else if("Mediocampista".equals(Receptor)){
										int queMedioRecibe=randInt(0,(formacion1.getMediocampistas().size()-1));
										
										Jugador MediocampistaReceptor = formacion1.getMediocampistas().get(queMedioRecibe);
										
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), MediocampistaReceptor.getAtaque());
										
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
										event.setDescripcion("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta al mediocampista"+MediocampistaReceptor.getNombreJ());
										
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados2.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
									else {
										
										int queDefensaRecibe=randInt(0,(formacion1.getDefensas().size()-1));
										Jugador defensaReceptor = formacion1.getDefensas().get(queDefensaRecibe);
										
										boolean tarjeta= EsTarjeta(DelanteroFalta.getDefensa(), defensaReceptor.getDefensa());
										
										event.setDescripcion("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta al defensor "+defensaReceptor.getNombreJ());
										System.out.println("El Delantero "+DelanteroFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(DelanteroFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DelanteroFalta.getNombreJ());
												System.out.println(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados2.add(DelanteroFalta.getNombreJ());
												event.setResultado(DelanteroFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DelanteroFalta.getNombreJ()+" AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
									
									
								}
							}
							// Un mediocampista comete la falta
							else if("Mediocampista".equals(infractor)){
								
								int queMediocampista=randInt(0,(formacion2.getMediocampistas().size()-1));
								Jugador MediocampistaFalta = formacion2.getMediocampistas().get(queMediocampista);
								
								if(!(EstaEnLista(MediocampistaFalta.getNombreJ(), expulsados2))){
									
								
									String Receptor= queTipoJugadorAtaque();
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion1.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion1.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), delanteroReceptor.getAtaque());
										
										event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al delantero "+delanteroReceptor.getNombreJ());
										System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										
										if(tarjeta){
											if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(MediocampistaFalta.getNombreJ());
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												
											}
											else{
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												expulsados2.add(MediocampistaFalta.getNombreJ());
												System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
										
										}
										else if("Mediocampista".equals(Receptor)){
											int queMedioRecibe=randInt(0,(formacion1.getMediocampistas().size()-1));
											
											Jugador MediocampistaReceptor = formacion1.getMediocampistas().get(queMedioRecibe);
											
											boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), MediocampistaReceptor.getAtaque());
											
											System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
											event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al mediocampista "+MediocampistaReceptor.getNombreJ());
											
											if(tarjeta){
												if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(MediocampistaFalta.getNombreJ());
												System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												expulsados2.add(MediocampistaFalta.getNombreJ());
												System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
												
												
											}
											event.setEsgol("no");
											cantFaltas2 = cantFaltas2 - 1;
											eventos.add(event);
										}
										else {
											int queDefensaRecibe=randInt(0,(formacion1.getDefensas().size()-1));
											Jugador defensaReceptor = formacion1.getDefensas().get(queDefensaRecibe);
											
											boolean tarjeta= EsTarjeta(MediocampistaFalta.getDefensa(), defensaReceptor.getDefensa());
											
											System.out.println("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
											event.setDescripcion("El Mediocampista "+MediocampistaFalta.getNombreJ()+" le comete falta al defensa "+defensaReceptor.getNombreJ());
											if(tarjeta){
												if(!(EstaEnLista(MediocampistaFalta.getNombreJ(),amarillas2))){
													
													amarillas2.add(MediocampistaFalta.getNombreJ());
													System.out.println(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
													event.setResultado(MediocampistaFalta.getNombreJ()+" recibe tarjeta amarilla");
												}
												else{
													expulsados2.add(MediocampistaFalta.getNombreJ());
													event.setResultado(MediocampistaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
													System.out.println(MediocampistaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
													
												}
												
											}
											event.setEsgol("no");
											cantFaltas2 = cantFaltas2 - 1;
											eventos.add(event);
										}
									
								}
							}
							else {
								int queDefensa=randInt(0,(formacion2.getDefensas().size()-1));
								Jugador DefensaFalta = formacion2.getDefensas().get(queDefensa);
								if(!(EstaEnLista(DefensaFalta.getNombreJ(), expulsados2))){
									
									String Receptor= queTipoJugadorAtaque();
									if("Delantero".equals(Receptor)){
										int queDelanteroRecibe=randInt(0,(formacion1.getDelanteros().size()-1));
										
										Jugador delanteroReceptor = formacion1.getDelanteros().get(queDelanteroRecibe);
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), delanteroReceptor.getAtaque());
										
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+delanteroReceptor.getNombreJ());
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al delantero "+delanteroReceptor.getNombreJ());
										if(tarjeta){
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados2.add(DefensaFalta.getNombreJ());
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DefensaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
									else if("Mediocampista".equals(Receptor)){
										int queMedioRecibe=randInt(0,(formacion1.getMediocampistas().size()-1));
										
										Jugador MediocampistaReceptor = formacion1.getMediocampistas().get(queMedioRecibe);
										
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), MediocampistaReceptor.getAtaque());
										
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+MediocampistaReceptor.getNombreJ());
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al mediocampista "+MediocampistaReceptor.getNombreJ());
										
										if(tarjeta){
											
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DefensaFalta.getNombreJ());
												
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
											}
											else{
												expulsados2.add(DefensaFalta.getNombreJ());
												System.out.println(DefensaFalta.getNombreJ()+"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA recibe la segunda tarjeta amarilla y es expulsado");
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
											}
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
									else {
										int queDefensaRecibe=randInt(0,(formacion1.getDefensas().size()-1));
										Jugador defensaReceptor = formacion1.getDefensas().get(queDefensaRecibe);
										
										boolean tarjeta= EsTarjeta(DefensaFalta.getDefensa(), defensaReceptor.getDefensa());
										
										System.out.println("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta a"+defensaReceptor.getNombreJ());
										event.setDescripcion("El Defensa "+DefensaFalta.getNombreJ()+" le comete falta al defensa "+defensaReceptor.getNombreJ());
										if(tarjeta){
											if(!(EstaEnLista(DefensaFalta.getNombreJ(),amarillas2))){
												
												amarillas2.add(DefensaFalta.getNombreJ());
												event.setResultado(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												System.out.println(DefensaFalta.getNombreJ()+" recibe tarjeta amarilla");
												
											}
											else{
												expulsados2.add(DefensaFalta.getNombreJ());
												event.setResultado(DefensaFalta.getNombreJ()+" recibe la segunda tarjeta amarilla y es expulsado");
												System.out.println(DefensaFalta.getNombreJ()+" AAAAAAAAAAAAAAAAAAAAAAAAAAArecibe la segunda tarjeta amarilla y es expulsado");
												
											}
											
										}
										event.setEsgol("no");
										cantFaltas2 = cantFaltas2 - 1;
										eventos.add(event);
									}
								}
							}
						
						
						}
					}	
					
				}
				else {
					//cant lesionados >0
					if(cantLesionados>0){
						int quienLesion = randInt(0,1);
						if(quienLesion==0){
							
							EventosPartido event= new EventosPartido();
							event.setEvento("Se lesiono ");
							event.setEsgol("no");
							
							int tipoJugador = randInt(1,3);
							if ((tipoJugador==1)){
								// "Defensa";
								int jugadorLesionado= randInt(0,formacion1.getDefensas().size()-1);
							//	formacion1.getDefensas().get(jugadorLesionado);
								String jugadorLesion=formacion1.getDefensas().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados))){
									Lesionados1.add(jugadorLesion);
									event.setResultado("el defensa "+jugadorLesion+" del equipo "+equipo1.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
								}
							}
							else if((tipoJugador==2)){
								// "Mediocampista";
								int jugadorLesionado= randInt(0,formacion1.getMediocampistas().size()-1);
							//	formacion1.getMediocampistas().get(jugadorLesionado);
								String jugadorLesion=formacion1.getMediocampistas().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados))){
									Lesionados1.add(jugadorLesion);
									event.setResultado("el mediocampista "+jugadorLesion+" del equipo "+equipo1.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
								}	
							}
							else{
								// "Delantero";
								int jugadorLesionado= randInt(0,formacion1.getDelanteros().size()-1);
							//	formacion1.getDelanteros().get(jugadorLesionado);
								String jugadorLesion=formacion1.getDelanteros().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados))){
									Lesionados1.add(jugadorLesion);
									event.setResultado("el delantero "+jugadorLesion+" del equipo "+equipo1.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
								}
							}
							
						}else
						{
							
							EventosPartido event= new EventosPartido();
							event.setEvento("Se lesiono ");
							event.setEsgol("no");
							int tipoJugador = randInt(1,3);
							if ((tipoJugador==1))
							{
								// "Defensa";
								int jugadorLesionado= randInt(0,formacion2.getDefensas().size()-1);
							//	formacion2.getDefensas().get(jugadorLesionado);
								String jugadorLesion=formacion2.getDefensas().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados2)))
								{
									Lesionados2.add(jugadorLesion);
									event.setResultado("el defensa "+jugadorLesion+" del equipo "+equipo2.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
								}
							}
							else if((tipoJugador==2)){
								// "Mediocampista";
								int jugadorLesionado= randInt(0,formacion2.getMediocampistas().size()-1);
							//	formacion2.getMediocampistas().get(jugadorLesionado);
								String jugadorLesion=formacion2.getMediocampistas().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados2)))
								{
									Lesionados2.add(jugadorLesion);
									event.setResultado("el mediocampista "+jugadorLesion+" del equipo "+equipo2.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
								}
							}
							else{
								// "Delantero";
								int jugadorLesionado= randInt(0,formacion2.getDelanteros().size()-1);
								//formacion2.getDelanteros().get(jugadorLesionado);
								String jugadorLesion=formacion2.getDelanteros().get(jugadorLesionado).getNombreJ();
								if(!(EstaEnLista(jugadorLesion, expulsados2)))
								{
									Lesionados2.add(jugadorLesion);
									event.setResultado("el delantero "+jugadorLesion+" del equipo "+equipo2.getNombreE());
									eventos.add(event);
									cantLesionados = cantLesionados - 1;
							
								
								}
							}
							
							
						}
					}
					
				}
				
			}//END WHILE
			
			/*
			System.out.println("Resultado Final: "+equipo1.getNombreE()+" "+gol1+"-"+gol2+" "+equipo2.getNombreE());
			
			
			
			System.out.println("Jugadores con amarilla equipo 1: ");
			for(String s :amarillas){
				System.out.print(s+" ,");
				
			}
			System.out.println();
			System.out.println("Jugadores con Roja equipo 1: ");
			for(String s :expulsados){
				System.out.print(s+" ,");
				
			}
			System.out.println();
			System.out.println("Jugadores con amarilla equipo 2: ");
			for(String s :amarillas2){
				System.out.print(s+" ,");
				
			}
			System.out.println();
			System.out.println("Jugadores con Roja equipo 2: ");
			for(String s :expulsados2){
				System.out.print(s+" ,");
				
			}
			System.out.println();
			
			
			System.out.println("------------------------------------------------------------------------------------------------------------------");
			
			System.out.println("Iteracion: "+iteracion);
			System.out.println("Incidencias: "+incidencias);
			System.out.println("Eventos: "+eventos.size());
			
			*/
			
			EventosPartido event= new EventosPartido();
			event.setEvento("Finalizo el partido");
			event.setDescripcion("felicitaciones a ambos jugadores");
			event.setFactorAleatorio1("");
			event.setFactorAleatorio2("");
			event.setEsgol("no");
			event.setResultado("");
			
			eventos.add(event);
			for (EventosPartido e:eventos){
				
				e.setPartido(partido);
				
			}
			
			//pago al ganador
			if(gol1>gol2){
				pagarGanador(equipo1.getNombreE(),500);
			}else if (gol2>gol1){
				pagarGanador(equipo2.getNombreE(),500);
								
			}
			else{
				pagarGanador(equipo1.getNombreE(),250);
				pagarGanador(equipo2.getNombreE(),250);
				
			}
			
					
			partido.setFinalizado(true);
			partido.setGolesL(gol1);
			partido.setGolesV(gol2);
			partido.setEventosPartido(eventos);
			
			ligaDAO.guardarPartido(partido);
			
			}
			else{
				System.out.println("TEMENDO NULL MUCHACHO");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	
	private void pagarGanador(String nombreE, int i) {
		
		equipoDAO.cambiarCaptial(nombreE, i);
	}
	private boolean EstaEnLista(String nombreJ, ArrayList<String> amarillas) {
		for(String aux:amarillas){
			
			if (aux.compareTo(nombreJ)==0){
				return true;
			}
			
		}
		return false;
	}
	
	private int RendimientoExpulsado(int elem,int cantExpulsados){
		
		if(cantExpulsados==1){
			return (Math.round(elem*(90/100)));
			
		}
		else if (cantExpulsados==2){
			
			return (Math.round(elem*(80/100)));
		}
		else if(cantExpulsados ==3){
			return (Math.round(elem*(70/100)));
		}
		else{
			return 1000;
		}
		
	}
	private int RendimientoAltura(int elem,int altura){
		
	/*	1501-2000:	jugadores a un 95%
		2001-2500:	jugadores a un 90%
		2501-3000:	jugadores a un 85%
		3001-3500:	jugadores a un 75%
		3501-4000:	jugadores a un 65%
		4001-4500: jugadores a un 50%
	*/
		if((altura>0)&&(altura<=1500))
		{
			return elem;
		}
		else if((altura>1500)&&(altura<=2000))
		{
			return (Math.round((95*elem))/100);
			
		}else if ((altura>2000)&&(altura<=2500))
		{
			return (Math.round((90*elem))/100);
			
		}else if ((altura>2500)&&(altura<=3000))
		{
			return (Math.round((85*elem))/100);
			
		}else if ((altura>3000)&&(altura<=3500))
		{
			return (Math.round((75*elem))/100);
			
		}else if ((altura>3500)&&(altura<=4000))
		{
			return (Math.round((65*elem))/100);
			
		}else
		{
			return (Math.round((50*elem))/100);
		}
		
		
		
	}
	
	
	private boolean EsTarjeta(int defensa, int ataque) {
		int ran=randInt(1, defensa+ataque);
		
		if(ran<=ataque){
			return true;
			
		}
		else{return false;
			}
		
	}
	
	
	private boolean EsGol(int ataque, int porteria) {
		int ran=randInt(1, porteria+ataque);
		if(ran<=ataque){
			return true;
			
		}
		else{return false;
			}
		
	}



	private String queTipoJugadorAtaque(){
		int tipoJugador = randInt(1,10);
		if ((tipoJugador<=10)&&(tipoJugador>4)){
			return "Delantero";
		}
		else if((tipoJugador<=4)&&(tipoJugador>1)){
			return "Mediocampista";
		}
		else{
			return "Defensa";
		}
		
	}
	private String queTipoJugadorFalta(){
		int tipoJugador = randInt(1,10);
		if ((tipoJugador<=10)&&(tipoJugador>4)){
			return "Defensa";
		}
		else if((tipoJugador<=4)&&(tipoJugador>1)){
			return "Mediocampista";
		}
		else{
			return "Delantero";
		}
		
	}
	

	public int calculoChanceGol(int Ataque){
		
		if ((Ataque<= 100)&&(Ataque>=95)){
			
			return randInt(15, 20);
			
		}
		else if((Ataque< 95)&&(Ataque>=80)){
			return randInt(9,15);
		}
		else if ((Ataque< 80)&&(Ataque>=70)){
			return randInt(3,9);
		}
		else
		{
			return randInt(1,3);	
		}
		
	}
	
	public int calculoTarjetas(double diferencia){
		
		if ((diferencia<= 40)&&(diferencia>=30)){
			
			return randInt(10, 20);
			
		}
		else if((diferencia< 30)&&(diferencia>=20)){
			return randInt(5,15);
		}
		else if ((diferencia< 20)&&(diferencia>=10)){
			return randInt(0,7);
		}
		else
		{
			return randInt(0,5);	
		}
		
	}
	
	private int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
public void asignarLiga(Liga liga, Equipo team){
		
		try {			
			System.out.println("Toy en controlador eq asig liga");
		    equipoDAO.asignarLiga(liga,team);
			

		} catch (Exception e) {

			e.printStackTrace();

		}

		

	}
@Override
public List<Equipo> listarEquipos(Liga laliga) {
	
	List<Equipo> equipos = equipoDAO.listarEquipos(laliga);
	return equipos;
	
}

public String darCiudad(String equipoName) {
	try{
		  
		   String nombreC = equipoDAO.darCiudad(equipoName);
		   
		 System.out.println("ESTOY EN EL CONTROLADOR Equipo EN DAR Ciudad");

		   
		   System.out.println(nombreC);


		   return nombreC;
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  return null;
}
public Double buscarCapital(String equipoName) {
	try{
		return equipoDAO.buscarCapital(equipoName);
	}catch(Exception e){
		   e.printStackTrace();
		  }
	return null;
	
}
//////////////////////////////////Me retorna la lista de experiencia de mi equipo//////////////////////
public List<Experiencia> getJugadoresExperiencia(String miEquipo) {

	List<Jugador> jugadores = new ArrayList();
	List<Experiencia> exp = new ArrayList();
	try{
		Equipo Equipo = equipoDAO.buscarEquipo(miEquipo); 
		jugadores = jugadorDAO.misJugadores(Equipo);
		exp = ExperienciaDAO.getJugadoresExperiencia(jugadores);
		
	
	}catch(Exception e){
		e.printStackTrace();
	
	}
	
	return exp;
}

////////////////////////Me dice si existe mi equipo///////////////////////////////////////////////

public boolean existeEquipo(String equipoName) {
	boolean existe = false ;
	try{
		existe = equipoDAO.existeEquipo(equipoName); 
		
	}catch(Exception e){
		e.printStackTrace();
		
	}
	
	return existe;
}


public List<String> darZona(String equipoName) {
	
	List<String> zona = new ArrayList<String>();
		
		try{
		  
		    zona = equipoDAO.darZona(equipoName);
	
		  
		  }catch(Exception e){
		   e.printStackTrace();
		  }
		  return zona;
}

}
