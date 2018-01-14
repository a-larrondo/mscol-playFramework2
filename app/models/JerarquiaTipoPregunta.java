package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.Logger;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
@Entity
@Table(name="jerarquiaTipoPregunta")
public class JerarquiaTipoPregunta extends Model {
	@Id
	@Column(name = "id_jerarquia")
	public Long id_jerarquia;

	@Required
	@Column(name = "nombre_jerarquia")
	public String nombreJerarquia;
	@Required
	public boolean jerarquia_habilitada;
	@Required
	public Long numero_jerarquia;
	
	@Required
	public boolean seleccion_unica;
	
 JerarquiaTipoPregunta(){
	 seleccion_unica=true;
}
@ManyToOne
@JoinColumn(name = "id_clasificacion_Superior", referencedColumnName = "id_jerarquia")
public JerarquiaTipoPregunta idClasificacionSuperior;

@ManyToOne
@JoinColumn(name = "nombre_clasificacion_Superior", referencedColumnName = "nombre_jerarquia")
public JerarquiaTipoPregunta nombreClasificacion_Superior;
@ManyToOne
@JoinColumn(name = "id_clasificacion_Anterior", referencedColumnName = "id_jerarquia")
public JerarquiaTipoPregunta idClasificacionAnterior;

@ManyToOne
@JoinColumn(name = "tipo_Pregunta_id", referencedColumnName = "id_tipoPregunta")
public TipoPregunta tipo_Preguntas;

public static Model.Finder<Long, Categoria> findId = new Model.Finder(
		Long.class, Categoria.class);

public static List<JerarquiaTipoPregunta> getJegarquia(Long IdTipo){
	Logger.info("JerarquiaTipoPregunta IdTipo="+IdTipo);
	String sql	= " select j.id_jerarquia, j.nombre_jerarquia,j.id_clasificacion_Superior, j.seleccion_unica, j.numero_jerarquia"
	+ " from jerarquiaTipoPregunta j"
	+ " group by j.id_jerarquia ";
	RawSql rawSql =	RawSqlBuilder
	.parse(sql)
	// map result columns to bean properties
	.columnMapping("j.id_jerarquia", "id_jerarquia")
	.columnMapping("j.nombre_jerarquia", "nombreJerarquia")
	.columnMapping("j.id_clasificacion_Superior", "idClasificacionSuperior.id_jerarquia")
	.columnMapping("j.seleccion_unica", "seleccion_unica")
	.columnMapping("j.numero_jerarquia", "numero_jerarquia")
	.create();
	Query<JerarquiaTipoPregunta> query = Ebean.find(JerarquiaTipoPregunta.class);
	query.setRawSql(rawSql)
	// with “parsed” SQL we can add expressions to the
	// where and having clauses etc
	.where().eq("tipo_Pregunta_id", IdTipo);
	 List<JerarquiaTipoPregunta> listJerarquiaTipoPregunta = query.findList();
	return listJerarquiaTipoPregunta;
}

}

