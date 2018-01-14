package controllers;

import java.util.List;

import models.Perfil;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.EleccionPerfil;

public class ElegirPerfil extends Controller {

	/* @Security.Authenticated(Seguridad.class) */
	public static Result elegir() {

		Usuario user = Usuarios.tipoUsuario(session().get("email"));
		List<Perfil> perfil = user.perfil;

		session("id", user.idUsuario.toString());

		/*
		 * Logger.info("id perfil="+perfil.get(0).idPerfil.toString());
		 * Logger.info("descripcion perfil="+perfil.get(0).descripcion);
		 */

		if (perfil.size() > 1) { /*
								 * en caso de que el usuario tenga mas de un
								 * perfil
								 */
			/*
			 * String s = ""; int i; Logger.info("asdf "+perfil.size());
			 * for(i=0;i<perfil.size() ;i++){ s =
			 * s.concat(perfil.get(i).idPerfil.toString()); }
			 */
			return ok(EleccionPerfil.render(perfil));
		} else {
			long l = perfil.get(0).idPerfil;
			int i = (int) l; /* i es el tipo de perfil del usuario */

			switch (i) {

			case 1: /* Caso Coordinador */
				return redirect(routes.Coordinadores.home());

			case 2: /* Caso Profesor */
				return redirect(routes.Profesores.index());

			case 3: /* Caso Alumno */
				return redirect(routes.Application.index());

			default:
				return redirect(routes.Application.index());
			}
		}
	}

}
