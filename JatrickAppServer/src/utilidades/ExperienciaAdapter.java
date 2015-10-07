package utilidades;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.Experiencia;
public class ExperienciaAdapter implements JsonSerializer<Experiencia> {
	
	@Override
	public JsonElement serialize(Experiencia experiencia, Type type,JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("ExpAtaque", experiencia.getExpAtaque());
		jsonObject.addProperty("ExpTiro", experiencia.getExpTiro());
		jsonObject.addProperty("ExpDefensa", experiencia.getExpDefensa());
		jsonObject.addProperty("ExpPorteria", experiencia.getExpPorteria());
		jsonObject.addProperty("nombreJ", experiencia.getJugador().getNombreJ());
		
		
		return jsonObject;
	}
	

}
