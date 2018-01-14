package controllers;

import models.Curso;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.profesores.homeProfesor;

public class Profesores extends Controller {

	public static Result index() {
		// List<Profesor> profe=Coordinador.obtenerProfesores((long) 1);
		Long l = (long) 1;
		String email = "freddi@sample.com";
		String password = "test";
		Usuario usr = Usuario.getUsuario(session().get("email"));

		session("id", usr.idUsuario.toString());
		return ok(homeProfesor.render(usr,
				Curso.listaCursosProfesor(usr.idUsuario)));

	}

}
