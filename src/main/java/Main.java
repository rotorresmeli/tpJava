import com.google.gson.Gson;
import entities.Item;
import entities.StandardResponse;
import entities.StatusResponse;
import services.ItemService;
import services.ItemServiceMapImpl;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        final ItemService itemService = new ItemServiceMapImpl();

        /**
         * Retorna una lista completa de items
         * La url se pasa completando en la url el tipo de articulo a listar ejemplo /items/casa
         *
         * @return lista completa de items
         */
        get("/items/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItems(":id"))));

        });

        /**
         * Retorna la lista de items que tienen un precio comprendido entre dos valores ingresados
         *
         * @param min Valor del precio minimo del item a filtrar
         * @param max Valor del precio máximo del item a filtrar
         *
         * @return lista completa de un item especifico filtrada por precios
         */
        get("/prices/:item", (request, response) -> {
            response.type("application/json");
            String minPrice = request.queryParams("min");
            String maxPrice = request.queryParams("max");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.searchItemsByPrices(minPrice, maxPrice, request.params(":item")))));
        });

        /**
         * Retorna los titulos de los items obtenidos
         *
         * @return Titulos descriptivos de un item específico
         */
        get("/itemTitles", (request, response) -> {
            response.type("application/json");
            String itemToFilter = request.queryParams("item");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItemTitles(itemToFilter))));
        });

        /**
         * Ordena los items teniendo en cuenta un parametro de ordenamiento y un tipo de ordenamiento
         *
         * @param element Elemento por el cual filtrar (Parametros validos = (PRICE,LISTING_TIPE))
         * @param type Tipo de ordenamiento (Por defecto Ascendente, Parametros validos = (DESC))
         *
         * @return Lista ordenada de un item especifico
         */

        get("/order/:item", (request, response) -> {
            response.type("application/json");
            String element = request.queryParams("element");
            String type = request.queryParams("type");

            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.orderItems(element, type, request.params(":item")))
            ));
        });

        /**
         * Devuelve items que contengan es el elemento tag (good_quality_thumbnail)
         *
         * @return Todos los elementos de un item especifico que contengan en array tag el tag good_quality_thumbnail
         */

        get("/tagged/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getTaggedItems(request.params(":id")))));
        });

        /**
         * Devuelve un item especifico en funcion del id (Ejemplo IML2134252) completado en la url
         *
         * @return El item especifico si existe. Si no existe retorna una lista vacía
         */

        get("/items/:item/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    new Gson().toJsonTree(itemService.getItem(request.params(":item"), request.params(":id")))));
        });

        /**
         * Actualiza un item especifico pasado por el body. Los elementos que sean distintos del item seran sobreescritos.
         *
         * @param id El id del elemento que se quiere actualizar
         *
         * @return Update de la lista del item especifico. Edit Error en caso de fallar
         */

        put("/items/:id", (request, response) -> {
            response.type("application/json");
            Item item = new Gson().fromJson(request.body(), Item.class);
            Item itemE = itemService.editItem(item,request.params(":id"));
            if (itemE != null) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemE)));
            } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR, new Gson().toJson("Edit error")));
            }
        });

        /**
         * Borra un item especifico de la lista pasando el id del mismo
         *
         * @param id El id del elemento que se quiere borrar
         *
         * @return true -> item borrado. En caso de no existir el elemento tiene la misma respuesta para indicar que no existe
         */

        delete("/items/:id", (request, response) -> {
            response.type("application/json");

            itemService.deleteItem(request.params(":id"));
            return new Gson().toJson(new StandardResponse(
                    StatusResponse.SUCCESS,
                    "item borrado"
            ));
        });
    }
}
