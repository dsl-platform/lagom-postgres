-- Create database owner
CREATE ROLE "comments_user" PASSWORD 'comments_pass' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT LOGIN;

-- Create database
CREATE DATABASE "comments_db" OWNER "comments_user" ENCODING 'utf8' TEMPLATE "template1";
