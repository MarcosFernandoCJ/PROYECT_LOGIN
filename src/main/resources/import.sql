

-- Departmentos de carreras
INSERT INTO departments (name) VALUES ('Electricidad y Electrónica');
INSERT INTO departments (name) VALUES ('Gestión y Producción');
INSERT INTO departments (name) VALUES ('Mecánica y Aviación');
INSERT INTO departments (name) VALUES ('Minería, Procesos Químicos y Metalúrgicos');
INSERT INTO departments (name) VALUES ('Seguridad y Salud en el Trabajo');
INSERT INTO departments (name) VALUES ('Tecnología Digital');


-- Carreras para el Departamento 1
INSERT INTO careers (name, department_id) VALUES ('Mecatrónica Industrial', 1);
INSERT INTO careers (name, department_id) VALUES ('Electricidad Industrial con mención en Sistemas', 1);
INSERT INTO careers (name, department_id) VALUES ('Electrónica y Automatización Industrial', 1);
INSERT INTO careers (name, department_id) VALUES ('Electricidad y Electrónica', 1);
INSERT INTO careers (name, department_id) VALUES ('Tecnología Mecánica Eléctrica', 1);

-- Carreras para el Departamento 2
INSERT INTO careers (name, department_id) VALUES ('Logística Digital Integrada', 2);
INSERT INTO careers (name, department_id) VALUES ('Administración y Emprendimiento de Negocios', 2);
INSERT INTO careers (name, department_id) VALUES ('Marketing Digital Analítico', 2);
INSERT INTO careers (name, department_id) VALUES ('Diseño Industrial', 2);
INSERT INTO careers (name, department_id) VALUES ('Tecnología de la Producción', 2);
INSERT INTO careers (name, department_id) VALUES ('Producción y Gestión Industrial', 2);

-- Carreras para el Departamento 3
INSERT INTO careers (name, department_id) VALUES ('Mantenimiento de Equipo Pesado', 3);
INSERT INTO careers (name, department_id) VALUES ('Mecatrónica y Gestión Automotriz', 3);
INSERT INTO careers (name, department_id) VALUES ('Gestión y Mantenimiento de Maquinaria Pesada', 3);
INSERT INTO careers (name, department_id) VALUES ('Aviónica y Mecánica Aeronáutica', 3);
INSERT INTO careers (name, department_id) VALUES ('Mantenimiento y Gestión de Plantas Industriales', 3);
INSERT INTO careers (name, department_id) VALUES ('Tecnología Mecánica Eléctrica', 3);

-- Carreras para el Departamento 4
INSERT INTO careers (name, department_id) VALUES ('Topografía y Geomática', 4);
INSERT INTO careers (name, department_id) VALUES ('Procesos Químicos y Metalúrgicos', 4);
INSERT INTO careers (name, department_id) VALUES ('Operaciones Mineras', 4);
INSERT INTO careers (name, department_id) VALUES ('Operación de Plantas de Procesamiento de Minerales', 4);

-- Carreras para el Departamento 5
INSERT INTO careers (name, department_id) VALUES ('Gestión de Seguridad y Salud en el Trabajo', 5);

-- Carreras para el Departamento 6
INSERT INTO careers (name, department_id) VALUES ('Modelado y Animación Digital', 6);
INSERT INTO careers (name, department_id) VALUES ('Ciberseguridad y Auditoría Informática', 6);
INSERT INTO careers (name, department_id) VALUES ('Diseño y Desarrollo de Software', 6);
INSERT INTO careers (name, department_id) VALUES ('Diseño y Desarrollo de Simuladores y Videojuegos', 6);
INSERT INTO careers (name, department_id) VALUES ('Administración de Redes y Comunicaciones', 6);
INSERT INTO careers (name, department_id) VALUES ('Big Data y Ciencia de Datos', 6);

-- Roles para usuarios
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_ORGANIZADOR');
INSERT INTO roles (name) VALUES ('ROLE_JURADO');
