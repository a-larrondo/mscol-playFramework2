package controllers;

import models.Usuario;
import play.mvc.Controller;

public class Usuarios extends Controller {

	public static Usuario tipoUsuario(String email) {
		Usuario user = Usuario.getUsuario(email);
		return user;

	}

}
