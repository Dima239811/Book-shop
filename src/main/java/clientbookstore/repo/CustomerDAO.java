package clientbookstore.repo;

import clientbookstore.model.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO extends HibernateAbstractDao<Customer> {
    public CustomerDAO() {
        super(Customer.class);
    }
}
