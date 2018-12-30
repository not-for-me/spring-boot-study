package kr.notforme.boot.cache.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.notforme.boot.cache.api.UserRepository.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/v1/users")
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/api/v1/users/{id}")
    public User getUser(@PathVariable int id) {
        return userRepository.findOne(id);
    }
}
