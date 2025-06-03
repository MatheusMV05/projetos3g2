package com.brasfi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "publication_files")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;

    @Column(name = "file_name", nullable = false, length = 500)
    private String fileName;

    @Column(name = "original_name", nullable = false, length = 500)
    private String originalName;

    @Column(name = "file_path", nullable = false, length = 1000)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "is_main_file")
    private Boolean isMainFile = false;

    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    public enum FileType {
        PDF, IMAGE, VIDEO, DOCUMENT
    }
}