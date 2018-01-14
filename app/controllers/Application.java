package controllers;

import static play.data.Form.form;
import models.Usuario;
import play.Logger;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.index;
import views.html.login;


public class Application extends Controller {

	 public static class Login {
	        
	        public String email;
	        public String password;

		public String validate() {
			 Logger.info("usuario="+email);
			 Logger.info("password="+password);
			 boolean autenticado = Usuario.authenticate(email, password);
				if (autenticado == false) {
				return "Usuario o contraseña invalida";
			} else {
				// Registro_Login.creaRegistro(validaUsuario.idUsuario ,
				// ip,navegador);
				return null;
			}
		}
	}
	public static Result login() {
		return ok(login.render(form(Login.class)));
	}

	public static Result authenticate() {
		 Form<Login> loginForm = form(Login.class).bindFromRequest();
	        
		if (loginForm.hasErrors()) {
			// en caso de error envia a la pagina inicial
			return badRequest(login.render(loginForm));
		} else {
			 Logger.info("email="+loginForm.get().email);
			session().clear();
			// envia a la pagina inicial del usuario autenticado
			Logger.info("Usuario valido");
			/*
			 * Logger.info("descripcion perfil="+loginForm.get().perfil.get(0).
			 * descripcion);
			 */

			session("email", loginForm.get().email);

			return redirect(routes.Profesores.index()
			// routes.ElegirPerfil.elegir()
			);
		}
	}

	@Security.Authenticated(Seguridad.class)
	public static Result index() {
		// Logger.info("session email="+session().get("email"));
		// Logger.info("session isEmpty="+session().get("email").isEmpty());
		// Logger.info("session length="+session().get("email").length());
		// || session().get("email")!=null

		return ok(index.render(session().get("email")));

	}

	/**
	 * muestra la pagina inicial del programa y la por defecto en caso de no
	 * complir ninguna condicion
	 */
	

	public static Result logout() {
		session().clear();
		flash("success", "Su secion a terminado");
		return redirect(routes.Application.login());
	}

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter(
				"jsRoutes",

				// Routes for Projects
				controllers.routes.javascript.Preguntas.getUnidades(),
				controllers.routes.javascript.Preguntas.getHabilidades(),
				controllers.routes.javascript.Preguntas.getPreguntas(),
				controllers.routes.javascript.Preguntas.getHabilidades(),
				controllers.routes.javascript.Preguntas.getPreguntasxCategorias(),
				controllers.routes.javascript.AsignaturaSecciones.getFiltros(),
				controllers.routes.javascript.AsignaturaSecciones.getTipoFiltros(),
				controllers.routes.javascript.Categorias.getCategorias(),
				controllers.routes.javascript.Categorias.getJerarquiaFiltro(),
				controllers.routes.javascript.Categorias.getJerarquia(),
				controllers.routes.javascript.Cursos.geCursosXprefesorJson(),
				controllers.routes.javascript.Preguntas.getPreguntasfiltradas(),
				controllers.routes.javascript.Alternativas.getAlternativas()));
		
	}

	public static Result ajaxWithError() {
		return badRequest("Pamplinas algo no esta bien");
	}
}
