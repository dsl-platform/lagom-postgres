import java.util.Locale

package object worldwonders {
  implicit class PimpedEnum(val underlying: Enum[_]) extends AnyVal {
    def lowerName = underlying.name.toLowerCase(Locale.ENGLISH)
  }
}
