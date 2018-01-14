import java.util.List;
import java.util.Map;

import models.Usuario;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		InitialData.insert(app);
	}

	static class InitialData {

		public static void insert(Application app) {
			if (Ebean.find(Usuario.class).findRowCount() == 0) {

				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml
						.load("initial-data.yml");

				Ebean.save(all.get("Usuarios"));

				Ebean.save(all.get("Perfiles"));
				for (Object Perfil : all.get("Perfiles")) {
					// Insert the project/user relation
					Ebean.saveManyToManyAssociations(Perfil, "miembros");
				}
				Ebean.save(all.get("Coordinadores"));

				Ebean.save(all.get("Cursos"));

				Ebean.save(all.get("Profesores"));

				Ebean.save(all.get("Asignaturas"));
				
				Ebean.save(all.get("Secciones"));
				
				Ebean.save(all.get("AsignaturaSecciones"));
				
				Ebean.save(all.get("TiposPreguntas"));

				Ebean.save(all.get("Unidades"));
			
				Ebean.save(all.get("Categorias"));
				
				Ebean.save(all.get("Preguntas"));

				Ebean.save(all.get("Alternativas"));
				
				Ebean.save(all.get("Alumnos"));
								
				Ebean.save(all.get("JerarquiaTipoPreguntas"));
				
				//Ebean.save(all.get("DescripcionCategoria"));
				
				Ebean.save(all.get("AsociacionesCategorias"));
				
								
			}
		}

	}

}