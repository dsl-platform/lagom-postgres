-- Create database owner
CREATE ROLE "storage_user" PASSWORD 'storage_pass' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN;

-- Create database
CREATE DATABASE "storage_db" OWNER "storage_user" ENCODING 'utf8' TEMPLATE "template1";
