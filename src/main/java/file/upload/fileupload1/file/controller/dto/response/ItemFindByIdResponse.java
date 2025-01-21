package file.upload.fileupload1.file.controller.dto.response;

import file.upload.fileupload1.file.controller.dto.CommonResponse;
import file.upload.fileupload1.file.domain.Item;
import file.upload.fileupload1.file.domain.UploadFile;

import java.util.List;

/**
 * date           : 2025-01-21
 * created by     : 임경재
 * description    :
 */
public class ItemFindByIdResponse extends CommonResponse {
    private Item item;
    private List<UploadFile> uploadFiles;

    public ItemFindByIdResponse(final Item item, final List<UploadFile> uploadFiles) {
        this.item = item;
        this.uploadFiles = uploadFiles;
    }

    public Item getItem() {
        return item;
    }

    public List<UploadFile> getUploadFiles() {
        return uploadFiles;
    }
}
