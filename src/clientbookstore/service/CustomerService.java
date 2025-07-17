package clientbookstore.service;

import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.dependesies.annotation.PostConstruct;
import clientbookstore.model.Customer;
import clientbookstore.collection.CustomerCol;
import clientbookstore.repo.dao.CustomerDAO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CustomerService implements IService<Customer>  {

    @Inject
    private CustomerDAO customerDAO;
    //private CustomerCol customerCol;

    @PostConstruct
    public void postConstruct() {
        System.out.println("CustomerService has been inizialized");
    }

    @Override
    public List<Customer> getAll() {
        try {
            List<Customer> customers = customerDAO.getAll();
            return customers == null ? Collections.emptyList() : customers;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all customers in customer service" + e.getMessage(), e);
        }
    }

    // вернет null если не найдет
    @Override
    public Customer getById(int id) {
        try {
            Customer customer = customerDAO.findById(id);
            return customer;

        } catch (SQLException e) {
            throw new RuntimeException("in customer service: Failed to find customer with ID " + id, e);
        }
    }

    @Override
    public void add(Customer item) {
        try {
            customerDAO.create(item);
        } catch (SQLException e) {
            throw new RuntimeException("in customer service: Failed to fetch customer with ID " + item.getCustomerID(), e);
        }
    }

    @Override
    public void update(Customer item) {
        try {
            customerDAO.update(item);
        } catch (SQLException e) {
            throw new RuntimeException("in customer service: Failed to update customer with ID " + item.getCustomerID(), e);
        }
    }
}
