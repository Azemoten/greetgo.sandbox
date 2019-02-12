package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.sandbox.controller.errors.NullCiaIdException;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.register.util.SQL;

public class SqlMigrationProvider {

  //@Insert("INSERT INTO public.migration_client(\n"
  //      + "cia_id, name, surname, patronymic, gender, birth, charm, inserted_at)\n"
  //      + "    VALUES (#{ciaId}, #{name}, #{surname}, #{patronymic}, #{gender}, #{birthDate}, #{charm}, #{insertedAt})\n")
  public static String createClient(Client client) throws Exception {
    if (client.surname == null || client.name == null || client.ciaId == null) {
      throw new RuntimeException();
    }
//    if (client.ciaId == null) {
//      throw new NullCiaIdException();
//    }
    String sql = "INSERT INTO public.migration_client(" +
        "cia_id, name, surname, patronymic, gender, birth, charm, inserted_at, charm_text)" +
        "  VALUES (#{ciaId}, #{name}, #{surname}, #{patronymic}, #{gender}, #{birthDate}, #{charm}, #{insertedAt}, #{charm_text})";

    return sql;
  }
}

