package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "perfil")
public class Perfil extends Model {
	@Id
	@Column(name = "idPerfil")
	public Long idPerfil;
	@Required
	public String descripcion;
	@ManyToMany
	public List<Usuario> miembros = new ArrayList<Usuario>();

	public static Model.Finder<String, Perfil> find = new Model.Finder(
			String.class, Perfil.class);

	public List<Perfil> getPerfiles(Long idUsuario) {
		return Perfil.find.where().eq("miembros.idUsuario", idUsuario)
				.findList();
	}
}