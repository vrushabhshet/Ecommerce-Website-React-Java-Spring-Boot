package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.dto.ProductSearchFilterDto;
import ecommerce.dinedesign.service.ProductService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Menu controller class. This controller and related pages can be accessed by
 * all users, regardless of their roles. The @RestController it's a convenience
 * annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the
 * application starts. The @RequestMapping("/api/v1/rest") annotation informs
 * that this controller will process requests whose URI begins with
 * "/api/v1/rest".
 *
 * @version 2.0
 * @see Product
 * @see ProductService
 */
@RestController
@RequestMapping("/api/v1/rest")
public class MenuRestController {
	private static final Logger logger = LoggerFactory.getLogger(MenuRestController.class);

	/**
	 * Service object for working with products.
	 */
	private final ProductService productService;

	/**
	 * Constructor for initializing the main variables of the menu controller.
	 * The @Autowired annotation will allow Spring to automatically initialize
	 * objects.
	 *
	 * @param productService Service object for working with products.
	 */
	@Autowired
	public MenuRestController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Returns list of Products to the menu page by selected parameters. URL request
	 * {"/menu/search"}, method POST.
	 *
	 * @param filterDto data transfer object for filter search.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@PostMapping("/menu/search")
	public ResponseEntity<?> findProductsByFilterParams(@RequestBody ProductSearchFilterDto filterDto) {
		logger.info("Searching products with filters: manufacturers={}, categories={}, prices={}",
				filterDto.getManufacturers(), filterDto.getCategories(), filterDto.getPrices());
		List<Product> filter = productService.filter(filterDto.getManufacturers(), filterDto.getCategories(),
				filterDto.getPrices());

		return new ResponseEntity<>(filter, HttpStatus.OK);
	}

	/**
	 * Returns list of Products to the menu which has the same Product category with
	 * the value of the input parameter. URL request {"/menu/category"}, method
	 * POST.
	 *
	 * @param filterDto data transfer object for filter search.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@PostMapping("/menu/category")
	public ResponseEntity<?> findByProductCategory(@RequestBody ProductSearchFilterDto filterDto) {
		logger.info("Searching Products by category: {}", filterDto.getProductCategory());
		List<Product> category = productService.findByProductCategoryOrderByPriceDesc(filterDto.getProductCategory());

		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	/**
	 * Returns list of Products to the menu page which has the same Product
	 * manufacturer with the value of the input parameter. URL request
	 * {"/menu/Manufacturer"}, method POST.
	 *
	 * @param filterDto data transfer object for filter search.
	 * @return ResponseEntity with HTTP response: status code, headers, and body.
	 */
	@PostMapping("/menu/Manufacturer")
	public ResponseEntity<?> findBygetManufacturer(@RequestBody ProductSearchFilterDto filterDto) {
		logger.info("Searching Products by Manufacturer: {}", filterDto.getManufacturer());
		List<Product> Manufacturer = productService.findByManufacturerOrderByPriceDesc(filterDto.getManufacturer());

		return new ResponseEntity<>(Manufacturer, HttpStatus.OK);
	}
}