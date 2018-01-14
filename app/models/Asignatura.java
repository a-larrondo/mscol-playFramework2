package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

@Entity
@Table(name = "asignatura")
public class Asignatura extends Model {
	@Id
	@Column(name = "idAsignatura")
	public Long idAsignatura;
	@Required
	public String nombreAsignatura;
	@Required
	public Date aYear;
	
	@ManyToMany
	@JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
	public Curso curso;
	
	@OneToMany
	@JoinColumn(name = "asignaturaseccion_id", referencedColumnName = "id_asignaturaseccion")
	public AsignaturaSeccion asignatura_seccion;
	
	@ManyToOne
	@JoinColumn(name="tipo_pregunta_id", referencedColumnName="id_tipoPregunta")
	public TipoPregunta tipo_pregunta_a;
	
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="asignatura_usuario_id",
	 * referencedColumnName="idUsuario") public Usuario usuario;
	 
	
	*/
	/*@ManyToOne
	@JoinColumn(name = "profesor_id", referencedColumnName = "idProfesor")
	public Profesor profesor;
	*/
	// protected List<Curso> cursolist;

	public static Model.Finder<Long, Asignatura> find = new Model.Finder(Long.class, Asignatura.class);

	// @ManyToOne
	// @JoinColumn(name="colegio_asignatura_id",
	// referencedColumnName="idColegio")
	// public Colegio colegio;

	public static List<Asignatura> buscarXCurso(Long idCurso, String idUsuario) {
		Long idUsr = Long.parseLong(idUsuario);
		Long idProfe = Profesor.getProfesor(idUsr).idProfesor;
		List<Asignatura> asignaturas= new ArrayList();
		try{
			asignaturas= find.where().eq("curso.idCurso", idCurso)
				.eq("profesor.idProfesor", idProfe).findList();
		}
		catch(NullPointerException e) {
			
		}
		return asignaturas;
	}

	public static List<Asignatura> buscarXCurso(Long idCurso) {
		List<Asignatura> asignaturas= new ArrayList();
		List<Asignatura> asignatura=find.where().eq("curso.idCurso", idCurso).findList();
		if(asignatura.isEmpty()) {
			
		}
		return asignaturas;
	}

	public static List<Curso> getCursos(Long idUsuario) {
		List<Curso> curso = Ebean.find(Curso.class)
				.fetch("asignatura", "curso").where()
				.eq("asignatura.usuario.idUsuario", idUsuario).findList();
		return curso;
	}
	

	/*
	 * TODO Borrar este metodo antes de finalizar el proyecto (ejemplo map
	 * asignaturas)
	 */
	/*
	 * ejemplo consulta que devuelve un map public static Map
	 * buscarXProfesor(Long idProfesor) {
	 * 
	 * Map<String, Asignatura> map = Ebean.find(Asignatura.class)
	 * .select("idAsignatura, nombreAsignatura") .where()
	 * .eq("profesor.idProfesor", idProfesor) .findMap("nombreAsignatura",
	 * String.class);
	 * 
	 * return map; }
	 */

	public static List<Asignatura> buscarXProfesor(Long idProfesor) {

		String sql = "SELECT aa.idAsignatura, aa.nombre_asignatura "
				+ " FROM asignatura_seccion ascc INNER JOIN asignatura aa "
				+ " ON aa.idAsignatura=ascc.asignatura_id "
				+ " WHERE ascc.profesor_id= " + idProfesor;

		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("aa.idAsignatura", "idAsignatura")
				.columnMapping("aa.nombre_asignatura", "nombreAsignatura")
				.create();
		Query<Asignatura> query = Ebean.find(Asignatura.class);
		query.setRawSql(rawSql);
		List<Asignatura> list=query.findList();
		return list;

	}

}
