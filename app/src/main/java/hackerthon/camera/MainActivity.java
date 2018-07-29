package hackerthon.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Context CONTEXT;
    ImageView addview;


    ListView listView;
    ListViewAdpter adpter;
    ArrayList<ViewItem> item;

    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CONTEXT = this;

        addview = (ImageView)findViewById(R.id.plusbutton);
        addview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this,addActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView) findViewById(R.id.ListView);

        item = new ArrayList<ViewItem>();

        dbHelper = DBHelper.getinstance(this);
        item = dbHelper.getResult();


        Log.d("size: ", String.valueOf(item.size()));
        adpter = new ListViewAdpter(this,R.layout.itemview,item);
        listView.setAdapter(adpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),InfoActivity.class);
                int amolang= item.get(position).getId();
                intent.putExtra("position",amolang);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        item = dbHelper.getResult();
        adpter = new ListViewAdpter(this,R.layout.itemview,item);
        listView.setAdapter(adpter);
    }

}
