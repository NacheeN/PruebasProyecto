package persistencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import dominio.Equipo;
import dominio.Experiencia;
import dominio.Formacion;
import dominio.Jugador;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JugadorDAO implements IJugadorDAO {

	@javax.persistence.PersistenceContext(unitName = "JatrickAppServer")
	private javax.persistence.EntityManager em;

	@EJB
	IExperienciaDAO expdao;
	
	@EJB
	private IEquipoDAO equipoDAO;
	
	@EJB
	private IFormacionDAO formacionDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean guardarJugador(Jugador jugador) {

		try {
			em.persist(jugador);
			return true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return false;

	}

	public List<Jugador> asignarJugadores(Equipo equipo) {
		// System.out.println("JugadorDao problema antes consulta");

		List<Jugador> jugadoresEnEquipo = new ArrayList<Jugador>();

		List<Jugador> jugadores = em
				.createQuery("SELECT j FROM Jugador j WHERE j.equipo IS NULL")
				.setMaxResults(23).getResultList();
		// System.out.println("Cantidad Jugadores"+ jugadores.size() );

		Random rnd = new Random();
		int num = 0, init = 1;
		int cont = 1, contEMed = 0, contEDef = 0, contEAtq = 0, contEArq = 0;
		boolean star = false;
		// //determino los puestos de los jugadores estrellas
		for (int i = 0; i < 3; i++) {
			// from + rndGenerator.nextInt(to - from + 1)
			num = init + rnd.nextInt(4);
			if (num == 1) {

				init = 2;
				contEArq++;
			} else if (num == 2) {

				contEDef++;

			} else if (num == 3) {

				contEMed++;

			} else {

				contEAtq++;

			}

		}

		// ///Cargo el cuadro a los jugadores y se le asignan las habilidades
		for (Jugador jg : jugadores) {

			jg.setEquipo(equipo);
			if (cont <= 8) {

				if (0 < contEMed) {
					contEMed--;
					star = true;
				} else
					star = false;

				jg.cargarJugador(jg.getNombreJ(), "Mediocampista", star);
				Experiencia exp = new Experiencia(0, 0, 0, 0);
				exp.setJugador(jg);
				em.persist(exp);
				jugadoresEnEquipo.add(jg);

				// expdao.guardarExperiencia(exp);
				cont++;
			} else if (cont > 8 && cont <= 16) {

				if (0 < contEDef) {
					contEDef--;
					star = true;
				} else
					star = false;

				jg.cargarJugador(jg.getNombreJ(), "Defensa", star);
				Experiencia exp = new Experiencia(0, 0, 0, 0);
				exp.setJugador(jg);
				jugadoresEnEquipo.add(jg);
				// expdao.guardarExperiencia(exp);
				em.persist(exp);
				cont++;
			} else if (cont > 16 && cont <= 21) {

				if (0 < contEAtq) {
					contEAtq--;
					star = true;
				} else
					star = false;
				jg.cargarJugador(jg.getNombreJ(), "Atacante", star);
				Experiencia exp = new Experiencia(0, 0, 0, 0);
				exp.setJugador(jg);
				em.persist(exp);
				jugadoresEnEquipo.add(jg);

				// expdao.guardarExperiencia(exp);
				cont++;

			} else {

				if (0 < contEArq) {
					contEArq--;
					star = true;
				} else
					star = false;
				jg.cargarJugador(jg.getNombreJ(), "Arquero", star);
				Experiencia exp = new Experiencia(0, 0, 0, 0);
				exp.setJugador(jg);
				em.persist(exp);
				jugadoresEnEquipo.add(jg);
				// expdao.guardarExperiencia(exp);
				cont++;
			}

		}

		return jugadoresEnEquipo;

	}
	
public List<Jugador> misJugadores(Equipo miEquipo){
		
		try{
			List<Jugador> jugadores = em.createQuery("SELECT j FROM Jugador j WHERE j.equipo LIKE :equipo").setParameter("equipo", miEquipo).getResultList();
			

			return jugadores;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	public boolean venderJugadores(List<Jugador> jugadores){
		try{
			Iterator<Jugador> it= jugadores.iterator();
			
			while(it.hasNext()) {
				 
				Jugador jugador= it.next();
				Jugador jg = em.find(Jugador.class,jugador.getNombreJ());
				jg.setVender(true);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List<Jugador> jugadoresAComp(String miEquipo){
		System.out.println("jugadores a comprar DAO miequipo::::"+ miEquipo);
		String query = "SELECT j FROM Jugador j WHERE j.vender=true AND j.equipo <> '" + miEquipo + "' ";
		try{
			List<Jugador> jugadores=em.createQuery(query).getResultList();
			//List<Jugador> jugadores = em.createQuery("SELECT j FROM Jugador j WHERE j.vender=true").getResultList();
			System.out.println("jugadores a comprar::::"+ jugadores.toString());
			Iterator<Jugador> it = jugadores.iterator();
			while(it.hasNext()) {
				 
				Jugador jugador= it.next();
				System.out.println(jugador.getNombreJ());
			}
			return jugadores;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public boolean comprarJugadores(List<Jugador> jugadores, Equipo equipo){
		
		
		  Equipo equipoVende;
		/*  System.out.println("jugador dao compra" + jugadores.toString());
		  System.out.println("jugador dao compra" + equipo.getNombreE());
		 */
		try{
			
			
			Iterator<Jugador> it= jugadores.iterator();
			List<String> suplentesAQuitar = new ArrayList<String>();
			List<Integer> idFormaciones = new ArrayList<Integer>();
			List<String> posiciones = new ArrayList<String>();
			List<Jugador> jugadorAQuitar = new ArrayList<Jugador>();

			
			List<Jugador> listaSuplentesCompra = new ArrayList<Jugador>();
			
		
			
			listaSuplentesCompra = this.listarJugadoresSuplentes(equipo.getFormacion().getId());
			
			
			
			
			
			while(it.hasNext()) {
				 
				Jugador jugador= it.next();
				
				
				suplentesAQuitar.add(jugador.getNombreJ());
				
				Jugador jg = em.find(Jugador.class,jugador.getNombreJ());
				jugadorAQuitar.add(jg);
				equipoVende = equipoDAO.buscarEquipo(jg.getEquipo().getNombreE());
		//		System.out.println("Id de formacion " + equipoVende.getFormacion().getId());
				idFormaciones.add(equipoVende.getFormacion().getId());
				
			
			
		
			
				 String query1 = "SELECT j FROM Formacion f join f.suplentes j WHERE f.id = "+ equipoVende.getFormacion().getId() + " AND j.nombreJ = '"+ jg.getNombreJ() + "'";
				 String query2 = "SELECT j FROM Formacion f join f.delanteros j WHERE f.id = "+ equipoVende.getFormacion().getId() + " AND j.nombreJ = '"+ jg.getNombreJ() + "'";
				 String query3 = "SELECT j FROM Formacion f join f.defensas j WHERE f.id = "+ equipoVende.getFormacion().getId() + " AND j.nombreJ = '"+ jg.getNombreJ() + "'";
				 String query4 = "SELECT j FROM Formacion f join f.arquero j WHERE f.id = "+ equipoVende.getFormacion().getId() + " AND j.nombreJ = '"+ jg.getNombreJ() + "'";
				 String query5 = "SELECT j FROM Formacion f join f.mediocampistas j WHERE f.id = "+ equipoVende.getFormacion().getId() + " AND j.nombreJ = '"+ jg.getNombreJ() + "'";
				 
				 Jugador suplente = null;
				 Jugador delantero = null;
				 Jugador defensa = null;
				 Jugador arquero = null;
				 Jugador mediocampista = null;
				 
				 try{				 
				 
					 suplente = (Jugador) em.createQuery(query1).getSingleResult();				
					
				 } catch (NoResultException nre){
				 //Ignore this because as per your logic this is ok!
				 }
				 try{				 
					 			
					 delantero = (Jugador) em.createQuery(query2).getSingleResult();
					 
				 } catch (NoResultException nre){
				 //Ignore this because as per your logic this is ok!
				 }
				 try{				 
					 defensa = (Jugador) em.createQuery(query3).getSingleResult();
					 
				 } catch (NoResultException nre){
				 //Ignore this because as per your logic this is ok!
				 }
				 try{				 
					 arquero = (Jugador) em.createQuery(query4).getSingleResult();				
				
					
				 } catch (NoResultException nre){
				 //Ignore this because as per your logic this is ok!
				 }
				 try{				 
							
					 mediocampista = (Jugador) em.createQuery(query5).getSingleResult();
					
				 } catch (NoResultException nre){
				 //Ignore this because as per your logic this is ok!
				 }
				
				
				if(suplente != null){
					
					posiciones.add("Suplente");
					
				}				
				else if(delantero != null){
					
					posiciones.add("Delantero");
				}else if(defensa != null){
					
					posiciones.add("Defensa");
				}else if(arquero != null){
					
					posiciones.add("Arquero");
				}else if(mediocampista != null){
					
					posiciones.add("Mediocampista");
				}
				

				
				equipoVende.setCapital(equipoVende.getCapital()+jugador.getPrecio()); 
				equipo.setCapital( equipo.getCapital()-jugador.getPrecio());
				jg.setEquipo(equipo);
				jg.setVender(false);
			
			//	System.out.println("TERMINA WHILE");
			}	/// Cierra while
			
			
			// variable de control que toma los lugares para cada jugador
			int i = 0;
			int k = 0;
			
			List<Formacion> formaciones = new ArrayList<Formacion>();
			
			for(Integer id :idFormaciones){
			
				formaciones.add(formacionDAO.getFormacion(id));
			}
			
			Jugador jug = new Jugador();
			
			for(String j : suplentesAQuitar)
			{

				if(posiciones.get(k).equals("Suplente")){
					jug = jugadorAQuitar.get(i);
					formaciones.get(k).getSuplentes().remove(jug);
					listaSuplentesCompra.add(jugadorAQuitar.get(i));
					equipo.getFormacion().setSuplentes(listaSuplentesCompra);
					jugadorAQuitar.remove(jug);
					/// 	quitar el jugador i en jugadorAQuitar
				}else if(posiciones.get(k).equals("Delantero")){
					jug = jugadorAQuitar.get(i);
					formaciones.get(k).getDelanteros().remove(jug);
					listaSuplentesCompra.add(jugadorAQuitar.get(i));
					equipo.getFormacion().setSuplentes(listaSuplentesCompra);
					jugadorAQuitar.remove(jug);
				}else if(posiciones.get(k).equals("Mediocampista")){
					jug = jugadorAQuitar.get(i);
					formaciones.get(k).getMediocampistas().remove(jug);
					listaSuplentesCompra.add(jugadorAQuitar.get(i));
					equipo.getFormacion().setSuplentes(listaSuplentesCompra);
					jugadorAQuitar.remove(jug);
				}else if(posiciones.get(k).equals("Defensa")){
					jug = jugadorAQuitar.get(i);
					formaciones.get(k).getDefensas().remove(jug);
					listaSuplentesCompra.add(jugadorAQuitar.get(i));
					equipo.getFormacion().setSuplentes(listaSuplentesCompra);
					jugadorAQuitar.remove(jug);
				}else if(posiciones.get(k).equals("Arquero")){
					jug = jugadorAQuitar.get(i);
					formaciones.get(k).getArquero().remove(jug);
					listaSuplentesCompra.add(jugadorAQuitar.get(i));
					equipo.getFormacion().setSuplentes(listaSuplentesCompra);
					jugadorAQuitar.remove(jug);
				}
				
		
	

				k++;
				//i++;
			}
	
			
			//System.out.println("suplentes de venta");
			for(Jugador j :formaciones.get(0).getSuplentes()){				
				
				System.out.println(j.getNombreJ()+" ");
			}

			//System.out.println("suplentes de compra a modificar: ");
			
		for(Jugador j : listaSuplentesCompra){				
				
				System.out.println(j.getNombreJ()+" ");
			}
		
			//Seteo la nueva formaicon el equipo que compro al jugador
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
		
	}

	public void guardarCambiosJugador(Jugador j) {
		try {
			em.merge(j);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Jugador> listarJugadoresSuplentes(int idFormacion) {

		try {

			// Acordarse que no es la tabla de la BD, es jpql entonces tenemos
			// que usar las entidades nuestras
			// Con esto traigo a los suplentes
			Query q = em
					.createQuery("SELECT j FROM Formacion f join f.suplentes j WHERE f.id = "
							+ idFormacion);
			List<Jugador> jugadores = q.getResultList();

			// List<Jugador> jugadores =
			// em.createQuery("SELECT j FROM Jugador j WHERE j.equipo = '"+nombreE+"'",Jugador.class).getResultList();
			// System.out.println(jugadores.get(0).getNombreJ() + "//" +
			// jugadores.get(1).getNombreJ());

			return jugadores;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public List<Jugador> listarJugadoresDelanteros(int idFormacion) {

		try {
			Query q = em
					.createQuery("SELECT j FROM Formacion f join f.delanteros j WHERE f.id = "
							+ idFormacion);
			List<Jugador> jugadores = q.getResultList();

			return jugadores;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Jugador> listarJugadoresMediocampistas(int idFormacion) {
		try {
			Query q = em
					.createQuery("SELECT j FROM Formacion f join f.mediocampistas j WHERE f.id = "
							+ idFormacion);
			List<Jugador> jugadores = q.getResultList();

			return jugadores;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Jugador> listarJugadoresDefensas(int idFormacion) {
		try {
			Query q = em
					.createQuery("SELECT j FROM Formacion f join f.defensas j WHERE f.id = "
							+ idFormacion);
			List<Jugador> jugadores = q.getResultList();

		
			return jugadores;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Jugador> listarJugadorArquero(int idFormacion) {
		try {
			Query q = em
					.createQuery("SELECT j FROM Formacion f join f.arquero j WHERE f.id = "
							+ idFormacion);
			List<Jugador> jugadores = q.getResultList();

			return jugadores;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}