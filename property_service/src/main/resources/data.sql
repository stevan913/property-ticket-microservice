INSERT INTO building (name)
VALUES
    ('Head Office'),
    ('Branch Office');

INSERT INTO tenant (name, email, building_id)
VALUES
    ('Mark', 'mark@test.com', 1),
    ('John', 'john@test.com', 1);