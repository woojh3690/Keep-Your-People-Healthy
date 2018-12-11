package woo.scroll;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import kyph.woo.kyph.R;
import woo.kyph.MainActivity;
import woo.kyph.RecipeActivity;

public class ListItem extends ConstraintLayout {
    private int type;
    private Context context;
    private ImageView leftImage;
    private TextView leftText;
    private ImageView rightImage;
    private TextView rightText;
    private String leftItem;
    private String rightItem;

    private static HashMap translation = new HashMap();

    public ListItem(Context context, int type) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.list_item, this, true);
        this.leftImage = findViewById(R.id.list_item_left_image);
        this.leftText = findViewById(R.id.list_item_left_text);
        this.rightImage = findViewById(R.id.list_item_right_image);
        this.rightText = findViewById(R.id.list_item_right_text);
        this.type = type;
        this.context = context;

        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(leftItem);
            }
        });

        rightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(rightItem);
            }
        });

        if (type == MainActivity.INGREDIENT) {
            translation.put("감자", "potato");
            translation.put("아보카도", "avocado");
            translation.put("바나나", "banana");
            translation.put("귀리", "oat");
            translation.put("시금치", "spinach");
            translation.put("비트", "beat");
            translation.put("연어", "salmon");
            translation.put("표고버섯", "shiitake_mushrooms");
            translation.put("양배추", "cabbage");
            translation.put("양파", "onion");
            translation.put("검은콩", "black_beans");
            translation.put("아스파라거스", "asparagus");
        }

    }

    public void setLeftImage(String leftImage) {
        leftItem = leftImage;
        this.leftImage.setImageResource(getImage(leftImage));
    }

    public void setLeftText(String text) {
        this.leftText.setText(text);
    }

    public void setRightImage(String rightImage) {
        rightItem = rightImage;
        this.rightImage.setImageResource(getImage(rightImage));
    }

    public void setRightText(String text) {
        this.rightText.setText(text);
    }

    private int getImage(String name) {
        Resources res = getResources();
        int id;
        if (type == MainActivity.FOODS) {
            id = res.getIdentifier("foods_" + name, "drawable", "woo.kyph");
        } else {
            String eng_name = (String)translation.get(name);
            id = res.getIdentifier(eng_name, "drawable", "woo.kyph");
        }
        return id;
    }

    private void changeView(String item) {
        if (type == MainActivity.FOODS) {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("item", item);
            context.startActivity(intent);
        } else {
            ((MainActivity)context).changeFragment(MainActivity.FOOD_FIND, item);
        }
    }
}
