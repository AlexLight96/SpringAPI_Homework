package com.project.demo.rest.category;


import com.project.demo.logic.entity.category.Category;
import com.project.demo.logic.entity.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole( 'SUPER_ADMIN')")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole( 'SUPER_ADMIN')")
    public Category updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    Optional.ofNullable(category.getName()).ifPresent(existingCategory::setName);
                    Optional.ofNullable(category.getDescription()).ifPresent(existingCategory::setDescription);
                    return categoryRepository.save(existingCategory);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    return categoryRepository.save(category);
                });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public void deleteCategory(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
    }

}
