package controllers;

import models.Asignatura;
import models.Curso;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.asignaturas.indexAsignaturas;

public class Asignaturas extends Controller {

	public static Result index(Long curso) {

		return ok(indexAsignaturas.render(Curso.find.byId(curso),
				Asignatura.buscarXCurso(curso, session().get("id"))));
	}

}