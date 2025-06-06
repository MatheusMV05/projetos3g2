package com.brasfi.repository;

import com.brasfi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findBySlug(String slug);

    List<Tag> findByNameContainingIgnoreCase(String name);

    List<Tag> findAllByOrderByNameAsc();

    List<Tag> findByActiveTrueOrderByNameAsc();

    @Query("SELECT t FROM Tag t ORDER BY t.usageCount DESC")
    List<Tag> findMostUsedTags(int limit);

    @Modifying
    @Query("UPDATE Tag t SET t.usageCount = t.usageCount + 1 WHERE t.id = :id")
    void incrementUsageCount(@Param("id") Long id);
}