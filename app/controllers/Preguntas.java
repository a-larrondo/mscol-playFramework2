package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import models.Alternativa;
import models.Analizar;
import models.Aplicar;
import models.Asignatura;
import models.AsignaturaSeccion;
import models.AsociacionesCategoria;
import models.Comprender;
import models.Conocer;
import models.Evaluar;
import models.JerarquiaTipoPregunta;
import models.Pregunta;
import models.TipoPregunta;
import models.Unidad;
import models.Usuario;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Constantes;
import views.html.preguntas.filtroPreguntas;
import views.html.preguntas.formPregunta;
import views.html.preguntas.indexPregunta;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Preguntas extends Controller {

	final static Form<Pregunta> preguntaForm = form(Pregunta.class);

	public static Result index(Long idUnidad) {
		Long l = (long) 1;
		String email = "freddi@sample.com";
		String password = "test";
		Usuario usr = Usuario.getUsuario(email);

		session("id", usr.idUsuario.toString());
		return ok(indexPregunta.render(Pregunta.getPreguntas(idUnidad)));
	}

	public static Result getPreguntas(String idUnidades) {
		Long l = (long) 1;
		String email = "freddi@sample.com";
		String password = "test";
		Usuario usr = Usuario.getUsuario(email);
		Long lidU = (long) 0;
		try {
			lidU = Long.parseLong(idUnidades);
		} catch (Exception e) {
			lidU = (long) 0;
		}
		session("id", usr.idUsuario.toString());

		List<Pregunta> preguntas = Pregunta.getPreguntasUnidad(usr.idUsuario,
				lidU);
		// Logger.info("****************************************");

		Integer a = preguntas.size();
		// Logger.info(a.toString());

		ObjectNode preguntasJson = Json.newObject();
		// JSONObject preguntasJson = new JSONObject();

		// LinkedList alternativaJson = new LinkedList();

		// result.put("pregunta", preguntas.get(1).pregunta);
		// result.put("alternativa",
		// preguntas.get(1).alternativas.get(1).alternativaTxt);
		// result.put("correcta",
		// preguntas.get(1).alternativas.get(1).esCorrecta);
		// System.out.print("*********************************************");
		// System.out.print(preguntas);
		Integer npregunta = 0;
		for (Pregunta pregunta : preguntas) {

			ObjectNode preguntaJson = Json.newObject();
			preguntaJson.put("idPregunta", pregunta.idPregunta);
			preguntaJson.put("pregunta", pregunta.pregunta);
			//preguntaJson.put("unidad", pregunta.unidad.idUnidad);
			preguntaJson.put("asignatura",
					pregunta.unidad.asignatura.idAsignatura);
			Integer nalternativa = 0;
			ObjectNode alternativasJson = Json.newObject();
			for (Alternativa alternativa : pregunta.alternativas) {
				ObjectNode alternativaJson = Json.newObject();
				alternativaJson.put("alternativaTxt",
						alternativa.alternativaTxt);
				alternativaJson.put("correcta", alternativa.es_correcta);
				alternativasJson.put("alternativa" + nalternativa,
						alternativaJson);
				nalternativa++;
			}
			preguntaJson.put("alternativas", alternativasJson);

			try {
				preguntasJson.put("pregunta" + npregunta, preguntaJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			npregunta++;
		}

		System.out.print(preguntasJson);
		return ok(Json.toJson(preguntasJson));

	}

	public static Result formBlank() {
		Long ag = (long) 1;
		List<Asignatura> asignaturas = Asignatura.buscarXProfesor(ag);
		return ok(formPregunta.render(preguntaForm, asignaturas));
	}

	public static Result addPregunta() {
		Long l = (long) 1;
		String email = "elton@sample.com";
		String password = "test";
		Usuario usr = Usuario.getUsuario(email);

		session("id", usr.idUsuario.toString());
		Long ag = (long) 1;
		List<Asignatura> asignaturas = Asignatura.buscarXProfesor(ag);
		/*
		 * comentada hasta la activacion de la clase de seguridad
		 * if(Secured.isMemberOf(project)) {
		 */
		Form<Pregunta> preguntaF = preguntaForm.bindFromRequest();
		if (preguntaF.hasErrors()) {
			return badRequest(formPregunta.render(preguntaF, asignaturas));
		} else {
			Pregunta.crearPregunta(preguntaF.get(), session().get("id"));
			return redirect(routes.Application.index());

		}
		/*
		 * 
		 * } else { return forbidden(); }
		 */
	}

	public static Result getUnidades(String idAsignatura) {
		List<Unidad> unidades = Unidad.buscarXAsignatura(idAsignatura);
		ObjectNode unidadesJson = Json.newObject();

		Logger.info("inicia el metodo getUnidades ");
		String op = "";
		int con = 0;
		for (Unidad unidad : unidades) {
			ObjectNode unidadJson = Json.newObject();
			unidadJson.put("idUnidad", unidad.idUnidad);
			unidadJson.put("nombreUnidad", unidad.nombreUnidad);
			// op+="<option value='" + unidad.idUnidad + "''> " +
			// unidad.nombreUnidad + "</option>";
			unidadesJson.put("unidad" + con, unidadJson);
			con++;
		}
		Logger.info("menu optinos unidades= " + op);
		return ok(Json.toJson(unidadesJson));
	}

	public static Result getHabilidades(String pregunta) {
		Logger.info("****************************************");
		Logger.info("getHabilidades. pregunta= " + pregunta);
		Logger.info("****************************************");
		String html = "";
		List data = Arrays.asList("");
		// List<String> analizarList = Analizar.palabrasClave();
		if (pregunta.length() > 4) {
			for (String analizar : Analizar.palabrasClave()) {
				if (pregunta.toLowerCase().contains(analizar.toString())) {

					html += "analizar";
				}
			}

			for (String aplicar : Aplicar.palabrasClave()) {
				Logger.info("aplicar= " + aplicar);
				if (pregunta.toLowerCase().contains(aplicar.toString())) {

					html += " aplicar";
				}
			}
			for (String comprender : Comprender.palabrasClave()) {
				Logger.info("comprender= " + comprender);
				if (pregunta.toLowerCase().contains(comprender.toString())) {
					html += " comprender";
				}
			}
			for (String conocer : Conocer.palabrasClave()) {
				Logger.info("conocer= " + conocer);
				if (pregunta.toLowerCase().contains(conocer.toString())) {
					html += " conocer";
				}
			}
			for (String evaluar : Evaluar.palabrasClave()) {
				Logger.info("evaluar= " + evaluar);
				if (pregunta.toLowerCase().contains(evaluar.toString())) {
					html += " evaluar";
				}
			}
		}
		return ok(html);
	}
	public static Result filtroPreguntas() {
		
		List<TipoPregunta> tipoPregunta=TipoPregunta.getTipoPreguntas();
		return ok(filtroPreguntas.render(tipoPregunta));
		
	}
	//obtener la informacion de curso y asignatura filtrada por el tipo pregunta y id del usuario
public static Result getFiltros(String idTipo) {
	Logger.info("idTipo="+idTipo);
	Long tipoPregunta=Long.parseLong(idTipo);
	String emailUsuario = "freddi@sample.com";
	Long idUsuario=(long) 2;
	Long idProfesor=(long) 1;
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

public static Result infoCategorias(Long idTipo,Long idJerarquia,Long idCategoriaSup){
	
	return ok();
	
}
public static Result getPreguntasfiltradas(){
	JsonNode filtroPreguntas = request().body().asJson();
	String curso=null;
	String asignatura=null;
	ArrayList<Long> idCategoriasSup=new ArrayList<>();
	HashMap<Long,Long>categoriasMap=new HashMap<Long, Long>();
	ObjectNode infoPreguntasJson = Json.newObject();
	int pagina=0;
	if(filtroPreguntas.has("pagina")){
		try {
			pagina=Integer.parseInt(filtroPreguntas.get("pagina").toString());			
		} catch (NumberFormatException e) {
			pagina=0;
		}
	}
	if(filtroPreguntas.has("curso")){
		 curso= filtroPreguntas.get("curso").toString();
		//Logger.info("curso="+curso);
	}
	if(filtroPreguntas.has("asignatura")){
		 asignatura= filtroPreguntas.get("asignatura").toString();
		//Logger.info("asignatura="+asignatura);
	}
	if(filtroPreguntas.has("categorias")){
		int iNumeroMayor, iPosicion;
		
		//Presuponemos que el numero mayor es el primero
		iNumeroMayor = 0;
		iPosicion = 0;
		
		if (filtroPreguntas.get("categorias").isArray()) {
			
			Long cont=(long) 1;
			for (final JsonNode objNode : filtroPreguntas.get("categorias")) {
				if(objNode.has("categoriaid") && objNode.has("categoriaSupid")){
					String categoriaSupid=objNode.get("categoriaSupid").toString();
					String categoriaid=objNode.get("categoriaid").toString();
					if(Constantes.isNumeric(categoriaSupid) && Constantes.isNumeric(categoriaid)){
						Long categoriaSupidL=Long.valueOf(objNode.get("categoriaSupid").toString()).longValue();
						Long categoriaidL=Long.valueOf(objNode.get("categoriaid").toString()).longValue();
						categoriasMap.put(categoriaidL, categoriaSupidL);
						idCategoriasSup.add(categoriaSupidL);						
					}
				//categoriasMap.put(Long.getLong(objNode.get("categoriaid").toString()), Long.getLong(objNode.get("idcatsup").toString()));
				//idCategoriasSup.add(Long.getLong());
				}
				//Logger.info("objeto nuemero "+cont +" es igual a "+objNode.toString());
				cont++;
			}
			
		}
		for (Long idCategoriaSup : idCategoriasSup) {
			categoriasMap.remove(idCategoriaSup);
		}
		
		 
		Page<Pregunta> page = Pregunta.getPreguntasCategorias(categoriasMap,pagina);		
		ObjectNode preguntasJson = Json.newObject();
		try {	
			infoPreguntasJson.put("pageIndex", page.getPageIndex());
			infoPreguntasJson.put("InfoPags", page.getDisplayXtoYofZ(" Hasta ", " De "));
			infoPreguntasJson.put("pageTotal", page.getTotalPageCount());
		} catch (Exception e) {
			//preguntasJson.put("pregunta" + 0, 0);
			e.printStackTrace();
		}
		List<Pregunta> preguntas= page.getList(); 
		Integer npregunta = 0;
		for (Pregunta pregunta : preguntas) {
			ObjectNode preguntaJson = Json.newObject();
			preguntaJson.put("idPregunta", pregunta.idPregunta);
			preguntaJson.put("pregunta", pregunta.pregunta);
			//preguntaJson.put("unidad", pregunta.unidad.idUnidad);
			//preguntaJson.put("asignatura",pregunta.unidad.asignatura.idAsignatura);
			Integer nalternativa = 0;
			/*
			ObjectNode alternativasJson = Json.newObject();
			for (Alternativa alternativa : pregunta.alternativas) {
				ObjectNode alternativaJson = Json.newObject();
				alternativaJson.put("alternativaTxt",
						alternativa.alternativaTxt);
				alternativaJson.put("correcta", alternativa.es_correcta);
				alternativasJson.put("alternativa" + nalternativa,
						alternativaJson);
				nalternativa++;
			}
			
			preguntaJson.put("alternativas", alternativasJson);
			 */
			try {				
				preguntasJson.put("pregunta" + npregunta, preguntaJson);
			} catch (Exception e) {
				preguntasJson.put("pregunta" + 0, 0);
				//e.printStackTrace();
			}
			npregunta++;
		}
		infoPreguntasJson.put("preguntas", preguntasJson);
		
		//JSONArray lang= JSONArray( filtroPreguntas.get("categorias").toString());
		 //asignatura= filtroPreguntas.get("asignatura").toString();
		//Logger.info("asignatura="+asignatura);
	}
	//Logger.info("get filtro preguntas");
	//Logger.info(filtroPreguntas.toString());
	return ok(infoPreguntasJson);
}
public static Result getPreguntasxCategorias(Long idAsignaturaL,Long idCursoL,Long idJerarquiaL){
	List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
	asociacionesCategorias =AsociacionesCategoria.getCategorias(idAsignaturaL,idCursoL, idJerarquiaL);
	
	ObjectNode infoCategorias = Json.newObject();
	
	int pagina=0;
	int contcat=0;
	for (AsociacionesCategoria asociacionesCategoria : asociacionesCategorias) {
		int preguntasXpaginas=20;
		ObjectNode infoPreguntasJson = Json.newObject();
		HashMap<Long,Long>categoriasMap=new HashMap<Long, Long>();
		categoriasMap.put(asociacionesCategoria.categoria_id.id_categoria,(long) 0);
		Page<Pregunta> page = Pregunta.getPreguntasCategorias(categoriasMap,pagina,preguntasXpaginas);		
		ObjectNode preguntasJson = Json.newObject();
		infoPreguntasJson.put("nombreCategoria",asociacionesCategoria.categoria_id.nombre_categoria );
		try {	
			infoPreguntasJson.put("pageIndex", page.getPageIndex());
			infoPreguntasJson.put("InfoPags", page.getDisplayXtoYofZ(" Hasta ", " De "));
			infoPreguntasJson.put("pageTotal", page.getTotalPageCount());
		} catch (Exception e) {
			//preguntasJson.put("pregunta" + 0, 0);
			e.printStackTrace();
		}
		List<Pregunta> preguntas= page.getList(); 
		Integer npregunta = 0;
		/*
		for (Pregunta pregunta : preguntas) {
			ObjectNode preguntaJson = Json.newObject();
			preguntaJson.put("idPregunta", pregunta.idPregunta);
			preguntaJson.put("pregunta", pregunta.pregunta);
			//preguntaJson.put("unidad", pregunta.unidad.idUnidad);
			//preguntaJson.put("asignatura",pregunta.unidad.asignatura.idAsignatura);
			Integer nalternativa = 0;
			
			ObjectNode alternativasJson = Json.newObject();
			for (Alternativa alternativa : pregunta.alternativas) {
				ObjectNode alternativaJson = Json.newObject();
				alternativaJson.put("alternativaTxt",
						alternativa.alternativaTxt);
				alternativaJson.put("correcta", alternativa.es_correcta);
				alternativasJson.put("alternativa" + nalternativa,
						alternativaJson);
				nalternativa++;
			}
			
			preguntaJson.put("alternativas", alternativasJson);
			 
			try {				
				preguntasJson.put("pregunta" + npregunta, preguntaJson);
			} catch (Exception e) {
				preguntasJson.put("pregunta" + 0, 0);
				//e.printStackTrace();
			}
			npregunta++;
			infoPreguntasJson.put("preguntas", preguntasJson);
		}*/
		infoCategorias.put("preguntasCategorias"+contcat, infoPreguntasJson);
		contcat++;
	}
	return ok(infoCategorias);
	
	
}
	

}
