-- Terminate all database connections
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'storage_db';

-- Drop database
DROP DATABASE "storage_db";

-- Drop owner
DROP ROLE "storage_user";
