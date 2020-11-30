-- Create "provider" table

create table if not exists provider (
    provider_id char(36) not null primary key,
    product varchar not null,
    phone varchar not null,
    email varchar not null
);
