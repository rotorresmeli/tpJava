import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchItemServiceImpl implements SearchItemService {

    private HashMap<String, List<Item>> cacheItems;
    private HashMap<String, Item> resultadoItems;

    public SearchItemServiceImpl() {
        this.cacheItems = new HashMap<String, List<Item>>();
        this.resultadoItems = new HashMap<>();
    }

    public void addItem(Item item){
        resultadoItems.put(item.getId(), item);
    }

    public boolean deleteItem(String id) {
        return resultadoItems.remove(id) != null;
    }

    public Item editItem(Item item){
        return resultadoItems.replace(item.getId(),item);
    }

    public Map<String,Item> getAllResult() {
        return resultadoItems;
    }

    public void cleanItemsCache() {
        this.cacheItems.clear();
    }

    public List<Item> searchItems(String search) {
        List<Item> listItems = new ArrayList<>();

        if (cacheItems.containsKey(search)) {
            listItems = cacheItems.get(search);
            return listItems;
        }

        if (search.isEmpty()) {
            return listItems;
        }

        URL url = null;

        try {
            url = new URL("https://api.mercadolibre.com/sites/MLA/search?q=" + search);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("URL inválida");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String urlString = "";
            String current = null;
            while ((current = in.readLine()) != null) {
                urlString += current;
            }

            Item[] arrItems = parsearJson(urlString);
            listItems = Arrays.asList(arrItems);

            for (Item item: listItems) {
                resultadoItems.put(item.getId(), item);
            }
            cacheItems.put(search, listItems);


        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return listItems;
    }

    public static Item[] parsearJson(String jsonLine) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        JsonElement jsonElement = new JsonParser().parse(jsonLine);
        JsonObject objetoPrincipal = jsonElement.getAsJsonObject();

        JsonArray jsonResults = objetoPrincipal.get("results").getAsJsonArray();

        Item aux = null;
        JsonObject auxObject;

        Item[] arrItems = new Item[jsonResults.size()];
        int i = 0;
        for (JsonElement elemento : jsonResults) {

            auxObject = elemento.getAsJsonObject();
            aux.setId(auxObject.get("id").getAsString());
            aux.setSiteId(auxObject.get("site_id").getAsString());
            aux.setTitle(auxObject.get("title").getAsString());


            aux.setThumbnail(auxObject.get("thumbnail").getAsString());
            JsonArray tagsJsonArray = auxObject.get("tags").getAsJsonArray();
            String[] tagsArray = new String[tagsJsonArray.size()];
            for (int j = 0; j < tagsJsonArray.size(); j++) {
                tagsArray[j] = tagsJsonArray.get(j).getAsString();
            }
            aux.setTags(tagsArray);

            arrItems[i] = aux;
            i++;

        }
        return arrItems;
    }
}
