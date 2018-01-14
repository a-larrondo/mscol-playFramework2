package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "curso")
public class Curso extends Model {
	@Id
	@Column(name = "idCurso")
	public Long idCurso;

	@Required
	public Integer nivel;
	@Required
	public String nombre;
	@Required
	public Date aYear;
	/*
	@ManyToOne
	@JoinColumn(name = "coordinador_id", referencedColumnName = "idCoordinador")
	public Coordinador coordinador;
	
	@OneToMany
	@JoinColumn(name = "asignatura_id", referencedColumnName = "idAsignatura")
	public Asignatura asignatura;
	*/
	@OneToMany
	@JoinColumn(name = "asignaturaseccion_id", referencedColumnName = "id_asignaturaseccion")
	public AsignaturaSeccion asignaturaSeccion;
	@ManyToOne
	@JoinColumn(name="tipo_pregunta_id", referencedColumnName="id_tipoPregunta")
	public TipoPregunta tipo_pregunta;
	
	public static Model.Finder<Long, Curso> find = new Model.Finder(Long.class,Curso.class);

	public static List<Curso> listaCursosCoordinador(Long idUsuario) {
		// Integer id=Integer.parseInt(user);
		return find.where().eq("coordinador.usuario.idUsuario", idUsuario)
				.findList();
	}

	public static List<Curso> listaCursosCoordinador(String idUsuario) {
		Integer id = Integer.parseInt(idUsuario);
		return find.where().eq("coordinador.usuario.idUsuario", id).findList();
	}

	public static List<Curso> listaCursosProfesor(Long idUsuario) {
		String sql = " SELECT crs.idCurso,crs.nivel,crs.nombre "
				+ " FROM curso crs"
				+ " LEFT JOIN asignatura_seccion asgn ON crs.idCurso = asgn.curso_id LEFT JOIN profesor p ON asgn .profesor_id=p.idProfesor"
				+ " WHERE p.usuario_id =" + idUsuario + " group by idCurso ";

		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("crs.idCurso", "idCurso")
				.columnMapping("crs.nivel", "nivel")
				.columnMapping("crs.nombre", "nombre").create();
		Query<Curso> query = Ebean.find(Curso.class);
		query.setRawSql(rawSql);
		List<Curso> list = query.findList();
		return list;
		
	}

	public static List<Curso> listaCursosProfesor(String idUsuario) {
		Long id = Long.parseLong(idUsuario);
		
		return find.where().eq("asignaturaSeccion.profesor.idUsuario", id).findList();
		/*
		 * return find.where() .eq("coordinador.usuario.idUsuario", id)
		 * .findList();
		 */
	}
	
	public static List<Curso> listaCursosProfesor(Long idUsuario,Long idTipoCategoria) {
		
		String sql = " SELECT crs.idCurso,crs.nivel,crs.nombre "
				+ " FROM curso crs"
				+ " LEFT JOIN asignatura_seccion asgn ON crs.idCurso = asgn.curso_id LEFT JOIN profesor p ON asgn .profesor_id=p.idProfesor"
				+ " WHERE p.usuario_id =" + idUsuario + " AND crs.tipo_pregunta_id="+idTipoCategoria+" group by idCurso ";
		List<Curso> list = new ArrayList();
		try{
		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("crs.idCurso", "idCurso")
				.columnMapping("crs.nivel", "nivel")
				.columnMapping("crs.nombre", "nombre").create();
		Query<Curso> query = Ebean.find(Curso.class);
		query.setRawSql(rawSql);
		 list = query.findList();
		}
		catch(NullPointerException e) {
			
		}
		return list;
		
	}
}
