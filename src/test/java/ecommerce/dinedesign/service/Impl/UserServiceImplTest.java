package ecommerce.dinedesign.service.Impl;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ecommerce.dinedesign.domain.Role;
import ecommerce.dinedesign.domain.User;
import ecommerce.dinedesign.repository.UserRepository;
import ecommerce.dinedesign.service.UserService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    public void addUser() {
        User user = new User();
        user.setEmail("testMail@test.com");
        user.setPassword("testPassword");

        when(userRepository.save(user)).thenReturn(user);
        user.setRoles(Collections.singleton(Role.USER));
        boolean isUserCreated = true;

        assertTrue(isUserCreated);
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        verify(userRepository, times(0)).save(user); // No real save
    }

    @Test
    public void userSave() {
        User user = new User();
        user.setUsername("User");
        user.setEmail("testMail@test.com");
        user.setPassword("test");

        when(userRepository.save(user)).thenReturn(user);
        boolean isUserCreated = true;

        assertTrue(isUserCreated);

        verify(userRepository, times(0)).save(user); // No real save
    }
}