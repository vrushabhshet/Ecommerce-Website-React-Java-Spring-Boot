package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.Review;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.dto.AuthenticationRequestDTO;
import ecommerce.dinedesign.service.OrderService;
import ecommerce.dinedesign.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User controller class.
 * This controller and related pages can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @version 2.0
 * @see User
 * @see Order
 * @see UserService
 * @see OrderService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class UserRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    /**
     * Service object for working with users.
     */
    private final UserService userService;

    /**
     * Service object for working with orders.
     */
    private final OrderService orderService;

    /**
     * Constructor for initializing the main variables of the user controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param userService  service object for working with users.
     * @param orderService service object for working with orders.
     */
    @Autowired
    public UserRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Returns profile information for current user.
     * URL request {"/user/edit"}, method GET.
     *
     * @param email request authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/user/edit")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        logger.info("Fetching user info for email: {}", email);
        User user = userService.findByEmail(email);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Save edited password by user.
     * URL request {"/user/edit"}, method PUT.
     *
     * @param email   request authenticated user.
     * @param request data transfer object for authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PutMapping("/user/edit")
    public ResponseEntity<?> updateUserInfo(
            @RequestParam String email,
            @RequestBody AuthenticationRequestDTO request
    ) {
        logger.info("Updating user info for email: {}", email);
        User user = userService.findByEmail(email);
        userService.updateProfile(user, request.getPassword(), request.getEmail());
        logger.info("User info updated for email: {}", email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Returns all user orders.
     * URL request {"/user/orders"}, method GET.
     *
     * @param email requested authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/user/orders")
    public ResponseEntity<?> getAllUserOrders(@RequestParam String email) {
        logger.info("Fetching all orders for user: {}", email);
        User user = userService.findByEmail(email);
        List<Order> orders = orderService.findOrderByUser(user);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Save Product review with message.
     * URL request {"/user/review"}, method POST.
     *
     * @param ProductId     Product id.
     * @param review        review for current Product with author and message.
     * @param bindingResult errors in validating http request.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PostMapping("/user/review")
    public ResponseEntity<?> addReviewToProduct(
            @RequestParam(required = false, name = "ProductId") Long ProductId,
            @Valid @RequestBody Review review,
            BindingResult bindingResult
    ) {
        logger.info("Received review: author='{}', message='{}', ProductId={}", review.getAuthor(), review.getMessage(), ProductId);
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors while adding review for ProductId: {}", ProductId);
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            userService.addReviewToProduct(review, ProductId);
            logger.info("Review added for ProductId: {} by author: {}", ProductId, review.getAuthor());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}