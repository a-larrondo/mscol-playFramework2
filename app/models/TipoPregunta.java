package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Json;

@Entity
@Table(name = "tipo_Pregunta")
public class TipoPregunta extends Model {
	@Id
	@Column(name = "id_tipoPregunta")
	public Long idTipoPregunta;
	@Required
	@Column(name = "nombreTipo")
	public String nombreTipo;
	@Column(name = "tipoPreguntaHabilitado")
	public boolean tipoPreguntaHabilitado;
	
	public static Model.Finder<Long, TipoPregunta> findId = new Model.Finder(
			Long.class, TipoPregunta.class);
	
	
	public static List<TipoPregunta> getTipoPreguntas(){
		
		return findId.where().eq("tipoPreguntaHabilitado", true)
		.findList();
	}
	
	public static List<TipoPregunta> getcategoriasTipoPreguntas() {
		
		   String sql = " SELECT t0.id_tipoPregunta, t0.nombreTipo"
				+ " FROM tipo_pregunta t0"
				+ " LEFT OUTER JOIN asignatura t1 ON t1.tipo_pregunta_id = t0.id_tipoPregunta LEFT OUTER JOIN curso t2 ON t1.tipo_pregunta_id = t0.id_tipoPregunta "
				+ " where t1.tipo_pregunta_id  IS NOT NULL group by t0.id_tipoPregunta";

			RawSql rawSql = RawSqlBuilder
				.parse(sql)
				// map result columns to bean properties
				.columnMapping("t0.id_tipoPregunta", "idTipoPregunta")
				.columnMapping("t0.nombreTipo", "nombreTipo")
				.create();
			Query<TipoPregunta> query = Ebean.find(TipoPregunta.class);
				query.setRawSql(rawSql);	
			List<TipoPregunta> tipoPregunta = query.findList();			
			
			return tipoPregunta;
		}
}
