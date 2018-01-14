package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "usuario")
public class Usuario extends Model {
	@Id
	@Column(name = "idUsuario")
	public Long idUsuario;

	@Column(unique = true)
	@Required
	public String mail;
	@Required
	public Integer rut;
	@Required
	public String nombre;
	@Required
	public String apellido;
	@Required
	public char genero;
	@Required
	public String clave;

	public boolean habilitado = true;
	 //public boolean habilitado1 =true;

	@ManyToMany(cascade = CascadeType.ALL)
	public List<Perfil> perfil;
	/*
	 * @ManyToMany
	 * 
	 * @NotNull
	 * 
	 * @JoinColumn(name="usuario_perfil_id", referencedColumnName="idPerfil")
	 * public Perfil perfil;
	 */

	public static Model.Finder<String, Usuario> find = new Model.Finder(
			String.class, Usuario.class);
	public static Model.Finder<Long, Usuario> findId = new Model.Finder(
			Long.class, Usuario.class);

	public static boolean authenticate(String email, String clave) {
		Usuario usrValido = Usuario.find.where().eq("mail", email)
				.eq("clave", clave).findUnique();
		if (usrValido == null) {
			return false;
		} else {
			/*
			 * usrValido.perfil=Perfil.find.where().eq("miembros.mail", email)
			 * .findList();
			 */
			return true;
		}
	}

	public static Usuario getUsuario(String email) {
		Usuario user = Usuario.find.fetch("perfil").where().eq("mail", email)
				.findUnique();
		if (user == null) {
			return user;
		} else {
			user.perfil = Perfil.find.where().eq("miembros.mail", email)
					.findList();
			return user;
		}

	}
}
