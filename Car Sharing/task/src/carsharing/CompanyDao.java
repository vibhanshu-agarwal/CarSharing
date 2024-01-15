package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDao {
    //Add a company to the database
    void addCompany(String companyName) throws SQLException;

    //Get all the companies from the database ordered by their id
    List<Company> getAllCompanies() throws SQLException;

//Get the company by name
//    Company getCompanyByName(String companyName) throws SQLException;

    //Get the company by id
    Company getCompanyById(int companyId) throws SQLException;
}
