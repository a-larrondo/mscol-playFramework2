package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Asignatura;
import models.AsociacionesCategoria;
import models.Categoria;
import models.Curso;
import models.JerarquiaTipoPregunta;
import models.TipoPregunta;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.categorias.*;
public class JerarquiaTipoPreguntas extends Controller {

	
	
	
	static public Result gerarquiaFiltros(Long idTipoPregunta){
		ObjectNode tipoPreguntas = Json.newObject();
		session().get("id");
			List<JerarquiaTipoPregunta> jerarquiaTipoPreguntas=JerarquiaTipoPregunta.getJegarquia(idTipoPregunta);
			
			for (JerarquiaTipoPregunta jerarquiaTipoPregunta : jerarquiaTipoPreguntas) {			
				ObjectNode categoriaJson = Json.newObject();	
				Long idJerarquia=jerarquiaTipoPregunta.id_jerarquia;			
			}
		
		
		
		
		return ok();
		
	}
	
	static public Result gerarquia(){
		//TODO: modificar para que discrimine entre usuario profesor y coordinador
		Long idUsuario=(long) 2;
		List<TipoPregunta> tiposPreguntas=TipoPregunta.getTipoPreguntas();
		List<JerarquiaTree> jerarquiaTrees= new ArrayList();
		for(TipoPregunta tipoPregunta :tiposPreguntas){
			JerarquiaTree jerarquiaTree1= new JerarquiaTree();
			jerarquiaTree1.idElemento=tipoPregunta.idTipoPregunta;
			jerarquiaTree1.nombreElemnto=tipoPregunta.nombreTipo;
			List <Curso> cursos=Curso.listaCursosProfesor(idUsuario,tipoPregunta.idTipoPregunta);
			/*for(Curso Curso :cursos){
				JerarquiaTree jerarquiaTree2= new JerarquiaTree();
				jerarquiaTree2.idElemento=Curso.idCurso;
				jerarquiaTree2.nombreElemnto=Curso.nombre;
				List<Asignatura> asignaturas=Asignatura.buscarXCurso(Curso.idCurso, Long.toString(idUsuario));
				for(Asignatura asignatura: asignaturas ){					
					JerarquiaTree jerarquiaTree3= new JerarquiaTree();
					jerarquiaTree3.idElemento=asignatura.idAsignatura;
					jerarquiaTree3.nombreElemnto=asignatura.nombreAsignatura;
					jerarquiaTree2.jerarquiaTreeList.add(jerarquiaTree3);
				} 
					jerarquiaTree1.jerarquiaTreeList.add(jerarquiaTree2);
				
							
			}*/

				jerarquiaTrees.add(jerarquiaTree1);
			
			
		
		}
		return ok(formCategorias.render(jerarquiaTrees));
	}
}
