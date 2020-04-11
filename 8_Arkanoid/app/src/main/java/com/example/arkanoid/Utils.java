package com.example.arkanoid;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {


    public static Point[] getPixelsInLine(Point src, Point dst) {
        ArrayList<Point> points = new ArrayList<Point>();
        int x1=src.x;
        int y1=src.y;
        int x2=dst.x;
        int y2=dst.y;

        // delta of exact value and rounded value of the dependent variable
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                points.add(new Point( x, y));
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                points.add(new Point( x, y));
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
        return points.toArray(new Point[0]);
    }

}
