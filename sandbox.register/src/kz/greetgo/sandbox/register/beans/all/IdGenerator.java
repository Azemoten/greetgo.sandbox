package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.depinject.core.Bean;

import java.security.SecureRandom;
import java.util.Random;

@Bean
public class IdGenerator {

  private static final String ENG = "abcdefghijklmnopqrstuvwxyz";
  private static final String DEG = "0123456789";
  private static final char[] ALL = (ENG.toLowerCase() + ENG.toUpperCase() + DEG).toCharArray();
  private static final int ALL_LENGTH = ALL.length;

  private static final int ID_LEN = 17;

  private final Random random = new SecureRandom();

  public String newId() {

    int[] indexes = random
        .ints(ALL_LENGTH)
        .map(i -> i < 0 ? -i : i)
        .map(i -> i % ALL_LENGTH)
        .toArray();

    char id[] = new char[ID_LEN];
    for (int i = 0; i < ID_LEN; i++) {
      id[i] = ALL[indexes[i]];
    }

    return new String(id);
  }
}
