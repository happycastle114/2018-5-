package hackerthon.camera;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Created by psm71 on 2018-07-28.
 */

public class ViewItem
{
    int id;
    String name;
    String type;
    String start;
    String end;
    String position;
    Bitmap picture;

    ViewItem(int id, String name, String type, String start,String end,String position , Bitmap picture)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.start = start;
        this.end = end;
        this.position = position;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }
    public String getType()
    {
        return type;
    }
    public String getStart() {
        return start;
    }

    public Bitmap getPicture()
    {
        return picture;
    }

    public String getEnd() {
        return end;
    }

    public String getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }
}
