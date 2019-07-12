package services;

import entities.Item;

import java.util.Collection;

public interface ItemService {
    void addItem(Item item);

    Collection<Item> getItems(String item);

    Collection<Item> orderItems(String element, String type, String item);

    Collection<Item> searchItemsByPrices(String minPrice, String maxPrice, String item);

    Item getItem(String item, String id);

    Item editItem(Item item, String id);

    boolean deleteItem(String id);

    Collection<Item> getTaggedItems(String item);

    Collection<String> getItemTitles(String title);
}
