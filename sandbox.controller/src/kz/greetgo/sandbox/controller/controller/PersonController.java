package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ParPath;
import kz.greetgo.mvc.annotations.ParamsTo;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnDelete;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientSave;
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

  @PublicAccess
  @ToJson
  @OnGet("/listCharms")
  public List<Charm> listCharms() {
    return clientRegister.get().listCharms();
  }

  @PublicAccess
  @ToJson
  @OnDelete("/deleteClient/{clientId}")
  public void deleteClient(@ParPath("clientId") Integer id){
    clientRegister.get().deleteClient(id);
  }

  @PublicAccess
  @ToJson
  @OnPost("/createFullClient")
  public void createFullClient(@ParamsTo ClientSave saveClient){
    clientRegister.get().createFullClient(saveClient);
  }

}
