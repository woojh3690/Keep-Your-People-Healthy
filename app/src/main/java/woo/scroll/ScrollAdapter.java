package woo.scroll;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import woo.kyph.MainActivity;
import woo.kyph.SqlHelper;

public class ScrollAdapter extends BaseAdapter {
    private int count = 0;
    private static final int interval = 10;
    private int type;
    private Context context;
    private SqlHelper sqlHelper;

    ArrayList<ListItem> items = new ArrayList<>();

    public ScrollAdapter(Context context, int type) {
        this.context  = context;
        this.type = type;
        this.sqlHelper = new SqlHelper(context);
    }

    public boolean update() {
        ArrayList<String[]> data = null;
        String limit = count + ", " + (count + interval);

        if (type == MainActivity.FOODS) {
            String[] columns = {"num", "food"};
            data = sqlHelper.getData(columns, null, limit);
        } else if (type == MainActivity.INGREDIENT) {
            String[] columns = {"ingredient", "ingredient"};
            data = sqlHelper.getData(true, columns, null, limit);
        }
        count += interval;

        assert data != null;
        for (int i = 0; i < data.size(); i += 2) {
            ListItem listItem = new ListItem(context);
            String[] left = data.get(i);
            listItem.setLeftImage(left[0]);
            listItem.setLeftText(left[1]);
            if (i+1 < data.size()) {
                String[] right = data.get(i + 1);
                listItem.setRightImage(right[0]);
                listItem.setRightText(right[1]);
            }
            items.add(listItem);
        }

        return !(data.size() < interval);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return items.get(position);
    }
}
