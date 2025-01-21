package file.upload.fileupload1.file.controller;

import file.upload.fileupload1.file.controller.dto.ItemForm;
import file.upload.fileupload1.file.controller.dto.response.ItemFindByIdResponse;
import file.upload.fileupload1.file.controller.dto.response.ItemSaveResponse;
import file.upload.fileupload1.file.domain.*;
import file.upload.fileupload1.file.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping(value = "/items/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ItemSaveResponse> saveItem(@ModelAttribute ItemForm form) {
        return ResponseEntity.ok(itemService.saveItem(form));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<ItemFindByIdResponse> items(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id);
        List<UploadFile> fileList = itemService.findUploadFilesByItemId(id);

        return ResponseEntity.ok(new ItemFindByIdResponse(item, fileList));
    }

    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) {
        return itemService.getUrlResource(filename);
    }

}
