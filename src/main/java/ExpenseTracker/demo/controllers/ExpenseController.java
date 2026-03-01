package ExpenseTracker.demo.controllers;

import ExpenseTracker.demo.dto.CategoryResponseDTO;
import ExpenseTracker.demo.dto.ExpenseRequestDTO;
import ExpenseTracker.demo.dto.ExpenseResponseDTO;
import ExpenseTracker.demo.dto.PageResponseDTO;
import ExpenseTracker.demo.entities.Category;
import ExpenseTracker.demo.entities.Expense;
import ExpenseTracker.demo.services.ExpenseService;
import ExpenseTracker.demo.services.ExpenseSpecification;
import ExpenseTracker.demo.utils.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<PageResponseDTO<ExpenseResponseDTO>> getExpenses(@PathVariable UUID userId, @RequestParam(required = false) UUID categoryId, @RequestParam(required = false) String type,
                                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to, Pageable pageable) {

        Page<ExpenseResponseDTO> page = expenseService.filterExpenses(userId, categoryId, type, from, to, pageable).map(this::mapToResponse);

        return ResponseEntity.ok(PageMapper.toResponse(page));

    }

    @DeleteMapping("/users/{userId}/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable UUID userId, @PathVariable UUID expenseId){
        expenseService.deleteExpense(userId, expenseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{userId}/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable UUID userId, @PathVariable UUID expenseId, @RequestParam UUID categoryId, @RequestBody ExpenseRequestDTO dto){
        Expense updated = expenseService.updateExpense(userId, expenseId, categoryId, dto);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    private ExpenseResponseDTO mapToResponse(Expense expense){
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .category(expense.getCategory().getName())
                .createdAt(expense.getCreatedAt())
                .build();
    }
}
