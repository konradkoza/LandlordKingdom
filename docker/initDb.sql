CREATE DATABASE landlordkingdom;
CREATE USER landlordkingdomadmin password 'adminP@ssw0rd';
\c landlordkingdom;
GRANT ALL ON SCHEMA public TO landlordkingdomadmin;