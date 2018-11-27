package woo.kyph;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.Toast;

import kyph.woo.kyph.R;

public class InitActivity extends AppCompatActivity {
    private static final String TAG = "InitActivity";
    private final Context context = this;

    ViewPager viewPager;
    int images[] = {R.drawable.sample1, R.drawable.sample2, R.drawable.sample3};
    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SettingPreferences set = new SettingPreferences(context);
        if (set.getBoolean("firstStart")) {
            startMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        viewPager = findViewById(R.id.viewPager);
        customPagerAdapter = new CustomPagerAdapter(this, images);
        viewPager.setAdapter(customPagerAdapter);
    }

    private void startMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private class CustomPagerAdapter extends PagerAdapter {
        Context context;
        int images[];
        LayoutInflater layoutInflater;


        CustomPagerAdapter(Context context, int images[]) {
            this.context = context;
            this.images = images;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView;

            if (position != images.length - 1) {
                itemView = layoutInflater.inflate(R.layout.item, container, false);
            } else {
                itemView = layoutInflater.inflate(R.layout.item_start, container, false);

                Button button = itemView.findViewById(R.id.button_start);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SettingPreferences set = new SettingPreferences(context);
                        set.saveBoolean("firstStart", true);
                        startMainActivity();
                    }
                });
            }

            ImageView imageView = itemView.findViewById(R.id.imageView);
            imageView.setImageResource(images[position]);


            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
