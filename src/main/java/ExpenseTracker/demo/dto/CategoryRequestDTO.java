package ExpenseTracker.demo.dto;

import ExpenseTracker.demo.entities.IncomeExpenseType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDTO {

    private String name;
    private String incomeExpenseType;
}
