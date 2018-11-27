package woo.kyph;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState;

import java.util.Arrays;
import java.util.List;

import kyph.woo.kyph.R;
import woo.fragment.ExamineFragment;
import woo.fragment.FoodsFragment;
import woo.fragment.MainFragment;
import woo.fragment.IngredientFragment;

public class MainActivity extends AppCompatActivity
        implements ExamineFragment.OnFragmentInteractionListener,
        IngredientFragment.OnFragmentInteractionListener,
        FoodsFragment.OnFragmentInteractionListener,
        MainFragment.OnFragmentInteractionListener {

    private static final String TAG = "mainActivity";
    public static final int MAIN = 3;
    public static final int EXAMINE = 0;
    public static final int FOODS = 1;
    public static final int INGREDIENT = 2;

    private final SparseArray<Fragment> fragmentSparseArray;
    {
        fragmentSparseArray = new SparseArray<>();
        fragmentSparseArray.put(MAIN, new MainFragment());
        fragmentSparseArray.put(EXAMINE, new ExamineFragment());
        fragmentSparseArray.put(FOODS, new FoodsFragment());
        fragmentSparseArray.put(INGREDIENT, new IngredientFragment());
    }

    private SlidingUpPanelLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSlideMenu();
        changeFragment(MAIN);
    }

    private void setSlideMenu() {
        ListView lv = findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == EXAMINE) {
                    changeFragment(position);
                } else if (position == FOODS){
                    changeFragment(position);
                } else if (position == INGREDIENT) {
                    changeFragment(position);
                }
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

        List<String> your_array_list = Arrays.asList(
                getString(R.string.examine),
                getString(R.string.foods),
                getString(R.string.Ingredient)
        );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        lv.setAdapter(arrayAdapter);

        mLayout = findViewById(R.id.sliding_layout);
        mLayout.setFadeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(PanelState.COLLAPSED);
            }
        });

        TextView t = findViewById(R.id.title);
        t.setText(getString(R.string.main));
    }

    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
            mLayout.setPanelState(PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void changeFragment(int index) {
        try {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.main_frame, fragmentSparseArray.get(index));

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
