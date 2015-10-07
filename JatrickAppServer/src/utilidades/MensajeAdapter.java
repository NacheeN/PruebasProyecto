package utilidades;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.Mensaje;
import dominio.Partido;

public class MensajeAdapter implements JsonSerializer<Mensaje> {
	
	@Override
	public JsonElement serialize(Mensaje mensaje, Type type, JsonSerializationContext jsc) {

		JsonObject jsonObject = new JsonObject();
	
		jsonObject.addProperty("EquipoEmisor", mensaje.getEquipoEmisor());
		jsonObject.addProperty("EquipoReceptor", mensaje.getEquipoReceptor().getNombreE());
		jsonObject.addProperty("Fecha", mensaje.getDate());
		jsonObject.addProperty("Descripcion", mensaje.getDescripcion());
				
		
		return jsonObject;
	}

}
