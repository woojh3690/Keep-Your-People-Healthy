package woo.scroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import kyph.woo.kyph.R;

public class ListItem extends ConstraintLayout {
    private ImageView leftImage;
    private TextView leftText;
    private ImageView rightImage;
    private TextView rightText;
    private Context context;

    public ListItem (Context context) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.list_item, this, true);
        this.leftImage = findViewById(R.id.list_item_left_image);
        this.leftText = findViewById(R.id.list_item_left_text);
        this.rightImage = findViewById(R.id.list_item_right_image);
        this.rightText = findViewById(R.id.list_item_right_text);
        this.context = context;
    }

    public void setLeftImage(String leftImage) {
        this.leftImage.setImageResource(getImage(leftImage));
    }

    public void setLeftText(String text) {
        this.leftText.setText(text);
    }

    public void setRightImage(String rightImage) {
        this.rightImage.setImageResource(getImage(rightImage));
    }

    public void setRightText(String text) {
        this.rightText.setText(text);
    }

    private int getImage(String name) {
        Resources res = getResources();
        int id = res.getIdentifier("foods_" + name, "drawable", "woo.kyph");
        return id;
    }
}
