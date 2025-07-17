package clientbookstore.repo.util;

import clientbookstore.model.Customer;
import clientbookstore.repo.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            //Customer customer = new Customer("Dima", 19, "+795555", "email", "adress");
            CustomerDAO customerDAO = new CustomerDAO();

            System.out.println(customerDAO.getAll());
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения: " + e.getMessage());
        }
    }
}
