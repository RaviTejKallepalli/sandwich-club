package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView placeOfOrigin_tv;
    private TextView alsoKnowAs_tv;
    private TextView ingridents_tv;
    private TextView description_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeUI();
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
            .load(sandwich.getImage())
            .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void initializeUI() {
        ingridents_tv = findViewById(R.id.ingredients_tv);
        description_tv = findViewById(R.id.description_tv);
        alsoKnowAs_tv = findViewById(R.id.also_known_tv);
        placeOfOrigin_tv = findViewById(R.id.origin_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String description = sandwich.getDescription();
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        List<String> ingredients = sandwich.getIngredients();
        List<String> alsoKnowAs = sandwich.getAlsoKnownAs();

        description_tv
            .setText(isEmptyOrNull(description) ? getString(R.string.not_available) : description);
        placeOfOrigin_tv.setText(
            isEmptyOrNull(placeOfOrigin) ? getString(R.string.not_available) : placeOfOrigin);

        setListToTextView(ingridents_tv, ingredients);
        setListToTextView(alsoKnowAs_tv, alsoKnowAs);
    }

    private boolean isEmptyOrNull(String input) {
        return input == null || input.isEmpty();
    }

    private void setListToTextView(TextView textView, List<String> list) {
        if (list.isEmpty()) {
            textView.setText(getString(R.string.not_available));
        } else {
            for (String ingredient : list) {
                textView.append(ingredient + "\n");
            }
        }
    }
}
