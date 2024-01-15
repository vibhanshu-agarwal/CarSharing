package carsharing;

import java.util.List;

public interface CustomerDao {
    //Save a customer name to the customer table in the database
    void saveCustomer(String name);

    List<Customer> getAllCustomers();

    void updateCustomerRentStatus(int id, Object o);

    //Get customer by name

}
