package com.apiuser.Controller;

import com.apiuser.Entity.User;
import com.apiuser.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody User user) {
        Optional<User> sameEmailUser = userService.findByEmail(user.getEmail());
        if (sameEmailUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email: " + user.getEmail() + " already exists");
        }
        User newUser = userService.add(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        List<User> userList = userService.list();
        return ResponseEntity.ok()
                .body(userList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("ID: " + id + " not found");
        } else {
            userService.delete(id);
            return ResponseEntity.ok()
                    .body("User with id " + id + " deleted");
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found");
        } else {
            return ResponseEntity.ok()
                    .body(user);
        }
    }

    @GetMapping("/searchEmail/{email}")
    public ResponseEntity<?> searchByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with Email " + email + " not found");
        } else {
            return ResponseEntity.ok()
                    .body(user);
        }
    }

}
