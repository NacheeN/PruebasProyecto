package utilidades;

import java.lang.reflect.Type;

import Wrappers.WrapperJugador;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;



public class JugadorAdapter implements JsonSerializer<WrapperJugador> {

	@Override
	public JsonElement serialize(WrapperJugador jugador, Type type, JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("nombreJ", jugador.getNombreJ());
		jsonObject.addProperty("tiro", jugador.getTiro());
		jsonObject.addProperty("defensa", jugador.getDefensa());
		jsonObject.addProperty("porteria", jugador.getPorteria());
		jsonObject.addProperty("tipo", jugador.getTipo());
		jsonObject.addProperty("star", jugador.isStar());
		jsonObject.addProperty("ataque", jugador.getAtaque());
		jsonObject.addProperty("vender", jugador.isVender());
		jsonObject.addProperty("precio", jugador.getPrecio());
		jsonObject.addProperty("fechaNac", jugador.getFechaNac().toString());
		
		return jsonObject;
	}

}
