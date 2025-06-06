package com.brasfi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicationFileDTO {
    private Long id;
    private String fileName;
    private String originalName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private String fileType;
    private Boolean isMainFile;
    private LocalDateTime uploadDate;
}