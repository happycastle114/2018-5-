package hackerthon.camera;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;

/**
 * Created by psm71 on 2018-07-28.
 */

public class ListViewAdpter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<ViewItem> data;

    private int layout;

    public ListViewAdpter(Context context, int layout, ArrayList<ViewItem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        ViewItem listviewitem= data.get(position);

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageBitmap(listviewitem.getPicture());
        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(listviewitem.getName());
        TextView start = (TextView) convertView.findViewById(R.id.start);
        start.setText(listviewitem.getStart());
        TextView end = (TextView) convertView.findViewById(R.id.end);
        end.setText(listviewitem.getEnd());
        TextView pos = (TextView) convertView.findViewById(R.id.position);
        pos.setText(listviewitem.getPosition());

        return convertView;
    }
}
