package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
//@Entity
//@Table(name = "RespuestasPregunta")
public class RespuestasPregunta extends Model {
	@Id
	@Column(name = "idRespuestasPreguntas")
	public Long idRespuestasPreguntas;
	@Required
	public boolean esCorrecta;
	@ManyToOne
	@JoinColumn(name = "tomaPrueba_id", referencedColumnName = "idTomaPrueba")
	public TomaPrueba tomaPrueba;
	@ManyToOne
	@JoinColumn(name = "pregunta_id", referencedColumnName = "idPregunta")
	public Pregunta pregunta;
	@ManyToOne
	@JoinColumn(name = "alternativa_id", referencedColumnName = "idAlternativa")
	public Alternativa alternativa;

}
