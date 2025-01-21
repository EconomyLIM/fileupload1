package file.upload.fileupload1.file.controller.dto.response;

import file.upload.fileupload1.file.controller.dto.CommonResponse;

/**
 * date           : 2025-01-21
 * created by     : 임경재
 * description    :
 */
public class ItemSaveResponse extends CommonResponse {
    private Long itemId;

    public ItemSaveResponse(final Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemId() {
        return itemId;
    }
}
