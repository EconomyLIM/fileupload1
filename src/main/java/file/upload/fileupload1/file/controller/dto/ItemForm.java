package file.upload.fileupload1.file.controller.dto;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ItemForm {

    private Long itemId;
    private String itemName;
    private List<MultipartFile> imageFiles;

    public ItemForm() {
    }

    public ItemForm(final Long itemId, final String itemName, final List<MultipartFile> imageFiles) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.imageFiles = imageFiles;
    }

    public ItemForm(final String itemName, final List<MultipartFile> imageFiles) {
        this.itemName = itemName;
        this.imageFiles = imageFiles;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(final Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(final String itemName) {
        this.itemName = itemName;
    }

    public List<MultipartFile> getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(final List<MultipartFile> imageFiles) {
        this.imageFiles = imageFiles;
    }
}
