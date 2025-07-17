package clientbookstore.repo.dao;

import clientbookstore.model.Customer;
import clientbookstore.repo.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements GenericDAO<Customer> {
    @Override
    public void create(Customer object) throws SQLException {
        String sqlCreateCustomer = "INSERT INTO customer (fullname, phonenumber, age, email, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCreateCustomer)) {

            statement.setString(1, object.getFullName());
            statement.setString(2, object.getPhoneNumber());
            statement.setInt(3, object.getAge());
            statement.setString(4, object.getEmail());
            statement.setString(5, object.getAddress());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

        } catch (SQLException exception) {
            throw new SQLException("Fail create Customer " + exception);
        }
    }

    @Override
    public Customer findById(int id) throws SQLException {
        String sqlFindByIdCustomer = "select * from customer where id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlFindByIdCustomer)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getString("fullname"),
                        rs.getInt("age"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("id")
                );
            }

            else {
                return null;
            }

        } catch (SQLException exception) {
            throw new SQLException("Fail find Customer with id: " + id + exception);
        }
    }

    @Override
    public void update(Customer object) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    public List<Customer> getAll() throws SQLException {
        String sqlFindAllCustomer = "select * from customer";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlFindAllCustomer)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("fullname"),
                        rs.getInt("age"),
                        rs.getString("phonenumber"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("id")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            throw new SQLException("Failed to fetch all customers: " + e.getMessage(), e);
        }
        return customers;
    }
}
