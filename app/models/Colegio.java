package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "colegio")
public class Colegio extends Model {
	@Id
	@Column(name = "idColegio")
	public Long idColegio;
	@Required
	public String nombre;
	public String nombre2;

	// @OneToMany(mappedBy = "colegio", fetch = FetchType.EAGER,
	// cascade=CascadeType.ALL)
	// protected List<Asignatura> asignatura;
}
