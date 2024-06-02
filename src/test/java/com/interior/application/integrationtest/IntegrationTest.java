package com.interior.application.integrationtest;

import com.interior.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled
@SpringBootTest
@ActiveProfiles(value = "dev")
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
        userRepository.findByEmail("a@a.com");
    }
}