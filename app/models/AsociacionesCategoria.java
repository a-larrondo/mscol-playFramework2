package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Logger;
import play.db.ebean.Model;
import play.libs.Json;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Entity
@Table(name="asociacionesCategoria")
public class AsociacionesCategoria extends Model{
	@Id
	@Column(name="idAsociacion")
	public Long idAsociacion;
	
	public String codigo;
	
	@ManyToOne
	@JoinColumn(name="curso_id", referencedColumnName="idCurso")
	public Curso curso;
	
	@ManyToOne
	@JoinColumn(name="asignatura_id", referencedColumnName="idAsignatura")
	public Asignatura asignatura;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria_superior", referencedColumnName = "id_categoria")
	public Categoria id_categoria_superior;
	
	@ManyToOne
	@JoinColumn(name="categoria_id", referencedColumnName="id_categoria")
	public Categoria categoria_id;
	
	@ManyToOne
	@JoinColumn(name="tipo_pregunta_id", referencedColumnName="id_tipoPregunta")
	public TipoPregunta tipo_pregunta_id;
	/*
	@ManyToOne
	@JoinColumn(name="pregunta_id", referencedColumnName="idPregunta")
	public Pregunta preguntaId;
	*/
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "nombre_categoria_superior", referencedColumnName = "nombre_categoria")
	public Categoria nombre_categoria_superior;
	
	@ManyToOne
	@JoinColumn(name = "id_categoria_anterior", referencedColumnName = "id_categoria")
	public Categoria id_categoria_anterior;
	
	@ManyToOne
	@JoinColumn(name = "asociacione_categoria_id_jerarquia", referencedColumnName = "id_jerarquia")
	public JerarquiaTipoPregunta jerarquiaTipoPregunta;
	
	
	public static List<AsociacionesCategoria> getCategorias(Long idAsignatura,Long idCurso,Long idJerarquia){
		/*consulta sql ejemplo obtener categorieas
		 * SELECT c.id_categoria, c.nombre_categoria FROM `asociacionescategoria`a inner join categoria c on a.categoria_id= 	id_categoria  WHERE a.curso_id=1 and asignatura_id=2 and id_jerarquia=2
		 */
		//Logger.info("AsociacionesCategoria getCategorias ****  idAsignatura="+idAsignatura+" idCurso="+idCurso+" idJerarquia="+idJerarquia);
		ObjectNode categoria = Json.newObject();
		//Logger.info("obteniendo cursos del profesor");
		List<AsociacionesCategoria> asociacionesCategoria = Ebean.find(AsociacionesCategoria.class)
				.fetch("jerarquiaTipoPregunta","id_jerarquia")
				.fetch("id_categoria_superior")
				.fetch("categoria_id")
				.fetch("nombre_categoria_superior")
				.where()
					.eq("asignatura_id", idAsignatura)
					.eq("curso_id", idCurso)
					.eq("asociacione_categoria_id_jerarquia", idJerarquia)
				 .orderBy("id_categoria_superior,id_categoria_superior,idAsociacion  asc")
				.findList();		
		return asociacionesCategoria;
		
	}
	public static List<AsociacionesCategoria> getCategoriasfiltradas(Long idCurso,Long idAsignatura,Long idJerarquiaSuperior,Long idJerarquia){
		/*consulta sql ejemplo obtener categorieas
		 * SELECT c.id_categoria, c.nombre_categoria FROM `asociacionescategoria`a inner join categoria c on a.categoria_id= 	id_categoria  WHERE a.curso_id=1 and asignatura_id=2 and id_jerarquia=2
		 */
		//TODO: cambiar el t0 en el idtipo pregunta
		Logger.info("AsociacionesCategoria getCategorias ****  idCurso="+idCurso+" idJerarquia="+idJerarquia+" idJerarquiaSuperior="+idJerarquiaSuperior);
		ObjectNode categoria = Json.newObject();
		Logger.info("obteniendo cursos del profesor");
		List<AsociacionesCategoria> asociacionesCategoria = Ebean.find(AsociacionesCategoria.class)
				.fetch("jerarquiaTipoPregunta","id_jerarquia")
				.fetch("categoria_id","id_categoria")
				.fetch("categoria_id","nombre_categoria")
				.fetch("nombre_categoria_superior")	
				.fetch("id_categoria_superior","numero_jerarquia")
				.where()
				.eq("asignatura_id", idAsignatura)
				.eq("curso_id", idCurso)
				.eq("asociacione_categoria_id_jerarquia", idJerarquia)
				.eq("id_categoria_superior.id_categoria", idJerarquiaSuperior)
				.findList();	
		
		return asociacionesCategoria;
		
	}

} 

