package iesmila.net.spinnerandrecycler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity
    implements LlistaFragment.ILlistaFragment_PersonatgeSelectedListener,
        DetallFragment.PersonatgeChangedEventListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicialitzaUniversalImageLoader();

        setContentView(R.layout.activity_main);



        // 1.- Afagar el fragment
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fgrLlista);
        // 2.- Preguntar quin és l'item seleccionat (si n'hi ha algun)
        if(f!=null && f instanceof  LlistaFragment) {
            Personatge p = ((LlistaFragment)f).getPersonatgeSeleccionat();
            // 3.- Mostrar el personatge carregant el fragment de detall (onPersonatgeSelected(Personatge p))
            onPersonatgeSelected(p);
        }


    }

    private void inicialitzaUniversalImageLoader() {


        DisplayImageOptions options = new DisplayImageOptions.Builder().
                showImageOnLoading(R.drawable.loading).build();


        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).
                defaultDisplayImageOptions(options).
                build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onPersonatgeSelected(Personatge p) {
        if(p!=null) {
            // Creació dinàmica del fragment
            if (findViewById(R.id.llyDetall) != null) {
                // Estic en horitzontal
                // posem el fragment de detall al contenidor

                DetallFragment fragmentDetall = DetallFragment.newInstance(p);

                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.llyDetall, fragmentDetall);
                t.commit();

            } else {
                //llanço una nova activity que durà a dins el fragment de detall
                
            }
        } else {// p val null

            // Si no hi ha cap personatge seleccionat, eliminem el fragment
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.llyDetall);
            if(f!= null) {
                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.remove(f);
                t.commit();
            }
        }

    }

    @Override
    public void onDeleted(Personatge p) {
        LlistaFragment lf = (LlistaFragment)
                getSupportFragmentManager().findFragmentById(R.id.fgrLlista);
        lf.onDeleted(p);
    }

    @Override
    public void onSaved(Personatge p) {
        LlistaFragment lf = (LlistaFragment)
                getSupportFragmentManager().findFragmentById(R.id.fgrLlista);
        lf.onSaved(p);
    }
}



