-- Create database owner
CREATE ROLE "wonders_user" PASSWORD 'wonders_pass' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN;

-- Create database
CREATE DATABASE "wonders_db" OWNER "wonders_user" ENCODING 'utf8' TEMPLATE "template1";
