import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        final ItemService itemService = new ItemServiceMapImpl();

        get("/items/:item", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItems(":item"))));

        });

        get("/items/:item/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItem(request.params(":item"), request.params(":id")))));
        });

        get("/itemTitles", (request, response) -> {
            response.type("application/json");
            String itemToFilter = request.queryParams("item");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItemTitles(itemToFilter))));
        });

        get("/order/:item", (request, response) -> {
            response.type("application/json");
            String element = request.queryParams("element");
            String type = request.queryParams("type");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.orderItems(element, type, request.params(":item")))
            ));
        });

        put("/items/:id", (request, response) -> {
            response.type("application/json");
            Item item = new Gson().fromJson(request.body(), Item.class);
            Item itemE = itemService.editItem(item);
            if (itemE != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemE)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("error al editar")));
            }
        });

        delete("/items/:id", (request, response) -> {
            response.type("application/json");

            itemService.deleteItem(request.params(":id"));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    "item borrado"
            ));
        });

        options("/items/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    itemService.itemExist(request.params(":id")) ? "El item existe" : "El item no existe"
            ));
        });

    }
}
