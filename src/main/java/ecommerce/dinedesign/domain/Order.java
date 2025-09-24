package ecommerce.dinedesign.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The class describes the "Orders" entity.
 * The @Entity annotation says that objects of this class will be processed by hibernate.
 * The @Table (name = "orders") annotation indicates to the "orders" table in which the objects will be stored.
 *
 * @version 1.0
 * @see User
 * @see Product
 */
@Entity
@Table(name = "orders")
public class Order {
    /**
     * The unique code of the object.
     * The @Id annotation says that the field is the key for the current object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Order total price.
     */
    private Double totalPrice;

    /**
     * Date when the order was made.
     */
    private LocalDate date;

    /**
     * The first name of the customer who placed the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String firstName;

    /**
     * The last name of the customer who placed the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String lastName;

    /**
     * City of delivery of the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String city;

    /**
     * Delivery address of the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String address;

    /**
     * Customer email.
     * The @Email annotation says the string has to be a well-formed email address.
     * The @NotBlank annotation says the field should not be empty.
     */
    @Email(message = "Incorrect email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    /**
     * Customer phone number.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    /**
     * Customer post index.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotNull(message = "Post index cannot be empty")
    @Min(value = 5, message = "Post index must contain 5 digits")
    private Integer postIndex;

    /**
     * List of products in the order.
     * Between the {@link Order} and {@link Product} objects, there is a many-to-many relationship, that is,
     * every record in one table is directly related to every record in another table.
     * Sampling on first access to the current object.
     */
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> productList;

    /**
     * The customer who made the order.
     * Between the {@link Order} and {@link User} objects, there is a many-to-one relationship, that is,
     * each record in one table is directly related to a single record in another table.
     */
    @ManyToOne
    private User user;

    public Order() {}

    public Order(User user) {
        this.date = LocalDate.now();
        this.user = user;
        this.productList = new ArrayList<>();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Integer getPostIndex() { return postIndex; }
    public void setPostIndex(Integer postIndex) { this.postIndex = postIndex; }
    public List<Product> getProductList() { return productList; }
    public void setProductList(List<Product> productList) { this.productList = productList; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return java.util.Objects.equals(id, order.id) &&
                java.util.Objects.equals(user, order.user) &&
                java.util.Objects.equals(productList, order.productList);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, user, productList);
    }
}