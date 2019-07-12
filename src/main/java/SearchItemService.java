import java.util.List;
import java.util.Map;

public interface SearchItemService {
    void addItem(Item item);
    Map<String,Item> getAllResult();
    List<Item> searchItems(String search);
    void cleanItemsCache();
    boolean deleteItem(String id);
    Item editItem(Item item);
}
