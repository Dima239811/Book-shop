package clientbookstore.service;

import clientbookstore.dependesies.annotation.Inject;
import clientbookstore.dependesies.annotation.PostConstruct;
import clientbookstore.model.Customer;
import clientbookstore.collection.CustomerCol;

import java.util.List;

public class CustomerService implements IService<Customer>  {

    @Inject
    private CustomerCol customerCol;

    @PostConstruct
    public void postConstruct() {
        System.out.println("CustomerService has been inizialized");
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
