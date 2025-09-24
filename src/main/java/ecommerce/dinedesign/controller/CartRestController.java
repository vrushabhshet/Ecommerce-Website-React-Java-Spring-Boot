package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Collections;

/**
 * User REST shopping cart controller class.
 * This controller can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @version 2.0
 * @see User
 * @see UserService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class CartRestController {
    private static final Logger logger = LoggerFactory.getLogger(CartRestController.class);

    /**
     * Service object for working with user shopping cart.
     */
    private final UserService userService;

    /**
     * Constructor for initializing the main variables of the cart controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param userService service object for working with user shopping cart.
     */
    @Autowired
    public CartRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns user shopping cart.
     * URL request {"/cart"}, method GET.
     *
     * @param userSession requested Authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/cart/{email}")
    public ResponseEntity<?> getCart(@PathVariable String email) {
        logger.info("Fetching cart for user: {}", email);
        if (email == null || email.trim().isEmpty()) {
            logger.warn("No email provided, returning empty cart.");
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        User user = userService.findByEmail(email);
        if (user == null) {
            logger.warn("User not found for email: {}, returning empty cart.", email);
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        List<Product> ProductList = user.getProductList();
        return new ResponseEntity<>(ProductList, HttpStatus.OK);
    }

    /**
     * Adds a product to the user shopping cart.
     * URL request {"/cart/add"}, method POST.
     *
     * @param Product     the product to add to the cart.
     * @param userSession request Authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PostMapping("/cart/add")
    public ResponseEntity<?> addToCart(@RequestBody Product Product, @RequestParam String email) {
        logger.info("Adding Product (ID: {}) to cart for user: {}", Product.getId(), email);
        User user = userService.findByEmail(email);
        if (user == null) {
            logger.error("User not found for email: {}", email);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        user.getProductList().add(Product);
        userService.save(user);
        logger.info("Product (ID: {}) added to cart for user: {}", Product.getId(), email);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Remove product from user shopping cart.
     * URL request {"/cart/remove"}, method POST.
     *
     * @param Product     the product to be removed from the user shopping cart.
     * @param userSession request Authenticated user.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Product Product, @RequestParam String email) {
        logger.info("Removing Product (ID: {}) from cart for user: {}", Product.getId(), email);
        User user = userService.findByEmail(email);
        if (user == null) {
            logger.error("User not found for email: {}", email);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        user.getProductList().remove(Product);
        userService.save(user);
        List<Product> ProductList = user.getProductList();
        logger.info("Product (ID: {}) removed from cart for user: {}", Product.getId(), email);
        return new ResponseEntity<>(ProductList, HttpStatus.OK);
    }
}