package com.interior;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class InteriorApplication {

    private static final Logger logger = LoggerFactory.getLogger(InteriorApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(InteriorApplication.class, args);

        logger.info("This is an info log message");
        logger.debug("This is a debug log message");
    }

}