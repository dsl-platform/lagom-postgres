/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders;

public class Boot implements org.revenj.extensibility.SystemAspect {
    public static org.revenj.patterns.ServiceLocator configure(String jdbcUrl) throws java.io.IOException {
        java.util.Properties properties = new java.util.Properties();
        java.io.File revProps = new java.io.File("revenj.properties");
        if (revProps.exists() && revProps.isFile()) {
            properties.load(new java.io.FileReader(revProps));
        }
        return configure(jdbcUrl, properties);
    }

    public static org.revenj.patterns.ServiceLocator configure(String jdbcUrl, java.util.Properties properties)
            throws java.io.IOException {
        properties.setProperty("revenj.namespace", "worldwonders");
        org.postgresql.ds.PGPoolingDataSource dataSource = new org.postgresql.ds.PGPoolingDataSource();
        dataSource.setUrl(jdbcUrl);
        String user = properties.getProperty("user");
        String revUser = properties.getProperty("revenj.user");
        if (revUser != null && revUser.length() > 0) {
            dataSource.setUser(revUser);
        } else if (user != null && user.length() > 0) {
            dataSource.setUser(user);
        }
        String password = properties.getProperty("password");
        String revPassword = properties.getProperty("revenj.password");
        if (revPassword != null && revPassword.length() > 0) {
            dataSource.setPassword(revPassword);
        } else if (password != null && password.length() > 0) {
            dataSource.setPassword(password);
        }
        return org.revenj.Revenj.setup(dataSource, properties, java.util.Optional.<ClassLoader> empty(),
                java.util.Collections.singletonList((org.revenj.extensibility.SystemAspect) new Boot()).iterator());
    }

    private java.util.List<org.revenj.postgres.ObjectConverter.ColumnInfo> loadColumnsInfo(
            org.revenj.extensibility.Container container,
            String query) throws java.sql.SQLException {
        java.util.List<org.revenj.postgres.ObjectConverter.ColumnInfo> columns = new java.util.ArrayList<>();
        try (java.sql.Connection connection = container.resolve(javax.sql.DataSource.class).getConnection();
                java.sql.Statement statement = connection.createStatement();
                java.sql.ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                columns.add(
                        new org.revenj.postgres.ObjectConverter.ColumnInfo(
                                rs.getString("type_schema"),
                                rs.getString("type_name"),
                                rs.getString("column_name"),
                                rs.getString("column_schema"),
                                rs.getString("column_type"),
                                rs.getShort("column_index"),
                                rs.getBoolean("is_not_null"),
                                rs.getBoolean("is_ngs_generated")
                        )
                );
            }
        }
        return columns;
    }

    public void configure(org.revenj.extensibility.Container container) throws java.io.IOException {
        java.util.Properties properties = container.resolve(java.util.Properties.class);
        String prevNamespace = properties.getProperty("revenj.namespace");
        if (prevNamespace != null && !"worldwonders".equals(prevNamespace)) {
                throw new java.io.IOException("Different namespace already defined in Properties file. Trying to add namespace=worldwonders. Found: " + prevNamespace);
        }
        properties.setProperty("revenj.namespace", "worldwonders");
        java.util.List<org.revenj.postgres.ObjectConverter.ColumnInfo> columns;
        try {
            columns = loadColumnsInfo(container, "SELECT * FROM \"-NGS-\".load_type_info()");
        } catch (java.sql.SQLException ignore) {
            try {
                columns = loadColumnsInfo(container, "SELECT " +
"    ns.nspname::varchar as type_schema, " +
"    cl.relname::varchar as type_name, " +
"    atr.attname::varchar as column_name, " +
"    ns_ref.nspname::varchar as column_schema, " +
"    typ.typname::varchar as column_type, " +
"    (SELECT COUNT(*) + 1 " +
"    FROM pg_attribute atr_ord " +
"    WHERE " +
"        atr.attrelid = atr_ord.attrelid " +
"        AND atr_ord.attisdropped = false " +
"        AND atr_ord.attnum > 0 " +
"        AND atr_ord.attnum < atr.attnum)::smallint as column_index, " +
"    atr.attnotnull as is_not_null, " +
"    coalesce(d.description LIKE 'NGS generated%', false) as is_ngs_generated " +
"FROM " +
"    pg_attribute atr " +
"    INNER JOIN pg_class cl ON atr.attrelid = cl.oid " +
"    INNER JOIN pg_namespace ns ON cl.relnamespace = ns.oid " +
"    INNER JOIN pg_type typ ON atr.atttypid = typ.oid " +
"    INNER JOIN pg_namespace ns_ref ON typ.typnamespace = ns_ref.oid " +
"    LEFT JOIN pg_description d ON d.objoid = cl.oid " +
"                                AND d.objsubid = atr.attnum " +
"WHERE " +
"    (cl.relkind = 'r' OR cl.relkind = 'v' OR cl.relkind = 'c') " +
"    AND ns.nspname NOT LIKE 'pg_%' " +
"    AND ns.nspname != 'information_schema' " +
"    AND atr.attnum > 0 " +
"    AND atr.attisdropped = FALSE " +
"ORDER BY 1, 2, 6");
            } catch (java.sql.SQLException e) {
                throw new java.io.IOException(e);
            }
        }
        org.revenj.postgres.jinq.JinqMetaModel metamodel = org.revenj.postgres.jinq.JinqMetaModel.configure(container);
        org.revenj.extensibility.PluginLoader plugins = container.resolve(org.revenj.extensibility.PluginLoader.class);

        worldwonders.wonders.converters.WonderConverter wonders$converter$WonderConverter = new worldwonders.wonders.converters.WonderConverter(columns);
        container.registerInstance(worldwonders.wonders.converters.WonderConverter.class, wonders$converter$WonderConverter, false);
        container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<worldwonders.wonders.Wonder>>(){}.type, wonders$converter$WonderConverter, false);

        worldwonders.wonders.converters.CommentConverter wonders$converter$CommentConverter = new worldwonders.wonders.converters.CommentConverter(columns);
        container.registerInstance(worldwonders.wonders.converters.CommentConverter.class, wonders$converter$CommentConverter, false);
        container.registerInstance(new org.revenj.patterns.Generic<org.revenj.postgres.ObjectConverter<worldwonders.wonders.Comment>>(){}.type, wonders$converter$CommentConverter, false);
        wonders$converter$WonderConverter.configure(container);
        metamodel.registerDataSource(worldwonders.wonders.Wonder.class, "\"wonders\".\"Wonder_entity\"");
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getURI", "\"URI\"", worldwonders.wonders.Wonder::getURI);

        container.register(worldwonders.wonders.repositories.WonderRepository.class);
        container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.SearchableRepository<worldwonders.wonders.Wonder>>(){}.type, worldwonders.wonders.repositories.WonderRepository::new, false);

        container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.Repository<worldwonders.wonders.Wonder>>(){}.type, worldwonders.wonders.repositories.WonderRepository::new, false);
        container.registerFactory(new org.revenj.patterns.Generic<org.revenj.postgres.BulkRepository<worldwonders.wonders.Wonder>>(){}.type, worldwonders.wonders.repositories.WonderRepository::new, false);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getEnglishName", "\"englishName\"", worldwonders.wonders.Wonder::getEnglishName);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getNativeNames", "\"nativeNames\"", worldwonders.wonders.Wonder::getNativeNames);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getIsAncient", "\"isAncient\"", worldwonders.wonders.Wonder::getIsAncient);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getImageLink", "\"imageLink\"", worldwonders.wonders.Wonder::getImageLink);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getTotalRatings", "\"totalRatings\"", worldwonders.wonders.Wonder::getTotalRatings);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getAverageRating", "\"averageRating\"", worldwonders.wonders.Wonder::getAverageRating);
        metamodel.registerProperty(worldwonders.wonders.Wonder.class, "getChosenComments", "\"chosenComments\"", worldwonders.wonders.Wonder::getChosenComments);
        wonders$converter$CommentConverter.configure(container);
        metamodel.registerDataSource(worldwonders.wonders.Comment.class, "\"wonders\".\"Comment\"");
        metamodel.registerProperty(worldwonders.wonders.Comment.class, "getUser", "\"user\"", worldwonders.wonders.Comment::getUser);
        metamodel.registerProperty(worldwonders.wonders.Comment.class, "getTitle", "\"title\"", worldwonders.wonders.Comment::getTitle);
        metamodel.registerProperty(worldwonders.wonders.Comment.class, "getBody", "\"body\"", worldwonders.wonders.Comment::getBody);
        metamodel.registerProperty(worldwonders.wonders.Comment.class, "getRating", "\"rating\"", worldwonders.wonders.Comment::getRating);
        metamodel.registerProperty(worldwonders.wonders.Comment.class, "getCreatedAt", "\"createdAt\"", worldwonders.wonders.Comment::getCreatedAt);

        container.registerFactory(new org.revenj.patterns.Generic<org.revenj.patterns.PersistableRepository<worldwonders.wonders.Wonder>>(){}.type, worldwonders.wonders.repositories.WonderRepository::new, false);
    }
}
