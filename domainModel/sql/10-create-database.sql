-- Create database owner
CREATE ROLE "wondersuser" PASSWORD 'wonderspass' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN;

-- Create database
CREATE DATABASE "wondersdb" OWNER "wondersuser" ENCODING 'utf8' TEMPLATE "template1";
