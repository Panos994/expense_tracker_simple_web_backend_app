package ExpenseTracker.demo.controllers;

import ExpenseTracker.demo.dto.RegisterRequestDTO;
import ExpenseTracker.demo.dto.RegisterResponseDTO;
import ExpenseTracker.demo.entities.User;
import ExpenseTracker.demo.services.CustomUserDetails;
import ExpenseTracker.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    //    @PostMapping("/create")
//    public ResponseEntity<User> registerUser(@RequestBody RegisterRequestDTO registerRequestDTO){
//        return ResponseEntity.ok(userService.registerUser(registerRequestDTO));
//    }
    @PostMapping("/create")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO){
        User user = userService.registerUser(registerRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(user));

    }

    @GetMapping("/me")
    public ResponseEntity<RegisterResponseDTO> getUser(Authentication authentication){
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(mapToResponse(user.getUser()));
    }



    private RegisterResponseDTO mapToResponse(User user){
        return RegisterResponseDTO.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
