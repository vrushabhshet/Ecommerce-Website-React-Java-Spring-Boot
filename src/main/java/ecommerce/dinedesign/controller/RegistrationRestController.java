package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registration controller class.
 * This controller and related pages can be accessed by all users, regardless of their roles.
 * The @RestController it's a convenience annotation that combines @Controller and @ResponseBody. Annotation serves to
 * inform Spring that this class is a bean and must be loaded when the application starts.
 * The @RequestMapping("/api/v1/rest") annotation informs that this controller will process requests whose URI begins
 * with "/api/v1/rest".
 *
 * @version 2.0
 * @see User
 * @see UserService
 * @see RestTemplate
 */
@RestController
@RequestMapping("/api/v1/rest")
public class RegistrationRestController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationRestController.class);

    /**
     * Service object for working with users.
     */
    private final UserService userService;

    /**
     * Object which offers templates for common scenarios by HTTP method.
     */
    private final RestTemplate restTemplate;

    /**
     * Constructor for initializing the main variables of the registration controller.
     * The @Autowired annotation will allow Spring to automatically initialize objects.
     *
     * @param userService  service object for working with users.
     * @param restTemplate object which offers templates for common scenarios by HTTP method.
     */
    @Autowired
    public RegistrationRestController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    /**
     * Saves user credentials.
     * URL request {"/registration"}, method POST.
     *
     * @param passwordConfirm user password.
     * @param user            valid user.
     * @param bindingResult   errors in validating http request.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registration(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult
    ) {
        if (user == null) {
            logger.error("Registration failed: user object is null");
            return new ResponseEntity<>("User data is missing", HttpStatus.BAD_REQUEST);
        }
        logger.info("Registration attempt for email: {}", user.getEmail());
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        boolean isPasswordDifferent = user.getPassword() != null && !user.getPassword().equals(passwordConfirm);
        Map<String, String> errors = new HashMap<>();
        if (isConfirmEmpty) {
            logger.warn("Registration failed: password confirmation empty for email: {}", user.getEmail());
            errors.put("password2Error", "Password confirmation cannot be empty");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (isPasswordDifferent) {
            logger.warn("Registration failed: passwords do not match for email: {}", user.getEmail());
            errors.put("passwordError", "Passwords do not match");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            logger.warn("Registration failed: validation error for email: {}", user.getEmail());
            Map<String, String> bindingResultErrors = null;
            try {
                bindingResultErrors = ControllerUtils.getErrors(bindingResult);
            } catch (Exception e) {
                logger.error("ControllerUtils.getErrors failed: {}", e.getMessage(), e);
                bindingResultErrors = new HashMap<>();
                bindingResultErrors.put("validationError", "Validation failed");
            }
            return new ResponseEntity<>(bindingResultErrors, HttpStatus.BAD_REQUEST);
        }
        if (!userService.addUser(user)) {
            logger.warn("Registration failed: email already used - {}", user.getEmail());
            errors.put("emailError", "Email is already used");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        logger.info("Registration successful for email: {}", user.getEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}