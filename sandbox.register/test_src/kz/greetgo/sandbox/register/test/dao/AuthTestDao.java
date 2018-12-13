package kz.greetgo.sandbox.register.test.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface AuthTestDao {

  @Insert("insert into Person (id, username, encoded_password, blocked) " +
      "values (#{id}, #{username}, #{encodedPassword}, 0)")
  void insertPerson(@Param("id") String id,
                    @Param("username") String username,
                    @Param("encodedPassword") String encodedPassword
  );

  @Update("update Person set ${fieldName} = #{fieldValue} where id = #{id}")
  void updatePersonField(@Param("id") String id,
                         @Param("fieldName") String fieldName,
                         @Param("fieldValue") Object fieldValue);

  @Insert(
      "insert into user_can (user_can, description) values (#{can}, 'description of '||#{can})" +
          " on conflict (user_can) do nothing")
  void upsert(@Param("can") String can);

  @Insert("insert into person_cans (person_id, user_can)" +
      " select p.id as person_id, #{can} as user_can" +
      " from person p, user_can where p.username = #{username} and p.blocked = 0" +
      " on conflict (person_id, user_can) do nothing")
  void personCan(@Param("username") String username, @Param("can") String can);
}
