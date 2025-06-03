package com.brasfi.dto;

import com.brasfi.model.PublicationType;
import lombok.Data;

import java.util.Set;

@Data
public class PublicationSearchDTO {
    private String searchTerm;
    private PublicationType type;
    private Long categoryId;
    private Set<Long> tagIds;
    private Integer year;
    private Boolean featured;
    private int page = 0;
    private int size = 10;
    private String sortBy = "publishedAt";
    private String sortDirection = "DESC";
}