package ExpenseTracker.demo.controllers;

import ExpenseTracker.demo.dto.CategoryRequestDTO;
import ExpenseTracker.demo.dto.CategoryResponseDTO;
import ExpenseTracker.demo.dto.PageResponseDTO;
import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.services.CategoryService;
import ExpenseTracker.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO, @PathVariable UUID userId){
        Category category = categoryService.createCategory(userId, categoryRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(category));
    }

    @GetMapping("/user/{userId}/categories")
    public ResponseEntity<PageResponseDTO<CategoryResponseDTO>> getUserCategories(@PathVariable UUID userId, Pageable pageable){
        Page<CategoryResponseDTO> categoryResponseDTOPage = categoryService.listUserCategories(userId,pageable).map(this::mapToResponse);
        PageResponseDTO<CategoryResponseDTO> response = PageMapper.toResponse(categoryResponseDTOPage);

        return ResponseEntity.ok(response);
    }

    private CategoryResponseDTO mapToResponse(Category category){
        return CategoryResponseDTO.builder()
                .name(category.getName())
                .categoryId(category.getId())
                .incomeExpenseType(category.getIncomeExpenseType())
                .build();
    }
}
