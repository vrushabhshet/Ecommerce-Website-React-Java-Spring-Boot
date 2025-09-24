package ecommerce.dinedesign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ecommerce.dinedesign.domain.Product;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.dto.AuthenticationRequestDTO;
import ecommerce.dinedesign.dto.PasswordResetDto;
import ecommerce.dinedesign.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/v1/rest")
public class AuthenticationRestController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationRestController.class);

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            logger.warn("Login failed: email not found - {}", request.getEmail());
            return new ResponseEntity<>("Email not found", HttpStatus.FORBIDDEN);
        }
        if (!request.getPassword().equals(user.getPassword())) {
            logger.warn("Login failed: incorrect password for email - {}", request.getEmail());
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }
        String userRole = user.getRoles().iterator().next().name();
        List<Product> ProductList = user.getProductList();
        Map<Object, Object> response = new HashMap<>();
        response.put("email", user.getEmail());
        response.put("userRole", userRole);
        response.put("ProductList", ProductList);
        logger.info("Login successful for email: {}", request.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody PasswordResetDto passwordReset) {
        logger.info("Password reset requested for email: {}", passwordReset.getEmail());

        boolean forgotPassword = userService.resetPasswordAndSendEmail(passwordReset.getEmail());
        if (!forgotPassword) {
            logger.warn("Password reset failed: email not found - {}", passwordReset.getEmail());
            return new ResponseEntity<>("Email not found", HttpStatus.BAD_REQUEST);
        }
        logger.info("New Password has been sent to your email: {}", passwordReset.getEmail());
        return new ResponseEntity<>("New password has been sent to your E-mail", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Logout attempt");
        request.getSession().invalidate();
        logger.info("Logout successful");
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}