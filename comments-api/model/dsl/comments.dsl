module comments
{
  event Comment {
    String       topic { Index; }
    String?      user;
    String(140)  body;
    Double       rating;

    specification findByTopic 'it => it.topic == topic' {
      String  topic;
    }
  }
}
