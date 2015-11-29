package com.example.ryoji.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ryoji on 11/29/15.
 */
public class Block implements DrawableItem {
    private final float mTop;
    private final float mLeft;
    private final float mRight;
    private final float mBottom;
    public final int DEFAULT_HARD = 1;
    private int mHard;
    private boolean mIsCollision = false;
    private boolean mIsExist = true;


    public Block(float top, float left, float right, float bottom){
        mTop = top;
        mLeft = left;
        mRight = right;
        mBottom = bottom;
        mHard = DEFAULT_HARD;
    }


    public void draw(Canvas canvas, Paint paint ){
        if(mIsExist){

            if(mIsCollision){
                mHard--;
                mIsCollision = false;
                if(mHard <= 0){
                    mIsExist = false;
                    return;
                }
            }
            //中身線画
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

            //枠線部分を線画
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4f);
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
        }
    }

    public void collision(){
        mIsCollision = true;
    }

    public boolean isExist(){
        return mIsExist;
    }


}
