package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
@Entity
@Table(name = "descripcionCategoria")
public class DescripcionCategoria extends Model{
	
	@Id
	@Column(name = "idDescripcionCategoria")
	public Long idDescripcionCategoria;
	@Required 
	public String nombreAtributo;
	@Required 
	public String valorAtributo;
	

	@Required 
	public boolean habilitadoDescripcionCategoria;
	
	@ManyToOne
	@JoinColumn(name = "idCategoria", referencedColumnName = "id_categoria")
	public Categoria idCategoria;
	@ManyToOne
	@JoinColumn(name = "nombreCategoria", referencedColumnName = "nombre_categoria")
	public Categoria nombreCategoria;
	

}
