package com.brasfi.controller;

import com.brasfi.dto.*;
import com.brasfi.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping
    public ResponseEntity<Page<PublicationDTO>> getAllPublications(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "publishedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        PublicationSearchDTO searchDto = new PublicationSearchDTO();
        searchDto.setSearchTerm(searchTerm);
        searchDto.setType(type);
        searchDto.setCategoryId(categoryId);
        searchDto.setYear(year);
        searchDto.setPage(page);
        searchDto.setSize(size);
        searchDto.setSortBy(sortBy);
        searchDto.setSortDirection(sortDirection);

        Page<PublicationDTO> publications = publicationService.getAllPublications(searchDto);
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<PublicationDTO> getPublicationBySlug(@PathVariable String slug) {
        PublicationDTO publication = publicationService.getPublicationBySlug(slug);
        return ResponseEntity.ok(publication);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable Long id) {
        PublicationDTO publication = publicationService.getPublicationById(id);
        return ResponseEntity.ok(publication);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<PublicationDTO> createPublication(
            @Valid @RequestBody PublicationCreateDTO publicationCreateDTO,
            Authentication authentication) {
        PublicationDTO createdPublication = publicationService.createPublication(publicationCreateDTO, authentication.getName());
        return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<PublicationDTO> updatePublication(
            @PathVariable Long id,
            @Valid @RequestBody PublicationUpdateDTO publicationUpdateDTO,
            Authentication authentication) {
        PublicationDTO updatedPublication = publicationService.updatePublication(id, publicationUpdateDTO, authentication.getName());
        return ResponseEntity.ok(updatedPublication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<PublicationDTO> publishPublication(@PathVariable Long id) {
        PublicationDTO publishedPublication = publicationService.publishPublication(id);
        return ResponseEntity.ok(publishedPublication);
    }

    @PostMapping("/{id}/unpublish")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<PublicationDTO> unpublishPublication(@PathVariable Long id) {
        PublicationDTO unpublishedPublication = publicationService.unpublishPublication(id);
        return ResponseEntity.ok(unpublishedPublication);
    }

    @PostMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublicationDTO> archivePublication(@PathVariable Long id) {
        PublicationDTO archivedPublication = publicationService.archivePublication(id);
        return ResponseEntity.ok(archivedPublication);
    }

    @GetMapping("/drafts")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Page<PublicationDTO>> getDraftPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublicationDTO> drafts = publicationService.getDraftPublications(page, size);
        return ResponseEntity.ok(drafts);
    }

    @GetMapping("/published")
    public ResponseEntity<Page<PublicationDTO>> getPublishedPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublicationDTO> published = publicationService.getPublishedPublications(page, size);
        return ResponseEntity.ok(published);
    }

    @GetMapping("/archived")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PublicationDTO>> getArchivedPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublicationDTO> archived = publicationService.getArchivedPublications(page, size);
        return ResponseEntity.ok(archived);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<PublicationDTO>> getPublicationsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublicationDTO> publications = publicationService.getPublicationsByCategory(categoryId, page, size);
        return ResponseEntity.ok(publications);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<Page<PublicationDTO>> getPublicationsByTag(
            @PathVariable Long tagId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PublicationDTO> publications = publicationService.getPublicationsByTag(tagId, page, size);
        return ResponseEntity.ok(publications);
    }

    @PostMapping("/{id}/files")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<PublicationFileDTO> uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(required = false) String description) {
        PublicationFileDTO fileDto = publicationService.uploadFile(id, file, description);
        return new ResponseEntity<>(fileDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{publicationId}/files/{fileId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Void> deleteFile(
            @PathVariable Long publicationId,
            @PathVariable Long fileId) {
        publicationService.deleteFile(publicationId, fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<List<PublicationFileDTO>> getPublicationFiles(@PathVariable Long id) {
        List<PublicationFileDTO> files = publicationService.getPublicationFiles(id);
        return ResponseEntity.ok(files);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<PublicationDTO>> getRecentPublications(
            @RequestParam(defaultValue = "5") int limit) {
        List<PublicationDTO> recentPublications = publicationService.getRecentPublications(limit);
        return ResponseEntity.ok(recentPublications);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<PublicationDTO>> getFeaturedPublications() {
        List<PublicationDTO> featuredPublications = publicationService.getFeaturedPublications();
        return ResponseEntity.ok(featuredPublications);
    }

    @PostMapping("/{id}/feature")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublicationDTO> featurePublication(@PathVariable Long id) {
        PublicationDTO featuredPublication = publicationService.featurePublication(id);
        return ResponseEntity.ok(featuredPublication);
    }

    @PostMapping("/{id}/unfeature")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublicationDTO> unfeaturePublication(@PathVariable Long id) {
        PublicationDTO unfeaturedPublication = publicationService.unfeaturePublication(id);
        return ResponseEntity.ok(unfeaturedPublication);
    }
}