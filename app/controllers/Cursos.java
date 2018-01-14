package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.AsignaturaSeccion;
import models.Curso;
import models.Profesor;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.cursos.grupoCurso;

public class Cursos extends Controller {

	public static Result index() {

		return ok(grupoCurso.render("2012",
				Curso.listaCursosProfesor(session().get("id"))));
	}
	
	public static Result geCursosXprefesorJson(Long idTipoCategoria){
		//TODO: al implementar la sesion eliminaresto
		Long idUsuario=(long) 2;
		//session().get("id");
		ObjectNode cursosJson = Json.newObject();
		Profesor profesor=Profesor.getProfesor(idUsuario);
		List<Curso> cursos=Curso.listaCursosProfesor(idUsuario,idTipoCategoria);
		int cont=0;
		for(Curso curso : cursos){
			ObjectNode cursoscaracteristicasJson = Json.newObject();
			cursoscaracteristicasJson.put("idCurso", curso.idCurso);
			cursoscaracteristicasJson.put("nombreCurso", curso.nombre);
			//AsignaturaSeccion.getAsignaturasXCurso(profesor.idProfesor, curso.idCurso);
			cursosJson.put("curso"+cont, cursoscaracteristicasJson);
			cont++;
		}
		return ok(Json.toJson(cursosJson));
	}

}
