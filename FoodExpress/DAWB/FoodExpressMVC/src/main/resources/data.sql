
-- ==========================================
-- INITIAL DATA FOR USERS, ROLES AND RELATIONS
-- ==========================================

-- ---- ROLES ----
INSERT INTO roles (name, description) VALUES
                                          ('ROLE_ADMIN', 'System administrator with full access'),
                                          ('ROLE_USER', 'Registered customer with standard permissions'),
                                          ('ROLE_DELIVERY', 'Delivery personnel with limited access');

-- ---- USERS ----
-- Password = "1234" (BCrypt-hashed)
-- You can generate new hashes with new BCryptPasswordEncoder().encode("1234")

INSERT INTO users (username, password, full_name, email) VALUES
                                                             ('admin', '$2a$10$sE.gGqPd76mGsgz9VUH1.OzX2k8cAGtQtUGvmUNw7dTXHDnI5rA2K', 'System Administrator', 'admin@foodexpress.com'),
                                                             ('john',  '$2a$10$sE.gGqPd76mGsgz9VUH1.OzX2k8cAGtQtUGvmUNw7dTXHDnI5rA2K', 'John Doe', 'john@foodexpress.com'),
                                                             ('emma',  '$2a$10$sE.gGqPd76mGsgz9VUH1.OzX2k8cAGtQtUGvmUNw7dTXHDnI5rA2K', 'Emma Stone', 'emma@foodexpress.com');

-- ---- USERS_ROLES ----
-- Because IDs are auto-generated, we must reference them dynamically.
-- H2 supports subqueries in INSERT for this purpose.

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'admin' AND r.name = 'ROLE_USER';

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'john' AND r.name = 'ROLE_USER';

INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'emma' AND r.name = 'ROLE_DELIVERY';