/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.storage.repositories;

public class ImageCacheRepository implements java.io.Closeable,
        org.revenj.patterns.SearchableRepository<worldwonders.storage.ImageCache>,
        org.revenj.postgres.BulkRepository<worldwonders.storage.ImageCache>,
        org.revenj.patterns.PersistableRepository<worldwonders.storage.ImageCache> {
    public ImageCacheRepository(
            final java.util.Optional<java.sql.Connection> transactionContext,
            final javax.sql.DataSource dataSource,
            final org.revenj.postgres.QueryProvider queryProvider,
            final worldwonders.storage.converters.ImageCacheConverter converter,
            final org.revenj.patterns.ServiceLocator locator) {
        this.transactionContext = transactionContext;
        this.dataSource = dataSource;
        this.queryProvider = queryProvider;
        this.transactionConnection = transactionContext.orElse(null);
        this.converter = converter;
        this.locator = locator;
    }

    private final java.util.Optional<java.sql.Connection> transactionContext;
    private final javax.sql.DataSource dataSource;
    private final org.revenj.postgres.QueryProvider queryProvider;
    private final java.sql.Connection transactionConnection;
    private final worldwonders.storage.converters.ImageCacheConverter converter;
    private final org.revenj.patterns.ServiceLocator locator;

    private java.sql.Connection getConnection() {
        if (transactionConnection != null) return transactionConnection;
        try {
            return dataSource.getConnection();
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void releaseConnection(java.sql.Connection connection) {
        if (this.transactionConnection != null) return;
        try {
            connection.close();
        } catch (java.sql.SQLException ignore) {}
    }

    public ImageCacheRepository(org.revenj.patterns.ServiceLocator locator) {
        this(locator.tryResolve(java.sql.Connection.class), locator.resolve(javax.sql.DataSource.class), locator
                .resolve(org.revenj.postgres.QueryProvider.class), locator
                .resolve(worldwonders.storage.converters.ImageCacheConverter.class), locator);
    }

    public static org.revenj.patterns.Specification<worldwonders.storage.ImageCache> rewriteSpecificationToLambda(
            org.revenj.patterns.Specification<worldwonders.storage.ImageCache> filter) {
        return filter;
    }

    private static final boolean hasCustomSecurity = false;

    @Override
    public org.revenj.patterns.Query<worldwonders.storage.ImageCache> query(
            org.revenj.patterns.Specification<worldwonders.storage.ImageCache> filter) {
        org.revenj.patterns.Query<worldwonders.storage.ImageCache> query = queryProvider.query(transactionConnection,
                locator, worldwonders.storage.ImageCache.class);
        if (filter != null) {
            query = query.filter(rewriteSpecificationToLambda(filter));
        }

        return query;
    }

    private java.util.List<worldwonders.storage.ImageCache> readFromDb(java.sql.PreparedStatement statement, java.util.List<worldwonders.storage.ImageCache> result) throws java.sql.SQLException, java.io.IOException {
        try (java.sql.ResultSet rs = statement.executeQuery();
            org.revenj.postgres.PostgresReader reader = org.revenj.postgres.PostgresReader.create(locator)) {
            while (rs.next()) {
                reader.process(rs.getString(1));
                result.add(converter.from(reader));
            }
        }

        return result;
    }

    @Override
    public java.util.List<worldwonders.storage.ImageCache> search(org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification, Integer limit, Integer offset) {
        final String selectType = "SELECT it";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql;
            if (specification == null) {
                sql = "SELECT r FROM \"storage\".\"ImageCache_entity\" r";
            }
            else {
                org.revenj.patterns.Query<worldwonders.storage.ImageCache> query = query(specification);
                if (offset != null) {
                    query = query.skip(offset);
                }
                if (limit != null) {
                    query = query.limit(limit);
                }
                try {
                    return query.list();
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (limit != null) {
                sql += " LIMIT " + Integer.toString(limit);
            }
            if (offset != null) {
                sql += " OFFSET " + Integer.toString(offset);
            }
            try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
                applyFilters.accept(statement);
                return readFromDb(statement, new java.util.ArrayList<>());
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.List<worldwonders.storage.ImageCache>> search(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification, Integer limit, Integer offset) {
        String selectType = "SELECT array_agg(_r) FROM (SELECT _it as _r";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("SELECT array_agg(_r) FROM (SELECT _r FROM \"storage\".\"ImageCache_entity\" _r");
        }

        else {
            sb.append("SELECT 0");
            return (rs, ind) -> search(specification, limit, offset);
        }
        if (limit != null && limit >= 0) {
            sb.append(" LIMIT ");
            sb.append(Integer.toString(limit));
        }
        if (offset != null && offset >= 0) {
            sb.append(" OFFSET ");
            sb.append(Integer.toString(offset));
        }
        sb.append(") _sq");
        return (rs, ind) -> {
            try {
                String res = rs.getString(ind);
                if (res == null || res.length() == 0 || res.length() == 2) {
                    return new java.util.ArrayList<>(0);
                }
                rdr.process(res);
                java.util.List<worldwonders.storage.ImageCache> result = org.revenj.postgres.converters.ArrayTuple.parse(rdr, 0, converter::from);

                return result;
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public long count(org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification) {
        final String selectType = "SELECT COUNT(*)";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql;
            if (specification == null) {
                sql = "SELECT COUNT(*) FROM \"storage\".\"ImageCache_entity\" r";
            }
            else {
                try {
                    return query(specification).count();
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (java.sql.PreparedStatement statement = connection.prepareStatement(sql)) {
                applyFilters.accept(statement);
                try (java.sql.ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    return rs.getLong(1);
                }
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, Long> count(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification) {
        String selectType = "SELECT count(*)";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("SELECT count(*) FROM \"storage\".\"ImageCache_entity\" r");
        }

        else {
            sb.append("SELECT 0");
            return (rs, ind) -> {
                try {
                    return query(specification).count();
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }
        return (rs, ind) -> {
            try {
                return rs.getLong(ind);
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public boolean exists(org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification) {
        final String selectType = "SELECT exists(SELECT *";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql = null;
            if (specification == null) {
                sql = "SELECT exists(SELECT * FROM \"storage\".\"ImageCache_entity\" r";
            }
            else {
                try {
                    return query(specification).any();
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (java.sql.PreparedStatement statement = connection.prepareStatement(sql + ")")) {
                applyFilters.accept(statement);
                try (java.sql.ResultSet rs = statement.executeQuery()) {
                    rs.next();
                    return rs.getBoolean(1);
                }
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, Boolean> exists(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.storage.ImageCache> specification) {
        String selectType = "exists(SELECT *";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("exists(SELECT * FROM \"storage\".\"ImageCache_entity\" r");
        }

        else {
            sb.append("SELECT 0");
            return (rs, ind) -> {
                try {
                    return query(specification).any();
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            };
        }
        return (rs, ind) -> {
            try {
                return rs.getBoolean(ind);
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public void close() throws java.io.IOException {}

    @Override
    public java.util.List<worldwonders.storage.ImageCache> find(String[] uris) {
        java.sql.Connection connection = getConnection();
        try (java.sql.Statement statement = connection.createStatement();
            org.revenj.postgres.PostgresReader reader = org.revenj.postgres.PostgresReader.create(locator)) {
            java.util.List<worldwonders.storage.ImageCache> result = new java.util.ArrayList<>(uris.length);
            StringBuilder sb = new StringBuilder("SELECT _r FROM \"storage\".\"ImageCache_entity\" _r WHERE _r.\"url\" IN (");
            org.revenj.postgres.PostgresWriter.writeSimpleUriList(sb, uris);
            sb.append(")");
            statement.setEscapeProcessing(false);
            try (java.sql.ResultSet rs = statement.executeQuery(sb.toString())) {
                while (rs.next()) {
                    reader.process(rs.getString(1));
                    result.add(converter.from(reader));
                }
            }

            return result;
        } catch (java.sql.SQLException | java.io.IOException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.List<worldwonders.storage.ImageCache>> find(org.revenj.postgres.BulkReaderQuery query, String[] uris) {
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        StringBuilder sb = query.getBuilder();
        if (uris == null || uris.length == 0) {
            sb.append("SELECT 0");
            return (rs, ind) -> new java.util.ArrayList<>(0);
        }
        sb.append("SELECT array_agg(_r) FROM \"storage\".\"ImageCache_entity\" _r WHERE _r.\"url\" IN (");
        org.revenj.postgres.PostgresWriter.writeSimpleUriList(sb, uris);
        sb.append(")");
        return (rs, ind) -> {
            try {
                String res = rs.getString(ind);
                if (res == null || res.length() == 0 || res.length() == 2) {
                    return new java.util.ArrayList<>(0);
                }
                rdr.process(res);
                java.util.List<worldwonders.storage.ImageCache> result = org.revenj.postgres.converters.ArrayTuple.parse(rdr, 0, converter::from);

                return result;
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public java.util.Optional<worldwonders.storage.ImageCache> find(String uri) {
        java.sql.Connection connection = getConnection();
        try (java.sql.Statement statement = connection.createStatement();
            org.revenj.postgres.PostgresReader reader = org.revenj.postgres.PostgresReader.create(locator)) {
            StringBuilder sb = new StringBuilder("SELECT _r FROM \"storage\".\"ImageCache_entity\" _r WHERE _r.\"url\" = ");
            org.revenj.postgres.PostgresWriter.writeSimpleUri(sb, uri);
            statement.setEscapeProcessing(false);
            worldwonders.storage.ImageCache instance;
            try (java.sql.ResultSet rs = statement.executeQuery(sb.toString())) {
                if (rs.next()) {
                    reader.process(rs.getString(1));
                    instance = converter.from(reader);
                } else {
                    return java.util.Optional.empty();
                }
            }
            if (!hasCustomSecurity) return java.util.Optional.of(instance);
            java.util.List<worldwonders.storage.ImageCache> result = new java.util.ArrayList<>(1);
            result.add(instance);

            if (result.size() == 1) {
                java.util.Optional.of(instance);
            }
            return java.util.Optional.empty();
        } catch (java.sql.SQLException | java.io.IOException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.Optional<worldwonders.storage.ImageCache>> find(org.revenj.postgres.BulkReaderQuery query, String uri) {
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        StringBuilder sb = query.getBuilder();
        if (uri == null) {
            sb.append("SELECT 0");
            return (rs, ind) -> java.util.Optional.empty();
        }
        sb.append("SELECT _r FROM \"storage\".\"ImageCache_entity\" _r WHERE _r.\"url\" = ");
        org.revenj.postgres.PostgresWriter.writeSimpleUri(sb, uri);
        return (rs, ind) -> {
            try {
                String res = rs.getString(ind);
                if (res == null) {
                    return java.util.Optional.empty();
                }
                rdr.process(res);
                worldwonders.storage.ImageCache instance = converter.from(rdr);
                if (!hasCustomSecurity) return java.util.Optional.of(instance);
                java.util.List<worldwonders.storage.ImageCache> result = new java.util.ArrayList<>(1);
                result.add(instance);

                if (result.size() == 1) {
                    java.util.Optional.of(instance);
                }
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
            return java.util.Optional.empty();
        };
    }

    public static void __setupPersist(
            java.util.function.BiConsumer<java.util.Collection<worldwonders.storage.ImageCache>, java.util.Map.Entry<org.revenj.postgres.PostgresWriter, org.revenj.patterns.ServiceLocator>> insert,
            java.util.function.BiConsumer<java.util.Map.Entry<java.util.List<worldwonders.storage.ImageCache>, java.util.List<worldwonders.storage.ImageCache>>, java.util.Map.Entry<org.revenj.postgres.PostgresWriter, org.revenj.patterns.ServiceLocator>> update,
            java.util.function.Consumer<java.util.Collection<worldwonders.storage.ImageCache>> delete,
            java.util.function.Function<worldwonders.storage.ImageCache, worldwonders.storage.ImageCache> track) {
        insertLoop = insert;
        updateLoop = update;
        deleteLoop = delete;
        trackChanges = track;
    }

    private static java.util.function.BiConsumer<java.util.Collection<worldwonders.storage.ImageCache>, java.util.Map.Entry<org.revenj.postgres.PostgresWriter, org.revenj.patterns.ServiceLocator>> insertLoop;
    private static java.util.function.BiConsumer<java.util.Map.Entry<java.util.List<worldwonders.storage.ImageCache>, java.util.List<worldwonders.storage.ImageCache>>, java.util.Map.Entry<org.revenj.postgres.PostgresWriter, org.revenj.patterns.ServiceLocator>> updateLoop;
    private static java.util.function.Consumer<java.util.Collection<worldwonders.storage.ImageCache>> deleteLoop;
    private static java.util.function.Function<worldwonders.storage.ImageCache, worldwonders.storage.ImageCache> trackChanges;

    private static final String[] EMPTY_URI = new String[0];

    @Override
    public String[] persist(
            java.util.Collection<worldwonders.storage.ImageCache> insert,
            java.util.Collection<java.util.Map.Entry<worldwonders.storage.ImageCache, worldwonders.storage.ImageCache>> update,
            java.util.Collection<worldwonders.storage.ImageCache> delete) throws java.io.IOException {
        java.sql.Connection connection = getConnection();
        try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT \"storage\".\"persist_ImageCache\"(?, ?, ?, ?)");
            org.revenj.postgres.PostgresWriter sw = org.revenj.postgres.PostgresWriter.create()) {
            String[] result;
            if (insert != null && !insert.isEmpty()) {
                insertLoop.accept(insert, new java.util.AbstractMap.SimpleEntry<>(sw, locator));
                sw.reset();
                org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(insert, converter::to);
                org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
                pgo.setType("\"storage\".\"ImageCache_entity\"[]");
                sw.reset();
                tuple.buildTuple(sw, false);
                pgo.setValue(sw.toString());
                statement.setObject(1, pgo);
                result = new String[insert.size()];
                int i = 0;
                for (worldwonders.storage.ImageCache it : insert) {
                    result[i++] = it.getURI();
                    trackChanges.apply(it);
                }
            } else {
                statement.setArray(1, null);
                result = EMPTY_URI;
            }
            if (update != null && !update.isEmpty()) {
                java.util.List<worldwonders.storage.ImageCache> oldUpdate = new java.util.ArrayList<>(update.size());
                java.util.List<worldwonders.storage.ImageCache> newUpdate = new java.util.ArrayList<>(update.size());
                java.util.Map<String, Integer> missing = new java.util.HashMap<>();
                int cnt = 0;
                for (java.util.Map.Entry<worldwonders.storage.ImageCache, worldwonders.storage.ImageCache> it : update) {
                    worldwonders.storage.ImageCache oldValue = trackChanges.apply(it.getValue());
                    if (it.getKey() != null) {
                        oldValue = it.getKey();
                    }
                    oldUpdate.add(oldValue);
                    if (oldValue == null) {
                        missing.put(it.getValue().getURI(), cnt);
                    }
                    newUpdate.add(it.getValue());
                    cnt++;
                }
                if (!missing.isEmpty()) {
                    java.util.List<worldwonders.storage.ImageCache> found = find(missing.keySet().toArray(new String[missing.size()]));
                    for (worldwonders.storage.ImageCache it : found) {
                        oldUpdate.set(missing.get(it.getURI()), it);
                    }
                }
                updateLoop.accept(new java.util.AbstractMap.SimpleEntry<>(oldUpdate, newUpdate), new java.util.AbstractMap.SimpleEntry<>(sw, locator));
                org.revenj.postgres.converters.PostgresTuple tupleOld = org.revenj.postgres.converters.ArrayTuple.create(oldUpdate, converter::to);
                org.revenj.postgres.converters.PostgresTuple tupleNew = org.revenj.postgres.converters.ArrayTuple.create(newUpdate, converter::to);
                org.postgresql.util.PGobject pgOld = new org.postgresql.util.PGobject();
                org.postgresql.util.PGobject pgNew = new org.postgresql.util.PGobject();
                pgOld.setType("\"storage\".\"ImageCache_entity\"[]");
                pgNew.setType("\"storage\".\"ImageCache_entity\"[]");
                tupleOld.buildTuple(sw, false);
                pgOld.setValue(sw.toString());
                sw.reset();
                tupleNew.buildTuple(sw, false);
                pgNew.setValue(sw.toString());
                sw.reset();
                statement.setObject(2, pgOld);
                statement.setObject(3, pgNew);
            } else {
                statement.setArray(2, null);
                statement.setArray(3, null);
            }
            if (delete != null && !delete.isEmpty()) {
                deleteLoop.accept(delete);
                org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(delete, converter::to);
                org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
                pgo.setType("\"storage\".\"ImageCache_entity\"[]");
                tuple.buildTuple(sw, false);
                pgo.setValue(sw.toString());
                statement.setObject(4, pgo);
            } else {
                statement.setArray(4, null);
            }
            try (java.sql.ResultSet rs = statement.executeQuery()) {
                rs.next();
                String message = rs.getString(1);
                if (message != null) throw new java.io.IOException(message);
            }
            return result;
        } catch (java.sql.SQLException e) {
            throw new java.io.IOException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public String insert(worldwonders.storage.ImageCache item) throws java.io.IOException {
        java.sql.Connection connection = getConnection();
        try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT \"storage\".\"insert_ImageCache\"(ARRAY[?])");
            org.revenj.postgres.PostgresWriter sw = org.revenj.postgres.PostgresWriter.create()) {
            java.util.List<worldwonders.storage.ImageCache> insert = java.util.Collections.singletonList(item);
            if (insertLoop != null) insertLoop.accept(insert, new java.util.AbstractMap.SimpleEntry<>(sw, locator));
            sw.reset();
            org.revenj.postgres.converters.PostgresTuple tuple = converter.to(item);
            org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
            pgo.setType("\"storage\".\"ImageCache_entity\"");
            sw.reset();
            tuple.buildTuple(sw, false);
            pgo.setValue(sw.toString());
            statement.setObject(1, pgo);
            statement.execute();
            trackChanges.apply(item);
            return item.getURI();
        } catch (java.sql.SQLException e) {
            throw new java.io.IOException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(worldwonders.storage.ImageCache oldItem, worldwonders.storage.ImageCache newItem) throws java.io.IOException {
        java.sql.Connection connection = getConnection();
        try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT \"storage\".\"update_ImageCache\"(ARRAY[?], ARRAY[?])");
             org.revenj.postgres.PostgresWriter sw = org.revenj.postgres.PostgresWriter.create()) {
            if (oldItem == null) oldItem = trackChanges.apply(newItem);
            else trackChanges.apply(newItem);
            if (oldItem == null) oldItem = find(newItem.getURI()).get();
            java.util.List<worldwonders.storage.ImageCache> oldUpdate = java.util.Collections.singletonList(oldItem);
            java.util.List<worldwonders.storage.ImageCache> newUpdate = java.util.Collections.singletonList(newItem);
            if (updateLoop != null) updateLoop.accept(new java.util.AbstractMap.SimpleEntry<>(oldUpdate, newUpdate), new java.util.AbstractMap.SimpleEntry<>(sw, locator));
            org.revenj.postgres.converters.PostgresTuple tupleOld = converter.to(oldItem);
            org.revenj.postgres.converters.PostgresTuple tupleNew = converter.to(newItem);
            org.postgresql.util.PGobject pgOld = new org.postgresql.util.PGobject();
            org.postgresql.util.PGobject pgNew = new org.postgresql.util.PGobject();
            pgOld.setType("\"storage\".\"ImageCache_entity\"");
            pgNew.setType("\"storage\".\"ImageCache_entity\"");
            tupleOld.buildTuple(sw, false);
            pgOld.setValue(sw.toString());
            sw.reset();
            tupleNew.buildTuple(sw, false);
            pgNew.setValue(sw.toString());
            statement.setObject(1, pgOld);
            statement.setObject(2, pgNew);
            try (java.sql.ResultSet rs = statement.executeQuery()) {
                rs.next();
                String message = rs.getString(1);
                if (message != null) throw new java.io.IOException(message);
            }
        } catch (java.sql.SQLException e) {
            throw new java.io.IOException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
