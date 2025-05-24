CREATE DATABASE ssbd02;
CREATE USER ssbd02admin password 'adminP@ssw0rd';
\c ssbd02;
GRANT ALL ON SCHEMA public TO ssbd02admin;