import java.util.Collection;

public interface ItemService {
    void addItem(Item item);
    Collection<Item> getItems(String criterio, String order);
    Collection<Item> searchItems(String search);
    Item getItem(String id);
    Item editItem(Item item);
    boolean deleteItem(String id);
    boolean itemExist(String id);
    Collection<String> getTitleItems();
}
