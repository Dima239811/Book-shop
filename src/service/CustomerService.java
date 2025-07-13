package service;

import dependesies.annotation.Inject;
import dependesies.annotation.PostConstruct;
import model.Customer;
import collection.CustomerCol;

import java.util.List;

public class CustomerService implements IService<Customer>  {

    @Inject
    private CustomerCol customerCol;

//    public CustomerService() {
//        this.customerCol = new CustomerCol();
//    }

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
