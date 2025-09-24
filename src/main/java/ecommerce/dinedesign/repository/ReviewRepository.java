package ecommerce.dinedesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.dinedesign.domain.Review;

/**
 * A repository for {@link Review} objects providing a set of JPA methods for working with the database.
 * Inherits interface {@link JpaRepository}.
 *
 * @version 1.0
 * @see Review
 * @see JpaRepository
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
