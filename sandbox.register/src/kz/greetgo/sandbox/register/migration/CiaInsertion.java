package kz.greetgo.sandbox.register.migration;

import java.io.File;
import java.security.InvalidParameterException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;

public class CiaInsertion {

  public static void main(String[] args)
      throws Exception {

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    XMLReader xmlReader = parser.getXMLReader();

    XMLHandler handler = new XMLHandler();
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("exam1.xml")));

  }
}
