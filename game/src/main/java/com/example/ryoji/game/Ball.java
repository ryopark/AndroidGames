package com.example.ryoji.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

/**
 * Created by Ryoji on 11/29/15.
 */
public class Ball implements DrawableItem {
    private float mX;
    private float mY;
    private float mSpeedX;
    private float mSpeedY;
    private final float mRadius;
    private final float mInitialX;
    private final float mInitialY;
    private final float mInitialSpeedX;
    private final float mInitialSpeedY;
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_SPEED_X = "speed_x";
    private static final String KEY_SPEED_Y = "speed_y";




    public Ball(float radius, float initialX, float initialY){
        mRadius = radius;
        mSpeedX = radius / 5;
        mSpeedY = -radius / 5;
        mX = initialX;
        mY = initialY;

        mInitialSpeedX = mSpeedX;
        mInitialSpeedY = mSpeedY;
        mInitialX = mX;
        mInitialY = mY;

    }

    public void move(){
        mX += mSpeedX;
        mY += mSpeedY;
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mX,mY,mRadius,paint);
    }


    public float getSpeedX(){
        return mSpeedX;
    }

    public float getSpeedY(){
        return mSpeedY;
    }

    public float getX(){
        return mX;
    }

    public  float getY(){
        return mY;
    }

    public void setSpeedX(float speedX){
        mSpeedX = speedX;
    }

    public void setSpeedY(float speedY){
        mSpeedY = speedY;
    }


    public void reset(){
        mX = mInitialX;
        mY = mInitialY;
        mSpeedY = mInitialSpeedY;
        mSpeedX = mInitialSpeedX * ((float) Math.random() - 0.5f);
    }

    public Bundle save(int width, int height){
        Bundle outState = new Bundle();
        outState.putFloat(KEY_X, mX / width);
        outState.putFloat(KEY_Y, mY / height);
        outState.putFloat(KEY_SPEED_X, mSpeedX / width);
        outState.putFloat(KEY_SPEED_Y, mSpeedY / height);
        return outState;

    }

    public void restore(Bundle inState, int width, int height){
        mX = inState.getFloat(KEY_X) * width;
        mY = inState.getFloat(KEY_Y) * height;
        mSpeedX = inState.getFloat(KEY_SPEED_X) * width;
        mSpeedY = inState.getFloat(KEY_SPEED_Y) * height;

    }
}
