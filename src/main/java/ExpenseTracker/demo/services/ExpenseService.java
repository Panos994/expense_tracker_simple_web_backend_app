package ExpenseTracker.demo.services;

import ExpenseTracker.demo.dto.CategoryRequestDTO;
import ExpenseTracker.demo.dto.ExpenseRequestDTO;
import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.entities.Expense;
import ExpenseTracker.demo.entities.User;
import ExpenseTracker.demo.repositories.CategoryRepository;
import ExpenseTracker.demo.repositories.ExpenseRepository;
import ExpenseTracker.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Expense createExpense(UUID userId, UUID categoryId, ExpenseRequestDTO expenseRequestDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        Category category = categoryRepository.findByIdAndUser_Id(categoryId, userId).orElseThrow(() -> new RuntimeException("Category and User not found!"));


        if(!category.getIncomeExpenseType().equals(expenseRequestDTO.getType())){
            throw new RuntimeException("Expense type must match category type!");
        }
        Expense expense = new Expense();

        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setDate(expenseRequestDTO.getDate());
        expense.setType(expenseRequestDTO.getType());
        expense.setUser(user);
        expense.setCategory(category);

        return expenseRepository.save(expense);

    }
}
