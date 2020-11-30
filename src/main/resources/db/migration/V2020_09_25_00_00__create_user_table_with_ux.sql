-- Create "user" table with ux

create table if not exists "user" (
    user_id char(36) not null primary key,
    email varchar not null,
    password varchar not null,
    "role" varchar not null
);

create unique index ux_user_email on "user" using btree ((email));
