import java.util.Collection;

public interface ItemService {
    void addItem(Item item);

    Collection<Item> getItems(String item);

    Collection<Item> orderItems(String element, String type, String item);

    Collection<Item> searchItems(String search);

    Item getItem(String item, String id);

    Item editItem(Item item);

    boolean deleteItem(String id);

    boolean itemExist(String id);

    Collection<String> getItemTitles(String title);
}
