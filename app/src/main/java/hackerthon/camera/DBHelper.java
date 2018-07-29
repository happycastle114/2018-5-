package hackerthon.camera;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by psm71 on 2018-07-28.
 */
@SuppressWarnings("serial")
public class DBHelper extends SQLiteOpenHelper {

    // 생성자=
    // 조회 method
    static DBHelper instance;

    public static DBHelper getinstance(Context context)
    {
        if (instance == null) {
            instance = new DBHelper(context, "List.db",null,1);
        }
        return instance;
    }
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE FOOD_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT,  kcal INTEGER ,protin INTEGER, fat INTEGER, cal INTEGER, na INTEGER, ca INTEGER, fe INTEGER);");
        db.execSQL("CREATE TABLE STUFF_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, start TEXT, end TEXT, position TEXT, image TEXT, gotoid INTEGER);");
    }
    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertFood( int kcal, int protin, int fat, int cal, int na, int ca, int fe) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO FOOD_INFO VALUES(null, " + kcal + "," + protin + ", " + fat + ", " + cal + ", " + na + "," + ca + ", " + fe + ");");
        db.close();
    }
    public void insert(String name, String type, String date, String position, Uri image)
    {
        SQLiteDatabase db = getWritableDatabase();
        long now = System.currentTimeMillis();
        Date a = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String getTime = sdf.format(a);

        //db.execSQL("CREATE TABLE STUFF_INFO (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, type TEXT, start DATE, end DATE, position TEXT, image TEXT");
        db.execSQL("INSERT INTO STUFF_INFO VALUES(null, '"+name+"', '"+type+"','"+getTime+"','"+date+"','"+position+"','"+image+"',"+0+");");
        db.close();
    }

    public void update(int id,int kcal, int protin, int fat, int cal, int na, int ca, int fe) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE FOOD_INFO SET kcal=" + kcal + ", protin =" + protin + ", fat=" + fat + ", cal=" + cal + ", na=" + na + " , ca=" + ca + " , fe=" + fe +  "WHERE _id=" + id + ";");
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM STUFF_INFO WHERE _id='" + id + "';");
        db.close();
    }
    public void drop()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE STUFF_INFO;");
    }
    public boolean exist()
    {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name ='STUFF_INFO'" , null);
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<ViewItem> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<ViewItem> result = new ArrayList<ViewItem>();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM STUFF_INFO;", null);
        while (cursor.moveToNext()) {
            result.add(new ViewItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), Uri.parse(cursor.getString(6))));
        }
        db.close();
        return result;
    }
    public ViewItem getSearch(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM STUFF_INFO WHERE _id="+id+";", null);

        ViewItem result = new ViewItem(0,"","","","","",Uri.parse(""));
        Log.d("cusor: ","Search");
        while (cursor.moveToNext()) {
            Log.d("cusor: ","Search");
            result = new ViewItem(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), Uri.parse(cursor.getString(6)));
        }
        db.close();
        return result;
    }

}