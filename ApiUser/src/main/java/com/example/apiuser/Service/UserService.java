package com.example.apiuser.Service;

import com.example.apiuser.Entity.User;
import com.example.apiuser.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> list() {
        return userRepository.findAll();
    }

    public User add(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            newUser.setEmail(user.getEmail());
        }
        newUser.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
