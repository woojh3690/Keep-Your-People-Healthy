package woo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import kyph.woo.kyph.R;
import woo.kyph.MainActivity;
import woo.scroll.ScrollAdapter;

public class IngredientFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private ScrollAdapter scrollAdapter;

    static boolean setAdapterTaskRunning = false;
    private boolean lastitemVisibleFlag = false;
    static boolean next;

    public IngredientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        next = true;
        View view = inflater.inflate(R.layout.ingredient_fragment, container, false);
        listView = view.findViewById(R.id.ingredient_listview);
        listView.setAdapter(scrollAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    Toast.makeText(getActivity(), "호출", Toast.LENGTH_SHORT).show();
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        scrollAdapter = new ScrollAdapter(context, MainActivity.INGREDIENT);
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
