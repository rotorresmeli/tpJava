package services;

import entities.Item;
import entities.JsonHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ItemServiceMapImpl implements ItemService {

    private HashMap<String, Item> itemsResult;
    private HashMap<String, List<Item>> cacheItems;

    public ItemServiceMapImpl() {
        this.cacheItems = new HashMap<>();//Para ir guardando resultados parciales
        this.itemsResult = new HashMap<>();
    }

    JsonHandler jsonHandler = new JsonHandler();

    public Collection<Item> getItems(String itemToSearch) {
        List<Item> items = new ArrayList<>();

        if (cacheItems.containsKey(itemToSearch)) {
            items = cacheItems.get(itemToSearch);
            return items;
        }

        items = jsonHandler.completeJsonList(itemToSearch);

        for (Item item: items) {
            itemsResult.put(item.getId(), item);
        }
        cacheItems.put(itemToSearch, items);

        return items;
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
        return itemsResult.replace(item.getId(),item);
    }

    public Collection<Item> getTaggedItems(String item) {
        List<Item> listItems = jsonHandler.getTaggedItems(item);

        return listItems;
    }

    public boolean deleteItem(String id) {
        return itemsResult.remove(id) != null;
    }

    public void addItem(Item item) {
        itemsResult.put(item.getId(), item);
    }
}
