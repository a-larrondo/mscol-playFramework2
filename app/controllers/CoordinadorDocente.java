package controllers;

import models.Usuario;
import play.mvc.Controller;
import play.mvc.Result;

public class CoordinadorDocente extends Controller {

	public static Result home() {
		// List<Profesor> profe=Coordinador.obtenerProfesores((long) 1);
		Long l = (long) 1;
		String email = "freddi@sample.com";
		String password = "test";
		Usuario usr = Usuario.getUsuario(email);

		session("id", usr.idUsuario.toString());
		return redirect(routes.Cursos.index());

	}
}