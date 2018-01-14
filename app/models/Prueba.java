package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name = "prueba")
public class Prueba extends Model {
	@Id
	@Column(name = "idPrueba")
	public Long idPrueba;
	@Required
	public Date fechaCreacion;
	@Required
	public String tituloPrueba;
	public String Descripcion;
	@ManyToOne
	@JoinColumn(name = "profesor_id", referencedColumnName = "idProfesor")
	public Profesor profesor;
	@ManyToMany
	public List<Pregunta> preguntas = new ArrayList<Pregunta>();

	public Prueba() {
		this.fechaCreacion = new Date();
	}

	public static Model.Finder<String, Prueba> find = new Model.Finder(
			String.class, Prueba.class);
	public static Model.Finder<Long, Prueba> findId = new Model.Finder(
			Long.class, Prueba.class);
	
	public static Prueba crearPrueba(Prueba prueba, String idUsuario) {
		prueba.profesor = Profesor.find.where()
				.eq("usuario.idUsuario", idUsuario).findUnique();
		prueba.save();
		prueba.saveManyToManyAssociations("preguntas");
		return prueba;

	}

	public static Prueba  getPrueba(Long idPrueba) {
		// TODO Auto-generated method stub
		Prueba prueba = Prueba.find.fetch("preguntas").where()
				.eq("idPrueba",idPrueba).findUnique();
		return prueba;
		
	}
	
	public static List<Prueba> getPruebas(Long IDProfesor) {
		// TODO Auto-generated method stub
		List<Prueba> pruebas = Prueba.find.where()
				.eq("idPrueba",IDProfesor).findList();
		return pruebas;
		
	}
	
	public void setProfesor (Profesor profesor){
		this.profesor=profesor;
	}
	public void setPreguntas(List<Pregunta> preguntas){
		this.preguntas=preguntas;
	}

}
