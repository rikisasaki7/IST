# --- !Ups

create table addressbook (
    id    bigint(20) NOT NULL AUTO_INCREMENT,
    name  varchar(50),
    age   int,
    tel   varchar(50),
    address  varchar(128),
    PRIMARY KEY (id)
);

# --- !Downs
 
drop table addressbook;
