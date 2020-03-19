package com.example.bernat.grafics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class SolSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public SolSurfaceView(Context context) {

        super(context);

        getHolder().addCallback(this);
    }

    // és necessari per usar el control des de l'XML
    public SolSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    SolSurfaceViewThread fil;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        fil = new SolSurfaceViewThread(holder);
        fil.start(); // engegar el fil
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(fil!=null) {
            fil.aturaFil();
            while(true){
                try {
                    // ens esperem a que el fil acabi
                    fil.join();
                    break;
                } catch(InterruptedException ex){
                }
            }
        }
    }
}
