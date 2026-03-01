package ExpenseTracker.demo.services;

import ExpenseTracker.demo.dto.ExpenseRequestDTO;
import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.entities.Expense;
import ExpenseTracker.demo.entities.IncomeExpenseType;
import ExpenseTracker.demo.entities.User;
import ExpenseTracker.demo.repositories.CategoryRepository;
import ExpenseTracker.demo.repositories.ExpenseRepository;
import ExpenseTracker.demo.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

    public Expense createExpense(UUID userId, UUID categoryId, ExpenseRequestDTO expenseRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));
        Category category = categoryRepository.findByIdAndUser_Id(categoryId, userId).orElseThrow(() -> new RuntimeException("Category and User not found!"));
        if (expenseRequestDTO.getAmount() == null || expenseRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero!");
        }
        IncomeExpenseType type;
        try {
            type = IncomeExpenseType.valueOf(expenseRequestDTO.getType().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid type. Use INCOME or EXPENSE!");
        }
        if (!category.getIncomeExpenseType().equals(type)) {
            throw new RuntimeException("Expense type does not match category type");
        }
        Expense expense = new Expense();
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setDate(expenseRequestDTO.getDate());
        expense.setType(type);
        expense.setUser(user);
        expense.setCategory(category);
        return expenseRepository.save(expense);
    }

//    public Page<Expense> listByUser(UUID userId, Pageable pageable){ intead of using repository -> findBy... using optional filtering
//        return expenseRepository.findByUser_Id(userId, pageable);
//    }
//
//    public Page<Expense> listByUserAndCagory(UUID userId, UUID categoryId, Pageable pageable){
//        return expenseRepository.findByUser_IdAndCategory_Id(userId, categoryId, pageable);
//    }

    public Page<Expense> filterExpenses(UUID userId, UUID categoryId, String typeStr, LocalDate from, LocalDate to, Pageable pageable){
        IncomeExpenseType type = null;
        if(typeStr != null){
            try{
                type = IncomeExpenseType.valueOf(typeStr.toUpperCase());
            } catch(Exception e){
                throw new RuntimeException("Invalid type. Use INCOME or EXPENSE!");
            }
        }
        Specification<Expense> spec = ExpenseSpecification.filterExpenses(userId, categoryId, type, from, to);
        return expenseRepository.findAll(spec, pageable);
    }

    public void deleteExpense(UUID userId, UUID expenseId){
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("expense not found!"));

        if(!expense.getUser().getId().equals(userId)){
            throw new RuntimeException("Expense not found for this user");
        }

        expenseRepository.delete(expense);
    }

    public Expense updateExpense(UUID userId, UUID expenseId, UUID categoryId, ExpenseRequestDTO expenseRequestDTO){
       Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found!"));

       if(!expense.getUser().getId().equals(userId)){
           throw new RuntimeException("Expense not found for this user!");
       }

       Category category = categoryRepository.findByIdAndUser_Id(categoryId, userId).orElseThrow(() -> new RuntimeException("Category not found for this user!"));

       if(expenseRequestDTO.getAmount() != null || expenseRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0){
           throw new RuntimeException("Amount must greater than zero!");
       }

       IncomeExpenseType type;
       try{
           type = IncomeExpenseType.valueOf(expenseRequestDTO.getType().toUpperCase());
       } catch (Exception e){
           throw new RuntimeException("Invalid type. Use INCOME or EXPENSE");
       }

       if(!category.getIncomeExpenseType().equals(type)){
           throw new RuntimeException("Expense type does not match any category type!");
       }

       expense.setAmount(expenseRequestDTO.getAmount());
       expense.setDescription(expenseRequestDTO.getDescription());
       expense.setDate(expenseRequestDTO.getDate());
       expense.setType(type);
       expense.setCategory(category);

       return expenseRepository.save(expense);

    }
}
