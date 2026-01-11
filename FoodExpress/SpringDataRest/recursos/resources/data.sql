-- =========================
-- AUTHORS
-- =========================
INSERT INTO authors (id, full_name) VALUES (1, 'Ursula K. Le Guin');
INSERT INTO authors (id, full_name) VALUES (2, 'Isaac Asimov');
INSERT INTO authors (id, full_name) VALUES (3, 'Frank Herbert');

-- =========================
-- BOOKS
-- =========================
INSERT INTO books (id, title, isbn, author_id) VALUES
                                                   (1, 'A Wizard of Earthsea', 'ISBN-001', 1),
                                                   (2, 'The Tombs of Atuan', 'ISBN-002', 1),
                                                   (3, 'Foundation', 'ISBN-003', 2),
                                                   (4, 'Foundation and Empire', 'ISBN-004', 2),
                                                   (5, 'Dune', 'ISBN-005', 3);

-- =========================
-- MEMBERS
-- =========================
INSERT INTO members (id, name, email) VALUES
                                          (1, 'Ana Ruiz', 'ana@demo.com'),
                                          (2, 'Luis Pérez', 'luis@demo.com'),
                                          (3, 'María Gómez', 'maria@demo.com');

-- =========================
-- LOANS
-- =========================
INSERT INTO loans (id, book_id, member_id, loan_date, due_date, returned_date) VALUES
                                                                                   (1, 3, 1, '2026-01-05', '2026-01-20', NULL),     -- Foundation prestado a Ana
                                                                                   (2, 5, 2, '2026-01-03', '2026-01-18', '2026-01-15'), -- Dune devuelto por Luis
                                                                                   (3, 1, 3, '2026-01-10', '2026-01-25', NULL);     -- Earthsea prestado a María
