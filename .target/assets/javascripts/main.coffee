class AppRouter extends Backbone.Router
    initialize: ->
        console.log  "appRouter"
        @currentApp = new CursoView
            el: $("#main")            
    routes:
        "curso/:curso/Asignaturas"   : "curso"
        ":curso/Unidad?:asignatuta"  : "asignatura" 
        "Pregunta?:idUnidad"		 : "preguntas"
        "Alumnos?:idCurso"		 	 : "alumnos"	
    curso: (curso) ->   	
        # load project || display app
        currentApp = @currentApp
        $("#main").load "/curso/" + curso + "/Asignaturas", (tpl) ->
            currentApp.render(curso)
    asignatura: (curso,asignatura) ->     	
    	 # load project || display app
        currentApp = @currentApp
        $("#main").load "/" + curso + "/Unidad?"+asignatura, (tpl) ->
            currentApp.render(curso)
    preguntas: (idUnidad) ->
     	currentApp =@currentApp
     	$("#main").load "/Pregunta?"+idUnidad, (tpl) ->
            currentApp.render(idUnidad)
    alumnos: (idCurso) ->
     	currentApp =@currentApp
     	$("#main").load "/Alumnos?"+idCurso, (tpl) ->
            currentApp.render(idCurso)        
     	        
            

# ----------------------------------------- TASKS
class CursoView extends Backbone.View
    render: (curso) ->
        @curso = curso
        # HTML is our model
        @cursos = $.map $("#cursosdiv", @el), (folder) =>
        		console.log "carpeta="+folder            
                el: $(folder)
                curso: @curso    

class AsignaturaView extends Backbone.View
    render: (curso) ->
        @curso = curso
        # HTML is our model
        @cursos = $.map $("#asignaturasdiv", @el), (folder) =>
                console.log "carpeta="+folder            
                el: $(folder)
                curso: @curso 

$ -> 
    app = new AppRouter()
        
    Backbone.history.start
        pushHistory: true