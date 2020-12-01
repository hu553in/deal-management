-- Create "deal" table

create type status_type as enum ('pending', 'approved', 'rejected');

create table if not exists deal (
    id char(36) not null primary key,
    customer_id char(36) not null references customer (id),
    provider_id char(36) not null references provider (id),
    status status_type not null,
    description varchar not null
);
