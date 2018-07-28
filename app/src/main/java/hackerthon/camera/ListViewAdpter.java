package hackerthon.camera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import static android.R.attr.data;

/**
 * Created by psm71 on 2018-07-28.
 */

public class ListViewAdpter extends BaseAdapter {
    private LayoutInflater inflater;
    private int icon;
    private String name;

    public ListViewAdpter(Context context, int layout){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        return convertView;
    }
}
