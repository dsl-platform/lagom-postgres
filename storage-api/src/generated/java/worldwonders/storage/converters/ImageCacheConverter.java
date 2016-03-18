/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.storage.converters;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.revenj.postgres.*;
import org.revenj.postgres.converters.*;

public class ImageCacheConverter implements ObjectConverter<worldwonders.storage.ImageCache> {
    @SuppressWarnings("unchecked")
    public ImageCacheConverter(List<ObjectConverter.ColumnInfo> allColumns) throws java.io.IOException {
        Optional<ObjectConverter.ColumnInfo> column;

        final java.util.List<ObjectConverter.ColumnInfo> columns =
                allColumns.stream().filter(it -> "storage".equals(it.typeSchema) && "ImageCache_entity".equals(it.typeName))
                .collect(Collectors.toList());
        columnCount = columns.size();

        readers = new ObjectConverter.Reader[columnCount];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        final java.util.List<ObjectConverter.ColumnInfo> columnsExtended =
                allColumns.stream().filter(it -> "storage".equals(it.typeSchema) && "-ngs_ImageCache_type-".equals(it.typeName))
                .collect(Collectors.toList());
        columnCountExtended = columnsExtended.size();

        readersExtended = new ObjectConverter.Reader[columnCountExtended];
        for (int i = 0; i < readersExtended.length; i++) {
            readersExtended[i] = (instance, rdr, ctx) -> { StringConverter.skip(rdr, ctx); return instance; };
        }

        column = columns.stream().filter(it -> "url".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'url' column in storage ImageCache_entity. Check if DB is in sync");
        __index___url = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "url".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'url' column in storage ImageCache. Check if DB is in sync");
        __index__extended_url = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "size".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'size' column in storage ImageCache_entity. Check if DB is in sync");
        __index___size = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "size".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'size' column in storage ImageCache. Check if DB is in sync");
        __index__extended_size = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "body".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'body' column in storage ImageCache_entity. Check if DB is in sync");
        __index___body = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "body".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'body' column in storage ImageCache. Check if DB is in sync");
        __index__extended_body = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "width".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'width' column in storage ImageCache_entity. Check if DB is in sync");
        __index___width = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "width".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'width' column in storage ImageCache. Check if DB is in sync");
        __index__extended_width = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "height".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'height' column in storage ImageCache_entity. Check if DB is in sync");
        __index___height = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "height".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'height' column in storage ImageCache. Check if DB is in sync");
        __index__extended_height = (int)column.get().order - 1;

        column = columns.stream().filter(it -> "createdAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'createdAt' column in storage ImageCache_entity. Check if DB is in sync");
        __index___createdAt = (int)column.get().order - 1;

        column = columnsExtended.stream().filter(it -> "createdAt".equals(it.columnName)).findAny();
        if (!column.isPresent()) throw new java.io.IOException("Unable to find 'createdAt' column in storage ImageCache. Check if DB is in sync");
        __index__extended_createdAt = (int)column.get().order - 1;
    }

    public void configure(org.revenj.patterns.ServiceLocator locator) {
        worldwonders.storage.ImageCache.__configureConverter(readers, __index___url, __index___size, __index___body,
                __index___width, __index___height, __index___createdAt);

        worldwonders.storage.ImageCache.__configureConverterExtended(readersExtended, __index__extended_url,
                __index__extended_size, __index__extended_body, __index__extended_width, __index__extended_height,
                __index__extended_createdAt);
    }

    @Override
    public String getDbName() {
        return "\"storage\".\"ImageCache_entity\"";
    }

    @Override
    public worldwonders.storage.ImageCache from(PostgresReader reader) throws java.io.IOException {
        return from(reader, 0);
    }

    private worldwonders.storage.ImageCache from(
            PostgresReader reader,
            int outerContext,
            int context,
            ObjectConverter.Reader<worldwonders.storage.ImageCache>[] readers) throws java.io.IOException {
        reader.read(outerContext);
        worldwonders.storage.ImageCache instance = new worldwonders.storage.ImageCache(reader, context, readers);
        reader.read(outerContext);
        return instance;
    }

    @Override
    public PostgresTuple to(worldwonders.storage.ImageCache instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCount];

        items[__index___url] = org.revenj.postgres.converters.UrlConverter.toTuple(instance.getUrl());
        items[__index___size] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getSize());
        items[__index___body] = org.revenj.postgres.converters.ByteaConverter.toTuple(instance.getBody());
        items[__index___width] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getWidth());
        items[__index___height] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getHeight());
        items[__index___createdAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance.getCreatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCount;
    private final ObjectConverter.Reader<worldwonders.storage.ImageCache>[] readers;

    public worldwonders.storage.ImageCache from(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.storage.ImageCache instance = from(reader, context, context == 0 ? 1 : context << 1, readers);
        reader.read();
        return instance;
    }

    public worldwonders.storage.ImageCache from(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readers);
    }

    public PostgresTuple toExtended(worldwonders.storage.ImageCache instance) {
        if (instance == null) return null;
        PostgresTuple[] items = new PostgresTuple[columnCountExtended];

        items[__index__extended_url] = org.revenj.postgres.converters.UrlConverter.toTuple(instance.getUrl());
        items[__index__extended_size] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getSize());
        items[__index__extended_body] = org.revenj.postgres.converters.ByteaConverter.toTuple(instance.getBody());
        items[__index__extended_width] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getWidth());
        items[__index__extended_height] = org.revenj.postgres.converters.IntConverter.toTuple(instance.getHeight());
        items[__index__extended_createdAt] = org.revenj.postgres.converters.TimestampConverter.toTuple(instance
                .getCreatedAt());
        return RecordTuple.from(items);
    }

    private final int columnCountExtended;
    private final ObjectConverter.Reader<worldwonders.storage.ImageCache>[] readersExtended;

    public worldwonders.storage.ImageCache fromExtended(PostgresReader reader, int context) throws java.io.IOException {
        int cur = reader.read();
        if (cur == ',' || cur == ')') return null;
        worldwonders.storage.ImageCache instance = from(reader, context, context == 0 ? 1 : context << 1,
                readersExtended);
        reader.read();
        return instance;
    }

    public worldwonders.storage.ImageCache fromExtended(PostgresReader reader, int outerContext, int context)
            throws java.io.IOException {
        return from(reader, outerContext, context, readersExtended);
    }

    public static String buildURI(org.revenj.postgres.PostgresBuffer _sw, worldwonders.storage.ImageCache instance)
            throws java.io.IOException {
        _sw.initBuffer();
        String _tmp;
        org.revenj.postgres.converters.UrlConverter.serializeURI(_sw, instance.getUrl());
        return _sw.bufferToString();
    }

    private final int __index___url;
    private final int __index__extended_url;
    private final int __index___size;
    private final int __index__extended_size;
    private final int __index___body;
    private final int __index__extended_body;
    private final int __index___width;
    private final int __index__extended_width;
    private final int __index___height;
    private final int __index__extended_height;
    private final int __index___createdAt;
    private final int __index__extended_createdAt;
}
