package woo.kyph;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
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
    public static final int MAIN = 0;
    public static final int FOOD_FIND = 4;
    public static final int EXAMINE = 1;
    public static final int FOODS = 2;
    public static final int INGREDIENT = 3;

    private int curFm = 3;

    private EditText searchText;
    private LinearLayout slideLayout;
    private FragmentManager fm = getFragmentManager();

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
        searchText = findViewById(R.id.searchText);
        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    searchText.setHint("");
                } else {
                    searchText.setHint(getString(R.string.search));
                    new Rect();
                }
            }
        });
        slideLayout = findViewById(R.id.dragView);
        setSlideMenu();
        changeFragment(MAIN);
    }

    private void setSlideMenu() {
        ListView lv = findViewById(R.id.list);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position += 1;
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
        } else if (curFm != MAIN) {
            changeFragment(MAIN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            } else {
                Rect outRect = new Rect();
                slideLayout.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())
                        && (mLayout.getPanelState() == PanelState.EXPANDED || mLayout.getPanelState() == PanelState.ANCHORED)) {
                    mLayout.setPanelState(PanelState.COLLAPSED);
                    return false;
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void changeFragment(int index) {
        this.changeFragment(index, null);
    }

    public void changeFragment(int index, String search) {
        if (index >= FOODS && index <= FOOD_FIND) {
            searchText.setVisibility(View.VISIBLE);
        } else {
            searchText.setVisibility(View.GONE);
        }
        try {
            FragmentTransaction ft = fm.beginTransaction();
            if (index == FOOD_FIND) {
                ft.replace(R.id.main_frame, FoodsFragment.newInstance(search));
            } else {
                ft.replace(R.id.main_frame, fragmentSparseArray.get(index));
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
            curFm = index;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
