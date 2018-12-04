package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientsDisplay;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.Filter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dto.ClientEntity1;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;
import com.google.common.net.HttpHeaders;

import static org.fest.assertions.api.Assertions.assertThat;

public class GetRegisterImplTest extends ParentTestNg {

    public BeanGetter<ClientTestDao> clientTestDao;
    public BeanGetter<ClientRegister> clientRegister;

    final String baseUrl = "http://localhost:1313/sandbox/api/person/listClient";

    public enum Gender {
        MALE, FEMALE
    }

    @Test
    public void getClients() {
        //
        //
        Charm charm = ClientEntity1.getCharm();
        Client testClient = ClientEntity1.getClient(charm.id);
        //
        //
        //
        //
        clientTestDao.get().insertCharm(charm);
        clientTestDao.get().insertClient(testClient);
        //
        //
        List<ClientsDisplay> client = clientRegister.get().listClients(new Filter());
        //
        //
        assertThat(client).isNotNull();
    }

    //http://localhost:1313/sandbox/api/person/listClient

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsNext(){
        final Response response = RestAssured.get(baseUrl+"?page=0&size=2");
        final String uriToNextPage = ClientEntity1.extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertThat(baseUrl+"?page=1&size=2").isEqualTo(uriToNextPage);
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived(){
        final String url = baseUrl + "?page=10&size=10";
        final Response response = RestAssured.get(url);
        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenNoPreviousPage(){
        final Response response = RestAssured.get(baseUrl + "?page=0&size=2");
        final String uriToPrevPage = ClientEntity1.extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertThat(uriToPrevPage).isNull();
    }

    @Test
    public void whenSecondPageOfResourcesAreRetrieved_thenFirstPageIsPrevious() {
        final Response response = RestAssured.get(baseUrl+"?page=1&size=2");

        final String uriToPrevPage = ClientEntity1.extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertThat(baseUrl + "?page=0&size=2").isEqualTo(uriToPrevPage);
    }

    @Test
    public void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable(){
        final Response first = RestAssured.get(baseUrl +"?page=0&size=2");
        final String uriToLastPage = ClientEntity1.extractURIByRel(first.getHeader(HttpHeaders.LINK), "last");

        final Response response = RestAssured.get(uriToLastPage);
        final String uriToNextPage = ClientEntity1.extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertThat(uriToNextPage).isNull();
    }

}
