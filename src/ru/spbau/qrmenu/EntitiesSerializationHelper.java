package ru.spbau.qrmenu;

import org.json.JSONException;
import org.json.JSONObject;
import ru.spbau.qrmenu.entities.RestaurantMenuItem;
import ru.spbau.qrmenu.entities.RestaurantTable;


public final class EntitiesSerializationHelper {

    private EntitiesSerializationHelper() {
    }

    public static RestaurantMenuItem parseMenuItem(String json) {
        try {
            JSONObject jObj = new JSONObject(json);

            if (2 != jObj.length()) {
                throw new JSONException("Unexpected object.");
            }

            String itemName = jObj.getString("name");
            double itemCost = jObj.getDouble("cost");

            return new RestaurantMenuItem(itemName, itemCost);
        } catch (JSONException je) {
            return null;
        }
    }

    public static String serializeMenuItem(RestaurantMenuItem rmi) {
        return rmi.toString();
    }

    public static RestaurantTable parseTable(String json) {
        try {
            JSONObject jObj = new JSONObject(json);

            if (3 != jObj.length()) {
                throw new JSONException("Unexpected object.");
            }

            String restaurantName = jObj.getString("name");
            String restaurantUrl = jObj.getString("url");
            int tableId = jObj.getInt("tableId");

            return new RestaurantTable(restaurantName, restaurantUrl, tableId);
        } catch (JSONException je) {
            return null;
        }
    }

    public static String serializeTable(RestaurantTable table) {
        return table.toString();
    }

}
