package service;

import model.Customer;
import collection.CustomerCol;

import java.util.List;

public class CustomerService implements IService<Customer>  {
    private final CustomerCol customerCol;

    public CustomerService() {
        this.customerCol = new CustomerCol();
    }

    @Override
    public List<Customer> getAll() {
        return customerCol.getAll();
    }

    @Override
    public Customer getById(int id) {
        return customerCol.findById(id);
    }

    @Override
    public void add(Customer item) {
        Customer existing = customerCol.findById(item.getCustomerID());
        if (existing != null) {
            update(item);
        } else {
            customerCol.add(item);
        }
    }

    @Override
    public void update(Customer item) {
        customerCol.update(item);
    }
}
