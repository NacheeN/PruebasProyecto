package utilidades;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.Jugador;

public class JugadorAdapter implements JsonSerializer<Jugador> {

	@Override
	public JsonElement serialize(Jugador jugador, Type type,JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("nombreJ", jugador.getNombreJ());
		
		
		/*
		System.out.println("ESTOY EN JUGADOR ADAPTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEER");
		System.out.println(jugador.getTiro());
		*/
		
		jsonObject.addProperty("Tiro", jugador.getTiro());
		jsonObject.addProperty("defensa", jugador.getDefensa());
		jsonObject.addProperty("porteria", jugador.getPorteria());
		jsonObject.addProperty("tipo", jugador.getTipo());
		jsonObject.addProperty("star", jugador.isStar());
		jsonObject.addProperty("ataque", jugador.getAtaque());
		jsonObject.addProperty("vender", jugador.isVender());
		jsonObject.addProperty("precio", jugador.getPrecio());
		jsonObject.addProperty("fechaNac", jugador.getFechaNac().toString());
	
	/*
	    Gson gson = new Gson();
	 
		JsonElement jsonElement = gson.toJsonTree(jugador.getEquipo()); //,Equipo.class);
		jsonObject.add("equipo", jsonElement);
	*/
	
		
		return jsonObject;
	}

}
