package kz.greetgo.sandbox.register.beans.all;

import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class IdGeneratorTest {

  @Test
  public void newId() {
    IdGenerator idGenerator = new IdGenerator();

    String id1 = idGenerator.newId();
    String id2 = idGenerator.newId();

    assertThat(id1).isNotNull();
    assertThat(id2).isNotNull();
    assertThat(id1).isNotEqualTo(id2);

    System.out.println("id1 = " + id1);
    System.out.println("id2 = " + id2);
  }
}