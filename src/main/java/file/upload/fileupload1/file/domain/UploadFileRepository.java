package file.upload.fileupload1.file.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    List<UploadFile> findByItem_Id(Long itemId);
}
