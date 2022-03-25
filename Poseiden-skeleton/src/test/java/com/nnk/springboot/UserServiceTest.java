package com.nnk.springboot;
import com.nnk.springboot.domain.DTO.UserDTO;
import com.nnk.springboot.services.UserService;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private UserDTO user1DTO;
    private User user1;
    private com.nnk.springboot.domain.User user2;
    private com.nnk.springboot.domain.User user3;
    private List<User> userList;

    @Before
    public void setUp() {
        user1DTO = new UserDTO("username1", "passwd123", "UserName Full", "USER");
        user1 = new User("username1", "passwd123", "UserName Full", "USER");
        user2 = new User("username2", "passwd123", "UserName Full", "USER");
        user2.setId(2);
        user3 = new User("username3", "passwd123", "UserName Full", "USER");
        userList = Arrays.asList(user2, user3);
    }

    @Test
    public void findAllTest() {
        when(userRepository.findAll()).thenReturn(this.userList);
        List<User> UserTest = userService.findAll();
        assertThat(UserTest).isSameAs(userList);
        assertThat(UserTest).hasSize(2);
    }

    @Test
    public void createUserTest() {
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User UserTest = userService.createUser(this.user1DTO);
        assertThat(UserTest).isEqualTo(this.user1);
    }

    @Test
    public void findByIdTest() {

        when(userRepository.findById(2)).thenReturn(Optional.ofNullable(this.user2));
        User UserFoundById = userService.findById(2).get();
        assertThat(UserFoundById).isEqualTo(this.user2);

    }

    @Test
    public void deleteTest() {
        userService.delete(user3);
        verify(userRepository, Mockito.times(1)).delete(user3);

    }
}
