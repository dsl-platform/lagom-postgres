module storage
{
  aggregate ImageCache(url) {
    URL       url;

    Binary    body;
    Int       size;
    String    mimeType;

    Int?      width;
    Int?      height;
    DateTime  createdAt;
  }
}
