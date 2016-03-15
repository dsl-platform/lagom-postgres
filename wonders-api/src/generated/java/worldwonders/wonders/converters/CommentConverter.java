/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.wonders.converters;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.revenj.postgres.*;
import org.revenj.postgres.converters.*;

public class CommentConverter implements ObjectConverter<worldwonders.wonders.Comment> {
    @SuppressWarnings("unchecked")
    public CommentConverter(List<ObjectConverter.ColumnInfo> allColumns) throws java.io.IOException {
        Optional<ObjectConverter.ColumnInfo> column;

        final java.util.List<ObjectConverter.ColumnInfo> columns =
                allColumns.stream().filter(it -> "wonders".equals(it.typeSchema) && "Comment".equals(it.typeName))
                .collect(Collectors.toList());
        columnCount = columns.size();

        readers = new ObjectConverter.Reader[columnCount];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        final java.util.List<ObjectConverter.ColumnInfo> columnsExtended =
                allColumns.stream().filter(it -> "wonders".equals(it.typeSchema) && "-ngs_Comment_type-".equals(it.typeName))
                .collect(Collectors.toList());
        columnCountExtended = columnsExtended.size();

        readersExtended = new ObjectConverter.Reader[columnCountExtended];
        for (int i = 0; i < readersExtended.length; i++) {
            readersExtended[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        column = columns.stream().filter(it -> "user".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'user' column in wonders Comment. Check if DB is in sync");
        __index___user = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "user".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'user' column in wonders Comment. Check if DB is in sync");
        __index__extended_user = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "title".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'title' column in wonders Comment. Check if DB is in sync");
        __index___title = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "title".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'title' column in wonders Comment. Check if DB is in sync");
        __index__extended_title = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "body".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'body' column in wonders Comment. Check if DB is in sync");
        __index___body = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "body".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'body' column in wonders Comment. Check if DB is in sync");
        __index__extended_body = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "rating".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'rating' column in wonders Comment. Check if DB is in sync");
        __index___rating = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "rating".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'rating' column in wonders Comment. Check if DB is in sync");
        __index__extended_rating = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "createdAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'createdAt' column in wonders Comment. Check if DB is in sync");
        __index___createdAt = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "createdAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'createdAt' column in wonders Comment. Check if DB is in sync");
        __index__extended_createdAt = (int)column.get().order - 1;
    }

    public void configure(org.revenj.patterns.ServiceLocator locator) {
        worldwonders.wonders.Comment.__configureConverter(readers, __index___user, __index___title, __index___body,
                __index___rating, __index___createdAt);

        worldwonders.wonders.Comment.__configureConverterExtended(readersExtended, __index__extended_user,
                __index__extended_title, __index__extended_body, __index__extended_rating, __index__extended_createdAt);
    }

    @Override
    public String getDbName() {
        return "\"wonders\".\"Comment\"";
    }

    @Override
    public worldwonders.wonders.Comment from(PostgresReader reader) throws java.io.IOException {
        return from(reader, 0);
    }

    private worldwonders.wonders.Comment from(
            PostgresReader reader,
            int outerContext,
            int context,
            ObjectConverter.Reader<worldwonders.wonders.Comment>[] readers) throws java.io.IOException {
        reader.read(outerContext);
        worldwonders.wonders.Comment instance = new worldwonders.wonders.Comment(reader, context, readers);
        reader.read(outerContext);
        return instance;
    }

    @Override
    public PostgresTuple to(worldwonders.wonders.Comment instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCount];

        items[__index___user] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getUser());
        items[__index___title] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getTitle());
        items[__index___body] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getBody());
        items[__index___rating] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getRating());
        items[__index___createdAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance.getCreatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCount;
    private final ObjectConverter.Reader<worldwonders.wonders.Comment>[] readers;

    public worldwonders.wonders.Comment from(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.wonders.Comment instance = from(reader, context, context == 0 ? 1 : context << 1, readers);
        reader.read();
        return instance;
    }

    public worldwonders.wonders.Comment from(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readers);
    }

    public PostgresTuple toExtended(worldwonders.wonders.Comment instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCountExtended];

        items[__index__extended_user] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getUser());
        items[__index__extended_title] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getTitle());
        items[__index__extended_body] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getBody());
        items[__index__extended_rating] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getRating());
        items[__index__extended_createdAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance
                .getCreatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCountExtended;
    private final ObjectConverter.Reader<worldwonders.wonders.Comment>[] readersExtended;

    public worldwonders.wonders.Comment fromExtended(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.wonders.Comment instance = from(reader, context, context == 0 ? 1 : context << 1, readersExtended);
        reader.read();
        return instance;
    }

    public worldwonders.wonders.Comment fromExtended(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readersExtended);
    }

    private final int __index___user;
    private final int __index__extended_user;
    private final int __index___title;
    private final int __index__extended_title;
    private final int __index___body;
    private final int __index__extended_body;
    private final int __index___rating;
    private final int __index__extended_rating;
    private final int __index___createdAt;
    private final int __index__extended_createdAt;
}
