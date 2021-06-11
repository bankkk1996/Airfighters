package milk.example.airfighters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView  extends SurfaceView implements Runnable{

    private Thread thread;
    private boolean isPlaying,isGameOver = false;
    public static int screenX,screenY,score = 0;
    public static float screenRatioX,screenRatioY;
    private Paint paint;
    private Rocket[] rockets;
    private SharedPreferences prefs;
    private Random random;
    private SoundPool soundPool;
    private List<Bullet> bullets;
    private int sound,soundBoom;
    private Flight flight;
    private Background background1,background2;
    private GameActivity activity;
    private Pause pause;

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;


    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;
        prefs = activity.getSharedPreferences("game",Context.MODE_PRIVATE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }else{
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        sound = soundPool.load(activity,R.raw.shoot,1);
        soundBoom = soundPool.load(activity,R.raw.boom,1);
        accelerometer = new Accelerometer(activity);
        gyroscope = new Gyroscope(activity);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f /screenX;
        screenRatioY = 1080f /screenY;
        pause = new Pause(getResources());
        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());

        flight = new Flight(this,screenX,getResources(),prefs.getString("name","fly"));

        bullets = new ArrayList<>();

        background2.y = screenY;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.WHITE);
        random = new Random();
        rockets = new Rocket[4];
        for (int i = 0;i<4;i++){
            Rocket bird = new Rocket(getResources());
            bird.x = random.nextInt(screenX-bird.width);
            rockets[i] = bird;
        }

        accelerometer.setListener(new Accelerometer.Listener() {
            @Override
            public void onTranslation(float tx, float ty, float tz) {
                if(tx>1.0f){
                    flight.isGoingR = true;
                    flight.isGoingL = false;
                }else if(tx<-1.0f){
                    flight.isGoingR = false;
                    flight.isGoingL = true;
                }
            }
        });

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if(rz>1.0f){
                    flight.isGoingR = true;
                    flight.isGoingL = false;
                }else if(rz<-1.0f){
                    flight.isGoingR = false;
                    flight.isGoingL = true;
                }
            }
        });
    }

    @Override
    public void run() {
        score = 0;
        while(isPlaying){

            update();
            draw();
            sleep();

        }
    }

    private void update(){
        background1.y += 15 * screenRatioY;
        background2.y += 15 * screenRatioY;


        if(background1.y>background1.background.getHeight()){
            background1.y = -screenY;
        }

        if(background2.y>background2.background.getHeight()){
            background2.y = -screenY;
        }

        if(flight.isGoingR){
            flight.x -= 10 * screenRatioX;
        }else if (flight.isGoingL){
            flight.x += 10 * screenRatioX;
        }


        if(flight.x<0)
            flight.x=0;

        if(flight.x>=screenX-flight.width)
            flight.x = screenX-flight.width;

        List<Bullet> trash = new ArrayList<>();

        for (Bullet bullet : bullets){
            if(bullet.y<0){
                trash.add(bullet);
            }

            bullet.y -=50*screenRatioY;

            for (Rocket rocket : rockets){
                if(Rect.intersects(rocket.getCollisionShape(),bullet.getCollisionShape())){
                    score++;
                    rocket.y = -500;
                    rocket.x = random.nextInt(screenX-rocket.width);
                    bullet.y=-1000;
                    rocket.wasShot = true;
                }
            }
        }

        for(Bullet bullet : trash){
            bullets.remove(bullet);
        }

        for (Rocket rocket : rockets){

            if(!rocket.wasShot){
                isGameOver = true;
                return;
            }
            rocket.y += rocket.speed;
            if(rocket.y>screenY-flight.height){
                int bound =(int) (30 * screenRatioY);
                rocket.speed = random.nextInt(bound);
                if(rocket.speed<(int)(10*screenRatioY)){
                    rocket.speed = (int) (10 * screenRatioY);
                }

                rocket.x = random.nextInt(screenX-rocket.width);
                rocket.y = 0;
                rocket.wasShot = false;
            }


            if(rocket.y>screenY-flight.height/2){
                if(!prefs.getBoolean("isMute",false))
                    soundPool.play(soundBoom,1,1,0,0,1);
                isGameOver = true;
                return;
            }
        }
    }

    private void draw(){
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);
            for (Rocket bird: rockets){
                canvas.drawBitmap(bird.getBird(),bird.x,bird.y,paint);
            }

            canvas.drawText(score+"",screenX/2f,164,paint);

            if(isGameOver){

                isPlaying = false;
                canvas.drawBitmap(flight.getDead(),flight.x,flight.y,paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHighScore();
                waitBeforeExiting();
                return;
            }




            canvas.drawBitmap(flight.getFlight(),flight.x,flight.y,paint);

            for (Bullet bullet:bullets){
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void waitBeforeExiting()  {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if(prefs.getInt("highscore",0)<score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore",score);
            editor.apply();
        }
    }

    private void sleep(){
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
        accelerometer.register();
        gyroscope.register();
    }

    public void pause(){
        try {
            isPlaying = false;
            thread.join();
            accelerometer.unregister();
            gyroscope.unregister();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:break;
            case MotionEvent.ACTION_UP:
                flight.toshoot++;
                break;
        }
        return true;
    }

    public void newBullet() {
        if(!prefs.getBoolean("isMute",false))
            soundPool.play(sound,1,1,0,0,1);
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x+(flight.width/2) - bullet.width/2;
        bullet.y = flight.y-bullet.height/2;
        bullets.add(bullet);
    }

}
