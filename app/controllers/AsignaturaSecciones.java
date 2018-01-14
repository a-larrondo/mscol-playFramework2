package controllers;

import java.util.List;

import models.Asignatura;
import models.AsignaturaSeccion;
import models.TipoPregunta;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;


public class AsignaturaSecciones extends Controller {
/*
	public static Result index() {

		return ok(grupoCurso.render("2012",
				AsignaturaSeccion.getSecciones(session().get("id"))));
	}*/
	//TODO: borrar una ves que se implemente el uso de sesiones
	
	String emailUsuario = "freddi@sample.com";
	static Long idUsuario=(long) 2;
	static Long idProfesor=(long) 1;
	public static Result getFiltros(String idTipo) {
		Logger.info("idTipo="+idTipo);
		Long tipoPregunta=Long.parseLong(idTipo);
		
		List <AsignaturaSeccion> cursosAsignaturas;
		ObjectNode asignaturasJson = Json.newObject();
		ObjectNode cursosJson = Json.newObject();
		ObjectNode cursosAsignaturaJson = Json.newObject();
		Logger.info("inicia el metodo getUnidades ");
		
		cursosAsignaturaJson.put("asignatura", AsignaturaSeccion.getAsignaturasXprofesor(idProfesor,tipoPregunta));
		cursosAsignaturaJson.put("curso", AsignaturaSeccion.getCursosXprofesor(idProfesor,tipoPregunta));
		
		//cursosAsignaturaJson.put("asignaturas", asignaturasJson);
		//cursosAsignaturaJson.put("cursos", cursosJson);
			return ok(Json.toJson(cursosAsignaturaJson));
			
		}
	public static Result getTipoFiltros() {
		//Long tipoPregunta=Long.parseLong(idTipo);
		List<TipoPregunta> tipoPreguntas=TipoPregunta.getcategoriasTipoPreguntas();
		Long tipoPregunta=tipoPreguntas.get(0).idTipoPregunta;
		
		ObjectNode tipoJson = Json.newObject();

		ObjectNode asignaturasJson = Json.newObject();
		int con = 0;
		for (TipoPregunta tipoPreg : tipoPreguntas) {
			ObjectNode cursoJson = Json.newObject();
			if(tipoPregunta==tipoPreg.idTipoPregunta){
			cursoJson.put("selected", 1);
			}
			else{
				cursoJson.put("selected", 0);
			}
			cursoJson.put("idTipoPregunta", tipoPreg.idTipoPregunta);
			cursoJson.put("nombreTipo", tipoPreg.nombreTipo);
			tipoJson.put("tipoPregunta" + con, cursoJson);		
			con++;
		}
		ObjectNode cursosAsignaturaJson = Json.newObject();
		Logger.info("inicia el metodo getUnidades ");
		
		cursosAsignaturaJson.put("tipoPregunta", tipoJson);		
		cursosAsignaturaJson.put("asignatura", AsignaturaSeccion.getAsignaturasXprofesor(idProfesor,tipoPregunta));
		cursosAsignaturaJson.put("curso", AsignaturaSeccion.getCursosXprofesor(idProfesor,tipoPregunta));
		
		//cursosAsignaturaJson.put("asignaturas", asignaturasJson);
		//cursosAsignaturaJson.put("cursos", cursosJson);
			return ok(Json.toJson(cursosAsignaturaJson));
			
		}
	
	static Result asignaturasXcurso(Long idProfesor, Long idCurso){
		List<AsignaturaSeccion> AsignaturaSecciones=AsignaturaSeccion.getAsignaturasXCurso(idProfesor, idCurso);
		ObjectNode asignaturasJson = Json.newObject();
		int con = 0;
		for(AsignaturaSeccion asignaturaSeccion:AsignaturaSecciones){
			ObjectNode asignaturaJson = Json.newObject();
				asignaturaJson.put("idAsignatura", asignaturaSeccion.asignatura.idAsignatura);
				asignaturaJson.put("nombreAsignatura", asignaturaSeccion.asignatura.nombreAsignatura);
				
				asignaturasJson.put("asignatura" + con, asignaturaJson);
				con++;		
		}
		return ok(Json.toJson(asignaturasJson));
		
	}
}
