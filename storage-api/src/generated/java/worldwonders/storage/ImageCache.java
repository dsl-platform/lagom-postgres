/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.storage;

public class ImageCache implements java.lang.Cloneable, java.io.Serializable, org.revenj.patterns.AggregateRoot {
    public ImageCache() {
        URI = java.lang.Integer.toString(System.identityHashCode(this));
        this.size = 0;
        this.body = org.revenj.Utils.EMPTY_BINARY;
        this.createdAt = java.time.OffsetDateTime.now(java.time.ZoneOffset.systemDefault());
    }

    private String URI;

    @com.fasterxml.jackson.annotation.JsonProperty("URI")
    public String getURI() {
        return this.URI;
    }

    @Override
    public int hashCode() {
        return URI.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || obj instanceof ImageCache == false) return false;
        final ImageCache other = (ImageCache) obj;
        return URI.equals(other.URI);
    }

    public boolean deepEquals(final ImageCache other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!URI.equals(other.URI)) return false;

        if (!(this.url == other.url || this.url != null && this.url.equals(other.url))) return false;
        if (!(this.size == other.size)) return false;
        if (!(java.util.Arrays.equals(this.body, other.body))) return false;
        if (!(this.width == other.width || this.width != null && this.width.equals(other.width))) return false;
        if (!(this.height == other.height || this.height != null && this.height.equals(other.height))) return false;
        if (!(this.createdAt == other.createdAt || this.createdAt != null && other.createdAt != null
                && this.createdAt.equals(other.createdAt))) return false;
        return true;
    }

    private ImageCache(ImageCache other) {
        this.URI = other.URI;
        this.__locator = other.__locator;
        this.url = other.url;
        this.size = other.size;
        this.body = other.body != null ? java.util.Arrays.copyOf(other.body, other.body.length) : null;
        this.width = other.width;
        this.height = other.height;
        this.createdAt = other.createdAt;
        this.__originalValue = other.__originalValue;
    }

    @Override
    public Object clone() {
        return new ImageCache(this);
    }

    @Override
    public String toString() {
        return "ImageCache(" + URI + ')';
    }

    public ImageCache(
            final java.net.URI url,
            final int size,
            final byte[] body,
            final Integer width,
            final Integer height,
            final java.time.OffsetDateTime createdAt) {
        URI = java.lang.Integer.toString(System.identityHashCode(this));
        setUrl(url);
        setSize(size);
        setBody(body);
        setWidth(width);
        setHeight(height);
        setCreatedAt(createdAt);
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    private ImageCache(
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JacksonInject("__locator") final org.revenj.patterns.ServiceLocator __locator,
            @com.fasterxml.jackson.annotation.JsonProperty("url") final java.net.URI url,
            @com.fasterxml.jackson.annotation.JsonProperty("size") final int size,
            @com.fasterxml.jackson.annotation.JsonProperty("body") final byte[] body,
            @com.fasterxml.jackson.annotation.JsonProperty("width") final Integer width,
            @com.fasterxml.jackson.annotation.JsonProperty("height") final Integer height,
            @com.fasterxml.jackson.annotation.JsonProperty("createdAt") final java.time.OffsetDateTime createdAt) {
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.__locator = java.util.Optional.ofNullable(__locator);
        this.url = url;
        this.size = size;
        this.body = body == null ? org.revenj.Utils.EMPTY_BINARY : body;
        this.width = width;
        this.height = height;
        this.createdAt = createdAt == null ? org.revenj.Utils.MIN_DATE_TIME : createdAt;
    }

    private transient java.util.Optional<org.revenj.patterns.ServiceLocator> __locator = java.util.Optional.empty();
    private static final long serialVersionUID = -7124896666328472769L;

    private java.net.URI url;

    @com.fasterxml.jackson.annotation.JsonProperty("url")
    public java.net.URI getUrl() {
        return url;
    }

    public ImageCache setUrl(final java.net.URI value) {
        if (value == null) throw new IllegalArgumentException("Property \"url\" cannot be null!");
        this.url = value;

        return this;
    }

    private int size;

    @com.fasterxml.jackson.annotation.JsonProperty("size")
    public int getSize() {
        return size;
    }

    public ImageCache setSize(final int value) {
        this.size = value;

        return this;
    }

    private byte[] body;

    @com.fasterxml.jackson.annotation.JsonProperty("body")
    public byte[] getBody() {
        return body;
    }

    public ImageCache setBody(final byte[] value) {
        if (value == null) throw new IllegalArgumentException("Property \"body\" cannot be null!");
        this.body = value;

        return this;
    }

    private Integer width;

    @com.fasterxml.jackson.annotation.JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    public ImageCache setWidth(final Integer value) {
        this.width = value;

        return this;
    }

    private Integer height;

    @com.fasterxml.jackson.annotation.JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    public ImageCache setHeight(final Integer value) {
        this.height = value;

        return this;
    }

    private java.time.OffsetDateTime createdAt;

    @com.fasterxml.jackson.annotation.JsonProperty("createdAt")
    public java.time.OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public ImageCache setCreatedAt(final java.time.OffsetDateTime value) {
        if (value == null) throw new IllegalArgumentException("Property \"createdAt\" cannot be null!");
        this.createdAt = value;

        return this;
    }

    private transient ImageCache __originalValue;

    static {
        worldwonders.storage.repositories.ImageCacheRepository.__setupPersist((aggregates, arg) -> {
            try {
                for (worldwonders.storage.ImageCache agg : aggregates) {
                    agg.URI = worldwonders.storage.converters.ImageCacheConverter.buildURI(arg.getKey(), agg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, (aggregates, arg) -> {
            try {
                java.util.List<worldwonders.storage.ImageCache> oldAggregates = aggregates.getKey();
                java.util.List<worldwonders.storage.ImageCache> newAggregates = aggregates.getValue();
                for (int i = 0; i < newAggregates.size(); i++) {
                    worldwonders.storage.ImageCache oldAgg = oldAggregates.get(i);
                    worldwonders.storage.ImageCache newAgg = newAggregates.get(i);

                    newAgg.URI = worldwonders.storage.converters.ImageCacheConverter.buildURI(arg.getKey(), newAgg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, aggregates -> {
            for (worldwonders.storage.ImageCache agg : aggregates) {}
        }, agg -> {
            ImageCache _res = agg.__originalValue;
            agg.__originalValue = (ImageCache) agg.clone();
            if (_res != null) { return _res; }
            return null;
        });
    }

    public ImageCache(
            org.revenj.postgres.PostgresReader reader,
            int context,
            org.revenj.postgres.ObjectConverter.Reader<ImageCache>[] readers) throws java.io.IOException {
        this.__locator = reader.getLocator();
        for (org.revenj.postgres.ObjectConverter.Reader<ImageCache> rdr : readers) {
            rdr.read(this, reader, context);
        }
        URI = worldwonders.storage.converters.ImageCacheConverter.buildURI(reader, this);
        this.__originalValue = (ImageCache) this.clone();
    }

    public static void __configureConverter(org.revenj.postgres.ObjectConverter.Reader<ImageCache>[] readers, int __index___url, int __index___size, int __index___body, int __index___width, int __index___height, int __index___createdAt) {
        readers[__index___url] = (item, reader, context) -> { item.url = org.revenj.postgres.converters.UrlConverter.parse(reader, context); return item; };
        readers[__index___size] = (item, reader, context) -> { item.size = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index___body] = (item, reader, context) -> { item.body = org.revenj.postgres.converters.ByteaConverter.parse(reader, context); return item; };
        readers[__index___width] = (item, reader, context) -> { item.width = org.revenj.postgres.converters.IntConverter.parseNullable(reader); return item; };
        readers[__index___height] = (item, reader, context) -> { item.height = org.revenj.postgres.converters.IntConverter.parseNullable(reader); return item; };
        readers[__index___createdAt] = (item, reader, context) -> { item.createdAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }

    public static void __configureConverterExtended(org.revenj.postgres.ObjectConverter.Reader<ImageCache>[] readers, int __index__extended_url, int __index__extended_size, int __index__extended_body, int __index__extended_width, int __index__extended_height, int __index__extended_createdAt) {
        readers[__index__extended_url] = (item, reader, context) -> { item.url = org.revenj.postgres.converters.UrlConverter.parse(reader, context); return item; };
        readers[__index__extended_size] = (item, reader, context) -> { item.size = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index__extended_body] = (item, reader, context) -> { item.body = org.revenj.postgres.converters.ByteaConverter.parse(reader, context); return item; };
        readers[__index__extended_width] = (item, reader, context) -> { item.width = org.revenj.postgres.converters.IntConverter.parseNullable(reader); return item; };
        readers[__index__extended_height] = (item, reader, context) -> { item.height = org.revenj.postgres.converters.IntConverter.parseNullable(reader); return item; };
        readers[__index__extended_createdAt] = (item, reader, context) -> { item.createdAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }
}
