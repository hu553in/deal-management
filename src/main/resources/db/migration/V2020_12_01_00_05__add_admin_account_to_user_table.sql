-- Add admin account to "user" table

insert into "user" values (
    '66397c00-87d0-4c61-ae9d-8d982c5be108',
    'admin@deal.management',
    '$2a$10$CeHl4PCY4WlDhlEsGbqaFebcH69LJyTDJVqyLipj5ZOZyy.wLrgTm', -- it's a hash for "j32YLvc6Ls" password
    'ROLE_ADMIN'::role_type
);
