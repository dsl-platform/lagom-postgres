/*
* Created by DSL Platform
* v1.5.5912.31151
*/

package worldwonders.wonders;

public final class NewComment implements java.lang.Cloneable, java.io.Serializable {
    public NewComment(
            final String wonderName,
            final int totalRatings,
            final double averageRating,
            final worldwonders.wonders.Comment comment) {
        setWonderName(wonderName);
        setTotalRatings(totalRatings);
        setAverageRating(averageRating);
        setComment(comment);
    }

    public NewComment() {
        this.wonderName = "";
        this.totalRatings = 0;
        this.averageRating = 0.0;
        this.comment = new worldwonders.wonders.Comment();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + 863417366;
        result = prime * result + (this.wonderName.hashCode());
        result = prime * result + (this.totalRatings);
        result = prime * result + (Double.valueOf(this.averageRating).hashCode());
        result = prime * result + (this.comment.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NewComment)) return false;
        return deepEquals((NewComment) obj);
    }

    public boolean deepEquals(final NewComment other) {
        if (other == null) return false;

        if (!(this.wonderName.equals(other.wonderName))) return false;
        if (!(this.totalRatings == other.totalRatings)) return false;
        if (!(Double.doubleToLongBits(this.averageRating) == Double.doubleToLongBits(other.averageRating)))
            return false;
        if (!(this.comment.equals(other.comment))) return false;
        return true;
    }

    private NewComment(NewComment other) {
        this.wonderName = other.wonderName;
        this.totalRatings = other.totalRatings;
        this.averageRating = other.averageRating;
        this.comment = other.comment;
    }

    @Override
    public Object clone() {
        return new NewComment(this);
    }

    @Override
    public String toString() {
        return "NewComment(" + wonderName + ',' + totalRatings + ',' + averageRating + ',' + comment + ')';
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    private NewComment(
            @com.fasterxml.jackson.annotation.JsonProperty("_helper") final boolean _helper,
            @com.fasterxml.jackson.annotation.JsonProperty("wonderName") final String wonderName,
            @com.fasterxml.jackson.annotation.JsonProperty("totalRatings") final int totalRatings,
            @com.fasterxml.jackson.annotation.JsonProperty("averageRating") final double averageRating,
            @com.fasterxml.jackson.annotation.JsonProperty("comment") final worldwonders.wonders.Comment comment) {
        this.wonderName = wonderName == null ? "" : wonderName;
        this.totalRatings = totalRatings;
        this.averageRating = averageRating;
        this.comment = comment == null ? new worldwonders.wonders.Comment() : comment;
    }

    private static final long serialVersionUID = -5802441012548522755L;

    private String wonderName;

    @com.fasterxml.jackson.annotation.JsonProperty("wonderName")
    public String getWonderName() {
        return wonderName;
    }

    public NewComment setWonderName(final String value) {
        if (value == null) throw new IllegalArgumentException("Property \"wonderName\" cannot be null!");
        this.wonderName = value;

        return this;
    }

    private int totalRatings;

    @com.fasterxml.jackson.annotation.JsonProperty("totalRatings")
    public int getTotalRatings() {
        return totalRatings;
    }

    public NewComment setTotalRatings(final int value) {
        this.totalRatings = value;

        return this;
    }

    private double averageRating;

    @com.fasterxml.jackson.annotation.JsonProperty("averageRating")
    public double getAverageRating() {
        return averageRating;
    }

    public NewComment setAverageRating(final double value) {
        this.averageRating = value;

        return this;
    }

    private worldwonders.wonders.Comment comment;

    @com.fasterxml.jackson.annotation.JsonProperty("comment")
    public worldwonders.wonders.Comment getComment() {
        return comment;
    }

    public NewComment setComment(final worldwonders.wonders.Comment value) {
        if (value == null) throw new IllegalArgumentException("Property \"comment\" cannot be null!");
        this.comment = value;

        return this;
    }
}
