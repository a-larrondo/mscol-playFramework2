package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import models.Categoria;
import models.Pregunta;
import models.Profesor;
import models.Prueba;
import models.TipoPregunta;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.pruebas.getPreguntasPrueba;
import views.html.preguntas.filtroPreguntas;


public class Pruebas extends Controller{
	final static Form<Prueba> pruebaForm = form(Prueba.class);

	public static Result crearPrueba(){
		  DynamicForm requestData = form().bindFromRequest();
		  Set<String> keys = request().body().asFormUrlEncoded().keySet();
		  List <String> idPreguntas=new ArrayList<String>();
		  String[] foo;
		  foo = request().body().asFormUrlEncoded().get("idPregunta");
		 List<Pregunta> preguntas=new ArrayList<>();
		  Prueba prueba=new Prueba();
		  //idPregunta
		  for (String key : foo) {
			  //idPreguntas.add(requestData.get(key));
			 Pregunta pregunta=new Pregunta();
			 //String idpreguntaString=key;
			 Long idpregunta=Long.parseLong(key.trim());
			 pregunta.setidPregunta(idpregunta);
			 preguntas.add(pregunta);
			 
			}
		 
		  String id=session().get("id");
		  Profesor profesor=  Profesor.getProfesor(Long.getLong(id));
		  prueba.setProfesor(profesor);
		  prueba.setPreguntas(preguntas);
		  
		  prueba.save();
		  
		   // String firstname = requestData.get("idPregunta");
		   // String lastname = requestData.get("idPregunta[9]");
		   
		    //Prueba.crearPrueba(prueba);
		return null;	
	}
	public static Result getPrueba(Long idPrueba){
		Prueba prueba=Prueba.getPrueba(idPrueba);
		List<Prueba> preguntasda=null;		
		List<Pregunta>preguntas =Pregunta.getPreguntasXprueba(idPrueba);
		List<Categoria>categorias =Categoria.getCategoriasXprueba(idPrueba);		
		return ok(getPreguntasPrueba.render(preguntas,categorias));		
		
	}
	public static Result getPruebas(Long idCurso){
		return TODO;
		
	}
}