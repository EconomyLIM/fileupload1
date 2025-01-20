package file.upload.fileupload1.file.controller;

import file.upload.fileupload1.file.controller.dto.ItemForm;
import file.upload.fileupload1.file.domain.*;
import file.upload.fileupload1.file.service.ItemService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.MalformedURLException;
import java.util.List;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(final ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm form) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) {
        Long itemId = itemService.saveItem(form);
        redirectAttributes.addFlashAttribute("itemId", itemId);
        return "redirect:/items/" + itemId;
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id);
        List<UploadFile> fileList = itemService.findUploadFilesByItemId(id);

        model.addAttribute("item", item);
        model.addAttribute("fileList", fileList);
        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) {
        return itemService.getUrlResource(filename);
    }

}
