package controllers;

import java.util.List;

import models.Alternativa;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Alternativas extends Controller {
	
	public static Result getAlternativas() {
		Logger.info("get alternativas");
		JsonNode filtroPreguntas = request().body().asJson();
		
		Long idPregunta=(long) 0;
		if(filtroPreguntas.has("idPregunta")){
			try {
				idPregunta=Long.parseLong(filtroPreguntas.get("idPregunta").toString());			
			} catch (NumberFormatException e) {
				idPregunta=(long) 0;
			}
		}
		ObjectNode alternativasJson = Json.newObject();
		Long idPreguntaL=idPregunta;
		List<Alternativa>alternativas=Alternativa.getAlternativaXpregunta(idPreguntaL);
		Long nalternativa=(long) 0;
		for(Alternativa alternativa: alternativas){
			ObjectNode alternativaJson = Json.newObject();
			alternativaJson.put("alternativaTxt",alternativa.alternativaTxt);
			alternativaJson.put("idAlternativa", alternativa.idAlternativa);
			alternativaJson.put("esImagen", alternativa.es_imagen);
			alternativaJson.put("correcta", alternativa.es_correcta);
			alternativasJson.put("alternativa" + nalternativa,
					alternativaJson);
			nalternativa++;
		}
		return ok(alternativasJson);
	}

}
