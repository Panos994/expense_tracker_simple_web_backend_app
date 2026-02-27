package ExpenseTracker.demo.services;

import ExpenseTracker.demo.dto.CategoryRequestDTO;
import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.entities.IncomeExpenseType;
import ExpenseTracker.demo.entities.User;
import ExpenseTracker.demo.repositories.CategoryRepository;
import ExpenseTracker.demo.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Category createCategory(UUID userId, CategoryRequestDTO categoryRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found!"));

        if (categoryRepository.existsByUser_IdAndNameIgnoreCase(userId, categoryRequestDTO.getName())) {
            throw new RuntimeException("Category with this name already exists for this user!");
        }

        IncomeExpenseType type;
        try {
            type = IncomeExpenseType.valueOf(categoryRequestDTO.getIncomeExpenseType().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid income/expense type. Use INCOME or EXPENSE!");
        }
        Category category = new Category();

        category.setName(categoryRequestDTO.getName());
        category.setIncomeExpenseType(type);
        category.setUser(user);

        return categoryRepository.save(category);
    }

    public Page<Category> listUserCategories(UUID userId, Pageable pageable){
//        Page<Category> userCategories = categoryRepository.findByUser_Id(userId, pageable);
//        return userCategories;
        return categoryRepository.findByUser_Id(userId, pageable);
    }
}
