package collection;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerCol implements ICollection<Customer> {
    private List<Customer> customers = new ArrayList<>();

//    public CustomerCol() {
//        this.customers = new ArrayList<>();
//    }

    @Override
    public void add(Customer item) {
        customers.add(item);
    }

    @Override
    public void update(Customer customer) {
        Customer existing = findById(customer.getCustomerID());
        if (existing != null) {
            existing.setFullName(customer.getFullName());
            existing.setAge(customer.getAge());
            existing.setPhoneNumber(customer.getPhoneNumber());
            existing.setAddress(customer.getAddress());
            existing.setEmail(customer.getEmail());
        }
    }

    @Override
    public Customer findById(int id) {
        return customers.stream()
                .filter(c -> c.getCustomerID() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> getAll() {
        return customers;
    }
}
