package ExpenseTracker.demo.dto;

import ExpenseTracker.demo.entities.Role;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {

    private UUID id;
    private String email;
    private Role role;
}
