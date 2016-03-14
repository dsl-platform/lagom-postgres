/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.model.converters;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.revenj.postgres.*;
import org.revenj.postgres.converters.*;

public class RatingConverter implements ObjectConverter<worldwonders.model.Rating> {
    @SuppressWarnings("unchecked")
    public RatingConverter(List<ObjectConverter.ColumnInfo> allColumns) throws java.io.IOException {
        Optional<ObjectConverter.ColumnInfo> column;

        final java.util.List<ObjectConverter.ColumnInfo> columns =
                allColumns.stream().filter(it -> "model".equals(it.typeSchema) && "Rating_entity".equals(it.typeName))
                .collect(Collectors.toList());
        columnCount = columns.size();

        readers = new ObjectConverter.Reader[columnCount];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        final java.util.List<ObjectConverter.ColumnInfo> columnsExtended =
                allColumns.stream().filter(it -> "model".equals(it.typeSchema) && "-ngs_Rating_type-".equals(it.typeName))
                .collect(Collectors.toList());
        columnCountExtended = columnsExtended.size();

        readersExtended = new ObjectConverter.Reader[columnCountExtended];
        for (int i = 0; i < readersExtended.length; i++) {
            readersExtended[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        column = columns.stream().filter(it -> "ID".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'ID' column in model Rating_entity. Check if DB is in sync");
        __index___ID = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "ID".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'ID' column in model Rating. Check if DB is in sync");
        __index__extended_ID = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "user".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'user' column in model Rating_entity. Check if DB is in sync");
        __index___user = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "user".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'user' column in model Rating. Check if DB is in sync");
        __index__extended_user = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "comment".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'comment' column in model Rating_entity. Check if DB is in sync");
        __index___comment = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "comment".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'comment' column in model Rating. Check if DB is in sync");
        __index__extended_comment = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "score".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'score' column in model Rating_entity. Check if DB is in sync");
        __index___score = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "score".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'score' column in model Rating. Check if DB is in sync");
        __index__extended_score = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "ratedAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'ratedAt' column in model Rating_entity. Check if DB is in sync");
        __index___ratedAt = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "ratedAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'ratedAt' column in model Rating. Check if DB is in sync");
        __index__extended_ratedAt = (int)column.get().order - 1;
    }

    public void configure(org.revenj.patterns.ServiceLocator locator) {
        worldwonders.model.Rating.__configureConverter(readers, __index___ID, __index___user, __index___comment,
                __index___score, __index___ratedAt);

        worldwonders.model.Rating.__configureConverterExtended(readersExtended, __index__extended_ID,
                __index__extended_user, __index__extended_comment, __index__extended_score, __index__extended_ratedAt);
    }

    @Override
    public String getDbName() {
        return "\"model\".\"Rating_entity\"";
    }

    @Override
    public worldwonders.model.Rating from(PostgresReader reader) throws java.io.IOException {
        return from(reader, 0);
    }

    private worldwonders.model.Rating from(
            PostgresReader reader,
            int outerContext,
            int context,
            ObjectConverter.Reader<worldwonders.model.Rating>[] readers) throws java.io.IOException {
        reader.read(outerContext);
        worldwonders.model.Rating instance = new worldwonders.model.Rating(reader, context, readers);
        reader.read(outerContext);
        return instance;
    }

    @Override
    public PostgresTuple to(worldwonders.model.Rating instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCount];

        items[__index___ID] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getID());
        items[__index___user] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getUser());
        items[__index___comment] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getComment());
        items[__index___score] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getScore());
        items[__index___ratedAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance.getRatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCount;
    private final ObjectConverter.Reader<worldwonders.model.Rating>[] readers;

    public worldwonders.model.Rating from(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.model.Rating instance = from(reader, context, context == 0 ? 1 : context << 1, readers);
        reader.read();
        return instance;
    }

    public worldwonders.model.Rating from(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readers);
    }

    public PostgresTuple toExtended(worldwonders.model.Rating instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCountExtended];

        items[__index__extended_ID] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getID());
        items[__index__extended_user] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getUser());
        items[__index__extended_comment] = org.revenj.postgres.converters.StringConverter
                .toTuple(instance.getComment());
        items[__index__extended_score] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getScore());
        items[__index__extended_ratedAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance
                .getRatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCountExtended;
    private final ObjectConverter.Reader<worldwonders.model.Rating>[] readersExtended;

    public worldwonders.model.Rating fromExtended(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.model.Rating instance = from(reader, context, context == 0 ? 1 : context << 1, readersExtended);
        reader.read();
        return instance;
    }

    public worldwonders.model.Rating fromExtended(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readersExtended);
    }

    private final int __index___ID;
    private final int __index__extended_ID;

    public static String buildURI(org.revenj.postgres.PostgresBuffer _sw, worldwonders.model.Rating instance)
            throws java.io.IOException {
        _sw.initBuffer();
        String _tmp;
        org.revenj.postgres.converters.IntConverter.serializeURI(_sw, instance.getID());
        return _sw.bufferToString();
    }

    private final int __index___user;
    private final int __index__extended_user;
    private final int __index___comment;
    private final int __index__extended_comment;
    private final int __index___score;
    private final int __index__extended_score;
    private final int __index___ratedAt;
    private final int __index__extended_ratedAt;
}
