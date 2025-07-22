package clientbookstore.ui;

import clientbookstore.controller.MainContr;
import javax.annotation.PostConstruct;
import clientbookstore.ui.action_factory.ActionFactory;
import clientbookstore.ui.menu_items.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Builder {
    private Menu rootMenu;

    @Autowired
    private MainContr dataManager;

    @Autowired
    private ActionFactory actionFactory;

    @PostConstruct
    public void init() {
        buildMenu();
    }

    public void buildMenu() {
        rootMenu = new Menu("Главное меню");

        Menu booksMenu = new Menu("Книги");


        booksMenu.addMenuItem(new MenuItem("Добавить книгу", actionFactory.createAddBookAction(),
                null));
        booksMenu.addMenuItem(new MenuItem("Списать книгу", actionFactory.writeOffBookAction(),
                null));

        // Подменю для просмотра и сортировки книг
        Menu bookListViewMenu = new Menu("Просмотр книг");
        bookListViewMenu.addMenuItem(new MenuItem("Показать все книги", actionFactory.allBooksListAction(),
                null));

        // Подменю для сортировки
        Menu sortMenu = new Menu("Сортировка книг");
        sortMenu.addMenuItem(new MenuItem("По названию (А-Я)", actionFactory.sortBooksByTitleAction(), null));
        sortMenu.addMenuItem(new MenuItem("По цене (дешевые первые)", actionFactory.sortBooksByPriceAction(), null));
        sortMenu.addMenuItem(new MenuItem("По году", actionFactory.sortBooksByYearDescAction(), null));
        sortMenu.addMenuItem(new MenuItem("По наличию на складе", actionFactory.sortBooksByAvailiable(), null));

        bookListViewMenu.addMenuItem(new MenuItem("Сортировка", null, sortMenu));
        booksMenu.addMenuItem(new MenuItem("Просмотр книг", null, bookListViewMenu));

        rootMenu.addMenuItem(new MenuItem("Операции с книгами", null, booksMenu));

        // клиенты
        Menu customersMenu = new Menu("Работа с клиентами");
        customersMenu.addMenuItem(new MenuItem("Просмотр всех клиентов",
                actionFactory.showAllCustomer(), null));
        customersMenu.addMenuItem(new MenuItem("Добавить клиента", actionFactory.addCustomerAction(), null));

        rootMenu.addMenuItem(new MenuItem("Клиенты", null, customersMenu));

        // Меню для заказов
        Menu ordersMenu = new Menu("Работа с заказами");
        ordersMenu.addMenuItem(new MenuItem("Создать заказ", actionFactory.createOrderAction(), null));
        ordersMenu.addMenuItem(new MenuItem("Отменить заказ", actionFactory.cancelOrderAction(), null));
        ordersMenu.addMenuItem(new MenuItem("Изменить статус заказа", actionFactory.changeOrderStatusAction(), null));

        // Подменю для просмотра заказов с сортировкой
        Menu viewOrdersMenu = new Menu("Просмотр заказов");
        viewOrdersMenu.addMenuItem(new MenuItem("Список всех заказов", actionFactory.showAllOrdersAction(), null));

        Menu sortOrdersMenu = new Menu("Сортировка заказов");
        sortOrdersMenu.addMenuItem(new MenuItem("По дате исполнения", actionFactory.sortOrdersByDateAction(), null));
        sortOrdersMenu.addMenuItem(new MenuItem("По цене", actionFactory.sortOrdersByPriceAction(), null));
        sortOrdersMenu.addMenuItem(new MenuItem("По статусу", actionFactory.sortOrdersByStatusAction(), null));

        viewOrdersMenu.addMenuItem(new MenuItem("Сортировка", null, sortOrdersMenu));

        ordersMenu.addMenuItem(new MenuItem("Просмотр заказов", null, viewOrdersMenu));

        rootMenu.addMenuItem(new MenuItem("Заказы", null, ordersMenu));


        Menu requestsMenu = new Menu("Запросы на книги");
        requestsMenu.addMenuItem(new MenuItem("Создать запрос", actionFactory.createBookRequestAction(), null));

        // Подменю для просмотра запросов
        Menu viewRequestsMenu = new Menu("Просмотр запросов");
        viewRequestsMenu.addMenuItem(new MenuItem("Все запросы", actionFactory.showAllBookRequestsAction(), null));

        // Подменю для сортировки запросов
        Menu sortRequestsMenu = new Menu("Сортировка запросов");
        sortRequestsMenu.addMenuItem(new MenuItem("По количеству запросов", actionFactory.sortRequestsByCountAction(), null));
        sortRequestsMenu.addMenuItem(new MenuItem("По алфавиту (название книги)", actionFactory.sortRequestsByTitleAction(), null));

        viewRequestsMenu.addMenuItem(new MenuItem("Сортировка", null, sortRequestsMenu));
        requestsMenu.addMenuItem(new MenuItem("Просмотр запросов", null, viewRequestsMenu));

        rootMenu.addMenuItem(new MenuItem("Запросы на книги", null, requestsMenu));

        // Отчеты и аналитика ---
        Menu reportsMenu = new Menu("Отчеты и аналитика");

        // подменю для аналитики по книгам
        Menu booksAnalyticsMenu = new Menu("Аналитика по книгам");
        booksAnalyticsMenu.addMenuItem(new MenuItem(
                "Залежавшиеся книги",
                actionFactory.showStaleBooksAction(),
                null
        ));

        // Подменю для отчетов по выполненным заказам
        Menu completedOrdersMenu = new Menu("Выполненные заказы за период");
        completedOrdersMenu.addMenuItem(new MenuItem("Сортировка по дате", actionFactory.sortCompletedOrdersByDateAction(), null));
        completedOrdersMenu.addMenuItem(new MenuItem("Сортировка по цене", actionFactory.sortCompletedOrdersByPriceAction(), null));

        // Подменю для аналитики
        Menu analyticsMenu = new Menu("Аналитика");
        analyticsMenu.addMenuItem(new MenuItem("Сумма заработанных средств", actionFactory.showTotalRevenueAction(), null));
        analyticsMenu.addMenuItem(new MenuItem("Кол-во выполненных заказов", actionFactory.showCompletedOrdersCountAction(), null));

        reportsMenu.addMenuItem(new MenuItem("Выполненные заказы", null, completedOrdersMenu));
        reportsMenu.addMenuItem(new MenuItem("Финансовая аналитика", null, analyticsMenu));
        reportsMenu.addMenuItem(new MenuItem("Аналитика по книгам", null, booksAnalyticsMenu));

        rootMenu.addMenuItem(new MenuItem("Отчеты и аналитика", null, reportsMenu));


        Menu importExportMenu = new Menu("Импорт/Экспорт данных");

        // Для книг
        Menu bookImportExportMenu = new Menu("Книги");
        bookImportExportMenu.addMenuItem(new MenuItem("Импорт", actionFactory.importBooksAction(), null));
        bookImportExportMenu.addMenuItem(new MenuItem("Экспорт", actionFactory.exportBooksAction(), null));

        // Для заказов
        Menu orderImportExportMenu = new Menu("Заказы");
        orderImportExportMenu.addMenuItem(new MenuItem("Импорт", actionFactory.importOrderAction(), null));
        orderImportExportMenu.addMenuItem(new MenuItem("Экспорт", actionFactory.exportOrderAction(), null));

        // Для клиентов
        Menu customerImportExportMenu = new Menu("Клиенты");
        customerImportExportMenu.addMenuItem(new MenuItem("Импорт", actionFactory.importClient(), null));
        customerImportExportMenu.addMenuItem(new MenuItem("Экспорт", actionFactory.exportClient(), null));

        // Для запросов
        Menu requestImportExportMenu = new Menu("Запросы на книги");
        requestImportExportMenu.addMenuItem(new MenuItem("Импорт", actionFactory.importRequestAction(), null));
        requestImportExportMenu.addMenuItem(new MenuItem("Экспорт", actionFactory.exportRequestAction(), null));

        importExportMenu.addMenuItem(new MenuItem("Книги", null, bookImportExportMenu));
        importExportMenu.addMenuItem(new MenuItem("Заказы", null, orderImportExportMenu));
        importExportMenu.addMenuItem(new MenuItem("Клиенты", null, customerImportExportMenu));
        importExportMenu.addMenuItem(new MenuItem("Запросы на книги", null, requestImportExportMenu));

        rootMenu.addMenuItem(new MenuItem("Импорт/Экспорт", null, importExportMenu));
    }

    public Menu getRootMenu() {
        if (rootMenu == null) {
            buildMenu();
        }
        return rootMenu;
    }
}
