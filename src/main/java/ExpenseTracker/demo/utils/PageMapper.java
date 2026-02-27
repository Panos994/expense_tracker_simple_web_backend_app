package ExpenseTracker.demo.utils;

import ExpenseTracker.demo.dto.PageResponseDTO;
import org.springframework.data.domain.Page;

public final class PageMapper {

   public static <T> PageResponseDTO<T> toResponse(Page<T> page){
        return PageResponseDTO.<T>builder()
                .items(page.getContent())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .hasNext(page.hasNext())
                .hasPrevious(page.hasPrevious())
                .page(page.getNumber())
                .build();
   }
}
