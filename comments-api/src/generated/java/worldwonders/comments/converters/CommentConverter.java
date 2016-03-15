/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.comments.converters;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.revenj.postgres.*;
import org.revenj.postgres.converters.*;

public class CommentConverter implements ObjectConverter<worldwonders.comments.Comment> {
    @SuppressWarnings("unchecked")
    public CommentConverter(List<ObjectConverter.ColumnInfo> allColumns) throws java.io.IOException {
        Optional<ObjectConverter.ColumnInfo> column;

        final java.util.List<ObjectConverter.ColumnInfo> columns =
                allColumns.stream().filter(it -> "comments".equals(it.typeSchema) && "Comment_event".equals(it.typeName))
                .collect(Collectors.toList());
        columnCount = columns.size();

        readers = new ObjectConverter.Reader[columnCount];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        column = columns.stream().filter(it -> "_event_id".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find '_event_id' column in comments Comment_event. Check if DB is in sync");
        __index____event_id = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "QueuedAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'QueuedAt' column in comments Comment_event. Check if DB is in sync");
        __index___QueuedAt = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "ProcessedAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'ProcessedAt' column in comments Comment_event. Check if DB is in sync");
        __index___ProcessedAt = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "topic".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'topic' column in comments Comment_event. Check if DB is in sync");
        __index___topic = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "user".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'user' column in comments Comment_event. Check if DB is in sync");
        __index___user = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "title".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'title' column in comments Comment_event. Check if DB is in sync");
        __index___title = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "body".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'body' column in comments Comment_event. Check if DB is in sync");
        __index___body = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "rating".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'rating' column in comments Comment_event. Check if DB is in sync");
        __index___rating = (int)column.get().order - 1;
    }

    public void configure(org.revenj.patterns.ServiceLocator locator) {
        worldwonders.comments.Comment.__configureConverter(readers, __index____event_id, __index___QueuedAt,
                __index___ProcessedAt, __index___topic, __index___user, __index___title, __index___body,
                __index___rating);
    }

    @Override
    public String getDbName() {
        return "\"comments\".\"Comment_event\"";
    }

    @Override
    public worldwonders.comments.Comment from(PostgresReader reader) throws java.io.IOException {
        return from(reader, 0);
    }

    private worldwonders.comments.Comment from(
            PostgresReader reader,
            int outerContext,
            int context,
            ObjectConverter.Reader<worldwonders.comments.Comment>[] readers) throws java.io.IOException {
        reader.read(outerContext);
        worldwonders.comments.Comment instance = new worldwonders.comments.Comment(reader, context, readers);
        reader.read(outerContext);
        return instance;
    }

    @Override
    public PostgresTuple to(worldwonders.comments.Comment instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCount];

        items[__index____event_id] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getURI());
        items[__index___QueuedAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance.getQueuedAt());
        items[__index___ProcessedAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance
                .getProcessedAt());
        items[__index___topic] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getTopic());
        items[__index___user] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getUser());
        items[__index___title] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getTitle());
        items[__index___body] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getBody());
        items[__index___rating] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getRating());
        return RecordTuple.from(items);
    }

    private final int columnCount;
    private final ObjectConverter.Reader<worldwonders.comments.Comment>[] readers;

    public worldwonders.comments.Comment from(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.comments.Comment instance = from(reader, context, context == 0 ? 1 : context << 1, readers);
        reader.read();
        return instance;
    }

    public worldwonders.comments.Comment from(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readers);
    }

    private final int __index____event_id;
    private final int __index___QueuedAt;
    private final int __index___ProcessedAt;
    private final int __index___topic;
    private final int __index___user;
    private final int __index___title;
    private final int __index___body;
    private final int __index___rating;
}
