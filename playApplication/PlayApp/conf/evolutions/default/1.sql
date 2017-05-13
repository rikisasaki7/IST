# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table checks (
  id                        bigint not null,
  name                      varchar(255),
  result                    varchar(255),
  created                   timestamp,
  modified                  timestamp,
  constraint pk_checks primary key (id))
;

create table message (
  id                        bigint not null,
  name                      varchar(255),
  mail                      varchar(255),
  message                   varchar(255),
  postdate                  timestamp not null,
  constraint pk_message primary key (id))
;

create sequence checks_seq;

create sequence message_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists checks;

drop table if exists message;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists checks_seq;

drop sequence if exists message_seq;

