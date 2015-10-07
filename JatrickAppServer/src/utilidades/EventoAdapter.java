package utilidades;

import java.lang.reflect.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dominio.EventosPartido;
import dominio.Jugador;
import dominio.Partido;

public class EventoAdapter implements JsonSerializer<EventosPartido> {

	@Override
	public JsonElement serialize(EventosPartido evento, Type type,JsonSerializationContext jsc) {

	//	System.out.println("ESTOY EN ADAPTER ANTES DE TODO");
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("idEvento", evento.getId());
		jsonObject.addProperty("Evento", evento.getEvento());
		jsonObject.addProperty("Descripcion", evento.getDescripcion());
		jsonObject.addProperty("EsGol", evento.getEsgol());
		if(evento.getFactorAleatorio1()==null){

			jsonObject.addProperty("FactorAleatorio1","");
		}
		else{
			
		
		jsonObject.addProperty("FactorAleatorio1", evento.getFactorAleatorio1());
		}
		
		if(evento.getFactorAleatorio2()==null){

			jsonObject.addProperty("FactorAleatorio2","");
		}
		else{
			
		
		jsonObject.addProperty("FactorAleatorio2", evento.getFactorAleatorio2());
		}
		
		
		if(evento.getResultado()==null){
			jsonObject.addProperty("Resultado", "");
		}else
		{
		jsonObject.addProperty("Resultado", evento.getResultado());
		}
		
		jsonObject.addProperty("Partido", evento.getPartido().getId());
			
		
		//System.out.println("ESTOY EN ADAPTER"+jsonObject);
		
		return jsonObject;
	}

}
