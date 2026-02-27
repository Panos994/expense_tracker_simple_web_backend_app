package ExpenseTracker.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    private String fullName;
    @NotNull
    @NotBlank(message = "email is required")
    @Email
    private String email;
    @NotNull
    @NotBlank(message = "password is required")
    private String password;


}
