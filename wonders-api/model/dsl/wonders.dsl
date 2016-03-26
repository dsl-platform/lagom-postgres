module wonders
{
  aggregate Wonder(englishName) {
    String         englishName;
    List<String>   nativeNames;
    Boolean        isAncient;
    URL?           imageLink;

    Int            totalRatings;
    Double         averageRating;
    List<Comment>  chosenComments;
  }

  value Comment {
    String?      user;
    String(140)  body;
    Int          rating;
    DateTime     createdAt;
  }

  event NewComment {
    String   wonderName;
    Int      totalRatings;
    Double   averageRating;
    Comment  comment;
  }
}
