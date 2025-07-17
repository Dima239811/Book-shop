package clientbookstore.repo.util;

import clientbookstore.dependesies.context.ApplicationContext;
import clientbookstore.dependesies.factory.BeanFactory;
import clientbookstore.enums.OrderStatus;
import clientbookstore.enums.RequestStatus;
import clientbookstore.enums.StatusBook;
import clientbookstore.model.Book;
import clientbookstore.model.Customer;
import clientbookstore.model.Order;
import clientbookstore.model.RequestBook;
import clientbookstore.repo.dao.BookDAO;
import clientbookstore.repo.dao.CustomerDAO;
import clientbookstore.repo.dao.OrderDAO;
import clientbookstore.repo.dao.RequestBookDAO;
import clientbookstore.service.OrderService;
import clientbookstore.service.RequestBookService;
import clientbookstore.service.WareHouseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DBTest {
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContext();
            BeanFactory beanFactory = new BeanFactory(applicationContext);
            applicationContext.setBeanFactory(beanFactory);
            WareHouseService wareHouseService = applicationContext.getBean(WareHouseService.class);

//            RequestBookDAO requestBookDAO = applicationContext.getBean(RequestBookDAO.class);
//            RequestBook requestBook = new RequestBook(requestBookDAO.getCustomerDAO().findById(7), requestBookDAO.getBookDAO().findById(3));
//            requestBookDAO.create(requestBook);

            //System.out.println("find by id " + requestBookDAO.findById(1));

            //RequestBook requestBook1 = new RequestBook(requestBookDAO.getCustomerDAO().findById(1), requestBookDAO.getBookDAO().findById(3));
            //requestBook1.setId(1);
            //requestBookDAO.update(requestBook1);

            //System.out.println("все запросы " + requestBookDAO.getAll());
            BookDAO bookDAO = applicationContext.getBean(BookDAO.class);
            RequestBookService requestBookService = applicationContext.getBean(RequestBookService.class);
            requestBookService.closeRequest(bookDAO.findById(3));


        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        }
    }
}
