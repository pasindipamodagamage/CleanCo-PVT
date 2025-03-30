package lk.ijse.cleancopvt.service.impl;

import lk.ijse.cleancopvt.dto.CategoryDTO;
import lk.ijse.cleancopvt.entity.Category;
import lk.ijse.cleancopvt.repo.CategoryRepo;
import lk.ijse.cleancopvt.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Add a new category
    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepo.save(category);
    }

    // Update an existing category
    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepo.save(category);
    }

    // Delete a category by ID
    @Override
    public void deleteCategory(UUID id) {
        categoryRepo.deleteById(id);
    }

    // Get all categories
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // Get a category by ID
    @Override
    public CategoryDTO getCategoryById(UUID id) {
        return categoryRepo.findById(id)
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .orElse(null);
    }
}
