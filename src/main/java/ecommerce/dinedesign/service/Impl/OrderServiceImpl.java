package ecommerce.dinedesign.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.repository.OrderRepository;
import ecommerce.dinedesign.repository.UserRepository;
import ecommerce.dinedesign.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The service layer class implements the accessor methods of {@link Order} objects
 * in the {@link OrderService} interface database.
 * The class is marked with the @Service annotation - an annotation announcing that this class
 * is a service - a component of the service layer. Service is a subtype of @Component class.
 * Using this annotation will automatically search for service beans.
 *
 * @version 1.0
 * @see Order
 * @see OrderService
 * @see OrderRepository
 */
@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    /**
     * Implementation of the {@link OrderRepository} interface
     * for working with orders with a database.
     */
    private final OrderRepository orderRepository;

    /**
     * Implementation of the {@link UserRepository} interface
     * for working with user with a database.
     */
    private final UserRepository userRepository;

    /**
     * Constructor for initializing the main variables of the order service.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param orderRepository implementation of the {@link OrderRepository} interface
     *                        for working with orders with a database.
     * @param userRepository  implementation of the {@link UserRepository} interface
     *                        for working with user with a database.
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    /**
     * Return list of all user orders.
     *
     * @return list of user {@link Order}.
     */
    @Override
    public List<Order> findAll() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    /**
     * Save order info.
     *
     * @param order order object to return.
     * @return The {@link Order} class object which will be saved in the database.
     */
    @Override
    public Order save(Order order) {
        logger.info("Saving order with ID: {}", order.getId());
        return orderRepository.save(order);
    }

    /**
     * Returns list of orders authenticated user.
     *
     * @param user name of authenticated user.
     * @return An object of type {@link List} is a list of orders of authenticated user.
     */
    @Override
    public List<Order> findOrderByUser(User user) {
        logger.info("Fetching orders for user: {}", user.getEmail());
        return orderRepository.findOrderByUser(user);
    }

    /**
     * Saves the user order.
     *
     * @param validOrder  requested valid order.
     * @param userSession requested authenticated user.
     * @return The {@link Order} class object which will be saved in the database.
     */
    @Override
    public Order postOrder(Order validOrder, User userSession) {
        logger.info("Posting new order for user: {}", userSession.getEmail());
        User user = userRepository.findByEmail(userSession.getEmail());
        Order order = new Order(user);

        order.getProductList().addAll(user.getProductList());
        order.setTotalPrice(validOrder.getTotalPrice());
        order.setFirstName(validOrder.getFirstName());
        order.setLastName(validOrder.getLastName());
        order.setCity(validOrder.getCity());
        order.setAddress(validOrder.getAddress());
        order.setPostIndex(validOrder.getPostIndex());
        order.setEmail(validOrder.getEmail());
        order.setPhoneNumber(validOrder.getPhoneNumber());
        user.getProductList().clear();

        orderRepository.save(order);
        logger.info("Order created with ID: {} for user: {}", order.getId(), user.getEmail());

        return order;
    }
}