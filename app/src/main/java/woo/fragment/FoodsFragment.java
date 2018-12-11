package woo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import kyph.woo.kyph.R;
import woo.kyph.MainActivity;
import woo.scroll.ScrollAdapter;

public class FoodsFragment extends Fragment {
    private static final String SEARCH = "where";

    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private ScrollAdapter scrollAdapter;
    private Context context;

    private String where =  null;

    static boolean setAdapterTaskRunning = false;
    private boolean lastitemVisibleFlag = false;
    static boolean next;

    public FoodsFragment() {
        // Required empty public constructor
    }

    public static FoodsFragment newInstance(String search) {
        FoodsFragment fragment = new FoodsFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH, search);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String search = getArguments().getString(SEARCH);
            where = "ingredient LIKE '%" + search + "%'";
        }
        scrollAdapter = new ScrollAdapter(context, MainActivity.FOODS, where);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        next = true;
        View view =  inflater.inflate(R.layout.fragment_foods, container, false);
        listView = view.findViewById(R.id.foods_listview);
        listView.setAdapter(scrollAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    if (next && !setAdapterTaskRunning) {
                        setAdapterTaskRunning = true;
                        new SetAdapter().execute(scrollAdapter);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });

        new SetAdapter().execute(scrollAdapter);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    static class SetAdapter extends AsyncTask<ScrollAdapter, Void, ScrollAdapter> {

        @Override
        protected ScrollAdapter doInBackground(ScrollAdapter... scrAdap) {
            next = scrAdap[0].update();
            return scrAdap[0];
        }

        @Override
        protected void onPostExecute(ScrollAdapter scrAdap) {
            scrAdap.notifyDataSetChanged();
            setAdapterTaskRunning = false;
        }
    }
}
