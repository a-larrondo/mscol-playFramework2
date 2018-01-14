package controllers;

import models.Usuario;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.homeCorrdinador;

//@Security.Authenticated(Seguridad.class)
public class Coordinadores extends Controller {

	public static Result home() {
		// List<Profesor> profe=Coordinador.obtenerProfesores((long) 1);
		Long l = (long) 1;
		Usuario usr = Usuario.findId.ref(l);
		return ok(

		homeCorrdinador.render(usr));

	}

}
// @(profesor: List[Profesor], usuario:Usuario )(body: Html)