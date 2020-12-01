-- Create "customer" table

create table if not exists customer (
    id char(36) not null primary key,
    product varchar not null,
    phone varchar not null
);
