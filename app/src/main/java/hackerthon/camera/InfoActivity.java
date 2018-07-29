package hackerthon.camera;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by psm71 on 2018-07-29.
 */

public class InfoActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoview);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);

        final ViewItem listviewitem = DBHelper.instance.getSearch(position);


        TextView name = (TextView)findViewById(R.id.name);
        name.setText(listviewitem.getName());
        TextView start = (TextView) findViewById(R.id.start);
        TextView type = (TextView) findViewById(R.id.type);
        type.setText(listviewitem.getType());
        start.setText(listviewitem.getStart());
        TextView end = (TextView) findViewById(R.id.end);
        end.setText(listviewitem.getEnd());
        TextView pos = (TextView) findViewById(R.id.position);
        pos.setText(listviewitem.getPosition());
        ImageView delete = (ImageView) findViewById(R.id.remove);
        ImageView backimage = (ImageView) findViewById(R.id.backimage);
        backimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DBHelper.instance.delete(listviewitem.getId());
                ((MainActivity)MainActivity.CONTEXT).onResume();

                finish();
            }
        });
    }
}
