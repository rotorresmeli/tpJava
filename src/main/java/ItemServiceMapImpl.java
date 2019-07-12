import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ItemServiceMapImpl implements ItemService {

    private SearchItemServiceImpl searchItemService;

    public ItemServiceMapImpl() {
        this.searchItemService = new SearchItemServiceImpl();
    }

    public void addItem(Item item) {
        if (item != null)
            searchItemService.addItem(item);
    }

    public Collection<Item> getItems(String criterio, String order) {
        List<Item> listItems = new ArrayList(searchItemService.getAllResult().values());

        if(criterio != null && !order.isEmpty())
        {
            if(criterio.equals("PRICE"))
            {
                Item.orderElement = Item.OrderElement.PRICE;
            }
            else if (criterio.equals("LISTING_TYPE")){
                Item.orderElement = Item.OrderElement.LISTING_TYPE;
            }
        }

        if(order != null && !order.isEmpty() && order.equals("DESC"))
        {
            OperacionServiceImpl.ordenarArrayDescendente(listItems);
        }
        else
        {
            OperacionServiceImpl.ordenarArrayAscendente(listItems);
        }
        return listItems;
    }

    @Override
    public Collection<Item> searchItems(String search) {
        List<Item> listItems = searchItemService.searchItems(search);

        return listItems;
    }

    public Item getItem(String id) {
        Item ret = null;
        if (!id.isEmpty()) {
            ret = searchItemService.getAllResult().get(id);
        }
        return ret;
    }

    public Item editItem(Item item) {
        return searchItemService.editItem(item);
    }

    public boolean deleteItem(String id) {
        return searchItemService.deleteItem(id);
    }

    public boolean itemExist(String id) {
        return searchItemService.getAllResult().containsKey(id);
    }

    public Collection<String> getTitleItems() {
        List<Item> listItems = new ArrayList(searchItemService.getAllResult().values());
        return listItems.stream()
                .map(x -> x.getTitle())
                .collect(Collectors.toList());
    }

    public Collection<Item> getItemsOrder(String order){
        List<Item> listItems = new ArrayList(searchItemService.getAllResult().values());
        if(order.equals("ASC"))
        {
            OperacionServiceImpl.ordenarArrayAscendente(listItems);
        }
        else
        {
            OperacionServiceImpl.ordenarArrayDescendente(listItems);
        }
        return listItems;
    }


}
