package ecommerce.dinedesign.service.Impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.domain.Review;
import ecommerce.dinedesign.domain.Role;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.dto.PasswordResetDto;
import ecommerce.dinedesign.repository.ProductRepository;
import ecommerce.dinedesign.repository.ReviewRepository;
import ecommerce.dinedesign.repository.UserRepository;
import ecommerce.dinedesign.service.UserService;

import java.time.LocalDate;
import java.util.*;

/**
 * The service layer class implements the accessor methods of {@link User} objects
 * in the {@link UserService} interface database.
 * The class is marked with the @Service annotation - an annotation announcing that this class
 * is a service - a component of the service layer. Service is a subtype of @Component class.
 * Using this annotation will automatically search for service beans.
 *
 * @version 1.0
 * @see User
 * @see UserService
 * @see UserRepository
 * @see JavaMailSender
 */
@Service("userDetailsServiceImpl")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository,
                           ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public boolean resetPasswordAndSendEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        String newPassword = generateRandomPassword(10);
        user.setPassword(newPassword);
        userRepository.save(user);

        if (mailSender != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Dine And Design - Your New Password");
            message.setText("Your new password is: " + newPassword +
                    "\nPlease log in and change it immediately.");
            mailSender.send(message);
        }
        return true;
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPasswordResetCode(String code) {
        return userRepository.findByPasswordResetCode(code);
    }

    @Override
    public User save(User user) {
        logger.info("Saving user: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public boolean addUser(User user) {
        logger.info("Attempting to add user: {}", user.getEmail());
        User userFromDb = userRepository.findByEmail(user.getEmail());
        if (userFromDb != null) {
            logger.warn("User already exists: {}", user.getEmail());
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        logger.info("User added: {}", user.getEmail());
        return true;
    }

    @Override
    public void passwordReset(PasswordResetDto passwordReset) {
        User user = userRepository.findByEmail(passwordReset.getEmail());
        user.setPassword(passwordReset.getPassword());
        user.setPasswordResetCode(null);

        userRepository.save(user);
    }

    @Override
    public void userSave(String username, Map<String, String> form, User user) {
        user.setUsername(username);
        String roleKey = form.get("role");
        if (roleKey != null) {
            Role newRole = Role.valueOf(roleKey);
            user.getRoles().clear();
            user.getRoles().add(newRole);
        }
        userRepository.save(user);
    }

    @Override
    public void updateProfile(User user, String password, String email) {
        logger.info("Updating profile for user: {}", user.getEmail());
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }

        userRepository.save(user);
        logger.info("Profile updated for user: {}", user.getEmail());
    }

    @Override
    public void addReviewToProduct(Review review, Long productId) {
        logger.info("Adding review to productId: {}", productId);
        Product product = productRepository.getOne(productId);
        Review productReview = new Review();
        productReview.setMessage(review.getMessage());
        productReview.setAuthor(review.getAuthor()); 
        productReview.setDate(LocalDate.now());
        product.getReviews().add(productReview);
        reviewRepository.save(productReview);
        productRepository.save(product);
        logger.info("Review added to productId: {}", productId);
    }
}
