/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.comments.repositories;

public class CommentRepository implements java.io.Closeable,
        org.revenj.patterns.SearchableRepository<worldwonders.comments.Comment>,
        org.revenj.patterns.DomainEventStore<worldwonders.comments.Comment>,
        org.revenj.postgres.BulkRepository<worldwonders.comments.Comment> {
    public CommentRepository(
            final java.util.Optional<java.sql.Connection> transactionContext,
            final javax.sql.DataSource dataSource,
            final org.revenj.postgres.QueryProvider queryProvider,
            final worldwonders.comments.converters.CommentConverter converter,
            final org.revenj.patterns.ServiceLocator locator,
            final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment>[] singleHandlers,
            final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]>[] collectionHandlers,
            final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>>[] lazyHandlers,
            final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>>[] collectionLazyHandlers) {
        this.transactionContext = transactionContext;
        this.dataSource = dataSource;
        this.queryProvider = queryProvider;
        this.transactionConnection = transactionContext.orElse(null);
        this.converter = converter;
        this.locator = locator;
        this.singleHandlers = singleHandlers;
        this.collectionHandlers = collectionHandlers;
        this.lazyHandlers = lazyHandlers;
        this.collectionLazyHandlers = collectionLazyHandlers;
    }

    private final java.util.Optional<java.sql.Connection> transactionContext;
    private final javax.sql.DataSource dataSource;
    private final org.revenj.postgres.QueryProvider queryProvider;
    private final java.sql.Connection transactionConnection;
    private final worldwonders.comments.converters.CommentConverter converter;
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

    public CommentRepository(org.revenj.patterns.ServiceLocator locator) {
        this(locator.tryResolve(java.sql.Connection.class), locator.resolve(javax.sql.DataSource.class), locator
                .resolve(org.revenj.postgres.QueryProvider.class), locator
                .resolve(worldwonders.comments.converters.CommentConverter.class), locator);
    }

    public static org.revenj.patterns.Specification<worldwonders.comments.Comment> rewriteSpecificationToLambda(
            org.revenj.patterns.Specification<worldwonders.comments.Comment> filter) {
        if (filter instanceof worldwonders.comments.Comment.findByTopic) { return ((worldwonders.comments.Comment.findByTopic) filter)
                .rewriteLambda(); }
        return filter;
    }

    private static final boolean hasCustomSecurity = false;

    @Override
    public org.revenj.patterns.Query<worldwonders.comments.Comment> query(
            org.revenj.patterns.Specification<worldwonders.comments.Comment> filter) {
        org.revenj.patterns.Query<worldwonders.comments.Comment> query = queryProvider.query(transactionConnection,
                locator, worldwonders.comments.Comment.class);
        if (filter != null) {
            query = query.filter(rewriteSpecificationToLambda(filter));
        }

        return query;
    }

    private java.util.List<worldwonders.comments.Comment> readFromDb(java.sql.PreparedStatement statement, java.util.List<worldwonders.comments.Comment> result) throws java.sql.SQLException, java.io.IOException {
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
    public java.util.List<worldwonders.comments.Comment> search(org.revenj.patterns.Specification<worldwonders.comments.Comment> specification, Integer limit, Integer offset) {
        final String selectType = "SELECT it";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql;
            if (specification == null) {
                sql = "SELECT r FROM \"comments\".\"Comment_event\" r";
            }
            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sql = selectType + " FROM \"comments\".\"Comment.findByTopic\"(?) it";

                applyFilters = applyFilters.andThen(ps -> {
                    try {
                        ps.setString(1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else {
                org.revenj.patterns.Query<worldwonders.comments.Comment> query = query(specification);
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

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.List<worldwonders.comments.Comment>> search(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.comments.Comment> specification, Integer limit, Integer offset) {
        String selectType = "SELECT array_agg(_r) FROM (SELECT _it as _r";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("SELECT array_agg(_r) FROM (SELECT _r FROM \"comments\".\"Comment_event\" _r");
        }

            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sb.append(selectType);
                sb.append(" FROM \"comments\".\"Comment.findByTopic\"(?) it");

                query.addArgument(ps -> {
                    try {
                        ps.setString(index + 1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
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
                java.util.List<worldwonders.comments.Comment> result = org.revenj.postgres.converters.ArrayTuple.parse(rdr, 0, converter::from);

                return result;
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public long count(org.revenj.patterns.Specification<worldwonders.comments.Comment> specification) {
        final String selectType = "SELECT COUNT(*)";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql;
            if (specification == null) {
                sql = "SELECT COUNT(*) FROM \"comments\".\"Comment_event\" r";
            }
            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sql = selectType + " FROM \"comments\".\"Comment.findByTopic\"(?) it";

                applyFilters = applyFilters.andThen(ps -> {
                    try {
                        ps.setString(1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
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

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, Long> count(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.comments.Comment> specification) {
        String selectType = "SELECT count(*)";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("SELECT count(*) FROM \"comments\".\"Comment_event\" r");
        }

            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sb.append(selectType);
                sb.append(" FROM \"comments\".\"Comment.findByTopic\"(?) it");

                query.addArgument(ps -> {
                    try {
                        ps.setString(index + 1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
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
    public boolean exists(org.revenj.patterns.Specification<worldwonders.comments.Comment> specification) {
        final String selectType = "SELECT exists(SELECT *";
        java.util.function.Consumer<java.sql.PreparedStatement> applyFilters = ps -> {};
        java.sql.Connection connection = getConnection();
        try (org.revenj.postgres.PostgresWriter pgWriter = org.revenj.postgres.PostgresWriter.create()) {
            String sql = null;
            if (specification == null) {
                sql = "SELECT exists(SELECT * FROM \"comments\".\"Comment_event\" r";
            }
            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sql = selectType + " FROM \"comments\".\"Comment.findByTopic\"(?) it";

                applyFilters = applyFilters.andThen(ps -> {
                    try {
                        ps.setString(1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
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

    public java.util.function.BiFunction<java.sql.ResultSet, Integer, Boolean> exists(org.revenj.postgres.BulkReaderQuery query, org.revenj.patterns.Specification<worldwonders.comments.Comment> specification) {
        String selectType = "exists(SELECT *";
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter pgWriter = query.getWriter();
        int index = query.getArgumentIndex();
        StringBuilder sb = query.getBuilder();
        if (specification == null) {
            sb.append("exists(SELECT * FROM \"comments\".\"Comment_event\" r");
        }

            else if (specification instanceof worldwonders.comments.Comment.findByTopic) {
                worldwonders.comments.Comment.findByTopic spec = (worldwonders.comments.Comment.findByTopic)specification;
                sb.append(selectType);
                sb.append(" FROM \"comments\".\"Comment.findByTopic\"(?) it");

                query.addArgument(ps -> {
                    try {
                        ps.setString(index + 1, spec.getTopic());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
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

    private final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment>[] singleHandlers;
    private final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]>[] collectionHandlers;
    private final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>>[] lazyHandlers;
    private final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>>[] collectionLazyHandlers;

    public CommentRepository(
            final java.util.Optional<java.sql.Connection> transactionContext,
            final javax.sql.DataSource dataSource,
            final org.revenj.postgres.QueryProvider queryProvider,
            final worldwonders.comments.converters.CommentConverter converter,
            final org.revenj.patterns.ServiceLocator locator) {
        this(
                transactionContext,
                dataSource,
                queryProvider,
                converter,
                locator,
                transactionContext.isPresent()
                        ? new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment>[]>() {}
                                .resolve(locator) : null,
                transactionContext.isPresent()
                        ? new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]>[]>() {}
                                .resolve(locator) : null,
                transactionContext.isPresent()
                        ? new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>>[]>() {}
                                .resolve(locator) : null,
                transactionContext.isPresent()
                        ? new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>>[]>() {}
                                .resolve(locator) : null);
    }

    @Override
    public java.util.List<worldwonders.comments.Comment> find(String[] uris) {
        long[] ids = new long[uris.length];
        for (int i = 0; i < uris.length; i++) {
            ids[i] = Long.parseLong(uris[i]);
        }
        java.sql.Connection connection = getConnection();
        try {
            return find(ids, connection);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public java.util.Optional<worldwonders.comments.Comment> find(String uri) {
        long id;
        try {
            id = Long.parseLong(uri);
        } catch (Exception ignore) {
            return java.util.Optional.empty();
        }
        java.sql.Connection connection = getConnection();
        try {
            return find(id, connection);
        } finally {
            releaseConnection(connection);
        }
    }

    public java.util.List<worldwonders.comments.Comment> find(long[] ids, java.sql.Connection connection) {
        try (java.sql.PreparedStatement statement = connection.prepareStatement("SELECT r FROM \"comments\".\"Comment_event\" r WHERE r._event_id = ANY(?)")) {
            Object[] arg = new Object[ids.length];
            for(int i = 0; i < ids.length; i++) {
                arg[i] = ids[i];
            }
            statement.setArray(1, connection.createArrayOf("int8", arg));
            return readFromDb(statement, new java.util.ArrayList<>(ids.length));
        } catch (java.sql.SQLException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    public java.util.Optional<worldwonders.comments.Comment> find(long id, java.sql.Connection connection) {
        try (java.sql.PreparedStatement statement = connection.prepareStatement("SELECT r FROM \"comments\".\"Comment_event\" r WHERE r._event_id = ?");
            org.revenj.postgres.PostgresReader reader = org.revenj.postgres.PostgresReader.create(locator)) {
            statement.setLong(1, id);
            worldwonders.comments.Comment instance;
            try (java.sql.ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    reader.process(rs.getString(1));
                    instance = converter.from(reader);
                } else {
                    return java.util.Optional.empty();
                }
            }
            if (!hasCustomSecurity) return java.util.Optional.of(instance);
            java.util.List<worldwonders.comments.Comment> result = new java.util.ArrayList<>(1);
            result.add(instance);

            if (result.size() == 1) {
                java.util.Optional.of(instance);
            }
            return java.util.Optional.empty();
        } catch (java.sql.SQLException | java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.Optional<worldwonders.comments.Comment>> find(org.revenj.postgres.BulkReaderQuery query, String uri) {
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        StringBuilder sb = query.getBuilder();
        int index = query.getArgumentIndex();
        if (uri == null) {
            sb.append("SELECT 0");
            return (rs, ind) -> java.util.Optional.empty();
        }
        final long id;
        try {
            id = Long.parseLong(uri);
        } catch (java.lang.Exception e) {
            sb.append("SELECT 0");
            return (rs, ind) -> java.util.Optional.empty();
        }
        sb.append("SELECT _r FROM \"comments\".\"Comment_event\" _r WHERE _r._event_id = ?");
        query.addArgument(ps -> {
            try {
                ps.setLong(index, id);
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return (rs, ind) ->
        {
            try {
                String res = rs.getString(ind);
                if (res == null) {
                    return java.util.Optional.empty();
                }
                rdr.process(res);
                worldwonders.comments.Comment instance = converter.from(rdr);
                if (!hasCustomSecurity) return java.util.Optional.of(instance);
                java.util.List<worldwonders.comments.Comment> result = new java.util.ArrayList<>(1);
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

    @Override
    public java.util.function.BiFunction<java.sql.ResultSet, Integer, java.util.List<worldwonders.comments.Comment>> find(org.revenj.postgres.BulkReaderQuery query, String[] uris) {
        final org.revenj.postgres.PostgresReader rdr = query.getReader();
        final org.revenj.postgres.PostgresWriter writer = query.getWriter();
        StringBuilder sb = query.getBuilder();
        int index = query.getArgumentIndex();
        if (uris == null || uris.length == 0) {
            sb.append("SELECT 0");
            return (rs, ind) -> new java.util.ArrayList<>(0);
        }
        sb.append("SELECT array_agg(_r) FROM \"comments\".\"Comment_event\" _r WHERE _r._event_id = ANY(?)");
        final long[] ids = new long[uris.length];
        for (int i = 0; i < uris.length; i++) {
            try {
                ids[i] = Long.parseLong(uris[i]);
            } catch (java.lang.Exception e) {
                throw new java.lang.IllegalArgumentException("Invalid URI value found: " + uris[i], e);
            }
        }
        query.addArgument(ps -> {
            try {
                org.postgresql.util.PGobject arr = new org.postgresql.util.PGobject();
                arr.setType("int8[]");
                writer.reset();
                org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(ids, org.revenj.postgres.converters.LongConverter::toTuple);
                tuple.buildTuple(writer, false);
                arr.setValue(writer.toString());
                ps.setObject(index, arr);
            } catch (java.sql.SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return (rs, ind) ->
        {
            try {
                String res = rs.getString(ind);
                if (res == null || res.length() == 0 || res.length() == 2) {
                    return new java.util.ArrayList<>(0);
                }
                rdr.process(res);
                java.util.List<worldwonders.comments.Comment> result = org.revenj.postgres.converters.ArrayTuple.parse(rdr, 0, converter::from);

                return result;
            } catch (java.sql.SQLException | java.io.IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private org.revenj.extensibility.Container executeBefore(
            java.sql.Connection connection,
            worldwonders.comments.Comment[] events) {
        final org.revenj.extensibility.Container context;
        final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment>[] sh;
        final org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]>[] ch;
        if (transactionContext.isPresent()) {
            context = null;
            sh = singleHandlers;
            ch = collectionHandlers;
        } else {
            context = locator.resolve(org.revenj.extensibility.Container.class);
            context.registerInstance(java.sql.Connection.class, connection, false);
            sh = new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment>[]>() {}
                    .resolve(context);
            ch = new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]>[]>() {}
                    .resolve(context);
        }
        if (sh != null) {
            for (org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment> s : sh) {
                for (worldwonders.comments.Comment c : events) {
                    s.handle(c);
                }
            }
        }
        if (ch != null) {
            for (org.revenj.patterns.DomainEventHandler<worldwonders.comments.Comment[]> s : ch) {
                s.handle(events);
            }
        }
        return context;
    }

    private void executeAfter(org.revenj.patterns.ServiceLocator context, worldwonders.comments.Comment[] events) {
        final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>>[] sh;
        final org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>>[] ch;
        if (context == null) {
            sh = lazyHandlers;
            ch = collectionLazyHandlers;
        } else {
            sh = new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>>[]>(){}.resolve(context);
            ch = new org.revenj.patterns.Generic<org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>>[]>(){}.resolve(context);
        }
        if (sh != null) {
            for (org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment>> s : sh) {
                for (worldwonders.comments.Comment c : events) {
                    s.handle(() -> c);
                }
            }
        }
        if (ch != null) {
            for (org.revenj.patterns.DomainEventHandler<java.util.concurrent.Callable<worldwonders.comments.Comment[]>> s : ch) {
                s.handle(() -> events);
            }
        }
    }

    @Override
    public String[] submit(java.util.Collection<worldwonders.comments.Comment> domainEvents) {
        java.sql.Connection connection = getConnection();
        worldwonders.comments.Comment[] events = domainEvents.toArray(new worldwonders.comments.Comment[domainEvents.size()]);
        org.revenj.extensibility.Container context = executeBefore(connection, events);
        try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT \"URI\" FROM \"comments\".\"submit_Comment\"(?)");
            org.revenj.postgres.PostgresWriter sw = org.revenj.postgres.PostgresWriter.create()) {
            if (prepareEvents != null) prepareEvents.accept(domainEvents);
            String[] result = new String[events.length];
            org.revenj.postgres.converters.PostgresTuple tuple = org.revenj.postgres.converters.ArrayTuple.create(events, converter::to);
            org.postgresql.util.PGobject pgo = new org.postgresql.util.PGobject();
            pgo.setType("\"comments\".\"Comment_event\"[]");
            tuple.buildTuple(sw, false);
            pgo.setValue(sw.toString());
            statement.setObject(1, pgo);
            try (java.sql.ResultSet rs = statement.executeQuery()) {
                for (int i = 0; i < result.length; i++) {
                    rs.next();
                    result[i] = rs.getString(1);
                }
            }
            if (assignUris != null) assignUris.accept(domainEvents, result);
            executeAfter(context, events);
            return result;
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (context != null) {
                try { context.close(); }
                catch (Exception e) { e.printStackTrace(); }
            }
            releaseConnection(connection);
        }
    }

    public static void __configure(
            java.util.function.Consumer<java.util.Collection<worldwonders.comments.Comment>> prepare,
            java.util.function.BiConsumer<java.util.Collection<worldwonders.comments.Comment>, String[]> assign) {
        prepareEvents = prepare;
        assignUris = assign;
    }

    private static java.util.function.Consumer<java.util.Collection<worldwonders.comments.Comment>> prepareEvents;
    private static java.util.function.BiConsumer<java.util.Collection<worldwonders.comments.Comment>, String[]> assignUris;

    @Override
    public void mark(String[] uris) {
        java.sql.Connection connection = getConnection();
        try (java.sql.PreparedStatement statement = connection.prepareStatement("/*NO LOAD BALANCE*/SELECT \"comments\".\"mark_Comment\"(?)")) {
            Object[] ids = new Object[uris.length];
            for(int i = 0; i < uris.length; i++) {
                ids[i] = Long.parseLong(uris[i]);
            }
            statement.setArray(1, connection.createArrayOf("int8", ids));
            statement.executeUpdate();
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}
