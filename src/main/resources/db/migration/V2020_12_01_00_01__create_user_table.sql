-- Create "user" table

create type role_type as enum ('VIEWER', 'EDITOR', 'SUPERVISOR', 'ADMIN');

create table if not exists "user" (
    id char(36) not null primary key,
    email varchar not null,
    password varchar not null,
    "role" role_type not null default 'VIEWER',
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create unique index ux_user_email on "user" using btree ((email));

create trigger set_updated_at_trigger
before update on "user"
for each row
execute procedure set_updated_at();
