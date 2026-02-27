package ExpenseTracker.demo.dto;

import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.entities.IncomeExpenseType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseRequestDTO {

    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private IncomeExpenseType type;
    private UUID categoryId;
}
