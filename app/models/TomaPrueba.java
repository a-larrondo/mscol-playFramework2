package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
@Table(name = "toma_prueba")
public class TomaPrueba extends Model {

	@Id
	@Column(name = "idTomaPrueba")
	public Long idTomaPrueba;
	@Required
	public boolean contestada;
	@Required
	public Timestamp inicioPrueba;
	@Required
	public Timestamp terminoPrueba;
	@ManyToOne
	@JoinColumn(name = "alumno_id", referencedColumnName = "idAlumno")
	public Alumno alumno;

	@ManyToOne
	@JoinColumn(name = "prueba_id", referencedColumnName = "idPrueba")
	public Prueba prueba;
}
