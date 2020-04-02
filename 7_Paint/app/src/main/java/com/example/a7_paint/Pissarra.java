package com.example.a7_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class Pissarra extends SurfaceView implements SurfaceHolder.Callback {


    private Bitmap bitmap;
    private Bitmap rainbow;
    private Canvas canvas;
    private Paint paint;
    private Paint blackPaint;
    private Path userPath;
    private FilRepintat fil;

    public Pissarra(Context context) {
        this(context, null);
    }

    enum Mode {
        PINTURA,
        PALETA
    }

    Mode mode;

    private void setMode(Mode mode) {
        this.mode = mode;
    }

    public Pissarra(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMode(Mode.PINTURA);
        getHolder().addCallback(this);
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        bitmap = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);

        rainbow = BitmapFactory.decodeResource(this.getResources(), R.drawable.rainbow);

        canvas = new Canvas(bitmap);
        userPath = new Path();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setStrokeWidth(2);

    }

    float baseX, baseY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (mode == Mode.PINTURA) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    userPath.moveTo(x, y);
                    baseX = x;
                    baseY = y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    userPath.quadTo(baseX, baseY, x, y);
                    baseX = x;
                    baseY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    canvas.drawPath(userPath, paint);
                    userPath.reset();
            }
        } else if (mode == Mode.PALETA) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_MOVE:
                    userPath.reset();
                    int xx = (int)(rainbow.getWidth()*x/bitmap.getWidth());
                    int yy = (int)( rainbow.getHeight()*y/(bitmap.getHeight()/2.0));
                    int color = rainbow.getPixel(xx,yy);
                    userPath.addCircle(x,y,20, Path.Direction.CCW);
                    paint.setColor(color);
                    //userPath.addCircle(x,y,10, Path.Direction.CCW);

                    break;
                case MotionEvent.ACTION_UP:
                    setMode(Mode.PINTURA);
                    userPath.reset();
            }
        }
        return true;
    }

    private void drawCanvas(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (mode == Mode.PALETA) {
            Rect src = new Rect(0, 0, rainbow.getWidth(), rainbow.getHeight());
            Rect dst = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
            canvas.drawBitmap(rainbow, src, dst, null);
        }
        canvas.drawPath(userPath, mode==Mode.PALETA?blackPaint:paint);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        fil = new FilRepintat(holder);
        fil.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        fil.stopMe();
    }

    public void showPalete() {
        setMode(Mode.PALETA);
    }

    private class FilRepintat extends Thread {
        SurfaceHolder holder;

        FilRepintat(SurfaceHolder holder) {
            this.holder = holder;
        }

        public boolean stop = false;

        public void stopMe() {
            stop = true;
        }

        @Override
        public void run() {
            while (!stop) {
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();
                    if (canvas != null) {
                        synchronized (canvas) {
                            Pissarra.this.drawCanvas(canvas);
                        }
                    }
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
