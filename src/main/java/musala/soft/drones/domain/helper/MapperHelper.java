package musala.soft.drones.domain.helper;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/** The type Mapper helper. */
public final class MapperHelper {

  /**
   * Validate String format.
   *
   * @param regex the regex
   * @param code the code
   * @return the boolean
   */
  public static boolean validateFormat(final String regex, final String code) {
    return Pattern.compile(regex).matcher(code).matches();
  }

  /**
   * Sets value if not null.
   *
   * @param <V> the type parameter
   * @param value the value
   * @param setter the setter
   */
  public static <V> void setIfNotNull(final V value, final Consumer<V> setter) {
    if (Objects.nonNull(value)) {
      setter.accept(value);
    }
  }
}
