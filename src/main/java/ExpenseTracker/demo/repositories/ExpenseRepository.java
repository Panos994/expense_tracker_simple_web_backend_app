package ExpenseTracker.demo.repositories;

import ExpenseTracker.demo.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Page<Expense> findByUser_Id(UUID userId, Pageable pageable);
    Page<Expense> findByUser_IdAndCategory_Id(UUID userId, UUID categoryId, Pageable pageable);

}
