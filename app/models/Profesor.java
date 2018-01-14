package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;
import utils.Constantes;

@Entity
@Table(name = "profesor")
public class Profesor extends Model {
	@Id
	@Column(name = "idProfesor")
	public Long idProfesor;
	public Date fechaInicio;
	public Date fechaExpiracion;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario")
	public Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "coordinador_id", referencedColumnName = "idCoordinador")
	public Coordinador coordinador;

	public static Model.Finder<String, Profesor> find = new Model.Finder(
			String.class, Profesor.class);

	public boolean verificarProfesor(Long idUsuario) {
		return Perfil.find.where().eq("usuario.idUsuario", idUsuario)
				.eq("perfil.idPerfil", Constantes.PROFESOR).findRowCount() > 0;
	}

	public static Profesor crearProfesor(Long idUsuario, Long idCordinador,
			Profesor profesor) {
		profesor.usuario = Usuario.findId.ref(idUsuario);
		profesor.coordinador = Coordinador.findId.ref(idCordinador);
		profesor.save();
		return profesor;
	}

	public static Profesor getProfesor(Long idUsuario) {
		return Profesor.find.where().eq("usuario.idUsuario", idUsuario)
				.findUnique();
	}
	
	

}
