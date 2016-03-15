/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.wonders;

public class Wonder implements java.lang.Cloneable, java.io.Serializable, org.revenj.patterns.AggregateRoot {
    public Wonder() {
        URI = java.lang.Integer.toString(System.identityHashCode(this));
        this.englishName = "";
        this.nativeNames = new java.util.ArrayList<String>(4);
        this.isAncient = false;
        this.totalRatings = 0;
        this.averageRating = 0.0;
        this.chosenComments = new java.util.ArrayList<worldwonders.wonders.Comment>(4);
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
        if (obj == null || obj instanceof Wonder == false) return false;
        final Wonder other = (Wonder) obj;
        return URI.equals(other.URI);
    }

    public boolean deepEquals(final Wonder other) {
        if (this == other) return true;
        if (other == null) return false;
        if (!URI.equals(other.URI)) return false;

        if (!(this.englishName.equals(other.englishName))) return false;
        if (!((this.nativeNames == other.nativeNames || this.nativeNames != null
                && this.nativeNames.equals(other.nativeNames)))) return false;
        if (!(this.isAncient == other.isAncient)) return false;
        if (!(this.imageLink == other.imageLink || this.imageLink != null && this.imageLink.equals(other.imageLink)))
            return false;
        if (!(this.totalRatings == other.totalRatings)) return false;
        if (!(Double.doubleToLongBits(this.averageRating) == Double.doubleToLongBits(other.averageRating)))
            return false;
        if (!((this.chosenComments == other.chosenComments || this.chosenComments != null
                && this.chosenComments.equals(other.chosenComments)))) return false;
        return true;
    }

    private Wonder(Wonder other) {
        this.URI = other.URI;
        this.__locator = other.__locator;
        this.englishName = other.englishName;
        this.nativeNames = new java.util.ArrayList<String>(other.nativeNames);
        this.isAncient = other.isAncient;
        this.imageLink = other.imageLink;
        this.totalRatings = other.totalRatings;
        this.averageRating = other.averageRating;
        this.chosenComments = new java.util.ArrayList<worldwonders.wonders.Comment>(other.chosenComments.size());
        if (other.chosenComments != null) {
            for (worldwonders.wonders.Comment it : other.chosenComments) {
                this.chosenComments.add((worldwonders.wonders.Comment) it.clone());
            }
        };
        this.__originalValue = other.__originalValue;
    }

    @Override
    public Object clone() {
        return new Wonder(this);
    }

    @Override
    public String toString() {
        return "Wonder(" + URI + ')';
    }

    public Wonder(
            final String englishName,
            final java.util.List<String> nativeNames,
            final boolean isAncient,
            final java.net.URI imageLink,
            final int totalRatings,
            final double averageRating,
            final java.util.List<worldwonders.wonders.Comment> chosenComments) {
        setEnglishName(englishName);
        setNativeNames(nativeNames);
        setIsAncient(isAncient);
        setImageLink(imageLink);
        setTotalRatings(totalRatings);
        setAverageRating(averageRating);
        setChosenComments(chosenComments);
        this.URI = this.englishName;
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    private Wonder(
            @com.fasterxml.jackson.annotation.JsonProperty("URI") final String URI,
            @com.fasterxml.jackson.annotation.JacksonInject("__locator") final org.revenj.patterns.ServiceLocator __locator,
            @com.fasterxml.jackson.annotation.JsonProperty("englishName") final String englishName,
            @com.fasterxml.jackson.annotation.JsonProperty("nativeNames") final java.util.List<String> nativeNames,
            @com.fasterxml.jackson.annotation.JsonProperty("isAncient") final boolean isAncient,
            @com.fasterxml.jackson.annotation.JsonProperty("imageLink") final java.net.URI imageLink,
            @com.fasterxml.jackson.annotation.JsonProperty("totalRatings") final int totalRatings,
            @com.fasterxml.jackson.annotation.JsonProperty("averageRating") final double averageRating,
            @com.fasterxml.jackson.annotation.JsonProperty("chosenComments") final java.util.List<worldwonders.wonders.Comment> chosenComments) {
        this.URI = URI != null ? URI : new java.util.UUID(0L, 0L).toString();
        this.__locator = java.util.Optional.ofNullable(__locator);
        this.englishName = englishName == null ? "" : englishName;
        this.nativeNames = nativeNames == null ? new java.util.ArrayList<String>(4) : nativeNames;
        this.isAncient = isAncient;
        this.imageLink = imageLink;
        this.totalRatings = totalRatings;
        this.averageRating = averageRating;
        this.chosenComments = chosenComments == null
                ? new java.util.ArrayList<worldwonders.wonders.Comment>(4)
                : chosenComments;
    }

    private transient java.util.Optional<org.revenj.patterns.ServiceLocator> __locator = java.util.Optional.empty();
    private static final long serialVersionUID = -1318175053909040955L;

    private String englishName;

    @com.fasterxml.jackson.annotation.JsonProperty("englishName")
    public String getEnglishName() {
        return englishName;
    }

    public Wonder setEnglishName(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"englishName\" cannot be null!");
        this.englishName = value;

        return this;
    }

    private java.util.List<String> nativeNames;

    @com.fasterxml.jackson.annotation.JsonProperty("nativeNames")
    public java.util.List<String> getNativeNames() {
        return nativeNames;
    }

    public Wonder setNativeNames(final java.util.List<String> value) {
        if (value == null) throw new IllegalArgumentException("Property \"nativeNames\" cannot be null!");
        org.revenj.Guards.checkNulls(value);
        this.nativeNames = value;

        return this;
    }

    private boolean isAncient;

    @com.fasterxml.jackson.annotation.JsonProperty("isAncient")
    public boolean getIsAncient() {
        return isAncient;
    }

    public Wonder setIsAncient(final boolean value) {
        this.isAncient = value;

        return this;
    }

    private java.net.URI imageLink;

    @com.fasterxml.jackson.annotation.JsonProperty("imageLink")
    public java.net.URI getImageLink() {
        return imageLink;
    }

    public Wonder setImageLink(final java.net.URI value) {
        this.imageLink = value;

        return this;
    }

    private int totalRatings;

    @com.fasterxml.jackson.annotation.JsonProperty("totalRatings")
    public int getTotalRatings() {
        return totalRatings;
    }

    public Wonder setTotalRatings(final int value) {
        this.totalRatings = value;

        return this;
    }

    private double averageRating;

    @com.fasterxml.jackson.annotation.JsonProperty("averageRating")
    public double getAverageRating() {
        return averageRating;
    }

    public Wonder setAverageRating(final double value) {
        this.averageRating = value;

        return this;
    }

    private java.util.List<worldwonders.wonders.Comment> chosenComments;

    @com.fasterxml.jackson.annotation.JsonProperty("chosenComments")
    public java.util.List<worldwonders.wonders.Comment> getChosenComments() {
        return chosenComments;
    }

    public Wonder setChosenComments(final java.util.List<worldwonders.wonders.Comment> value) {
        if (value == null) throw new IllegalArgumentException("Property \"chosenComments\" cannot be null!");
        org.revenj.Guards.checkNulls(value);
        this.chosenComments = value;

        return this;
    }

    private transient Wonder __originalValue;

    static {
        worldwonders.wonders.repositories.WonderRepository.__setupPersist((aggregates, arg) -> {
            try {
                for (worldwonders.wonders.Wonder agg : aggregates) {
                    agg.URI = worldwonders.wonders.converters.WonderConverter.buildURI(arg.getKey(), agg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, (aggregates, arg) -> {
            try {
                java.util.List<worldwonders.wonders.Wonder> oldAggregates = aggregates.getKey();
                java.util.List<worldwonders.wonders.Wonder> newAggregates = aggregates.getValue();
                for (int i = 0; i < newAggregates.size(); i++) {
                    worldwonders.wonders.Wonder oldAgg = oldAggregates.get(i);
                    worldwonders.wonders.Wonder newAgg = newAggregates.get(i);

                    newAgg.URI = worldwonders.wonders.converters.WonderConverter.buildURI(arg.getKey(), newAgg);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }, aggregates -> {
            for (worldwonders.wonders.Wonder agg : aggregates) {}
        }, agg -> {
            Wonder _res = agg.__originalValue;
            agg.__originalValue = (Wonder) agg.clone();
            if (_res != null) { return _res; }
            return null;
        });
    }

    public Wonder(
            org.revenj.postgres.PostgresReader reader,
            int context,
            org.revenj.postgres.ObjectConverter.Reader<Wonder>[] readers) throws java.io.IOException {
        this.__locator = reader.getLocator();
        for (org.revenj.postgres.ObjectConverter.Reader<Wonder> rdr : readers) {
            rdr.read(this, reader, context);
        }
        URI = worldwonders.wonders.converters.WonderConverter.buildURI(reader, this);
        this.__originalValue = (Wonder) this.clone();
    }

    public static void __configureConverter(org.revenj.postgres.ObjectConverter.Reader<Wonder>[] readers, int __index___englishName, int __index___nativeNames, int __index___isAncient, int __index___imageLink, int __index___totalRatings, int __index___averageRating, worldwonders.wonders.converters.CommentConverter __converter_chosenComments, int __index___chosenComments) {
        readers[__index___englishName] = (item, reader, context) -> { item.englishName = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index___nativeNames] = (item, reader, context) -> { { java.util.List<String> __list = org.revenj.postgres.converters.StringConverter.parseCollection(reader, context, false); if(__list != null) {item.nativeNames = __list;} else item.nativeNames = new java.util.ArrayList<String>(4); }; return item; };
        readers[__index___isAncient] = (item, reader, context) -> { item.isAncient = org.revenj.postgres.converters.BoolConverter.parse(reader); return item; };
        readers[__index___imageLink] = (item, reader, context) -> { item.imageLink = org.revenj.postgres.converters.UrlConverter.parse(reader, context); return item; };
        readers[__index___totalRatings] = (item, reader, context) -> { item.totalRatings = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index___averageRating] = (item, reader, context) -> { item.averageRating = org.revenj.postgres.converters.DoubleConverter.parse(reader); return item; };
        readers[__index___chosenComments] = (item, reader, context) -> { { java.util.List<worldwonders.wonders.Comment> __list = org.revenj.postgres.converters.ArrayTuple.parse(reader, context, __converter_chosenComments::from); if (__list != null) {item.chosenComments = __list;} else item.chosenComments = new java.util.ArrayList<worldwonders.wonders.Comment>(4); }; return item; };
    }

    public static void __configureConverterExtended(org.revenj.postgres.ObjectConverter.Reader<Wonder>[] readers, int __index__extended_englishName, int __index__extended_nativeNames, int __index__extended_isAncient, int __index__extended_imageLink, int __index__extended_totalRatings, int __index__extended_averageRating, final worldwonders.wonders.converters.CommentConverter __converter_chosenComments, int __index__extended_chosenComments) {
        readers[__index__extended_englishName] = (item, reader, context) -> { item.englishName = org.revenj.postgres.converters.StringConverter.parse(reader, context, false); return item; };
        readers[__index__extended_nativeNames] = (item, reader, context) -> { { java.util.List<String> __list = org.revenj.postgres.converters.StringConverter.parseCollection(reader, context, false); if(__list != null) {item.nativeNames = __list;} else item.nativeNames = new java.util.ArrayList<String>(4); }; return item; };
        readers[__index__extended_isAncient] = (item, reader, context) -> { item.isAncient = org.revenj.postgres.converters.BoolConverter.parse(reader); return item; };
        readers[__index__extended_imageLink] = (item, reader, context) -> { item.imageLink = org.revenj.postgres.converters.UrlConverter.parse(reader, context); return item; };
        readers[__index__extended_totalRatings] = (item, reader, context) -> { item.totalRatings = org.revenj.postgres.converters.IntConverter.parse(reader); return item; };
        readers[__index__extended_averageRating] = (item, reader, context) -> { item.averageRating = org.revenj.postgres.converters.DoubleConverter.parse(reader); return item; };
        readers[__index__extended_chosenComments] = (item, reader, context) -> { { java.util.List<worldwonders.wonders.Comment> __list = org.revenj.postgres.converters.ArrayTuple.parse(reader, context, __converter_chosenComments::fromExtended); if (__list != null) {item.chosenComments = __list;} else item.chosenComments = new java.util.ArrayList<worldwonders.wonders.Comment>(4); }; return item; };
    }
}
