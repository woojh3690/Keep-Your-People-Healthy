package woo.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kyph.woo.kyph.R;
import woo.kyph.MainActivity;
import woo.scroll.ScrollAdapter;

public class IngredientFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private ScrollAdapter scrollAdapter;
    private SetAdapter setAdapter;

    public IngredientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_fragment, container, false);
        listView = view.findViewById(R.id.ingredient_listview);
        listView.setAdapter(scrollAdapter);
        setAdapter.execute();
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
        setAdapter  = new SetAdapter();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class SetAdapter extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String limit = scrollAdapter.update();
            return limit;
        }

        @Override
        protected void onPostExecute(String s) {
            scrollAdapter.notifyDataSetChanged();
        }
    }
}
