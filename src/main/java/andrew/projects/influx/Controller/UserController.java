package andrew.projects.influx.Controller;

import andrew.projects.influx.Domain.User;
import andrew.projects.influx.Repos.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @PutMapping("/idUser")
    public ResponseEntity<?> updateUser(User u, @PathVariable int idUser) {
        if (idUser != u.getId()) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Optional<User> storedUser = userRepo.findById(idUser);
        if (storedUser.isPresent()) {
            User user = storedUser.get();
            user.setEmail(u.getEmail());
            user.setUsername(u.getUsername());
            user.setAuthorities(u.getAuthorities());

            return ResponseEntity.ok(userRepo.save(user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}