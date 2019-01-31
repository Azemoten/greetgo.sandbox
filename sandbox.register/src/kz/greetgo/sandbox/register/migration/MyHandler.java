package kz.greetgo.sandbox.register.migration;

import java.util.List;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

  private List<ClientDetail> clientDetailList = null;
  private ClientDetail clientDetail = null;
  private StringBuilder data = null;

  public List<ClientDetail> getClientDetailList(){
    return clientDetailList;
  }

  boolean bClient = false;
  boolean bAddr = false;
  boolean bPhone = false;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes){
    if(qName.equalsIgnoreCase("client")){
      String id = attributes.getValue("id");

      clientDetail = new ClientDetail();

    }
  }
}
