package com.brasfi.repository;

import com.brasfi.model.PublicationFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationFileRepository extends JpaRepository<PublicationFile, Long> {

    List<PublicationFile> findByPublicationIdOrderByUploadDateDesc(Long publicationId);

    List<PublicationFile> findByPublicationIdAndFileType(Long publicationId, PublicationFile.FileType fileType);
}