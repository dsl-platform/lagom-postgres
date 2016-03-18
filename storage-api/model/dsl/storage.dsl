module storage
{
  aggregate ImageCache(url) {
    URL       url;
    Int       size;
    Binary    body;
    Int?      width;
    Int?      height;
    DateTime  createdAt;
  }
}
