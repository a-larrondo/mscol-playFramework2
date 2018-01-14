package models;

import play.db.ebean.Model;

import com.avaje.ebean.annotation.EnumValue;

public class Habilidades extends Model {

	public enum nombreHabilidadesEnum {
		@EnumValue("evaluar")
		EVALUAR, @EnumValue("sintetizar")
		SINTETIZAR, @EnumValue("analizar")
		ANALIZAR, @EnumValue("aplicar")
		APLICAR, @EnumValue("comprender")
		COMPRENDER, @EnumValue("conocer")
		CONOCER,

	}
}
