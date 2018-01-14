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
@Table(name = "coordinador")
public class Coordinador extends Model {

	@Id
	@Column(name = "idCoordinador")
	public Long idCoordinador;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
	public Usuario usuario;

	public static Model.Finder<String, Coordinador> find = new Model.Finder(
			String.class, Coordinador.class);
	public static Model.Finder<Long, Coordinador> findId = new Model.Finder(
			Long.class, Coordinador.class);

	public static List<Profesor> obtenerProfesores(Long idCoordinador) {
		List<Profesor> profesAcargo = Profesor.find.fetch("usuario")
				.fetch("usuario", "nombre,apellido").where()
				.eq("profesor_coordinador_id", idCoordinador).findList();
		return profesAcargo;
	}
}
