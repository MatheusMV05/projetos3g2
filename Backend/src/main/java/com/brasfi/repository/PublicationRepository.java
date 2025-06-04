package com.brasfi.repository;

import com.brasfi.model.Publication;
import com.brasfi.model.PublicationType;
import com.brasfi.model.PublicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    // Buscar por slug
    Optional<Publication> findBySlug(String slug);

    // Buscar publicações publicadas
    Page<Publication> findByStatusOrderByPublishedAtDesc(PublicationStatus status, Pageable pageable);

    // Buscar por tipo e status
    Page<Publication> findByTypeAndStatusOrderByPublishedAtDesc(PublicationType type, PublicationStatus status, Pageable pageable);

    // Buscar por categoria
    Page<Publication> findByCategoryIdAndStatusOrderByPublishedAtDesc(Long categoryId, PublicationStatus status, Pageable pageable);

    // Buscar publicações em destaque
    List<Publication> findByFeaturedTrueAndStatusOrderByPublishedAtDesc(PublicationStatus status);

    // Buscar publicações agendadas para agora
    @Query("SELECT p FROM Publication p WHERE p.status = 'SCHEDULED' AND p.scheduledAt <= :now")
    List<Publication> findScheduledForPublication(@Param("now") LocalDateTime now);

    // Busca por texto completo
    @Query("SELECT p FROM Publication p WHERE " +
            "MATCH(p.title, p.summary, p.content, p.searchKeywords) AGAINST(:searchTerm IN NATURAL LANGUAGE MODE) " +
            "AND p.status = :status " +
            "ORDER BY MATCH(p.title, p.summary, p.content, p.searchKeywords) AGAINST(:searchTerm IN NATURAL LANGUAGE MODE) DESC")
    Page<Publication> searchByContent(@Param("searchTerm") String searchTerm,
                                      @Param("status") PublicationStatus status,
                                      Pageable pageable);

    // Buscar por ano de publicação
    @Query("SELECT p FROM Publication p WHERE YEAR(p.publishedAt) = :year AND p.status = :status ORDER BY p.publishedAt DESC")
    Page<Publication> findByPublicationYear(@Param("year") Integer year,
                                            @Param("status") PublicationStatus status,
                                            Pageable pageable);

    // Buscar por tag
    @Query("SELECT p FROM Publication p JOIN p.tags t WHERE t.id = :tagId AND p.status = :status ORDER BY p.publishedAt DESC")
    Page<Publication> findByTagId(@Param("tagId") Long tagId,
                                  @Param("status") PublicationStatus status,
                                  Pageable pageable);

    // Incrementar contador de visualizações
    @Query("UPDATE Publication p SET p.viewCount = p.viewCount + 1 WHERE p.id = :id")
    void incrementViewCount(@Param("id") Long id);
}