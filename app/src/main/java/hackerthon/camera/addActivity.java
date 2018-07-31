package hackerthon.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
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

import java.io.ByteArrayOutputStream;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
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

               dbHelper.insert(name.getText().toString(),spinnerResult, date,position.getText().toString(),getByteArrayFromDrawble(stuffimage.getDrawable()));
                Toast.makeText(getApplicationContext(),"생성되었습니다.",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        stuffimage = (ImageView) findViewById(R.id.addimage);
        stuffimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
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

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            stuffimage.setImageBitmap(imageBitmap);

        }
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public byte[] getByteArrayFromDrawble(Drawable d)
    {
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] data = stream.toByteArray();

        return data;
    }
}
