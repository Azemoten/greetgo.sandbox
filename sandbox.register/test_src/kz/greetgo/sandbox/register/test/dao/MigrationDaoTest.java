package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAccount;
import kz.greetgo.sandbox.controller.model.ClientAccountTransaction;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface MigrationDaoTest {
  @Insert("INSERT INTO public.migration_client(\n"
      + "cia_id, name, surname, patronymic, gender, birth, charm, inserted_at)\n"
      + "    VALUES (#{ciaId}, #{name}, #{surname}, #{patronymic}, #{gender}, #{birthDate}, #{charm}, #{insertedAt})\n")
  void insertMigrationClient(Client client);

  @Insert("INSERT INTO public.migration_address(\n"
      + "            cia_id, type, street, house, flat)\n"
      + "    VALUES (#{ciaId}, #{type}, #{street}, #{house}, #{flat})\n")
  void insertMigrationAddress(ClientAddr addr);

  @Insert("INSERT INTO public.migration_phone(\n"
      + "            cia_id, \"number\", type)\n"
      + "    VALUES (#{ciaId}, #{number}, #{type})\n")
  void insertMigrationPhone(ClientPhone phone);

  @Insert("INSERT INTO public.migration_account(\n"
      + " cia_id, registered_at, account_number, money)\n"
      + " VALUES (#{ciaId}, #{registered_at}, #{number}, #{money})\n")
  void insertMigrationAccount(ClientAccount account);

  @Insert("INSERT INTO public.migration_transactions(\n"
      + "money, finished_at, transaction_type, account_number\n"
      + ")\n"
      + "    VALUES (#{money}, #{finishedAt}, #{type}, #{accountNumber}\n"
      + ");")
  void insertMigrationTransaction(ClientAccountTransaction transaction);

  @Select("Select cia_id as ciaId, gender, surname, patronymic, birth as birthDate from migration_client where cia_id=#{ciaId} and actual=1")
  Client selectClient(String ciaId);

  @Select("Select cia_id as ciaId, type, street, house, flat from migration_address where cia_id=#{ciaId} and actual=1")
  ClientAddr selectAddr(String ciaId);

  @Select("select * from migration_phone where cia_id=#{ciaId} and actual=1")
  ClientPhone selectPhone(String ciaId);


  @Select("Select account_number as number, money, registered_at from migration_account where cia_id=#{ciaId}")
  ClientAccount selectAccount(String ciaId);

  @Select("select account_number as accountNumber, transaction_type as type, finished_at as finishedAt, money from migration_transactions where account_number=#{accountNumber}")
  ClientAccountTransaction selectTransaction(String accountNumber);

  @Update("update migration_client set actual=0")
  void deactualClients();


  @Update("update migration_address set actual=0")
  void deactualAddrs();

  @Update("update migration_phone set actual=0")
  void deactualPhones();
}
