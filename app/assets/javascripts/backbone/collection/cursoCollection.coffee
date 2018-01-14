class AppRouter extends Backbone.Router
    initialize: ->
        console.log  "appRouter"
        @currentApp = new CursoView
            el: $("#main")            
    routes:
        "/curso/:curso/Asignaturas"   : "curso"    
    curso: (curso) ->   	
        # load project || display app
        currentApp = @currentApp
        $("#main").load "/curso/"+curso+"/Asignaturas", (tpl) ->
            currentApp.render(curso)
            

# ----------------------------------------- TASKS
class CursoView extends Backbone.View
    render: (curso) ->
        @curso = curso
        # HTML is our model
        @cursos = $.map $(".cursosdiv", @el), (folder) =>
        		console.log folder            
                el: $(folder)
                curso: @curso    

# ---------------------------------- TASKS FOLDER

# ------------------------------------- TASK ITEM


# ------------------------------------- INIT APP
$ -> # document is ready!

    app = new AppRouter()
    
    Backbone.history.start
        pushHistory: true