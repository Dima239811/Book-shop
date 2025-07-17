package clientbookstore.repo.util;

import clientbookstore.dependesies.context.ApplicationContext;
import clientbookstore.dependesies.factory.BeanFactory;
import clientbookstore.enums.OrderStatus;
import clientbookstore.enums.StatusBook;
import clientbookstore.model.Book;
import clientbookstore.model.Customer;
import clientbookstore.model.Order;
import clientbookstore.repo.dao.BookDAO;
import clientbookstore.repo.dao.CustomerDAO;
import clientbookstore.repo.dao.OrderDAO;
import clientbookstore.service.OrderService;
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

            OrderService orderService = applicationContext.getBean(OrderService.class);
            //Order order = new Order(new BookDAO().findById(3), new CustomerDAO().findById(7), new Date(), 120, OrderStatus.COMPLETED);
            //order.setOrderId(1);
            //orderService.changeOrderStatus(1, OrderStatus.COMPLETED);
            //orderService.add(order);
            //System.out.println("все заказы");
            //System.out.println(orderService.getAll());
            LocalDate start = LocalDate.of(2025, 5, 17); // 17 мая 2025
            LocalDate end = LocalDate.of(2025, 7, 25);   // 25 июля 2025

            // Конвертируем в Date (если нужно)
            Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

            System.out.println("Заказы: " +
                    orderService.getCountPerformedOrder(startDate, endDate));


        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        }
    }
}
