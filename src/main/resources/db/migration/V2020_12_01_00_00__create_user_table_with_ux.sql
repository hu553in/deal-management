-- Create "user" table with ux

create type role_type as enum ('viewer', 'editor', 'supervisor', 'admin');

create table if not exists "user" (
    id char(36) not null primary key,
    email varchar not null,
    password varchar not null,
    "role" role_type not null
);

create unique index ux_user_email on "user" using btree ((email));
