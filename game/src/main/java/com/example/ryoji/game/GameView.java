package com.example.ryoji.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import static android.view.TextureView.SurfaceTextureListener;

/**
 * Created by Ryoji on 11/28/15.
 */
public class GameView extends TextureView implements SurfaceTextureListener, View.OnTouchListener {

    volatile private boolean mIsRunnable;
    volatile private float mTouchedX;
    volatile private float mTouchedY;

    public void start(){
        mIsRunnable = true;
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.RED);
                while(mIsRunnable){
                    Canvas canvas = lockCanvas();

                    if(canvas == null){
                        continue;
                    }
                    canvas.drawColor(Color.BLACK);
                    canvas.drawCircle(mTouchedX, mTouchedY, 50, paint);
                    unlockCanvasAndPost(canvas);


                }

            }
        });
        mThread.start();
    }

    public  void stop(){
        mIsRunnable = false;

    }



    public GameView(Context context) {
        super(context);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {


    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchedX = event.getX();
        mTouchedY = event.getY();
        return true;
    }
}
