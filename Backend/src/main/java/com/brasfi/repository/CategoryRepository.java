package com.brasfi.repository;

import com.brasfi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findBySlug(String slug);

    List<Category> findAllByOrderByNameAsc();

    @Query("SELECT c FROM Category c LEFT JOIN c.publications p WHERE p.status = 'PUBLISHED' GROUP BY c ORDER BY COUNT(p) DESC")
    List<Category> findCategoriesWithPublishedContent();
}