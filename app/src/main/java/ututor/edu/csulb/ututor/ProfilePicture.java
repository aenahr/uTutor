package ututor.edu.csulb.ututor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ProfilePicture implements Serializable{

    private Context currentContext;
    private int color;

    public ProfilePicture(Context context){
        currentContext = context;

        color = 0;

    }


    public void setColor(int newColor){

        if( newColor < 0 || newColor > 4){
            color = 0;
        }
        else{
            color = newColor;
        }
    }

    public String getColor(){
        Bitmap bitmap;
        if( color == 0 ){ //blue
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.ututorlogo); // drawable to bitmap
        } else if( color == 1 ){ // red

            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_red); // drawable to bitmap

        } else if(color == 2) // green
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_green); // drawable to bitmap
        } else if(color == 3) // yellow
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_yellow); // drawable to bitmap
        } else if( color == 4)// purple
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_purple); // drawable to bitmap
        }
        else{
            return "";
        }
        return BitMapToString(bitmap);
    }

    public Bitmap getBitmapColor(){
        Bitmap bitmap;
        if( color == 0 ){ //blue
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.ututorlogo); // drawable to bitmap
        } else if( color == 1 ){ // red

            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_red); // drawable to bitmap

        } else if(color == 2) // green
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_green); // drawable to bitmap
        } else if(color == 3) // yellow
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_yellow); // drawable to bitmap
        } else if( color == 4)// purple
        {
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.tutorhead_purple); // drawable to bitmap
        }
        else{
            bitmap = BitmapFactory.decodeResource(currentContext.getResources(), R.drawable.ututorlogo); // drawable to bitmap - default blue
            return bitmap;
        }
        return bitmap;
    }

    public int getIntColor(){
        return color;
    }
    /**
     * Converting bitmap to string code
     * @param bitmap
     * @return
     */
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


}
