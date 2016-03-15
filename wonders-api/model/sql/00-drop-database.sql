-- Terminate all database connections
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'wonders_db';

-- Drop database
DROP DATABASE "wonders_db";

-- Drop owner
DROP ROLE "wonders_user";
