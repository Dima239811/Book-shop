package clientbookstore.repo.util;

import clientbookstore.model.Customer;
import clientbookstore.repo.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            Customer customer = new Customer("Dima new", 19, "+795555", "email", "adress");
            CustomerDAO customerDAO = new CustomerDAO();
            //customer.setCustomerID(1);
            customerDAO.create(customer);
            System.out.println(customerDAO.findById(1));
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения: " + e.getMessage());
        }
    }
}
