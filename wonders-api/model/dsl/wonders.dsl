module wonders
{
  aggregate Wonder(englishName) {
    String         englishName;
    WonderType     wonderType;
    List<String>   nativeNames;
    String?        description;

    Int?           ordinal;
    ImageInfo      imageInfo;

    Double         totalRatings;
    Double?        averageRating;
    List<Comment>  chosenComments;
  }

  value ImageInfo {
    URL      imageLink;
    Boolean  doubleWidth;
    Boolean  doubleHeight;
  }

  enum WonderType {
    Ancient;
    Modern;
  }

  value Comment {
    String?      user;
    String(140)  body;
    Double       rating;
    DateTime     createdAt;
  }

  event NewComment {
    String   wonderName;
    Comment  comment;

    Double   totalRatings;
    Double?  averageRating;
  }
}
