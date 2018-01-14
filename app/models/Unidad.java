package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "unidad")
public class Unidad extends Model {
	@Id
	@Column(name = "idUnidad")
	public Long idUnidad;
	@Required
	public String nombreUnidad;
	public Date aYearUnidad;
	// @OneToMany(mappedBy = "unidad", fetch = FetchType.EAGER,
	// cascade=CascadeType.ALL)
	// protected List<Pregunta> pregunta;
	public static Model.Finder<Long, Unidad> findId = new Model.Finder(
			Long.class, Unidad.class);

	@ManyToOne
	@JoinColumn(name = "asignatura_id", referencedColumnName = "idAsignatura")
	public Asignatura asignatura;

	public static List<Unidad> getUnidades(Long idAsignatura) {
		return findId.where().eq("asignatura.idAsignatura", idAsignatura)
				.findList();

	}

	public static List<Unidad> buscarXAsignatura(String idAsignatura) {
		Long idAsignaturaLong = Long.parseLong(idAsignatura);
		return findId.where().eq("asignatura.idAsignatura", idAsignaturaLong)
				.findList();

	}
}
