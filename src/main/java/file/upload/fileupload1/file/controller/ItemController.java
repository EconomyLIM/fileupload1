package file.upload.fileupload1.file.controller;

import file.upload.fileupload1.file.domain.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.util.List;

@Controller
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    private final UploadFileRepository uploadFileRepository;

    public ItemController(final ItemRepository itemRepository, final FileStore fileStore, final UploadFileRepository uploadFileRepository) {
        this.itemRepository = itemRepository;
        this.fileStore = fileStore;
        this.uploadFileRepository = uploadFileRepository;
    }

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        System.out.println("aaaaaaaaaaaaaaaa");
        return "item-form";
    }

    @PostMapping("/items/add")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) {
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        // 데이터 베이스 저장
        Item item = new Item(form.getItemName());
        itemRepository.save(item);

        settingItem(attachFile, item);
        uploadFileRepository.save(attachFile);

        for (UploadFile storeImageFile : storeImageFiles) {
            settingItem(storeImageFile, item);
            uploadFileRepository.save(storeImageFile);
        }

        redirectAttributes.addFlashAttribute("itemId", item.getId());
        return "redirect:/items/" + item.getId();
    }

    private static void settingItem(final UploadFile attachFile, final Item item) {
        attachFile.setItem(item);
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("item not found"));
        List<UploadFile> fileList = uploadFileRepository.findByItem_Id(id);
        model.addAttribute("item", item);
        model.addAttribute("fileList", fileList);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

}
