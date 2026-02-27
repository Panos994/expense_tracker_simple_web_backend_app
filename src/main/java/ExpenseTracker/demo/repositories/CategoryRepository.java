package ExpenseTracker.demo.repositories;

import ExpenseTracker.demo.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Page<Category> findByUser_Id(UUID userId, Pageable pageable);
    boolean existsByUser_IdAndNameIgnoreCase(UUID userId, String name);
    Optional<Category> findByIdAndUser_Id(UUID id, UUID userId);
}
