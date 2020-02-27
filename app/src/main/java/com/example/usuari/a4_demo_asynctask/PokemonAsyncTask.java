package com.example.usuari.a4_demo_asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
//                                                 *1*     *2*       *3*
public class PokemonAsyncTask extends AsyncTask< String, Integer ,String> {

    private IJsonDownloadListener mListener;

    public interface IJsonDownloadListener {
        void onJsonDownloaded(String json);
    }

    public PokemonAsyncTask( IJsonDownloadListener listener) {
        mListener = listener;
    }

    @Override
    //         *3*                   /*1*/
    protected String doInBackground(String... strings) {

        // descarregar arxiu
        StringBuffer json = new StringBuffer();
        try {
            // Create a URL for the desired page
            URL url = new URL("https://raw.githubusercontent.com/infomila/testRemote2020/master/data.json");
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str="";
            while (  !isCancelled() && (str = in.readLine()) != null) {
                json.append(str);
            }
            in.close();
        } catch (Exception e) {
            Log.e("JSON", "error descarregant JSON", e);
        }
        // Aquí NOOOOOOOOOOOOOOOOOOOOOOOOO PUC accedir a la interfície gràfica
        return json.toString();
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        // Aquí PUC accedir a la interfície gràfica
        if(mListener!=null) {
            mListener.onJsonDownloaded(json);
        }
    }
}
