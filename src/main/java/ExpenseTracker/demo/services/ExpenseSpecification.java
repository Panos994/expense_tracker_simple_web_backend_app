package ExpenseTracker.demo.services;

import ExpenseTracker.demo.entities.Expense;
import ExpenseTracker.demo.entities.IncomeExpenseType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpenseSpecification {

    public static Specification<Expense> filterExpenses(UUID userId, UUID categoryId, IncomeExpenseType type, LocalDate from, LocalDate to){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));

            if(categoryId!=null){
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            if(type != null){
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if(from!= null && to !=null){
                predicates.add(criteriaBuilder.between(root.get("date"), from, to));
            } else if(from != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"),from));
            } else if (to != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"),to));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
