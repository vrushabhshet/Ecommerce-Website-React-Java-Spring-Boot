package ecommerce.dinedesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class EcommerceApplication {

    private static final Logger logger = LoggerFactory.getLogger(EcommerceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
        logger.info("EcommerceApplication started successfully.");
    }

}