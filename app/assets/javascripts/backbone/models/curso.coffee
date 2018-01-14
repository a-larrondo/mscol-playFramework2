$ ->
class CursoModel extends Backbone.Model
    defaults:
    	idCurso: 0
    	nivel: 0
    	seccion: 0
    	nombre: ''
    	a_year: ''
    	coordinador_id: ''
    	
    initialize: ->
    	if !@get("nombre")
    		@set({ "nombre": @defaults.content })
    		
    clean: ->
    	@destroy()
    	
    	
class CursoCollection extends Backbone.Collection
    model: CursoModel
    url: "/todos"
    comparator: (todo) ->
      todo.get("id")  
      
      
  class AppRouter extends Backbone.Router
    initialize: ->
        @currentApp = new CursoView
            el: $("#main")
    routes:
        "/:curso/Asignaturas"   : "tasks"    
    tasks: (curso) ->
        # load project || display app
        currentApp = @currentApp
        $("#main").load "/cursos/" + curso + "/ramos", (tpl) ->
            currentApp.render(curso)    
            
                  
class CursoView extends Backbone.View
	el: $("#main")
    render: (curso) ->
  
        @curso = curso
        # HTML is our model
        @folders = $.map $(".folder", @el), (folder) =>
            new TaskFolder
                el: $(folder)
                curso: @curso
                    
      
window.Todos = new CursoCollection
window.App = new CursoView        				