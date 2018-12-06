package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.*;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnDelete;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.sandbox.controller.model.*;
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
    @OnGet("/getList")
    public List<ClientRecord> getList(@ParamsTo ClientFilter clientFilter) {
        return clientRegister.get().getList(clientFilter);
    }

    @PublicAccess
    @ToJson
    @OnGet("/listCharms")
    public List<Charm> listCharms() {
        return clientRegister.get().listCharms();
    }

    @PublicAccess
    @ToJson
    @OnGet("/numPage")
    public Integer numPage(){
        return clientRegister.get().numPage();
    }


    @PublicAccess
    @ToJson
    @OnDelete("/deleteClient/{clientId}")
    public void deleteClient(@ParPath("clientId") Integer id) {
        clientRegister.get().deleteClient(id);
    }

    @PublicAccess
    @ToJson
    @OnPost("/createFullClient")
    public void createFullClient(@ParamsTo ClientSave saveClient) {
        clientRegister.get().createFullClient(saveClient);
    }



    @ToJson
    @PublicAccess
    @OnPost("/exCreate")
    public void exCreate(@RequestInput @Json Client client) {
        clientRegister.get().exCreate(client);
    }
}
