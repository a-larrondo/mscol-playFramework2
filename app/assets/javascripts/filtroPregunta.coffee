this.ctrs= exports ? this
$ ->
#Al cargar el documento se cargara el combo tipo de preguntas
  console.log $("#idTipoPregunta option:selected").html()
  LimpiarSelect "idAsignatura"
  LimpiarSelect "idCurso"
  getCursoAsignatura ""
  
  
getCursoAsignatura = () ->
  jsRoutes.controllers.AsignaturaSecciones.getFiltros($("#idTipoPregunta option:selected").val()).ajax myAjax

#Funcion para limpiar el dropdown
LimpiarSelect = (idControl) ->
  mySelect = document.getElementById(idControl)
  mySelect.options.length = 0

getAsignatura = (data) ->
   newAsignatura=""
   $.each data.asignatura, (asignaturas, asignatura) ->
      newAsignatura +="<option value='" + asignatura.idAsignatura + "''> " + asignatura.nombreAsignatura + "</option>"
   newAsignatura

getCurso = (data) ->
  newCurso=""
  $.each data.curso, (cursos, curso) ->
      console.log(curso.nombre)                 
      newCurso +="<option value='" + curso.idCurso + "'> " + curso.nombreCurso + "</option>"
  newCurso

  #Si la función AJAX devuelve la data correcta llenamos dropdown
successFn = (data) ->
  #console.log(data)  
  $("#idCurso").html getCurso(data)
  $("#idAsignatura").html getAsignatura(data)

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

$(document).on "change", "#idTipoPregunta", ->
  LimpiarSelect "idAsignatura"
  LimpiarSelect "idCurso"
  getCursoAsignatura ""

$(document).on "change", "#idAsignatura", ->
  console.log("cambio asignatura")
  getCategorias ""
$(document).on "change", "#idCurso", ->
  console.log("cambio curso")
  getCategorias ""
   
getCategorias = () ->
  console.log("get categorias")
  idTipo=$("#idTipoPregunta option:selected").val()
  idAsignatura=$("#idAsignatura option:selected").val()
  idCurso=$("#idCurso option:selected").val()
  unless idAsignatura and idCurso and idTipo
    $("#mensajeErr").append "<p class='error'>No se pueden obtener categorias sin el curso y asignatura seleccionada</p>"
  else
    console.log("categoriasssss")
    jsRoutes.controllers.Categorias.getJerarquia(idTipo,idAsignatura,idCurso).ajax ctgrsAjax

sucFirstCtgrsAjax = (data) ->
  console.log "datos categorias"
  #console.log(data)
  $("#filtrocategorias").html data

getPreguntas = (infoFiltro, paginacion = 0) ->
  ctrs.json=JSON.parse(infoFiltro)
  ctrs.json["pagina"]=paginacion
  infoFiltro=JSON.stringify(ctrs.json);
  r = jsRoutes.controllers.Preguntas.getPreguntasfiltradas()
  ctrs.json=infoFiltro
  $.ajax
    type: r.type
    contentType:"application/json"
    url: r.url    
    context: this
    data: infoFiltro
    dataType:"json" 
    success: (data) ->
      ctrs.data = data
      console.log data
      $("#preguntasContent").html " "
      $.each data.preguntas, (i, e) ->
        #console.log "valor1="+ e.pregunta
        div1="<div id='"+e.idPregunta+"' class='pregunta'>"
        table1="<table class='table table-hover table-bordered'>"
        if($('#preguntasPruebas :input[value="'+e.idPregunta+'"]').length)
           thead1="<thead><th class='active'><button type='button' class='btn btn-danger btn-lg'><span class='glyphicon glyphicon-plus-sign prueba' data-idPregunta='"+e.idPregunta+"'></span> </button><br><strong>"+e.pregunta
        else   
           thead1="<thead><th class='active'><button type='button' class='btn btn-default btn-lg'><span class='glyphicon glyphicon-plus-sign prueba' data-idPregunta='"+e.idPregunta+"'></span> </button><br><strong>"+e.pregunta   
        closethead1="</strong></span></th></thead>"
        tbody1="<tbody><tr><td><div class='alternativas' data-idPregunta='"+e.idPregunta+"'><div class='btn btn-success'>cargar alternativas</div></div></td></tr>"
        closetbody1="</tbody></table></div>"
        #console.log div1+table1+thead1+closethead1+closetbody1
        $("#preguntasContent").append div1+table1+thead1+closethead1+tbody1+closetbody1
      paginacion= "<ul class='pagination'>"
      if(ctrs.data.pageIndex==0)
        paginacion+= "<li class='disabled'><a href='#'>«</a></li>"
      else
        pag=ctrs.data.pageIndex-1
        paginacion+= "<li data-page='"+pag+"'><a href='#'>«</a></li>" 
      i = 0
      totalPags=ctrs.data.pageTotal
      while i <= totalPags
        a=i
        a++        
        if(ctrs.data.pageIndex==i)
          paginacion+="<li class='active' data-page='"+i+"'><a href='#'>"+a+"</a></li>"        
        else
          paginacion+="<li data-page='"+i+"'><a href='#'>"+a+"</a></li>"  
        i++
      if(ctrs.data.pageIndex==ctrs.data.pageTotal)  
        paginacion+="<li class='disabled'><a href='#'>»</a></li>"
      else
        pag=ctrs.data.pageIndex+1
        paginacion+="<li data-page='"+pag+"'><a href='#'>»</a></li>" 
      paginacion+="</ul>"        
      $("#preguntaspaginacion").html paginacion
    error: () ->
      $("#preguntasContent").html " "
      $("#preguntaspaginacion").html " "

ctgrsAjax =
  success: sucFirstCtgrsAjax
  error: errorFn

$(document).on "click", "#btngetpreguntas", ->  
  console.log getCaracteristicasSeleccionadas ""  
  #console.log "tipo de consulta="+ r.url   
  ctrs.json = getCaracteristicasSeleccionadas ""
  getPreguntas ctrs.json

$(document).on "click", ".pagination li", -> 
  #console.log "pagination" 
  #console.log $(this).data("page")
  if ( typeof ctrs.json != 'undefined')
    getPreguntas ctrs.json, $(this).data("page")
  else
     getPreguntas getCaracteristicasSeleccionadas "", $(this).data("page")

getCaracteristicasSeleccionadas = () ->
  jsonCaracteristicas=[]
  jsonFiltros={}
  $("#filtrocategorias :checked").each ->
    idcatsup=$(this).parent().data('idcatsup')    
    idCategoria=parseInt($(this).val())
    console.log "la categoria sup es "+idcatsup+ " id de la categoria es "+idCategoria
    caracteristica = {}
    caracteristica["categoriaSupid"] = idcatsup
    caracteristica["categoriaid"] = idCategoria
    jsonCaracteristicas.push(caracteristica)
  jsonFiltros["curso"]=$("#idCurso option:selected").val()
  jsonFiltros["asignatura"]=$("#idAsignatura option:selected").val()  
  jsonFiltros["categorias"]=jsonCaracteristicas 
  console.log "apreto el btn de filtro"
  console.log jsonFiltros
  return JSON.stringify(jsonFiltros);

$(document).on "change", "#filtrocategorias input:checkbox", ->
  opSeleccionada = $(this).val()
  if($(this).is(':checked'))
    console.log "chequeada "  + opSeleccionada 
    $("div [data-idcatsup=" + opSeleccionada + "]").show()   
  #if($(this).parent().find('input').is(':checked'))    
    #console.log opSeleccionada

  if(!$(this).is(':checked'))
    console.log "deseleccionada " + opSeleccionada 
    idJerarquia=$(this).parent().parent().data('idjerarquia')
    console.log "id jerarquia es igual a "+ idJerarquia
    console.log "desmarcada jerarquia="+idJerarquia
    desmarcar opSeleccionada      
      #$("div[data-idcatsup=" + opSeleccionada + "] input:checkbox").attr('checked', false);

  

desmarcar = (idCatsup) ->
  console.log "desmarcar id categoria superior="+idCatsup
  $("div[data-idcatsup=" + idCatsup + "] input:checkbox").each ->   
    if $(this).parent().find("input").is(":checked")
      $("div[data-idcatsup=" + idCatsup + "] input:checkbox").attr('checked', false)
      opseleccionada=$(this).val()
      desmarcar(opseleccionada)
      #console.log $(this).val()
    $("div[data-idcatsup=" + idCatsup + "]").hide()  
  return
  

refrescarCategorias = (idCategoriaSeleccionada) ->
    jsRoutes.controllers.Projects.getCategorias().ajax
        success: (data) ->
  #$("div[data-idjerarquia=" + id + "]").html "prueba variables"

$(document).on "click", ".alternativas", ->
  console.log "id pregunta="+$(this).data("idpregunta")
  getAlternativas ($(this).data("idpregunta"))

getAlternativas = (idpregunta) ->
  idPreguntaJson={}
  idPreguntaJson["idPregunta"]=idpregunta
  r = jsRoutes.controllers.Alternativas.getAlternativas()  
  $.ajax
    type: r.type
    contentType:"application/json"
    url: r.url    
    context: this
    data: JSON.stringify(idPreguntaJson)
    dataType:"json" 
    success: (data) ->
      console.log "alternativas"
      console.log data
      $(".alternativas").filter('[data-idpregunta="'+idpregunta+'"]').html " "
      table1="<table class='table table-hover table-bordered'><tbody>"
      div1=""
      tbody1=""
      $.each data, (i, e) ->
        ctrs.alternativa=e
        altrTexto=e.alternativaTxt
         
        if e.esImagen is true
          altrTexto="<img src='/assets/"+e.alternativaTxt+"' alt='some_text'>"
        
        if e.correcta is true  
          tbody1+="<tr><td><div class='alternativa alert alert-success' data-idAlternativa='"+e.idAlternativa+"'>"+altrTexto+"</div></td></tr>"  
        else
          tbody1+="<tr><td><div class='alternativa' data-idAlternativa='"+e.idAlternativa+"'>"+altrTexto+"</div></td></tr>"
      closetbody1="</tbody></table>"
      $(".alternativas").filter('[data-idpregunta="'+idpregunta+'"]').append table1+tbody1+closetbody1
  return

$(document).on "click", "button .prueba", ->
  idPregunta=$(this).data('idpregunta')  
  largoTxt=100
  if($('#preguntasPruebas :input[value="'+idPregunta+'"]').length)
    $("#preguntasPruebas .wrapperpregunta#"+idPregunta+" ").remove()
    $(".pregunta#"+idPregunta+" button").removeClass('btn-danger');
    $( ".pregunta#"+idPregunta+" button" ).blur();
  else
    $(".pregunta#"+idPregunta+" button").addClass('btn btn-danger');
    valPregunta=$(".pregunta#"+idPregunta+" tr th strong").text().substring(0,largoTxt);
    console.log("valor es igual="+idPregunta)  
    campoPregunta="<div class='wrapperpregunta' id='"+idPregunta+"'><label>"+valPregunta.replace(/\W*\s(\S)*$/, '...')+"</label><input type='hidden' name='idPregunta' id='idPregunta"+idPregunta+"' value='"+idPregunta+"'> <br><div/>"
    $("#preguntasPruebas").append(campoPregunta);
    $( ".pregunta#"+idPregunta+" button" ).blur();

#Trogle de el menu de pruebas
$(document).on "click", "#sliderBarPruebabtn button", ->
   $("#sliderBarPruebainner").slideToggle();  

#Mostrar y ocultar imput y etiquetas
$(document).on "click", ".labelEditable", ->
   $("input", this).show();
   $("input", this).focus();
   $("label", this).hide();  
   #e.preventDefault();

$(document).on "focusout", ".labelEditable input", ->
   if($(this).val().trim().length>2)
      $(this).siblings("label").html($(this).val().trim()); 
      console.log("mayor a 2")
   if($(this).val().trim().length<=2)
      console.log("menor a 2")
      $(this).html($(this).siblings("label").val());       
   $(this).hide();
   $(this).siblings("label").show(); 
   #e.preventDefault();

$(document).on "keypress", ".labelEditable input",(e) ->
  #console.log(e.which)
  code = e.keyCode || e.which
  if(code == 13)
    console.log("apreto enter")
    (this).blur()  