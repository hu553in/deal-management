-- Create "provider" table

create table if not exists provider (
    id char(36) not null primary key,
    product varchar not null,
    phone varchar not null,
    email varchar not null
);