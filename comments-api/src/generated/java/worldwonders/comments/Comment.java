/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.comments;

public final class Comment implements java.io.Serializable, org.revenj.patterns.DomainEvent {
    public Comment(final String topic, final String user, final String title, final String body, final int rating) {
        setTopic(topic);
        setUser(user);
        setTitle(title);
        setBody(body);
        setRating(rating);
    }

    public Comment() {
        this.topic = "";
        this.title = "";
        this.body = "";
        this.rating = 0;
    }

    private String URI;

    @com.fasterxml.jackson.annotation.JsonProperty("URI")
    public String getURI() {
        return this.URI;
    }

    private java.time.OffsetDateTime ProcessedAt;

    @com.fasterxml.jackson.annotation.JsonProperty("ProcessedAt")
    public java.time.OffsetDateTime getProcessedAt() {
        return this.ProcessedAt;
    }

    private java.time.OffsetDateTime QueuedAt;

    @com.fasterxml.jackson.annotation.JsonProperty("QueuedAt")
    public java.time.OffsetDateTime getQueuedAt() {
        return this.QueuedAt;
    }

    @Override
    public int hashCode() {
        return URI != null ? URI.hashCode() : super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;
        final Comment other = (Comment) obj;

        return URI != null && URI.equals(other.URI);
    }

    @Override
    public String toString() {
        return URI != null ? "Comment(" + URI + ')' : "new Comment(" + super.hashCode() + ')';
    }

    private static final long serialVersionUID = -4037349446018084952L;

    private String topic;

    @com.fasterxml.jackson.annotation.JsonProperty("topic")
    public String getTopic() {
        return topic;
    }

    public Comment setTopic(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"topic\" cannot be null!");
        this.topic = value;

        return this;
    }

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

    public static class findByTopic implements java.io.Serializable, org.revenj.patterns.Specification<Comment> {
        public findByTopic(final String topic) {
            setTopic(topic);
        }

        public findByTopic() {
            this.topic = "";
        }

        private static final long serialVersionUID = 6271760565102606402L;

        private String topic;

        @com.fasterxml.jackson.annotation.JsonProperty("topic")
        public String getTopic() {
            return topic;
        }

        public findByTopic setTopic(final String value) {
            if (value == null) throw new IllegalArgumentException("Property \"topic\" cannot be null!");
            this.topic = value;

            return this;
        }

        public boolean test(worldwonders.comments.Comment it) {
            return it.getTopic().equals(this.getTopic());
        }

        public org.revenj.patterns.Specification<Comment> rewriteLambda() {
            String _topic_ = this.getTopic();
            return it -> it.getTopic().equals(_topic_);
        }
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    private Comment(
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JsonProperty("ProcessedAt") final java.time.OffsetDateTime ProcessedAt,
            @com.fasterxml.jackson.annotation.JsonProperty("QueuedAt") final java.time.OffsetDateTime QueuedAt,
            @com.fasterxml.jackson.annotation.JsonProperty("topic") final String topic,
            @com.fasterxml.jackson.annotation.JsonProperty("user") final String user,
            @com.fasterxml.jackson.annotation.JsonProperty("title") final String title,
            @com.fasterxml.jackson.annotation.JsonProperty("body") final String body,
            @com.fasterxml.jackson.annotation.JsonProperty("rating") final int rating) {
        this.URI = URI != null ? URI : "new " + new java.util.UUID(0L, 0L).toString();
        this.ProcessedAt = ProcessedAt == null ? null : ProcessedAt;
        this.QueuedAt = QueuedAt == null ? null : QueuedAt;
        this.topic = topic == null ? "" : topic;
        this.user = user;
        this.title = title == null ? "" : title;
        this.body = body == null ? "" : body;
        this.rating = rating;
    }

    public Comment(
            org.revenj.postgres.PostgresReader reader,
            int context,
            org.revenj.postgres.ObjectConverter.Reader<Comment>[] readers) throws java.io.IOException {
        for (org.revenj.postgres.ObjectConverter.Reader<Comment> rdr : readers) {
            rdr.read(this, reader, context);
        }
    }

    public static void __configureConverter(org.revenj.postgres.ObjectConverter.Reader<Comment>[] readers, int __index____event_id, int __index___QueuedAt, int __index___ProcessedAt, int __index___topic, int __index___user, int __index___title, int __index___body, int __index___rating) {
        readers[__index____event_id] = (item, reader, context) -> { item.URI = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___QueuedAt] = (item, reader, context) -> { item.QueuedAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
        readers[__index___ProcessedAt] = (item, reader, context) -> { item.ProcessedAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, true, false); return item; };
        readers[__index___topic] = (item, reader, context) -> { item.topic = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___user] = (item, reader, context) -> { item.user = org.revenj.postgres.converters.StringConverter.parse(reader, context, true); return item; };
        readers[__index___title] = (item, reader, context) -> { item.title = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___body] = (item, reader, context) -> { item.body = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___rating] = (item, reader, context) -> { item.rating = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
    }

    static {
        worldwonders.comments.repositories.CommentRepository.__configure(events -> {
            java.time.OffsetDateTime now = java.time.OffsetDateTime.now();
            for (worldwonders.comments.Comment eve : events) {
                eve.URI = null;
                eve.QueuedAt = now;
                eve.ProcessedAt = now;
            }
        }, (events, uris) -> {
            int _i = 0;
            for (worldwonders.comments.Comment eve : events) {
                eve.URI = uris[_i++];
            }
        });
    }
}
