-- Create "deal" table

create table if not exists deal (
    deal_id char(36) not null primary key,
    customer_id char(36) not null references customer (customer_id),
    provider_id char(36) not null references provider (provider_id),
    status varchar not null,
    description varchar not null
);
