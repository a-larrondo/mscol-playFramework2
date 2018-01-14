$ ->

$(document).on "click", "#tipoPreguntas a", -> 
	data=getCursosxtipoPregunta($(this).parent("div").data("idtipopregunta"))
	console.log data
	$(this).parent("div").append(data)



getCursosxtipoPregunta = (idTipo) ->
  idTipoInt=parseInt(idTipo);
  r =jsRoutes.controllers.Cursos.geCursosXprefesorJson(idTipoInt)
  retrn=""
  $.ajax
    type: r.type
    url: r.url    
    context: this
    dataType:"json" 
    success: (data) ->
      retrn=cursos(data) 

  #if ($("#cursos").length)
        #$("#cursos").replaceWith(cursos(data))		
       #else
        #$(this).parent("div").append(cursos(data))     
      
	  		
cursos = (data) ->
  
  htmlpre= ""
  $.each data, (i, e) ->
  	htmlpre+= "<h3>"+e.idCurso+"</h3><div><p>"
  console.log htmlpre
  htmlpre


