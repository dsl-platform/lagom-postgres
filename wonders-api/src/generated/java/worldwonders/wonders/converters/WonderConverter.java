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

public class WonderConverter implements ObjectConverter<worldwonders.wonders.Wonder> {
    @SuppressWarnings("unchecked")
    public WonderConverter(List<ObjectConverter.ColumnInfo> allColumns) throws java.io.IOException {
        Optional<ObjectConverter.ColumnInfo> column;

        final java.util.List<ObjectConverter.ColumnInfo> columns =
                allColumns.stream().filter(it -> "wonders".equals(it.typeSchema) && "Wonder_entity".equals(it.typeName))
                .collect(Collectors.toList());
        columnCount = columns.size();

        readers = new ObjectConverter.Reader[columnCount];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        final java.util.List<ObjectConverter.ColumnInfo> columnsExtended =
                allColumns.stream().filter(it -> "wonders".equals(it.typeSchema) && "-ngs_Wonder_type-".equals(it.typeName))
                .collect(Collectors.toList());
        columnCountExtended = columnsExtended.size();

        readersExtended = new ObjectConverter.Reader[columnCountExtended];
        for (int i = 0; i < readersExtended.length; i++) {
            readersExtended[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        column = columns.stream().filter(it -> "englishName".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'englishName' column in wonders Wonder_entity. Check if DB is in sync");
        __index___englishName = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "englishName".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'englishName' column in wonders Wonder. Check if DB is in sync");
        __index__extended_englishName = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "nativeNames".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'nativeNames' column in wonders Wonder_entity. Check if DB is in sync");
        __index___nativeNames = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "nativeNames".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'nativeNames' column in wonders Wonder. Check if DB is in sync");
        __index__extended_nativeNames = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "isAncient".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'isAncient' column in wonders Wonder_entity. Check if DB is in sync");
        __index___isAncient = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "isAncient".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'isAncient' column in wonders Wonder. Check if DB is in sync");
        __index__extended_isAncient = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "imageLink".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'imageLink' column in wonders Wonder_entity. Check if DB is in sync");
        __index___imageLink = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "imageLink".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'imageLink' column in wonders Wonder. Check if DB is in sync");
        __index__extended_imageLink = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "totalRatings".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'totalRatings' column in wonders Wonder_entity. Check if DB is in sync");
        __index___totalRatings = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "totalRatings".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'totalRatings' column in wonders Wonder. Check if DB is in sync");
        __index__extended_totalRatings = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "averageRating".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'averageRating' column in wonders Wonder_entity. Check if DB is in sync");
        __index___averageRating = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "averageRating".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'averageRating' column in wonders Wonder. Check if DB is in sync");
        __index__extended_averageRating = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "chosenComments".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'chosenComments' column in wonders Wonder_entity. Check if DB is in sync");
        __index___chosenComments = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "chosenComments".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'chosenComments' column in wonders Wonder. Check if DB is in sync");
        __index__extended_chosenComments = (int)column.get().order - 1;
    }

    public void configure(org.revenj.patterns.ServiceLocator locator) {
        __converter_chosenComments = locator.resolve(worldwonders.wonders.converters.CommentConverter.class);

        worldwonders.wonders.Wonder.__configureConverter(readers, __index___englishName, __index___nativeNames,
                __index___isAncient, __index___imageLink, __index___totalRatings, __index___averageRating,
                __converter_chosenComments, __index___chosenComments);

        worldwonders.wonders.Wonder.__configureConverterExtended(readersExtended, __index__extended_englishName,
                __index__extended_nativeNames, __index__extended_isAncient, __index__extended_imageLink,
                __index__extended_totalRatings, __index__extended_averageRating, __converter_chosenComments,
                __index__extended_chosenComments);
    }

    @Override
    public String getDbName() {
        return "\"wonders\".\"Wonder_entity\"";
    }

    @Override
    public worldwonders.wonders.Wonder from(PostgresReader reader) throws java.io.IOException {
        return from(reader, 0);
    }

    private worldwonders.wonders.Wonder from(
            PostgresReader reader,
            int outerContext,
            int context,
            ObjectConverter.Reader<worldwonders.wonders.Wonder>[] readers) throws java.io.IOException {
        reader.read(outerContext);
        worldwonders.wonders.Wonder instance = new worldwonders.wonders.Wonder(reader, context, readers);
        reader.read(outerContext);
        return instance;
    }

    @Override
    public PostgresTuple to(worldwonders.wonders.Wonder instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCount];

        items[__index___englishName] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getEnglishName());
        items[__index___nativeNames] = org.revenj.postgres.converters.ArrayTuple.create(instance.getNativeNames(), org.revenj.postgres.converters.StringConverter::toTuple);
        items[__index___isAncient] = org.revenj.postgres.converters.BoolConverter.toTuple(instance.getIsAncient());
        items[__index___imageLink] = org.revenj.postgres.converters.UrlConverter.toTuple(instance.getImageLink());
        items[__index___totalRatings] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getTotalRatings());
        items[__index___averageRating] = org.revenj.postgres.converters.DoubleConverter.toTuple(instance.getAverageRating());
        items[__index___chosenComments] = org.revenj.postgres.converters.ArrayTuple.create(instance.getChosenComments(), __converter_chosenComments::to);
        return RecordTuple.from(items);
    }

    private final int columnCount;
    private final ObjectConverter.Reader<worldwonders.wonders.Wonder>[] readers;

    public worldwonders.wonders.Wonder from(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.wonders.Wonder instance = from(reader, context, context == 0 ? 1 : context << 1, readers);
        reader.read();
        return instance;
    }

    public worldwonders.wonders.Wonder from(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readers);
    }

    public PostgresTuple toExtended(worldwonders.wonders.Wonder instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCountExtended];

        items[__index__extended_englishName] = org.revenj.postgres.converters.StringConverter.toTuple(instance.getEnglishName());
        items[__index__extended_nativeNames] = org.revenj.postgres.converters.ArrayTuple.create(instance.getNativeNames(), org.revenj.postgres.converters.StringConverter::toTuple);
        items[__index__extended_isAncient] = org.revenj.postgres.converters.BoolConverter.toTuple(instance.getIsAncient());
        items[__index__extended_imageLink] = org.revenj.postgres.converters.UrlConverter.toTuple(instance.getImageLink());
        items[__index__extended_totalRatings] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getTotalRatings());
        items[__index__extended_averageRating] = org.revenj.postgres.converters.DoubleConverter.toTuple(instance.getAverageRating());
        items[__index__extended_chosenComments] = org.revenj.postgres.converters.ArrayTuple.create(instance.getChosenComments(), __converter_chosenComments::toExtended);
        return RecordTuple.from(items);
    }

    private final int columnCountExtended;
    private final ObjectConverter.Reader<worldwonders.wonders.Wonder>[] readersExtended;

    public worldwonders.wonders.Wonder fromExtended(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.wonders.Wonder instance = from(reader, context, context == 0 ? 1 : context << 1, readersExtended);
        reader.read();
        return instance;
    }

    public worldwonders.wonders.Wonder fromExtended(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readersExtended);
    }

    public static String buildURI(org.revenj.postgres.PostgresBuffer _sw, worldwonders.wonders.Wonder instance)
            throws java.io.IOException {
        _sw.initBuffer();
        String _tmp;
        org.revenj.postgres.converters.StringConverter.serializeURI(_sw, instance.getEnglishName());
        return _sw.bufferToString();
    }

    private final int __index___englishName;
    private final int __index__extended_englishName;
    private final int __index___nativeNames;
    private final int __index__extended_nativeNames;
    private final int __index___isAncient;
    private final int __index__extended_isAncient;
    private final int __index___imageLink;
    private final int __index__extended_imageLink;
    private final int __index___totalRatings;
    private final int __index__extended_totalRatings;
    private final int __index___averageRating;
    private final int __index__extended_averageRating;
    private worldwonders.wonders.converters.CommentConverter __converter_chosenComments;
    private final int __index___chosenComments;
    private final int __index__extended_chosenComments;
}
