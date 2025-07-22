package clientbookstore.service.entityService;

import clientbookstore.model.entity.Customer;
import clientbookstore.repo.CustomerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerService implements IService<Customer>  {

    @Autowired
    private final CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
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
            return customerDAO.findById(id);
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
