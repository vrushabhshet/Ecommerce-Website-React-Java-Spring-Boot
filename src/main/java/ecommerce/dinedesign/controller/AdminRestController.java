package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.service.OrderService;
import ecommerce.dinedesign.service.ProductService;
import ecommerce.dinedesign.service.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Admin controller class. The @RestController it's a convenience annotation
 * that combines @Controller and @ResponseBody. Annotation serves to inform
 * Spring that this class is a bean and must be loaded when the application
 * starts. The @RequestMapping("/api/v1/rest") annotation informs that this
 * controller will process requests whose URI begins with "/api/v1/rest".
 *
 * @version 2.0
 * @see User
 * @see Product
 * @see Order
 * @see UserService
 * @see ProductService
 * @see OrderService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class AdminRestController {
	private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);

	/**
	 * Upload path for images.
	 */
	@Value("${upload.path}")
	private String uploadPath;

	/**
	 * Service object for working with users.
	 */
	private final UserService userService;

	/**
	 * Service object for working with products.
	 */
	private final ProductService productService;

	/**
	 * Service object for working with orders.
	 */
	private final OrderService orderService;

	/**
	 * Constructor for initializing the main variables of the admin controller.
	 * The @Autowired annotation will allow Spring to automatically initialize
	 * objects.
	 *
	 * @param userService    service object for working with users.
	 * @param productService service object for working with products.
	 * @param orderService   service object for working with orders.
	 */
	@Autowired
	public AdminRestController(UserService userService, ProductService productService, OrderService orderService) {
		this.userService = userService;
		this.productService = productService;
		this.orderService = orderService;
	}

	/**
	 * Save new product to the database by an administrator. URL request
	 * {"/admin/add"}, method POST.
	 *
	 * @param product       saved product.
	 * @param bindingResult errors in validating http request.
	 * @param file          file image.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@PostMapping("/admin/add")
	public ResponseEntity<?> addProduct(@Valid Product product, BindingResult bindingResult,
			@RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
		if (bindingResult.hasErrors()) {
			logger.warn("Validation errors while adding product: {}", product.getProductTitle());
			Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
			return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
		} else {
			saveFile(product, file);
			logger.info("Product file uploaded for: {}", product.getProductTitle());
			Product savedProduct = productService.save(product);
			logger.info("Product added successfully: {} (ID: {})", savedProduct.getProductTitle(),
					savedProduct.getId());
			return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
		}
	}

	/**
	 * Update and save product to the database by an administrator. URL request
	 * {"/admin/edit"}, method PUT.
	 *
	 * @param product       edited product.
	 * @param bindingResult errors in validating http request.
	 * @param file          file image.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	   @PutMapping("/admin/edit")
	   public ResponseEntity<?> updateProduct(@Valid Product product, BindingResult bindingResult,
			   @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
		   if (bindingResult.hasErrors()) {
			   logger.warn("Validation errors while editing product: {}", product.getProductTitle());
			   Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
			   return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
		   } else {
			   Product existingProduct = productService.getOne(product.getId());
			   saveFile(product, file, existingProduct);
			   logger.info("Product file uploaded for update: {}", product.getProductTitle());
			   productService.saveProductInfoById(product.getProductTitle(), product.getManufacturer(), product.getYear(),
					   product.getProductCategory(), product.getDescription(), product.getFilename(), product.getPrice(),
					   product.getId());
			   logger.info("Product updated successfully: {} (ID: {})", product.getProductTitle(), product.getId());
			   return new ResponseEntity<>(HttpStatus.OK);
		   }
	   }

	/**
	 * Returns all orders of all users. URL request {"/admin/orders"}, method GET.
	 *
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@GetMapping("/admin/orders")
	public ResponseEntity<?> getAllOrders() {
		List<Order> orders = orderService.findAll();

		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	/**
	 * Returns user for edit by an administrator. URL request {"/admin/user/{id}"},
	 * method GET.
	 *
	 * @param userId user id.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@GetMapping("/admin/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
		User user = userService.getOne(userId);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Returns all users. URL request {"/admin/user/all"}, method GET.
	 *
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@GetMapping("/admin/user/all")
	public ResponseEntity<?> getAllUsers() {
		List<User> users = userService.findAll();

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Update and save user by an administrator. URL request {"/admin/user/edit"},
	 * method POST.
	 *
	 * @param username user name.
	 * @param form     user roles.
	 * @param user     user id.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@PutMapping("/admin/user/edit")
	public ResponseEntity<?> updateUser(@RequestParam String username, @RequestParam Map<String, String> form,
			@RequestParam("userId") User user) {
		userService.userSave(username, form, user);
		logger.info("User updated by admin: {} (ID: {})", username, user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	   /**
		* Method for saving file in upload directory.
		*
		* @param product current product.
		* @param file    file image.
		* @param existingProduct existing product from DB (for edit case).
		*/
	   private void saveFile(Product product, MultipartFile file, Product existingProduct) throws IOException {
		   if (file == null || file.isEmpty()) {
			   // If editing and no new file, preserve old filename
			   if (existingProduct != null && existingProduct.getFilename() != null) {
				   product.setFilename(existingProduct.getFilename());
				   return;
			   }
			   product.setFilename("empty.jpg");
		   } else {
			   File uploadDir = new File(uploadPath);
			   if (!uploadDir.exists()) {
				   uploadDir.mkdir();
			   }
			   String uuidFile = UUID.randomUUID().toString();
			   String resultFilename = uuidFile + "." + file.getOriginalFilename();
			   file.transferTo(new File(uploadPath + "/" + resultFilename));
			   product.setFilename(resultFilename);
		   }
	   }

	   // Overload for addProduct to keep existing API
	   private void saveFile(Product product, MultipartFile file) throws IOException {
		   saveFile(product, file, null);
	   }
}