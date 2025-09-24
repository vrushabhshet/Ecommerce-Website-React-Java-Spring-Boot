package ecommerce.dinedesign.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

/**
 * The class describes the "User" entity.
 * The @Entity annotation says that objects of this class will be processed by hibernate.
 * The @Table (name = "usr") annotation indicates to the "usr" table in which the objects will be stored.
 *
 * @version 1.0
 * @see Perfume
 */
@Entity
@Table(name = "usr")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    /**
     * The unique code of the object.
     * The @Id annotation says that the field is the key for the current object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User name.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Username cannot be empty")
    private String username;

    /**
     * User password for logging into account on the site.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;

    /**
     * User email.
     * The @Email annotation says the string has to be a well-formed email address.
     * The @NotBlank annotation says the field should not be empty.
     */
    @Email(message = "Incorrect email")
    @NotBlank(message = "Email cannot be empty")
    private String email;


    /**
     * Password reset code that is sent to the user's email.
     */
    private String passwordResetCode;

    /**
     * User role. User can have multiple roles.
     * Sampling on first access to the current object.
     * The value of the field (id of the {@link User}) is stored in the "user_id" column.
     */
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    /**
     * List of products in the shopping cart.
     * Between the {@link User} and {@link Perfume} objects, there is a many-to-many relationship, that is,
     * every record in one table is directly related to every record in another table.
     * Sampling on first access to the current object.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> productList;

    /**
     * Method for verifying a user with administrator rights.
     */
    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    // Remove all Lombok imports and annotations
    // Add explicit equals and hashCode methods if needed
    // Add explicit no-args constructor
    // (Getters and setters for User already added previously)

    public User() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return java.util.Objects.equals(id, user.id) &&
                java.util.Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, username);
    }

    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getPasswordResetCode() {
        return passwordResetCode;
    }
    public void setPasswordResetCode(String passwordResetCode) {
        this.passwordResetCode = passwordResetCode;
    }
    public List<Product> getProductList() {
        return productList;
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}