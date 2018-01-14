package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Transactional;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

@Entity
@Table(name = "pregunta")
public class Pregunta extends Model {
	@Id
	@Column(name = "idPregunta")
	public Long idPregunta;
	@Required
	@Lob
	public String pregunta;
	@Required
	public String justificacion; // que se quiere lograr con la pregunta
	public String Comentario; // informacion adicional de la pregunta
	@Required
	public String desarrollo; // como se llega a la solucion de la pregunta
								// planteada
	@Required
	boolean es_imagen;
	/*
	 * @Required public String nivel_discriminacio;
	 * 
	 * @Required public Long nivel_dificultad;
	 */
	public Date aYearPregunta;
	@ManyToOne
	@JoinColumn(name = "curso_id", referencedColumnName = "idCurso")
	public Curso curso;
	@ManyToOne
	@JoinColumn(name = "asignatura_id", referencedColumnName = "idAsignatura")
	public Asignatura asignatura;
	@ManyToOne
	@JoinColumn(name = "pregunta_usuario_id", referencedColumnName = "idUsuario")
	public Usuario usuario;
	@ManyToMany
	public List<Categoria> categoriasPregunta = new ArrayList<Categoria>(); 
	@OneToMany
	@Valid
	public  List<Alternativa> alternativas = new ArrayList();

	public Pregunta() {
		this.aYearPregunta = new Date();
		this.es_imagen=false;
	}

	Pregunta(String pregunta, String justificacion, String desarrollo,
			Usuario usuario, Alternativa... alternativas) {
		this.aYearPregunta = new Date();
		this.pregunta = pregunta;
		this.justificacion = justificacion;
		this.desarrollo = desarrollo;
		this.usuario = usuario;
		this.alternativas = new ArrayList<Alternativa>();
		for (Alternativa alternativa : alternativas) {
			this.alternativas.add(alternativa);
		}
	}

	// TODO crear clase nivel asosiar persona con pregunta
/* */
	@ManyToOne
	@JoinColumn(name = "unidad_id", referencedColumnName = "idUnidad")
	public Unidad unidad;
/* */
	public static Model.Finder<String, Pregunta> find = new Model.Finder(
			String.class, Pregunta.class);
	public static Model.Finder<Long, Pregunta> findId = new Model.Finder(
			Long.class, Pregunta.class);

	public static List<Pregunta> getPreguntas(Long idUnidad) {
		return findId.where().eq("unidad.idUnidad", idUnidad).findList();
	}

	public static List<Pregunta> getPreguntasUsuario(Long idUsuario) {
		return findId.where().eq("usuario.idUsuario", idUsuario).findList();
	}

	public static List<Pregunta> getPreguntasUnidad(Long idUsuario,
			Long idUnidad) {
		return findId.where().eq("unidad.idUnidad", idUnidad)
				.eq("usuario.idUsuario", idUsuario).findList();
	}
	public static Page<Pregunta> getPreguntasCategorias(Map<Long,Long> categorias,int pagina) {
		String where= " ";
		Long val=(long) 0;
		int pageSize=5;
		for (Long idCategoria : categorias.keySet()) {
			val=idCategoria;
			where+="categoria_id_categoria ="+idCategoria+" or ";
		
		}
		where+="categoria_id_categoria ="+val+" ";
		
		String sql = " SELECT pre.idPregunta,pre.es_imagen,pre.pregunta "
				+ " FROM pregunta pre "
				+ " LEFT JOIN pregunta_categoria pcat ON pre.idPregunta=pcat.pregunta_idPregunta  LEFT JOIN alternativa alt ON pre.idPregunta = alt.pregunta_idPregunta "
				+ " WHERE " + where + " GROUP BY pregunta";

		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("pre.idPregunta", "idPregunta")
				.columnMapping("pre.es_imagen", "es_imagen")
				.columnMapping("pre.pregunta", "pregunta")
				.create();
				/*.columnMapping(alternativas)
				.columnMapping("alt.alternativa_txt", "pregunta.alternativa.alternativa_txt")
				.columnMapping("alt.es_correcta", "pregunta.alternativa.es_correcta")
				.columnMapping("alt.es_imagen", "pregunta.alternativa.es_imagen")
				*/
				
				
		Query<Pregunta> query = Ebean.find(Pregunta.class);
		query.setRawSql(rawSql);
		PagingList<Pregunta> pagingList  = query.findPagingList(pageSize);
		pagingList.getFutureRowCount(); 
		Page<Pregunta> page = pagingList.getPage(pagina);  
		//List<Pregunta> list= page.getList();  
		 return page; 
	}

	@Transactional
	public static Pregunta crearPregunta(Pregunta preguntaO, String idUsuario) {
		// Ebean.beginTransaction();
		// try {
		Usuario usuario = new Usuario();
		usuario.idUsuario = Long.parseLong(idUsuario);

		List<Alternativa> alternativas = preguntaO.alternativas;
		preguntaO.usuario = usuario;

		preguntaO.save();
		// List<Alternativa> alternativas = preguntaO.alternativas;
		for (Alternativa alternativa : alternativas) {
			alternativa.pregunta = preguntaO;
			alternativa.save();
		}
		// }
		// finally {
		// Ebean.endTransaction();
		return preguntaO;
		// }
	}
	public static List<Pregunta> getPreguntasXprueba(Long idPrueba){
		String where="pp.prueba_idPrueba="+idPrueba;
		String sql = " SELECT pre.idPregunta,pre.es_imagen,pre.pregunta "
				+ " FROM pregunta pre "
				+ " LEFT JOIN prueba_pregunta pp ON pre.idPregunta=pp.pregunta_idPregunta  LEFT JOIN alternativa alt ON pre.idPregunta = alt.pregunta_idPregunta "
				+ " WHERE " + where + " GROUP BY pregunta";
		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("pre.idPregunta", "idPregunta")
				.columnMapping("pre.es_imagen", "es_imagen")
				.columnMapping("pre.pregunta", "pregunta")
				.create();
		Query<Pregunta> query = Ebean.find(Pregunta.class);
		query.setRawSql(rawSql);
		List<Pregunta> preguntas  = query.findList(); 
		
		return preguntas;
	}
	public void setidPregunta(Long idPregunta){
		this.idPregunta = idPregunta;		
	}
	public static Page<Pregunta> getPreguntasCategorias(Map<Long,Long> categorias,int pagina,int pageSize) {
		String where= " ";
		Long val=(long) 0;
		//int pageSize=5;
		for (Long idCategoria : categorias.keySet()) {
			val=idCategoria;
			where+="categoria_id_categoria ="+idCategoria+" or ";
		
		}
		where+="categoria_id_categoria ="+val+" ";
		
		String sql = " SELECT pre.idPregunta,pre.es_imagen,pre.pregunta "
				+ " FROM pregunta pre "
				+ " LEFT JOIN pregunta_categoria pcat ON pre.idPregunta=pcat.pregunta_idPregunta  LEFT JOIN alternativa alt ON pre.idPregunta = alt.pregunta_idPregunta "
				+ " WHERE " + where + " GROUP BY pregunta";

		RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("pre.idPregunta", "idPregunta")
				.columnMapping("pre.es_imagen", "es_imagen")
				.columnMapping("pre.pregunta", "pregunta")
				.create();
				/*.columnMapping(alternativas)
				.columnMapping("alt.alternativa_txt", "pregunta.alternativa.alternativa_txt")
				.columnMapping("alt.es_correcta", "pregunta.alternativa.es_correcta")
				.columnMapping("alt.es_imagen", "pregunta.alternativa.es_imagen")
				*/
				
				
		Query<Pregunta> query = Ebean.find(Pregunta.class);
		query.setRawSql(rawSql);
		PagingList<Pregunta> pagingList  = query.findPagingList(pageSize);
		pagingList.getFutureRowCount(); 
		Page<Pregunta> page = pagingList.getPage(pagina);  
		//List<Pregunta> list= page.getList();  
		 return page; 
	}

}