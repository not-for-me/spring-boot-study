package kr.notforme.boot.cache.api;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Repository
public class UserRepository {
    private static final Map<Integer, User> USER_MAP;
    private static final Random random = new Random(System.currentTimeMillis());

    static {
        USER_MAP = new HashMap<>();
        USER_MAP.put(1, new User(1, "Alice", 22));
        USER_MAP.put(2, new User(2, "Bob", 21));
        USER_MAP.put(3, new User(3, "Carol", 12));
    }

    public List<User> findAll() {
        simulateWaiting();
        return USER_MAP.values().stream()
                       .sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
    }

    public User findOne(int id) {
        simulateWaiting();
        return USER_MAP.getOrDefault(id, new User());
    }

    private void simulateWaiting() {
        try {
            Thread.sleep(getRandomMillis());
        } catch (InterruptedException e) {
            // noop
        }
    }

    private int getRandomMillis() {
        final int maxSeconds = 5;
        return random.nextInt(maxSeconds) * 1000;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private int id;
        private String name;
        private int age;
    }

}
