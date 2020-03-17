package com.example.usuari.demoanimacio;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.drm.DrmStore;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv;
    private ConstraintLayout clyScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.imvSubZero);
        AnimationDrawable animation = (AnimationDrawable) iv.getBackground();
        animation.start();

        //------------------------
        clyScreen = (ConstraintLayout) findViewById(R.id.clyScreen);
        clyScreen.setOnClickListener(this);
        //-------------------------

        // Animació directa
        /*iv.animate().setDuration(1000).rotationBy(90).alpha(0.5f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }
            @Override
            public void onAnimationRepeat(Animator animation) { }
            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                iv.animate().setDuration(1000).rotationBy(-90).alpha(1f).setListener(null);
            }


        });*/
        //----------------------------------------
        // Les propietats dels objectes
        /*
        iv.setAlpha(0.5f);
        iv.setX(200f);
        iv.setRotation(45);
        */

        //--------------------------------------------------
        // Animació usant ObjectAnimator
        clyScreen.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout()  {
                clyScreen.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ferAnimacio();
            }
        });

    }

    private void ferAnimacio() {

        int w = clyScreen.getWidth();
        int h = clyScreen.getHeight();
        View llyTerra = findViewById(R.id.llyTerra);
        h-= llyTerra.getHeight();



        ObjectAnimator a10 = ObjectAnimator.ofFloat(iv, "X", w-iv.getWidth() - (iv.getHeight()-iv.getWidth())/2);
        ObjectAnimator a11 = ObjectAnimator.ofFloat(iv, "ScaleX", 1, 2, 1);
        ObjectAnimator a12 = ObjectAnimator.ofFloat(iv, "ScaleY", 1, 2, 1);
        ObjectAnimator a13 = ObjectAnimator.ofFloat(iv, "Y",  h-iv.getHeight() , h-iv.getHeight()*1.5f , h-iv.getHeight());
        ObjectAnimator a2 = ObjectAnimator.ofFloat(iv, "Rotation", -90);
        ObjectAnimator a3 = ObjectAnimator.ofFloat(iv, "Y", 0);
        ObjectAnimator a4 = ObjectAnimator.ofFloat(iv, "Rotation", -180);
        ObjectAnimator a5 = ObjectAnimator.ofFloat(iv, "X", (iv.getHeight()-iv.getWidth())/2);
        ObjectAnimator a6 = ObjectAnimator.ofFloat(iv, "Rotation", -270);
        ObjectAnimator a7 = ObjectAnimator.ofFloat(iv, "Y", h-iv.getHeight());
        ObjectAnimator a8 = ObjectAnimator.ofFloat(iv, "Rotation", -360);

        AnimatorSet as1 = new AnimatorSet();
        as1.playTogether(a10, a11,a12, a13);
        as1.setDuration(3000);
        AnimatorSet as = new AnimatorSet();
        as.playSequentially(as1,a2, a3, a4, a5, a6, a7, a8);
        as.setDuration(1000);
        as.start();
    }








    @Override
    public void onClick(View v) {
        //Aturem l'animació
        AnimationDrawable animation = (AnimationDrawable) iv.getBackground();
        animation.stop();
        // Carregar l'animació de cop de puny
        AnimationDrawable hitAnimation = (AnimationDrawable)getResources().getDrawable(R.drawable.animacio_hit_sub_zero);
        iv.setBackgroundDrawable(hitAnimation);
        hitAnimation.setOneShot(true);
        hitAnimation.start();
        // l'animació dura ...?
        EsperaUnMoment espera = new EsperaUnMoment();
        espera.execute(500);

    }

    private void reengegarAnimacio() {
        AnimationDrawable animation = (AnimationDrawable) iv.getBackground();
        animation.stop();
        // Carregar l'animació idle
        AnimationDrawable idleAnimation = (AnimationDrawable)getResources().getDrawable(R.drawable.animacio_idle_sub_zero);
        iv.setBackgroundDrawable(idleAnimation);
        idleAnimation.setOneShot(false);
        idleAnimation.start();

    }

    private class EsperaUnMoment extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... temps) {
            try {
                Thread.sleep(temps[0]);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MainActivity.this.reengegarAnimacio();
        }
    }


}
