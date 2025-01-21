package file.upload.fileupload1.file.domain;


import jakarta.persistence.*;

@Entity
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uploadFileName; // 업로드한 파일 명
    private String storeFileName; // 저장한 파일 명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    protected UploadFile() {
    }

    public UploadFile(final String uploadFileName, final String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile(final String uploadFileName, final String storeFileName, final Item item) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.item = item;
    }

    public void setItem(final Item item) {
        this.item = item;
    }

    public Long getId() {
        return id;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }


}
