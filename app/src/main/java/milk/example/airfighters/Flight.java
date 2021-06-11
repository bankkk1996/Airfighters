package milk.example.airfighters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static milk.example.airfighters.GameView.screenY;


public class Flight {
    public boolean isGoingR =false,isGoingL = false;
    int toshoot = 0;
    int x,y, width,height ,wingCounter = 0,shootCounter=1;
    Bitmap flight1,flight2,shoot1,shoot2,shoot3,shoot4,shoot5,dead;

    private GameView gameView;
    Flight(GameView gameView,int screenX, Resources res,String name){

        this.gameView = gameView;
        if (name.equalsIgnoreCase("1")){
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_01_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_01_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_01_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_01_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_01_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_01_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_01_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_01);
        }else if (name.equalsIgnoreCase("2")) {
            flight1 = BitmapFactory.decodeResource(res, R.drawable.original_02_01);
            flight2 = BitmapFactory.decodeResource(res, R.drawable.original_02_02);
            shoot1 = BitmapFactory.decodeResource(res, R.drawable.shoot_02_01);
            shoot2 = BitmapFactory.decodeResource(res, R.drawable.shoot_02_02);
            shoot3 = BitmapFactory.decodeResource(res, R.drawable.shoot_02_03);
            shoot4 = BitmapFactory.decodeResource(res, R.drawable.shoot_02_04);
            shoot5 = BitmapFactory.decodeResource(res, R.drawable.shoot_02_05);
            dead = BitmapFactory.decodeResource(res, R.drawable.death_02);
        }else if (name.equalsIgnoreCase("3")){
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_03_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_03_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_03_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_03_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_03_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_03_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_03_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_03);
        }else if (name.equalsIgnoreCase("4")){
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_04_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_04_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_04_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_04_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_04_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_04_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_04_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_04);
        }else if (name.equalsIgnoreCase("5")){
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_05_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_05_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_05_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_05_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_05_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_05_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_05_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_05);
        }else if (name.equalsIgnoreCase("6")){
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_06_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_06_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_06_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_06_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_06_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_06_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_06_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_06);
        }else{
            flight1 = BitmapFactory.decodeResource(res,R.drawable.original_00_01);
            flight2 = BitmapFactory.decodeResource(res,R.drawable.original_00_02);
            shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot_00_01);
            shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot_00_02);
            shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot_00_03);
            shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot_00_04);
            shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot_00_05);
            dead = BitmapFactory.decodeResource(res,R.drawable.death_00);
        }


        width = flight1.getWidth();
        height = flight1.getHeight();

        width /=3;
        height /= 3;
//
//        width *= (int) screenRatioX;
//        height *= (int) screenRatioY;

        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false);

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5 = Bitmap.createScaledBitmap(shoot5,width,height,false);

        dead = Bitmap.createScaledBitmap(dead,width,height,false);

        x = (screenX/2)-(width/2);
        y =(int) screenY-(height);
    }

    Bitmap getFlight(){

        if(toshoot!=0){
            if(shootCounter==1){
                shootCounter++;
                return shoot1;
            }

            if(shootCounter==2){
                shootCounter++;
                return shoot2;
            }

            if(shootCounter==3){
                shootCounter++;
                return shoot3;
            }

            if(shootCounter==4){
                shootCounter++;
                return shoot4;
            }

            shootCounter = 1;
            toshoot--;

            gameView.newBullet();
            return shoot5;
        }
        if(wingCounter ==0){
            wingCounter++;
            return flight1;
        }

        wingCounter--;

        return flight2;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }

    Bitmap getDead(){
        return dead;
    }


}
