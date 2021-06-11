package milk.example.airfighters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Pause {
    Bitmap pause;
    int x, y, width,height;
    Pause(Resources res){
        pause = BitmapFactory.decodeResource(res,R.drawable.ic_pause);
        width = 36;
        height = 36;
    }

    Bitmap getPause(){
        return pause;
    }
}
