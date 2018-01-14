package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
@Table(name = "categoria")
public class Categoria extends Model {
	@Id
	@Column(name = "id_categoria")
	public Long id_categoria;

	@Required
	@Column(name = "nombre_categoria")
	public String nombre_categoria;
	
	public String descripcion;
	@Required
	public boolean categoriaHabilitada;
	@Transient
	public long cantidad;
	
	@ManyToOne
	@JoinColumn(name = "tipo_Pregunta_id", referencedColumnName = "id_tipoPregunta")
	public TipoPregunta tipo_Pregunta;
	
	
	
/*
@ManyToOne
@JoinColumn(name = "id_Categoria_Superior", referencedColumnName = "id_categoria")
public Categoria idCategoriaSuperior;

@ManyToOne
@JoinColumn(name = "nombreCategoria_Superior", referencedColumnName = "nombreCategoria")
public Categoria nombreCategoria_Superior;

@ManyToOne
@JoinColumn(name = "id_Categoria_Anterior", referencedColumnName = "id_categoria")
public Categoria idCategoriaAnterior;



@ManyToOne
@JoinColumn(name = "jerarquia_id", referencedColumnName = "id_jerarquia")
public JerarquiaTipoPregunta jerarquiaTipoPregunta;
*/
public static Model.Finder<Long, Categoria> findId = new Model.Finder(
		Long.class, Categoria.class);
/*
public static List<Categoria> getCategoriasXprueba(Long idPrueba){
	String where="id_categoria="+idPrueba;
	String sql = " select ca.id_categoria, ca.nombre_categoria"
			+ " FROM categoria ca "
			+ " WHERE " + where + " ";
	RawSql rawSql = RawSqlBuilder.parse(sql)
			// map result columns to bean properties
			.columnMapping("ca.id_categoria", "id_categoria")
			.columnMapping("ca.nombre_categoria", "nombre_categoria")
			.create();
	Query<Categoria> query = Ebean.find(Categoria.class);
	query.setRawSql(rawSql);
	List<Categoria> categorias  = query.findList(); 
	
	return categorias;
}*/
public static List<Categoria> getCategoriasXprueba(Long idPrueba){
		String where="prueba_pregunta.prueba_idPrueba="+idPrueba;
		String sql = " select categoria.id_categoria, COUNT(categoria.id_categoria) , categoria.nombre_categoria"
				+ " FROM categoria "
				+ " INNER JOIN pregunta_categoria ON categoria.id_categoria = pregunta_categoria.categoria_id_categoria INNER JOIN prueba_pregunta ON prueba_pregunta.pregunta_idPregunta = pregunta_categoria.pregunta_idPregunta"
				+ " WHERE " + where + " GROUP BY categoria.nombre_categoria";
		RawSql rawSql = RawSqlBuilder.parse(sql)
				// map result columns to bean properties
				.columnMapping("categoria.id_categoria", "id_categoria")
				.columnMapping("COUNT(categoria.id_categoria)", "cantidad")
				.columnMapping("categoria.nombre_categoria", "nombre_categoria")
				.create();
		Query<Categoria> query = Ebean.find(Categoria.class);
		query.setRawSql(rawSql);
		List<Categoria> categorias  = query.findList(); 
	return categorias;
}
}


