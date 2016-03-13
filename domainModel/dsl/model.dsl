module model
{
  aggregate Wonder(englishName) {
    String        englishName;
    List<String>  nativeNames;
    Boolean       isAncient;
    URL           imageLink;
  }
}
