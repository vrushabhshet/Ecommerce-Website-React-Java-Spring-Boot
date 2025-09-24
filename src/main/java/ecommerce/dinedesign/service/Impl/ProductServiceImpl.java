package ecommerce.dinedesign.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.repository.ProductRepository;
import ecommerce.dinedesign.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The service layer class implements the accessor methods of {@link Product}
 * objects in the {@link ProductService} interface database. The class is marked
 * with the @Service annotation - an annotation announcing that this class is a
 * service - a component of the service layer. Service is a subtype
 * of @Component class. Using this annotation will automatically search for
 * service beans.
 *
 * @version 2.0
 * @see Product
 * @see ProductService
 * @see ProductRepository
 */
@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	/**
	 * Implementation of the {@link ProductRepository} interface for working with
	 * products with a database.
	 */
	private final ProductRepository productRepository;

	/**
	 * Constructor for initializing the main variables of the product service.
	 * The @Autowired annotation will allow Spring to automatically initialize
	 * objects.
	 *
	 * @param productRepository implementation of the {@link ProductRepository}
	 *                          interface for working with products with a database.
	 */
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Retrieves a Product by its id.
	 *
	 * @param id must not be null.
	 * @return the Product with the given id.
	 */
	@Override
	public Product getOne(Long id) {
		logger.info("Fetching product with ID: {}", id);
		return productRepository.getOne(id);
	}

	/**
	 * Return list of all products.
	 *
	 * @return list of {@link Product}.
	 */
	@Override
	public List<Product> findAll() {
		logger.info("Fetching all products");
		return productRepository.findAll();
	}

	/**
	 * Returns list of products which has the same product manufacturers, product
	 * Categorys and the price is in the range between of starting price and ending
	 * price with the value of the input parameter.
	 *
	 * @param Manufacturers product manufacturers to return.
	 * @param Categorys     product Categorys to return.
	 * @param prices        product price range
	 * @return list of {@link Product}.
	 */
	// TODO rewrite filter logic
	@Override
	public List<Product> filter(List<String> Manufacturers, List<String> Categorys, List<Integer> prices) {
		logger.info("Filtering products with Manufacturers: {}, Categorys: {}, prices: {}", Manufacturers, Categorys,
				prices);
		List<Product> productList;

		if (!prices.isEmpty()) {
			productList = productRepository.findByPriceBetweenOrderByPriceDesc(prices.get(0), prices.get(1));
		} else if (!Manufacturers.isEmpty() && !Categorys.isEmpty()) {
			productList = productRepository.findByManufacturerInAndProductCategoryInOrderByPriceDesc(Manufacturers,
					Categorys);
		} else if (!Manufacturers.isEmpty() || !Categorys.isEmpty()) {
			productList = productRepository.findByManufacturerInOrProductCategoryInOrderByPriceDesc(Manufacturers,
					Categorys);
		} else {
			productList = productRepository.findAll();
		}

		return productList;
	}

	/**
	 * Returns list of products which has the same product manufacturer with the
	 * value of the input parameter.
	 *
	 * @param Manufacturer product manufacturer to return.
	 * @return list of {@link Product}.
	 */
	@Override
	public List<Product> findByManufacturerOrderByPriceDesc(String Manufacturer) {
		logger.info("Fetching products by Manufacturer: {}", Manufacturer);
		return productRepository.findByManufacturerOrderByPriceDesc(Manufacturer);
	}

	/**
	 * Returns list of products which has the same Category with the value of the
	 * input parameter.
	 *
	 * @param ProductCategory product Category to return.
	 * @return list of {@link Product}.
	 */
	@Override
	public List<Product> findByProductCategoryOrderByPriceDesc(String ProductCategory) {
		logger.info("Fetching products by Category: {}", ProductCategory);
		return productRepository.findByProductCategoryOrderByPriceDesc(ProductCategory);
	}

	/**
	 * Save updated product.
	 *
	 * @param ProductTitle         product title to update.
	 * @param Manufacturer         product manufacturer to update.
	 * @param year                 the year the product was released to update.
	 * @param country              manufacturer country to update.
	 * @param ProductCategory      Category to update to update.
	 * @param fragranceTopNotes    fragrance top notes to update.
	 * @param fragranceMiddleNotes fragrance middle notes to update.
	 * @param fragranceBaseNotes   fragrance base notes to update.
	 * @param description          product description to update.
	 * @param filename             product image to update.
	 * @param price                product price to update.
	 * @param volume               product volume to update.
	 * @param type                 type of fragrance to update.
	 * @param id                   the unique code of the product to update.
	 */
	@Override
	public void saveProductInfoById(String ProductTitle, String Manufacturer, Integer year,
			String ProductCategory, String description, String filename, Integer price, Long id) {
		logger.info("Updating product info for ID: {}", id);
		productRepository.saveProductInfoById(ProductTitle, Manufacturer, year, ProductCategory, description, filename,
				price, id);
	}

	/**
	 * Save product info.
	 *
	 * @param product product object to return.
	 * @return The {@link Product} class object which will be saved in the database.
	 */
	@Override
	public Product save(Product product) {
		logger.info("Saving product: {}", product.getProductTitle());
		return productRepository.save(product);
	}
}