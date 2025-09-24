package ecommerce.dinedesign.service;

import java.util.List;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.service.Impl.OrderServiceImpl;

/**
 * The service layer interface describes a set of methods for working with objects of the {@link Order} class.
 *
 * @version 1.0
 * @see Order
 * @see OrderServiceImpl
 */
public interface OrderService {
    /**
     * Return list of all user orders.
     *
     * @return list of user {@link Order}.
     */
    List<Order> findAll();

    /**
     * Save order info.
     *
     * @param order order object to return.
     * @return The {@link Order} class object which will be saved in the database.
     */
    Order save(Order order);

    /**
     * Returns list of orders authenticated user.
     *
     * @param user name of authenticated user.
     * @return An object of type {@link List} is a list of orders of authenticated user.
     */
    List<Order> findOrderByUser(User user);

    /**
     * Saves the user order and send message with order params to email address.
     *
     * @param validOrder  requested valid order.
     * @param userSession requested authenticated user.
     * @return The {@link Order} class object which will be saved in the database.
     */
    Order postOrder(Order validOrder, User userSession);
}
