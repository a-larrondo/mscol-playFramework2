$ ->
#Al cargar el documento se cargara el combo de unidades y desabilitara el boton de eliminar
  console.log $("#idAsignatura option:selected").html()
  LimpiarSelect "Unidad.idUnidad"
  jsRoutes.controllers.Preguntas.getUnidades($("#idAsignatura option:selected").val()).ajax myAjax
  numAlter = $('.alternativa').length;
  if numAlter > 1
	  $("#btnDel").attr "disabled", false



#Funcion para limpiar el dropdown
LimpiarSelect = (idControl) ->
  mySelect = document.getElementById(idControl)
  mySelect.options.length = 0

#cada ves que se cambia el texto de la pregunta se buscan las habilidades correspondiendes
$(document).on "change", "#pregunta", ->
   console.log("funciona")
   jsRoutes.controllers.Preguntas.getHabilidades($(this).val()).ajax habilidades 
# obtenidas correctamete las habilidades correspondientes se selecciona la casilla de la  habilidad
mostrarHabilidadesFn = (data) ->
  console.log "mostrar habilidades:" + data
  habilidades = data
  habilidadesList = habilidades.split(" ")
  console.log "mostrar habilidades:" + habilidadesList.length
  i = 0
  while i < habilidadesList.length
  	$("#" + habilidadesList[i]).attr "checked", true
  	console.log "#" + habilidadesList[i]
  	i++
  console.debug "Success:"
  console.debug data 
  
habilidades =
  success: mostrarHabilidadesFn
  error: errorFn  
     
#En el evento OnChange capturamos el value de la opción seleccionada
$(document).on "change", "#idAsignatura", ->
  
  #limpiamos el dropdown de resultados
  LimpiarSelect "Unidad.idUnidad"
  
  #obtenemos el json con la informacion de las unidades
  jsRoutes.controllers.Preguntas.getUnidades($(this).val()).ajax myAjax


#Si la función AJAX devuelve la data correcta llenamos dropdown
successFn = (data) ->
  console.log("Se cargo unidades")
  newDiv =""
  $.each data, (key, unidad) ->
     newDiv +="<option value='" + unidad.idUnidad + "''> " + unidad.nombreUnidad + "</option>"
  	 $(".idUnidad").html newDiv
  console.debug "Success:"
  console.debug data


#Si hay error mostramos el error en consola
errorFn = (err) ->
  console.debug "Error:"
  console.debug err
#metodo que redirecciona a la accion a realizar dependiendo del resultado e la consulta ajax
myAjax =
  success: successFn
  error: errorFn
    