package ExpenseTracker.demo.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseResponseDTO {

    private UUID id;
    private BigDecimal amount;
    private String category;
    private LocalDateTime createdAt;
}
