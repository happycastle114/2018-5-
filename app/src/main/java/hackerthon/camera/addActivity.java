package hackerthon.camera;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by psm71 on 2018-07-29.
 */

public class addActivity extends Activity {

    ImageView backimage;
    ImageView sendimage;
    ImageView stuffimage;

    EditText name;
    EditText position;

    Spinner spinner;

    DatePicker datePicker;

    DBHelper dbHelper;
    ArrayList<String> listview_items;

    String spinnerResult ="옷";
    String date ="0000-00-00";

    Uri mlmageCaptureUri = Uri.parse("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        //dbHelper = new DBHelper(getParent(),"List.db",null,1);

        dbHelper= DBHelper.instance;

        backimage = (ImageView) findViewById(R.id.backimage);
        backimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        sendimage = (ImageView) findViewById(R.id.sendimage);
        sendimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               dbHelper.insert(name.getText().toString(),spinnerResult, date,position.getText().toString(),mlmageCaptureUri);
                Toast.makeText(getApplicationContext(),"생성되었습니다.",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        stuffimage = (ImageView) findViewById(R.id.addimage);
        stuffimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                 String fileName;
                final String SAVE_FOLDER = "/save_folder";
                String savePath = Environment.getExternalStorageDirectory().toString() + SAVE_FOLDER;
                File dir = new File(savePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                fileName =  name.getText()+".jpg";

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mlmageCaptureUri = Uri.fromFile(new File(savePath, fileName));

                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mlmageCaptureUri);
                startActivityForResult(intent, 0);


                fileName = name + ".jpg";
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                String targetDir = Environment.getExternalStorageDirectory().toString() + SAVE_FOLDER;

                File file = new File(targetDir + "/" + fileName);

                //type 지정 (이미지)

                i.setDataAndType(Uri.fromFile(file), "image/");

                getApplicationContext().startActivity(i);

                //이미지 스캔해서 갤러리 업데이트

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            }
        });

        name = (EditText) findViewById(R.id.name);
        position = (EditText) findViewById(R.id.position);

        spinner = (Spinner) findViewById(R.id.spinner1);

        //데이터를 저장하게 되는 리스트
        listview_items = new ArrayList<String>();

        listview_items.add("옷");
        listview_items.add("음식");
        listview_items.add("기타");

        //리스트뷰와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter listview_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listview_items);

        //리스트뷰의 어댑터 지정
        spinner.setAdapter(listview_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //각 항목 클릭시 포지션값을 토스트에 띄운다.
                spinnerResult = listview_items.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        date = datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("code: ", String.valueOf(resultCode));
        stuffimage.setImageURI(data.getData());
    }
}
