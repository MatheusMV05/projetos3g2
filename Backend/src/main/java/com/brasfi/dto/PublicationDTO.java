package com.brasfi.dto;

import com.brasfi.model.PublicationType;
import com.brasfi.model.PublicationStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class PublicationDTO {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String content;
    private PublicationType type;
    private PublicationStatus status;
    private String authorName;
    private CategoryDTO category;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private LocalDateTime scheduledAt;
    private LocalDateTime updatedAt;
    private Integer viewCount;
    private Boolean featured;
    private String metaTitle;
    private String metaDescription;
    private Set<TagDTO> tags;
    private List<PublicationFileDTO> files;
}