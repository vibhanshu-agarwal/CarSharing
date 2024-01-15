package carsharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DBHelper {
  private static final String DEFAULT_DB_NAME = "carsharing";
  private static final String URL_TEMPLATE = "jdbc:h2:./src/carsharing/db/%s";

  private static final String CREATE_COMPANY_TABLE_SQL =
      "CREATE TABLE IF NOT EXISTS COMPANY "
          + "(ID INT PRIMARY KEY AUTO_INCREMENT,"
          + " NAME VARCHAR UNIQUE NOT NULL)";

  private static final String CREATE_CAR_TABLE_SQL =
      "CREATE TABLE IF NOT EXISTS CAR "
          + "(ID INT PRIMARY KEY AUTO_INCREMENT,"
          + " NAME VARCHAR UNIQUE NOT NULL,"
          + " COMPANY_ID INT NOT NULL,"
          + " FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";

  private static final String CREATE_CUSTOMER_TABLE_SQL =
      "CREATE TABLE IF NOT EXISTS CUSTOMER "
          + "(ID INT PRIMARY KEY AUTO_INCREMENT,"
          + " NAME VARCHAR UNIQUE NOT NULL,"
          + " RENTED_CAR_ID INT,"
          + " FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";

  private static final String ALTER_COMPANY_TABLE_SQL =
      "ALTER TABLE company ALTER COLUMN id RESTART WITH 1";

  private static final String DELETE_CAR_TABLE_SQL = "DELETE FROM CAR";
  private static final String DELETE_COMPANY_TABLE_SQL = "DELETE FROM COMPANY";
  private static final String DELETE_CUSTOMER_TABLE_SQL = "DELETE FROM CUSTOMER";



  static void dbSetup(String[] args) throws SQLException {
    String databaseName = getDatabaseNameFromArgs(args);

    try (Connection connection = establishConnection(databaseName);
        Statement statement = connection.createStatement()) {

      executeSqlStatement(statement, CREATE_COMPANY_TABLE_SQL);
      executeSqlStatement(statement, CREATE_CAR_TABLE_SQL);
      executeSqlStatement(statement, CREATE_CUSTOMER_TABLE_SQL);
      executeSqlStatement(statement, ALTER_COMPANY_TABLE_SQL);


      connection.setAutoCommit(true);
    }
  }

  static void clearDatabase(String[] args) throws SQLException {
    String databaseName = getDatabaseNameFromArgs(args);

    try (Connection connection = establishConnection(databaseName);
        Statement statement = connection.createStatement()) {

      executeCombinedSqlStatements(statement, DELETE_CAR_TABLE_SQL, DELETE_COMPANY_TABLE_SQL, DELETE_CUSTOMER_TABLE_SQL);

      connection.setAutoCommit(true);
    }
  }

  public static String getDatabaseNameFromArgs(String[] args) {
    if (args.length > 1 && args[1] != null && !args[1].isEmpty()) {
      return args[1];
    }
    return DEFAULT_DB_NAME;
  }

  public static Connection establishConnection(String databaseName) throws SQLException {
    String url = String.format(URL_TEMPLATE, databaseName);
    return DriverManager.getConnection(url);
  }

  private static void executeSqlStatement(Statement statement, String sql) throws SQLException {
    statement.executeUpdate(sql);
  }

  private static void executeCombinedSqlStatements(Statement statement, String... sqlCommands) {
    Arrays.stream(sqlCommands)
        .forEach(
            sql -> {
              try {
                statement.executeUpdate(sql);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            });
  }
}