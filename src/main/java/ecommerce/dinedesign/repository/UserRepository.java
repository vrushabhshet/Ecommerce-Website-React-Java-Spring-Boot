package ecommerce.dinedesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.dinedesign.domain.User;

/**
 * A repository for {@link User} objects providing a set of JPA methods for working with the database.
 * Inherits interface {@link JpaRepository}.
 *
 * @version 1.0
 * @see User
 * @see JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Returns the user from the database that has the same name as the value of the input parameter.
     *
     * @param username user name to return.
     * @return The {@link User} class object.
     */
    User findByUsername(String username);

    /**
     * Returns the user from the database that has the same email as the value of the input parameter.
     *
     * @param email user email to return.
     * @return The {@link User} class object.
     */
    User findByEmail(String email);

    /**
     * Returns the user from the database that has the same password reset code as the value of the input parameter.
     *
     * @param code password reset code.
     * @return The {@link User} class object.
     */
    User findByPasswordResetCode(String code);
}