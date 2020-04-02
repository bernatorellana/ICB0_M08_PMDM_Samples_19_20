package com.example.arkanoid;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class ArkanoidSurface extends SurfaceView implements SurfaceHolder.Callback {

    FilUpdate thread;
    private Bitmap bitmapJoc;
    private Canvas bitmapJocCanvas;

    //--------------------------------------------
    // Dimensions de la pantalla
    private int W,H;
    //--------------------------------------------
    // Dimensions del totxo
    private int w,h;
    //--------------------------------------------
    // Dimensions de la graella de totxos
    private int fils, cols;
    private boolean totxos[][];
    //--------------------------------------------
    // Radi de la bola
    private int R;
    private int speedBola;
    private Paint paintBola;
    //--------------------------------------------
    private Point coordBola;
    private Point direccioBola;
    //--------------------------------------------

    public ArkanoidSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        //-------------------------------------------------------------------------------------------------------------
        //  Prenem les dimensions de la pantalla
        //-------------------------------------------------------------------------------------------------------------
        Display d = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        W = d.getWidth();
        H = d.getHeight();
        bitmapJoc = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888);
        bitmapJocCanvas = new Canvas(bitmapJoc);
        //-------------------------------------------------------------------------------------------------------------
        R = 30;
        speedBola = 15;

        //-------------------------------------------------------------------------------------------------------------
        fils = 10;
        cols = 5;
        totxos = new boolean[cols][fils];
        //-------------------------------------------------------------------------------------------------------------
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new FilUpdate(holder);
        thread.start();
    }

    private void dibuixaJoc(Canvas c) {
        c.drawColor(Color.BLACK);
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {    }

    private class FilUpdate extends Thread {

        SurfaceHolder holder;
        boolean stop = false;

        public void stopMe() {
            stop = true;
        }
        public FilUpdate(SurfaceHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            while(!stop) {
                //-------------pintar
                Canvas c = null;
                try {
                    c = holder.lockCanvas();
                    if (c != null) {
                        synchronized (c) {
                            ArkanoidSurface.this.dibuixaJoc(c);
                        }
                    }
                }finally {
                    if(c!=null) holder.unlockCanvasAndPost(c);
                }
                //------------- espera
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }


}
