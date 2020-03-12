package com.example.usuari.demoanimacio;

import android.animation.Animator;
import android.drm.DrmStore;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        iv.animate().setDuration(1000).rotationBy(90).alpha(0.5f).setListener(new Animator.AnimatorListener() {
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


        });


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
