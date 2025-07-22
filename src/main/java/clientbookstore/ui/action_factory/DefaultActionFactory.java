package clientbookstore.ui.action_factory;

import clientbookstore.controller.MainContr;
import clientbookstore.ui.actions.IAction;
import clientbookstore.ui.actions.book.*;
import clientbookstore.ui.actions.completed_orders.*;
import clientbookstore.ui.actions.csv.book.ExportBookAction;
import clientbookstore.ui.actions.csv.book.ImportBooksAction;
import clientbookstore.ui.actions.csv.customer.ExportCustomerAction;
import clientbookstore.ui.actions.csv.customer.ImportCustomerAction;
import clientbookstore.ui.actions.csv.order.ExportOrderAction;
import clientbookstore.ui.actions.csv.order.ImportOrderAction;
import clientbookstore.ui.actions.csv.request.ExportRequestAction;
import clientbookstore.ui.actions.csv.request.ImportRequestAction;
import clientbookstore.ui.actions.customer.AddCustomerAction;
import clientbookstore.ui.actions.customer.ShowAllCustomer;
import clientbookstore.ui.actions.request.*;
import clientbookstore.ui.actions.order.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class DefaultActionFactory implements ActionFactory {

//    @Autowired
//    private MainContr dataManager;

    private final ApplicationContext context;

    @Autowired
    public DefaultActionFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public IAction createAddBookAction() {
        return context.getBean(AddBookAction.class);
    }

    @Override
    public IAction writeOffBookAction() {
        return context.getBean(WriteOffBookAction.class);
    }

    @Override
    public IAction allBooksListAction() {
        return context.getBean(AllBooksListAction.class);
    }

    @Override
    public IAction sortBooksByTitleAction() {
        return context.getBean(SortBooksByTitleAction.class);
    }

    @Override
    public IAction sortBooksByPriceAction() {
        return context.getBean(SortBooksByPriceAction.class);
    }

    @Override
    public IAction sortBooksByYearDescAction() {
        return context.getBean(SortBooksByYearDescAction.class);
    }

    @Override
    public IAction sortBooksByAvailiable() {
        return context.getBean(SortBooksByAvailiable.class);
    }

    @Override
    public IAction createOrderAction() {
        return context.getBean(CreateOrderAction.class);
    }

    @Override
    public IAction cancelOrderAction() {
        return context.getBean(CancelOrderAction.class);
    }

    @Override
    public IAction changeOrderStatusAction() {
        return context.getBean(ChangeOrderStatusAction.class);
    }

    @Override
    public IAction showAllOrdersAction() {
        return context.getBean(ShowAllOrdersAction.class);
    }

    @Override
    public IAction sortOrdersByDateAction() {
        return context.getBean(SortOrdersByDateAction.class);
    }

    @Override
    public IAction sortOrdersByPriceAction() {
        return context.getBean(SortOrdersByPriceAction.class);
    }

    @Override
    public IAction sortOrdersByStatusAction() {
        return context.getBean(SortOrdersByStatusAction.class);
    }

    @Override
    public IAction createBookRequestAction() {
        return context.getBean(CreateBookRequestAction.class);
    }

    @Override
    public IAction showAllBookRequestsAction() {
        return context.getBean(ShowAllBookRequestsAction.class);
    }

    @Override
    public IAction sortRequestsByCountAction() {
        return context.getBean(SortRequestsByCountAction.class);
    }

    @Override
    public IAction sortRequestsByTitleAction() {
        return context.getBean(SortRequestsByTitleAction.class);
    }

    @Override
    public IAction sortCompletedOrdersByDateAction() {
        return context.getBean(SortCompletedOrdersByDateAction.class);
    }

    @Override
    public IAction sortCompletedOrdersByPriceAction() {
        return context.getBean(SortCompletedOrdersByPriceAction.class);
    }

    @Override
    public IAction showCompletedOrdersCountAction() {
        return context.getBean(ShowCompletedOrdersCountAction.class);
    }

    @Override
    public IAction showTotalRevenueAction() {
        return context.getBean(ShowTotalRevenueAction.class);
    }

    @Override
    public IAction importBooksAction() {
        return context.getBean(ImportBooksAction.class);
    }

    @Override
    public IAction exportBooksAction() {
        return context.getBean(ExportBookAction.class);
    }

    @Override
    public IAction exportOrderAction() {
        return context.getBean(ExportOrderAction.class);
    }

    @Override
    public IAction importOrderAction() {
        return context.getBean(ImportOrderAction.class);
    }

    @Override
    public IAction showAllCustomer() {
        return context.getBean(ShowAllCustomer.class);
    }

    @Override
    public IAction importClient() {
        return context.getBean(ImportCustomerAction.class);
    }

    @Override
    public IAction exportClient() {
        return context.getBean(ExportCustomerAction.class);
    }

    @Override
    public IAction importRequestAction() {
        return context.getBean(ImportRequestAction.class);
    }

    @Override
    public IAction exportRequestAction() {
        return context.getBean(ExportRequestAction.class);
    }

    @Override
    public IAction showStaleBooksAction() {
        return context.getBean(ShowStaleBooksAction.class);
    }

    @Override
    public IAction addCustomerAction() {
        return context.getBean(ShowStaleBooksAction.class);
    }
}
