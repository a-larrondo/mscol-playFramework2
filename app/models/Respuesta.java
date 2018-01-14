package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "respuesta")
public class Respuesta extends Model {
	@Id
	@Column(name = "idRespuesta")
	public Long idRespuesta;
	@Required
	public String nombre;
	@ManyToOne
	@JoinColumn(name = "prueba_id", referencedColumnName = "idPrueba")
	public Prueba prueba;
	//@ManyToOne
	@OneToOne
	@JoinColumn(name = "respuesta_alternativas_id", referencedColumnName = "idAlternativas")
	public List<Alternativa> alternativa;
	@ManyToOne
	@JoinColumn(name = "alumno_id", referencedColumnName = "idAlumno")
	public Alumno alumno;
}
