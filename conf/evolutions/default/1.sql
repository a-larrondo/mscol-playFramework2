# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table alternativa (
  id_alternativa            bigint auto_increment not null,
  alternativa_txt           varchar(255),
  es_correcta               tinyint(1) default 0,
  es_imagen                 tinyint(1) default 0,
  pregunta_idPregunta       bigint,
  constraint pk_alternativa primary key (id_alternativa))
;

create table alumno (
  idAlumno                  bigint auto_increment not null,
  usuario_id                bigint,
  seccion_id                bigint,
  constraint pk_alumno primary key (idAlumno))
;

create table asignatura (
  idAsignatura              bigint auto_increment not null,
  nombre_asignatura         varchar(255),
  a_year                    date,
  tipo_pregunta_id          bigint,
  constraint pk_asignatura primary key (idAsignatura))
;

create table asignatura_seccion (
  id_asignaturaseccion      bigint auto_increment not null,
  nombre_asignatura_seccion varchar(255),
  profesor_id               bigint,
  seccion_id                bigint,
  curso_id                  bigint,
  asignatura_id             bigint,
  constraint pk_asignatura_seccion primary key (id_asignaturaseccion))
;

create table asociacionesCategoria (
  idAsociacion              bigint auto_increment not null,
  codigo                    varchar(255),
  curso_id                  bigint,
  asignatura_id             bigint,
  id_categoria_superior     bigint,
  categoria_id              bigint,
  tipo_pregunta_id          bigint,
  nombre_categoria_superior varchar(255),
  id_categoria_anterior     bigint,
  asociacione_categoria_id_jerarquia bigint,
  constraint pk_asociacionesCategoria primary key (idAsociacion))
;

create table categoria (
  id_categoria              bigint auto_increment not null,
  nombre_categoria          varchar(255),
  descripcion               varchar(255),
  categoria_habilitada      tinyint(1) default 0,
  tipo_Pregunta_id          bigint,
  constraint pk_categoria primary key (id_categoria))
;

create table colegio (
  idColegio                 bigint auto_increment not null,
  nombre                    varchar(255),
  nombre2                   varchar(255),
  constraint pk_colegio primary key (idColegio))
;

create table coordinador (
  idCoordinador             bigint auto_increment not null,
  usuario_id                bigint,
  constraint pk_coordinador primary key (idCoordinador))
;

create table curso (
  idCurso                   bigint auto_increment not null,
  nivel                     integer,
  nombre                    varchar(255),
  a_year                    datetime,
  tipo_pregunta_id          bigint,
  constraint pk_curso primary key (idCurso))
;

create table descripcionCategoria (
  idDescripcionCategoria    bigint auto_increment not null,
  nombre_atributo           varchar(255),
  valor_atributo            varchar(255),
  habilitado_descripcion_categoria tinyint(1) default 0,
  idCategoria               bigint,
  nombreCategoria           varchar(255),
  constraint pk_descripcionCategoria primary key (idDescripcionCategoria))
;

create table jerarquiaTipoPregunta (
  id_jerarquia              bigint auto_increment not null,
  nombre_jerarquia          varchar(255),
  jerarquia_habilitada      tinyint(1) default 0,
  numero_jerarquia          bigint,
  seleccion_unica           tinyint(1) default 0,
  id_clasificacion_Superior bigint,
  nombre_clasificacion_Superior varchar(255),
  id_clasificacion_Anterior bigint,
  tipo_Pregunta_id          bigint,
  constraint pk_jerarquiaTipoPregunta primary key (id_jerarquia))
;

create table perfil (
  idPerfil                  bigint auto_increment not null,
  descripcion               varchar(255),
  constraint pk_perfil primary key (idPerfil))
;

create table pregunta (
  idPregunta                bigint auto_increment not null,
  pregunta                  longtext,
  justificacion             varchar(255),
  comentario                varchar(255),
  desarrollo                varchar(255),
  es_imagen                 tinyint(1) default 0,
  a_year_pregunta           datetime,
  curso_id                  bigint,
  asignatura_id             bigint,
  pregunta_usuario_id       bigint,
  unidad_id                 bigint,
  constraint pk_pregunta primary key (idPregunta))
;

create table profesor (
  idProfesor                bigint auto_increment not null,
  fecha_inicio              datetime,
  fecha_expiracion          datetime,
  usuario_id                bigint,
  coordinador_id            bigint,
  constraint pk_profesor primary key (idProfesor))
;

create table prueba (
  idPrueba                  bigint auto_increment not null,
  fecha_creacion            datetime,
  titulo_prueba             varchar(255),
  descripcion               varchar(255),
  profesor_id               bigint,
  constraint pk_prueba primary key (idPrueba))
;

create table respuesta (
  idRespuesta               bigint auto_increment not null,
  nombre                    varchar(255),
  prueba_id                 bigint,
  alumno_id                 bigint,
  constraint pk_respuesta primary key (idRespuesta))
;

create table seccion (
  id_seccion                bigint auto_increment not null,
  nombre_seccion            varchar(255),
  curso_id                  bigint,
  constraint pk_seccion primary key (id_seccion))
;

create table tipo_Pregunta (
  id_tipoPregunta           bigint auto_increment not null,
  nombreTipo                varchar(255),
  tipoPreguntaHabilitado    tinyint(1) default 0,
  constraint pk_tipo_Pregunta primary key (id_tipoPregunta))
;

create table toma_prueba (
  idTomaPrueba              bigint auto_increment not null,
  contestada                tinyint(1) default 0,
  inicio_prueba             datetime,
  termino_prueba            datetime,
  alumno_id                 bigint,
  prueba_id                 bigint,
  constraint pk_toma_prueba primary key (idTomaPrueba))
;

create table unidad (
  idUnidad                  bigint auto_increment not null,
  nombre_unidad             varchar(255),
  a_year_unidad             datetime,
  asignatura_id             bigint,
  constraint pk_unidad primary key (idUnidad))
;

create table usuario (
  idUsuario                 bigint auto_increment not null,
  mail                      varchar(255),
  rut                       integer,
  nombre                    varchar(255),
  apellido                  varchar(255),
  genero                    varchar(255),
  clave                     varchar(255),
  habilitado                tinyint(1) default 0,
  constraint uq_usuario_mail unique (mail),
  constraint pk_usuario primary key (idUsuario))
;


create table perfil_usuario (
  perfil_idPerfil                bigint not null,
  usuario_idUsuario              bigint not null,
  constraint pk_perfil_usuario primary key (perfil_idPerfil, usuario_idUsuario))
;

create table pregunta_categoria (
  pregunta_idPregunta            bigint not null,
  categoria_id_categoria         bigint not null,
  constraint pk_pregunta_categoria primary key (pregunta_idPregunta, categoria_id_categoria))
;

create table prueba_pregunta (
  prueba_idPrueba                bigint not null,
  pregunta_idPregunta            bigint not null,
  constraint pk_prueba_pregunta primary key (prueba_idPrueba, pregunta_idPregunta))
;

create table usuario_perfil (
  usuario_idUsuario              bigint not null,
  perfil_idPerfil                bigint not null,
  constraint pk_usuario_perfil primary key (usuario_idUsuario, perfil_idPerfil))
;
alter table alternativa add constraint fk_alternativa_pregunta_1 foreign key (pregunta_idPregunta) references pregunta (idPregunta) on delete restrict on update restrict;
create index ix_alternativa_pregunta_1 on alternativa (pregunta_idPregunta);
alter table alumno add constraint fk_alumno_usuario_2 foreign key (usuario_id) references usuario (idUsuario) on delete restrict on update restrict;
create index ix_alumno_usuario_2 on alumno (usuario_id);
alter table alumno add constraint fk_alumno_seccion_3 foreign key (seccion_id) references seccion (id_seccion) on delete restrict on update restrict;
create index ix_alumno_seccion_3 on alumno (seccion_id);
alter table asignatura add constraint fk_asignatura_tipo_pregunta_a_4 foreign key (tipo_pregunta_id) references tipo_Pregunta (id_tipoPregunta) on delete restrict on update restrict;
create index ix_asignatura_tipo_pregunta_a_4 on asignatura (tipo_pregunta_id);
alter table asignatura_seccion add constraint fk_asignatura_seccion_profesor_5 foreign key (profesor_id) references profesor (idProfesor) on delete restrict on update restrict;
create index ix_asignatura_seccion_profesor_5 on asignatura_seccion (profesor_id);
alter table asignatura_seccion add constraint fk_asignatura_seccion_seccion_6 foreign key (seccion_id) references seccion (id_seccion) on delete restrict on update restrict;
create index ix_asignatura_seccion_seccion_6 on asignatura_seccion (seccion_id);
alter table asignatura_seccion add constraint fk_asignatura_seccion_curso_7 foreign key (curso_id) references curso (idCurso) on delete restrict on update restrict;
create index ix_asignatura_seccion_curso_7 on asignatura_seccion (curso_id);
alter table asignatura_seccion add constraint fk_asignatura_seccion_asignatura_8 foreign key (asignatura_id) references asignatura (idAsignatura) on delete restrict on update restrict;
create index ix_asignatura_seccion_asignatura_8 on asignatura_seccion (asignatura_id);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_curso_9 foreign key (curso_id) references curso (idCurso) on delete restrict on update restrict;
create index ix_asociacionesCategoria_curso_9 on asociacionesCategoria (curso_id);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_asignatura_10 foreign key (asignatura_id) references asignatura (idAsignatura) on delete restrict on update restrict;
create index ix_asociacionesCategoria_asignatura_10 on asociacionesCategoria (asignatura_id);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_id_categoria_superior_11 foreign key (id_categoria_superior) references categoria (id_categoria) on delete restrict on update restrict;
create index ix_asociacionesCategoria_id_categoria_superior_11 on asociacionesCategoria (id_categoria_superior);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_categoria_id_12 foreign key (categoria_id) references categoria (id_categoria) on delete restrict on update restrict;
create index ix_asociacionesCategoria_categoria_id_12 on asociacionesCategoria (categoria_id);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_tipo_pregunta_id_13 foreign key (tipo_pregunta_id) references tipo_Pregunta (id_tipoPregunta) on delete restrict on update restrict;
create index ix_asociacionesCategoria_tipo_pregunta_id_13 on asociacionesCategoria (tipo_pregunta_id);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_nombre_categoria_superior_14 foreign key (nombre_categoria_superior) references categoria (nombre_categoria) on delete restrict on update restrict;
create index ix_asociacionesCategoria_nombre_categoria_superior_14 on asociacionesCategoria (nombre_categoria_superior);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_id_categoria_anterior_15 foreign key (id_categoria_anterior) references categoria (id_categoria) on delete restrict on update restrict;
create index ix_asociacionesCategoria_id_categoria_anterior_15 on asociacionesCategoria (id_categoria_anterior);
alter table asociacionesCategoria add constraint fk_asociacionesCategoria_jerarquiaTipoPregunta_16 foreign key (asociacione_categoria_id_jerarquia) references jerarquiaTipoPregunta (id_jerarquia) on delete restrict on update restrict;
create index ix_asociacionesCategoria_jerarquiaTipoPregunta_16 on asociacionesCategoria (asociacione_categoria_id_jerarquia);
alter table categoria add constraint fk_categoria_tipo_Pregunta_17 foreign key (tipo_Pregunta_id) references tipo_Pregunta (id_tipoPregunta) on delete restrict on update restrict;
create index ix_categoria_tipo_Pregunta_17 on categoria (tipo_Pregunta_id);
alter table coordinador add constraint fk_coordinador_usuario_18 foreign key (usuario_id) references usuario (idUsuario) on delete restrict on update restrict;
create index ix_coordinador_usuario_18 on coordinador (usuario_id);
alter table curso add constraint fk_curso_tipo_pregunta_19 foreign key (tipo_pregunta_id) references tipo_Pregunta (id_tipoPregunta) on delete restrict on update restrict;
create index ix_curso_tipo_pregunta_19 on curso (tipo_pregunta_id);
alter table descripcionCategoria add constraint fk_descripcionCategoria_idCategoria_20 foreign key (idCategoria) references categoria (id_categoria) on delete restrict on update restrict;
create index ix_descripcionCategoria_idCategoria_20 on descripcionCategoria (idCategoria);
alter table descripcionCategoria add constraint fk_descripcionCategoria_nombreCategoria_21 foreign key (nombreCategoria) references categoria (nombre_categoria) on delete restrict on update restrict;
create index ix_descripcionCategoria_nombreCategoria_21 on descripcionCategoria (nombreCategoria);
alter table jerarquiaTipoPregunta add constraint fk_jerarquiaTipoPregunta_idClasificacionSuperior_22 foreign key (id_clasificacion_Superior) references jerarquiaTipoPregunta (id_jerarquia) on delete restrict on update restrict;
create index ix_jerarquiaTipoPregunta_idClasificacionSuperior_22 on jerarquiaTipoPregunta (id_clasificacion_Superior);
alter table jerarquiaTipoPregunta add constraint fk_jerarquiaTipoPregunta_nombreClasificacion_Superior_23 foreign key (nombre_clasificacion_Superior) references jerarquiaTipoPregunta (nombre_jerarquia) on delete restrict on update restrict;
create index ix_jerarquiaTipoPregunta_nombreClasificacion_Superior_23 on jerarquiaTipoPregunta (nombre_clasificacion_Superior);
alter table jerarquiaTipoPregunta add constraint fk_jerarquiaTipoPregunta_idClasificacionAnterior_24 foreign key (id_clasificacion_Anterior) references jerarquiaTipoPregunta (id_jerarquia) on delete restrict on update restrict;
create index ix_jerarquiaTipoPregunta_idClasificacionAnterior_24 on jerarquiaTipoPregunta (id_clasificacion_Anterior);
alter table jerarquiaTipoPregunta add constraint fk_jerarquiaTipoPregunta_tipo_Preguntas_25 foreign key (tipo_Pregunta_id) references tipo_Pregunta (id_tipoPregunta) on delete restrict on update restrict;
create index ix_jerarquiaTipoPregunta_tipo_Preguntas_25 on jerarquiaTipoPregunta (tipo_Pregunta_id);
alter table pregunta add constraint fk_pregunta_curso_26 foreign key (curso_id) references curso (idCurso) on delete restrict on update restrict;
create index ix_pregunta_curso_26 on pregunta (curso_id);
alter table pregunta add constraint fk_pregunta_asignatura_27 foreign key (asignatura_id) references asignatura (idAsignatura) on delete restrict on update restrict;
create index ix_pregunta_asignatura_27 on pregunta (asignatura_id);
alter table pregunta add constraint fk_pregunta_usuario_28 foreign key (pregunta_usuario_id) references usuario (idUsuario) on delete restrict on update restrict;
create index ix_pregunta_usuario_28 on pregunta (pregunta_usuario_id);
alter table pregunta add constraint fk_pregunta_unidad_29 foreign key (unidad_id) references unidad (idUnidad) on delete restrict on update restrict;
create index ix_pregunta_unidad_29 on pregunta (unidad_id);
alter table profesor add constraint fk_profesor_usuario_30 foreign key (usuario_id) references usuario (idUsuario) on delete restrict on update restrict;
create index ix_profesor_usuario_30 on profesor (usuario_id);
alter table profesor add constraint fk_profesor_coordinador_31 foreign key (coordinador_id) references coordinador (idCoordinador) on delete restrict on update restrict;
create index ix_profesor_coordinador_31 on profesor (coordinador_id);
alter table prueba add constraint fk_prueba_profesor_32 foreign key (profesor_id) references profesor (idProfesor) on delete restrict on update restrict;
create index ix_prueba_profesor_32 on prueba (profesor_id);
alter table respuesta add constraint fk_respuesta_prueba_33 foreign key (prueba_id) references prueba (idPrueba) on delete restrict on update restrict;
create index ix_respuesta_prueba_33 on respuesta (prueba_id);
alter table respuesta add constraint fk_respuesta_alumno_34 foreign key (alumno_id) references alumno (idAlumno) on delete restrict on update restrict;
create index ix_respuesta_alumno_34 on respuesta (alumno_id);
alter table seccion add constraint fk_seccion_curso_35 foreign key (curso_id) references curso (idCurso) on delete restrict on update restrict;
create index ix_seccion_curso_35 on seccion (curso_id);
alter table toma_prueba add constraint fk_toma_prueba_alumno_36 foreign key (alumno_id) references alumno (idAlumno) on delete restrict on update restrict;
create index ix_toma_prueba_alumno_36 on toma_prueba (alumno_id);
alter table toma_prueba add constraint fk_toma_prueba_prueba_37 foreign key (prueba_id) references prueba (idPrueba) on delete restrict on update restrict;
create index ix_toma_prueba_prueba_37 on toma_prueba (prueba_id);
alter table unidad add constraint fk_unidad_asignatura_38 foreign key (asignatura_id) references asignatura (idAsignatura) on delete restrict on update restrict;
create index ix_unidad_asignatura_38 on unidad (asignatura_id);



alter table perfil_usuario add constraint fk_perfil_usuario_perfil_01 foreign key (perfil_idPerfil) references perfil (idPerfil) on delete restrict on update restrict;

alter table perfil_usuario add constraint fk_perfil_usuario_usuario_02 foreign key (usuario_idUsuario) references usuario (idUsuario) on delete restrict on update restrict;

alter table pregunta_categoria add constraint fk_pregunta_categoria_pregunta_01 foreign key (pregunta_idPregunta) references pregunta (idPregunta) on delete restrict on update restrict;

alter table pregunta_categoria add constraint fk_pregunta_categoria_categoria_02 foreign key (categoria_id_categoria) references categoria (id_categoria) on delete restrict on update restrict;

alter table prueba_pregunta add constraint fk_prueba_pregunta_prueba_01 foreign key (prueba_idPrueba) references prueba (idPrueba) on delete restrict on update restrict;

alter table prueba_pregunta add constraint fk_prueba_pregunta_pregunta_02 foreign key (pregunta_idPregunta) references pregunta (idPregunta) on delete restrict on update restrict;

alter table usuario_perfil add constraint fk_usuario_perfil_usuario_01 foreign key (usuario_idUsuario) references usuario (idUsuario) on delete restrict on update restrict;

alter table usuario_perfil add constraint fk_usuario_perfil_perfil_02 foreign key (perfil_idPerfil) references perfil (idPerfil) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table alternativa;

drop table alumno;

drop table asignatura;

drop table asignatura_seccion;

drop table asociacionesCategoria;

drop table categoria;

drop table colegio;

drop table coordinador;

drop table curso;

drop table descripcionCategoria;

drop table jerarquiaTipoPregunta;

drop table perfil;

drop table perfil_usuario;

drop table pregunta;

drop table pregunta_categoria;

drop table profesor;

drop table prueba;

drop table prueba_pregunta;

drop table respuesta;

drop table seccion;

drop table tipo_Pregunta;

drop table toma_prueba;

drop table unidad;

drop table usuario;

drop table usuario_perfil;

SET FOREIGN_KEY_CHECKS=1;

