-- Terminate all database connections
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'comments_db';

-- Drop database
DROP DATABASE "comments_db";

-- Drop owner
DROP ROLE "comments_user";
