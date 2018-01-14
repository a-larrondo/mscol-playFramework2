package controllers;

import models.Unidad;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.unidades.indexUnidad;

public class Unidades extends Controller {

	public static Result index(Long IdAsignatura, String nombreAsignatura) {
		String IdUsuario = session().get("id");
		return ok(indexUnidad.render(nombreAsignatura,
				Unidad.getUnidades(IdAsignatura)));
	}

}
