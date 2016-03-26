module comments
{
  event Comment {
    String       topic { Index; }
    String?      user;
    String(140)  body;
    Int          rating;

    specification findByTopic 'it => it.topic == topic' {
      String  topic;
    }
  }
}
