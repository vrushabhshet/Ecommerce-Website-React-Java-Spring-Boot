package ecommerce.dinedesign.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * The class describes the "Review" entity.
 * The @Entity annotation says that objects of this class will be processed by hibernate.
 * The @Table (name = "review") annotation indicates to the "review" table in which the objects will be stored.
 *
 * @version 1.0
 */
@Entity
@Table(name = "review")
public class Review {
    /**
     * The unique code of the object.
     * The @Id annotation says that the field is the key for the current object.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    /**
     * Author message for current product review
     */
    @NotBlank(message = "Fill in the input field")
    private String message;

    /**
     * Author of current product review
     */
    @NotBlank(message = "Fill in the input field")
    private String author;

    /**
     * Date of product review
     */
    private LocalDate date;

    public Review() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}