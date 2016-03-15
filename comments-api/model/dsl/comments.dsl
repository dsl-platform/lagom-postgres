module comments
{
  event Comment {
    String       topic { Index; }
    String?      user;
    String(100)  title;
    String       body;
    Int          rating;

    specification findByTopic 'it => it.topic == topic' {
      String  topic;
    }
  }
}
