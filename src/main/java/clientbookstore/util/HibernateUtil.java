package clientbookstore.util;

import clientbookstore.model.entity.Book;
import clientbookstore.model.entity.Customer;
import clientbookstore.model.entity.Order;
import clientbookstore.model.entity.RequestBook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private static Session session;

    static {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Book.class)
                    .buildMetadata()
                    .buildSessionFactory();

            session = sessionFactory.openSession();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
        return session;
    }

    public static void shutdown() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
    }
