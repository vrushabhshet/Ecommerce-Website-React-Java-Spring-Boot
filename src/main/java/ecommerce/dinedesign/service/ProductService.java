package ecommerce.dinedesign.service;

import java.util.List;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.service.Impl.ProductServiceImpl;

/**
 * The service layer interface describes a set of methods for working with
 * objects of the {@link Product} class.
 *
 * @version 1.0
 * @see Product
 * @see ProductServiceImpl
 */
public interface ProductService {
	/**
	 * Retrieves a Product by its id.
	 *
	 * @param id must not be null.
	 * @return the Product with the given id.
	 */
	Product getOne(Long id);

	/**
	 * Return list of all products.
	 *
	 * @return list of {@link Product}.
	 */
	List<Product> findAll();

	/**
	 * Returns list of products which has the same product manufacturers, product
	 * Categorys and the price is in the range between of starting price and ending
	 * price with the value of the input parameter.
	 *
	 * @param manufacturers product manufacturers to return.
	 * @param Categorys     product Categorys to return.
	 * @param prices        product price range
	 */
	List<Product> filter(List<String> manufacturers, List<String> Categorys, List<Integer> prices);

	/**
	 * Returns list of products which has the same product manufacturer with the
	 * value of the input parameter.
	 *
	 * @param manufacturer product manufacturer to return.
	 * @return list of {@link Product}.
	 */
	List<Product> findByManufacturerOrderByPriceDesc(String manufacturer);

	/**
	 * Returns list of products which has the same Category with the value of the
	 * input parameter.
	 *
	 * @param productCategory product Category to return.
	 * @return list of {@link Product}.
	 */
	List<Product> findByProductCategoryOrderByPriceDesc(String productCategory);

	/**
	 * Save updated product.
	 *
	 * @param productTitle         product title to update.
	 * @param manufacturer         product manufacturer to update.
	 * @param year                 the year the product was released to update.
	 * @param country              manufacturer country to update.
	 * @param productCategory      Category to update to update.
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
	void saveProductInfoById(String productTitle, String manufacturer, Integer year,
			String productCategory, String description, String filename, Integer price, Long id);

	/**
	 * Save product info.
	 *
	 * @param product product object to return.
	 * @return The {@link Product} class object which will be saved in the database.
	 */
	Product save(Product product);
}