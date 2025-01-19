package file.upload.fileupload1.file.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) {
        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (file.isEmpty()) {
                continue;
            }

            uploadFiles.add(storeFile(file));
        }

        return uploadFiles;
    }

    public UploadFile storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        // ex image.png
        String originalFilename = file.getOriginalFilename();

        String storeFileName = makeStoreFileName(originalFilename);
        try {
            file.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new UploadFile(originalFilename, storeFileName);
    }

    private String makeStoreFileName(final String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(final String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }


}
