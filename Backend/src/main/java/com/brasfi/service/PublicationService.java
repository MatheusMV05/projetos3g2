package com.brasfi.service;

import com.brasfi.dto.*;
import com.brasfi.model.*;
import com.brasfi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagService tagService;

    public Page<PublicationDTO> getAllPublications(PublicationSearchDTO searchDto) {
        Pageable pageable = createPageable(searchDto);
        Page<Publication> publications;

        if (searchDto.getSearchTerm() != null && !searchDto.getSearchTerm().trim().isEmpty()) {
            publications = publicationRepository.searchByContent(
                    searchDto.getSearchTerm(),
                    PublicationStatus.PUBLISHED,
                    pageable
            );
        } else if (searchDto.getType() != null) {
            publications = publicationRepository.findByTypeAndStatusOrderByPublishedAtDesc(
                    searchDto.getType(),
                    PublicationStatus.PUBLISHED,
                    pageable
            );
        } else if (searchDto.getCategoryId() != null) {
            publications = publicationRepository.findByCategoryIdAndStatusOrderByPublishedAtDesc(
                    searchDto.getCategoryId(),
                    PublicationStatus.PUBLISHED,
                    pageable
            );
        } else if (searchDto.getYear() != null) {
            publications = publicationRepository.findByPublicationYear(
                    searchDto.getYear(),
                    PublicationStatus.PUBLISHED,
                    pageable
            );
        } else {
            publications = publicationRepository.findByStatusOrderByPublishedAtDesc(
                    PublicationStatus.PUBLISHED,
                    pageable
            );
        }

        return publications.map(this::convertToDTO);
    }

    public Optional<PublicationDTO> getPublicationBySlug(String slug) {
        Optional<Publication> publication = publicationRepository.findBySlug(slug);
        if (publication.isPresent()) {
            publicationRepository.incrementViewCount(publication.get().getId());
            return Optional.of(convertToDTO(publication.get()));
        }
        return Optional.empty();
    }

    public PublicationDTO createPublication(PublicationCreateDTO createDto, String authorEmail) {
        Publication publication = new Publication();
        mapCreateDtoToEntity(createDto, publication, authorEmail);

        Publication savedPublication = publicationRepository.save(publication);
        return convertToDTO(savedPublication);
    }

    public Optional<PublicationDTO> updatePublication(Long id, PublicationUpdateDTO updateDto) {
        return publicationRepository.findById(id)
                .map(publication -> {
                    mapUpdateDtoToEntity(updateDto, publication);
                    publication.setUpdatedAt(LocalDateTime.now());

                    Publication savedPublication = publicationRepository.save(publication);
                    return convertToDTO(savedPublication);
                });
    }

    public boolean deletePublication(Long id) {
        if (publicationRepository.existsById(id)) {
            publicationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<PublicationDTO> publishPublication(Long id) {
        return publicationRepository.findById(id)
                .map(publication -> {
                    publication.setStatus(PublicationStatus.PUBLISHED);
                    publication.setPublishedAt(LocalDateTime.now());
                    publication.setUpdatedAt(LocalDateTime.now());

                    Publication savedPublication = publicationRepository.save(publication);
                    return convertToDTO(savedPublication);
                });
    }

    public Optional<PublicationDTO> unpublishPublication(Long id) {
        return publicationRepository.findById(id)
                .map(publication -> {
                    publication.setStatus(PublicationStatus.DRAFT);
                    publication.setUpdatedAt(LocalDateTime.now());

                    Publication savedPublication = publicationRepository.save(publication);
                    return convertToDTO(savedPublication);
                });
    }

    public List<PublicationDTO> getRecentPublications(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return publicationRepository.findByStatusOrderByPublishedAtDesc(
                        PublicationStatus.PUBLISHED, pageable)
                .map(this::convertToDTO)
                .getContent();
    }

    public List<PublicationDTO> getFeaturedPublications() {
        return publicationRepository.findByFeaturedTrueAndStatusOrderByPublishedAtDesc(
                        PublicationStatus.PUBLISHED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> getPublicationsByAuthor(String authorEmail) {
        return publicationRepository.findByAuthorEmailOrderByCreatedAtDesc(authorEmail)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<PublicationDTO> getPublicationsByTag(String tagSlug) {
        return publicationRepository.findByTagsSlugAndStatusOrderByPublishedAtDesc(
                        tagSlug, PublicationStatus.PUBLISHED)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void mapCreateDtoToEntity(PublicationCreateDTO createDto, Publication publication, String authorEmail) {
        publication.setTitle(createDto.getTitle());
        publication.setSlug(generateSlug(createDto.getTitle()));
        publication.setContent(createDto.getContent());
        publication.setSummary(createDto.getSummary());
        publication.setType(createDto.getType());
        publication.setStatus(PublicationStatus.DRAFT);
        publication.setFeatured(createDto.isFeatured());
        publication.setAuthorEmail(authorEmail);
        publication.setCreatedAt(LocalDateTime.now());
        publication.setUpdatedAt(LocalDateTime.now());
        publication.setViewCount(0L);

        // Set category
        if (createDto.getCategoryId() != null) {
            categoryRepository.findById(createDto.getCategoryId())
                    .ifPresent(publication::setCategory);
        }

        // Set tags
        if (createDto.getTagNames() != null && !createDto.getTagNames().isEmpty()) {
            Set<Tag> tags = tagService.findOrCreateTags(createDto.getTagNames());
            publication.setTags(tags);
        }
    }

    private void mapUpdateDtoToEntity(PublicationUpdateDTO updateDto, Publication publication) {
        if (updateDto.getTitle() != null) {
            publication.setTitle(updateDto.getTitle());
            publication.setSlug(generateSlug(updateDto.getTitle()));
        }
        if (updateDto.getContent() != null) {
            publication.setContent(updateDto.getContent());
        }
        if (updateDto.getSummary() != null) {
            publication.setSummary(updateDto.getSummary());
        }
        if (updateDto.getType() != null) {
            publication.setType(updateDto.getType());
        }
        if (updateDto.getFeatured() != null) {
            publication.setFeatured(updateDto.getFeatured());
        }

        // Update category
        if (updateDto.getCategoryId() != null) {
            categoryRepository.findById(updateDto.getCategoryId())
                    .ifPresent(publication::setCategory);
        }

        // Update tags
        if (updateDto.getTagNames() != null) {
            if (updateDto.getTagNames().isEmpty()) {
                publication.getTags().clear();
            } else {
                Set<Tag> tags = tagService.findOrCreateTags(updateDto.getTagNames());
                publication.setTags(tags);
            }
        }
    }

    private PublicationDTO convertToDTO(Publication publication) {
        PublicationDTO dto = new PublicationDTO();
        dto.setId(publication.getId());
        dto.setTitle(publication.getTitle());
        dto.setSlug(publication.getSlug());
        dto.setContent(publication.getContent());
        dto.setSummary(publication.getSummary());
        dto.setType(publication.getType());
        dto.setStatus(publication.getStatus());
        dto.setFeatured(publication.isFeatured());
        dto.setAuthorEmail(publication.getAuthorEmail());
        dto.setViewCount(publication.getViewCount());
        dto.setCreatedAt(publication.getCreatedAt());
        dto.setUpdatedAt(publication.getUpdatedAt());
        dto.setPublishedAt(publication.getPublishedAt());

        // Convert category
        if (publication.getCategory() != null) {
            CategoryDTO categoryDto = new CategoryDTO();
            categoryDto.setId(publication.getCategory().getId());
            categoryDto.setName(publication.getCategory().getName());
            categoryDto.setSlug(publication.getCategory().getSlug());
            dto.setCategory(categoryDto);
        }

        // Convert tags
        if (publication.getTags() != null) {
            Set<TagDTO> tagDtos = publication.getTags().stream()
                    .map(tag -> {
                        TagDTO tagDto = new TagDTO();
                        tagDto.setId(tag.getId());
                        tagDto.setName(tag.getName());
                        tagDto.setSlug(tag.getSlug());
                        tagDto.setColor(tag.getColor());
                        return tagDto;
                    })
                    .collect(Collectors.toSet());
            dto.setTags(tagDtos);
        }

        // Convert files
        if (publication.getFiles() != null) {
            Set<PublicationFileDTO> fileDtos = publication.getFiles().stream()
                    .map(file -> {
                        PublicationFileDTO fileDto = new PublicationFileDTO();
                        fileDto.setId(file.getId());
                        fileDto.setFileName(file.getFileName());
                        fileDto.setOriginalFileName(file.getOriginalFileName());
                        fileDto.setFileType(file.getFileType());
                        fileDto.setFileSize(file.getFileSize());
                        fileDto.setFilePath(file.getFilePath());
                        return fileDto;
                    })
                    .collect(Collectors.toSet());
            dto.setFiles(fileDtos);
        }

        return dto;
    }

    private Pageable createPageable(PublicationSearchDTO searchDto) {
        int page = searchDto.getPage() != null ? searchDto.getPage() : 0;
        int size = searchDto.getSize() != null ? searchDto.getSize() : 10;
        String sortBy = searchDto.getSortBy() != null ? searchDto.getSortBy() : "publishedAt";
        String sortDir = searchDto.getSortDirection() != null ? searchDto.getSortDirection() : "desc";

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }

    private String generateSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}