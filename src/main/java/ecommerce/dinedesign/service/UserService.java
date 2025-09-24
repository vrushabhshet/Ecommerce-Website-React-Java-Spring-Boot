package ecommerce.dinedesign.service;

import java.util.List;
import java.util.Map;

import ecommerce.dinedesign.domain.Review;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.dto.PasswordResetDto;
import ecommerce.dinedesign.service.Impl.UserServiceImpl;

/**
 * The service layer interface describes a set of methods for working with objects of the {@link User} class.
 *
 * @version 1.0
 * @see User
 * @see UserServiceImpl
 */
public interface UserService {
    /**
     * Resets the user's password to a new random value and sends it to their email.
     * @param email user's email
     * @return true if user exists and password was reset, false otherwise
     */
    boolean resetPasswordAndSendEmail(String email);
    /**
     * Retrieves an User by its id.
     *
     * @param id must not be null.
     * @return User with the given id.
     */
    User getOne(Long id);

    /**
     * Returns the user with the same email as the value of the input parameter.
     *
     * @param email user email to return.
     * @return The {@link User} class object.
     */
    User findByEmail(String email);

    /**
     * Return list of all registered users.
     *
     * @return list of {@link User}.
     */
    List<User> findAll();

    /**
     * Returns the user with the same name as the value of the input parameter.
     *
     * @param username user name to return.
     * @return The {@link User} class object.
     */
    User findByUsername(String username);

    /**
     * Returns the user that has the same password reset code as the value of the input parameter.
     *
     * @param code password reset code.
     * @return The {@link User} class object.
     */
    User findByPasswordResetCode(String code);

    /**
     * Save user info.
     *
     * @param user user object to return.
     * @return The {@link User} class object which will be saved in the database.
     */
    User save(User user);

    /**
     * Save user in database and send activation code to user email.
     *
     * @param user user who has registered.
     * @return true if the user is not exists.
     */
    boolean addUser(User user);


    /**
     * Reset user password.
     *
     * @param passwordReset data transfer object with user email and password.
     */
    void passwordReset(PasswordResetDto passwordReset);

    /**
     * Save updated user with set of roles.
     *
     * @param username user name of registered user.
     * @param form     form of user roles.
     * @param user     user from the database.
     */
    void userSave(String username, Map<String, String> form, User user);

    /**
     * Save updated user profile with new password or email.
     *
     * @param user      user from the database.
     * @param password  the user's password to be changed.
     * @param email     the user's email to be changed.
     */
    void updateProfile(User user, String password, String email);

    /**
     * Save Product review.
     *
     * @param review    review for current Product with message
     * @param ProductId Product id in database
     */
    void addReviewToProduct(Review review, Long ProductId);
}