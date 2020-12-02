-- Create "provider" table

create table if not exists provider (
    id char(36) not null primary key,
    product varchar not null,
    phone varchar not null,
    email varchar not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create trigger set_updated_at_trigger
before update on provider
for each row
execute procedure set_updated_at();
