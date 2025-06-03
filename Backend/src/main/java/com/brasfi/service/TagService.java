package com.brasfi.service;

import com.brasfi.dto.TagDTO;
import com.brasfi.model.Tag;
import com.brasfi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public List<TagDTO> getAllTags() {
        return tagRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TagDTO> getTagById(Long id) {
        return tagRepository.findById(id)
                .map(this::convertToDTO);
    }

    public Optional<TagDTO> getTagBySlug(String slug) {
        return tagRepository.findBySlug(slug)
                .map(this::convertToDTO);
    }

    public List<TagDTO> getTagsByName(String name) {
        return tagRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TagDTO createTag(TagDTO tagDto) {
        Tag tag = new Tag();
        tag.setName(tagDto.getName());
        tag.setSlug(generateSlug(tagDto.getName()));
        tag.setDescription(tagDto.getDescription());
        tag.setColor(tagDto.getColor());
        tag.setActive(tagDto.isActive());

        Tag savedTag = tagRepository.save(tag);
        return convertToDTO(savedTag);
    }

    public Optional<TagDTO> updateTag(Long id, TagDTO tagDto) {
        return tagRepository.findById(id)
                .map(tag -> {
                    tag.setName(tagDto.getName());
                    tag.setSlug(generateSlug(tagDto.getName()));
                    tag.setDescription(tagDto.getDescription());
                    tag.setColor(tagDto.getColor());
                    tag.setActive(tagDto.isActive());

                    Tag savedTag = tagRepository.save(tag);
                    return convertToDTO(savedTag);
                });
    }

    public boolean deleteTag(Long id) {
        if (tagRepository.existsById(id)) {
            tagRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TagDTO> getActiveTags() {
        return tagRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TagDTO> getMostUsedTags(int limit) {
        return tagRepository.findMostUsedTags(limit)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Set<Tag> findOrCreateTags(Set<String> tagNames) {
        return tagNames.stream()
                .map(name -> {
                    String slug = generateSlug(name);
                    return tagRepository.findBySlug(slug)
                            .orElseGet(() -> {
                                Tag newTag = new Tag();
                                newTag.setName(name);
                                newTag.setSlug(slug);
                                newTag.setActive(true);
                                return tagRepository.save(newTag);
                            });
                })
                .collect(Collectors.toSet());
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        dto.setSlug(tag.getSlug());
        dto.setDescription(tag.getDescription());
        dto.setColor(tag.getColor());
        dto.setActive(tag.isActive());
        dto.setCreatedAt(tag.getCreatedAt());
        dto.setUpdatedAt(tag.getUpdatedAt());
        return dto;
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}