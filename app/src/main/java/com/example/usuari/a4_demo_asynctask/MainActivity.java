package com.example.usuari.a4_demo_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PokemonAsyncTask.IJsonDownloadListener {


    Button btnGo;
    EditText edtJSON;
    ProgressBar pgrProgress;

    PokemonAsyncTask pAt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------------------------------------
        btnGo = findViewById(R.id.btnGo);
        edtJSON = findViewById(R.id.edtJSON);
        pgrProgress = findViewById(R.id.pgrProgress);
        //-------------------------------------------
        btnGo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        pAt = new PokemonAsyncTask(this);
        //pAt.doInBackground(); // NEVER / JAMAIS / NUNCA / MAI --> android.os.NetworkOnMainThreadException
        pgrProgress.setVisibility(View.VISIBLE);
        btnGo.setEnabled(false);
        pAt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onJsonDownloaded(String json) {
        edtJSON.setText(json);
        pgrProgress.setVisibility(View.INVISIBLE);
        btnGo.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(pAt!=null && !pAt.isCancelled()) {
            pAt.cancel(true);

        }
    }
}

