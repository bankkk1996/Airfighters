package milk.example.airfighters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    ListView listView;
    private SharedPreferences prefs;
    String fly[] = {"Sliver Eagle","Blue Sky","Raptor Zero","Tiger","Manta","Thunder Roar","Fly Rider"};
    String name[] = {"1","2","3","4","5","6","7"};
    int img[] = {   R.drawable.original_00_01,
                    R.drawable.original_01_01,
                    R.drawable.original_02_01,
                    R.drawable.original_03_01,
                    R.drawable.original_04_01,
                    R.drawable.original_05_01,
                    R.drawable.original_06_01};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.listView);
        prefs = getSharedPreferences("game",Context.MODE_PRIVATE);

        MyAdapter adapter = new MyAdapter(this,fly,img,prefs);
        listView.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String string[];
        int img[];
        private SharedPreferences prefs;

        MyAdapter(Context c,String name[],int img[],SharedPreferences prefs){
            super(c,R.layout.row,R.id.nameFly,name);
            this.context = c;
            this.string = name;
            this.img = img;
            this.prefs = prefs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            ImageView img = row.findViewById(R.id.imgFly);
            TextView name = row.findViewById(R.id.nameFly);
            row.findViewById(R.id.selectFly).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("name",position+"");
                    editor.apply();

                    startActivity(new Intent(MainActivity2.this,MainActivity.class));
                }
            });
            img.setImageResource(this.img[position]);
            name.setText(string[position]);
            return row;
        }
    }
}