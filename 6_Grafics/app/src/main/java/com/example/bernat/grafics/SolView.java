package com.example.bernat.grafics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SolView extends View {
    public SolView(Context context) {
        super(context);
    }

    // és necessari per usar el control des de l'XML
    public SolView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int blue= Color.rgb(30,30,200);
        int blueLight= Color.rgb(160,160,255);
        float x =canvas.getWidth()/2;
        float y =canvas.getHeight()/2;
        float r = canvas.getWidth()/5;
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,r,p);
        p.setColor(blue);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x,y,r,p);
        //------------------
        float midaUll = r/3;

        pintaUll(canvas, blue, blueLight, x-r/2, y-r/2, p, midaUll);
        pintaUll(canvas, blue, blueLight, x+r/2-midaUll, y-r/2, p, midaUll);

        //----------------------------
        // Linies
        float lon = r/3;
        p.setColor(blue);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x,y-r, x, y-r-lon, p);
        canvas.drawLine(x,y+r, x, y+r+lon, p);
        canvas.drawLine(x+r,y, x+r+lon, y, p);
        canvas.drawLine(x-r,y, x-r-lon, y, p);

        //---------------------------------------------
        Path path = new Path();
        path.moveTo(x-r/2, y+r/4);
        //path.lineTo(x+r/2, y+r/2);
        path.quadTo(x,y+r*0.75f, x+r/2, y+r/4);
        path.quadTo(x,y+r*1.00f, x-r/2, y+r/4);

        p.setColor(Color.WHITE);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, p);
        p.setColor(blue);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, p);

    }

    private void pintaUll(Canvas canvas, int blue, int blueLight, float x, float y, Paint p, float midaUll) {
        p.setColor(blueLight);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, x+midaUll, y+midaUll, p);

        p.setColor(blue);
        p.setStyle(Paint.Style.STROKE);
        canvas.drawRect(x, y, x+midaUll, y+midaUll, p);


        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        float midaPupila = midaUll/2;
        float centrat =  (midaUll-midaPupila)/2;
        canvas.drawRect(x +centrat, y+centrat*1.25f, x+centrat+midaPupila, y+centrat*1.25f+midaPupila, p);

    }
}
