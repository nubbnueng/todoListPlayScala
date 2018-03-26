# --- !Ups

create table "task" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "name" varchar not null,
  "detail" varchar not null,
  "boolean" bit not null
);

# --- !Downs

drop table "task" if exists;