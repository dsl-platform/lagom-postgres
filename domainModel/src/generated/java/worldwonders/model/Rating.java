/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.model;

public class Rating implements java.lang.Cloneable, java.io.Serializable, org.revenj.patterns.AggregateRoot,
        com.dslplatform.json.JsonObject {
    public Rating() {
        this.ID = 0;
        this.ID = --__SequenceCounterID__;
        this.comment = "";
        this.score = 0;
        this.ratedAt = java.time.OffsetDateTime.now(java.time.ZoneOffset.systemDefault());
        this.URI = java.lang.Integer.toString(this.ID);
    }

    private String URI;

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
        if (obj == null || obj instanceof Rating == false) return false;
        final Rating other = (Rating) obj;
        return URI.equals(other.URI);
    }

    public boolean deepEquals(final Rating other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!URI.equals(other.URI)) return false;

        if (!(this.ID == other.ID)) return false;
        if (!(this.user == other.user || this.user != null && this.user.equals(other.user))) return false;
        if (!(this.comment.equals(other.comment))) return false;
        if (!(this.score == other.score)) return false;
        if (!(this.ratedAt == other.ratedAt || this.ratedAt != null && other.ratedAt != null
                && this.ratedAt.equals(other.ratedAt))) return false;
        return true;
    }

    private Rating(Rating other) {
        this.URI = other.URI;
        this.__locator = other.__locator;
        this.ID = other.ID;
        this.user = other.user;
        this.comment = other.comment;
        this.score = other.score;
        this.ratedAt = other.ratedAt;
        this.__originalValue = other.__originalValue;
    }

    @Override
    public Object clone() {
        return new Rating(this);
    }

    @Override
    public String toString() {
        return "Rating(" + URI + ')';
    }

    public Rating(final String user, final String comment, final int score, final java.time.OffsetDateTime ratedAt) {
        this.ID = --__SequenceCounterID__;
        setUser(user);
        setComment(comment);
        setScore(score);
        setRatedAt(ratedAt);
        this.URI = java.lang.Integer.toString(this.ID);
    }

    private transient java.util.Optional<org.revenj.patterns.ServiceLocator> __locator = java.util.Optional.empty();
    private static final long serialVersionUID = -6267934832270059036L;

    private int ID;

    public int getID() {
        return ID;
    }

    private Rating setID(final int value) {
        this.ID = value;

        return this;
    }

    static {
        worldwonders.model.repositories.RatingRepository
                .__setupSequenceID((items, connection) -> {
                    try (java.sql.PreparedStatement st = connection
                            .prepareStatement("/*NO LOAD BALANCE*/SELECT nextval('\"model\".\"Rating_ID_seq\"'::regclass)::int FROM generate_series(1, ?)")) {
                        st.setInt(1, items.size());
                        try (java.sql.ResultSet rs = st.executeQuery()) {
                            java.util.Iterator<Rating> iterator = items.iterator();
                            while (rs.next()) {
                                iterator.next().setID(rs.getInt(1));
                            }
                        }
                    } catch (java.sql.SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static int __SequenceCounterID__;

    private String user;

    public String getUser() {
        return user;
    }

    public Rating setUser(final String value) {
        this.user = value;

        return this;
    }

    private String comment;

    public String getComment() {
        return comment;
    }

    public Rating setComment(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"comment\" cannot be null!");
        this.comment = value;

        return this;
    }

    private int score;

    public int getScore() {
        return score;
    }

    public Rating setScore(final int value) {
        this.score = value;

        return this;
    }

    private java.time.OffsetDateTime ratedAt;

    public java.time.OffsetDateTime getRatedAt() {
        return ratedAt;
    }

    public Rating setRatedAt(final java.time.OffsetDateTime value) {
        if (value == null) throw new IllegalArgumentException("Property \"ratedAt\" cannot be null!");
        this.ratedAt = value;

        return this;
    }

    private transient Rating __originalValue;

    static {
        worldwonders.model.repositories.RatingRepository.__setupPersist((aggregates, arg) -> {
            try {
                for (worldwonders.model.Rating agg : aggregates) {
                    agg.URI = worldwonders.model.converters.RatingConverter.buildURI(arg.getKey(), agg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, (aggregates, arg) -> {
            try {
                java.util.List<worldwonders.model.Rating> oldAggregates = aggregates.getKey();
                java.util.List<worldwonders.model.Rating> newAggregates = aggregates.getValue();
                for (int i = 0; i < newAggregates.size(); i++) {
                    worldwonders.model.Rating oldAgg = oldAggregates.get(i);
                    worldwonders.model.Rating newAgg = newAggregates.get(i);

                    newAgg.URI = worldwonders.model.converters.RatingConverter.buildURI(arg.getKey(), newAgg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, aggregates -> {
            for (worldwonders.model.Rating agg : aggregates) {}
        }, agg -> {
            Rating _res = agg.__originalValue;
            agg.__originalValue = (Rating) agg.clone();
            if (_res != null) { return _res; }
            return null;
        });
    }

    public void serialize(final com.dslplatform.json.JsonWriter sw, final boolean minimal) {
        sw.writeByte(com.dslplatform.json.JsonWriter.OBJECT_START);
        if (minimal) {
            __serializeJsonObjectMinimal(this, sw, false);
        } else {
            __serializeJsonObjectFull(this, sw, false);
        }
        sw.writeByte(com.dslplatform.json.JsonWriter.OBJECT_END);
    }

    static void __serializeJsonObjectMinimal(
            final Rating self,
            com.dslplatform.json.JsonWriter sw,
            boolean hasWrittenProperty) {
        sw.writeAscii("\"URI\":");
        com.dslplatform.json.StringConverter.serializeShort(self.URI, sw);

        if (self.ID != 0) {
            sw.writeAscii(",\"ID\":", 6);
            com.dslplatform.json.NumberConverter.serialize(self.ID, sw);
        }

        if (self.user != null) {
            sw.writeAscii(",\"user\":", 8);
            sw.writeString(self.user);
        }

        if (!(self.comment.length() == 0)) {
            sw.writeAscii(",\"comment\":", 11);
            sw.writeString(self.comment);
        }

        if (self.score != 0) {
            sw.writeAscii(",\"score\":", 9);
            com.dslplatform.json.NumberConverter.serialize(self.score, sw);
        }

        if (self.ratedAt != java.time.OffsetDateTime.now(java.time.ZoneOffset.systemDefault())) {
            sw.writeAscii(",\"ratedAt\":", 11);
            com.dslplatform.json.JavaTimeConverter.serialize(self.ratedAt, sw);
        }
    }

    static void __serializeJsonObjectFull(
            final Rating self,
            com.dslplatform.json.JsonWriter sw,
            boolean hasWrittenProperty) {
        sw.writeAscii("\"URI\":");
        com.dslplatform.json.StringConverter.serializeShort(self.URI, sw);

        sw.writeAscii(",\"ID\":", 6);
        com.dslplatform.json.NumberConverter.serialize(self.ID, sw);

        if (self.user != null) {
            sw.writeAscii(",\"user\":", 8);
            sw.writeString(self.user);
        } else {
            sw.writeAscii(",\"user\":null", 12);
        }

        sw.writeAscii(",\"comment\":", 11);
        sw.writeString(self.comment);

        sw.writeAscii(",\"score\":", 9);
        com.dslplatform.json.NumberConverter.serialize(self.score, sw);

        sw.writeAscii(",\"ratedAt\":", 11);
        com.dslplatform.json.JavaTimeConverter.serialize(self.ratedAt, sw);
    }

    public static final com.dslplatform.json.JsonReader.ReadJsonObject<Rating> JSON_READER = new com.dslplatform.json.JsonReader.ReadJsonObject<Rating>() {
        @Override
        public Rating deserialize(final com.dslplatform.json.JsonReader reader) throws java.io.IOException {
            return new worldwonders.model.Rating(reader);
        }
    };

    private Rating(final com.dslplatform.json.JsonReader<org.revenj.patterns.ServiceLocator> reader)
            throws java.io.IOException {
        String _URI_ = "";
        this.__locator = java.util.Optional.ofNullable(reader.context);
        int _ID_ = 0;
        String _user_ = null;
        String _comment_ = "";
        int _score_ = 0;
        java.time.OffsetDateTime _ratedAt_ = org.revenj.Utils.MIN_DATE_TIME;
        byte nextToken = reader.last();
        if (nextToken != '}') {
            int nameHash = reader.fillName();
            nextToken = reader.getNextToken();
            if (nextToken == 'n') {
                if (reader.wasNull()) {
                    nextToken = reader.getNextToken();
                } else {
                    throw new java.io.IOException("Expecting 'u' (as null) at position " + reader.positionInStream()
                            + ". Found " + (char) nextToken);
                }
            } else {
                switch (nameHash) {
                    case 2053729053:
                        _URI_ = reader.readString();
                        nextToken = reader.getNextToken();
                        break;
                    case 1458105184:
                        _ID_ = com.dslplatform.json.NumberConverter.deserializeInt(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 1618501362:
                        _user_ = com.dslplatform.json.StringConverter.deserialize(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 1738982494:
                        _comment_ = com.dslplatform.json.StringConverter.deserialize(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case -768634731:
                        _score_ = com.dslplatform.json.NumberConverter.deserializeInt(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 435320340:
                        _ratedAt_ = com.dslplatform.json.JavaTimeConverter.deserializeDateTime(reader);
                        nextToken = reader.getNextToken();
                        break;
                    default:
                        nextToken = reader.skip();
                        break;
                }
            }
            while (nextToken == ',') {
                nextToken = reader.getNextToken();
                nameHash = reader.fillName();
                nextToken = reader.getNextToken();
                if (nextToken == 'n') {
                    if (reader.wasNull()) {
                        nextToken = reader.getNextToken();
                        continue;
                    } else {
                        throw new java.io.IOException("Expecting 'u' (as null) at position "
                                + reader.positionInStream() + ". Found " + (char) nextToken);
                    }
                }
                switch (nameHash) {
                    case 2053729053:
                        _URI_ = reader.readString();
                        nextToken = reader.getNextToken();
                        break;
                    case 1458105184:
                        _ID_ = com.dslplatform.json.NumberConverter.deserializeInt(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 1618501362:
                        _user_ = com.dslplatform.json.StringConverter.deserialize(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 1738982494:
                        _comment_ = com.dslplatform.json.StringConverter.deserialize(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case -768634731:
                        _score_ = com.dslplatform.json.NumberConverter.deserializeInt(reader);
                        nextToken = reader.getNextToken();
                        break;
                    case 435320340:
                        _ratedAt_ = com.dslplatform.json.JavaTimeConverter.deserializeDateTime(reader);
                        nextToken = reader.getNextToken();
                        break;
                    default:
                        nextToken = reader.skip();
                        break;
                }
            }
            if (nextToken != '}') { throw new java.io.IOException("Expecting '}' at position "
                    + reader.positionInStream() + ". Found " + (char) nextToken); }
        }

        this.URI = _URI_;
        this.ID = _ID_;
        this.user = _user_;
        this.comment = _comment_;
        this.score = _score_;
        this.ratedAt = _ratedAt_;
    }

    public static Object deserialize(final com.dslplatform.json.JsonReader<org.revenj.patterns.ServiceLocator> reader)
            throws java.io.IOException {
        switch (reader.getNextToken()) {
            case 'n':
                if (reader.wasNull()) return null;
                throw new java.io.IOException("Invalid null value found at: " + reader.positionInStream());
            case '{':
                reader.getNextToken();
                return new worldwonders.model.Rating(reader);
            case '[':
                return reader.deserializeNullableCollection(JSON_READER);
            default:
                throw new java.io.IOException("Invalid char value found at: " + reader.positionInStream()
                        + ". Expecting null, { or [. Found: " + (char) reader.last());
        }
    }

    public Rating(
            org.revenj.postgres.PostgresReader reader,
            int context,
            org.revenj.postgres.ObjectConverter.Reader<Rating>[] readers) throws java.io.IOException {
        this.__locator = reader.getLocator();
        for (org.revenj.postgres.ObjectConverter.Reader<Rating> rdr : readers) {
            rdr.read(this, reader, context);
        }
        URI = worldwonders.model.converters.RatingConverter.buildURI(reader, this);
        this.__originalValue = (Rating) this.clone();
    }

    public static void __configureConverter(org.revenj.postgres.ObjectConverter.Reader<Rating>[] readers, int __index___ID, int __index___user, int __index___comment, int __index___score, int __index___ratedAt) {
        readers[__index___ID] = (item, reader, context) -> { item.ID = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index___user] = (item, reader, context) -> { item.user = org.revenj.postgres.converters.StringConverter.parse(reader, context, true); return item; };
        readers[__index___comment] = (item, reader, context) -> { item.comment = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___score] = (item, reader, context) -> { item.score = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index___ratedAt] = (item, reader, context) -> { item.ratedAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }

    public static void __configureConverterExtended(org.revenj.postgres.ObjectConverter.Reader<Rating>[] readers, int __index__extended_ID, int __index__extended_user, int __index__extended_comment, int __index__extended_score, int __index__extended_ratedAt) {
        readers[__index__extended_ID] = (item, reader, context) -> { item.ID = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index__extended_user] = (item, reader, context) -> { item.user = org.revenj.postgres.converters.StringConverter.parse(reader, context, true); return item; };
        readers[__index__extended_comment] = (item, reader, context) -> { item.comment = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index__extended_score] = (item, reader, context) -> { item.score = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index__extended_ratedAt] = (item, reader, context) -> { item.ratedAt = org.revenj.postgres.converters.TimestampConverter.parseOffset(reader, context, false, false); return item; };
    }
}
