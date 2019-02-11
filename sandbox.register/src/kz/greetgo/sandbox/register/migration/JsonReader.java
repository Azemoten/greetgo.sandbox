package kz.greetgo.sandbox.register.migration;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;

public class JsonReader {

  Connection conn = new ConnectionToDB().getConnection();

  private static final Logger logger = Logger.getLogger(ClassName.class.getName());
  FileHandler testLog;
  FileHandler realLog;
  boolean invalidVal;
  String registeredAt;
  Integer transaction_id;
  String ciaId;
  String value;
  String type;
  String accountNumber;
  String finishedAt;
  Double money;
  String transaction;
  String sqlInsertNewAccount = "INSERT INTO public.migration_account(\n"
      + "            cia_id, registered_at, account_number)\n"
      + "    VALUES (?, ?, ?);";
  String sqlInsertTransaction = "INSERT INTO public.migration_transaction(\n"
      + "money, finished_at, transaction_type, account_number \n"
      + ")\n"
      + "    VALUES (?, ?, ?, ? \n"
      + ")";
  int batchSize = 5000;
  int currentAccountSize = 0;
  int currentTransactionSize = 0;
  PreparedStatement accountStmt = conn.prepareStatement(sqlInsertNewAccount);
  PreparedStatement transactionStmt = conn.prepareStatement(sqlInsertTransaction);


  public JsonReader(String fileName) throws Exception {

    testLog = new FileHandler("FrsTestLog.log", false);
    realLog = new FileHandler("FrsLog.log", true);
    logger.addHandler(testLog);
    logger.addHandler(realLog);
    SimpleFormatter formatter = new SimpleFormatter();
    testLog.setFormatter(formatter);
    realLog.setFormatter(formatter);
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(new File(fileName));
    conn.setAutoCommit(false);

    while (!parser.isClosed()) {
      JsonToken token = parser.nextToken();

      if (token == null) {
        break;
      }
      processJSONValue(token, parser, "");
    }
    accountStmt.executeBatch();
    transactionStmt.executeBatch();
    conn.commit();
    conn.close();
  }

  private void processJSONValue(JsonToken token, JsonParser parser, String indent)
      throws IOException, SQLException {
    String res = "";
    if (JsonToken.START_OBJECT.equals(token)) {
      processJSONObject(parser, indent);
    } else if (JsonToken.START_ARRAY.equals(token)) {
      processJSONArray(parser, indent);
    } else {
      value = parser.getValueAsString();
      System.out.println("" + "Value: " + parser.getValueAsString());
    }
  }

  private void processJSONArray(JsonParser parser, String indent) throws IOException, SQLException {
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

  private void processJSONObject(JsonParser parser, String indent)
      throws IOException, SQLException {
    System.out.println("" + "JSON Object");
    indent += " ";

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

      if ("money".equals(parser.getCurrentName())) {
        money = Double.parseDouble(value.replaceAll("_", ""));
      } else if ("transaction_type".equals(parser.getCurrentName())) {
        transaction = value;
      } else if ("type".equals(parser.getCurrentName())) {
        type = value;

      } else if ("finished_at".equals(parser.getCurrentName())) {
        finishedAt = value;

      } else if ("account_number".equals(parser.getCurrentName())) {
        if (Objects.nonNull(value) && !"".equals(value)) {
          accountNumber = value;
        } else {
          invalidVal = true;
        }
      } else if ("client_id".equals(parser.getCurrentName())) {
        if (Objects.nonNull(value) && !"".equals(value)) {
          ciaId = value;
        } else {
          invalidVal = true;
        }
      } else if ("registered_at".equals(parser.getCurrentName())) {
        registeredAt = value;
      }

    }

    System.out.println("WELL");
    try {
      if (invalidVal) {
        throw new InvalidParameterException("cia_id or account_number incorrect");
      }
      switch (type) {
        case "new_account":
          insertNewAccount();
          break;
        case "transaction":
          insertTransaction();
          break;
      }

    } catch (InvalidParameterException e) {
      logger.info("ID user" + ciaId);
      logger.info(" " + e);
    }
    clearProperties();

  }

  private void clearProperties() {
    registeredAt = null;
    ciaId = null;
    value = null;
    type = null;
    accountNumber = null;
    finishedAt = null;
    money = null;
    transaction = null;
    invalidVal = false;
    transaction_id = null;
  }

  private void insertTransaction() throws SQLException {
    //money, finished_at, transaction_type, account_number
    transactionStmt.setDouble(1, money);
    transactionStmt.setString(2, finishedAt);
    transactionStmt.setString(3, transaction);
    transactionStmt.setString(4, accountNumber);
    transactionStmt.addBatch();
    currentTransactionSize++;
    executeBatch("transaction");


  }

  private void insertNewAccount() throws SQLException {
    accountStmt.setString(1, ciaId);
    accountStmt.setString(2, registeredAt);
    accountStmt.setString(3, accountNumber);
    accountStmt.addBatch();
    currentAccountSize++;
    executeBatch("account");
  }

  private void executeBatch(String statement) throws SQLException {
    switch (statement) {
      case "account":
        if (currentAccountSize == batchSize) {
          accountStmt.executeBatch();
          currentAccountSize = 0;
        }
        break;
      case "transaction":
        if (currentTransactionSize == batchSize) {
          transactionStmt.executeBatch();
          currentTransactionSize = 0;
        }
        break;
    }
  }

}
