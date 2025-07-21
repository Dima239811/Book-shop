package clientbookstore.repo.dao;

import clientbookstore.model.entity.Customer;

public class CustomerDAO extends HibernateAbstractDao<Customer, Integer>{
    public CustomerDAO() {
        super(Customer.class);
    }
}
