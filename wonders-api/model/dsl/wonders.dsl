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
    String(100)  title;
    String       body;
    Int          rating;
    DateTime     createdAt;
  }

  struct NewComment {
    String   wonderName;
    Int      totalRatings;
    Double   averageRating;
    Comment  comment;
  }
}
