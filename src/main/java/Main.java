import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        final ItemService itemService = new ItemServiceMapImpl();

        post("/items", (request, response) -> {
            response.type("application/json");
            Item item = new Gson().fromJson(request.body(), Item.class);
            itemService.addItem(item);
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));

        });

        get("/items/search", (request, response) -> {
            String search = request.queryParams("search");
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.searchItems(search)
                    )));
        });

        get("/items/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItem(request.params(":id"))
                    )));
        });

        get("/itemstitle", (request, response) -> {
            response.type("application/json");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getTitleItems())
            ));
        });

        get("/items", (request, response) -> {
            response.type("application/json");
            String order = request.queryParams("order");
            String criterio = request.queryParams("criterio");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItems(criterio, order))
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
