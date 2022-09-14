SET SCHEMA dbo;

INSERT INTO flower (code, name) VALUES
    ('R12', 'Roses'),
    ('L09', 'Lilies'),
    ('T58', 'Tulips');

INSERT INTO bundle (flower_code, size, price) VALUES
    ('R12', 5, 699),
    ('R12', 10, 1299),
    ('L09', 3, 995),
    ('L09', 6, 1695),
    ('L09', 9, 2495),
    ('T58', 3, 595),
    ('T58', 5, 995),
    ('T58', 9, 1699);