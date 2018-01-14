this.ctrs= exports ? this
$ ->
  getTipoPregunta ""
 	console.log "cargo documnento"
 	$("#accordion").accordion
    collapsible: true
    active: false
#$(document).on "click", "#btngetTipos", ->  
  #getTipoPregunta ""
  #console.log json

getTipoPregunta = () ->
  r =jsRoutes.controllers.AsignaturaSecciones.getTipoFiltros().ajax myAjax
      
       
#Si la función AJAX devuelve la data correcta llenamos dropdown
successFn = (data) ->
  #console.log data
  ctrs.data=data
  htmlSelects =getTipos(data)
  htmlSelects +="<br>"+ getAsignatura(data)
  htmlSelects +="<br>"+ getCurso(data)
  ctrs.html=htmlSelects
  $("#filtrosTiposPreguntas").html htmlSelects
  getCategorias ""

#Si hay error mostramos el error en consola
errorFn = (err) ->
  console.debug "Error:"
  console.debug err
#metodo que redirecciona a la accion a realizar dependiendo del resultado e la consulta ajax
myAjax =
  success: successFn
  error: errorFn
  		

getTipos = (data) ->
   newTipo="<select id='idTipoPregunta'>"
   $.each data.tipoPregunta, (tipoPreguntas, tipoPregunta) ->
      newTipo +="<option value='" + tipoPregunta.idTipoPregunta + "''> " + tipoPregunta.nombreTipo + "</option>"
   newTipo +="</select>"   
   newTipo

getAsignatura = (data) ->
   newAsignatura="<select id='idAsignatura'>"
   $.each data.asignatura, (asignaturas, asignatura) ->
      newAsignatura +="<option value='" + asignatura.idAsignatura + "''> " + asignatura.nombreAsignatura + "</option>"
   newAsignatura +="</select>"  
   newAsignatura

getCurso = (data) ->
  newCurso="<select id='idCurso'>"
  $.each data.curso, (cursos, curso) ->
      console.log(curso.nombre)                 
      newCurso +="<option value='" + curso.idCurso + "'> " + curso.nombreCurso + "</option>"
  newCurso+="<select>"
  newCurso



getCategorias = () ->
  #console.log("get categorias")
  idTipo=$("#idTipoPregunta option:selected").val()
  idAsignatura=$("#idAsignatura option:selected").val()
  idCurso=$("#idCurso option:selected").val()
  unless idAsignatura and idCurso and idTipo
    $("#mensajeErr").append "<p class='error'>No se pueden obtener categorias sin el curso y asignatura seleccionada</p>"
  else
    console.log("categoriasssss")
    jsRoutes.controllers.Categorias.getJerarquiaFiltro(idTipo,idAsignatura,idCurso).ajax ctgrsAjax

sucFirstCtgrsAjax = (data) ->
  console.log "datos categorias"
  console.log(data)
  $("#filtrocategorias").html data

ctgrsAjax =
  success: sucFirstCtgrsAjax
  error: errorFn

$(document).on "change", "#filtrosTiposPreguntas select", ->
  getCategorias ""

$(document).on "change", "#filtrocategorias input:checkbox", ->
  dTipo=parseInt($("#idTipoPregunta option:selected").val())
  idAsignatura=parseInt($("#idAsignatura option:selected").val())
  idCurso=parseInt($("#idCurso option:selected").val())
  idJerarquiaL = parseInt($(this).val())
  console.log "opcion seleccionada="+idJerarquiaL
  $("#filtrocategorias input:checkbox").removeAttr('checked')  
  $(this).prop( "checked", true );
  jsRoutes.controllers.Preguntas.getPreguntasxCategorias(idAsignatura,idCurso, idJerarquiaL).ajax PreCatAjax  
  

sucPreCatAjax = (data) ->
  console.log "info preguntas"
  console.log data
  preguntasHTML (data)

PreCatAjax =
  success: sucPreCatAjax
  error: errorFn 

preguntasHTML = (data) ->
  htmlpre= ""
  $.each data, (i, e) ->
    htmlpre+= "<h3>"+e.nombreCategoria+"</h3><div><p>"
    htmlpre+="</p></div>"
  console.log htmlpre
  $("#filtrosPreguntas #accordion").html(htmlpre)
  $("#accordion").accordion
    active: false
  $("#accordion").accordion("refresh")



tablaPregunta = (e) ->
      retrn= ""
      div1="<div id='"+e.idPregunta+"' class='pregunta'>"
      table1="<table class='table table-hover table-bordered'>"  
      thead1="<thead><th class='active'><button type='button' class='btn btn-default btn-lg'><span class='glyphicon glyphicon-plus-sign prueba' data-idPregunta='"+e.idPregunta+"'></span> </button><br><strong>"+e.pregunta   
      closethead1="</strong></span></th></thead>"
      tbody1="<tbody><tr><td><div class='alternativas' data-idPregunta='"+e.idPregunta+"'><div class='btn btn-success'>cargar alternativas</div></div></td></tr>"
      closetbody1="</tbody></table></div>"
      retrn= div1+table1+thead1+closethead1+tbody1+closetbody1
      retrn