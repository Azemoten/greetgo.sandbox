package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.ParamsTo;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@ControllerPrefix("/setting")
public class SettingController implements Controller {

  public BeanGetter<ClientRegister> clientRegister;

  @PublicAccess
  @ToJson
  @OnGet("/numPage")
  public Integer numPage(@ParamsTo ClientFilter clientFilter) {
    return clientRegister.get().numPage(clientFilter);
  }
}
