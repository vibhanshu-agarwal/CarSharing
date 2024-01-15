package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDao {
    void addCar(String carName, int companyId) throws SQLException;
    List<Car> getAllCarsByCompany(int companyId) throws SQLException;
    Car getRentedCar(int customerId) throws SQLException;

    Car getCarById(Integer rentedCarId) throws SQLException;

    List<Car> getAllAvailableCarsByCompany(int id) throws SQLException;

//    void rentCar(int id) throws SQLException;
}
