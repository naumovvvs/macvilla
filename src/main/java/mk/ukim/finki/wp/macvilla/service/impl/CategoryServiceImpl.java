package mk.ukim.finki.wp.macvilla.service.impl;

import mk.ukim.finki.wp.macvilla.model.Category;
import mk.ukim.finki.wp.macvilla.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.macvilla.repository.CategoryRepository;
import mk.ukim.finki.wp.macvilla.service.CategoryService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long categoryId) {
        return this.categoryRepository.findById(categoryId);
    }

    @Override
    public Category save(String name, String description) {
        return this.categoryRepository.save(new Category(name, description));
    }

    @Override
    public Category update(Long categoryId, String name, String description) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    @Override
    public Category delete(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        this.categoryRepository.delete(category);
        return category;
    }
}
