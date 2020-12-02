-- Create "deal" table

create type status_type as enum ('PENDING', 'APPROVED', 'REJECTED');

create table if not exists deal (
    id char(36) not null primary key,
    customer_id char(36) not null references customer (id) on delete cascade,
    provider_id char(36) not null references provider (id) on delete cascade,
    status status_type not null default 'PENDING',
    description varchar not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create trigger set_updated_at_trigger
before update on deal
for each row
execute procedure set_updated_at();
