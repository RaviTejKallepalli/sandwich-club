package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONObject name = jsonObject.getJSONObject("name");

            String mainName = name.getString("mainName");
            sandwich.setMainName(mainName);
            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            sandwich.setAlsoKnownAs(convertToList(alsoKnownAs));
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            String description = jsonObject.getString("description");
            sandwich.setDescription(description);
            String imageUrl = jsonObject.getString("image");
            sandwich.setImage(imageUrl);

            JSONArray ingredients = jsonObject.getJSONArray("ingredients");
            sandwich.setIngredients(convertToList(ingredients));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> convertToList(JSONArray input) throws JSONException {
        List<String> list = new ArrayList<>();

        if (input != null) {
            for (int i = 0; i < input.length(); i++) {
                list.add(input.getString(i));
            }
        }

        return list;
    }
}
