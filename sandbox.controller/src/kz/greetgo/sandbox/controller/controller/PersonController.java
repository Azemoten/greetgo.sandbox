package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.ClientsDisplay;
import kz.greetgo.sandbox.controller.model.PersonRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.register.PersonRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/person")
public class PersonController implements Controller {

  public BeanGetter<PersonRegister> personRegister;
  public BeanGetter<ClientRegister> clientRegister;

  @PublicAccess
  @ToJson
  @OnGet("/list")
  public List<PersonRecord> list() {
    return personRegister.get().list();
  }

  @PublicAccess
  @ToJson
  @OnGet("/listClients")
  public List<ClientsDisplay> listClients(){
    return clientRegister.get().listClients();
  }
}
