module model
{
  aggregate Wonder(englishName) {
    List<String>  nativeNames;
    String        englishName;
    Boolean       isAncient;
    URL?          imageLink;
    List<Rating>  *ratings;
  }

  aggregate Rating {
    String?   user;
    String    comment;
    Int       score;
    DateTime  ratedAt;
  }
}
