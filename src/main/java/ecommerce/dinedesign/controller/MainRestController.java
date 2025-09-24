package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.service.ProductService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home page controller class.
 * This controller and related pages can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @version 2.0
 * @see Product
 * @see ProductService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class MainRestController {
    private static final Logger logger = LoggerFactory.getLogger(MainRestController.class);
    /**
     * Service object for working with products.
     */
    private final ProductService productService;

    /**
     * Constructor for initializing the main variables of the main controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param productService Service object for working with products.
     */
    @Autowired
    public MainRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Returns all products to the home page.
     * URL request {"/"}, method GET.
     *
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        logger.info("Fetching all products for home page");
        List<Product> products = productService.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Returns product to the home page.
     * URL request {"/product/{id}"}, method GET.
     *
     * @param productId product id.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) {
        logger.info("Fetching product with ID: {}", productId);
        Product product = productService.getOne(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}