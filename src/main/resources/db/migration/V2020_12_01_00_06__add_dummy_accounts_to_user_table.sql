-- Add dummy accounts to "user" table

insert into "user" values (
    'db12d3c4-06e1-42f8-ab17-700e81844b83',
    'viewer@deal.management',
    '$2a$10$CeHl4PCY4WlDhlEsGbqaFebcH69LJyTDJVqyLipj5ZOZyy.wLrgTm', -- it's a hash for "j32YLvc6Ls" password
    'ROLE_VIEWER'::role_type
), (
    '43b8a718-c746-4efe-a8e4-719a87ed7de5',
    'editor@deal.management',
    '$2a$10$CeHl4PCY4WlDhlEsGbqaFebcH69LJyTDJVqyLipj5ZOZyy.wLrgTm', -- it's a hash for "j32YLvc6Ls" password
    'ROLE_EDITOR'::role_type
), (
    '34580dfa-6919-44a1-8e38-718b3a29e2fd',
    'supervisor@deal.management',
    '$2a$10$CeHl4PCY4WlDhlEsGbqaFebcH69LJyTDJVqyLipj5ZOZyy.wLrgTm', -- it's a hash for "j32YLvc6Ls" password
    'ROLE_SUPERVISOR'::role_type
);
