package file.upload.fileupload1.file.service;

import file.upload.fileupload1.file.controller.dto.ItemForm;
import file.upload.fileupload1.file.controller.dto.response.ItemSaveResponse;
import file.upload.fileupload1.file.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private FileStore fileStore;

    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    @Transactional
    void saveItem_shouldSaveItemAndUploadFiles() {
        // Given
        ItemForm form = new ItemForm("Test Item", List.of(new MockMultipartFile("file", "test.jpg", "image/jpeg", "test data".getBytes())));
        Item mockItem = new Item("Test Item");

        List<UploadFile> mockUploadFiles = List.of(new UploadFile("test.jpg", "stored-test.jpg"));

        when(fileStore.storeFiles(form.getImageFiles())).thenReturn(mockUploadFiles);
        when(itemRepository.save(any(Item.class))).thenReturn(mockItem);

        // When
        ItemSaveResponse itemSaveResponse = itemService.saveItem(form);

        org.assertj.core.api.Assertions.assertThat(itemSaveResponse.getItemId()).isEqualTo(mockItem.getId());
//        // Then
//        assertNotNull(itemId);
//        assertEquals(1L, itemId);
//        verify(itemRepository).save(any(Item.class));
//        verify(uploadFileRepository, times(1)).save(any(UploadFile.class));
    }

    @Test
    void findItemById_shouldReturnItem() {
        // Given
        Long itemId = 1L;
        Item mockItem = new Item("Test Item");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(mockItem));

        // When
        Item result = itemService.findItemById(itemId);

        // Then
        assertNotNull(result);
        assertEquals("Test Item", result.getItemName());
        verify(itemRepository).findById(itemId);
    }

    @Test
    void findItemById_shouldThrowExceptionWhenItemNotFound() {
        // Given
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.findItemById(itemId));
        assertEquals("item not found", exception.getMessage());
    }

    @Test
    void findUploadFilesByItemId_shouldReturnFiles() {
        // Given
        Long itemId = 1L;
        List<UploadFile> mockFiles = List.of(new UploadFile("test.jpg", "stored-test.jpg"));
        when(uploadFileRepository.findByItem_Id(itemId)).thenReturn(mockFiles);

        // When
        List<UploadFile> files = itemService.findUploadFilesByItemId(itemId);

        // Then
        assertNotNull(files);
        assertEquals(1, files.size());
        assertEquals("test.jpg", files.get(0).getUploadFileName());
        verify(uploadFileRepository).findByItem_Id(itemId);
    }

    @Test
    void getFullPath_shouldReturnFullPath() {
        // Given
        String filename = "stored-test.jpg";
        String expectedPath = "/uploads/stored-test.jpg";
        when(fileStore.getFullPath(filename)).thenReturn(expectedPath);

        // When
        String fullPath = itemService.getFullPath(filename);

        // Then
        assertEquals(expectedPath, fullPath);
        verify(fileStore).getFullPath(filename);
    }

    @Test
    void getUrlResource_shouldReturnUrlResource() throws IOException {
        // Given
        String filename = "stored-test.jpg";
        String fullPath = "C:/file/fileupload1/stored-test.jpg";
        when(fileStore.getFullPath(filename)).thenReturn(fullPath);

        // When
        UrlResource resource = itemService.getUrlResource(filename);

        // Then
        assertNotNull(resource);
        assertEquals("file:C:/file/fileupload1/stored-test.jpg", resource.getURI().toString());
    }

//    @Test
    // Todo 불가능한 파일을 업로드시 예외 발생
    void getUrlResource_shouldThrowExceptionForInvalidUrl() {
        // Given
        String filename = "invalid-file.jpg";
        when(fileStore.getFullPath(filename)).thenReturn("invalid-path");

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.getUrlResource(filename));
        assertTrue(exception.getMessage().contains("invalid-path"));
    }
}