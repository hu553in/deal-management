-- Create "customer" table

create table if not exists customer (
    id char(36) not null primary key,
    product varchar not null,
    phone varchar not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create trigger set_updated_at_trigger
before update on customer
for each row
execute procedure set_updated_at();
