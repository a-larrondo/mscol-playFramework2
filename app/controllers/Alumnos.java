package controllers;

import models.Alumno;
import models.Curso;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.alumnos.indexAlumnos;

public class Alumnos extends Controller {

	public static Result index(Long curso) {

		return ok(indexAlumnos.render(Curso.find.byId(curso),
				Alumno.getAlumnosXcurso(curso)));
	}
}
