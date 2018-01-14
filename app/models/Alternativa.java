package models;

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
@Table(name = "alternativa")
public class Alternativa extends Model {
	@Id
	@Column(name = "id_alternativa")
	public Long idAlternativa;
	@Required
	public String alternativaTxt;
	@Required
	public boolean es_correcta;
	@Required
	public boolean es_imagen;
	@ManyToOne
	@JoinColumn(name = "pregunta_idPregunta", referencedColumnName = "idPregunta")
	public Pregunta pregunta;

	public static Model.Finder<Long, Alternativa> findId = new Model.Finder(
			Long.class, Alternativa.class);
	
	Alternativa() {
		this.es_correcta = false;
		this.es_imagen=false;
	}

	Alternativa(Long idAlternativa, String alternativaTxt, boolean esCorrecta,
			Pregunta pregunta) {
		this.idAlternativa = idAlternativa;
		this.alternativaTxt = alternativaTxt;
		this.es_correcta = esCorrecta;
		this.pregunta = pregunta;
	}
	
	public static List<Alternativa> getAlternativaXpregunta(Long idPregunta) {

		return findId.where()
				.eq("pregunta.idPregunta", idPregunta).findList();
	}
}
