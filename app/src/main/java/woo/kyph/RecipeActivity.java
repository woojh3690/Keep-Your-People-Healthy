package woo.kyph;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kyph.woo.kyph.R;

public class RecipeActivity extends AppCompatActivity {

    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String id = intent.getStringExtra(ID);

        ImageView imageView = findViewById(R.id.recipe_image);
        int resourse = getResources().getIdentifier("foods_" + id, "drawable", "woo.kyph");
        imageView.setImageResource(resourse);

        String where = "num='" + id + "'";
        SqlHelper sqlHelper = new SqlHelper(this);
        ArrayList<String[]> data =  sqlHelper.getData(false, SqlHelper.DATA_TABLE, new String[] {"food_efficacy", "recipe"}, where, null);

        String[] text = data.get(0);
        TextView efficacy = findViewById(R.id.recipe_text_efficacy);
        efficacy.setText(text[0]);
        TextView recipe = findViewById(R.id.recipe_text_recipe);
        recipe.setText(text[1]);
    }
}
