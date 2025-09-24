package ecommerce.dinedesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ecommerce.dinedesign.domain.Product;

import java.util.List;

/**
 * A repository for {@link Product} objects providing a set of JPA methods for
 * working with the database. Inherits interface {@link JpaRepository}.
 *
 * @version 2.0
 * @see Product
 * @see JpaRepository
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * 
	 * @param manufacturers
	 * @param categories
	 * @return
	 */
	List<Product> findByManufacturerInAndProductCategoryInOrderByPriceDesc(List<String> manufacturers,
			List<String> categories);

	/**
	 * Returns list of products from the database which has the same manufacturers
	 * or categories with the value of the input parameter.
	 *
	 * @param manufacturers product manufacturers to return.
	 * @param categories    categories to return.
	 * @return list of {@link Product}.
	 */
	List<Product> findByManufacturerInOrProductCategoryInOrderByPriceDesc(List<String> manufacturers,
			List<String> categories);

	/**
	 * Returns list of products from the database in which the price is in the range
	 * between of starting price and ending price.
	 *
	 * @param startingPrice the starting price of the product that the user enters.
	 * @param endingPrice   the ending price of the product that the user enters.
	 * @return list of {@link Product}.
	 */
	List<Product> findByPriceBetweenOrderByPriceDesc(Integer startingPrice, Integer endingPrice);

	/**
	 * Returns list of products from the database which has the same manufacturer
	 * with the value of the input parameter.
	 *
	 * @param manufacturer product manufacturer to return.
	 * @return list of {@link Product}.
	 */
	List<Product> findByManufacturerOrderByPriceDesc(String manufacturer);

	/**
	 * Returns list of products from the database which has the same category with
	 * the value of the input parameter.
	 *
	 * @param productCategory product Category to return.
	 * @return list of {@link Product}.
	 */
	List<Product> findByProductCategoryOrderByPriceDesc(String productCategory);

	/**
	 * Save updated product to the database. The @Modifying annotation declaring
	 * manipulating queries. The @Transactional annotation - before the execution of
	 * the method marked with this annotation, a transaction starts, after the
	 * method is executed, the transaction is committed, and when a RuntimeException
	 * is thrown, it is rolled back. The @Query annotation to declare finder queries
	 * directly on repository methods.
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
	@Modifying
	@Transactional
	@Query("update Product p set p.productTitle = ?1, p.manufacturer = ?2, p.year = ?3, "
			+ "p.productCategory = ?4, p.description = ?5, p.filename = ?6, p.price = ?7 " + "where p.id = ?8")
	void saveProductInfoById(String productTitle, String manufacturer, Integer year, String productCategory,
			String description, String filename, Integer price, Long id);

}