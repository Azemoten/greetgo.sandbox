package kz.greetgo.sandbox.controller.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterUtil {

  public static <T> List<T> skipNulls(List<T> listWithNulls) {
    return listWithNulls.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }
}
