package lk.ijse.cleancopvt.service;

import lk.ijse.cleancopvt.dto.CategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
        public void addCategory(CategoryDTO categoryDTO);
        public void updateCategory(CategoryDTO categoryDTO);
        public void deleteCategory(UUID id);
        public List<CategoryDTO> getAllCategories();
        public CategoryDTO getCategoryById(UUID id);
}
