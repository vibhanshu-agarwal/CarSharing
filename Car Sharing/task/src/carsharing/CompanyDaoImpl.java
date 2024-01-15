package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {

  private String[] args;
  // Constructor using String[] args
  public CompanyDaoImpl(String[] args) {
    this.args = args;
  }
  @Override
  public void addCompany(String companyName) throws SQLException {
    String sql = "INSERT INTO COMPANY (NAME) VALUES (?)";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, companyName);
      pstmt.executeUpdate();
    }
  }

  @Override
  public List<Company> getAllCompanies() throws SQLException {
    List<Company> companies = new ArrayList<>();
    String sql = "SELECT * FROM COMPANY ORDER BY ID";
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Company company = new Company(id, name);
        companies.add(company);
      }
    }
    return companies;
  }

  // Implementing the method to get company by id using prepared statement
  @Override
  public Company getCompanyById(int companyId) throws SQLException {
    String sql = "SELECT * FROM COMPANY WHERE ID = ?";
    Company company = null;
    try (Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, companyId);
      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        company = new Company(id, name);
      }
    }
    return company;
  }

  private Connection getConnection() throws SQLException {
    //Initialize DB_URL from DBHelper
    String dbName = DBHelper.getDatabaseNameFromArgs(this.args);
    Connection conn = DBHelper.establishConnection(dbName);
    conn.setAutoCommit(true);
    return conn;
  }
}
