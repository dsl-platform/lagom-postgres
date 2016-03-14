-- Terminate all database connections
SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = 'wondersdb';

-- Drop database
DROP DATABASE "wondersdb";

-- Drop owner
DROP ROLE "wondersuser";
