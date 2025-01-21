package file.upload.fileupload1.file.service;

import file.upload.fileupload1.file.controller.dto.ItemForm;
import file.upload.fileupload1.file.controller.dto.response.ItemSaveResponse;
import file.upload.fileupload1.file.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.List;

/**
 * date           : 2025-01-20
 * created by     : 임경재
 * description    :
 */
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    private final UploadFileRepository uploadFileRepository;
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    public ItemService(final ItemRepository itemRepository, final FileStore fileStore, final UploadFileRepository uploadFileRepository) {
        this.itemRepository = itemRepository;
        this.fileStore = fileStore;
        this.uploadFileRepository = uploadFileRepository;
    }

    @Transactional
    public ItemSaveResponse saveItem(ItemForm form){
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        // 데이터 베이스 저장
        Item item = new Item(form.getItemName());
        itemRepository.save(item);

        for (UploadFile storeImageFile : storeImageFiles) {
            settingItem(storeImageFile, item);
            uploadFileRepository.save(storeImageFile);
        }
        return new ItemSaveResponse(item.getId());
    }

    private static void settingItem(final UploadFile attachFile, final Item item) {
        attachFile.setItem(item);
    }

    public Item findItemById(Long id){
        return itemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("item not found"));
    }

    public List<UploadFile> findUploadFilesByItemId(Long id){
        return uploadFileRepository.findByItem_Id(id);
    }

    public String getFullPath(String filename){
        return fileStore.getFullPath(filename);
    }

    public UrlResource getUrlResource(String filename){
        try {
            return new UrlResource("file:" + getFullPath(filename));
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
