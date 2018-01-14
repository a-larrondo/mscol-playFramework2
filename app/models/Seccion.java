package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "seccion")
public class Seccion extends Model {
	@Id
	@Column(name="id_seccion")
	public Long id_seccion;
	
	@Required
	public String nombre_seccion;
	
	@ManyToOne
	@JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
	public Curso curso;
}
