package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name = "alumno")
public class Alumno extends Model {
	@Id
	@Column(name = "idAlumno")
	public Long idAlumno;
	

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
	public Usuario usuario;
	/*
	@ManyToOne
	@JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
	public Curso curso;
	*/
	@ManyToOne
	@JoinColumn(name = "seccion_id", referencedColumnName = "id_seccion")
	public Seccion seccion;

	public static Model.Finder<Long, Alumno> findId = new Model.Finder(
			Long.class, Alumno.class);

	public static List<Alumno> getAlumnosXcurso(Long idCurso) {

		return findId.where()
				.eq("curso.idCurso", idCurso).findList();
	}

}
