import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceMapImpl implements ItemService {

    JsonHandler jsonHandler = new JsonHandler();

    public Collection<Item> getItems(String itemToSearch) {
        return jsonHandler.completeJsonList(itemToSearch);
    }

    public Collection<Item> orderItems(String element, String type, String item) {
        return jsonHandler.orderItems(element, type, item);
    }

    public Collection<String> getItemTitles(String item) {
        return jsonHandler.getItemTitles(item);

    }

    public Item getItem(String item, String id) {
        return jsonHandler.getItemById(item, id);
    }

    @Override
    public Collection<Item> searchItemsByPrices(String min, String max, String item) {
        List<Item> listItems = jsonHandler.filterItemsByPrices(min, max, item);

        return listItems;
    }

    public Item editItem(Item item, String id) {
        Item updatedItem = jsonHandler.updateItem(item, id);
        return item;

    }

    public Collection<Item> getTaggedItems(String item) {
        List<Item> listItems = jsonHandler.getTaggedItems(item);

        return listItems;
    }

    public boolean deleteItem(String id) {
        //return searchItemService.deleteItem(id);
        return false;

    }

    public boolean itemExist(String id) {
        //return searchItemService.getAllResult().containsKey(id);
        return false;

    }

    public void addItem(Item item) {
       /* if (item != null)
            searchItemService.addItem(item);*/
    }
}
