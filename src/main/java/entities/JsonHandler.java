package entities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Currency;
import entities.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class JsonHandler {

    public static Item[] getItems(String url) {
        JsonElement jsonElement = new JsonParser().parse(url);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();

        Item item;
        JsonObject auxObject;

        Item[] items = new Item[jsonArray.size()];
        int i = 0;
        for (JsonElement elemento : jsonArray) {

            auxObject = elemento.getAsJsonObject();
            item = new Item();
            item.setId(auxObject.get("id").getAsString());
            item.setSiteId(auxObject.get("site_id").getAsString());
            item.setTitle(auxObject.get("title").getAsString());
            item.setPrice(auxObject.get("price").getAsInt());
            item.setCurrency(new Currency(auxObject.get("currency_id").getAsString()));
            item.setListingType(auxObject.get("listing_type_id").getAsString());
            item.setStopTime(auxObject.get("stop_time").getAsString());
            item.setThumbnail(auxObject.get("thumbnail").getAsString());
            JsonArray tagsJsonArray = auxObject.get("tags").getAsJsonArray();
            String[] tagsArray = new String[tagsJsonArray.size()];
            for (int j = 0; j < tagsJsonArray.size(); j++) {
                tagsArray[j] = tagsJsonArray.get(j).getAsString();
            }
            item.setTags(tagsArray);

            items[i] = item;
            i++;

        }
        return items;
    }

    public List<Item> completeJsonList(String itemToSearch) {
        List<Item> listItems = new ArrayList<>();
        URL url;

        try {
            url = new URL("https://api.mercadolibre.com/sites/MLA/search?q=" + itemToSearch);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                throw new MalformedURLException("UrlConnection type is invalid. HttpUrlConnection required.");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String urlString = "";
            String current = null;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }

            Item[] items = getItems(urlString);
            listItems = Arrays.asList(items);

        } catch (MalformedURLException e) {
            System.out.println("Invalid url: " + e.getMessage());

        } catch (IOException e) {
            System.out.println("Input/Output connection problem: " + e.getMessage());
        }
        return listItems;
    }

    public Item getItemById(String item, String id) {
        List<Item> items = completeJsonList(item);
        try {
            return items.stream().filter(s -> s.getId()
                    .equalsIgnoreCase(id))
                    .findFirst()
                    .get();
        } catch (NoSuchElementException e) {
            System.out.println("No se encontro el id del item");
        }
        return null;
    }

    public List<String> getItemTitles(String item) {
        List<Item> items = completeJsonList(item);
        try {
            return items.stream()
                    .map(Item::getTitle)
                    .collect(Collectors.toList());

        } catch (NoSuchElementException e) {
            System.out.println("No se encontraron items");
        }
        return null;
    }

    public List<Item> orderItems(String element, String type, String item) {
        List<Item> items = completeJsonList(item);

        if (element != null && element.equalsIgnoreCase("PRICE")) {
            Item.orderElement = Item.OrderElement.PRICE;
        } else if (element != null && element.equalsIgnoreCase("LISTING_TYPE")) {
            Item.orderElement = Item.OrderElement.LISTING_TYPE;
        }

        if (type != null && type.equalsIgnoreCase("DESC")) {
            Collections.sort(items);
        } else {
            Collections.sort(items, Collections.reverseOrder());
        }
        return items;
    }

    public List<Item> filterItemsByPrices(String min, String max, String item) {
        List<Item> items = completeJsonList(item);
        try {
            return items.stream()
                    .filter(s -> s.getPrice() >= Integer.valueOf(min) && s.getPrice() <= Integer.valueOf(max))
                    .collect(Collectors.toList());
        } catch (NoSuchElementException e) {
            System.out.println("No se encontro un item en ese rango de precios");
        }
        return null;
    }

    public Item updateItem(Item itemToUpdate, String id) {
        List<Item> items = completeJsonList(id);
        return null;
        /*return items.stream()
                    .map(s -> s.getId() == itemToUpdate.getId() ? entities.Item : s)
                    .collect(Collectors.toList());
                    putIntoCache(newObject.getKey(), newItems);*/

    }

    public List<Item> getTaggedItems(String item) {
        List<Item> items = completeJsonList(item);
        return items.stream()
                .filter(s -> s.getTags().toString().contains("good_quality_thumbnail"))
                .collect(Collectors.toList());
    }
}
