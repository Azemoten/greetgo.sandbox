package kz.greetgo.sandbox.register.migration;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLOutput;
import kz.greetgo.sandbox.register.migration.model.Account;
import kz.greetgo.sandbox.register.migration.model.Transaction;

public class JsonReader {

  Connection conn = new ConnectionToDB().getConnection();
  static int count = 0;


  public JsonReader() throws Exception {}

  public static void main(String[] args) throws IOException {
    Transaction transaction = new Transaction();
    Account account = new Account();
    String type = null;
    String accountNumber = null;

    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File("exam2.txt"));

    while (!parser.isClosed()) {
      JsonToken token = parser.nextToken();

      if (token == null) {
        break;
      }

      String indent = "";
      String a = parser.getCurrentName();
      String q = parser.getValueAsString();
      processJSONValue(token, parser, indent);
      System.out.println(count);
    }
  }

  private static void processJSONValue(JsonToken token, JsonParser parser, String indent)
      throws IOException {
    if (JsonToken.START_OBJECT.equals(token)) {
      processJSONObject(parser, indent);
    } else if (JsonToken.START_ARRAY.equals(token)) {
      processJSONArray(parser, indent);
    } else {
      System.out.println("" + "Value: " + parser.getValueAsString());
    }
  }

  private static void processJSONArray(JsonParser parser, String indent) throws IOException {
    System.out.println("" + "JSON Array");
    indent += " ";
    while (!parser.isClosed()) {
      JsonToken token = parser.nextToken();
      if (JsonToken.END_ARRAY.equals(token)) {
        // The en of the array has been reached
        break;
      }
      processJSONValue(token, parser, indent);
    }
  }

  private static void processJSONObject(JsonParser parser, String indent) throws IOException {
    System.out.println("" + "JSON Object");
    indent += " ";
    count++;
    while (!parser.isClosed()) {
      JsonToken token = parser.nextToken();
      if (JsonToken.END_OBJECT.equals(token)) {
        //The end of the JSON object has been reached
        break;
      }
      if (!JsonToken.FIELD_NAME.equals(token)) {
        System.out.println("Error. Expected a field name");
        break;
      }
      System.out.println(indent + "Field: " + parser.getCurrentName());
      token = parser.nextToken();
      processJSONValue(token, parser, indent);
    }
    System.out.println("EXIT ");
    //

    //
  }
}
