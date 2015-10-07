package utilidades;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.Jugador;
import dominio.Partido;

public class PartidoAdapter implements JsonSerializer<Partido> {

	@Override
	public JsonElement serialize(Partido partido, Type type, JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
	
		jsonObject.addProperty("idPartido", partido.getId());
		jsonObject.addProperty("IdEquipo2", partido.getEquipo2().getNombreE());
		jsonObject.addProperty("IdEquipo", partido.getEquipo1().getNombreE());
		jsonObject.addProperty("local", partido.getLocal());
		jsonObject.addProperty("golesV", partido.getGolesV());
		jsonObject.addProperty("golesL", partido.getGolesL());
		jsonObject.addProperty("finalizado", partido.isFinalizado());
		

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String fecha = sdf.format(partido.getFecha());
		
	
		
		jsonObject.addProperty("fecha", fecha);
		
		return jsonObject;
	}

}
