package controllers;

import java.util.ArrayList;
import java.util.List;

import models.AsociacionesCategoria;
import models.JerarquiaTipoPregunta;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Categorias extends Controller {
	
	//obtener y ordenar las categorias de acuerdo a su nivel de gerarquia
	public static Result getJerarquia(String idTipo,String idAsignatura,String idCurso){
		Long tipoPregunta=Long.parseLong(idTipo);
		Long idAsignaturaL=Long.parseLong(idAsignatura);
		Long idCursoL=Long.parseLong(idCurso);
		ObjectNode categoriasJson = Json.newObject();
		String Html = "";
		Html+="<div id='btngetpreguntas' class='btn btn-default'>Filtrar preguntas</div>";
		Long idCategoriaSup=null;
		//obtenemos las jerarquias de acuerdo al tipo de pregunta (PSU, SIMCE, etc)
		List<JerarquiaTipoPregunta> jerarquiaTipoPreguntas=JerarquiaTipoPregunta.getJegarquia(tipoPregunta);
		//recorremos los distintos niveles de jerarquia 
		for (JerarquiaTipoPregunta jerarquiaTipoPregunta : jerarquiaTipoPreguntas) {			
			ObjectNode categoriaJson = Json.newObject();	
			Long idJerarquia=jerarquiaTipoPregunta.id_jerarquia;
			List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
			Html+="<div data-idJerarquia='"+jerarquiaTipoPregunta.id_jerarquia+"'class='jerarquias' >";
			Html+=jerarquiaTipoPregunta.nombreJerarquia+"<br>";	
			// obtenemos las categorias de la gerarquia
			 asociacionesCategorias =AsociacionesCategoria.getCategorias(idAsignaturaL,idCursoL, idJerarquia);
			int con=0;
			Long idCategoria=(long) -1;
			Long idCatSup=(long) -1;
			if(!asociacionesCategorias.isEmpty()){
				idCategoria=asociacionesCategorias.get(0).categoria_id.id_categoria;
			}
			// almacenamos en un Json la info de la categoria
			for (AsociacionesCategoria asociacionesCategoria : asociacionesCategorias) {
				ObjectNode detalleCategoriaJson = Json.newObject();	
				detalleCategoriaJson.put("idCategoria", asociacionesCategoria.categoria_id.id_categoria);
				detalleCategoriaJson.put("nombreCategoria", asociacionesCategoria.categoria_id.nombre_categoria);
				if(asociacionesCategoria.id_categoria_superior!=null){
				 //verificamos que tenga categoria superior en caso de ser la categoria superior se le da id 0	
					if(asociacionesCategoria.id_categoria_superior.id_categoria!=idCatSup){
						if(idCatSup!=(long)-1){
							Html+="</div>";
						}
						Html+="<div class='categoria' data-idcatsup='"+asociacionesCategoria.id_categoria_superior.id_categoria+"' style='display: none;'>"+asociacionesCategoria.id_categoria_superior.nombre_categoria+"<br>";									
						 idCatSup=asociacionesCategoria.id_categoria_superior.id_categoria;
					//Html+="<div class='categoria'>"+asociacionesCategoria.id_categoria_superior.nombre_categoria+"<br>";				
						//Html+="</div>";
					}
					
				}
				else{
					Html+="<div class='categoria' data-idcatsup='0'><br>";
					idCatSup=(long) -2;
					
				}
				categoriaJson.put(jerarquiaTipoPregunta.nombreJerarquia + con , detalleCategoriaJson);
				con++;
				
				if(idCategoria.equals(asociacionesCategoria.categoria_id.id_categoria)){
					Html+="<input type='checkbox' value='" + asociacionesCategoria.categoria_id.id_categoria + "'> "+  asociacionesCategoria.categoria_id.nombre_categoria + "<br>";
					idCategoriaSup=idCategoria;
				}
				else{
					Html+="<input type='checkbox' value='" + asociacionesCategoria.categoria_id.id_categoria + "'> " +  asociacionesCategoria.categoria_id.nombre_categoria + "<br>";
				}
			}
			//Html+="</select>";
			Html+="</div>"; //end div class categoria
			Html+="</div>";
			categoriasJson.put(jerarquiaTipoPregunta.nombreJerarquia , categoriaJson);
			
		}
		
		
		return ok(Html);
	}
	
	public ObjectNode getCategorias(Long idAsignaturaL,Long idCursoL,Long idJerarquia){
		ObjectNode categoriaJson = Json.newObject(); 
		List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
		 asociacionesCategorias =AsociacionesCategoria.getCategorias(idAsignaturaL,idCursoL, idJerarquia);
		 String Html="";
		 Long count=(long) 0;
		 for (AsociacionesCategoria asociacionesCategoria : asociacionesCategorias) {
				ObjectNode detalleCategoriaJson = Json.newObject();	
				detalleCategoriaJson.put("idCategoria", asociacionesCategoria.categoria_id.id_categoria);
				detalleCategoriaJson.put("nombreCategoria", asociacionesCategoria.categoria_id.nombre_categoria);
				if(asociacionesCategoria.id_categoria_superior!=null){				 
						Html+="<div class='categoria' data-idcatsup='"+asociacionesCategoria.id_categoria_superior.id_categoria+"' style='display: none;'>"+asociacionesCategoria.id_categoria_superior.nombre_categoria+"<br>";	
					}					
					
		Html+="<input type='checkbox' value='" + asociacionesCategoria.categoria_id.id_categoria + "'> " +  asociacionesCategoria.categoria_id.nombre_categoria + "<br>";
		 categoriaJson.put(	"categoria"+count,	detalleCategoriaJson);
		 count++;
		}
		
		 return categoriaJson;
		
	}
	
	public static Result getCategorias (String idCurso,String idAsignatura,String idCategoriaSup,String idJerarquia){
		List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
		Long idAsignaturaL=Long.parseLong(idAsignatura);
		Long idCategoriaSupL=Long.parseLong(idCategoriaSup);
		Long idCursoL=Long.parseLong(idCurso);
		Long idJerarquiaL=Long.parseLong(idJerarquia);
		asociacionesCategorias =AsociacionesCategoria.getCategoriasfiltradas(idCursoL,idAsignaturaL,idCategoriaSupL,idJerarquiaL);	
		ObjectNode categoriasJson = Json.newObject();
		String html;
		int con=0;
		Long idCategoria=(long) -1;
		ObjectNode categoriaJson = Json.newObject();
		if(!asociacionesCategorias.isEmpty()){
			idCategoria=asociacionesCategorias.get(0).categoria_id.id_categoria;
		}
		for (AsociacionesCategoria asociacionesCategoria : asociacionesCategorias) {
			ObjectNode detalleCategoriaJson = Json.newObject();	
			detalleCategoriaJson.put("idCategoria", asociacionesCategoria.categoria_id.id_categoria);
			detalleCategoriaJson.put("nombreCategoria", asociacionesCategoria.categoria_id.nombre_categoria);
			categoriaJson.put(asociacionesCategoria.categoria_id.nombre_categoria + con , detalleCategoriaJson);
			con++;
				
			if(idCategoria.equals(asociacionesCategoria.categoria_id.id_categoria)){
				idCategoriaSupL=idCategoria;
			}
			
		}
		return ok();
		
	}
	public static Result getJerarquiaFiltro(String idTipo,String idAsignatura,String idCurso){
		Long tipoPregunta=Long.parseLong(idTipo);
		Long idAsignaturaL=Long.parseLong(idAsignatura);
		Long idCursoL=Long.parseLong(idCurso);
		ObjectNode categoriasJson = Json.newObject();
		String Html = "";
		Long idCategoriaSup=null;
		//obtenemos las jerarquias de acuerdo al tipo de pregunta (PSU, SIMCE, etc)
		List<JerarquiaTipoPregunta> jerarquiaTipoPreguntas=JerarquiaTipoPregunta.getJegarquia(tipoPregunta);
		//recorremos los distintos niveles de jerarquia 
		for (JerarquiaTipoPregunta jerarquiaTipoPregunta : jerarquiaTipoPreguntas) {			
			ObjectNode categoriaJson = Json.newObject();	
			Long idJerarquia=jerarquiaTipoPregunta.id_jerarquia;
			List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
			Html+="<div data-idJerarquia='"+jerarquiaTipoPregunta.id_jerarquia+"'class='jerarquias' >";
				
			// obtenemos las categorias de la gerarquia
			 asociacionesCategorias =AsociacionesCategoria.getCategorias(idAsignaturaL,idCursoL, idJerarquia);
			String checkBox="<input type='checkbox' name='"+jerarquiaTipoPregunta.nombreJerarquia+"' value='"+jerarquiaTipoPregunta.id_jerarquia+"'>"+jerarquiaTipoPregunta.nombreJerarquia+"<br>";
			Html+=checkBox;
			int con=0;
			Long idCategoria=(long) -1;
			Long idCatSup=(long) -1;
			if(!asociacionesCategorias.isEmpty()){
				idCategoria=asociacionesCategorias.get(0).categoria_id.id_categoria;
			}
			
			Html+="</div>";
			categoriasJson.put(jerarquiaTipoPregunta.nombreJerarquia , categoriaJson);
			
		}
		
		
		return ok(Html);
	}
	
	public static Result getJerarquia(Long idAsignaturaL,Long idCursoL,Long idJerarquia){
		List<AsociacionesCategoria> asociacionesCategorias=new ArrayList<AsociacionesCategoria>();
		ObjectNode categoriaJson = Json.newObject();
		// obtenemos las categorias de la gerarquia
		 asociacionesCategorias =AsociacionesCategoria.getCategorias(idAsignaturaL,idCursoL, idJerarquia);
		int con=0;
		Long idCategoria=(long) -1;
		Long idCatSup=(long) -1;
		if(!asociacionesCategorias.isEmpty()){
			idCategoria=asociacionesCategorias.get(0).categoria_id.id_categoria;
		}
		// almacenamos en un Json la info de la categoria
		for (AsociacionesCategoria asociacionesCategoria : asociacionesCategorias) {
			ObjectNode detalleCategoriaJson = Json.newObject();	
			detalleCategoriaJson.put("idCategoria", asociacionesCategoria.categoria_id.id_categoria);
			detalleCategoriaJson.put("nombreCategoria", asociacionesCategoria.categoria_id.nombre_categoria);
			if(asociacionesCategoria.id_categoria_superior!=null){
			 //verificamos que tenga categoria superior en caso de ser la categoria superior se le da id 0	
				if(asociacionesCategoria.id_categoria_superior.id_categoria!=idCatSup){
					if(idCatSup!=(long)-1){
						//Html+="</div>";
					}
					//Html+="<div class='categoria' data-idcatsup='"+asociacionesCategoria.id_categoria_superior.id_categoria+"' style='display: none;'>"+asociacionesCategoria.id_categoria_superior.nombre_categoria+"<br>";									
					 idCatSup=asociacionesCategoria.id_categoria_superior.id_categoria;
				//Html+="<div class='categoria'>"+asociacionesCategoria.id_categoria_superior.nombre_categoria+"<br>";				
					//Html+="</div>";
				}
				
			}
			categoriaJson.put("ascategoriad"+con,detalleCategoriaJson);
			con++;
		}
			
			
			
			return ok(categoriaJson);
		}	
	
	
}
