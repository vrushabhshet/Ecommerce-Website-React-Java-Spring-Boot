package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.service.OrderService;
import ecommerce.dinedesign.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User order controller class.
 * This controller and related pages can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @version 2.0
 * @see Order
 * @see User
 * @see OrderService
 * @see UserService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class OrderRestController {
    private static final Logger logger = LoggerFactory.getLogger(OrderRestController.class);

    /**
     * Service object for working with user.
     */
    private final UserService userService;

    /**
     * Service object for working orders.
     */
    private final OrderService orderService;

    /**
     * Constructor for initializing the main variables of the order controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param userService  service object for working with user.
     * @param orderService service object for working orders.
     */
    @Autowired
    public OrderRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Returns the user order.
     * URL request {"/order"}, method GET.
     *
     * @param email request authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/order")
    public ResponseEntity<?> getOrder(@RequestParam String email) {
        logger.info("Fetching order for user: {}", email);
        User user = userService.findByEmail(email);
        List<Product> ProductList = user.getProductList();

        return new ResponseEntity<>(ProductList, HttpStatus.OK);
    }

    /**
     * Saves the user order.
     * URL request {"/order"}, method POST.
     *
     * @param email         requested authenticated user.
     * @param validOrder    requested valid order.
     * @param bindingResult errors in validating http request.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PostMapping("/order")
    public ResponseEntity<?> postOrder(
            @RequestParam String email,
            @Valid @RequestBody Order validOrder,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors while posting order for user: {}", email);
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            User user = userService.findByEmail(email);
            Order order = orderService.postOrder(validOrder, user);
            logger.info("Order created successfully for user: {}. Order ID: {}", email, order.getId());

            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
    }

    /**
     * Returns the finalize order with order index.
     * URL request {"/order/finalize"}, method GET.
     *
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/order/finalize")
    public ResponseEntity<?> finalizeOrder() {
        logger.info("Finalizing order");
        List<Order> orderList = orderService.findAll();
        Order orderIndex = orderList.get(orderList.size() - 1);

        return new ResponseEntity<>(orderIndex.getId(), HttpStatus.OK);
    }

    /**
     * Returns all user orders.
     * URL request {"/order/list"}, method GET.
     *
     * @param email requested authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/order/list")
    public ResponseEntity<?> getUserOrdersList(@RequestParam String email) {
        logger.info("Fetching order list for user: {}", email);
        User user = userService.findByEmail(email);
        List<Order> orders = orderService.findOrderByUser(user);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}