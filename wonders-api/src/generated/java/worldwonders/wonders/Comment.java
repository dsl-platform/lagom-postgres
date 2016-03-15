/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.wonders;

public final class Comment implements java.lang.Cloneable, java.io.Serializable {
    public Comment(
            final String user,
            final String title,
            final String body,
            final int rating,
            final java.time.OffsetDateTime createdAt) {
        setUser(user);
        setTitle(title);
        setBody(body);
        setRating(rating);
        setCreatedAt(createdAt);
    }

    public Comment() {
        this.title = "";
        this.body = "";
        this.rating = 0;
        this.createdAt = java.time.OffsetDateTime.now(java.time.ZoneOffset.systemDefault());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + 249983416;
        result = prime * result + (this.user != null ? this.user.hashCode() : 0);
        result = prime * result + (this.title.hashCode());
        result = prime * result + (this.body.hashCode());
        result = prime * result + (this.rating);
        result = prime * result + (this.createdAt == null ? 0 : this.createdAt.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Comment)) return false;
        return deepEquals((Comment) obj);
    }

    public boolean deepEquals(final Comment other) {
        if (other == null) return false;

        if (!(this.user == other.user || this.user != null && this.user.equals(other.user))) return false;
        if (!(this.title.equals(other.title))) return false;
        if (!(this.body.equals(other.body))) return false;
        if (!(this.rating == other.rating)) return false;
        if (!(this.createdAt == other.createdAt || this.createdAt != null && other.createdAt != null
                && this.createdAt.equals(other.createdAt))) return false;
        return true;
    }

    private Comment(Comment other) {
        this.user = other.user;
        this.title = other.title;
        this.body = other.body;
        this.rating = other.rating;
        this.createdAt = other.createdAt;
    }

    @Override
    public Object clone() {
        return new Comment(this);
    }

    @Override
    public String toString() {
        return "Comment(" + user + ',' + title + ',' + body + ',' + rating + ',' + createdAt + ')';
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    private Comment(
            @com.fasterxml.jackson.annotation.JsonProperty("_helper") final boolean _helper,
            @com.fasterxml.jackson.annotation.JsonProperty("user") final String user,
            @com.fasterxml.jackson.annotation.JsonProperty("title") final String title,
            @com.fasterxml.jackson.annotation.JsonProperty("body") final String body,
            @com.fasterxml.jackson.annotation.JsonProperty("rating") final int rating,
            @com.fasterxml.jackson.annotation.JsonProperty("createdAt") final java.time.OffsetDateTime createdAt) {
        this.user = user;
        this.title = title == null ? "" : title;
        this.body = body == null ? "" : body;
        this.rating = rating;
        this.createdAt = createdAt == null ? org.revenj.Utils.MIN_DATE_TIME : createdAt;
    }

    private static final long serialVersionUID = -3406686163811864999L;

    private String user;

    @com.fasterxml.jackson.annotation.JsonProperty("user")
    public String getUser() {
        return user;
    }

    public Comment setUser(final String value) {
        this.user = value;

        return this;
    }

    private String title;

    @com.fasterxml.jackson.annotation.JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public Comment setTitle(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"title\" cannot be null!");
        org.revenj.Guards.checkLength(value, 100);
        this.title = value;

        return this;
    }

    private String body;

    @com.fasterxml.jackson.annotation.JsonProperty("body")
    public String getBody() {
        return body;
    }

    public Comment setBody(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"body\" cannot be null!");
        this.body = value;

        return this;
    }

    private int rating;

    @com.fasterxml.jackson.annotation.JsonProperty("rating")
    public int getRating() {
        return rating;
    }

    public Comment setRating(final int value) {
        this.rating = value;

        return this;
    }

    private java.time.OffsetDateTime createdAt;

    @com.fasterxml.jackson.annotation.JsonProperty("createdAt")
    public java.time.OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Comment setCreatedAt(final java.time.OffsetDateTime value) {
        if (value == null) throw new IllegalArgumentException("Property \"createdAt\" cannot be null!");
        this.createdAt = value;

        return this;
    }

    public Comment(
            org.revenj.postgres.PostgresReader reader,
            int context,
            org.revenj.postgres.ObjectConverter.Reader<Comment>[] readers) throws java.io.IOException {
        for (org.revenj.postgres.ObjectConverter.Reader<Comment> rdr : readers) {
            rdr.read(this, reader, context);
        }
    }

    public static void __configureConverter(org.revenj.postgres.ObjectConverter.Reader<Comment>[] readers, int __index___user, int __index___title, int __index___body, int __index___rating, int __index___createdAt) {
        readers[__index___user] = (item, reader, context) -> { item.user = org.revenj.postgres.converters.StringConverter.parse(reader, context, true); return item; };
        readers[__index___title] = (item, reader, context) -> { item.title = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___body] = (item, reader, context) -> { item.body = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___rating] = (item, reader, context) -> { item.rating = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index___createdAt] = (item, reader, context) -> { item.createdAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }

    public static void __configureConverterExtended(org.revenj.postgres.ObjectConverter.Reader<Comment>[] readers, int __index__extended_user, int __index__extended_title, int __index__extended_body, int __index__extended_rating, int __index__extended_createdAt) {
        readers[__index__extended_user] = (item, reader, context) -> { item.user = org.revenj.postgres.converters.StringConverter.parse(reader, context, true); return item; };
        readers[__index__extended_title] = (item, reader, context) -> { item.title = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index__extended_body] = (item, reader, context) -> { item.body = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index__extended_rating] = (item, reader, context) -> { item.rating = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index__extended_createdAt] = (item, reader, context) -> { item.createdAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }
}
