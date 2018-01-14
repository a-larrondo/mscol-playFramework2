package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Logger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Json;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;
@Entity
@Table(name = "asignatura_seccion")
public class AsignaturaSeccion extends Model {
	@Id
	public Long id_asignaturaseccion;
	
	@Required
	public String nombre_asignatura_seccion;
	
	@ManyToOne
	@JoinColumn(name = "profesor_id", referencedColumnName = "idProfesor")
	public Profesor profesor;
	
	@ManyToOne
	@JoinColumn(name = "seccion_id", referencedColumnName = "id_seccion")
	public Seccion seccion;
	@ManyToOne
	@JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
	public Curso curso;
	
	@ManyToOne
	@JoinColumn(name = "asignatura_id", referencedColumnName = "idAsignatura")
	public Asignatura asignatura;
	
	public static ObjectNode getAsignaturasXprofesor(Long idProfesor,Long tipoPregunta){
		Logger.info("tipoPregunta="+tipoPregunta);
		//TODO:cambiar los t1 y t2 estos son los codigos que ebeans le da a asignatura t1 y curso t2 cuando crea la consulta no pude haceerlo funcionar con loss nombre de las clases   
		/*List<AsignaturaSeccion> asignaturas_seccion = Ebean.find(AsignaturaSeccion.class)
				.fetch("asignatura")
				.where()
				.eq("profesor_id", idProfesor) 
				.eq("t1.tipo_pregunta_id", tipoPregunta)
				.findList();	
		*/
		String where= " ";
		where+="profesor_id ="+idProfesor+" and tipo_pregunta_id ="+tipoPregunta+" ";
		String sql = " SELECT t0.id_asignaturaseccion, t1.idAsignatura, t1.nombre_asignatura "
			+ " FROM asignatura_seccion t0 "
			+ " LEFT OUTER JOIN asignatura t1 ON t1.idAsignatura = t0.asignatura_id "
			+ " WHERE " + where + " GROUP BY t1.nombre_asignatura";

		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("t0.id_asignaturaseccion", "id_asignaturaseccion")
				.columnMapping("t1.idAsignatura", "asignatura.idAsignatura")
				.columnMapping("t1.nombre_asignatura", "asignatura.nombreAsignatura")
				.create();
		Query<AsignaturaSeccion> query = Ebean.find(AsignaturaSeccion.class);
				query.setRawSql(rawSql);
		List<AsignaturaSeccion> asignaturas_seccion = query.findList();	
		List<Asignatura> asignatura = null;
		ObjectNode asignaturasJson = Json.newObject();
		int con = 0;
		for (AsignaturaSeccion asignatura_seccion : asignaturas_seccion) {
			ObjectNode asignaturaJson = Json.newObject();
			asignaturaJson.put("idAsignatura", asignatura_seccion.asignatura.idAsignatura);
			asignaturaJson.put("nombreAsignatura", asignatura_seccion.asignatura.nombreAsignatura);
			
			asignaturasJson.put("asignatura" + con, asignaturaJson);
			con++;
		}
			return asignaturasJson;
		}
	
		public static ObjectNode getCursosXprofesor(Long idProfesor,Long tipoPregunta) {
			Logger.info("obteniendo cursos del profesor");
			/*
			List<AsignaturaSeccion> asignaturas_seccion = Ebean.find(AsignaturaSeccion.class)
					.fetch("curso")
					.where()
					.eq("profesor_id", idProfesor) 
					.eq("t1.tipo_pregunta_id", tipoPregunta)
					.orderBy("t1.idCurso")
					.findList();
			*/
		    String where= " ";
			where+="profesor_id ="+idProfesor+" and tipo_pregunta_id ="+tipoPregunta+" ";
			String sql = " SELECT t0.id_asignaturaseccion, t1.idCurso, t1.nombre "
				+ " FROM asignatura_seccion t0 "
				+ " LEFT OUTER JOIN curso t1 ON t1.idCurso = t0.curso_id "
				+ " WHERE " + where + " GROUP BY t1.nombre ORDER BY t1.idCurso";

			RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("t0.id_asignaturaseccion", "id_asignaturaseccion")
				.columnMapping("t1.idCurso", "curso.idCurso")
				.columnMapping("t1.nombre", "curso.nombre")
				.create();
			Query<AsignaturaSeccion> query = Ebean.find(AsignaturaSeccion.class);
				query.setRawSql(rawSql);	
			List<AsignaturaSeccion> asignaturas_seccion = query.findList();			
			ObjectNode cursosJson = Json.newObject();
			List<Curso> curso = null;
			int con = 0;
			for (AsignaturaSeccion asignatura_seccion : asignaturas_seccion) {
				ObjectNode cursoJson = Json.newObject();
				cursoJson.put("idCurso", asignatura_seccion.curso.idCurso);
				cursoJson.put("nombreCurso", asignatura_seccion.curso.nombre);
				cursosJson.put("curso" + con, cursoJson);		
				con++;
			}
			return cursosJson;
		}
		
		public static List<AsignaturaSeccion> getAsignaturasXCurso(Long idProfesor,Long idcurso){
			//Logger.info("tipoPregunta="+tipoPregunta);
			//TODO:cambiar los t1 y t2 estos son los codigos que ebeans le da a asignatura t1 y curso t2 cuando crea la consulta no pude haceerlo funcionar con loss nombre de las clases   
			
			String where= " ";
			where+="profesor_id ="+idProfesor+" and curso_id ="+idcurso+" ";
			String sql = " SELECT t0.id_asignaturaseccion, t1.idAsignatura, t1.nombre_asignatura "
				+ " FROM asignatura_seccion t0 "
				+ " LEFT OUTER JOIN asignatura t1 ON t1.idAsignatura = t0.asignatura_id "
				+ " WHERE " + where + " GROUP BY t1.nombre_asignatura";

			RawSql rawSql = RawSqlBuilder
					.parse(sql)
					// map result columns to bean properties
					.columnMapping("t0.id_asignaturaseccion", "id_asignaturaseccion")
					.columnMapping("t1.idAsignatura", "asignatura.idAsignatura")
					.columnMapping("t1.nombre_asignatura", "asignatura.nombreAsignatura")
					.create();
			Query<AsignaturaSeccion> query = Ebean.find(AsignaturaSeccion.class);
					query.setRawSql(rawSql);
			List<AsignaturaSeccion> asignaturas_seccion = query.findList();	
			
				return asignaturas_seccion;
			}
		
		

}
