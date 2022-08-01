package mk.ukim.finki.wp.macvilla.service;

import mk.ukim.finki.wp.macvilla.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> listAllCategories();

    Optional<Category> findById(Long categoryId);

    Category save(String name, String description);

    Category update(Long categoryId, String name, String description);

    Category delete(Long categoryId);

}
