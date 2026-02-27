package ExpenseTracker.demo.dto;

import ExpenseTracker.demo.entities.IncomeExpenseType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {
    private UUID categoryId;
    private String name;
    private IncomeExpenseType incomeExpenseType;
}
