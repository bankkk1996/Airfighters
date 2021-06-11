package milk.example.airfighters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private boolean isMute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        findViewById(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });

        findViewById(R.id.chooseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });

        TextView highScoreTxt = findViewById(R.id.highScoreTxt);
        SharedPreferences prefs = getSharedPreferences("game",MODE_PRIVATE);

        highScoreTxt.setText("HighScore: "+prefs.getInt("highscore",0));
        isMute = prefs.getBoolean("isMute",false);

        ImageView volume = findViewById(R.id.volumeBtn);

        if(isMute){
            volume.setImageResource(R.drawable.unsound);
        }else{
            volume.setImageResource(R.drawable.sound);
        }

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if(isMute){
                    volume.setImageResource(R.drawable.unsound);
                }else{
                    volume.setImageResource(R.drawable.sound);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute",isMute);
                editor.apply();
            }
        });
        String name = prefs.getString("name","fly");
        ImageView image = findViewById(R.id.imageAir);
        if (name.equalsIgnoreCase("1")){
            image.setImageResource(R.drawable.original_01_01);
        }else if (name.equalsIgnoreCase("2")) {
            image.setImageResource(R.drawable.original_02_01);
        }else if (name.equalsIgnoreCase("3")){
            image.setImageResource(R.drawable.original_03_01);
        }else if (name.equalsIgnoreCase("4")){
            image.setImageResource(R.drawable.original_04_01);
        }else if (name.equalsIgnoreCase("5")){
            image.setImageResource(R.drawable.original_05_01);
        }else if (name.equalsIgnoreCase("6")){
            image.setImageResource(R.drawable.original_06_01);
        }else{
            image.setImageResource(R.drawable.original_00_01);
        }

        findViewById(R.id.howToPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity3.class));
            }
        });
    }

}