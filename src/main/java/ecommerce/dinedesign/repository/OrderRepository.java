package ecommerce.dinedesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.dinedesign.domain.Order;
import ecommerce.dinedesign.domain.User;

import java.util.List;

/**
 * A repository for {@link Order} objects providing a set of JPA methods for working with the database.
 * Inherits interface {@link JpaRepository}.
 *
 * @version 1.0
 * @see Order
 * @see JpaRepository
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Returns list of orders authenticated user.
     *
     * @param user name of authenticated user.
     * @return An object of type {@link List} is a list of orders of authenticated user.
     */
    List<Order> findOrderByUser(User user);
}