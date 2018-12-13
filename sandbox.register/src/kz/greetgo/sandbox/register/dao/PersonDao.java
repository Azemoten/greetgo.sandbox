package kz.greetgo.sandbox.register.dao;

import java.util.Calendar;
import java.util.Date;
import kz.greetgo.sandbox.controller.model.PersonRecord;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PersonDao {

  @Select("with cans as (\n" +
      "  select person_id, string_agg(user_can, ', ' order by user_can asc) as cans\n" +
      "  from person_cans\n" +
      "  group by person_id\n" +
      ")\n" +
      "\n" +
      "select id,\n" +
      "       surname||' '||substring(name from 1 for 1)||'. '||substring(patronymic from 1 for 1)||'.' as fio,\n"
      +
      "       username,\n" +
      "       to_char(birth_date, 'YYYY-MM-DD') as birthDate,\n" +
      "       cans\n" +
      "     from person\n" +
      "     left join cans pc on person_id = id\n" +
      "     where blocked = 0\n" +
      "     order by surname, name")
  List<PersonRecord> list();
}
class A{

  public static void main(String[] args) {
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    System.out.println(cal.get(Calendar.YEAR));
  }
}