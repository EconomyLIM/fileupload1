package file.upload.fileupload1.file.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    protected Item() {
    }

    public Item(final String itemName) {
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
