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
import kz.greetgo.sandbox.controller.security.PublicAccess;
import kz.greetgo.sandbox.controller.util.Controller;

import java.util.List;

@Bean
@ControllerPrefix("/client")
public class ClientController implements Controller {

    public BeanGetter<ClientRegister> clientRegister;


    @PublicAccess
    @ToJson
    @OnGet("/list")
    public List<ClientRecord> list(@ParamsTo ClientFilter clientFilter) {
        return clientRegister.get().list(clientFilter);
    }

    @PublicAccess
    @ToJson
    @OnGet("/list/Charms")
    public List<Charm> listCharms() {
        return clientRegister.get().listCharms();
    }

    @PublicAccess
    @ToJson
    @OnDelete("/remove/{clientId}")
    public void remove(@ParPath("clientId") Integer id) {
        clientRegister.get().remove(id);
    }

    @PublicAccess
    @ToJson
    @OnPost("/create")
    public void create(@Par("clientDetail") @Json ClientDetail clientDetail) {
        clientRegister.get().create(clientDetail);
    }

    @PublicAccess
    @OnPost("/update")
    public void update(@Json @Par("clientDetail") ClientDetail clientDetail) {
        clientRegister.get().update(clientDetail);
    }


    @ToJson
    @PublicAccess
    @OnGet("/clientDetails/{clientId}")
    public ClientDetail clientDetails(@ParPath("clientId") int clientId) {
        return clientRegister.get().clientDetails(clientId);
    }
}
