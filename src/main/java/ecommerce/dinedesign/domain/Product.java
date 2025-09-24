package ecommerce.dinedesign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * The class describes the "Product" entity. The @Entity annotation says that
 * objects of this class will be processed by hibernate.
 *
 * @version 1.0
 */
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product {
	/**
	 * The unique code of the object. The @Id annotation says that the field is the
	 * key for the current object.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Product title. The @NotBlank annotation says the field should not be empty.
	 * Max length of product title field is 255 characters.
	 */
	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String productTitle;

	/**
	 * Product manufacturer. The @NotBlank annotation says the field should not be
	 * empty. Max length of product manufacturer field is 255 characters.
	 */
	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String manufacturer;

	/**
	 * The year the product was released. The @NotBlank annotation says the field
	 * should not be empty.
	 */
	@NotNull(message = "Fill in the input field")
	private Integer year;

	/**
	 * productCategory. The @NotBlank annotation says the field should not be empty.
	 * Max length of manufacturer country field is 255 characters.
	 */
	@NotBlank(message = "Fill in the input field")
	@Length(max = 255)
	private String productCategory;

	/**
	 * Product description.
	 */
	private String description;

	/**
	 * Product image.
	 */
	private String filename;

	/**
	 * Product price. The @NotBlank annotation says the field should not be empty.
	 */
	@NotNull(message = "Fill in the input field")
	private Integer price;

	/**
	 * List of reviews for current product. Between the {@link Product} and
	 * {@link Review} objects, there is a one-to-many relationship, that is, each
	 * record in one table is directly related to a single record in another table.
	 */
	@OneToMany
	private List<Review> reviews;

	public Product() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}


	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Product product = (Product) o;
		return java.util.Objects.equals(id, product.id) && java.util.Objects.equals(manufacturer, product.manufacturer)
				&& java.util.Objects.equals(productTitle, product.productTitle)
				&& java.util.Objects.equals(productCategory, product.productCategory)
				&& java.util.Objects.equals(price, product.price);
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash(id, manufacturer, productTitle, productCategory, price);
	}
}