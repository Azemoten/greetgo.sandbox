package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.PersonRecord;
import kz.greetgo.sandbox.controller.register.PersonRegister;
import kz.greetgo.sandbox.register.dao.PersonDao;

import java.util.List;

@Bean
public class PersonRegisterImpl implements PersonRegister {

  public BeanGetter<PersonDao> personDao;

  @Override
  public List<PersonRecord> list() {
    return personDao.get().list();
  }
}
